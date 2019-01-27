import math as m

a = 1
while 1:
    square = a**0.5
    length = (int(m.log10(a))+1)

    for count in range(0, int(m.log(square))):
        if a == int(square*(10**(length-count))) % (10**length):
            print(a, ' ', count, ' ', int(square*(10**(length-count))) % (10**length))
    
    a += 1
