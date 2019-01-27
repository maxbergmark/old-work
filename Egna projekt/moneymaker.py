


def calcTime(amount, goal, rate):
	if amount < goal:
		return (goal-amount)/rate
	return 0





inc = 1.3
names = ['curse jar', 'card collection', 'sons college fund', 'government bonds', 'wine collection', 'money printer', 'timeshares', 'pizza franchaise', 'condo development', 'film production', 'pharmaceuticals', 'ponzi scheme', 'startup incubator', 'microloans', 'utility monopoly', 'carbon credit', 'peer to peer lending', 'pneumatic tube transportation', 'time machine technology']
prices = [14600, 217100, 32.1e6, 47.1e3, 136.2e6, 2.1e6, 3.8e6, 20.7e6, 504.4e6, 1.6e9, 376e9, 6.1e12, 3.1e12, 18.9e12, 96.5e12, 352.7e12, 1e15, 25e15, 400e15]
pays = [1, 2, 5, 16, 50, 200, 800, 3e3, 10.5e3, 28e3, 75e3, 2.2e6, 10e6, 300e6, 15.2e9, 60.2e9, 100e9, 15e12, 2e15]
levels = [0 for i in range(19)]

goal = 2e15
rate = 396.4e9
time = 0


while rate*100 < goal:
	times = [1e20 for i in range(19)]
	for i in range(13, 19):
		price = prices[i]*inc**levels[i]
		times[i] = calcTime(0, price, rate) + calcTime(0, goal, rate+pays[i])
	minTime = times.index(min(times))
	time += calcTime(0, prices[minTime]*inc**levels[minTime], rate)
	rate += pays[minTime]
	levels[minTime] += 1
	print(names[minTime], rate, rate/goal, time)
	input()


print(time)