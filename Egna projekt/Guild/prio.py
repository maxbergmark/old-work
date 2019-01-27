










def getSort(obj):
	if (obj[3] <= obj[1]):
		return 0
	return (obj[1]+1)*obj[2]*(obj[4]**0.3)


def printList(priolist, name):
	print('\n   This is the', name + ':', '\n')
	for player in priolist:
		print('    ', player[0], round(getSort(player), 2))

def printOthers(priolist, name):
	print('\n   These is the', name + ':', '\n')
	for player in priolist:
		print('    ', player)
	print()

tokens = {}
tokens['vanquisher'] = ['dk', 'mage', 'druid', 'rogue']
tokens['protector'] = ['hunter', 'monk', 'shaman', 'warrior']
tokens['conqueror'] = ['paladin', 'priest', 'warlock']


classes = ['Warrior', 'Paladin', 'Hunter', 'Rogue', 'Priest', 'Death Knight', 'Shaman', 'Mage', 'Warlock', 'Monk', 'Druid']

players = {}
players['warrior'] = {'Teríth': (0, 'prot', 2), 'Azeman': (1, '2Hfury', 3), 'Harshel': (0, '2Hfury', 2)}
players['paladin'] = {'Güardian': (0, 'retri', 1), 'Ligger': (0, 'holy', 2), 'Zinqier': (0, 'holy', 3)}
players['hunter'] = {'Hönan': (0, 'bm', 2), 'Pniece': (0, 'mm', 1), 'Maethel': (0, 'bm', 1), 'Kalashnikow': (0, 'bm', 1)}
players['rogue'] = {'Lokthak': (0, 'ass', 2), 'Xén': (0, 'ass', 2), 'Quimera': (0, 'ass', 1)}
players['priest'] = {'Venerated': (0, 'disc', 2)}
players['dk'] = {'Snakso': (0, 'blood', 3), 'Kimáhri': (4, '1Hfrost', 3)}
players['shaman'] = {'Molak': (0, 'enhancement', 1)}
players['mage'] = {'Havocwreaker': (0, 'arcane', 3), 'Zoranda': (0, 'arcane', 1)}
players['warlock'] = {}
players['monk'] = {'Shilou': (0, '2Hww', 1), 'Xaap': (0, '2Hww', 1)}
players['druid'] = {'Khrubby': (0, 'resto', 3), 'Cooleja': (0, 'feral', 2), 'Willferâl': (0, 'balance', 2)}

upgrades = {}
upgrades['warrior'] = {'arms': [1.29, 9.22, 10.63], '1Hfury': [2.97, 4.23, 7.32], '2Hfury': [3.10, 4.59, 7.83], 'gladiator': [9.58, 2.45, 12.26]}
upgrades['paladin'] = {'retri': [6.73, 7.94, 15.21]}
upgrades['hunter'] = {'mm': [2.5, 11.01, 13.78], 'bm': [4.42, 8.94, 13.76], 'survival': [6.20, 2.88, 9.26]} 
upgrades['rogue'] = {'ass': [4.45, 8.08, 12.89], 'combat': [6.99, 5.95, 13.35], 'sublety': [2.80, 5.15, 8.09]}
upgrades['priest'] = {'shadow': [1.97, 5.32, 7.39]} 
upgrades['dk'] = {'1Hfrost': [5.62, 12.29, 18.21], '2Hfrost': [6.76, 11.00, 18.50], 'unholy': [0.42, 13.27, 13.75]} 
upgrades['shaman'] = {'enhancement': [5.58, 11.10, 17.30], 'elemental': [4.74, 7.99, 13.11]} 
upgrades['mage'] = {'arcane': [11.74, 5.35, 17.72], 'fire': [5.91, 5.49, 11.73], 'frost': [6.00, 2.82, 8.99]} 
upgrades['warlock'] = {'affliction': [14.10, 8.39, 23.67], 'demonology': [5.73, 5.92, 11.99], 'destruction': [7.69, 5.57, 13.69]}
upgrades['monk'] = {'1Hww': [9.68, 5.35, 15.55], '2Hww': [9.52, 5.49, 15.54]}
upgrades['druid'] = {'feral': [7.07, 9.59, 17.33], 'balance': [10.25, 6.10, 16.97]}

for token in sorted(tokens.keys()):
	twosetPrio = []
	foursetPrio = []
	twofoursetPrio = []
	others = []
	for classes in tokens[token]:
		for player in players[classes].keys():
			specc = players[classes][player][1]
			#print(player, classes, specc)
			if (players[classes][player][1] in upgrades[classes].keys()):
				prio = players[classes][player][2]
				twosetPrio.append((player, players[classes][player][0], upgrades[classes][specc][0], 2, prio))
				twofoursetPrio.append((player, players[classes][player][0], upgrades[classes][specc][1], 4, prio))
				foursetPrio.append((player, players[classes][player][0], upgrades[classes][specc][2], 4, prio))
			else:
				others.append(player)

	sort2set = sorted(twosetPrio, key = getSort, reverse = True) 
	sort24set = sorted(twofoursetPrio, key = getSort, reverse = True)
	sort4set = sorted(foursetPrio, key = getSort, reverse = True)

	print(token[:1].upper() + token[1:])
	printList(sort2set, 'two set prio')
	printList(sort4set, 'four set prio')
	printOthers(others, 'other players')
