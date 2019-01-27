from tkinter import *
import math
import time
import numpy as np

def iterate(dragon):
	return np.concatenate((dragon, np.array([dragon[(len(dragon)-1)//2]]), -1*np.array(dragon)[::-1]))

def fourDragons(dragon, canvas, size):
	t0 = time.clock()
	names = ['first', 'second', 'third', 'fourth']
	for c in range(4):
		pos = [size//2, size//2]
		ang = c*math.pi/2
		d = size*.28/2**(.5*n)*(1+.2*((n+1)%2))
		for i in dragon + [0]:
			pos.extend([pos[-2]+d*math.cos(ang), pos[-1]+d*math.sin(ang)])
			ang += i*math.pi/2
		canvas.create_line(pos, fill = '#%02x%02x%02x' % (0, 255*(1+c)//4, 0))
		print(names[c], 'dragon drawn in', round(time.clock()-t0, 5), 'seconds')
	print('all dragons drawn in', round(time.clock()-t0, 5), 'seconds')

def standardDragon(dragon, canvas, size):
	t0 = time.clock()
	pos = [.55*size//2, 1.15*size//2]
	ang = -math.pi/4*(n+1)
	d = size*.4/2**(.5*n)
	for i in np.append(dragon, np.array([0]), 0):
		pos.extend([pos[-2]+d*math.cos(ang), pos[-1]+d*math.sin(ang)])
		ang += i*math.pi/2
	canvas.create_line(pos, fill = 'green')
	print('dragon drawn in', round(time.clock()-t0, 5), 'seconds')

n = int(input('Iterations: '))

dragon = np.array([1])
#dragon = [1]
t0 = time.clock()
for _ in range(n):
	dragon = iterate(dragon)
#print(dragon)
print('array of length', len(dragon),'completed in', round(time.clock()-t0, 5), 'seconds')
#quit()
root = Tk()
root.title('Dragon Curve')
size = 800
canvas = Canvas(root, width = size, height = size, bg = 'black')
canvas.pack()

#fourDragons(dragon, canvas, size)
standardDragon(dragon, canvas, size)

root.mainloop()