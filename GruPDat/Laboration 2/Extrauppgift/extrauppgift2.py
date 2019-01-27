import time

for count in range(1, 10000):
    summa = 0
    for check in range(1, int(count/2+1)):
        if count % check == 0:
            summa += check
    if summa == count:
        print(count)
