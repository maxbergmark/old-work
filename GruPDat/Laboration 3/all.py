import imp
import sprak

string = str(input("hemligifiera: "))
temp = string.split()
result = ''
print()
for ordet in range(0, len(temp), 1):
    
    for bokstav in range(0, len(temp[ordet])):
        
        if sprak.vokaltest(temp[ordet][bokstav]) == True:
            result += temp[ordet][bokstav:] + temp[ordet][:bokstav] + 'all '
            break
            
        
print(result)
