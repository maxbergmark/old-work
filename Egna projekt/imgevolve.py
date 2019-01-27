import random
import math
import time
from PIL import Image, ImageDraw, ImageTk
import sys
import itertools
import copy
import tkinter

def fitness(key, test):
	pairs = zip(key.getdata(), test.getdata())
	return sum(abs(c1-c2) for p1, p2 in pairs for c1, c2 in zip(p1, p2))


class ImEvolve:

	def __init__(self, key, starttime):
		self.key = key
		self.starttime = starttime
		self.num = 500
		self.gen = 0
		self.changes = 0
		self.maxfit = 3*255*self.key.size[0]*self.key.size[1]
		self.fitvalue = self.maxfit
		print(self.maxfit)
		self.tk = tkinter.Tk()
		self.canvas = tkinter.Canvas(self.tk, width = self.key.size[0], height = self.key.size[1])
		self.canvas.pack()
		self.polygons = [self.placePolygon((self.key.size[0]*random.random(), self.key.size[1]*random.random())) for _ in range(self.num)]
		self.colors = [tuple([int(255*random.random()) for _ in range(4)]) for _ in range(self.num)]
		self.image = Image.new('RGB', (self.key.size[0], self.key.size[1]), 'white')
		self.draw = ImageDraw.Draw(self.image, 'RGBA')
		self.evolve()

	def placePolygon(self, center):
		return [(center[0] + 20*random.random(), center[1] + 20*random.random()) for _ in range(3)]

	def drawImage(self, image, polygons, colors):
		for polygon, color in zip(polygons, colors):
			image.polygon(polygon, color)

	def clearimage(self, image):
		image.rectangle([(0, 0), (self.key.size[0], self.key.size[1])], (255, 255, 255))

	def mutatepolygon(self, polygon, rate):
		return [(polygon[i][0] + int(random.random()< rate)*(-20 + 40*random.random()), polygon[i][1] + int(random.random()<rate)*(-20 + 40*random.random())) for i in range(3)]

	def mutatecolor(self, color, rate):
		return tuple([int(min(255, max(0, color[i] + (random.random()<rate)*(-20 + 40*random.random())))) for i in range(4)])

	def generation(self):
		self.gen += 1
		self.clearimage(self.draw)
		self.drawImage(self.draw, self.polygons, self.colors)
		#self.canvas.delete('all')
		self.drawing = ImageTk.PhotoImage(self.image)
		self.canvas.create_image((0, 0), image = self.drawing, anchor = tkinter.NW)
#		self.canvas.update_idletasks()
		temppolygons = [self.mutatepolygon(polygon, .2*self.fitvalue/self.maxfit) for polygon in self.polygons]
		tempcolors = [self.mutatecolor(color, .2*self.fitvalue/self.maxfit) for color in self.colors]
#		temppolygons = copy.deepcopy(self.polygons)
#		tempcolors = copy.deepcopy(self.colors)
		tempimage = Image.new('RGB', (self.key.size[0], self.key.size[1]), 'white')
		tempdraw = ImageDraw.Draw(tempimage, 'RGBA')
		self.drawImage(tempdraw, temppolygons, tempcolors)
		tempfit = fitness(self.key, tempimage)
		#imfit = fitness(self.key, self.image)
		if tempfit < self.fitvalue:
			print(tempfit, self.fitvalue, self.fitvalue-tempfit, 'better fit')
			self.polygons = copy.deepcopy(temppolygons)
			self.colors = copy.deepcopy(tempcolors)
			self.fitvalue = tempfit
			self.changes += 1
			tempimage.save('test/imageevolve' + str(self.changes) + '.png')

	def evolve(self):

		print(self.gen, time.clock() - self.starttime)
		self.generation()
		self.tk.after(10, self.evolve)

imagekey = Image.open('monalisa.jpg').convert('RGB')
evolver = ImEvolve(imagekey, time.clock())
#evolver.drawImage()
evolver.tk.mainloop()
#evolver.evolve()

evolver.image.save('testimage.png')