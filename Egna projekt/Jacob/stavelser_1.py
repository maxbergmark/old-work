#försöker öppna en fil som användaren bestämmer

print()
print(42*'-')
print('- VÄLKOMMEN TILL ORDSVÅRIGHETSBERÄKNAREN -')
print(42*'-')
filnamn = str(input('Vad heter din fil? '))
ordstring = open(filnamn+'.txt','r').read()

#ingen returdata förutom print()-raderna ovanför
