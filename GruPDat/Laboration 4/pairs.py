import functions as fn
import time as t

fil = open('ordlista.txt',)

string = fil.read()
lista = string.split()
print()
start = t.clock()
fn.linparsok(lista)
end = t.clock()
print(end-start)
