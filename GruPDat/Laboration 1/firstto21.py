
# First to 21 by Henrik Eriksson 1987
# Adapted to Python3 by Ann Bengtson 2010

import time

print ("First to 21 is the winner,")
i=0
while i<21:
    print("you may say",i+1,"or",i+2)
    j=int(input("You say "))
    if j<i+1 or j>i+2:
        print ("You cheated,",)
    else:
        print ("I must say",j+1,"or",j+2,"hmmm...")
        time.sleep(2)
        i=i+3          
        print ("I say",i,)
print ("and I am the winner!")
print ("Had you won, the prize was a golden ruby laser!!")
