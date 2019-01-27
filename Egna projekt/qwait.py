import json,pygame,time
from urllib.request import urlopen
pygame.init()
pygame.mixer.music.load("confirm.wav")

m = set()
while 1:
	s = bytes(urlopen('http://queue.csc.kth.se/api/queue/DD1346').read()).decode('utf-8')
	t = set([i['time'] for i in json.loads(s)['queue']])
	if t > m: pygame.mixer.music.play();print('New person in queue.')
	m = t;time.sleep(.5)