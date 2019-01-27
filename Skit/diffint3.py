import time

def funct(x):
    value = 3*x**3+6*x**2+7*x+4
    return float(value)

x0 = float(input("x0: "))
x1 = float(input("x1: "))
count = 0
temp = x0

while 1:

    x0 = temp
    temp = x1
    count += 1
    x1 = x1 - (x1-x0)/(funct(x1)-funct(x0))*funct(x1)
    print(count, "   ", x1)
    time.sleep(0.1)
