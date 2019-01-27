def triangel3(base,distance):
    triangel1(base,distance)
    triangel2(base-2, distance+1)

def triangel1(base, distance):
    for i in range(0, base//2+1):
        print((distance+base//2-i)*' ', end="")
        print((2*i+1)*'*')

def triangel2(base, distance):
    for i in range(0, base//2+1):
        print((distance+i)*' ', end="")
        print((base-2*i)*'*')

base = int(input("base: "))
distance = int(input("distance: "))

triangel3(base, distance)

