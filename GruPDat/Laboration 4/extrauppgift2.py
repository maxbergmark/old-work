import functions as fn
import time as t

fil = open('ordlista.txt',)
func = [fn.linparsok, fn.binparsok]
temp = int(input('Vilken funktion önskas?\n1 Linparsok  2 Binparsok\n'))
string = fil.read()
lista = string.split()
print()
start = t.clock()
fn.extra2(lista, func[temp-1])
end = t.clock()
print(end-start)
