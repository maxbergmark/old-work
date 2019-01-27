import imp
import sprak

string = str(input("hemligifiera: "))
temp = string.split()
result = ''
print()
for ordet in range(0, len(temp), 1):
    
    for bokstav in range(0, len(temp[ordet])):
        
        if sprak.vokaltest(temp[ordet][bokstav]) == True:
            result += 3*temp[ordet][:bokstav+1] + ' '
            break
            
        
print(result)
