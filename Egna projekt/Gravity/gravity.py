from pygame.locals import *
import pygame
import time



class Player:

	def __init__(self, x, y, root):
		self.x = x
		self.y = y
		self.xvel = 0
		self.yvel = 0
		self.root = root
		self.space = False

	def nextFrame(self, keys):
		if keys[pygame.K_LEFT]:
			self.xvel = -(5-self.xvel)/2
		elif keys[pygame.K_RIGHT]:
			self.xvel = (5+self.xvel)/2
		else:
			
			if abs(self.xvel) < .5:
				self.xvel = 0
			else:
				self.xvel -= .3*self.xvel/abs(self.xvel)

		if keys[pygame.K_SPACE]:
			if not self.space:
				self.root.gravity *= -1
				self.space = True
		else:
			self.space = False

		self.yvel += self.root.gravity

		self.x += self.xvel
		self.y += self.yvel
		if self.y > 600 and self.yvel > 0:
			#self.y = 600
			self.yvel *= -.3
			if abs(self.yvel) < .5:
				self.y = 600
				self.yvel = 0

		#print(self.x, self.y, self.xvel, self.yvel)

	def drawPlayer(self):
		pygame.draw.circle(self.root.bg, (255, 255, 255), [int(self.x), int(self.y)], 10, 10)
		




class Game:

	def __init__(self):
		self.width = 1080
		self.height = 640
		self.frame = 0
		self.player = Player(100, 100, self)
		self.gravity = .1

	def nextFrame(self):
		keys = pygame.key.get_pressed()
		self.player.nextFrame(keys)

	def initGraphic(self):
		self.screen = pygame.display.set_mode([self.width, self.height])
		self.bg = pygame.Surface(self.screen.get_size())
		self.bg = self.bg.convert()
		self.bg.fill((0, 0, 0))

		#self.root = Tk()
		#self.root.title('Gravity')
		#self.canvas = Canvas(self.root, width = self.width, height = self.height, bg = 'black')
		#self.canvas.pack()

	def updateFrame(self):
		while True:
			for event in pygame.event.get():
				if event.type == QUIT:
					return
			keys = pygame.key.get_pressed()
			self.player.nextFrame(keys)
			
			#play = pygame.draw.circle(self.bg, (255, 255, 255), [self.player.x, self.player.y], 10, 10)
			#pygame.draw.rect(self.bg, (255, 255, 255), (100,100,50,200), 0)

			self.frame += 1
			time.sleep(.01)
			
			self.bg.fill((0, 0, 0))
			self.player.drawPlayer()
			font = pygame.font.Font(None, 15)
			text = font.render(str(self.frame), 1, (255, 255, 255))
			textpos = text.get_rect()
			textpos.right = 1078
			textpos.bottom = 640
			self.bg.blit(text, textpos)
			
			self.screen.blit(self.bg, (0, 0))
			pygame.display.flip()


		#player.drawMaster()

		#self.canvas.after(1, self.updateFrame)



def backendRun(game):
	while True:
		game.nextFrame()

def graphicStart(game):
	game.initGraphic()
	game.updateFrame()

game = Game()
pygame.init()


graphicStart(game)
