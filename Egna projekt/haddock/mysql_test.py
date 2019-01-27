import datetime
import pymysql
import subprocess
import json
from unidecode import unidecode as u


def updateIPs(cur, conn):
	cur.execute("SELECT ip, country, region, city, org FROM visitors order by id")
	loc = {}
	loc2 = {}

	for row in cur:
		if (None in row[1:] and row[0] not in loc and row[0] not in loc2):
			s = "curl ipinfo.io/" + row[0]
			proc = subprocess.Popen([s, ""], stdout=subprocess.PIPE, shell=True)
			(out, err) = proc.communicate()
			print(out)
			d = json.loads(out.decode('utf-8'))
			loc[row[0]] = d
			print(row)
		elif (None in row[1:] and row[0] not in loc and row[0] in loc2):
			loc[row[0]] = loc2[row[0]]
		else:
			loc2[row[0]] = {'country': row[1], 'region': row[2], 'city': row[3], 'org': row[4]}


	for ip in loc.keys():
		query = "update visitors set country = \"" + u(loc[ip]['country']) + "\", region = \"" + u(loc[ip]['region']) + "\", city = \"" + u(loc[ip]['city']) + "\", org= \"" + u(loc[ip]['org']) + "\" where ip=\"" + ip + "\""
		print(query)
		cur.execute(query)

	conn.commit()


def updateFreq(cur, conn):
	cur.execute("SELECT curse FROM visitors")
	cur2 = conn.cursor()
	cur2.execute("UPDATE curses SET freq=0")
	i = 0
	for row in cur:
		i += 1
		print(i, row)
		cur2.execute("UPDATE curses SET freq=freq+1 WHERE curse=\"" + row[0] + "\"")

	conn.commit()

conn = pymysql.connect(host="wikipedia.cconvbhs8rou.eu-west-2.rds.amazonaws.com", user="ubuntu", passwd="maxmysql",db="haddock")
cur = conn.cursor()

updateIPs(cur, conn)
# updateFreq(cur, conn)

cur.close()
conn.close()

