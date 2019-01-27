from win32api import *
from win32gui import *
import time
from random import randint

while True:
    SetCursorPos((GetCursorPos()[0] + randint(-1, 1), GetCursorPos()[1] + randint(-1, 1)))
    time.sleep(0.05)
    
