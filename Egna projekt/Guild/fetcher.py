try:
    # For Python 3.0 and later
    from urllib.request import urlopen
except ImportError:
    # Fall back to Python 2's urllib2
    from urllib2 import urlopen

import json
import os, datetime
import pickle
import time


def get_jsonparsed_data(url):
    """Receive the content of ``url``, parse it as JSON and return the
       object.
    """

    response = urlopen(url)
    data = bytes(response.read()).decode('utf-8')
#    print(data)
    formattedData = data.replace('},\n', '}LINE_SPLIT\n').replace('\n', '').replace('[', '').replace(']', '')
    dataList = formattedData.split('LINE_SPLIT')
#    print(dataList)
    jsonList = [json.loads(i) for i in dataList]
#    print(jsonList)
    return jsonList

def get_jsonparsed_data2(url):
    """Receive the content of ``url``, parse it as JSON and return the
       object.
    """

    response = urlopen(url)
    data = bytes(response.read()).decode('utf-8')
#    print(data)
    #formattedData = data.replace('},\n', '}LINE_SPLIT\n').replace('\n', '').replace('[', '').replace(']', '')
    #dataList = formattedData.split('LINE_SPLIT')
#    print(dataList)
    jsonobj = json.loads(data)
    #jsonList = [json.loads(i) for i in dataList]
#    print(jsonList)
    return jsonobj

def checkPlayer(player, players):
	for p in players:
		for name in p:
			if (name == player):
				return p[0]
	return ''

def printPlayer(player, attendance, numberOfRaids):
	if (attendance[0] == int(attendance[0])):
		attendance[0] = int(attendance[0])
	attStr = str(attendance[0])
	perc = 0
	if (attendance[1] > 0):
		perc = int(100*attendance[0]/attendance[1])
	percStr = str(perc)
	print(player, (17-len(player)-len(attStr))*' ', attStr, (5-len(percStr))*' ', percStr, end = '   ')
	if (perc >= 80):
		if (attendance[1] >= 12):
			print('     RAIDER')
		else:
			print('    UPCOMER')
	else:
		print(' NOT RAIDER')
	return perc

def printList(nameList, playerDict, name):


	print()
	print('These are our ' + name + ':')
	print()

	for raider in nameList:
		space1 = (17-len(raider)-len(str(playerDict[raider][0])))
		space2 = (2-len(str(playerDict[raider][1])))
		print('  ', raider, space1*' ', playerDict[raider][0], '/', space2*' ' + str(playerDict[raider][1])) 

def printDict(nameList, name, cutoff):


	print()
	print('These are our ' + name + ':')
	print()

	for raider in sorted(nameList.keys()):
		if (nameList[raider] >= cutoff):
			numStr = str(nameList[raider])
			print('  ', raider, (21-len(raider)-len(numStr))*' ', numStr) 

def findLastRaid():
	paths = next(os.walk('RaidData'))[2]
	return paths

def getStartTime():
	dates = findLastRaid()
	if (len(dates) == 0):
		return 0
	lastDate = dates[-1].replace('.p', '')
	lastTime = time.mktime(datetime.datetime.strptime(lastDate, "%Y-%m-%d_%H.%M").timetuple())
	return str(1000*int(lastTime))

def getKey(obj):
	return obj['name']

players = []
players.append(['Khrubby', 'Phrubby'])
players.append(['Snakso', 'Snakaso', 'Asymptotem', 'Tsumester'])
players.append(['Yemy', 'Markemang', 'Venerated'])
players.append(['Xaap', 'Toojkie', 'Stjärntecken'])
players.append(['Kalaschnikow','Suuci'])
players.append(['Cooleja'])
players.append(['Azeman', 'Daskarn'])
players.append(['Matmonster', 'Greetíngs'])
players.append(['Shifthappeñs'])
players.append(['Harshel'])
players.append(['Arwonas', 'Zoranda'])
players.append(['Kimáhri', 'Fènrir', 'Sephìroth', 'Aéris', 'Báhamut', 'Ifrìt'])
players.append(['Cylen', 'Sylens', 'Kylens'])
players.append(['Zinqier', 'Nyfsa', 'Mowdra', 'Azméa', 'Vima'])
players.append(['Lokthak', 'Barator'])
players.append(['Ligger'])
players.append(['Willferâl'])
players.append(['Maethel'])
players.append(['Escá'])
players.append(['Havocwreaker'])
players.append(['Teríth'])
players.append(['Dyslexi'])
players.append(['Obliviia'])
players.append(['Pniece'])
players.append(['Güardian'])
players.append(['Quimera'])
players.append(['Xén', 'Xanity', 'Haleey', 'Undisputéd'])
players.append(['Aphóxx'])
players.append(['Captncoco', 'Paowli', 'Captnsoapbbl', 'Captnlacoco', 'Shilou'])
players.append(['Doppelboy'])
players.append(['Creycina'])
players.append(['Hönan', 'Healinghönan'])
players.append(['Jägarn'])
players.append(['Molak'])
'''
players.append([])
players.append([])
players.append([])
players.append([])
'''

