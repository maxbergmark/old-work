import functions as fn


fil = open('ordlista.txt',)

string = fil.read()
lista = string.split()
print()
while 1:
    check = str(input("Ditt ord: "))
    if fn.binsok(lista, check) is True:
        result = 'finns'
    else:
        result = 'finns inte'
    print(check, result)
