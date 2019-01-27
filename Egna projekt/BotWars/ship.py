import math
import uuid
import random
from bullet import Bullet
from scan import Scan

def toDegrees(rad):
	return 180*rad/math.pi

def toRadians(deg):
	return math.pi*deg/180

def rotate(coordinate, angle):
	return [coordinate[0]*math.cos(toRadians(angle)) - coordinate[1]*math.sin(toRadians(angle)), coordinate[0]*math.sin(toRadians(angle)) + coordinate[1]*math.cos(toRadians(angle))]

class Ship:

	def __init__(self, root, playerID, playerColor, playerUUID = '', active = True):
		self.root = root
		self.playerID = playerID
		self.uuid = playerUUID
		self.playerColor = playerColor
		self.speed = 4
		self.angle = 0
		self.score = 0
		self.kills = 0
		self.fired = 0
		self.hit = 0
		self.alive = True
		self.active = active
		self.pos = [random.randint(0,self.root.width), random.randint(0,self.root.height)]
		self.size = 2
		self.bullets = []
		self.scans = []
		self.health = 200
		self.energy = 100
		self.boost = False
		self.shield = False
		self.boostFrame = 0
		self.lastHitFrame = 0
		self.deathFrame = 0


	def drawShip(self):
		corners = [rotate([self.size*-5, self.size*3], self.angle), rotate([self.size*5, self.size*0], self.angle), rotate([self.size*-5, self.size*-3], self.angle)]
		self.corners = [None for i in range(len(corners))]
		for corner in range(len(corners)):
			self.corners[corner] = self.root.canvas.create_line((self.pos[0] + corners[corner-1][0])/self.root.scale, (self.pos[1] + corners[corner-1][1])/self.root.scale, (self.pos[0] + corners[corner][0])/self.root.scale, (self.pos[1] + corners[corner][1])/self.root.scale, fill = self.playerColor, width = self.size/2)

	def drawBars(self):
		self.healthBar = [self.root.canvas.create_line((self.pos[0]-self.size*10)/self.root.scale, (self.pos[1]+self.size*8)/self.root.scale, (self.pos[0]+10*self.size*(-1+self.health/100))/self.root.scale, (self.pos[1]+self.size*8)/self.root.scale, fill = 'white', width = 2), self.root.canvas.create_line((self.pos[0]+10*self.size*(-1+self.health/100))/self.root.scale, (self.pos[1]+self.size*8)/self.root.scale, (self.pos[0]+self.size*10)/self.root.scale, (self.pos[1]+self.size*8)/self.root.scale, fill = 'red', width = 2)]
		self.energyBar = [self.root.canvas.create_line((self.pos[0]-self.size*10)/self.root.scale, (self.pos[1]+self.size*10)/self.root.scale, (self.pos[0]+10*self.size*(-1+self.energy/50))/self.root.scale, (self.pos[1]+self.size*10)/self.root.scale, fill = 'white', width = 2), self.root.canvas.create_line((self.pos[0]+10*self.size*(-1+self.energy/50))/self.root.scale, (self.pos[1]+self.size*10)/self.root.scale, (self.pos[0]+self.size*10)/self.root.scale, (self.pos[1]+self.size*10)/self.root.scale, fill = 'blue', width = 2)]

	def drawShield(self):
		self.shieldObject = self.root.canvas.create_oval((self.pos[0]-7*self.size)/self.root.scale, (self.pos[1]+7*self.size)/self.root.scale, (self.pos[0]+7*self.size)/self.root.scale, (self.pos[1]-7*self.size)/self.root.scale, outline = '')
		if self.shield:
			self.root.canvas.itemconfig(self.shieldObject, outline = 'green')

	def drawName(self):
		self.nameObject = self.root.canvas.create_text((self.pos[0])/self.root.scale, (self.pos[1]-6*self.size)/self.root.scale, text = self.playerID, fill = 'white', font = ('Courier', 8))

	def changeName(self, name):
		self.playerID = name

	def changeColor(self, color):
		self.playerColor = color


	def moveShip(self):
		if self.alive:
			self.pos = [(self.pos[0]+self.speed*math.cos(toRadians(self.angle))) % self.root.width, (self.pos[1]+self.speed*math.sin(toRadians(self.angle))) % self.root.height]
		for bullet in self.bullets:
			bullet.moveBullet()
			bullet.checkCollision()
		for scan in self.scans:
			scan.deleteScan()
		self.checkShield()
		self.endBoost()



	def drawMaster(self):
		for bullet in self.bullets:
			bullet.drawBullet()
		for scan in self.scans:
			scan.drawScan()
		if self.alive:
			self.drawShip()
			self.drawShield()
			self.drawBars()
			self.drawName()




	def setAngle(self, angle):
		if self.energy >= 10 and self.alive:
			self.angle = angle
			self.energy -= 10

	def activateShield(self):
		if self.energy >= 2 and self.alive:
			self.shield = True
			

	def checkShield(self):
		if self.energy < 2:
			self.shield = False
			self.disableShield()
		if self.shield:
			self.energy -= 2

	def disableShield(self):
		self.shield = False


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

	def fireBullet(self):
		if self.energy >= 20 and self.alive:
			self.fired += 1
			self.bullets.append(Bullet(self.root, self, self.angle, self.pos, self.playerID))
			self.energy -= 20



	def scanShips(self):
		if self.energy >= 5 and self.alive:
			self.scans.append(Scan(self.root, self, self.pos, self.playerID))
			self.energy -= 5
			return {'status': True, 'result': self.scans[-1].checkShips()}
		return {'status': False, 'result': []}

	def destroyShip(self):
		self.alive = False
		self.deathFrame = self.root.frame

	def respawnShip(self):
		self.pos = [random.randint(0, self.root.width), random.randint(0, self.root.height)]
		self.health = 200
		self.alive = True
		self.score = max(0, self.score-200)