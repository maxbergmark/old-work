import win32api
from win32con import *
import time
import winsound
import pyHook
import pythoncom
import win32com.client as comclt


global click
click = False

def callback(event):
    print(pyHook.HookManager.GetKeyState(55))
    if event.Ascii == 10:
        time.sleep(.2)
        wsh.SendKeys('fdsa')
        
    elif event.Key == 'F9':
        print('EXIT')
        quit()
    return True


hook = pyHook.HookManager()
hook.KeyDown = callback
hook.HookKeyboard()
wsh = comclt.Dispatch("WScript.Shell")
count = 0


print('RUNNING')



while True:

    pythoncom.PumpWaitingMessages()
winsound.Beep(500, 500)
