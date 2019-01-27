from tkinter import *
import colorsys
import math

class Field:

	def __init__(self, master):
		self.root = master
		self.canvas = Canvas(self.root, width = 1080, height = 1080, bg = 'black')
		self.canvas.pack()
		self.drawField()

	def drawField(self):

		for r in range(0, 1080, 20):
			d = 2*math.pi*r+.00001
			for t in [.00001*x for x in range(0, int(200000*math.pi-2*d**.5), int(4190000*math.pi/d))]:
				y = r*math.cos(t)
				x = r*math.sin(t)
				s = 5
				self.canvas.create_oval([540+x-s, 320+y-s, 540+x+s, 320+y+s], fill = 'white', outline = None)

root = Tk()
field = Field(root)
root.title('VectorField')
root.mainloop()