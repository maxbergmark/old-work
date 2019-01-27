
string = open('skit.txt', 'r').read()
lines = string.split('\n')
print(len(lines))
words = []
for element in lines:
    words.extend(element.split())

print(len(words))
