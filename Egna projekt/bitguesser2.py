import random
import time

def checkScore(score, guess, numList, setOnes, length):

	for element in numList[:]:
		if length-calcOnes(guess^element) != int(score):
			numList.remove(element)
	#return numList

def makeNums2(length = 4):
	return [i for i in range(2**length)]

def calcOnes(setOnes, length, num):
	totOnes = 0
	while num > 0:
		totOnes += setOnes[num & 255]
		num >>= 8
	return totOnes

def makeGuess(wrongs, length, setOnes):
	if len(wrongs) == 0:
		return 0
	for i in range(wrongs[-1][0], 2**length):
		for j in range(len(wrongs)):
			if length-calcOnes(setOnes, length, i^wrongs[j][0]) == wrongs[j][1]:
				pass
			else:
				break
			if j == len(wrongs)-1:
				return i
	return -1

def getScore(guess, secret, setOnes, length):
	return length - calcOnes(setOnes, length, guess ^ secret)

def getBinary(num, length):
	binStr = "{0:b}".format(num)
	return (length-len(binStr))*'0' + binStr

def playGame(secret, length, setOnes):
	turns = 0
	wrongs = []
	guess = 0
	#print()
	while guess >= 0:
		
		turns += 1
		t0 = time.clock()
		guess = makeGuess(wrongs, length, setOnes)
		t1 = time.clock()
		score = getScore(guess, secret, setOnes, length)
		wrongs.append((guess, score))
		#print(getBinary(guess, length), getBinary(secret, length), score, len(wrongs), round(t1-t0, 3))
		if score == length:
			return turns
		#checkScore(score, guess, numList, setOnes, length)
	return turns

n = 15
t0 = time.clock()
setOnes = [bin(i).count('1') for i in range(256)]


for j in range(0, n):
	secrets = [random.randint(0,2**j-1) for i in range(100)]
	print('playing game', j+1)
	scores = [playGame(secret, j+1, setOnes) for secret in secrets]
	average = sum(scores)/len(scores)
	print(average)

t1 = time.clock()
print(t1-t0)