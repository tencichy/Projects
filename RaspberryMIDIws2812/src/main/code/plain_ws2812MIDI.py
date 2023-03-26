import mido as midi
#import RPi.GPIO as GPIO
from neopixel import *

# Pedal and LED configuration
#GPIO.setmode(GPIO.BCM)
#O = GPIO.OUT
#H = GPIO.HIGH
#L = GPIO.LOW
#led1 = 23
#led2 = 24
#led3 = 25
#pedal = 16
#GPIO.setup(led1, O)
#GPIO.setup(led2, O)
#GPIO.setup(led3, O)
#GPIO.setup(pedal, GPIO.IN, pull_up_down=GPIO.PUD_UP)
#GPIO.output(led1, L)
#GPIO.output(led2, L)
#GPIO.output(led3, L)
#btnVal = False
#btnLastVal = False
#counter = 1

# MIDI configuration
devices = midi.get_output_names()
port = midi.open_input(devices[1])

# LED strip configuration:
LED_COUNT = 60  # Number of LED pixels.
LED_PIN = 18  # GPIO pin connected to the pixels (18 uses PWM!).
LED_FREQ_HZ = 800000  # LED signal frequency in hertz (usually 800khz)
LED_DMA = 10  # DMA channel to use for generating signal (try 10)
LED_BRIGHTNESS = 150  # Set to 0 for darkest and 255 for brightest
LED_INVERT = False  # True to invert the signal (when using NPN transistor level shift)
LED_CHANNEL = 0  # set to '1' for GPIOs 13, 19, 41, 45 or 53
strip = Adafruit_NeoPixel(LED_COUNT, LED_PIN, LED_FREQ_HZ, LED_DMA, LED_INVERT, LED_BRIGHTNESS, LED_CHANNEL)
strip.begin()


def colorWipe(strip, color, wait_ms=50):
    for i in range(strip.numPixels()):
        strip.setPixelColor(i, color)
        strip.show()
        time.sleep(wait_ms / 1000.0)


def valmap(x, in_min, in_max, out_min, out_max):
    return (x - in_min) * (out_max - out_min) // (in_max - in_min) + out_min


while True:
    try:
        for msg in port.iter_pending():
            if msg.type == "note_on":
                print(msg.type + " - " + str(msg.note))
            elif msg.type == "note_off":
                print(msg.type + " - " + str(msg.note))
    except Exception as e:
        if isinstance(e, AttributeError):
            print("Midi error")
            pass
        elif isinstance(e, KeyboardInterrupt):
            colorWipe(strip, Color(0, 0, 0), 10)
