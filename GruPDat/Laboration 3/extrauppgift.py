import imp
import sprak

def visk(string):
    
    result = ''
    
    for temp in range(0, len(string)):
        if sprak.vokaltest(string[temp]) == False:
            result += string[temp]
    return result

def fikon(string):

    temp = string.split()
    result = ''
    
    for ordet in range(0, len(temp), 1):
    
        for bokstav in range(0, len(temp[ordet])):
        
            if sprak.vokaltest(temp[ordet][bokstav]) == True:
                result += 'fi' + temp[ordet][bokstav+1:] + temp[ordet][:bokstav+1] + 'kon '
                break
            
    return result

def bebis(string):
    
    temp = string.split()
    result = ''
    
    for ordet in range(0, len(temp), 1):
    
        for bokstav in range(0, len(temp[ordet])):
        
            if sprak.vokaltest(temp[ordet][bokstav]) == True:
                result += 3*temp[ordet][:bokstav+1] + ' '
                break
            
    return result

def fylle(string):

    result = ''
   
    for temp in range(0, len(string)):
        if sprak.vokaltest(string[temp]) == True:
            result += string[temp] + 'f' + string[temp]
        else:
            result += string[temp]
    return result

def rovar(string):
    result = ''
    

    for temp in range(0, len(string)):
        if sprak.konsonanttest(string[temp]) == True:
            result += string[temp] + 'o' + string[temp]
        else:
            result += string[temp]
    return result

def alls(string):

    temp = string.split()
    result = ''
    
    for ordet in range(0, len(temp), 1):
    
        for bokstav in range(0, len(temp[ordet])):
        
            if sprak.vokaltest(temp[ordet][bokstav]) == True:
                result += temp[ordet][bokstav:] + temp[ordet][:bokstav] + 'all '
                break
            
    return result
lista = [visk, rovar, fylle, bebis, alls, fikon]
lista2 = ["Viskspråket:", "Rövarspråket:", "Fyllespråket:", "Bebisspråket:", "Allspråket:", "Fikonspråket:"]
while 1:
    num = int(input("Vilket språk önskas? \n1 Visk 2 Rövar 3 Fylle 4 Bebis \n5 All 6 Fikon 7 Avsluta programmet \n")) 
    print()
    if num == 7:
        print("Hej då")
        break
    else:
        test = str(input("Vad önskas översättas? \n"))
        print()
        print(lista2[num-1], '\n'+lista[num-1](test))
