
import pymysql

# loc_id,country,region,city,postalCode,latitude,longitude,metroCode,areaCode


conn = pymysql.connect(host="wikipedia.cconvbhs8rou.eu-west-2.rds.amazonaws.com", user="ubuntu", passwd="maxmysql",db="haddock")
cur = conn.cursor()

all_locs = []

with open("city_blocks.csv", "r") as f:
	i = 0
	for row in f:
		row = row.replace("\n", "")
		row = row.replace("\"", "")
		row = row.split(",")
		row[0] = int(row[0])
		row[1] = int(row[1])
		row[2] = int(row[2])
		row = tuple(row)
		i += 1
		all_locs.append(row)
		print(i)

#		if i > 10:
#			break

	# s = "INSERT INTO ip_location (low_ip, high_ip, lower, upper, country_code, country_name) values (\"%s\", \"%s\", %d, %d, \"%s\", \"%s\")" % row
	s = "INSERT INTO ip_city_indexer (lower, upper, value) values (%s, %s, %s)"


	print("preparing")
	print(s)
	print(len(all_locs))
	# print(all_locs)
	cur.executemany(s, all_locs)
	print("committing")
	conn.commit()