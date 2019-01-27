def triangel(base, distance):
    for i in range(0, base//2+1):
        print((distance+i)*' ', end="")
        print((base-2*i)*'*')

base = int(input("base: "))
distance = int(input("distance: "))

triangel(base, distance)

