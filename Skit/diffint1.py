import time

def funct(x):
    value = 3*x**3+6*x**2+7*x+4
    return float(value)

x = float(0)
inta = float(-10**3)
intb = float(10**3)
count = 0

while 1:
    count += 1
    x = 0.5*(inta+intb)
    print("count: ", count)
    print ("y: ", funct(x))

    if inta*funct(x) > 0:
        inta = x
    else:
        intb = x


    print("x: ", x)
    print()
    time.sleep(0.01)
