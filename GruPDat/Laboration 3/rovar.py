import imp
import sprak

string = str(input("hemligifiera: "))
result = ''
print()
for temp in range(0, len(string)):
    if sprak.konsonanttest(string[temp]) == True:
        result += string[temp] + 'o' + string[temp]
    else:
        result += string[temp]
print(result)
