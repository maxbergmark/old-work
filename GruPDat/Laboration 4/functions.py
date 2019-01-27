def extra1(lista, function):
    check = str(input("Ditt ord: "))
    
    if function(lista, check) is True:
        
        result = 'finns'
    else:
        result = 'finns inte'
    string = check + ' ' + result
    
    return string


def extra2(lista, function):
    return function(lista)

def linsok(lista, elem):

    return elem in lista



def linparsok(lista):
    
    for test in lista:        
        if linsok(lista, test[3:] + test[:3]) is True:
            print(test, test[3:] + test[:3])


def binsok(li, x):
    lo = 0
    hi = len(li)-1
    while lo <= hi:
        mid = (lo+hi)//2
        #print(li[mid])
        if x < li[mid]:
            hi = mid - 1
        elif x > li[mid]:
            lo = mid + 1
        else:
            return True
    return False

def binparsok(lista):
    for test in lista:        
        if binsok(lista, test[3:] + test[:3]) is True:
            print(test, test[3:] + test[:3])
