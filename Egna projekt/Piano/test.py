from tkinter import *
import threading
import random
import time


class Game:

	def __init__(self, master):
		
		self.root = master
		self.width = 320
		self.height = 640
		self.canvas = Canvas(self.root, width = self.width, height = self.height)
		self.canvas.pack()
		self.top = 0
		self.level = ''
		self.started = False
		self.draw = True

		self.vel = 0
		self.frame = 0
		self.bottom = 0
		self.press = 0
		self.stack = 4
		self.lose = False
		self.keys = ['A', 'S', 'D', 'F']
		for k in range(1000):
			self.level += random.choice([str(i) for i in range(len(self.keys))])
		self.time = time.clock()
		self.root.bind('<Key>', self.buttonPress)
		for k in range(len(self.keys)):
			self.canvas.create_text((2*k+1)*self.width/(2*len(self.keys)), self.height*7/8, text = self.keys[k], font=("Purisa",24), fill = 'red')
		self.updateFrame()

	def updateFrame(self):
		if self.draw:
			if self.started:
				self.canvas.delete('all')
				self.frame += 1
			self.top = int(.25*self.frame**1.2-self.height/4)
			self.framerate = 1/(time.clock()-self.time)
			self.time = time.clock()
			self.vel = .25*self.framerate/self.height*4*1.2*self.frame**.2
			self.bottom = self.top // (self.height//self.stack)
			if self.press < self.bottom:
				print(self.press, round(self.vel, 2))
				self.lose = True
				self.root.destroy()
			if not self.lose:
				for i in range(self.bottom, self.bottom+self.stack+1):
					self.drawStep(int(self.level[i]), i)
				self.canvas.create_text(self.width/2, 10, text = str(round(self.vel, 2)) + ' per second', font=("Purisa",12), fill = 'red', anchor = CENTER)
			if not self.started:
				self.draw = False
		if not self.lose:
			self.root.after(1, self.updateFrame)

	def drawStep(self, button, idNum):
		for k in range(len(self.keys)):
			if k == button and idNum >= self.press:
				color = 'black'
			else:
				color = 'white'
			if idNum >= 0 and not self.lose:
				self.canvas.create_rectangle(self.width*k/len(self.keys), self.height+self.top-idNum*self.height/self.stack, self.width*(k+1)/len(self.keys), self.height+self.top-(idNum+1)*self.height/self.stack, fill = color)


	def buttonPress(self, event):
		if event.char.upper() in self.keys:
			index = self.keys.index(event.char.upper())
		else:
			return False
		if index == int(self.level[self.press]): 
			if self.press <= self.bottom+(self.stack+1)//2:
				self.press += 1
			self.started = True
			self.draw = True
		elif self.press <= self.bottom+2 and self.started:
			print(self.press, round(self.vel, 2))
			self.lose = True
			self.root.destroy()

root = Tk()
game = Game(root)


root.iconify()
root.update()
root.deiconify()
root.mainloop()