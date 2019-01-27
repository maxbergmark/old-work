import math

def toRadians(deg):
	return math.pi*deg/180

class Bullet:

	def __init__(self, root, ship, angle, pos, playerID):
		
		self.root = root
		self.ship = ship
		self.speed = 10
		self.pos = pos
		self.size = 2
		self.angle = angle
		self.playerID = playerID
		

	def drawBullet(self):
		self.bullet = self.root.canvas.create_rectangle([(self.pos[i]+[-self.size, self.size][i])/self.root.scale for i in range(2)], [(self.pos[i]+[self.size, -self.size][i])/self.root.scale for i in range(2)], fill = 'white')


	def moveBullet(self):
		self.pos = [(self.pos[0]+self.speed*math.cos(toRadians(self.angle))), (self.pos[1]+self.speed*math.sin(toRadians(self.angle)))]
		if self.pos[0] > self.root.width or self.pos[1] > self.root.width or min(self.pos) < 0:
			self.ship.bullets.remove(self)
		

	def checkCollision(self):
		for player in self.root.players:
			if ((self.pos[0]-player.pos[0])**2 + (self.pos[1]-player.pos[1])**2)**.5 < player.size*5 and self.ship.uuid != player.uuid and player.alive:
				if player.shield == False:
					player.lastHitFrame = self.root.frame
					player.health -= 80
					self.ship.score += 20
					self.ship.hit += 1
					#print(self.ship.playerID, self.ship.fired, self.ship.hit/self.ship.fired)
					if player.health <= 0:
						player.destroyShip()
						self.ship.score += 80
						self.ship.kills += 1
						print(player.playerID, 'died a horrible death.', self.ship.playerID, 'is responsible.')#, self.ship.kills, self.ship.score)
				else:
					player.disableShield()
				if self in self.ship.bullets:
					self.ship.bullets.remove(self)