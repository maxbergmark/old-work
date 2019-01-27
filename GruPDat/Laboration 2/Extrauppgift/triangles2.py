def triangel2(base, distance):
    string = ''
    for i in range(0, base//2+1):
        string += (distance+i)*' '
        string += (base-2*i)*'*'
        string += '\n'
    return string

base = int(input("base: "))
distance = int(input("distance: "))

string = triangel2(base, distance)
print(string)
