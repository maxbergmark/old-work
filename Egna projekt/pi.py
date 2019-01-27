

div = 1
summa = 0

while 1:

    summa += int(10**100/div) - int(10**100/(div+2))
    div += 4
    if div % 100001 == 0:
        print(4*summa)
