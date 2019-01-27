
import pymysql

# loc_id,country,region,city,postalCode,latitude,longitude,metroCode,areaCode


conn = pymysql.connect(host="wikipedia.cconvbhs8rou.eu-west-2.rds.amazonaws.com", user="ubuntu", passwd="maxmysql",db="haddock")
cur = conn.cursor()

all_locs = []

with open("country_ip_data.csv", "r") as f:
	i = 0
	for row in f:
		row = row.replace("\n", "")
		row = row.replace("\"", "")
		row = row.split(",")
		row[2] = int(row[2])
		row[5] = int(row[5])
		row[6] = int(row[6])
		row = tuple(row)
		i += 1
		all_locs.append(row)
		# print(i)
		if len(row) != 6:
			print("asdf")

#		if i > 10:
#			break

	# s = "INSERT INTO ip_location (low_ip, high_ip, lower, upper, country_code, country_name) values (\"%s\", \"%s\", %d, %d, \"%s\", \"%s\")" % row
	s = "INSERT INTO ip_location (low_ip, high_ip, lower, upper, country_code, country_name) values (%s, %s, %s, %s, %s, %s)"


	print("preparing")
	print(s)
	print(len(all_locs))
	# print(all_locs)
	cur.executemany(s, all_locs)
	print("committing")
	conn.commit()