import time
import string

inputstr = str(input('Letters: '))

fil = open('DSSO.txt')
string = fil.read()
lista = string.split('\n')
ordlista = []
for element in lista:
    ordlista.append(element.strip(' '))
print('ord: ', len(ordlista))
 


svar = []
t = time.clock()


inputstr = sorted(inputstr.lower())
print(inputstr)

print()

