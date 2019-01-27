import pickle

level = [[{'status': True, 'action': None, 'img': 'testimg.gif'}for i in range(10)] for j in range(15)]
for i in range(len(level)):
	for j in range(len(level[0])):
		if i == 0 or j == 0 or i == len(level)-1 or j == len(level[0])-1:
			level[i][j] = {'status': False, 'action': None, 'img': 'block.gif'}
level[5][5] = {'status': True, 'action': {'command': 'level', 'value': 'level2', 'pos': [5, 5]}, 'img': 'door.gif'}

with open('level1.pickle', 'wb') as b:
	pickle.dump(level, b)
levelData = {'block.gif': 'block.gif', 'testimg.gif': 'testimg.gif', 'door.gif': 'door.gif', 'house.gif': 'house.gif', 'ashhouse.gif': 'ashhouse.gif'}
with open('level1Data.pickle', 'wb') as b:
	pickle.dump(levelData, b)


level = [[{'status': True, 'action': None, 'img': 'testimg.gif'}for i in range(20)] for j in range(30)]
for i in range(len(level)):
	for j in range(len(level[0])):
		if i == 0 or j == 0 or i == len(level)-1 or j == len(level[0])-1:
			level[i][j] = {'status': False, 'action': None, 'img': 'block.gif'}
level[8][8] = {'status': True, 'action': {'command': 'level', 'value': 'level1', 'pos': [5, 8]}, 'img': 'door.gif'}
for i in range(15, 20):
	for j in range(10, 13):
		level[i][j] = {'status': False, 'action': None, 'img': 'trans.gif'}
level[15][10] = {'status': False, 'action': None, 'img': 'ashhouse.gif'}
level[17][12] = {'status': True, 'action': {'command': 'level', 'value': 'level3', 'pos': [4, 7]}, 'img': 'trans.gif'}

with open('level2.pickle', 'wb') as b:
	pickle.dump(level, b)
levelData = {'block.gif': 'block.gif', 'testimg.gif': 'testimg.gif', 'door.gif': 'door.gif', 'house.gif': 'house.gif', 'ashhouse.gif': 'ashhouse.gif'}
with open('level2Data.pickle', 'wb') as b:
	pickle.dump(levelData, b)





level = [[{'status': True, 'action': None, 'img': 'trans.gif'}for i in range(9)] for j in range(10)]
for i in range(len(level)):
	for j in range(len(level[0])):
		if i == 0 or j == 0 or i == len(level)-1 or j == len(level[0])-1:
			level[i][j] = {'status': False, 'action': None, 'img': 'trans.gif'}
level[1][0] = {'status': False, 'action': None, 'img': 'ashbottomfloor.gif'}
level[3][8] = {'status': True, 'action': {'command': 'level', 'value': 'level2', 'pos': [17, 13]}, 'img': 'trans.gif'}
level[4][8] = {'status': True, 'action': {'command': 'level', 'value': 'level2', 'pos': [17, 13]}, 'img': 'trans.gif'}
level[4][5] = {'status': False, 'action': None, 'img': 'trans.gif'}
level[5][5] = {'status': False, 'action': None, 'img': 'trans.gif'}
level[4][4] = {'status': False, 'action': None, 'img': 'trans.gif'}
level[5][4] = {'status': False, 'action': None, 'img': 'trans.gif'}
level[6][4] = {'status': False, 'action': None, 'img': 'trans.gif'}
level[4][1] = {'status': False, 'action': None, 'img': 'trans.gif'}
level[1][1] = {'status': False, 'action': None, 'img': 'trans.gif'}
level[2][1] = {'status': False, 'action': None, 'img': 'trans.gif'}
level[8][1] = {'status': True, 'action': {'command': 'level', 'value': 'level4', 'pos': [8, 1]}, 'img': 'trans.gif'}

with open('level3.pickle', 'wb') as b:
	pickle.dump(level, b)
levelData = {'block.gif': 'block.gif', 'testimg.gif': 'testimg.gif', 'door.gif': 'door.gif', 'house.gif': 'house.gif', 'ashhouse.gif': 'ashhouse.gif', 'ashbottomfloor.gif': 'ashbottomfloor.gif'}
with open('level3Data.pickle', 'wb') as b:
	pickle.dump(levelData, b)

level = [[{'status': True, 'action': None, 'img': 'trans.gif'}for i in range(9)] for j in range(10)]
for i in range(len(level)):
	for j in range(len(level[0])):
		if i == 0 or j == 0 or i == len(level)-1 or j == len(level[0])-1:
			level[i][j] = {'status': False, 'action': None, 'img': 'trans.gif'}
level[1][0] = {'status': False, 'action': None, 'img': 'ashupperfloor.gif'}
level[8][1] = {'status': True, 'action': {'command': 'level', 'value': 'level3', 'pos': [8, 1]}, 'img': 'trans.gif'}
level[4][5] = {'status': False, 'action': None, 'img': 'trans.gif'}
level[4][4] = {'status': False, 'action': None, 'img': 'trans.gif'}
level[1][6] = {'status': False, 'action': None, 'img': 'trans.gif'}
level[1][7] = {'status': False, 'action': None, 'img': 'trans.gif'}
level[1][1] = {'status': False, 'action': None, 'img': 'trans.gif'}
level[2][1] = {'status': False, 'action': None, 'img': 'trans.gif'}
level[3][1] = {'status': False, 'action': None, 'img': 'trans.gif'}
level[7][6] = {'status': False, 'action': None, 'img': 'trans.gif'}
level[7][7] = {'status': False, 'action': None, 'img': 'trans.gif'}


with open('level4.pickle', 'wb') as b:
	pickle.dump(level, b)
levelData = {'block.gif': 'block.gif', 'testimg.gif': 'testimg.gif', 'door.gif': 'door.gif', 'house.gif': 'house.gif', 'ashhouse.gif': 'ashhouse.gif', 'ashupperfloor.gif': 'ashupperfloor.gif'}
with open('level4Data.pickle', 'wb') as b:
	pickle.dump(levelData, b)