import socket
import random

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

s.bind((socket.gethostname(), 4445))
s.listen(5)

r2 = '''HTTP/1.0 200 OK
Server: SimpleHTTP/0.6 Python/2.7.6
Date: Mon, 13 Oct 2014 17:55:56 GMT
Expires: Tue, 03 Jul 2001 06:00:00 GMT
Last-Modified: {now} GMT
Cache-Control: no-store, no-cache, must-revalidate, max-age=0
Cache-Control: post-check=0, pre-check=0
Pragma: no-cache
Content-type: text/html; charset=UTF-8
Content-Length: '''

r3 = '''<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 3.2 Final//EN"><html>
<title>Super cool website</title>
<body>
<h2>top secret stuff. '''

r4 = '''</h2>
</body>
</html>\r\n'''

users = set()

while True:
	(clientsocket, address) = s.accept()
	n = str(random.randint(0,10**10))
	l = len(r3+n+r4)
	if address[0] not in users:
		print("new user connecting from:", address[0])
		users.add(address[0])
	print(n, "sent to", address)
	clientsocket.send((r2 + str(l) + "\n\n" + r3 + n + r4).encode())
