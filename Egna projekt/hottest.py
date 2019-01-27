import win32api
import win32gui
from win32con import *
import win32clipboard
import pyHook
import pythoncom
import string
import tkinter as tk
import functools


class App:
    def __init__(self, master, paster):
        self.master = master
        self.frame = tk.Frame(master)
        self.frame.grid()
        tk.Label(self.frame, text = 'Height: ').grid(row = 0, column = 0)
        tk.Label(self.frame, text = 'Width: ').grid(row = 1, column = 0)
        s1 = tk.StringVar()
        s2 = tk.StringVar()
        self.e1 = tk.Entry(self.frame, textvariable = s1)
        self.e2 = tk.Entry(self.frame, textvariable = s2)
        
        self.e1.grid(row = 0, column = 1)
        self.e2.grid(row = 1, column = 1)

        
        self.button1 = tk.Button(self.frame, width = 5, height = 1, text = 'List', command = functools.partial(self.testfunc, s1, s2, paster))
        self.button1.grid(row = 0, column = 2, rowspan = 1)
        self.button1.otext = 'List'

        self.button2 = tk.Button(self.frame, width = 5, height = 1, text = 'Annoy', command = functools.partial(self.testfunc2, s1, s2, paster))
        self.button2.grid(row = 1, column = 2, rowspan = 1)
        self.button2.otext = 'Annoy'

        self.master.bind('<Return>', functools.partial(self.testfunc, s1, s2, paster))
        
        
    def testfunc(self, e1, e2, paster, event = None):
        if e1.get().isdigit() and e2.get().isdigit():
            temp = '[' + (int(e1.get())*('[' + (int(e2.get())-1)*' , ' + '],\n')).strip(',\n') + ']'
            paster.set(temp)
            self.master.destroy()
        else:
            self.button1['text'] = 'ERROR'
            self.button1['fg'] = 'red'
            self.frame.after(500, self.resetText, self.button1)

    def testfunc2(self, e1, e2, paster, event = None):
        if e1.get().isdigit() and e2.get().isdigit():
            temp = (int(e1.get())*(((int(e2.get())//3+1)*'hej')[:int(e2.get())] + '\n')).strip('\n')
            paster.set(temp)
            self.master.destroy()
        else:
            self.button2['text'] = 'ERROR'
            self.button2['fg'] = 'red'
            self.frame.after(500, self.resetText, self.button2)

    def resetText(self, button):
        button['text'] = button.otext
        button['fg'] = 'black'
                             
                             

def word1(args):
    root = tk.Tk()
    root.geometry('+' + str(win32gui.GetCursorPos()[0]-60) + '+' + str(win32gui.GetCursorPos()[1]-40))
    paster = tk.StringVar()
    app = App(root, paster)
    root.update()
    root.deiconify()
    root.mainloop()
    
    
    return paster.get()

def word2(args):
    return 'asdfasdf'


def callback(event, strings):
       
    data = ''
    if event.Ascii <= len(keys):
        if keys[event.Ascii-1] in strings and keys[event.Ascii-1] == event.Key:
            args = [10, 20]
            data = strings[keys[event.Ascii-1]](args)

    if data:
        win32clipboard.OpenClipboard()
        win32clipboard.EmptyClipboard() 
        win32clipboard.SetClipboardData(CF_UNICODETEXT, data)
        win32clipboard.CloseClipboard()

	        
    if event.Key == 'F9':
        print('EXIT')
        quit()
    return True

keys = list(string.ascii_uppercase)
strings = {}
strings['M'] = lambda args: word1(args)
strings['B'] = word2

hook = pyHook.HookManager()
hook.KeyDown = lambda event: callback(event, strings)
hook.HookKeyboard()



print('RUNNING')



while True:

    pythoncom.PumpWaitingMessages()
winsound.Beep(500, 500)
