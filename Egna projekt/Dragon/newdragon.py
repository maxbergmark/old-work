from tkinter import *
import math
import time
import numpy as np
from PIL import Image, ImageDraw
import sys

def iterate(dragon):
	return np.concatenate((dragon, np.array([dragon[(len(dragon)-1)//2]]), -1*np.array(dragon)[::-1]))

def fourDragons(dragon, draw, size):
	t0 = time.clock()
	names = ['first', 'second', 'third', 'fourth']
	for c in range(4):
		t1 = time.clock()
		pos = [size//2, size//2]
		ew = [1, 0, -1, 0]
		ns = [0, 1, 0, -1]
		direction = c
		d = size*.28/2**(.5*n)*(1+.2*((n+1)%2))
		for i in dragon + [0]:
			#pos.extend([pos[-2]+d*math.cos(ang), pos[-1]+d*math.sin(ang)])
			pos.extend([pos[-2]+ew[direction%4], pos[-1]+ns[direction%4]])
			direction += i
		draw.line(pos, fill = '#%02x%02x%02x' % (0, 255*(1+c)//4, 0))
		#canvas.create_line(pos, fill = '#%02x%02x%02x' % (0, 255*(1+c)//4, 0))
		print(names[c], 'dragon drawn in', round(time.clock()-t1, 5), 'seconds')
	print('all dragons drawn in', round(time.clock()-t0, 5), 'seconds')

def standardDragonFaster(dragon, draw, size):
	t0 = time.clock()
	dragon = np.concatenate((np.array([dragon[0]]), dragon))
	directions = np.cumsum(dragon) % 4
	dx = np.take([0, 2, 0, -2], directions)
	dy = np.take([-2, 0, 2, 0], directions)
	xpos = size//2-(max(np.cumsum(dx))+min(np.cumsum(dx)))//2
	ypos = size//2-(max(np.cumsum(dy))+min(np.cumsum(dy)))//2
	x = np.cumsum(np.r_[xpos, dx])
	y = np.cumsum(np.r_[ypos, dy])
	pos = np.vstack((x,y)).ravel('F')
	print('fast dragon created in', round(time.clock()-t0, 5), 'seconds')
	t0 = time.clock()
	draw.line(pos.tolist(), fill = '#00ff00')
	print('dragon drawn in', round(time.clock()-t0, 5), 'seconds')
	return pos.nbytes


n = int(input('Iterations: '))




dragon = np.array([1])
t0 = time.clock()
for _ in range(n):
	dragon = iterate(dragon)
dragonSize = dragon.nbytes
print(dragon)
print('array of length', len(dragon),'created in', round(time.clock()-t0, 5), 'seconds')

size = 2**0

t1 = time.clock()
image = Image.new('RGB', (size, size), 'black')
draw = ImageDraw.Draw(image, 'RGB')
print('image created in', round(time.clock()-t1, 5), 'seconds')
pathSize = standardDragonFaster(dragon, draw, size)

t1 = time.clock()
image.save('dragon5/dragon' + str(n) + '-' + str(size) + '.png')
print('image saved in', round(time.clock()-t1, 5), 'seconds')
print('total execution time:', round(time.clock()-t0, 5), 'seconds')
print('size of dragon array:', dragonSize//1024**2, 'MB')
print('size of path array:', pathSize//1024**2, 'MB')
