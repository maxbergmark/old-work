import functions as fn


fil = open('ordlista.txt',)

string = fil.read()
lista = string.split()

while 1:
    check = str(input("Ditt ord: "))
    if fn.linsok(lista, check) is True:
        result = 'finns'
    else:
        result = 'finns inte'
    print(check, result)
