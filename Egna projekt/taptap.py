import time
import msvcrt
import sys

def restartLine():
    sys.stdout.write('\r')
    sys.stdout.flush()

lista = [b'H', b'K', b'P', b'M']
tlista = ['^', '<', 'V', '>']
numlista = [3, 0, 2, 0, 1, 0, 3, 0, 2, 0, 1, 0, 3, 0, 2, 0, 1, 0, 3, 2, 0, 1, 3, 2, 0, 1, 3, 2, 0, 1, 3, 2, 0, 1, 3, 2, 0, 1, 3, 2, 0, 1, 3, 2, 0, 1, 3, 2, 0, 1, 3, 2, 0, 1]
lista2 = []
lista3 = []

for check in range(0, len(numlista)):
    lista2.append(lista[numlista[check]])
    lista3.append(tlista[numlista[check]])

temp = ''
errors = 0
print()
print("Tryck på piltangenterna i följande ordning:")
string = ''
for count in range(0, len(lista2)):
    string += lista3[count]
print(string)



while temp != lista2[0]:
    temp = msvcrt.getch()

start = time.clock()
for count in range(0, len(lista2)):
    tempcount = 0
    while temp != lista2[count]:
        temp = msvcrt.getch()
        if temp != lista2[count]:
            tempcount += 1
            if tempcount == 2:
                errors += 1
    temp = ''
    #print((count+1)*'*')
    #print(string)
    restartLine()
    sys.stdout.write((count+1)*'*')
    sys.stdout.flush()
    
    
    
    
time = time.clock() - start
print()
print("Tid:", round(time, 3), "sekunder")
print("Tryck per sekund:", round(len(numlista)/time, 2))
print("Fel:", errors)
