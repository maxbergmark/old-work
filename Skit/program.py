a = float(input("a: "))
b = a
count = 1
while (1):
    
    c = a+b
    d = a*b
    
    a = c+d
    b = c*d

    if count % 1000000 == 0:
        print(round(count/1000000), "   ", a)
    
    count = count + 1

print(a)

    


