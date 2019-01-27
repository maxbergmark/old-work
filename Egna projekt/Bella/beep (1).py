import os
import time

def flicFunc():
	global count
#	tones = [0,2,4,5,7,9,11]
	tones = [0,4,7]
	tones = [2,0,3,1,4,2,5,3,6,4,7,5,8,6,9,7,10,8,11,9,12,10,13,11]
#	tones = [0,0,7,7,9,9,7,7,5,5,4,4,2,2,0,0,7,7,5,5,4,4,2,2,7,7,5,5,4,4,2,2]
#	for i in range(1):
	for i in range(4*len(tones)):
#	for i in range(len(tones)):
#		count[0] = count[1]
		os.system('beep -f%d -l20' % (110*2**((tones[i]))))

	# time.sleep(200)

flicFunc()
