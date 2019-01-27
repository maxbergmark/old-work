import time

def faculty(n):
    for i in range(1,n):
        n *= i
    return n

print()
n = int(input("n: "))
k = int(input("k: "))

print()
print(k, ": ", int(faculty(n)/(faculty(k)*faculty(n-k))))

imax = 0
imaxnum = 0

for i in range(1, n):
    if imax < (faculty(n)/(faculty(i)*faculty(n-i))):
        imax = (faculty(n)/(faculty(i)*faculty(n-i)))
        imaxnum = i

print(imaxnum, ": ", int(imax))
