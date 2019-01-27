import time
import math




t0 = time.clock()


squares = [i*i for i in range(1000)]

tests = [i for i in range(1000000)]

for i in tests:
	math.sqrt(i)
	


print(time.clock()-t0)