import random
import math
import time
from PIL import Image, ImageDraw, ImageTk
import sys
import itertools
import copy
import pickle
import os.path
from operator import itemgetter

def fitness(key, test, x0, y0, x1, y1, xs):
	vals = [y*xs + x for y in range(y0, y1) for x in range(x0, x1)]
	#pairs = zip(key.getdata(), test)
	temp = key.getdata()
	pairs2 = [(temp[i], test[i]) for i in vals]
	s = 0
	for p1, p2 in pairs2:
		for c1, c2 in zip(p1,p2):
			s += abs(c1-c2)
	return s

class ImEvolve:

	def __init__(self, key, starttime, test):

		self.key = key
		self.test = test
		self.starttime = starttime
		self.gen = 0
		self.checks = 0
		self.changes = 0
		self.maxfit = 3*255*self.key.size[0]*self.key.size[1]
		self.fitvalue = self.maxfit
		self.savedchanges = 0

			
		self.pixels = [tuple([0 for _ in range(3)]) for _ in range(self.key.size[0]*self.key.size[1])]
		

		self.image = Image.new('RGB', (self.key.size[0], self.key.size[1]), 'white')
		self.draw = ImageDraw.Draw(self.image, 'RGB')


	def drawImage(self, image, polygons, colors):
		im = Image.fromarray(self.pixels)



	def mutatecolor(self, x0, y0, x1, y1):
		randcolor = random.randint(0, 3)
		oldfit = fitness(self.key, self.pixels, x0, y0, x1, y1, self.key.size[0])
		minfit = oldfit
		minpixels = self.pixels[:]
		counter = 0
		firstFound = False
		cstep = (x1-x0)*(y1-y0)//1000+1
		for c in range(0, 256, cstep):
			print(c)
			temp = self.pixels[:]
			for x in range(x0, x1):
				for y in range(y0, y1):
					#print('looping:', temp[y*self.key.size[0] + x])
					temp[y*self.key.size[0] + x] = tuple([(temp[y*self.key.size[0] + x][randcolor] + c) % 256 if i == randcolor else temp[y*self.key.size[0] + x][i] for i in range(3)])
			
			tempfit = fitness(self.key, temp, x0, y0, x1, y1, self.key.size[0])
			if (tempfit < minfit):
				minfit = tempfit
				minpixels = temp[:]
				print('new best color found')
				firstFound = True
				counter = 0
			else:
				counter += 1
			if (counter == 3 and firstFound):
				break

		if (minfit < oldfit):
			self.fitvalue -= (oldfit-minfit)
			print('new fit found:', minfit, oldfit)
			self.changes += 1
			self.pixels = minpixels
		


	def generation(self):
		self.gen += 1

		(x0, x1) = sorted((random.randint(0, self.key.size[0]) for _ in range(2)))
		(y0, y1) = sorted((random.randint(0, self.key.size[1]) for _ in range(2)))

		self.mutatecolor(x0, y0, x1, y1)

		#print('pixels:', self.pixels)
		if (self.changes >= self.savedchanges + 10):
			self.scale().save(self.test + '/image' + str(self.changes) + '_' + str(random.randint(0, 1000)) + '.png')
			self.savedchanges = self.changes

	def scale(self):
		im2 = Image.new(self.key.mode, self.key.size)
		im2.putdata(self.pixels)
		#tempimage = Image.fromarray(self.pixels)

		return im2


	def evolve(self):
		while True:
			print(self.gen, time.clock() - self.starttime)
			self.generation()





imagekey = Image.open('cat.jpg').convert('RGB')
evolver = ImEvolve(imagekey, time.clock(), 'test17')
#evolver.drawImage()
evolver.evolve()

#evolver.image.save('testimage.png')