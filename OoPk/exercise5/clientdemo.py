import socket

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
host = "52.56.110.203"
port = 4444
s.connect((host,port))
# s.connect(("52.56.38.143", 4444))

while True:
	inp = str(input("Text to send: "))
	form_input = "%s\r\n" % inp
	s.send(form_input.encode())
#	s.send(("%s\r\n" % str(input("Text to send: "))).encode())
	data = s.recv(1024).decode()
	print("Answer:", data.strip())
#	print("Answer:", s.recv(65536).decode().strip())
