from tkinter import *
import winsound
import string
import random
import time
import threading
from PIL import Image, ImageTk





class App:

    def __init__(self, master, level, player, hard):

        self.frame = Frame(master)
        self.frame.grid()
        master.bind("<Key>", self.keyPressed)

        self.width = 10
        self.height = 23
        self.pixelSize = 30
        
        
        self.player = player
        self.speed = int(1000*.8**level)

        self.mainObject = None
        self.mainShape = None
        self.mainColor = None
        self.mainLeft = None
        self.mainRight = None
        self.mainCenter = None
        self.heldObject = None
        self.heldColor = None
        self.heldVirtualObject = None
        self.next = random.choice(['square', 'line', 'lBlock', 'revlBlock', 'squiggly', 'revSquiggly', 'tBlock'])
        #self.next = 'line'
        self.holdAble = True

        self.paused = True
        self.muted = False

        self.dropHard = hard

        
        self.places = [['black' for i in range(self.width)] for j in range(self.height+1)]
        self.places[-1] = self.width*['white']

        self.lines = 0
        self.score = 0
        self.level = level



        self.createGrid()
        self.spawn()
        self.gravity()
        self.playMusic()
        self.pause()

    def playMusic(self):
        #import pygame
        #pygame.init()
        
        #pygame.mixer.music.load('game.mp3')
        #print('loading music')
        #pygame.mixer.music.play(-1, 0.0)
        #print('playing music')
        if not self.muted:
            winsound.PlaySound('game1.wav', winsound.SND_ASYNC | winsound.SND_FILENAME)
            self.frame.after(77000, self.playMusic)

    def soundEffect(self):
        pass
        #winsound.PlaySound('win.wav', winsound.SND_ASYNC | winsound.SND_FILENAME)
        
    def playSelf(self):
        print()

        t0 = time.clock()
        
        if self.heldObject == None:
            
            self.holdObject()
            return
        
        matrix = self.places[:]
        firstRot = [None for i in range(4)]
        secondRot = [None for i in range(4)]
        thirdRot = [None for i in range(4)]
        fourthRot = [None for i in range(4)]
        for i in range(4):
            firstRot[i] = [int((self.mainObject[i][1] - self.mainCenter[1]) + self.mainCenter[0]), int(-(self.mainObject[i][0] - self.mainCenter[0]) + self.mainCenter[1])]
            secondRot[i] = [int((firstRot[i][1] - self.mainCenter[1]) + self.mainCenter[0]), int(-(firstRot[i][0] - self.mainCenter[0]) + self.mainCenter[1])]
            thirdRot[i] = [int((secondRot[i][1] - self.mainCenter[1]) + self.mainCenter[0]), int(-(secondRot[i][0] - self.mainCenter[0]) + self.mainCenter[1])]
            fourthRot[i] = [int((thirdRot[i][1] - self.mainCenter[1]) + self.mainCenter[0]), int(-(thirdRot[i][0] - self.mainCenter[0]) + self.mainCenter[1])]


        nextFirstRot = [None for i in range(4)]
        nextSecondRot = [None for i in range(4)]
        nextThirdRot = [None for i in range(4)]
        nextFourthRot = [None for i in range(4)]
                    
        for i in range(4):
            nextFirstRot[i] = [int((self.nextVirtualObject[i][1] - self.nextCenter[1]) + self.nextCenter[0]), int(-(self.nextVirtualObject[i][0] - self.nextCenter[0]) + self.nextCenter[1])]
            nextSecondRot[i] = [int((nextFirstRot[i][1] - self.nextCenter[1]) + self.nextCenter[0]), int(-(nextFirstRot[i][0] - self.nextCenter[0]) + self.nextCenter[1])]
            nextThirdRot[i] = [int((nextSecondRot[i][1] - self.nextCenter[1]) + self.nextCenter[0]), int(-(nextSecondRot[i][0] - self.nextCenter[0]) + self.nextCenter[1])]
            nextFourthRot[i] = [int((nextThirdRot[i][1] - self.nextCenter[1]) + self.nextCenter[0]), int(-(nextThirdRot[i][0] - self.nextCenter[0]) + self.nextCenter[1])]

        tempChecked = []


        minHeight = self.height
        minPlace = 0
        minRot = 0
        minHoles = self.height*self.width

        self.firstMinRot = [None for i in range(4)]
        self.firstMinPlace = [None for i in range(4)]


        self.nextMinHeight = [self.height for i in range(4)]
        self.nextMinPlace = [0 for i in range(4)]
        self.nextMinRot = [0 for i in range(4)]
        self.nextMinHoles = [self.height*self.width for i in range(4)]

        self.nextMinHolesFound = None
        self.nextMinHeightFound = None
        self.nextMinPlaceFound = None
        self.nextMinRotFound = None
        self.firstMinRotFound = None
        self.firstMinPlaceFound = None

        self.willHold = False
        self.checkHold = False

        waysChecked = 0

        place = 0

