import json
import re

data = {}

def readData(onData): 
	with open("data.csv") as f:
		for line in f:
			onData(line)

def parseLine(line):
	newLine = re.sub("\"\"", "\"", line).strip()
	if newLine.startswith('"') and newLine.endswith('"'):
		newLine = newLine[1:-1]

	try:
		j = json.loads(newLine)
		return (j[0], j[1], j[2])
	except Exception: 
		print("could not parse: " + line)

	
def onLine(line):
	user, interested, tag = parseLine(line)
	data[user['username']] = 1





readData(onLine)

print(data)