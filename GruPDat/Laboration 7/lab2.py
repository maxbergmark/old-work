from mynode import Node, Queue
#from myqueue import Queue
import time


mening = input("Skriv en mening: ")
start = time.clock()
myq = Queue()
for ordet in mening.split():    # dela upp meningen i ord
    myq.put(ordet)              # och sätt in varje ord i kön


while not myq.isempty():        # alla köns element
    
    print(myq.get())            # skrivs ut
    
print()                         # tom rad
print(myq.get())                # None skrivs ut eftersom kön är tom
print(time.clock() - start)