classes = ['Warrior', 'Paladin', 'Hunter', 'Rogue', 'Priest', 'DeathKnight', 'Shaman', 'Mage', 'Warlock', 'Monk', 'Druid']

if not os.path.exists('RaidData'):
	os.makedirs('RaidData')

findLastRaid()


guildName = 'Stress%20Management'
serverName = 'Darksorrow'
regionName = 'EU'
apiKey = '5dd1e07aa60c455100068cd2e31cae76'
startTime = '0'

guildURL = 'https://www.warcraftlogs.com:443/v1/reports/guild/' + guildName + '/' + serverName + '/' + regionName + '?start=' + startTime + '&api_key=' + apiKey
#print(guildURL)
print('Fetching guild raids...')
t0 = time.clock()
raidList = get_jsonparsed_data(guildURL)
print('Raids fetched in', str(int(1000*(time.clock()-t0))), 'ms.')

t1 = time.clock()
raidInfoList = []
dateList = []



numberOfRaids = 14

for raid in raidList[-numberOfRaids:]:
	raidID = raid['id']
	dateList.append(datetime.datetime.fromtimestamp(int(raid['start']/1000)).strftime("%Y-%m-%d_%H.%M"))
#	print(raidID)
	if (not os.path.isfile('RaidData/' + dateList[-1].replace(' ', '_') + '.p')):
		raidURL = 'https://www.warcraftlogs.com:443/v1/report/fights/' + raidID + '?api_key=' + apiKey
		tempJSON = get_jsonparsed_data2(raidURL)
		pickle.dump(tempJSON, open('RaidData/' + dateList[-1].replace(' ', '_') + '.p', 'wb'))
	else:
		tempJSON = pickle.load(open('RaidData/' + dateList[-1].replace(' ', '_') + '.p', 'rb'))
	raidInfoList.append(tempJSON)
#	print(raid['start'])
	print('Fetched data from:', dateList[-1])
#	print()


playerDict = dict()
raidDict = {}
count = 0
outsiderDict = {}


for raid in raidInfoList:
	print()
	print(dateList[count])
	count += 1
	raidDict.clear()	
	for p in players:
		raidDict[p[0]] = 0
#	print()
	totalFights = 0
	for fight in raid['fights']:
		if (fight['boss'] != 0):
			totalFights += 1
	if (totalFights > 0):
		for player in sorted(raid['friendlies'], key = getKey):

			if (player['type'] in classes):
				fights = 0
	#			print(player['name'])
				for fight in player['fights']:
					if (raid['fights'][fight['id']-1]['boss'] != 0):
						fights += 1

				#print(player['name'], fights, totalFights)
				attendance = 0
				if (checkPlayer(player['name'], players) != ''):
					raidDict[checkPlayer(player['name'], players)] += fights
					#print(raidDict)
				else:
					if (player['name'] not in outsiderDict.keys()):
						outsiderDict[player['name']] = 0
					outsiderDict[player['name']] += 1
					#print(player['name'])
		print()
		#print(raidDict)
		for player in sorted(raidDict.keys()):
			
			fights = raidDict[player]
			attendance = 1
			if (fights/totalFights < 0.8):
				attendance = 0.5
			if (fights/totalFights == 0):
				attendance = 0
			if (attendance != 0):
				print(player, attendance)



			if player in playerDict:
				#print(player)
				playerDict[player][0] += attendance
			else:
				playerDict[player] = [attendance, 0]

		for player in playerDict.keys():
			if (playerDict[player][1] > 0 or raidDict[player] > 0):
				playerDict[player][1] += 1
#		print(playerDict)

[print() for _ in range(3)]
print('NAME           ATND      %        STATUS')
print()
#print(playerDict)
raiderList = []
upcomingList = []
memberList = []
trialList = []
restList = []

for player in sorted(playerDict.keys()):
	perc = printPlayer(player, playerDict[player], numberOfRaids)
	if (perc >= 80):
		if (playerDict[player][1] >= 12):
			raiderList.append(player)
		else:
			upcomingList.append(player)
	if (perc >= 30):
		if (playerDict[player][1] >= 12):
			if (player not in raiderList):
				memberList.append(player)
		else:
			trialList.append(player)
	else:
		restList.append(player)
	print(40*'-')


printList(raiderList, playerDict, 'raiders')
printList(upcomingList, playerDict, 'upcomers')
printList(memberList, playerDict, 'members')
printList(trialList, playerDict, 'trials')
printList(restList, playerDict, 'other players')
printDict(outsiderDict, 'outsiders', 5)

print('\nAttendance calculated and printed in', str(int(1000*(time.clock()-t1))), 'ms.')
# print(playerDict)