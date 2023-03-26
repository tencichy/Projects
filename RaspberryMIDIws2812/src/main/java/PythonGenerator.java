import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

class PythonGenerator {

    private final String part1 = "import mido as midi\n" +
            "#import RPi.GPIO as GPIO\n" +
            "from neopixel import *\n" +
            "\n" +
            "# Pedal and LED configuration\n" +
            "#GPIO.setmode(GPIO.BCM)\n" +
            "#O = GPIO.OUT\n" +
            "#H = GPIO.HIGH\n" +
            "#L = GPIO.LOW\n" +
            "#led1 = 23\n" +
            "#led2 = 24\n" +
            "#led3 = 25\n" +
            "#pedal = 16\n" +
            "#GPIO.setup(led1, O)\n" +
            "#GPIO.setup(led2, O)\n" +
            "#GPIO.setup(led3, O)\n" +
            "#GPIO.setup(pedal, GPIO.IN, pull_up_down=GPIO.PUD_UP)\n" +
            "#GPIO.output(led1, L)\n" +
            "#GPIO.output(led2, L)\n" +
            "#GPIO.output(led3, L)\n" +
            "#btnVal = False\n" +
            "#btnLastVal = False\n" +
            "#counter = 1\n" +
            "\n" +
            "# MIDI configuration\n" +
            "devices = midi.get_output_names()\n" +
            "port = midi.open_input(devices[1])\n" +
            "\n" +
            "# LED strip configuration:\n";
    private final String part2 = "LED_PIN = 18  # GPIO pin connected to the pixels (18 uses PWM!).\n" +
            "LED_FREQ_HZ = 800000  # LED signal frequency in hertz (usually 800khz)\n" +
            "LED_DMA = 10  # DMA channel to use for generating signal (try 10)\n" +
            "LED_BRIGHTNESS = 150  # Set to 0 for darkest and 255 for brightest\n" +
            "LED_INVERT = False  # True to invert the signal (when using NPN transistor level shift)\n" +
            "LED_CHANNEL = 0  # set to '1' for GPIOs 13, 19, 41, 45 or 53\n" +
            "strip = Adafruit_NeoPixel(LED_COUNT, LED_PIN, LED_FREQ_HZ, LED_DMA, LED_INVERT, LED_BRIGHTNESS, LED_CHANNEL)\n" +
            "strip.begin()\n" +
            "\n" +
            "\n" +
            "def colorWipe(strip, color, wait_ms=50):\n" +
            "\tfor i in range(strip.numPixels()):\n" +
            "\t\tstrip.setPixelColor(i, color)\n" +
            "\t\tstrip.show()\n" +
            "\t\ttime.sleep(wait_ms / 1000.0)\n" +
            "\n" +
            "\n" +
            "def valmap(x, in_min, in_max, out_min, out_max):\n" +
            "\treturn (x - in_min) * (out_max - out_min) // (in_max - in_min) + out_min\n" +
            "\n" +
            "\n" +
            "while True:\n" +
            "\ttry:\n" +
            "\t\tfor msg in port.iter_pending():\n" +
            "\t\t\tif msg.type == \"note_on\":\n";
    private final String part3 = "\t\t\telif msg.type == \"note_off\":\n";
    private final String part4 = "\texcept Exception as e:\n" +
            "\t\tif isinstance(e, AttributeError):\n" +
            "\t\t\tprint(\"Midi error\")\n" +
            "\t\t\tpass\n" +
            "\t\telif isinstance(e, KeyboardInterrupt):\n" +
            "\t\t\tcolorWipe(strip, Color(0, 0, 0), 10)\n";

    PythonGenerator(MainController mainController,ObservableList<Commands> commands, String ip, String login, String password, String fileName, Integer leds) throws Exception{
        File sendFile = new File("src/main/code/send.bat");
        FileWriter sendFileWriter = new FileWriter(sendFile);
        sendFileWriter.write("psftp " + ip + " -l " + login +" -pw " + password + " -b ftpTransfer.scr");
        sendFileWriter.close();
        File ftpFile = new File("src/main/code/ftpTransfer.scr");
        FileWriter ftpFileWriter = new FileWriter(ftpFile);
        if(fileName == null) {
            ftpFileWriter.write("lcd " + System.getProperty("user.dir") + "\\src\\main\\code\n" +
                    "put " + "ws2812MIDI.py");
        }else{
            ftpFileWriter.write("lcd " + System.getProperty("user.dir") + "\\src\\main\\code\n" +
                    "put " + fileName);
        }
        ftpFileWriter.close();
        File pythonFile;
        if(fileName == null){
            pythonFile = new File("src/main/code/ws2812MIDI.py");
        }else{
            pythonFile = new File("src/main/code/" + fileName);
        }
        FileWriter pythonFileWriter = new FileWriter(pythonFile);
        StringBuilder toWrite = new StringBuilder(part1 + "LED_COUNT = " + leds + "  # Number of LED pixels.\n" + part2);
        for (Commands c: commands) {
            StringBuilder temp = new StringBuilder("\t\t\t\tif msg.note == " + c.getNote() + ":\n");
            for (Map.Entry<Integer,Color> e: c.getLedsNumber().entrySet()) {
                temp.append("\t\t\t\t\tstrip.setPixelColor(").append(e.getKey()).append(", Color(").append(e.getValue().getRed()).append(", ").append(e.getValue().getGreen()).append(", ").append(e.getValue().getBlue()).append("))\n");
            }
            temp.append("\t\t\t\t\tstrip.show()\n");
            toWrite.append(temp.toString());
        }
        toWrite.append(part3);
        for (Commands c: commands) {
            StringBuilder temp = new StringBuilder("\t\t\t\tif msg.note == " + c.getNote() + ":\n");
            for (Map.Entry<Integer,Color> e: c.getLedsNumber().entrySet()) {
                temp.append("\t\t\t\t\tstrip.setPixelColor(").append(e.getKey()).append(", Color(0, 0, 0))\n");
            }
            temp.append("\t\t\t\t\tstrip.show()\n");
            toWrite.append(temp.toString());
        }
        toWrite.append(part4);
        pythonFileWriter.write(toWrite.toString());
        pythonFileWriter.close();
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "send.bat");
        File dir = new File("src/main/code");
        pb.directory(dir);
        new Thread(() -> {
            try {
                Process p = pb.start();
                if(p.getErrorStream().read() != 85){
                    //TODO: Set spinner visible to false
                    Platform.runLater(()-> new AlertGenerator("Error","Error while sending file to raspberry", Alert.AlertType.ERROR));
                }else{
                    //TODO: Set spinner visible to false
                }
            } catch (IOException e) {
                new TextAlertGenerator(e, Alert.AlertType.ERROR);
            }
        }).start();


    }
}
