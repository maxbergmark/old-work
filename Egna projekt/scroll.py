import win32api
from win32con import *
import time
import winsound
import pyHook
import pythoncom


global up
global down

up = False
down = False



def callback(event):
    global up
    global down
    
    if event.Key == 'O':
        if up:
            up = False
            print('UP OFF')
        else:
            down = False
            up = True
            print('UP ON')
            
    elif event.Key == 'L':
        if down:
            down = False
            print('DOWN OFF')
        else:
            up = False
            down = True
            print('DOWN ON')
            
    elif event.Key == 'Q':
        print('QUIT')
        quit()
    return True

print('WAIT...')
hook = pyHook.HookManager()
hook.KeyDown = callback
hook.HookKeyboard()

print('RUNNING')
while not down:
    pythoncom.PumpWaitingMessages()


while True:
    
    if up:
        win32api.mouse_event(MOUSEEVENTF_WHEEL, 0, 0, 1, 0)
    elif down:
        win32api.mouse_event(MOUSEEVENTF_WHEEL, 0, 0, -1, 0)
    
    pythoncom.PumpWaitingMessages()

