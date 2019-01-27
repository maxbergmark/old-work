import pickle
from tkinter import *


class Game:

	def __init__(self, root):
		self.root = root
		self.size = 16
		self.pos = [4, 4]
		self.direc = 'down'
		self.levelnr = 'level1'
		self.root.bind("<Key>", self.keyPress)		
		
		

	def initGraphic(self):
		self.canvas = Canvas(self.root, width = self.size*10, height = self.size*10)
		self.canvas.pack()
		self.loadLevel('level1')
		self.playerImgs = {'down': 'ashdown.gif', 'up': 'ashup.gif', 'right': 'ashright.gif', 'left': 'ashleft.gif'}
		for key, value in self.playerImgs.items():
			self.playerImgs[key] = PhotoImage(file = value)
		self.playerImg = self.playerImgs[self.direc]
		self.draw()

	def loadLevel(self, levelName):
		self.canvas.delete('all')

		self.canvas.create_text(80, 80, text = 'LOADNING', anchor = CENTER)
		self.canvas.update_idletasks()
		with open(levelName+'.pickle', 'rb') as level:
			self.level = pickle.load(level)
		with open(levelName+'Data.pickle', 'rb') as levelData:
			self.levelData = pickle.load(levelData)
		for key, value in self.levelData.items():
			self.levelData[key] = PhotoImage(file = value)

	def draw(self):
		self.canvas.delete('all')
		for i in range(-5, 15):
			for j in range(-5, 15):
				try:
					if self.pos[0]+i-4 >= 0 and self.pos[1]+j-4 >= 0:
						self.canvas.create_image(self.size*i, self.size*j, image = self.levelData[self.level[self.pos[0]+i-4][self.pos[1]+j-4]['img']], anchor = NW)
				except:
					pass
		self.canvas.create_image(self.size*4, self.size*4-4, image = self.playerImg, anchor = NW)

		#print(self.levelnr)
		self.root.after(10, self.draw)

	def move(self, direction):
		valid = True
		test = [self.pos[i]+direction[i] for i in range(2)]
		if self.level[test[0]][test[1]]['status']:
			self.pos = [self.pos[i]+direction[i] for i in range(2)]
			self.action()
			return True
		return False

	def action(self):
		test = self.level[self.pos[0]][self.pos[1]]['action']
		if test:
			if test['command'] == 'level':
				self.levelnr = test['value']
				self.loadLevel(test['value'])
				self.pos = test['pos']


 


	def keyPress(self, event):
		self.direc = event.keysym.lower()
		if event.keysym == 'Up':
			direction = [0, -1]
		elif event.keysym == 'Down':
			direction = [0, 1]
		elif event.keysym == 'Left':
			direction = [-1, 0]
		elif event.keysym == 'Right':
			direction = [1, 0]
		else:
			return False
		self.playerImg = self.playerImgs[self.direc]
		return self.move(direction)








root = Tk()
game = Game(root)
game.initGraphic()
root.mainloop()