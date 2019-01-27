from win32api import *
import msvcrt as m
import time

save = open('list.txt', 'r+')

lista = save.read().split('\n')[:256]
if lista == []:
    lista = ['0' for x in range(256)]
print(lista)

save = open('list.txt', 'w')

temp = False

while GetCursorPos() != (0,0):
    
    if m.kbhit():
        x = ord(m.getch())
        if x == 224:
            temp = True
        if x != 224:
            if temp:
                x += 150
                temp = False
            char = str(input('Tecken: '))
            lista[x] = char

save.write('\n'.join(lista))


