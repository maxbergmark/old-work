def triangel1(base, distance):
    string = ''
    for i in range(0, base//2+1):
        string += (distance+base//2-i)*' '
        string += (2*i+1)*'*'
        string += '\n'
    return string

base = int(input("base: "))
distance = int(input("distance: "))

string = triangel1(base, distance)
print(string)
