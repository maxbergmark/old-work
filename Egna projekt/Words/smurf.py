import time

fil = open('ordlista.txt')

ordlista = fil.read().split('\n')
count = 0
namn = 'aeiouyåäö'
namnlista = set(namn)
print(namnlista)

print()
t0 = time.clock()
for element in ordlista:
    check = True
    for letter in element:
        if letter in namnlista:
            check = False
            break
    if check:
        # print('  ' + element)
        count += 1

t1 = time.clock()
print(t1-t0)

print()
print(count)

