from tkinter import *
import math
import time
import numpy as np
from PIL import Image, ImageDraw
import sys
import matplotlib.pyplot as plt

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

def standardDragon(dragon, draw, size):
	t0 = time.clock()
	pos = [.55*size//2, 1.15*size//2]
	ang = -math.pi/4*(n+1)
	d = size*.4/2**(.5*n)
	for i in np.append(dragon, np.array([0]), 0):
		pos.extend([pos[-2]+round(d*math.cos(ang)), pos[-1]+round(d*math.sin(ang))])
		#pos = pos + [pos[-2]+round(d*math.cos(ang)), pos[-1]+round(d*math.sin(ang))]
		ang += i*math.pi/2
	#canvas.create_line(pos, fill = 'green')
	print('dragon created in', round(time.clock()-t0, 5), 'seconds')
	t0 = time.clock()
	draw.line(pos, fill = '#00ff00')
	print('dragon drawn in', round(time.clock()-t0, 5), 'seconds')

def standardDragonFast(dragon, draw, size):
	t0 = time.clock()
	#pos = [.55*size//2, 1.15*size//2]
	pos = [100, 100]
	ew = [1, 0, -1, 0]
	ns = [0, 1, 0, -1]
	#d = size*.4/2**(.5*n)
	d = 2
	direction = 0
	for i in np.append(dragon, np.array([0]), 0):
		pos.extend([pos[-2]+d*ew[direction%4], pos[-1]+d*ns[direction%4]])
		direction += i
	#canvas.create_line(pos, fill = 'green')
	#print(pos)
	print('dragon created in', round(time.clock()-t0, 5), 'seconds')
	t0 = time.clock()
	draw.line(pos, fill = '#00ff00')
	print('dragon drawn in', round(time.clock()-t0, 5), 'seconds')

def standardDragonFaster(dragon, draw, size):
	t0 = time.clock()
	xpos = .55*size//2
	ypos = 1.15*size//2
	#xpos = 100
	#ypos = 100
	dragon = np.concatenate((np.array([dragon[0]]), dragon))
	directions = np.cumsum(dragon) % 4
	dx = np.take([0, 1, 0, -1], directions)
	dy = np.take([-1, 0, 1, 0], directions)
	x = np.cumsum(np.r_[xpos, dx])
	y = np.cumsum(np.r_[ypos, dy])
	#print(x, y)
	pos = np.vstack((x,y)).ravel('F')
	print('fast dragon created in', round(time.clock()-t0, 5), 'seconds')
	t0 = time.clock()
	draw.line(pos.tolist(), fill = '#00ff00')
	print('dragon drawn in', round(time.clock()-t0, 5), 'seconds')
	return pos.nbytes

def standardDragonTest(dragon, size):
	t0 = time.clock()
	xpos = .55*size//2
	ypos = 1.15*size//2
	#xpos = 100
	#ypos = 100
	dragon = np.concatenate((np.array([dragon[0]]), dragon))
	directions = np.cumsum(dragon) % 4
	dx = np.take([0, 5, 0, -5], directions)
	dy = np.take([-5, 0, 5, 0], directions)
	x = np.cumsum(np.r_[xpos, dx])
	y = np.cumsum(np.r_[ypos, dy])
	#print(x, y)
	#pos = np.vstack((x,y)).ravel('F')

	#print(pos)
	print('test dragon created in', round(time.clock()-t0, 5), 'seconds')
	#t0 = time.clock()
	fig= plt.figure(figsize = (size, size), dpi = 1, frameon = False)

	#fig = plt.figure(figsize = (1, 1))
	ax = fig.add_axes([0, 0, 1, 1])
	fig.patch.set_visible(False)
	ax.plot(x, y, color = '#00ff00', linewidth = 1, antialiased = False)
	ax.set_axis_bgcolor('#000000')
	ax.set_xticks([])
	ax.set_yticks([])
	ax.set_ylim([0, size])
	ax.set_xlim([0, size])
	#ax.axis('off')
	fig.savefig('dragon4/test.png', dpi = 1)
	print('dragon drawn in', round(time.clock()-t0, 5), 'seconds')

n = int(input('Iterations: '))




dragon = np.array([1])
#dragon = [1]
t0 = time.clock()
for _ in range(n):
	dragon = iterate(dragon)
dragonSize = dragon.nbytes
#print(dragon)
print('array of length', len(dragon),'created in', round(time.clock()-t0, 5), 'seconds')
#quit()
root = Tk()
root.title('Dragon Curve')
size = 2**2
#canvas = Canvas(root, width = size, height = size, bg = 'black')
#canvas.pack()
t1 = time.clock()
image = Image.new('RGB', (size, size), 'black')
draw = ImageDraw.Draw(image, 'RGB')
imageSize = sys.getsizeof(image)
drawSize = sys.getsizeof(draw)
print('image created in', round(time.clock()-t1, 5), 'seconds')
#fourDragons(dragon, draw, size)
#fourDragonsFast(dragon, draw, size)
standardDragon(dragon, draw, size)
#pathSize = standardDragonFaster(dragon, draw, size)
#standardDragonTest(dragon, size)

t1 = time.clock()
image.save('dragon4/dragon2' + str(n) + '-' + str(size) + '.png')
print('image saved in', round(time.clock()-t1, 5), 'seconds')
print('total execution time:', round(time.clock()-t0, 5), 'seconds')
print('created', 3*len(dragon)/round(time.clock()-t0, 5), 'elements per second')
#print((dragonSize + pathSize)//1024**2)
#root.mainloop()