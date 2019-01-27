import time


global lists
lists = [None for i in range(1000000)]

def countCol(num0):
    num = num0
    count = 0
    lista = [num]
    while num != 1:
        #print(' ', num)
        if num < len(lists) and lists[num] != None:
            #print('hej')
            lista.extend(lists[num])
            lists[num0] = lista
            return lista
        if num % 2 == 0:
            num //= 2
        else:
            num = num*3 + 1
        count += 1
        lista.append(num)
    #print(lista)
    lists[num0] = lista
    return lista

def countCol2(num):
    lista = [num]
    while num != 1:
        if num % 2 == 0:
            num //= 2
        else:
            num = num*3 + 1
        lista.append(num)
    return lista

#print(len(lists))
t0 = time.clock()
for i in range(1, 1000000):
    #print()
    #print(i)
    #num = int(input('Number: '))
    countCol2(i)
    #if len(countCol(i)) > 300:
        #print(i, countCol(i))

t1 = time.clock()

#print(lists)

print(t1-t0)
