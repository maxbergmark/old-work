import math

class Scan:

	def __init__(self, root, ship, pos, playerID):
		self.root = root
		self.ship = ship
		self.pos = pos
		self.playerID = playerID
		self.startFrame = self.root.frame
		self.scanSize = 150
		

	def checkShips(self):
		self.positions = [player.pos for player in self.root.players if player.alive]
		self.relativePos = [[pos[i]-self.pos[i] for i in range(2)] for pos in self.positions]
		self.inRangeRelative = []
		for pos in self.relativePos:
			if (pos[0]**2+pos[1]**2)**.5 <= self.scanSize and (pos[0]**2+pos[1]**2)**.5 > 0:
				self.inRangeRelative.append(pos)
		return self.inRangeRelative

	def drawScan(self):
		self.scanObject = self.root.canvas.create_oval((self.pos[0]-self.scanSize)/self.root.scale, (self.pos[1]+self.scanSize)/self.root.scale, (self.pos[0]+self.scanSize)/self.root.scale, (self.pos[1]-self.scanSize)/self.root.scale, outline = 'white')

	def deleteScan(self):
		if self.root.frame >= self.startFrame + 3:
			self.ship.scans.remove(self)