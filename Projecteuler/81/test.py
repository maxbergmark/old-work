import time

start = time.clock()

f = open('matrix.txt','r')

A = []
for l in f:
    A.append([int(i) for i in l.split(',')])

f.close()

N = 80

B = []
for i in range(N):
    B.append([0 for i in range(N)])

B[0][0] = A[0][0]
for i in range(1,N):
    B[i][0] = B[i-1][0]+A[i][0]
    B[0][i] = B[0][i-1]+A[0][i]

for i in range(1,N):
    for j in range(1,N):
        B[i][j] = min(B[i-1][j],B[i][j-1])+A[i][j]

print(B[N-1][N-1])
print(time.clock() - start)
