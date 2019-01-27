import imp
import sprak

string = str(input("viska: "))
result = ''
print()
for temp in range(0, len(string)):
    if sprak.vokaltest(string[temp]) == False:
        result += string[temp]
print(result)
