import math
import random
from scan import Scan
import pygame
from pygame.locals import *

def toDegrees(rad):
	return 180*rad/math.pi

def toRadians(deg):
	return math.pi*deg/180

def rotate(coordinate, angle):
	return [coordinate[0]*math.cos(toRadians(angle)) - coordinate[1]*math.sin(toRadians(angle)), coordinate[0]*math.sin(toRadians(angle)) + coordinate[1]*math.cos(toRadians(angle))]

class Ship:

	def __init__(self, root, color = (255, 255, 255), bot = False, health = 1000, speed = 2):
		self.root = root
		self.bot = bot
		self.color = color
		self.speed = speed
		self.standardSpeed = speed
		self.angle = 0
		self.score = 0
		self.kills = 0
		self.fired = 0
		self.hit = 0
		self.alive = True
		if self.bot == False:
			self.pos = [random.randint(0,self.root.width), random.randint(0,self.root.height)]
		else:
			self.pos = self.calcSpawn(random.random())
		self.vel = [0, 0]
		self.size = 2
		self.scans = []
		self.health = health
		self.maxhealth = health
		self.energy = 1000
		self.maxenergy = self.energy
		self.boost = False
		self.shield = False
		self.death = False
		self.boostFrame = 0
		self.lastHitFrame = 0
		self.deathFrame = 0
		self.space = True


	def drawShip(self):
		corners = [rotate([self.size*-5, self.size*3], self.angle), rotate([self.size*5, self.size*0], self.angle), rotate([self.size*-5, self.size*-3], self.angle)]
		self.corners = [None for i in range(len(corners))]
		for corner in range(len(corners)):
			self.corners[corner] = ((self.pos[0] + corners[corner][0])/self.root.scale, (self.pos[1] + corners[corner][1])/self.root.scale)
