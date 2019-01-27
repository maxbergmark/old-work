import random

wins = 0
n = 1000000

for i in range(n):
	reds = 26
	blacks = 26
	thresh = 1
	picknext = False
	lastcard = -1
	for i in range(52):

		r = random.random()
		if (r < reds / (reds+blacks)):
			lastcard = 0
			reds -= 1
		else:
			lastcard = 1
			blacks -= 1
		if (i == 51 or picknext):
			if (lastcard == 0):
				wins += 1
			break
		if (reds - blacks > thresh):
			picknext = True

print(wins/n)