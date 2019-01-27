from win32api import *
import msvcrt as m
import time

save = open('log.txt', 'r+')

while True:
    temp = GetCursorPos()
    time.sleep(0.1)
    if temp != GetCursorPos():
        print(GetCursorPos())
    if m.kbhit():
        print(chr(ord(m.getch())))
