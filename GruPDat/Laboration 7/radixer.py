from mynode import Node, Queue
from random import randint

hinkar = [Queue() for i in range(10)]


numbers = Queue()

n = 7
m = 100

for x in range(m):
    numbers.put(randint(1, 10**n))
    
for digit in range(1, n+1):
   
    while not numbers.isempty():
        number = numbers.get()
        hinkar[int(number%(10**digit)//(10**(digit-1)))].put(number)
    for hink in hinkar:
        while not hink.isempty():
            numbers.put(hink.get())

prev = 0

while not numbers.isempty():
    test = numbers.get()
    if test < prev:
        print('Not sorted!')
        break
    print(test)
    prev = test


