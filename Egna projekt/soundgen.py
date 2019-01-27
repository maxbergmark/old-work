import os

for i in range(48):
	for j in [1, -1]:
		os.system('beep -f%d -l20' % (220*2**((i+j)/12)))