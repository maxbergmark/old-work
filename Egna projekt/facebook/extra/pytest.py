import queue
import time

l = [i for i in range(100000)]

t0 = time.clock()
q = queue.Queue()
[q.put(i) for i in l]
t1 = time.clock()
q2 = queue.Queue()
q2.queue = queue.deque(l)
t2 = time.clock()
print(t1-t0, t2-t1, (t1-t0)/(t2-t1))

print(q.qsize(), q2.qsize())

while not (q.empty() or q2.empty):
	if q.get() != q2.get():
		print('not equal')