


def analyze(dices, counts, temp):
	maxvals = [0 for _ in range(6)]
	maxdice = 0
	tempcount = 0
	for i in range(6):
		maxvals[dices[i]] += 1
		if maxvals[dices[i]] > maxdice:
			maxdice = maxvals[dices[i]]

	for i in range(6):
		if maxvals[i] == maxdice:
			tempcount += 1
	counts[tempcount-1] += 1
	check = [0,0,0,0,2,4]
	if sorted(maxvals) == check:
		temp += 1
	return temp




counts = [0 for i in range(6)]
temp = 0

for d1 in range(6):
	for d2 in range(6):
		for d3 in range(6):
			for d4 in range(6):
				for d5 in range(6):
					for d6 in range(6):
						temp = analyze([d1,d2,d3,d4,d5,d6],counts,temp)

print(counts)
print(sum(counts))
print([round(100*i/sum(counts),4) for i in counts])
print(temp)
