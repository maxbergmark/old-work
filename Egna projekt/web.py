import urllib.request as url


def connect():
    page = url.urlopen('http://www.eveandersson.com/pi/digits/1000000').read()
    return page

page = str(connect()).replace(r'\n', '')

index = page.find('3.14159')
pi = page[index:1000002+index]

print(pi[:1000])
