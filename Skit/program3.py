import random

while 1:
    output = random.randint(0, 1)
    outputstring = str(output)
    for i in range(1, 120*50+119):
        rand = random.randint(0, 1)
        randstring = str(rand)
        outputstring += randstring

    print(outputstring)
        
