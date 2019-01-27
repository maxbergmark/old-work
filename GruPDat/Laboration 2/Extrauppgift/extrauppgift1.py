import time

def function(count):
    
    summa = 0
    for check in range(1, int(count/2+1)):
        if count % check == 0:
            summa += check
    if summa == count:
        return True
    else:
        return False




tal = int(input("Tal: "))
print(function(tal))
