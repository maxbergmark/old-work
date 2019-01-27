import wave
import random
import struct
import time
import winsound
import math
import sys


def makeWave(length, freq = 1000, amplitude = 1):
	temp = []
	for i in range(length):
		ramp = 1-2**(-.01*(length-i-1))-2**(-.01*i)
		value = int(ramp*amplitude*2**15*math.sin(i*freq*2*math.pi/44100))
		packed_value = struct.pack('h', value)
		temp.append(packed_value)
	return b''.join(temp)


def makeMorse(letter, morse_dict, speed = 1):
	morse = []
	for digit in morse_dict[letter]:
		if digit == '1':
			morse.append(makeWave(int(20000/speed), 880))
		elif digit == '2':
			morse.append(makeWave(int(40000/speed), 880))
		else:
			morse.append(makeWave(int(20000/speed), 880, 0))
		morse.append(makeWave(int(20000/speed), 880, 0))
	return b''.join(morse)


def restartLine():
    sys.stdout.write('\r')
    sys.stdout.flush()


morse_dict = {'!': '000', '?': '000', '\n': '00000', ' ': '00', '.': '000', ',': '0', 'a': '12', 'b': '2111', 'c': '2121', 'd': '211', 'e': '1', 'f': '1121', 'g': '221', 'h': '1111', 'i': '11', 'j': '1222', 'k': '212', 'l': '1211', 'm': '22', 'n': '21', 'o': '222', 'p': '1221', 'q': '2212', 'r': '121', 's': '111', 't': '2', 'u': '112', 'v': '1112', 'w': '122', 'x': '2112', 'y': '2122', 'z': '2211', '1': '12222', '2': '11222', '3': '11122', '4': '11112', '5': '11111', '6': '21111', '7': '22111', '8': '22211', '9': '22221', '0': '22222'}

#inp = []
s=''
try:
	while True:
		s += '\n' + str(input())
		#inp.append(s)
except:
	pass

input_string = s
#input_string = str(input('Text: '))
#speed = float(input('Speed: '))
speed = 5

t0 = time.clock()

output = wave.open('morsefile.wav', 'w')
output.setparams((2, 2, 44100, 0, 'NONE', 'compressed'))

values = []
count = 0
length = len(input_string)

print('\n   Generating morse code...')

for letter in input_string:
	try:
		values.append(makeMorse(letter.lower(), morse_dict, speed))
		values.append(makeWave(int(30000/speed), 0))
		count += 1
		restartLine()
		sys.stdout.write('   |' + int(30*count/length)*'=' + (30-int(30*count/length))*' ' + '| ' + str(round(100*count/length)) + '%')
		sys.stdout.flush()
	except KeyError:
		pass


value_str = b''.join(values)
output.writeframes(value_str)

output.close() 

print('\n   Complete! Elapsed time:', round(time.clock()-t0, 3), 'seconds.')
print('   Playing generated morse code...')

winsound.PlaySound('morsefile.wav', winsound.SND_ASYNC | winsound.SND_FILENAME)
duration = output.getnframes() / output.getframerate()
time.sleep(duration)
print('   Completed!')