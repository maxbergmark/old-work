import random
import string
import sys
import time
import itertools

global number
number = 2

global field
field = [0 for x in range(4)]

global hand
hand = []

global operations
operations = ['+', '-', '/', '*']

def show():
    print()
    print('  Field: ')
    print()
    print('   ', field[0], field[1])
    print('   ', field[2], field[3])
    print()
    print('  Hand: ')
    print()
    print('   ', hand)
    print()
    return

def choose(choicelist):
    global field
    global hand
    
    if choicelist == []:
        return None
    
    for element in choicelist:
        print(choicelist.index(element)+1, ' '+element[0]+element[1]+element[2]+'='+element[3])

    choice = int(input())
    choice -= 1
    field[field.index(int(choicelist[choice][0]))] = 0
    field[field.index(int(choicelist[choice][2]))] = 0
    hand.remove(int(choicelist[choice][3]))
    

def addhand(value):
    global hand
    hand.append(value)

def addfield(value):
    global field
    if 0 not in field:
        return False
    for element in range(len(field)):
        if field[element] == 0:
            field[element] = value
            break
    return False
            
def checkfield():
    global field
    global hand
    if 0 in field:
        return False
    correct = []
    iters = list(itertools.permutations(field, number))
    opers = list(itertools.permutations(operations, number-1))
    for cards in iters:
        for operation in operations:
            for card in hand:
                if card == eval(str(cards[0])+operation+str(cards[1])):
                    correct.append((str(cards[0]), operation, str(cards[1]), str(card)))

    return choose(list(set(correct)))    

def removefield(value):
    global field
    if value in field:
        field[field.index(value)] = 0
    else:
        return False

funclist = [exit, addhand, addfield, removefield, checkfield, show]
print()
while True:
    choice = int(input('1  add to hand\n2  add to field\n3  remove from field\n4  check\n5  show board\n'))
    if choice >= 1 and choice <= 3:
        print()
        value = int(input('  Value: '))
        print()
        funclist[choice](value)
    elif choice == 4:
        print()
        funclist[choice]()
        print()
    elif choice == 5:
        funclist[choice]()
    else:
        exit()

    
