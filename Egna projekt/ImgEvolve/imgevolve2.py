import random
import math
import time
from PIL import Image, ImageDraw, ImageTk
import sys
import itertools
import copy
import pickle
import os.path

def fitness(key, test):
	pairs = zip(key.getdata(), test.getdata())
	return sum(abs(c1-c2) for p1, p2 in pairs for c1, c2 in zip(p1, p2))


class ImEvolve:

	def __init__(self, key, starttime, test):
		self.key = key
		self.test = test
		self.change = ''
		self.num = 1000
		if os.path.isfile(test + '/info.pickle'):
			with open(self.test + '/info.pickle', 'rb') as b:
				info = pickle.load(b)
				self.starttime = info['starttime']
				self.gen = info['gen']
				self.checks = info['checks']
				self.changes = info['changes']
				self.maxfit = info['maxfit']
				self.fitvalue = info['fitvalue']
				self.bw = info['bw']

			with open(self.test + '/polygons.pickle', 'rb') as b:
				self.polygons = pickle.load(b)
			with open(self.test + '/colors.pickle', 'rb') as b:
				self.colors = pickle.load(b)
		else:
			self.starttime = starttime
			self.gen = 0
			self.checks = 0
			self.changes = 0
			self.maxfit = 3*255*self.key.size[0]*self.key.size[1]
			self.fitvalue = self.maxfit
			self.bw = True
			
			self.polygons = [self.placePolygon((self.key.size[0]*random.random(), self.key.size[1]*random.random())) for _ in range(self.num)]
			if self.bw:
				self.colors = [tuple(3*[random.randint(0, 255)] + [random.randint(0, 255)]) for _ in range(self.num)]
			else:
				self.colors = [tuple([int(255*random.random()) for _ in range(4)]) for _ in range(self.num)]
			print(self.colors[0])
		

		self.image = Image.new('RGB', (self.key.size[0], self.key.size[1]), 'white')
		self.draw = ImageDraw.Draw(self.image, 'RGBA')

	def placePolygon(self, center):
		return [(center[0] + 20*random.random(), center[1] + 20*random.random()) for _ in range(3)]

	def drawImage(self, image, polygons, colors):
		for polygon, color in zip(polygons, colors):
			image.polygon(polygon, color)

	def clearimage(self, image):
		image.rectangle([(0, 0), (self.key.size[0], self.key.size[1])], (255, 255, 255))

	def mutatepolygon(self, polygon):
		randvertice = random.randint(0, 2)
		return [(polygon[i][0] + int(i==randvertice)+(-20 + 40*random.random()), polygon[i][1] + int(i==randvertice)+(-20 + 40*random.random())) for i in range(3)]

	def mutatecolor(self, color, bw = False):
		randcolor = random.randint(0, 3)
		if bw:
			return tuple(3*[random.randint(0, 255)] + [random.randint(0, 255)])
		return tuple([random.randint(0, 255) if i != randcolor else color[i] for i in range(4)])


	def generation(self):
		self.gen += 1
		self.clearimage(self.draw)
		self.drawImage(self.draw, self.polygons, self.colors)
		for i in range(self.num):
			#print(i)
			temppolygons = copy.deepcopy(self.polygons)
			tempcolors = copy.deepcopy(self.colors)
			if random.random() < .5:
				temppolygons[i] = self.mutatepolygon(self.polygons[i])
				self.change = 'polygon'
			else:
				tempcolors[i] = self.mutatecolor(self.colors[i], self.bw)
				self.change = 'color'
			tempimage = Image.new('RGB', (self.key.size[0], self.key.size[1]), 'white')
			tempdraw = ImageDraw.Draw(tempimage, 'RGBA')
			self.drawImage(tempdraw, temppolygons, tempcolors)
			tempfit = fitness(self.key, tempimage)
			self.checks += 1
			#print(tempfit, self.fitvalue)
			if tempfit < self.fitvalue:
				print(self.changes, self.checks, tempfit, self.fitvalue-tempfit, round(100*(self.maxfit-tempfit)/self.maxfit, 3), self.change, 'after time:', round(time.clock() - self.starttime, 2))
				self.polygons = copy.deepcopy(temppolygons)
				self.colors = copy.deepcopy(tempcolors)
				with open(self.test + '/polygons.pickle', 'wb') as b:
					pickle.dump(self.polygons, b)
				with open(self.test + '/colors.pickle', 'wb') as b:
					pickle.dump(self.colors, b)
				with open(self.test + '/info.pickle', 'wb') as b:
					pickle.dump({'bw': self.bw, 'gen': self.gen, 'checks': self.checks, 'changes': self.changes, 'fitvalue': self.fitvalue, 'maxfit': self.maxfit, 'starttime': self.starttime}, b)

				self.fitvalue = tempfit
				self.changes += 1
				if self.changes % 100 == 0:
					print('saving image', self.changes // 100)
					self.scale((1000, 1000)).save(self.test + '/imageevolve' + str(self.changes) + '.png')


	def scale(self, dim):
		tempimage = Image.new('RGB', dim, 'white')
		tempdraw = ImageDraw.Draw(tempimage, 'RGBA')
		for polygon, color in zip(self.polygons, self.colors):
			tempdraw.polygon([(polygon[i][0]*dim[0]/self.key.size[0], polygon[i][1]*dim[1]/self.key.size[1]) for i in range(3)], color)
		return tempimage


	def evolve(self):
		while True:
			print(self.gen, time.clock() - self.starttime)
			self.generation()




imagekey = Image.open('gzaxel.jpg').convert('RGB')
evolver = ImEvolve(imagekey, time.clock(), 'test18')
#evolver.drawImage()
evolver.evolve()

#evolver.image.save('testimage.png')