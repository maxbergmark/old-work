import functions as fn
import time as t

fil = open('ordlista.txt',)
func = [fn.linsok, fn.binsok]
printer = []
string = fil.read()
lista = string.split()

while 1:
    temp = int(input('Vilken funktion önskas?\n1 Linsok  2 Binsok\n'))
    print(fn.extra1(lista, func[temp-1]))
    print()
    
