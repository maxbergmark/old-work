import time

summa = float(input("Sum: "))
print()

n = 0
summa2 = 0
nmax = 10000000
start = time.clock()

while summa2 < summa and n < nmax:
    n += 1
    summa2 += 1/n

end = time.clock()

if summa < summa2:
    print("n: ", n)
    print("Sum: ", summa2)
    print()
    print("Time: ", 1000000*(end-start), " microseconds")
    print(n/(end-start), " iterations per second")
else:
    print("Too many iterations")
