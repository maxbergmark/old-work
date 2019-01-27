import time



class Card:

	def __init__(self, string):
		self.suit = string[1]
		self.value = ['2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'].index(string[0])

class Hand:

	def __init__(self, string):
		self.string = string
		self.cards = [Card(i) for i in string.split()]
		self.tests = [highCard, checkPair, check2Pair, check3Pair, checkStraight, checkFlush, checkHouse, check4Kind]
		self.checkHand()


	def __repr__(self):
		return self.string

	def checkHand(self):
		self.checks = [check(self) for check in self.tests]
		self.checks.extend([False, False])
		if self.checks[4] and self.checks[5]:
			self.checks[8] = True
			if self.checks[4] == 12:
				self.checks[9] = True
		self.checks.reverse()

class Game:

	def __init__(self, string):
		self.hands = [None, None]
		self.hands[0] = Hand(string[:14])
		self.hands[1] = Hand(string[15:])

def highCard(hand):
	return sorted([i.value for i in hand.cards], reverse = True)

def checkPair(hand):
	temp = [i.value for i in hand.cards]
	if len(set(temp)) == 4:
		for card in temp:
			if temp.count(card) == 2:
				return card
	return False

def check2Pair(hand):
	temp = [i.value for i in hand.cards]
	tempfirst = None
	if len(set(temp)) == 3:
		for card in temp:
			if temp.count(card) == 2:
				if tempfirst == None:
					tempfirst = card
				if tempfirst != card:
					return (tempfirst, card)
	return False

def check3Pair(hand):
	temp = [i.value for i in hand.cards]
	if len(set(temp)) == 3:
		for card in temp:
			if temp.count(card) == 3:
				return card
	return False

def checkStraight(hand):
	temp = sorted([i.value for i in hand.cards])
	dif = [j-i for i, j in zip(temp[:-1], temp[1:])]
	if dif == [1, 1, 1, 1]:
		return temp[-1]
	elif temp == [0, 1, 2, 3, 12]:
		return 3
	return False


def checkFlush(hand):
	temp = [i.suit for i in hand.cards]
	if len(set(temp)) == 1:
		return temp[0]
	return False

def checkHouse(hand):
	temp = [i.value for i in hand.cards]
	tempfirst = None
	tempsecond = None
	if len(set(temp)) == 2:
		for card in temp:
			if temp.count(card) == 2:
				tempfirst = card
			if temp.count(card) == 3:
				tempsecond = card
		if tempfirst and tempsecond:
			return (tempfirst, tempsecond)

	return False

def check4Kind(hand):
	temp = [i.value for i in hand.cards]
	if len(set(temp)) == 2:
		for card in temp:
			if temp.count(card) == 4:
				return card
	return False

def decideWinner(game):
	for i in range(10):
		temp1 = game.hands[0].checks[i]
		temp2 = game.hands[1].checks[i]
		if temp1 and not temp2:
			return True
		elif temp2 and not temp1:
			return False
		elif temp1 and temp2:
			if i in [2, 5, 6, 8]:
				if temp1 > temp2:
					return True
				elif temp1 < temp2:
					return False
			if i in [3, 7]:
				if max(temp1) > max(temp2):
					return True
				elif max(temp2) > max(temp1):
					return False
				else:
					if min(temp1) > min(temp2):
						return True
					elif min(temp2) > min(temp1):
						return False
			if i == 9:
				for j in range(5):
					if temp1[j] > temp2[j]:
						return True
					elif temp2[j] > temp1[j]:
						return False

	return False

t = time.clock()

hands = open('p054_poker.txt', 'r').read().strip('\n ')

games = [Game(game) for game in hands.split('\n')]



count = 0
for game in games:
	count += (decideWinner(game))
	#print('  '.join([hand.string for hand in game.hands]), decideWinner(game))

print(time.clock()-t)
print(count)
