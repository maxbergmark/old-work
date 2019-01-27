import threading
import socket
import select
import random
import math
import time
from ship import Ship
from color import Color
import pygame
from pygame.locals import *


class Game:

	def __init__(self):

		self.player = None
		self.bots = []
		self.frame = 0
		self.score = 0

		self.width = 640*1
		self.height = 640*1
		self.scale = 1
		self.framerate = 100
		self.timer = time.clock()
		self.gtimer = time.clock()
		self.ready = threading.Event()

	def initGraphic2(self):

		self.root = Tk()
		self.root.title('Survive')
		self.canvas = Canvas(self.root, width = self.width/self.scale, height = self.height/self.scale, bg = 'black')
		self.canvas.pack()

	def initGraphic(self):
		pygame.init()
		self.screen = pygame.display.set_mode([self.width, self.height])
		self.bg = pygame.Surface(self.screen.get_size())
		self.bg = self.bg.convert()
		self.bg.fill((0, 0, 0))


	def updateFrame2(self):

		self.canvas.delete("all")
		self.scoreBoard = self.canvas.create_text(5, 5, text = self.scoreString, anchor = 'nw', fill = 'white')
		self.frameBoard = self.canvas.create_text(5, self.height/self.scale, text = str(self.frame), anchor = 'sw', fill = 'white')


		if self.player.active:
			self.player.drawMaster()
		for bot in self.bots:
			bot.drawMaster()

		self.canvas.after(1, self.updateFrame2)

	def gameOver(self):
		time.sleep(1)
		while True:
			for event in pygame.event.get():
				if event.type == QUIT:
					return

			self.bg.fill((0, 0, 0))

			font = pygame.font.Font(None, 150)
			scoretext = font.render(str(self.score), 1, (255, 255, 255))
			scoretextpos = scoretext.get_rect()
			scoretextpos.centerx = self.width/2
			scoretextpos.centery = self.height/2
			self.bg.blit(scoretext, scoretextpos)

			self.screen.blit(self.bg, (0, 0))
			pygame.display.flip()



	def updateFrame(self):
		while True:
			self.ready.clear()
			self.gtimer = time.clock()
			for event in pygame.event.get():
				if event.type == QUIT:
					return
			if self.player.alive == False:
				self.gameOver()
				return
			keys = pygame.key.get_pressed()
			self.player.moveShip(keys)
			
			self.bg.fill((0, 0, 0))
			self.player.drawMaster()

			for bot in self.bots:
				bot.drawMaster()
			
			font = pygame.font.Font(None, 15)
			frametext = font.render(str(self.frame), 1, (255, 255, 255))
			frametextpos = frametext.get_rect()
			frametextpos.right = 640
			frametextpos.bottom = 640
			self.bg.blit(frametext, frametextpos)

			scoretext = font.render(str(self.score), 1, (255, 255, 255))
			scoretextpos = scoretext.get_rect()
			scoretextpos.left = 0
			scoretextpos.bottom = 640
			self.bg.blit(scoretext, scoretextpos)
			
			self.screen.blit(self.bg, (0, 0))
			pygame.display.flip()
			timedif = time.clock() - self.gtimer
			time.sleep(max(0, 1/self.framerate - timedif))
			self.ready.set()


	def addPlayer(self):
		self.player = Ship(self, (0, 255, 0))

	def addBot(self, health, speed):
		self.bots.append(Ship(self, (max(255*math.sin(self.frame/10000), 0), max(255*math.sin(self.frame/10000+6.28/3), 0), max(255*math.sin(self.frame/10000-6.28/3), 0)), True, health, speed))



	def newFrame(self):
		self.timer = time.clock()
		if self.frame - self.player.lastHitFrame > 50:
			self.player.health = min(self.player.health+1, self.player.maxhealth)
			
		self.player.energy = min(self.player.energy+1, self.player.maxenergy)
		

		#if self.frame % (50) == 0:
			#self.player.setAngle(360*random.random())
			#self.player.scanShips()
		if self.frame % 1 == 0 and len(self.bots) < 100:
			self.addBot(100 + 10*self.frame*(random.random() < .00) + self.frame//40, (.5+.5*random.random())*(1+self.frame/10000))
			
		
			
		for bot in self.bots:
			
			#if self.frame - bot.lastHitFrame > 200:
				#bot.health = min(bot.health+1, bot.maxhealth)
			if self.player.alive:
				
				#if bot.calcDistance(500):
				bot.calcAngle()
				bot.moveShip2()
			for scan in self.player.scans[:]:
				
				if self.frame - scan.startFrame > 2:
					self.player.scans.remove(scan)
			if bot.calcDistance(20):
				
				self.player.health = max(self.player.health-1, 0)
				if self.player.health == 0 and self.player.alive:
					self.player.alive = False
					self.player.deathFrame = self.frame

				
		if self.player.alive == False and self.frame > self.player.deathFrame+100:
			self.player.respawnShip()

		self.frame += 1
		timedif = time.clock() - self.timer
		#time.sleep(max(0, 1/self.framerate - timedif))
		self.ready.wait()


	def getPlayerScore(self, player):
		return player.score


def serverStart(game):
	while True:
		game.newFrame()
		if game.player.alive == False:
			return


def graphicStart(game):
	
	game.initGraphic()
	game.updateFrame()
	#game.root.mainloop()

graphic = True
game = Game()

game.addPlayer()
if graphic:
	threading.Thread(target = graphicStart, args = [game]).start()

serverStart(game)
