from tkinter import *
import colorsys

class Field:

	def __init__(self, master):
		self.root = master
		self.canvas = Canvas(self.root, width = 1280, height = 720, bg = 'black', cursor = 'none')
		self.canvas.bind('<Motion>', self.drawField)
		self.root.bind('<Escape>', self.quitProgram)

		self.canvas.pack()
		self.field = [[[40*x, 40*y] for x in range(32)] for y in range(18)]

	def drawField(self, event):
		self.canvas.delete('all')
		pos = [event.x, event.y]
		for row in self.field:
			for ele in row:
				try:
					dist = ((ele[0]-pos[0])**2+(ele[1]-pos[1])**2)**.5
					dirv = [40*(ele[i]-pos[i])/dist*(.02+2**(-dist*.01)) for i in range(2)]
				except:
					dirv = [0, 0]
				color = tuple([int(value*256) for value in colorsys.hls_to_rgb(dist/(640*2**-1), 2**(-1-.005*dist), .99)])
				color = '#%02X%02X%02X' % color
				self.canvas.create_line(ele[0], ele[1], ele[0]+dirv[0], ele[1]+dirv[1], fill = color)
				#self.canvas.create_oval([ele[0]-20*2**(-.005*dist), ele[1]-20*2**(-.005*dist), ele[0]+20*2**(-.005*dist), ele[1]+20*2**(-.005*dist)], fill = color, outline = None)

	def quitProgram(self, event):
		self.root.destroy()

root = Tk()
field = Field(root)
root.title('VectorField')
root.mainloop()