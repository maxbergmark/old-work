import time
start = time.clock()
lista = list(set([a**b for a in range(2, 101) for b in range(2, 101)]))
print(len(lista))
print(time.clock() - start)
