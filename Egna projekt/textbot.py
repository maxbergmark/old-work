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

    wsh = comclt.Dispatch("WScript.Shell")
    global click
    text1 = ['OFF', 'ON']

    if event.Key == 'F8':
        
        click = not click
        if click:
            winsound.Beep(500, 200)
        else:
            speedcheck = True
        print(text1[click])
    elif event.Key == 'F9':
        print('EXIT')
        quit()
    return True

text = str(input('INPUT: '))
enter = input('ENTER(y/n): ')
enter = (enter == 'y')
freq = float(input('FREQUENCY: '))
hook = pyHook.HookManager()
hook.KeyDown = callback
hook.HookKeyboard()
wsh = comclt.Dispatch("WScript.Shell")
count = 0


print('RUNNING')



while True:
    if click:
        t0 = time.clock()
        if count == 0:
            tstore = time.clock()
        wsh.SendKeys(text)
        if enter:
            wsh.SendKeys('~')
        count += 1
        t1 = time.clock()
        if count == 100:
            if (t1-tstore)/100 > 1/freq:
                print('Frequency too high. Current frequency is:', 100/(t1-tstore))
        time.sleep(max(0, 1/freq-(t1-t0)))
    else:
        count = 0
    
    pythoncom.PumpWaitingMessages()
winsound.Beep(500, 500)
