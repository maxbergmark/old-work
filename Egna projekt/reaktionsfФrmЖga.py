import time
import random
import msvcrt

count = 0
medel = 0
print()
while 1:
    
    print("Press space to start, press esc to exit, press m for average time")
    temp = ''
    while temp != b' ':
        temp = msvcrt.getch()
        if temp == b'\x1b':
            if count > 1:
                print("Medeltid: ", medel)
            quit()
        if temp == b'm':
            print()
            print("Medeltid:", medel)
            print()
            print("Press space to start, press esc to exit, press m for average time")            
    temp = ''
    start = time.clock()
    end = start + 1 + 3*random.random()
    print("wait", round(end-start, 2), "seconds")
    print()
    while time.clock() < end:
        pass        

    print("NOW")
    temp = ''
    while temp != b' ':
        temp = msvcrt.getch()
    finish = time.clock() - end
    if finish > 0.005:
        print("Reaction time:", finish)
        count += 1
        medel = (medel * (count-1) + finish) / count
    else:
        print("Cheater!")
    print()
