import imp
import sprak

string = str(input("hemligifiera: "))
result = ''
print()
for temp in range(0, len(string)):
    if sprak.vokaltest(string[temp]) == True:
        result += string[temp] + 'f' + string[temp]
    else:
        result += string[temp]
print(result)
