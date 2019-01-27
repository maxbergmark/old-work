import random
import time


def checkScore(score, guess, numList):
	tempnumList = []
	for element in numList:
		equalCount = 0
		wrongCount = 0
		for i in range(len(guess)):
			if guess[i] == element[i]:
				equalCount += 1

		if equalCount == int(score):
			tempnumList.append(element)

	return tempnumList




def makeNums2(length = 4):
    if length == 0:
        return ['']
    lista = ['01'[i]+rest for i in range(2) for rest in makeNums2(length-1)]
    return lista


def makeGuess(numList):
	return random.choice(numList)

def getScore(guess, secret):
	equalCount = 0
	wrongCount = 0
	for i in range(len(secret)):
		if guess[i] == secret[i]:
			equalCount += 1
	return equalCount


def playGame(secret, numList):
	turns = 0
	#print()
	while len(numList) > 0:
		turns += 1
		guess = makeGuess(numList)
		
		score = getScore(guess, secret)
		#print(guess, score)
		if score == len(secret):
			return turns
		numList = checkScore(score, guess, numList)
	return turns
n = 15
t0 = time.clock()
numLists = [makeNums2(i) for i in range(1, n+1)]
#print(numList)
for j in range(n):
	secrets = [random.choice(numLists[j]) for i in range(10)]
	print('playing game')
	scores = [playGame(secret, numLists[j][:]) for secret in secrets]
	average = sum(scores)/len(scores)

	print(average)

t1 = time.clock()
print(t1-t0)