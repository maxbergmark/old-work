from win32api import *
import time
s=time.sleep;k=keybd_event
while True:
    s(9);k(87,0,);s(.1);k(87,0,2)
    s(9);k(37,0,);s(.5);k(37,0,2)