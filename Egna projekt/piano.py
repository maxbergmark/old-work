import time
import winsound
import threading
import pyHook
import pythoncom


class SummingThread(threading.Thread):
    def __init__(self, low, high):
        threading.Thread.__init__(self)
        self.low = low
        self.high = high
        self.total = 0

    def run(self):
        for a in range(self.low, self.high):
            self.total += a
    



summa = 0
t0 = time.clock()
for i in range(1000):
    t = SummingThread(1000*i, 1000*i+1000)
    t.daemon = True
    t.start()
    t.join()
    summa += t.total

print(time.clock()-t0)
print(summa)

summa = 0
t0 = time.clock()
for i in range(1000000):
    summa += i
print(time.clock()-t0)
print(summa)
