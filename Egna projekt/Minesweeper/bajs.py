from tkinter import *
import winsound
import string
import random
import functools
import time
import tkinter.messagebox
import sys

def hejsan():
    print('hejsan')

class App:
    def __init__(self, master):
        frame = Frame(master)
        frame.grid()
        self.button = Button(frame, width = 2, height = 2, text='hej', command = hejsan)
        self.button.grid(row = 0, column = 0)
        frame.pack()

        
root = Tk()
root.title = 'asdf'
app = App(root)
root.mainloop()
