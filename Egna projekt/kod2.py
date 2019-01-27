from queue import *




def check(y, x, visit = [], q = Queue()):
    if matrix[y][x] == '#':
        return 'Fel'
    visit.append((y, x))
    if x == 0 or x == 29 or y == 0 or y == 19:
        return 'Fungerar'
    if matrix[y+1][x] == '-' and (y+1, x) not in visit:
        q.put(check(y+1, x, visit, q))
        
    if matrix[y-1][x] == '-' and (y-1, x) not in visit:
        q.put(check(y-1, x, visit, q))
        
    if matrix[y][x+1] == '-' and (y, x+1) not in visit:
        q.put(check(y, x+1, visit, q))
        
    if matrix[y][x-1] == '-' and (y, x-1) not in visit:
        q.put(check(y, x-1, visit, q))
        
    elif q.empty():
        return 'Empty'
    return q.get()



string = '##############################\n\
####------###------########---\n\
#----####-####-###----##----##\n\
####-###-------######-##-##-##\n\
#--#-#########-######-##-##-##\n\
##-----####-------###-----####\n\
######----####-##-############\n\
#########-#----##-####------##\n\
##------#-#######-#-#####-#-##\n\
#######-#-###----##-##-##-#--#\n\
----#-#-#-###-#####-##----##-#\n\
###-#-#---#########-##-#####-#\n\
###-#-#-#----###-------#####-#\n\
#-------####-###-##-##----####\n\
###-###-####-----##-##-###-###\n\
###-###-####-######-######---#\n\
###-#####----###-----#####-###\n\
#-----######-########---------\n\
#####-###---------#####-##-###\n\
#####-###########-############'

global q
q = Queue()

lista = string.split('\n')
#print(lista)
global matrix
matrix = [list(x) for x in lista]
#print(lista)

while True:
    y = int(input('rad: '))
    x = int(input('kolumn: '))
    global temp
    temp = [(y, x)]
    print(check(y, x))
print(check(1, 5))