#			self.corners[corner] = self.root.canvas.create_line((self.pos[0] + corners[corner-1][0])/self.root.scale, (self.pos[1] + corners[corner-1][1])/self.root.scale, (self.pos[0] + corners[corner][0])/self.root.scale, (self.pos[1] + corners[corner][1])/self.root.scale, fill = self.playerColor, width = self.size/2)
			#pygame.draw.line(self.root.bg, self.color, ((int(self.pos[0] + corners[corner-1][0])/self.root.scale), int((self.pos[1] + corners[corner-1][1])/self.root.scale)), (int((self.pos[0] + corners[corner][0])/self.root.scale), int((self.pos[1] + corners[corner][1])/self.root.scale)), int(self.size/2))
		pygame.draw.aalines(self.root.bg, self.color, True, self.corners, int(self.size/2))

	def drawBars(self):
		pygame.draw.line(self.root.bg, (255, 255, 255), ((self.pos[0]-self.size*10)/self.root.scale, (self.pos[1]+self.size*10)/self.root.scale), ((self.pos[0]+10*self.size*(-1+self.health*2/self.maxhealth))/self.root.scale, (self.pos[1]+self.size*10)/self.root.scale), 2)
		pygame.draw.line(self.root.bg, (255, 0, 0), ((self.pos[0]+10*self.size*(-1+self.health*2/self.maxhealth))/self.root.scale, (self.pos[1]+self.size*10)/self.root.scale), ((self.pos[0]+self.size*10)/self.root.scale, (self.pos[1]+self.size*10)/self.root.scale), 2)
		if self.bot == False:
			pygame.draw.line(self.root.bg, (255, 255, 255), ((self.pos[0]-self.size*10)/self.root.scale, (self.pos[1]+self.size*12)/self.root.scale), ((self.pos[0]+10*self.size*(-1+self.energy*2/self.maxenergy))/self.root.scale, (self.pos[1]+self.size*12)/self.root.scale), 2)
			pygame.draw.line(self.root.bg, (0, 0, 255), ((self.pos[0]+10*self.size*(-1+self.energy*2/self.maxenergy))/self.root.scale, (self.pos[1]+self.size*12)/self.root.scale), ((self.pos[0]+self.size*10)/self.root.scale, (self.pos[1]+self.size*12)/self.root.scale), 2)
		
	def calcSpawn(self, num):
		if num < .25:
			return [-20, random.randint(0, self.root.height)]
		elif num < .5:
			return [random.randint(0, self.root.width), -20]
		elif num < .75:
			return [self.root.width + 20, random.randint(0, self.root.height)]
		else:
			return [random.randint(0, self.root.width), self.root.height + 20]

	def changeColor(self, color):
		self.playerColor = color

	def length(self, temp):
		return (sum(i**2 for i in temp))**.5

	def moveShip(self, keys = {}):
		temp = [0, 0]
		if keys[pygame.K_LEFT] and not keys[pygame.K_RIGHT]:
			temp[0] -= self.speed
			self.setAngle(180)
		if keys[pygame.K_RIGHT] and not keys[pygame.K_LEFT]:
			temp[0] += self.speed
			self.setAngle(0)
		if keys[pygame.K_UP] and not keys[pygame.K_DOWN]:
			temp[1] -= self.speed
			self.setAngle(-90)
		if keys[pygame.K_DOWN] and not keys[pygame.K_UP]:
			temp[1] += self.speed
			self.setAngle(90)
		if self.length(temp) > 0:
			temp = [i*self.speed/self.length(temp) for i in temp]

		self.pos = [(self.pos[i] + temp[i])%[self.root.width, self.root.height][i] for i in range(2)]

		if keys[pygame.K_LEFT] and keys[pygame.K_UP] and not keys[pygame.K_DOWN] and not keys[pygame.K_RIGHT]:
			self.setAngle(225)
		if keys[pygame.K_LEFT] and keys[pygame.K_DOWN] and not keys[pygame.K_UP] and not keys[pygame.K_RIGHT]:
			self.setAngle(135)
		if keys[pygame.K_RIGHT] and keys[pygame.K_UP] and not keys[pygame.K_DOWN] and not keys[pygame.K_LEFT]:
			self.setAngle(-45)
		if keys[pygame.K_RIGHT] and keys[pygame.K_DOWN] and not keys[pygame.K_UP] and not keys[pygame.K_LEFT]:
			self.setAngle(45)


		if keys[pygame.K_v] and self.space:
			self.space = False
			self.scanShips()
		elif not keys[pygame.K_v]:
			self.space = True
		
		if keys[pygame.K_c]:
			if self.energy >= 5:
				self.energy -= 5
				self.speed = 2*self.standardSpeed
			else:
				self.speed = self.standardSpeed
		else:
			self.speed = self.standardSpeed
		
	def moveShip2(self):
		if self.alive:
			if self.bot == False or True:
				self.pos = [(self.pos[0]+self.speed*math.cos(toRadians(self.angle))) % self.root.width, (self.pos[1]+self.speed*math.sin(toRadians(self.angle))) % self.root.height]
			else:
				self.vel = [(self.vel[0]+self.speed*math.cos(toRadians(self.angle))), (self.vel[1]+self.speed*math.sin(toRadians(self.angle)))]
				self.pos = [self.pos[i]+.01*self.vel[i] for i in range(2)]
		for scan in self.scans:
			scan.deleteScan()
		self.endBoost()



	def drawMaster(self):
		for scan in self.scans:
			scan.drawScan()
		if self.alive:
			self.drawShip()
			self.drawBars()


	def calcAngle(self):
		diff = [self.root.player.pos[i] - self.pos[i] for i in range(2)]
		angle = toDegrees(math.atan2(diff[1], diff[0]))
		self.angle = angle

	def calcDistance(self, dmgDist):
		diff = [self.root.player.pos[i] - self.pos[i] for i in range(2)]
		dist = (diff[0]**2 + diff[1]**2)**.5
		if dist < dmgDist:
			return True
		return False

	def setAngle(self, angle):
		if self.alive:
			self.angle = angle

	def startBoost(self):
		if self.energy >= 25 and self.alive:
			self.speed = 8
			self.boostFrame = self.root.frame
			self.boost = True
			self.energy -= 25

	def endBoost(self):
		if self.root.frame >= self.boostFrame+50 and self.boost:
			self.speed = 4
			self.boost = False


	def scanShips(self):
		if self.energy >= 200 and self.alive:
			self.scans.append(Scan(self.root, self, self.pos))
			self.energy -= 200
			return self.scans[-1].checkShips(200)
		return False

	def destroyShip(self):
		self.alive = False
		self.deathFrame = self.root.frame
		if self.bot:
			self.root.bots.remove(self)

	def respawnShip(self):
		self.pos = [random.randint(0, self.root.width), random.randint(0, self.root.height)]
		self.health = self.maxhealth
		self.energy = self.maxenergy
		self.alive = True