import time
import string

inputstr = str(input('Letters: '))

inputlist = inputstr.split(' ')

reuse = False


fil = open('wordfreq.txt')



string = fil.read()
lista = string.split('\n')
ordlista = []
for element in lista:
    ordlista.append(element.split())
print('ord: ', len(ordlista))



svar = []
t = time.clock()


inputstr = sorted(inputstr.lower().strip())
inputstr

print()

count = 0
temp = 0
tstop = 0
maxlen = 0
maxword = []
found = []

t0 = time.clock()

for element in ordlista:
    if element != []:
        temp = 0
        templist = inputstr[:]
        for letter in range(len(element[0])):
            if element[1][letter] in templist:
                temp += 1
                templist[templist.index(element[1][letter])] = '0'
                if temp == len(element[0]):
                    found.append(element[0])
                    #print('  ' + element[0])
                    if len(element[0]) > maxlen:
                        maxlen = len(element[0])
                        maxword = [element[0]]
                    if len(element[0]) == maxlen and (element[0] not in maxword):
                        maxword.append(element[0])
                    count += 1

    
found.sort(key=len,reverse=True)

t1 = time.clock()
print(t1-t0)
# for element in found:
    # print(element)