####
        print(self.nextMinHeight)
        threadlist = [None for i in range(4)]
        for e in range(4):
            
            translation = 0
            temp = self.translateShape([1, 0], [firstRot, secondRot, thirdRot, fourthRot][e])
            if (self.mainShape in ['line', 'squiggly', 'revSquiggly'] and e < 2) or (self.mainShape in ['square'] and e < 1) or (self.mainShape in ['lBlock', 'revlBlock', 'tBlock']):
                threadlist[e] = threading.Thread(target = self.checkBestSolution, args = (temp, e, [nextFirstRot, nextSecondRot, nextThirdRot, nextFourthRot]))
                threadlist[e].start()
                threadlist[e].join()



        for e in range(4):
            break
            if threadlist[e] != None:
                threadlist[e].join()


        self.checkLists()


######
        print(self.nextMinHeight)
        if self.heldVirtualObject != self.mainObject:

            matrix = self.places[:]
            firstRot = [None for i in range(4)]
            secondRot = [None for i in range(4)]
            thirdRot = [None for i in range(4)]
            fourthRot = [None for i in range(4)]
            for i in range(4):
                firstRot[i] = [int((self.heldVirtualObject[i][1] - self.heldCenter[1]) + self.heldCenter[0]), int(-(self.heldVirtualObject[i][0] - self.heldCenter[0]) + self.heldCenter[1])]
                secondRot[i] = [int((firstRot[i][1] - self.heldCenter[1]) + self.heldCenter[0]), int(-(firstRot[i][0] - self.heldCenter[0]) + self.heldCenter[1])]
                thirdRot[i] = [int((secondRot[i][1] - self.heldCenter[1]) + self.heldCenter[0]), int(-(secondRot[i][0] - self.heldCenter[0]) + self.heldCenter[1])]
                fourthRot[i] = [int((thirdRot[i][1] - self.heldCenter[1]) + self.heldCenter[0]), int(-(thirdRot[i][0] - self.heldCenter[0]) + self.heldCenter[1])]

        
            place = 0
            threadlistNext = [None for e in range(4)]
            self.checkHold = True
            for e in range(4):
                
                translation = 0
                temp = self.translateShape([1, 0], [firstRot, secondRot, thirdRot, fourthRot][e])
                if (self.heldObject in ['line', 'squiggly', 'revSquiggly'] and e < 2) or (self.heldObject in ['square'] and e < 1) or (self.heldObject in ['lBlock', 'revlBlock', 'tBlock']):
                    threadlistNext[e] = threading.Thread(target = self.checkBestSolution, args = (temp, e, [nextFirstRot, nextSecondRot, nextThirdRot, nextFourthRot]))
                    threadlistNext[e].start()
                    threadlistNext[e].join()

            for e in range(4):
                break
                if threadlistNext[e] != None:
                    threadlistNext[e].join()
            #if self.willHold:
            #    self.holdObject()

            self.checkLists()

        
                

        print('Found solution')
        print(self.firstMinRotFound)
        self.eraseMain()
        self.translateShape([1, 0])
        self.calcCenter()
        self.drawMain()
        for i in range(self.firstMinRotFound+1):
            self.rotateRight()
        self.eraseMain()
        for i in range(abs(self.firstMinPlaceFound)):
            if self.checkRight() and self.checkLeft():
                
                self.translateShape([0, int(self.firstMinPlaceFound/abs(self.firstMinPlaceFound))])
        self.calcCenter()
        self.drawMain()
        self.drawBoard()
        if self.dropHard:
            self.frame.update_idletasks()
            event = Event()
            event.keysym = 'space'
            self.keyPressed(event)

    def checkLists(self):
        checkHoles = self.height
        checkHeight = self.width*self.height
        for e in range(4):
            if self.nextMinHoles[e] <= checkHoles:
                if self.nextMinHeight[e] <= checkHeight:
                    checkHoles = self.nextMinHoles[e]
                    checkHeight = self.nextMinHeight[e]
                    self.nextMinHolesFound = self.nextMinHoles[e]
                    self.nextMinHeightFound = self.nextMinHeight[e]
                    self.nextMinPlaceFound = self.nextMinPlace[e]
                    self.nextMinRotFound = self.nextMinRot[e]
                    self.firstMinRotFound = self.firstMinRot[e]
                    self.firstMinPlaceFound = self.firstMinPlace[e]
                    

    def checkBestSolution(self, shape, e, nextShapeList):


        minHeight = self.height
        minPlace = 0
        minRot = 0
        minHoles = self.height*self.width



        translation = 0

        
        while self.calcLeftRight(shape)[0] > 0:
            shape = self.translateShape([0, -1], shape)
            translation -= 1
        while True:
            matrix = [e[:] for e in self.places]
            
            if not self.checkIntersect(shape[:]):
                downtemp = shape[:]
                #downtemp = self.translateShape([self.calcHeight(matrix[:]) - self.calcDown(temp)-5, 0], downtemp[:])
                #downtemp = self.translateShape([12, 0], downtemp)
                
                
                while not self.checkUnder(downtemp):

                    downtemp = self.translateShape([1, 0], downtemp)
                for i in downtemp:
                    matrix[i[0]][i[1]] = self.mainColor
                    

                    
                cLR = self.calcLeftRight(shape)
                rowedMatrix = self.removeRows(matrix[2:])
                rowedMatrix.insert(0, self.width*['black'])
                rowedMatrix.insert(0, self.width*['black'])

