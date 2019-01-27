import time


def countCol2(num):
    lista = [num]
    while num != 1:
        if num % 2 == 0:
            num //= 2
        else:
            num = num*3 + 1
        lista.append(num)
    return lista

inp = str(input('Number: '))
exec('num = ' + inp)
print(num)
t0 = time.clock()
lista = countCol2(num)


t1 = time.clock()
#print(lista)
print(len(lista))

print(t1-t0)
