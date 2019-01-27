import math
import copy
from tkinter import *
import colorsys

def mult(c1, c2):
	return (c1[0]*c2[0]-c1[1]*c2[1], c1[0]*c2[1]+c1[1]*c2[0])

def add(c1, c2):
	return (c1[0]+c2[0], c1[1]+c2[1])

def length(c1):
	return (c1[0]**2+c1[1]**2)**.5

def toHex(value):
	hval = hex(int(value*255))[2:]
	if len(hval) ==1:
		hval = '0' + hval
	return hval

def frange(a, b, n):
	return [a+(1-i/n)*(b-a) for i in range(n)]

n = 200
matrix = [[(-j,i) for i in frange(-1, 1, n)] for j in frange(-1, 2, n)]
c = copy.deepcopy(matrix)

for a in range(20):
	for i in range(len(matrix)):
		for j in range(len(matrix[0])):
			v = matrix[i][j]
			if length(v) < 5:
				matrix[i][j] = add(mult(v, v), c[i][j])


lengths = [[length(matrix[i][j]) for i in range(len(matrix))] for j in range(len(matrix[0]))]
maxlength = max([max(temp) for temp in lengths])

normlength = [[lengths[i][j]/maxlength for i in range(len(lengths))] for j in range(len(lengths[0]))]

size = 640
root = Tk()
frame = Frame(root, width = size, height = size)
buttons = [[Label(frame, bg = '#0000' + toHex(normlength[i][j]), fg = '#0000' + toHex(normlength[i][j]), bitmap = 'info', width = size/n, height = size/n) for i in range(len(matrix))] for j in range(len(matrix[0]))]
for i in range(len(buttons)):
	for j in range(len(buttons[0])):
		buttons[i][j].grid(row = i, column = j)

frame.pack()
root.mainloop()