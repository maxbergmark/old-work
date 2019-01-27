import random
from tkinter import *
import time
import datetime
from PIL import Image, ImageDraw
import shelve

class Node:

	def __init__(self, value, prev = None, next = None):
		self.value = value
		self.prev = prev
		self.next = next

class Queue:

	def __init__(self):
		self.first = None
		self.last = None

	def put(self, value):
		if self.first == None:
			self.first = Node(value)
			self.last = self.first
		else:
			self.last.next = Node(value, self.last)
			self.last = self.last.next

	def pop(self):
		if not self.isEmpty():
			value = self.first.value
			self.first = self.first.next
			return value
		return None

	def isEmpty(self):
		return self.first == None

class Stack:

	def __init__(self):
		self.top = None

	def put(self, value):
		if self.top == None:
			self.top = Node(value)
		else:
			self.top = Node(value, self.top)

	def pop(self):
		if not self.isEmpty():
			value = self.top.value
			self.top = self.top.prev
			return value
		return None

	def show(self):
		return self.top.value

	def isEmpty(self):
		return self.top == None

class mazeNode:

	def __init__(self, pos, conn = None):
		self.pos = pos
		if conn:
			self.conns = [conn]
		else:
			self.conns = []

class Maze:

	def __init__(self, xSize, ySize):
		
		self.pos = (xSize//2, ySize//2)
		self.xSize = xSize
		self.ySize = ySize
		self.directions = [(0, 1), (1, 0), (0, -1), (-1, 0)]
		self.visit = set([self.pos])
		self.shelf = shelve.open('shelf')
		self.shelf.clear()
#		self.maze = mazeNode(self.pos)
		self.shelf[str(self.pos)] = mazeNode(self.pos)
		self.node = self.shelf[str(self.pos)]
		self.stack = Stack()
		self.deadEnds = 0
		for i in randPerm(list(range(4))):
			self.stack.put(mazeNode(add(self.pos, self.directions[i]), self.node))

	def makeMaze(self):
		while not self.stack.isEmpty():
			#print(self.node.pos, [node.pos for node in self.node.conns])
			count = 0
			while self.node.pos in self.visit and not self.stack.isEmpty():
				self.node = self.stack.pop()
				count += 1
			
			if self.stack.isEmpty():
				break

			self.shelf[str(self.node.pos)] = self.node
			if self.node.pos not in self.visit:
				self.visit.add(self.shelf[str(self.node.pos)].pos)

			for conn in self.node.conns:
				break
				temp = self.shelf[str(conn.pos)]
				temp.conns.append(self.node)
				self.shelf[str(conn.pos)] = temp
				

			tempCount = 0
			for i in randPerm(list(range(4))):
				pos = add(self.node.pos, self.directions[i])
				if pos not in self.visit and check(pos, self.xSize, self.ySize):
					#print(self.stack.show().pos)
					self.stack.put(mazeNode(pos, self.node))
					tempCount += 1
			if tempCount == 0:
				self.deadEnds += 1
		if len(self.shelf[str(self.pos)].conns) == 1:
			self.deadEnds += 1

			

	def drawMaze(self):
#		self.canvas = Canvas(self.root, width = 2*self.xSize-1, height = 2*self.ySize-1, bg = 'black')
#		self.canvas.pack()
		self.lines = 0
		self.image = Image.new('RGB', (2*self.xSize-1, 2*self.ySize-1), 'black')
		self.draw = ImageDraw.Draw(self.image)
		self.drawn = set([])
		#'''
		for key in self.shelf.keys():
			temp = self.shelf[key]
			for conn in temp.conns:
				tPos = translate(temp.pos)
				cPos = translate(conn.pos)
				self.draw.line([tPos[0], tPos[1], cPos[0], cPos[1]], 'white')
				self.lines += 1
		'''
		self.node = self.shelf[str(self.pos)]
		for conn in self.node.conns:
			self.stack.put(conn)
		while not self.stack.isEmpty():
			self.node = self.shelf[str(self.stack.pop().pos)]
			for conn in self.node.conns:
				tPos = translate(self.node.pos)
				cPos = translate(conn.pos)
				if not ((self.node.pos + conn.pos) in self.drawn):
					self.draw.line([tPos[0], tPos[1], cPos[0], cPos[1]], 'white')
#					self.canvas.create_line(tPos[0], tPos[1], cPos[0], cPos[1], fill = 'white')
#					self.canvas.update_idletasks()
					self.lines += 1
				if conn.pos not in self.drawn:
					self.stack.put(conn)
				self.drawn.add(conn.pos + self.node.pos)
				self.drawn.add(self.node.pos)
		'''
		filename = 'maze' + str(self.xSize) + 'x' + str(self.ySize) + '.png'
		self.image.save(filename)




def translate(pos):
	return tuple([pos[i]*2 for i in range(len(pos))])

def check(pos, xSize, ySize):
	if pos[0] >= 0 and pos[1] >= 0 and pos[0] < xSize and pos[1] < ySize:
		return True
	return False

def add(tuple1, tuple2):
	if len(tuple1) == len(tuple2):
		return tuple([tuple1[i]+tuple2[i] for i in range(len(tuple1))])
	return False

def randPerm(values):
	random.shuffle(values)
	return values



print('\n', datetime.datetime.now())
start = time.clock()
maze = Maze(10, 10)
maze.makeMaze()
print(' Creation time:', round(time.clock()-start, 3))
maze.drawMaze()
#maze.root = Tk()
#maze.root.after(1, maze.drawMaze)
#maze.root.mainloop()
maze.shelf.close()
print(' Drawing time:', round(time.clock()-start, 3))
print(' Dead ends:', maze.deadEnds)