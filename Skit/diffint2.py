import time

def funct(x):
    value = 3*x**3+6*x**2+7*x+4
    return float(value)

def dfunct(x):
    value = 9*x**2+12*x+7
    return float(value)

x = float(input("x: "))
count = 0
while 1:

    x = x - funct(x)/dfunct(x)
    count += 1
    print(count, "   ", x)
    time.sleep(0.01)
    
