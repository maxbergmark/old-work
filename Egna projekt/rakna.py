lista = [0]*100
for mult in range(1, 101):
    n = 1
    while mult*n < 101:
        if (lista[mult*(n)-1] == 0):
            lista[mult*(n)-1] = 1
        else:
            lista[mult*(n)-1] = 0
        n += 1
    print(sum(lista))
