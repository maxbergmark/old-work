import string

def dropdown(n, player):
    
    global grid
    if grid[0][n] != ' ':
        return False
    else:
        for i in range(len(grid)-1):
            if grid[i+1][n] != ' ':
                grid[i][n] = str(player)
                
                return None
        grid[len(grid)-1][n] = str(player)
        return None
        
def drawfield():
    global grid
    print(' '+string.digits[1:len(grid[0])+1])
    for i in range(len(grid)):
        print(str(i+1)+''.join(grid[i]))
    return None

def checkwin():
    for row in range(len(grid)):
        for start in range(len(grid[0])-3):
            if grid[row][start] != ' ' and len(set(grid[row][start:start+4])) == 1:
                return True
            
    for column in range(len(grid[0])):
        for start in range(len(grid)-3):
            if grid[start][column] == grid[start+1][column] and grid[start][column] == grid[start+2][column] and grid[start][column] == grid[start+3][column] and grid[start][column] != ' ':
                return True



    return False


global grid
grid = [[' ' for i in range(7)] for j in range(6)]
players = int(input('Players: '))
playerlist = list(range(1, players+1))
index = 0


while not checkwin():
    drawfield()
    move = int(input('choose: '))
    if move == 13:
        print(grid)
    dropdown(move-1, playerlist[index])
    index = (index+1) % players
    
