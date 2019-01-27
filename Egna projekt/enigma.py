import random
import time
from tkinter import Tk as r


def decrypt(string, rot1, rot2, rot3, shuffle, slist):
    alpha = slist
    kryp = slist

    for i in range(int(0.5*len(shuffle))):
        kryp[int(shuffle[i])], kryp[int(shuffle[int(i+0.5*len(shuffle))])] = kryp[int(shuffle[int(i+0.5*len(shuffle))])], kryp[int(shuffle[i])]

    lista = string.split()
    retstring = ''
    for elem in lista:
        
        for lett in range(len(elem)):
            
            retstring += alpha[(kryp.index(elem[lett]) - (rot1**rot2+rot2**rot3+rot3**rot1)) % len(slist)]
            rot1 += 1
            if rot1 == len(slist):
                rot1 = 0
                rot2 += 1
                if rot2 == len(slist):
                    rot2 = 0
                    rot3 += 1
                    if rot3 == len(slist):
                        rot3 = 0

        retstring += ' '

    retstring.strip()
    return retstring



def crypt(string, sec, slist):
    mening = string.split()
    retstring = ''

    alpha = slist
    kryp = slist

    shuffle = random.sample(range(0, len(slist)), 2*sec)

    for i in range(sec):
        kryp[shuffle[i]], kryp[shuffle[i+sec]] = kryp[shuffle[i+sec]], kryp[shuffle[i]]
    
    rot1 = random.randint(0, len(slist)-1)
    rot2 = random.randint(0, len(slist)-1)
    rot3 = random.randint(0, len(slist)-1)
    rot1o = rot1
    rot2o = rot2
    rot3o = rot3
    
    for elem in mening:
        
        for lett in range(len(elem)):
            retstring += kryp[(alpha.index(elem[lett]) + rot1**rot2+rot2**rot3+rot3**rot1) % len(slist)]
            rot1 += 1
            if rot1 == len(slist):
                rot1 = 0
                rot2 += 1
                if rot2 == len(slist):
                    rot2 = 0
                    rot3 += 1
                    if rot3 == len(slist):
                        rot3 = 0
        retstring += ' '
    
    return [retstring.strip(), rot1o, rot2o, rot3o, shuffle]


#slist = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'å', 'ä', 'ö', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'Å', 'Ä', 'Ö', '.', ',', '?', '!', '-', '_', '@', '/', ':', ';', '+', '(', ')', "'", '"']
slist = []
for i in range(ord('A'), ord('Z')):
    slist += chr(i)
for i in range(ord('a'), ord('z')):
    slist += chr(i)

num = int(input("1 encrypt\n2 decrypt\n3 exit\n")) 
print()
if num != 1 and num != 2:
    print("bye")
    quit()
elif num == 1:
    string = str(input("text: "))
    sec = int(input("security: "))
    result = crypt(string, sec, slist)
    r.clipboard_append(r(), str(result[0]))
    print(result[0])
    print(result[1:4])
    print(result[4])
elif num == 2:
    string = str(input("text: "))
    rawinp = str(input("cryptolist: "))
    shuffle = rawinp.split()
    rotinp = str(input("rotlist: "))
    rotlist = rotinp.split()
    rot1 = int(rotlist[0])
    rot2 = int(rotlist[1])
    rot3 = int(rotlist[2])
    print(decrypt(string, rot1, rot2, rot3, shuffle, slist))
