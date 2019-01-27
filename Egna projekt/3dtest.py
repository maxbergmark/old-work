from tkinter import *
import math
import win32api
import win32gui
import random


def length(v1):
	return (sum(i**2 for i in v1))**.5

def sProd(v1, v2):
	return sum([v1[i]*v2[i] for i in range(3)])

def mult(v1, k):
	return [i*k for i in v1]

def add(v1, v2):
	return [v1[i]+v2[i] for i in range(3)]

def subt(v1, v2):
	return [v1[i]-v2[i] for i in range(3)]

def cross(v1, v2):
	return [v1[1]*v2[2]-v1[2]*v2[1], v1[2]*v2[0]-v1[0]*v2[2], v1[0]*v2[1]-v1[1]*v2[0]]

def proj1(v1, p1):
	return mult(subt(p1, v1),1/length(subt(p1, v1)))


def proj2(nv, p1):
	if sProd(nv, p1) == 0:
		return p1
	return subt(p1, mult(nv, sProd(nv, p1)))


def display(p1, view):
	#print(cartToSph(subt(view, p1)))
	return subt([320, 320, 320], mult(anglesub(cartToSph(p1), cartToSph(view)), 1280))

def displaycheck(p1, view):
	return (abs(anglesub(cartToSph(view), cartToSph(p1))[1]) < 2 and abs(anglesub(cartToSph(view), cartToSph(p1))[2]) < 2)


def cartToSph(v1):
	return [length(v1), math.acos(v1[2]/length(v1)), math.atan2(v1[1], v1[0])]

def sphToCart(v1):
	return [v1[0]*math.sin(v1[1])*math.cos(v1[2]), v1[0]*math.sin(v1[1])*math.sin(v1[2]), v1[0]*math.cos(v1[1])]

def anglesub(v1, v2):
	ret = [0, 0, 0]
	ret[1] = v2[1]-v1[1]
	ret[2] = v2[2]-v1[2] + math.pi*int(v2[2] < v1[2]) - math.pi*int(v2[2] > v1[2])
	return ret




class Vertice:

	def __init__(self, points, color = None):

		self.points = points
		if color:
			self.color = color
		else:
			self.color = '#' + str(hex(random.randint(10000000, 16000000)))[2:]












class Game:

	def __init__(self, root, vertices):
		self.root = root
		self.canvas = Canvas(self.root, width = 640, height = 640, bg = 'black')
		self.canvas.pack()
		self.root.bind('<Button-1>', self.test)
		self.root.bind('<KeyPress>', self.KeyPress)
		self.mouse = win32api.GetCursorPos()
		self.state = False
		self.vertices = vertices
		self.pos = [2, 0, 0]
		self.view = [1, 0, 0]
		self.frame = 0
		self.drawFrame()

	def test(self, event):
		self.state = not self.state

	def mouseMove(self):
		temp = win32api.GetCursorPos()
		diff = (temp[0]-self.mouse[0], temp[1]-self.mouse[1])
		if self.state:
			self.pos[0] += diff[1]/100
			self.pos[1] += diff[0]/100
		else:
			tempview = cartToSph(self.view)
			tempview[2] += diff[0]/100
			tempview[1] += diff[1]/100
			self.view = sphToCart(tempview)
		self.mouse = temp

	def KeyPress(self, event):
		vel = 5
		if event.char == 'w':
			self.pos = subt(self.pos, [vel*self.view[0], vel*self.view[1], 0])
		elif event.char == 'a':
			self.pos = subt(self.pos, [vel*self.view[1], vel*-self.view[0], 0])
		elif event.char == 's':
			self.pos = add(self.pos, [vel*self.view[0], vel*self.view[1], 0])
		elif event.char == 'd':
			self.pos = add(self.pos, [vel*self.view[1], vel*-self.view[0], 0])
		elif event.char == 'e':
			self.pos = add(self.pos, [0, 0, vel*1])
		elif event.char == 'q':
			self.pos = add(self.pos, [0, 0, vel*-1])

	def drawFrame(self):
		self.canvas.delete('all')
		self.frame += 1
		for vertice in self.vertices:
			points = [proj1(self.pos, v1) for v1 in vertice.points]
			points2 = [proj2(self.view, p1) for p1 in points]

			tempcheck = sum([1 if displaycheck(p1, self.view) else 0 for p1 in points]) == 3
			points = [display(i, self.view) for i in points]
			if tempcheck: 
				self.canvas.create_polygon(points[0][2], points[0][1], points[1][2], points[1][1], points[2][2], points[2][1], fill = vertice.color)
		self.mouseMove()
		self.root.after(1, self.drawFrame)




root = Tk()

points = []
for i in range(-5, 5):
	for j in range(-5, 5):
		points.append([[10*i, 10*j, 0], [10*(i+1), 10*j, 0], [10*i, 10*(j+1), 0]])

for i in range(-5, 5):
	for j in range(-5, 5):
		points.append([[50, 10*i, 50+10*j], [50, 10*(i+1), 50+10*j], [50, 10*i, 50+10*(j+1)]])

for i in range(-5, 5):
	for j in range(-5, 5):
		points.append([[-50, 10*i, 50+10*j], [-50, 10*(i+1), 50+10*j], [-50, 10*i, 50+10*(j+1)]])


for i in range(-5, 5):
	for j in range(-5, 5):
		points.append([[10*i, -50, 50+10*j], [10*(i+1), -50, 50+10*j], [10*i, -50, 50+10*(j+1)]])

for i in range(-5, 5):
	for j in range(-5, 5):
		points.append([[10*i, 50, 50+10*j], [10*(i+1), 50, 50+10*j], [10*i, 50, 50+10*(j+1)]])

for i in range(-5, 5):
	for j in range(-5, 5):
		points.append([[10*i, 10*j, 100], [10*(i+1), 10*j, 100], [10*i, 10*(j+1), 100]])

'''

for i in range(-10, 10):
	for j in range(-10, 10):
		points.append([[10*i, 10*j, 0], [10*(i+1), 10*j, 0], [10*i, 10*(j+1), 0]])

for i in range(-10, 10):
	for j in range(-10, 10):
		points.append([[10*i, 10*j, 0], [10*(i+1), 10*j, 0], [10*i, 10*(j+1), 0]])

for i in range(10):
	points.append([[20*math.cos(math.pi*2*i/10), 20*math.sin(math.pi*2*i/10), 0], [20*math.cos(math.pi*2*i/10), 20*math.sin(math.pi*2*i/10), 100], [20*math.cos(math.pi*2*(i+1)/10), 20*math.sin(math.pi*2*(i+1)/10), 0]])
	points.append([[20*math.cos(math.pi*2*i/10), 20*math.sin(math.pi*2*i/10), 100], [20*math.cos(math.pi*2*(i+1)/10), 20*math.sin(math.pi*2*(i+1)/10), 100], [20*math.cos(math.pi*2*(i+1)/10), 20*math.sin(math.pi*2*(i+1)/10), 0]])
'''
vertices = [Vertice(point) for point in points]

game = Game(root, vertices)
game.root.mainloop()