##


                
                for k in range(4):
                    nextTranslation = 0
                    nextTemp = self.translateShape([1, 0], nextShapeList[k])
                    if (self.next in ['line', 'squiggly', 'revSquiggly'] and k < 2) or (self.next in ['square'] and k < 1) or (self.next in ['lBlock', 'revlBlock', 'tBlock']):
                        while self.calcLeftRight(nextTemp)[0] > 0:
                            nextTemp = self.translateShape([0, -1], nextTemp)
                            nextTranslation -= 1
                        while True:
                            
                            nextMatrix = matrix[:]

                            if not self.checkIntersect(nextTemp[:]):
                                nextDownTemp = nextTemp[:]
                                while not self.checkUnder(nextDownTemp):
                                    nextDownTemp = self.translateShape([1, 0], nextDownTemp)
                                for j in nextDownTemp:
                                    nextMatrix[j[0]][j[1]] = self.nextColor
                                nextcLR = self.calcLeftRight(nextTemp)
                                nextRowedMatrix = self.removeRows(nextMatrix[2:])
                                nextRowedMatrix.insert(0, self.width*['black'])
                                nextRowedMatrix.insert(0, self.width*['black'])
                                nextTempHoles = self.calcHoles(nextRowedMatrix[:])
                                nextTempHeight = self.calcHeight(nextRowedMatrix[:])

                                if self.nextMinHoles[e] >= nextTempHoles:
                                    if self.nextMinHeight[e] >= nextTempHeight and nextcLR[0] >= 0:
                                        
                                        self.nextMinHoles[e] = nextTempHoles
                                        self.nextMinHeight[e] = nextTempHeight
                                        self.nextMinPlace[e] = nextTranslation
                                        self.nextMinRot[e] = k
                                        self.firstMinRot[e] = e
                                        self.firstMinPlace[e] = translation
                                        
                                        if self.checkHold:
                                            if self.holdAble:
                                                self.willHold = True
                                                return

                                        


                                for i in nextDownTemp:
                                    nextMatrix[i[0]][i[1]] = 'black'

                            nextTranslation += 1
                            nextTemp = self.translateShape([0, 1], nextTemp)

                            if self.calcLeftRight(nextTemp)[1] > self.width-1:
                        
                                break
                    
