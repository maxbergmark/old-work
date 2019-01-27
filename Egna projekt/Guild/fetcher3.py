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
	attStr = str(round(attendance[0], 2))
	perc = 0
	if (attendance[1] > 0):
		perc = int(100*attendance[0]/min(checkRaids, attendance[1]))
	percStr = str(perc)
	print(player, (17-len(player)-len(attStr))*' ', attStr, (5-len(percStr))*' ', percStr, end = '   ')
	if (perc >= 80):
		if (attendance[1] >= checkRaids):
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
		attStr = str(round(playerDict[raider][0], 2))
		space1 = (17-len(raider)-len(attStr))
		space2 = (2-len(str(playerDict[raider][1])))
		print('  ', raider, space1*' ', attStr, '/', space2*' ' + str(min(checkRaids, playerDict[raider][1]))) 

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


def fillDict(raidDict, playerDict):
	for player in sorted(raidDict.keys()):
	
		fights = raidDict[player]
		attendance = fights/totalFights
		if (attendance != 0 or True):
			print(player, round(attendance, 2))

		if player in playerDict:
			#print(player)
			playerDict[player][0] += attendance
		else:
			playerDict[player] = [attendance, 0]

	for player in playerDict.keys():
		if (playerDict[player][1] > 0 or raidDict[player] > 0):
			playerDict[player][1] += 1

players = []


players.append(['Gum', 'Iraku'])
players.append(['Gohealurself'])
players.append(['Netpro'])
players.append(['Khrubby', 'Chrubby'])
players.append(['Ahoú'])
players.append(['Auranicz'])
players.append(['Daybringer'])
players.append(['Deathlymaid'])
players.append(['Emeraldfox'])
players.append(['Fearnaabs'])
players.append(['Gabbworlock'])
players.append(['Illiesaria'])
players.append(['Monicru'])
players.append(['Oák'])
players.append(['Sifern'])
players.append(['Strandza'])
players.append(['Tarkaza'])
players.append(['Therer'])
players.append(['Thoorer'])
players.append(['Timchi'])
players.append(['Virtua'])
players.append(['Vìper'])
players.append(['Wubbelbutt'])
players.append(['Damae'])
players.append(['Jazeus', 'Go'])

'''
players.append([])
'''

classes = ['Warrior', 'Paladin', 'Hunter', 'Rogue', 'Priest', 'DeathKnight', 'Shaman', 'Mage', 'Warlock', 'Monk', 'Druid']

if not os.path.exists('RaidData'):
	os.makedirs('RaidData')

findLastRaid()


guildName = 'Aggressive%20Authority'
serverName = 'Frostmane'
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



numberOfRaids = 120
checkRaids = 62
trialPeriod = 12
raiderPeriod = 12

for raid in raidList[-numberOfRaids:]:
	raidID = raid['id']
	dateList.append(datetime.datetime.fromtimestamp(int(raid['start']/1000)).strftime("%Y-%m-%d_%H.%M"))
#	print(raidID)
	if (not os.path.isfile('AggressiveAuthority/' + dateList[-1].replace(' ', '_') + '.p')):
		raidURL = 'https://www.warcraftlogs.com:443/v1/report/fights/' + raidID + '?api_key=' + apiKey
		tempJSON = get_jsonparsed_data2(raidURL)
		pickle.dump(tempJSON, open('AggressiveAuthority/' + dateList[-1].replace(' ', '_') + '.p', 'wb'))
	else:
		tempJSON = pickle.load(open('AggressiveAuthority/' + dateList[-1].replace(' ', '_') + '.p', 'rb'))
	raidInfoList.append(tempJSON)
#	print(raid['start'])
	print('Fetched data from:', dateList[-1])
#	print()


playerDict = {}
dateDict = {}
raidDict = {}
count = 0
raidCount = 0
outsiderDict = {}
for i in range(numberOfRaids):
	raid = raidInfoList[i]
	dateDict[dateList[i]] = {}
	print()
	print(dateList[i] + "asdfasdf")
	count += 1
	raidDict.clear()	
	for p in players:
		raidDict[p[0]] = 0
		dateDict[dateList[i]][p[0]] = 0
#	print()
	totalFights = 0
	for fight in raid['fights']:
		if (fight['boss'] != 0):
			totalFights += 1
	if (totalFights > 0):
		dateDict[dateList[i]]['totalFights'] = totalFights
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
					
					dateDict[dateList[i]][checkPlayer(player['name'], players)] += fights
					#print(raidDict)
				else:
					if (player['name'] not in outsiderDict.keys()):
						outsiderDict[player['name']] = 0
					outsiderDict[player['name']] += 1
					#print(player['name'])
		else:
			#dateDict.pop(dateList[i], None)
			pass
		print()
		#print(raidDict)
		fillDict(raidDict, playerDict)
#		print(playerDict)

raidDateDict = {}
raidDates = sorted(dateDict.keys(), reverse = True)
checkedRaids = 0
i = 0

while checkedRaids < checkRaids + 12:
	if (i > 0):
		if (raidDates[i][:10] == raidDates[i-1][:10]):
			for k in dateDict[raidDates[i]].keys():
				raidDateDict[raidDates[i][:10]][k] += dateDict[raidDates[i]][k]
		else:
			raidDateDict[raidDates[i][:10]] = dateDict[raidDates[i]]
			checkedRaids += 1
	else:
		raidDateDict[raidDates[i][:10]] = dateDict[raidDates[i]]
		checkedRaids += 1
	i += 1


attendanceDict = {}
maxAttendanceDict = {}
finalRaidDates = sorted(raidDateDict.keys(), reverse = False)

for i in range(len(finalRaidDates)):

	raid = finalRaidDates[i]
	for player in raidDateDict[raid].keys():

		if (player != 'totalFights'):
			if (len(finalRaidDates)-i-1 < checkRaids):
				if (player in attendanceDict.keys()):
					#print(raidDateDict[raid][player], raidDateDict[raid]['totalFights'])
					attendanceDict[player] += 0 < raidDateDict[raid][player]/raidDateDict[raid]['totalFights']
				else:
					attendanceDict[player] = 0 < raidDateDict[raid][player]/raidDateDict[raid]['totalFights']
			if (player in maxAttendanceDict.keys()):
				maxAttendanceDict[player] += 1
			elif (raidDateDict[raid][player] > 0):
				maxAttendanceDict[player] = 1

for player in sorted(attendanceDict.keys()):
	if (player in maxAttendanceDict.keys()):
		playerDict[player] = [attendanceDict[player], maxAttendanceDict[player]]
	else:
		playerDict[player] = [attendanceDict[player], 0]

'''
	print(player, round(attendanceDict[player], 2), end = ' ')
	if (attendanceDict[player]/min(maxAttendanceDict[player], checkRaids) > 0.8):
		if (maxAttendanceDict[player] >= checkRaids):
			print('RAIDER')
		else:
			print('UPCOMER')
	else:
		print('NOT RAIDER')
'''






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
		if (playerDict[player][1] >= raiderPeriod):
			raiderList.append(player)
		elif (playerDict[player][1] >= trialPeriod):
			memberList.append(player)
		else:
			upcomingList.append(player)
	elif (perc >= 30):
		if (playerDict[player][1] >= trialPeriod):
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
#printDict(outsiderDict, 'outsiders', 5)

print('\nAttendance calculated and printed in', str(int(1000*(time.clock()-t1))), 'ms.')
