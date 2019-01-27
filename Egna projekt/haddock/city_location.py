import pymysql
from unidecode import unidecode as u
import codecs

# loc_id,country,region,city,postalCode,latitude,longitude


conn = pymysql.connect(host="wikipedia.cconvbhs8rou.eu-west-2.rds.amazonaws.com", user="ubuntu", passwd="maxmysql",db="haddock")
cur = conn.cursor()

all_locs = []

with codecs.open('city_location.csv', encoding='ISO-8859-1', mode='r') as f:
	i = 0
	for row in f:
		row = row.replace("\n", "")
		row = row.replace("\"", "")
		row = row.split(",")
		del row[-2:]
		# print(row)
		if (len(row) > 7):
			row[3] = row[4] + ' ' + row[3]
			del row[4]
		# print(row)
		row[0] = int(row[0])
		row[5] = float(row[5])
		row[6] = float(row[6])
		row = tuple(row)
		i += 1
		all_locs.append(row)
		# print(i, row)

#		if i > 10:
#			break

	# s = "INSERT INTO ip_location (low_ip, high_ip, lower, upper, country_code, country_name) values (\"%s\", \"%s\", %d, %d, \"%s\", \"%s\")" % row
	s = "INSERT INTO ip_cities (loc_id,country,region,city,postal_code,latitude,longitude) values (%s, %s, %s, %s, %s, %s, %s)"


	print("preparing")
	print(s)
	print(len(all_locs))
	# print(all_locs)
	cur.executemany(s, all_locs)
	print("committing")
	conn.commit()