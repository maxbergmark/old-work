import random
from tkinter import *
import time
import datetime
from PIL import Image, ImageDraw
import sys

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
		self.size = 0

	def put(self, value):
		if self.top == None:
			self.top = Node(value)
		else:
			self.top = Node(value, self.top)
		self.size += 1

	def pop(self):
		if not self.isEmpty():
			value = self.top.value
			self.top = self.top.prev
			self.size -= 1
			return value
		return None

	def show(self):
		return self.top.value

	def isEmpty(self):
		return self.top == None

class mazeNode:
	__slots__ = ('pos', 'conns')

	def __init__(self, pos, conn = None):
		self.pos = pos
		self.conns = [conn] if conn else []

class Maze:

	def __init__(self, xSize, ySize):
		self.pos = (xSize//2, ySize//2)
		self.xSize = xSize
		self.ySize = ySize
		self.directions = [(0, 1), (1, 0), (0, -1), (-1, 0)]
		self.visit = set([self.pos])
		self.maze = mazeNode(self.pos)
		self.node = self.maze
		self.stack = Stack()
		self.deadEnds = 0
		for i in randPerm(list(range(4))):
			self.stack.put(mazeNode(add(self.pos, self.directions[i]), self.node))

	def makeMaze(self):
		while True:
			
			while self.node.pos in self.visit and not self.stack.isEmpty():
				self.node = self.stack.pop()
			if self.stack.isEmpty():
				break
			if self.node.pos not in self.visit:
				self.visit.add(self.node.pos)

			for conn in self.node.conns:
				conn.conns.append(self.node)

			tempCount = 0
			for i in randPerm(list(range(4))):
				pos = add(self.node.pos, self.directions[i])
				if pos not in self.visit and check(pos, self.xSize, self.ySize):
					tempNode = mazeNode(pos, self.node)
					self.stack.put(tempNode)
					#self.visit.add(tempNode.pos)
					tempCount += 1
			if tempCount == 0:
				self.deadEnds += 1
		if len(self.maze.conns) == 1:
			self.deadEnds += 1

			

	def drawMaze(self):
#		self.canvas = Canvas(self.root, width = 2*self.xSize-1, height = 2*self.ySize-1, bg = 'black')
#		self.canvas.pack()
		self.lines = 0
		self.image = Image.new('RGB', (2*self.xSize+1, 2*self.ySize+1), 'black')
		self.draw = ImageDraw.Draw(self.image)
		self.drawn = set()
		self.node = self.maze
		self.draw.line([0, 1, 0, 1], 0x00ff00)
		
		self.draw.line([2*self.xSize, 2*self.ySize-1, 2*self.xSize, 2*self.ySize-1], 0x0000ff)
		for conn in self.node.conns:
			self.stack.put(conn)
		while not self.stack.isEmpty():
			self.node = self.stack.pop()
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
		filename = 'Mapsearch/Mazes/maze' + str(self.xSize) + 'x' + str(self.ySize) + '.png'
		self.image.save(filename)


def translate(pos):
	return tuple([pos[i]*2+1 for i in range(len(pos))])

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
s = [i*10**j for j in range(1,3) for i in range(1,10)]
for i in range(1):
	maze = Maze(10, 10)
	maze.makeMaze()
	print(' Creation time:', round(time.clock()-start, 3))
	maze.drawMaze()
	#maze.root = Tk()
	#maze.root.after(1, maze.drawMaze)
	#maze.root.mainloop()
	print(' Drawing time:', round(time.clock()-start, 3))
	print(' Dead ends:', maze.deadEnds)