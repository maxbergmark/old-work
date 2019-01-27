
summa = int(input("Summa: "))

for a in range(1, int((summa/2)**(1/3)+1)):
    for b in range(1, int((summa)**(1/3)+1)):
        if a**3 + b**3 == summa:
            print(a, "^3 + ", b, "^3 = ", summa)
