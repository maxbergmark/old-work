import time

t = time.clock()
for i in range(1000):
    a = i%65536
print(time.clock()-t)