##


                    
                for i in downtemp:
                    matrix[i[0]][i[1]] = 'black'

            translation += 1
            shape = self.translateShape([0, 1], shape)
           

            if self.calcLeftRight(shape)[1] > self.width-1:
                
                break
            
        return (self.nextMinHoles, self.nextMinHeight, self.firstMinRot, self.firstMinPlace)


    def takeDown(self):
        event = Event()
        event.keysym = 'Down'
        self.keyPressed(event)
        self.frame.after(10, self.takeDown)


    def calcHoles2(self, matrix):
        holes = 0 
        
        for i in range(len(matrix[0])):
            first = False
            for j in range(1, len(matrix)):
                if matrix[j][i] != 'black' and not first:
                    first = True
                elif matrix[j][i] == 'black' and first:
                    holes += 1
                    
        #self.printMatrix(matrix)
        
        return holes

    def calcHoles(self, matrix):
        holes = 0
        for i in range(1, len(matrix)):
            for j in range(len(matrix[0])):
                if matrix[i][j] == 'black':
                    if j > 0 and j < len(matrix[0])-1:
                        if matrix[i][j-1] != 'black' or matrix[i][j+1] != 'black':
                            holes += 1
                    elif j > 0:
                        if matrix[i][j-1] != 'black':
                            holes += 1
                    elif j < len(matrix[0])-1:
                        if matrix[i][j+1] != 'black':
                            holes += 1
                    if i > 0 and matrix[i-1][j] != 'black':
                        holes += 1
        return holes

    def removeRows(self, matrix):
        tempMatrix = matrix[:]
        for i in matrix[:len(matrix)-1]:
                if 'black' not in i:
                    tempMatrix.remove(i)
                    tempMatrix.insert(0, self.width*['black'])

        return tempMatrix
            

    def getPartialMatrix(self, dim, matrix):
        tempMatrix = [[matrix[j+dim[0][0]][i+dim[1][0]] for i in range(dim[1][1]-dim[1][0])] for j in range(dim[0][1]-dim[0][0])]
        #self.printMatrix(tempMatrix)
        return tempMatrix
            


    def calcHeight(self, matrix):
        test = 1
        for i in matrix:
            for j in i:
                if j != 'black':
                    return len(matrix)-test
            test += 1
        return 0
            


    def printMatrix(self, matrix = None):
        print()
        if matrix == None:
            matrix = self.places
        for i in matrix:
            tempstr = ''
            for j in i:
                if j != 'black':
                    tempstr += j[0]
                else:
                    tempstr += '-'
            print(tempstr)
            


    def createGrid(self):
        self.button = [[None for i in range(self.width+9)] for j in range(self.height)]
        for i in range(self.height):
            for j in range(self.width+9):
                if j >= self.width+1  and i < 6:
                    if j == self.width+1 and i == 0:
                        
                        img = PhotoImage(file='tetristitle.gif')
                        self.button[i][j] = Label(self.frame, width = 8*self.pixelSize-4, height = 6*self.pixelSize-4, image = img)
                        self.button[i][j].photo = img
                        self.button[i][j].grid(row = i, column = j, rowspan = 6, columnspan = 8)
                elif j >= self.width+1 and j < self.width+8 and i == 13:
                    if j == self.width+1:

                        self.scoreFrame = Frame(self.frame, width = 7*self.pixelSize-4, height = self.pixelSize-4)
                        self.button[i][j] = Label(self.scoreFrame, text = 'Level: ' + str(self.level) + '     Score: ' + str(self.score), bg = 'black', fg = 'white', width = 30, height = 3, anchor = N)
                        self.button[i][j].grid(row = 0, column = 0)
                        self.scoreFrame.grid(row = i, column = j, rowspan = 1, columnspan = 7)
                        self.scoreFrame.grid_propagate(False)
                                                  
                else:
                    if j == self.width:
                        img = PhotoImage(file='tetrisdiv.gif')
                    else:
                        img = PhotoImage(file='tetrisblack.gif')
                    self.button[i][j] = Label(self.frame, width = self.pixelSize-4, height = self.pixelSize-4, image = img)
                    self.button[i][j].photo = img
                    self.button[i][j].grid(row = i, column = j)

    def gravity(self):
        if not self.paused:
            self.checkUnder()
            self.moveObject([1, 0])

        

        self.frame.after(self.speed, self.gravity)

    def pause(self):
        self.paused = not self.paused

    def rotateRight(self):
        
        temp = [None for i in range(4)]
        for i in range(4):
            temp[i] = [int((self.mainObject[i][1] - self.mainCenter[1]) + self.mainCenter[0]), int(-(self.mainObject[i][0] - self.mainCenter[0]) + self.mainCenter[1])]
        minMax = self.calcLeftRight(temp)
        if minMax[0] < 0 or minMax[1] > self.width-1 or self.checkIntersect(temp):

            rightTest = self.translateShape([0, 1], temp)
            leftTest = self.translateShape([0, -1], temp)
            right2Test = self.translateShape([0, 2], temp)
            left2Test = self.translateShape([0, -2], temp)

            if self.calcLeftRight(rightTest)[1] < self.width-1 and (not self.checkIntersect(rightTest)) and self.checkIntersect(leftTest):
                self.eraseMain()
                self.mainObject = rightTest
                self.calcLeftRight()
                if not self.checkIntersect(self.translateShape([0, -1], rightTest)):
                    self.moveObject([0, -1])
                self.drawMain()
                self.calcCenter()
                return True
            elif self.calcLeftRight(leftTest)[0] >= 0 and (not self.checkIntersect(leftTest)) and self.checkIntersect(rightTest):
                self.eraseMain()
                self.mainObject = leftTest
                if not self.checkIntersect(self.translateShape([0, 1], leftTest)):
                    self.moveObject([0, 1])
                self.calcCenter()
                self.drawMain()
                return True
            
            if self.calcLeftRight(right2Test)[1] < self.width-1 and (not self.checkIntersect(right2Test)) and self.checkIntersect(left2Test):
                self.eraseMain()
                self.mainObject = right2Test
                self.calcLeftRight()
                if not self.checkIntersect(self.translateShape([0, -1], rightTest)):
                    self.moveObject([0, -1])
                self.drawMain()
                self.calcCenter()
                return True
            elif self.calcLeftRight(left2Test)[0] >= 0 and (not self.checkIntersect(left2Test)) and self.checkIntersect(right2Test):
                self.eraseMain()
                self.mainObject = left2Test
                if not self.checkIntersect(self.translateShape([0, 1], leftTest)):
                    self.moveObject([0, 1])
                self.calcCenter()
                self.drawMain()
                return True
            else:
                return False
            
        if not self.checkIntersect(temp):
            self.eraseMain()
            for i in range(4):
                y = self.mainObject[i][0] - self.mainCenter[0]
                x = self.mainObject[i][1] - self.mainCenter[1]
                self.mainObject[i][0] = int(x + self.mainCenter[0])
                self.mainObject[i][1] = int(-y + self.mainCenter[1])

            self.drawMain()
            return True
        return False

    def translateShape(self, vector, shape = None):
        if shape != None:
            return [[shape[i][0] + vector[0], shape[i][1] + vector[1]] for i in range(4)]
        else:
            for i in range(4):
                self.mainObject[i][0] += vector[0]
                self.mainObject[i][1] += vector[1]                

    def calcCenter(self):
        y = [self.mainObject[i][0] for i in range(4)]
        x = [self.mainObject[i][1] for i in range(4)]
        if self.mainShape == 'square':
            ysum = sum(y)
            xsum = sum(x)
            self.mainCenter = [ysum/4, xsum/4]

        elif self.mainShape == 'line':
            ysum = sum(y)
            xsum = sum(x)
            if self.mainObject[0][0] == self.mainObject[1][0]:
                self.mainCenter = [ysum/4 + (int(self.mainObject[0][1] < self.mainObject[1][1])-.5), xsum/4]
            else:
                self.mainCenter = [ysum/4, xsum/4 + (int(self.mainObject[0][0] > self.mainObject[1][0])-.5)]
        elif self.mainShape == 'lBlock' or self.mainShape == 'revlBlock':
            self.mainCenter = self.mainObject[2][:]

        elif self.mainShape == 'squiggly':
            self.mainCenter = self.mainObject[3][:]
        elif self.mainShape == 'revSquiggly':
            self.mainCenter = self.mainObject[2][:]

        elif self.mainShape == 'tBlock':
            self.mainCenter = self.mainObject[2][:]
            
            


    def checkIntersect(self, shape):
        for e in shape:
            if e[1] < 0 or e[1] > self.width-1:
                return True
            if self.places[e[0]][e[1]] != 'black' and e not in self.mainObject:
                return True

        return False            
            
    def checkUnder(self, shape = None):
        if shape != None:
            for e in shape:
                if self.places[e[0]+1][e[1]] != 'black' and ([e[0]+1, e[1]] not in shape):
                    return True
            return False
        for e in self.mainObject:
            if self.places[e[0]+1][e[1]] != 'black' and ([e[0]+1, e[1]] not in self.mainObject):
                self.holdAble = True
                self.spawn()
                return True
        return False

    def checkLeft(self):
        for e in self.mainObject:
            if e[1]-1 < 0:
                return False
            if self.places[e[0]][e[1]-1] != 'black' and ([e[0], e[1]-1] not in self.mainObject):
                return False
        return True

    def checkRight(self):
        for e in self.mainObject:
            if e[1]+1 > self.width-1:
                return False
            if self.places[e[0]][e[1]+1] != 'black' and ([e[0], e[1]+1] not in self.mainObject):
                return False
        return True

    def checkLine(self):
        tempLines = 0
        for i in self.places[:len(self.places)-1]:
            check = True
            for j in i:
                if j == 'black':
                    check = False
                    break

            if check:
                self.places.remove(i)
                self.places.insert(0, self.width*['black'])
                self.lines += 1
                tempLines += 1
                #self.score += self.level*100
                #self.button[13][self.width+1]['text'] = str(self.score)
                #self.soundEffect()
                if self.lines % 10 == 0:
                    self.level += 1
                    self.speed = int(self.speed*.8)
                self.drawBoard()
        self.score += [0, 40, 100, 300, 1200][tempLines]*(self.level+1)
        self.button[13][self.width+1]['text'] = 'Level: ' + str(self.level) + '     Score: ' + str(self.score)
        if tempLines != 0:
            print(self.level, '   ', self.lines)

    def drawBoard(self):
        for i in range(len(self.places)-1):
            for j in range(self.width):
                img = PhotoImage(file='tetris' + self.places[i][j] + '.gif')
                self.button[i][j]['image'] = img
                self.button[i][j].photo = img

    def moveObject(self, vector):
        self.eraseMain()
        for i in range(4):
            self.mainObject[i][0] += vector[0]
            self.mainObject[i][1] += vector[1]
        self.mainCenter[0] += vector[0]
        self.mainCenter[1] += vector[1]
        self.drawMain()

    def hardDrop(self):
        self.eraseMain()
        tempScore = 0
        while not self.checkIntersect(self.translateShape([1, 0], self.mainObject[:])):
            self.translateShape([1, 0])
            tempScore += 2
        self.score += tempScore
        self.drawMain()
        self.holdAble = True
        self.spawn()
                
    def eraseMain(self):
        img = PhotoImage(file='tetrisblack.gif')
        for e in self.mainObject:
            self.button[e[0]][e[1]]['image'] = img
            self.button[e[0]][e[1]].photo = img
            self.places[e[0]][e[1]] = 'black'

    def drawMain(self):
        img = PhotoImage(file='tetris' + self.mainColor + '.gif')
        for e in self.mainObject:
            self.button[e[0]][e[1]]['image'] = img
            self.button[e[0]][e[1]].photo = img
            self.places[e[0]][e[1]] = self.mainColor
        self.button[0][0].update_idletasks()

    def calcLeftRight(self, shape = None):
        if shape == None:
            RLList = [self.mainObject[i][1] for i in range(4)]
        else:
            RLList = [shape[i][1] for i in range(4)]
            return [min(RLList), max(RLList)]
        self.mainLeft = min(RLList)
        self.mainRight = max(RLList)

    def calcDown(self, shape = None):
        if shape == None:
            downList = [self.mainObject[i][0] for i in range(4)]
        else:
            downList = [shape[i][0] for i in range(4)]
            return max(downList)

    def holdObject(self):

        if self.holdAble:
            willSpawn = False
            self.eraseMain()
            if self.heldObject == None:

                self.heldObject = self.mainShape
                self.heldColor = self.mainColor
                willSpawn = True

                
            else:
                temp = self.heldObject
                self.heldObject = self.mainShape
                self.heldColor = self.mainColor
                exec('self.' + temp + '()')
            #print(self.heldObject)
            if self.heldObject == 'square':

                shape = [[20, self.width+4], [20, self.width+5], [21, self.width+4], [21, self.width+5]]
                self.heldVirtualObject = [[0, 4], [0, 5], [1, 4], [1, 5]]
                self.heldCenter = [0.5, 4.5]

                color = 'yellow'
            elif self.heldObject == 'line':

                shape = [[21, self.width+3], [21, self.width+4], [21, self.width+5], [21, self.width+6]]
                color = 'cyan'
                self.heldVirtualObject = [[0, 3], [0, 4], [0, 5], [0, 6]]
                self.heldCenter = [0.5, 4.5]

            elif self.heldObject == 'lBlock':

                shape = [[21, self.width+3], [21, self.width+4], [21, self.width+5], [20, self.width+5]]
                color = 'orange'
                self.heldVirtualObject = [[0, 5], [1, 3], [1, 4], [1, 5]]
                self.heldCenter = [1, 4]

            elif self.heldObject == 'revlBlock':

                shape = [[21, self.width+3], [21, self.width+4], [21, self.width+5], [20, self.width+3]]
                color = 'blue'
                self.heldVirtualObject = [[0, 3], [1, 3], [1, 4], [1, 5]]
                self.heldCenter = [1, 4]

            elif self.heldObject == 'squiggly':

                shape = [[21, self.width+3], [21, self.width+4], [20, self.width+4], [20, self.width+5]]
                color = 'green'
                self.heldVirtualObject = [[0, 4], [0, 5], [1, 3], [1, 4]]
                self.heldCenter = [1, 4]
                
            elif self.heldObject == 'revSquiggly':
                
                shape = [[21, self.width+4], [21, self.width+5], [20, self.width+3], [20, self.width+4]]
                color = 'red'
                self.heldVirtualObject = [[0, 3], [0, 4], [1, 4], [1, 5]]
                self.heldCenter = [1, 4]
                
            elif self.heldObject == 'tBlock':
                
                shape = [[21, self.width+3], [21, self.width+4], [21, self.width+5], [20, self.width+4]]
                color = 'purple'
                self.heldVirtualObject = [[0, 4], [1, 3], [1, 4], [1, 5]]
                self.heldCenter = [1, 4]
            #print(shape, color)
            self.eraseArea([20, 21, self.width+3, self.width+6])
            self.drawObject(shape, color)
                
            self.holdAble = False
            if willSpawn:
                self.spawn()

    def checkSpawn(self):
        for e in self.mainObject:
            if self.places[e[0]+1][e[1]] != 'black':
                return False
        return True
                

    def spawn(self):

        self.checkLine()
        #self.line()
        #random.choice([self.line, self.square])()
        #random.choice([self.square, self.lBlock, self.revlBlock, self.squiggly, self.revSquiggly, self.tBlock])()
        #random.choice([self.square, self.line, self.lBlock, self.revlBlock, self.squiggly, self.revSquiggly, self.tBlock])()
        exec('self.' + self.next + '()')
        self.generateNext()
        #self.next = random.choice(['tBlock'])
        self.showNext()
        self.calcLeftRight()
        if not self.checkSpawn():
            print(self.score)
            quit()
            
        self.drawMain()
        if self.player == False:
            self.playSelf()

    def generateNext(self):
        self.next = random.choice(['square', 'line', 'lBlock', 'revlBlock', 'squiggly', 'revSquiggly', 'tBlock'])
        #self.next = random.choice(['square', 'line'])
        if self.next == 'square':
            self.nextVirtualObject = [[0, 4], [0, 5], [1, 4], [1, 5]]
            self.nextCenter = [0.5, 4.5]
        elif self.next == 'line':
            self.nextVirtualObject = [[0, 3], [0, 4], [0, 5], [0, 6]]
            self.nextCenter = [0.5, 4.5]
        elif self.next == 'lBlock':
            self.nextVirtualObject = [[0, 5], [1, 3], [1, 4], [1, 5]]
            self.nextCenter = [1, 4]
        elif self.next == 'revlBlock':
            self.nextVirtualObject = [[0, 3], [1, 3], [1, 4], [1, 5]]
            self.nextCenter = [1, 4]
        elif self.next == 'squiggly':
            self.nextVirtualObject = [[0, 4], [0, 5], [1, 3], [1, 4]]
            self.nextCenter = [1, 4]
        elif self.next == 'revSquiggly':
            self.nextVirtualObject = [[0, 3], [0, 4], [1, 4], [1, 5]]
            self.nextCenter = [1, 4]
        elif self.next == 'tBlock':
            self.nextVirtualObject = [[0, 4], [1, 3], [1, 4], [1, 5]]
            self.nextCenter = [1, 4]


    def showNext(self):
        img = PhotoImage(file='tetrisblack.gif')
        for i in range(8, 10):
            for j in range(self.width+3, self.width+7):

                self.button[i][j]['image'] = img
                self.button[i][j].photo = img
        if self.next == 'square':
            self.nextObject = [[8, self.width+4], [8, self.width+5], [9, self.width+4], [9, self.width+5]]
            self.nextColor = 'yellow'
        elif self.next == 'line':
            self.nextObject = [[9, self.width+3], [9, self.width+4], [9, self.width+5], [9, self.width+6]]
            self.nextColor = 'cyan'
        elif self.next == 'lBlock':
            self.nextObject = [[9, self.width+3], [9, self.width+4], [9, self.width+5], [8, self.width+5]]
            self.nextColor = 'orange'
        elif self.next == 'revlBlock':
            self.nextObject = [[9, self.width+3], [9, self.width+4], [9, self.width+5], [8, self.width+3]]
            self.nextColor = 'blue'
        elif self.next == 'squiggly':
            self.nextObject = [[9, self.width+3], [9, self.width+4], [8, self.width+4], [8, self.width+5]]
            self.nextColor = 'green'
        elif self.next == 'revSquiggly':
            self.nextObject = [[9, self.width+4], [9, self.width+5], [8, self.width+3], [8, self.width+4]]
            self.nextColor = 'red'
        elif self.next == 'tBlock':
            self.nextObject = [[9, self.width+3], [9, self.width+4], [9, self.width+5], [8, self.width+4]]
            self.nextColor = 'purple'
        self.drawObject(self.nextObject, self.nextColor)

    def drawObject(self, shape, color):
        img = PhotoImage(file='tetris' + color + '.gif')
        for e in shape:
            self.button[e[0]][e[1]]['image'] = img
            self.button[e[0]][e[1]].photo = img

    def eraseArea(self, area):
        img = PhotoImage(file='tetrisblack.gif')
        for i in range(area[0], area[1]+1):
            for j in range(area[2], area[3]+1):
                self.button[i][j]['image'] = img
                self.button[i][j].photo = img


    def square(self):
        self.mainObject = [[0, 4], [0, 5], [1, 4], [1, 5]]
        self.mainCenter = [0.5, 4.5]
        self.mainColor = 'yellow'
        self.mainShape = 'square'


    def line(self):
        self.mainObject = [[0, 3], [0, 4], [0, 5], [0, 6]]
        self.mainCenter = [0.5, 4.5]
        self.mainColor = 'cyan'
        self.mainShape = 'line'


    def lBlock(self):
        self.mainObject = [[0, 5], [1, 3], [1, 4], [1, 5]]
        self.mainCenter = [1, 4]
        self.mainColor = 'orange'
        self.mainShape = 'lBlock'

    def revlBlock(self):
        self.mainObject = [[0, 3], [1, 3], [1, 4], [1, 5]]
        self.mainCenter = [1, 4]
        self.mainColor = 'blue'
        self.mainShape = 'revlBlock'

    def squiggly(self):
        self.mainObject = [[0, 4], [0, 5], [1, 3], [1, 4]]
        self.mainCenter = [1, 4]
        self.mainColor = 'green'
        self.mainShape = 'squiggly'

    def revSquiggly(self):
        self.mainObject = [[0, 3], [0, 4], [1, 4], [1, 5]]
        self.mainCenter = [1, 4]
        self.mainColor = 'red'
        self.mainShape = 'revSquiggly'

    def tBlock(self):
        self.mainObject = [[0, 4], [1, 3], [1, 4], [1, 5]]
        self.mainCenter = [1, 4]
        self.mainColor = 'purple'
        self.mainShape = 'tBlock'


    def keyPressed(self, event):
        if not self.paused:
            if (event.keysym == 'Up'):
                self.rotateRight()
            elif (event.keysym == 'Down'):
                self.checkUnder()
                self.moveObject([1, 0])
                self.score += 1
                self.button[13][self.width+1]['text'] = 'Level: ' + str(self.level) + '     Score: ' + str(self.score)
            elif (event.keysym == 'Left'):
                if self.mainLeft > 0 and self.checkLeft():
                    self.moveObject([0, -1])
            elif (event.keysym == 'Right'):
                if self.mainRight < self.width-1 and self.checkRight():
                    self.moveObject([0, 1])
            elif (event.keysym == 'x'):
                self.holdObject()
            elif (event.keysym == 'space'):
                self.hardDrop()
                self.button[13][self.width+1]['text'] = 'Level: ' + str(self.level) + '     Score: ' + str(self.score)
            self.calcLeftRight()
        if (event.keysym == 'p'):
            self.pause()
        if (event.keysym == 'm'):
            self.muted = not self.muted
            if self.muted:
                winsound.PlaySound(None, winsound.SND_ASYNC)
            else:
                self.playMusic()

#player = str(input('Player(y/n): '))
player = str(input('Do you wish to play?(y/n)  '))
level = int(input('Level: '))
player = (player == 'y')
hard = False
if not player:
    hard = str(input('Hard Drop?(y/n)  '))

hard = (hard == 'y')

root = Tk()
root.title('Tetris')

app = App(root, level, player, hard)
if level <= 30:
    root.iconify()
    root.update()
    root.deiconify()

root.mainloop()
