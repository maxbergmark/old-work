import queue

global q
q = queue.Queue()

global columns
global rows
columns = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i']
rows = ['1', '2', '3', '4', '5', '6', '7', '8', '9']

def getpos(player):
    for i in range(len(matrix)):
        for j in range(len(matrix[i])):
            if matrix[i][j] == player:
                return columns[j//2] + str(rows[i//2])
    else:
        return False

def checksquare(move):
    if matrix[movetocoords(move)[0]][movetocoords(move)[1]] == '0':
        return True
    else:
        return False


def validmove(player, move):
    if move[0] not in columns or move[1] not in rows:
        return False

    if abs(columns.index(getpos(player)[0])-columns.index(move[0])) + abs(rows.index(getpos(player)[1])-rows.index(move[1])) == 1:
        if matrix[int((movetocoords(move)[0] + movetocoords(getpos(player))[0]) / 2)][int((movetocoords(move)[1] + movetocoords(getpos(player))[1]) / 2)] != 'x':
            return True
    else:
        return False

def movetocoords(move):
    #e1 -> (0, 8)
    return (2*(rows.index(move[1])-1), 2*(columns.index(move[0])-1))

def win():
    for i in playerlist:
        if getpos(i)[0] == winpos[playerlist.index(i)] or getpos(i)[1] == winpos[playerlist.index(i)]:
            return i
    return False


def check(y, x, visit = [], q = queue.Queue(), l = 0):
    if matrix[y][x] == '#':
        return 'Fel'
    visit.append((y, x))
    if x == 16:
        return 'Fungerar'
    if matrix[y+1][x] != 'x' and (y+2, x) not in visit:
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


global matrix
matrix = [['0' for x in range(17)] for y in range(17)]
for i in range(17):
    print(matrix[i])

global playerlist
global playerpos
global winpos

playerlist = ['a', 'b', 'c', 'd']
playerpos = [(0, 8), (16, 8), (8, 0), (8, 16)]
winpos = ['9', '1', 'i', 'a']

players = int(input('Players: '))

playerlist = playerlist[:players]
playerpos = playerpos[:players]
print(playerlist)


for i in range(players):
    matrix[playerpos[i][0]][playerpos[i][1]] = playerlist[i]

for i in range(17):
    print(matrix[i])
    
while not win():
    locate = str(input('Player: '))
    print(getpos(locate))
    move = str(input('move: '))
    print(validmove(locate, move))
