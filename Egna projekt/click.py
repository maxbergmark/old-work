# import win32api
# from win32con import *
import uinput
import time
# import winsound
import pyHook
import pythoncom

global click
global up
global down
global speeds
global speed

click = False
up = False
down = False
speeds = [round(x*10**j, 3) for j in range(-1, 4) for x in range(1, 10)]

speed = 20

def callback(event):
    
    global click
    global up
    global down
    global speeds
    global speed
    text1 = ['OFF', 'ON']
    text2 = [['UP OFF', 'UP ON'], ['DOWN OFF', 'DOWN ON']]
    
    if event.Key == 'K':
        click = not click
        #if not click:
        #    winsound.Beep(500, 200)
        print(text1[click])
        
    if event.Key == 'O':
        if up:
            up = False
        else:
            down = False
            up = True
        print(text2[0][up])
            
    elif event.Key == 'L':
        if down:
            down = False
        else:
            up = False
            down = True
        print(text2[1][down])
    elif event.Key == 'Oem_Period':
        if speed != len(speeds)-1:
            print(round(speeds[speed+1], 1), 'clicks per second')    
        speed += (speed != len(speeds)-1)
        
    elif event.Key == 'Oem_Comma':
        if speed != 0:
            print(round(speeds[speed-1], 1), 'clicks per second')
        speed -= (speed != 0)
    elif event.Key == 'Q':
        print('EXIT')
        quit()
    return True

print('\nWelcome to the clickbot. \n\n  \'K\' triggers clicking,',
      '\n  \'O\' triggers scroll up, \n  \'L\' triggers scroll down,',
      '\n  \'.\' increases clicking frequency, \n  \',\' decreases clicking frequency.\n')

hook = pyHook.HookManager()
hook.KeyDown = callback
hook.HookKeyboard()

print('RUNNING')
print(speeds[speed], 'clicks per second')


while True:
    if click:
        win32api.mouse_event(MOUSEEVENTF_LEFTDOWN,0,0)
        win32api.mouse_event(MOUSEEVENTF_LEFTUP,0,0)
        time.sleep(1/speeds[speed])
    if up:
        win32api.mouse_event(MOUSEEVENTF_WHEEL, 0, 0, 1, 0)
    elif down:
        win32api.mouse_event(MOUSEEVENTF_WHEEL, 0, 0, -1, 0)

    
    pythoncom.PumpWaitingMessages()

winsound.Beep(500, 500)
