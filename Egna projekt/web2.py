import urllib.request as url
import json
import sys
import xml.etree.ElementTree
import re
import string

TAG_RE = re.compile(r'<[^>]+>')

def remove_tags(text):
    return TAG_RE.sub('', text)

def remove_tags2(text):
    ''.join(xml.etree.ElementTree.fromstring(text).itertext())

def connect(urlstr):
    page = url.urlopen(urlstr).read()
    return json.loads(page.decode())

def getSort(value):
	return value[1]

charmap = {}

for k in range(10):
	randlist = connect('http://en.wikipedia.org/w/api.php?action=query&list=random&rnlimit=10&rnnamespace=0&format=json')
	randids = [item['id'] for item in randlist['query']['random']]
	print(randids)
	for randid in randids:
		urlstr = 'http://en.wikipedia.org/w/api.php?action=parse&pageid=' + str(randid) + '&format=json&prop=text'

		page = connect(urlstr)
		text = page['parse']['text']['*']

		text = remove_tags(text)
		#print(text.encode('utf8'))
		splitlist = re.split('\W+', text)

		print()
		#print(splitlist)
		while '' in splitlist:
			splitlist.remove('')
		#print(' '.join(splitlist).lower().encode('utf8'))
		strings = ''.join(splitlist).lower()
		for letter in strings:
			if letter.isalpha():
				if letter in charmap:
					charmap[letter] += 1
				else:
					charmap[letter] = 1

alphabet = string.ascii_lowercase
counts = [(letter, charmap[letter]/sum(charmap[letter] for letter in alphabet)) for letter in alphabet]
for i in range(len(counts)):
	print(counts[i])

test = sum(i[1]**2 for i in counts)
print()
print(test)
print(1/len(counts))
freqsort = sorted(counts, key = getSort, reverse = True)
for i in range(len(freqsort)):
	print(freqsort[i])