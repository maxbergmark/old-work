def triangel(base, distance):
    for i in range(0, base//2+1):
        print((distance+base//2-i)*' ', end="")
        print((2*i+1)*'*')

base = int(input("base: "))
distance = int(input("distance: "))

triangel(base, distance)

