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

			with open(self.test + '/circles.pickle', 'rb') as b:
				self.circles = pickle.load(b)
			with open(self.test + '/colors.pickle', 'rb') as b:
				self.colors = pickle.load(b)
		else:
			self.starttime = starttime
			self.gen = 0
			self.checks = 0
			self.changes = 0
			self.maxfit = 3*255*self.key.size[0]*self.key.size[1]
			self.fitvalue = self.maxfit
			
			self.circles = [self.placeCircles((self.key.size[0]*random.random(), self.key.size[1]*random.random())) for _ in range(self.num)]
			self.colors = [tuple([int(255*random.random()) for _ in range(4)]) for _ in range(self.num)]
		

		self.image = Image.new('RGB', (self.key.size[0], self.key.size[1]), 'white')
		self.draw = ImageDraw.Draw(self.image, 'RGBA')

	def placeCircles(self, center):
		return (center[0], center[1], 10*random.random())

	def drawImage(self, image, circles, colors):
		for circle, color in zip(circles, colors):
			image.ellipse((circle[0]-circle[2], circle[1]-circle[2], circle[0]+circle[2], circle[1]+circle[2]), color)

	def clearimage(self, image):
		image.rectangle([(0, 0), (self.key.size[0], self.key.size[1])], (255, 255, 255))

	def mutatecircle(self, circle):
		randvertice = random.randint(0, 2)
		if randvertice < 2:
			return tuple([circle[i] + int(i==randvertice)*(-20+40*random.random()) for i in range(3)])
		else:
			return (circle[0], circle[1], max(0, circle[2]-20+40*random.random()))

	def mutatecolor(self, color):
		randcolor = random.randint(0, 3)
		return tuple([random.randint(0, 255) if i == randcolor else color[i] for i in range(4)])


	def generation(self):
		self.gen += 1
		self.clearimage(self.draw)
		self.drawImage(self.draw, self.circles, self.colors)
		for i in range(self.num):
			tempcircles = copy.deepcopy(self.circles)
			tempcolors = copy.deepcopy(self.colors)
			if random.random() < .5:
				tempcircles[i] = self.mutatecircle(self.circles[i])
				self.change = 'circle'
			else:
				tempcolors[i] = self.mutatecolor(self.colors[i])
				self.change = 'color'
			tempimage = Image.new('RGB', (self.key.size[0], self.key.size[1]), 'white')
			tempdraw = ImageDraw.Draw(tempimage, 'RGBA')
			self.drawImage(tempdraw, tempcircles, tempcolors)
			tempfit = fitness(self.key, tempimage)
			self.checks += 1

			if tempfit < self.fitvalue:
				print(self.changes, self.checks, tempfit, self.fitvalue-tempfit, round(100*(self.maxfit-tempfit)/self.maxfit, 3), self.change, 'after time:', round(time.clock() - self.starttime, 2))
				self.circles = copy.deepcopy(tempcircles)
				self.colors = copy.deepcopy(tempcolors)
				with open(self.test + '/circles.pickle', 'wb') as b:
					pickle.dump(self.circles, b)
				with open(self.test + '/colors.pickle', 'wb') as b:
					pickle.dump(self.colors, b)
				with open(self.test + '/info.pickle', 'wb') as b:
					pickle.dump({'gen': self.gen, 'checks': self.checks, 'changes': self.changes, 'fitvalue': self.fitvalue, 'maxfit': self.maxfit, 'starttime': self.starttime}, b)

				self.fitvalue = tempfit
				self.changes += 1
				if self.changes % 100 == 0:
					print('saving image', self.changes // 100)
					self.scale((1000, 1500)).save(self.test + '/imageevolve' + str(self.changes) + '.png')


	def scale(self, dim):
		tempimage = Image.new('RGB', dim, 'white')
		tempdraw = ImageDraw.Draw(tempimage, 'RGBA')
		for circle, color in zip(self.circles, self.colors):
			tempdraw.ellipse(((circle[0] - circle[2])*dim[0]/self.key.size[0], (circle[1] - circle[2])*dim[1]/self.key.size[1], (circle[0] + circle[2])*dim[0]/self.key.size[0], (circle[1] + circle[2])*dim[1]/self.key.size[1]), color)
		return tempimage


	def evolve(self):
		while True:
			print(self.gen, time.clock() - self.starttime)
			self.generation()




imagekey = Image.open('wush.jpg').convert('RGB')
evolver = ImEvolve(imagekey, time.clock(), 'test12')
#evolver.drawImage()
evolver.evolve()

#evolver.image.save('testimage.png')