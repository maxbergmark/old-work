import math
import pygame
from pygame.locals import *

class Scan:

	def __init__(self, root, ship, pos):
		self.root = root
		self.ship = ship
		self.pos = [int(pos[0]), int(pos[1])]
		self.startFrame = self.root.frame
		self.scanSize = 150
		

	def checkShips(self, dmg):
		for bot in self.root.bots[:]:
			if self.getDistance(bot):
				bot.health = max(bot.health - dmg, 0)
				if bot.health == 0:
					bot.destroyShip()
					self.root.score += 1
					

	def getDistance(self, bot):
		return ((bot.pos[0]-self.pos[0])**2 + (bot.pos[1]-self.pos[1])**2)**.5 < self.scanSize

	def drawScan(self):
		pass
		pygame.draw.circle(self.root.bg, (255, 255, 255), self.pos, self.scanSize, 1)
#		self.scanObject = self.root.canvas.create_oval((self.pos[0]-self.scanSize)/self.root.scale, (self.pos[1]+self.scanSize)/self.root.scale, (self.pos[0]+self.scanSize)/self.root.scale, (self.pos[1]-self.scanSize)/self.root.scale, outline = 'white')

	def deleteScan(self):
		if self.root.frame >= self.startFrame + 3:
			self.ship.scans.remove(self)