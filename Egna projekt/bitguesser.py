import random
import time

def checkScore(score, guess, numList, setOnes, length):
	tempnumList = []
	for element in numList:
		if length-setOnes[guess^element] == int(score):
			tempnumList.append(element)
	return tempnumList

def makeNums2(length = 4):
	return [i for i in range(2**length)]

def makeGuess(numList):
	#return numList[0]
	return random.choice(numList)

def getScore(guess, secret, setOnes, length):
	return length - setOnes[guess ^ secret]

def playGame(secret, numList, length, setOnes):
	turns = 0
	#print()
	while len(numList) > 0:

		turns += 1
		guess = makeGuess(numList)

		score = getScore(guess, secret, setOnes, length)
		#print("{0:b}".format(guess), "{0:b}".format(secret), score)
		if score == length:
			return turns
		numList = checkScore(score, guess, numList, setOnes, length)
	return turns

n = 25
t0 = time.clock()
setOnes = [bin(i).count('1') for i in range(2**n)]
numLists = [makeNums2(i) for i in range(0, n+1)]

for j in range(n):
	secrets = [random.choice(numLists[j]) for i in range(10)]
	print('playing game', j+1)
	scores = [playGame(secret, numLists[j][:], 20+j+1, setOnes) for secret in secrets]
	average = sum(scores)/len(scores)
	print(average)

t1 = time.clock()
print(t1-t0)