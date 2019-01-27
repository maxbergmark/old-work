import random


def checkScore(score, guess, numList):
	tempnumList = []
	for element in numList:
		equalCount = 0
		wrongCount = 0
		for i in range(len(guess)):
			if guess[i] == element[i]:
				equalCount += 1
			elif guess[i] != element[i] and guess[i] in element:
				wrongCount += 1
		if (True and equalCount == int(score[0])) and wrongCount == int(score[1]):
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
		elif guess[i] != secret[i] and guess[i] in secret:
			wrongCount += 1
	return (equalCount, wrongCount)


def playGame(secret, numList):
	turns = 0
	#print()
	while len(numList) > 0:
		turns += 1
		guess = makeGuess(numList)
		
		score = getScore(guess, secret)
		#print(guess, score)
		if score[0] == len(secret):
			return turns
		numList = checkScore(score, guess, numList)
	return turns

numList = makeNums2(5)
#print(numList)

secrets = [random.choice(numList) for i in range(100)]
print('playing game')
scores = [playGame(secret, numList[:]) for secret in secrets]
average = sum(scores)/len(scores)

print(average)