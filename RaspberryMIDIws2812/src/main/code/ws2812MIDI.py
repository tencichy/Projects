import mido as midi
from neopixel import *

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
				if msg.note == 55:
					strip.setPixelColor(1, Color(74, 20, 140))
					strip.setPixelColor(60, Color(74, 20, 140))
					strip.show()
				if msg.note == 50:
					strip.setPixelColor(2, Color(38, 198, 218))
					strip.setPixelColor(59, Color(27, 94, 32))
					strip.show()
				if msg.note == 57:
					strip.setPixelColor(3, Color(233, 30, 99))
					strip.setPixelColor(58, Color(233, 30, 99))
					strip.show()
				if msg.note == 60:
					strip.setPixelColor(4, Color(244, 81, 30))
					strip.setPixelColor(57, Color(244, 81, 30))
					strip.show()
				if msg.note == 74:
					strip.setPixelColor(5, Color(38, 198, 218))
					strip.show()
				if msg.note == 53:
					strip.setPixelColor(6, Color(48, 63, 159))
					strip.show()
				if msg.note == 62:
					strip.setPixelColor(7, Color(244, 67, 54))
					strip.show()
				if msg.note == 69:
					strip.setPixelColor(8, Color(0, 77, 64))
					strip.show()
				if msg.note == 76:
					strip.setPixelColor(9, Color(192, 202, 51))
					strip.show()
				if msg.note == 64:
					strip.setPixelColor(10, Color(192, 202, 51))
					strip.show()
				if msg.note == 46:
					strip.setPixelColor(11, Color(62, 39, 35))
					strip.show()
				if msg.note == 67:
					strip.setPixelColor(12, Color(3, 155, 229))
					strip.show()
				if msg.note == 65:
					strip.setPixelColor(13, Color(38, 166, 154))
					strip.show()
				if msg.note == 72:
					strip.setPixelColor(14, Color(216, 27, 96))
					strip.show()
				if msg.note == 70:
					strip.setPixelColor(15, Color(149, 117, 205))
					strip.show()
				if msg.note == 77:
					strip.setPixelColor(16, Color(239, 83, 80))
					strip.show()
				if msg.note == 48:
					strip.setPixelColor(17, Color(38, 198, 218))
					strip.show()
				if msg.note == 58:
					strip.setPixelColor(18, Color(124, 179, 66))
					strip.show()
				if msg.note == 34:
					strip.setPixelColor(19, Color(49, 27, 146))
					strip.show()
				if msg.note == 52:
					strip.setPixelColor(20, Color(0, 70, 182))
					strip.show()
				if msg.note == 43:
					strip.setPixelColor(21, Color(255, 193, 7))
					strip.show()
				if msg.note == 45:
					strip.setPixelColor(22, Color(255, 143, 0))
					strip.show()
				if msg.note == 36:
					strip.setPixelColor(23, Color(67, 160, 71))
					strip.show()
				if msg.note == 38:
					strip.setPixelColor(24, Color(0, 110, 108))
					strip.show()
				if msg.note == 81:
					strip.setPixelColor(25, Color(255, 167, 38))
					strip.show()
				if msg.note == 61:
					strip.setPixelColor(26, Color(0, 187, 22))
					strip.show()
				if msg.note == 33:
					strip.setPixelColor(27, Color(38, 229, 60))
					strip.show()
				if msg.note == 79:
					strip.setPixelColor(28, Color(27, 94, 32))
					strip.show()
				if msg.note == 73:
					strip.setPixelColor(29, Color(96, 220, 0))
					strip.show()
				if msg.note == 40:
					strip.setPixelColor(30, Color(96, 220, 0))
					strip.show()
				if msg.note == 41:
					strip.setPixelColor(31, Color(0, 70, 182))
					strip.show()
				if msg.note == 51:
					strip.setPixelColor(32, Color(215, 84, 2))
					strip.show()
				if msg.note == 63:
					strip.setPixelColor(33, Color(247, 120, 40))
					strip.show()
			elif msg.type == "note_off":
				if msg.note == 55:
					strip.setPixelColor(1, Color(0, 0, 0))
					strip.setPixelColor(60, Color(0, 0, 0))
					strip.show()
				if msg.note == 50:
					strip.setPixelColor(2, Color(0, 0, 0))
					strip.setPixelColor(59, Color(0, 0, 0))
					strip.show()
				if msg.note == 57:
					strip.setPixelColor(3, Color(0, 0, 0))
					strip.setPixelColor(58, Color(0, 0, 0))
					strip.show()
				if msg.note == 60:
					strip.setPixelColor(4, Color(0, 0, 0))
					strip.setPixelColor(57, Color(0, 0, 0))
					strip.show()
				if msg.note == 74:
					strip.setPixelColor(5, Color(0, 0, 0))
					strip.show()
				if msg.note == 53:
					strip.setPixelColor(6, Color(0, 0, 0))
					strip.show()
				if msg.note == 62:
					strip.setPixelColor(7, Color(0, 0, 0))
					strip.show()
				if msg.note == 69:
					strip.setPixelColor(8, Color(0, 0, 0))
					strip.show()
				if msg.note == 76:
					strip.setPixelColor(9, Color(0, 0, 0))
					strip.show()
				if msg.note == 64:
					strip.setPixelColor(10, Color(0, 0, 0))
					strip.show()
				if msg.note == 46:
					strip.setPixelColor(11, Color(0, 0, 0))
					strip.show()
				if msg.note == 67:
					strip.setPixelColor(12, Color(0, 0, 0))
					strip.show()
				if msg.note == 65:
					strip.setPixelColor(13, Color(0, 0, 0))
					strip.show()
				if msg.note == 72:
					strip.setPixelColor(14, Color(0, 0, 0))
					strip.show()
				if msg.note == 70:
					strip.setPixelColor(15, Color(0, 0, 0))
					strip.show()
				if msg.note == 77:
					strip.setPixelColor(16, Color(0, 0, 0))
					strip.show()
				if msg.note == 48:
					strip.setPixelColor(17, Color(0, 0, 0))
					strip.show()
				if msg.note == 58:
					strip.setPixelColor(18, Color(0, 0, 0))
					strip.show()
				if msg.note == 34:
					strip.setPixelColor(19, Color(0, 0, 0))
					strip.show()
				if msg.note == 52:
					strip.setPixelColor(20, Color(0, 0, 0))
					strip.show()
				if msg.note == 43:
					strip.setPixelColor(21, Color(0, 0, 0))
					strip.show()
				if msg.note == 45:
					strip.setPixelColor(22, Color(0, 0, 0))
					strip.show()
				if msg.note == 36:
					strip.setPixelColor(23, Color(0, 0, 0))
					strip.show()
				if msg.note == 38:
					strip.setPixelColor(24, Color(0, 0, 0))
					strip.show()
				if msg.note == 81:
					strip.setPixelColor(25, Color(0, 0, 0))
					strip.show()
				if msg.note == 61:
					strip.setPixelColor(26, Color(0, 0, 0))
					strip.show()
				if msg.note == 33:
					strip.setPixelColor(27, Color(0, 0, 0))
					strip.show()
				if msg.note == 79:
					strip.setPixelColor(28, Color(0, 0, 0))
					strip.show()
				if msg.note == 73:
					strip.setPixelColor(29, Color(0, 0, 0))
					strip.show()
				if msg.note == 40:
					strip.setPixelColor(30, Color(0, 0, 0))
					strip.show()
				if msg.note == 41:
					strip.setPixelColor(31, Color(0, 0, 0))
					strip.show()
				if msg.note == 51:
					strip.setPixelColor(32, Color(0, 0, 0))
					strip.show()
				if msg.note == 63:
					strip.setPixelColor(33, Color(0, 0, 0))
					strip.show()
	except Exception as e:
		if isinstance(e, AttributeError):
			print("Midi error")
			pass
		elif isinstance(e, KeyboardInterrupt):
			colorWipe(strip, Color(0, 0, 0), 10)
