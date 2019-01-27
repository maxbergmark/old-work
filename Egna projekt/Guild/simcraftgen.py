import string
import time
import re


def makeNums2(gChoice, length):
	if length == -1:
		return ['']
	if (length < len(gChoice)-1):
		if (gChoice[length+1] == 'x'):
			lista = [rest + ' ' + (str(i+1)) for i in range(1,int(gChoice[length])) for rest in makeNums2(gChoice, length-1)]
	if (gChoice[length] == 'x'):
		lista = [rest + ' ' + (str(i+1)) for rest in makeNums2(gChoice, length-1) for i in range(int((rest.split(' '))[len((rest.split(' ')))-1])-1)]
	else:
		lista = [rest + ' ' + (str(i+1)) for i in range(int(gChoice[length])) for rest in makeNums2(gChoice, length-1)]
	return lista
    
def genTalents(gChoice, length):
    if length == -1:
        return ['']
    lista = [rest+i for i in gChoice[length] for rest in genTalents(gChoice, length-1)]
    return lista

def processInput():
	with open('simcgearinput.txt', 'r') as f:
		gear = {}
		lastGear = ''
		for currentLine in f:
			if (currentLine.strip() != ''):
				if (not currentLine.startswith('//')):
					if (currentLine.startswith('override')):
						geartype=currentLine.split('=')[0]
						gearvalue='='.join(currentLine.split('=')[1:])
						#print(name, geartype, gearvalue)
						if (not geartype in gear):
							gear[geartype] = []
						gear[geartype].append((gearvalue.strip(),'override'))

					elif (currentLine.startswith('talents')):
						geartype=currentLine.split('=')[0]
						gearvalue=currentLine.split('=')[1]
						#print(name, geartype, gearvalue)
						gear[geartype] = gearvalue.strip()

					elif (currentLine.startswith('class')):
						geartype=currentLine.split('=')[0]
						gearvalue=currentLine.split('=')[1]
						gear[geartype] = gearvalue.strip()

					elif (currentLine.startswith('base_gear')):
						geartype=currentLine.split('=')[0]
						gearvalue=currentLine.split('=')[1]
						gear[geartype] = gearvalue.strip()
					
					elif (currentLine.startswith('enchant_id')):
						spl = currentLine.split(' ')
						name = spl[1].split('=')[1].strip()
						enchant=spl[0].strip()
						p = re.compile(',enchant_id=[0-9]+')
						lastStripped = p.sub('', lastGear[0])
						newEnchant = lastStripped + ',' + enchant
						newName = lastGear[1] + '_' + name
						gear[lastGear[2]].append((newEnchant, newName))

					elif (currentLine.startswith('relic_id')):
						spl = currentLine.split(' ')
						name = spl[1].split('=')[1].strip()
						relic=spl[0].strip()
						p = re.compile(',relic_id=[0-9/:]+')
						lastStripped = p.sub('', lastGear[0])
						newEnchant = lastStripped + ',' + relic
						newName = lastGear[1] + '_' + name
						gear[lastGear[2]].append((newEnchant, newName))

					elif (currentLine.startswith('gem_id')):
						spl = currentLine.split(' ')
						name = spl[1].split('=')[1].strip()
						gem=spl[0].strip()
						p = re.compile(',gem_id=[0-9/:]+')
						lastStripped = p.sub('', lastGear[0])
						newEnchant = lastStripped + ',' + gem
						newName = lastGear[1] + '_' + name
						gear[lastGear[2]].append((newEnchant, newName))

					elif (currentLine.startswith('bonus_id')):
						spl = currentLine.split(' ')
						name = spl[1].split('=')[1].strip()
						bonus=spl[0].strip()
						p = re.compile(',bonus_id=[0-9/]+')
						lastStripped = p.sub('', lastGear[0])
						newEnchant = lastStripped + ',' + bonus
						newName = lastGear[1] + '_' + name
						gear[lastGear[2]].append((newEnchant, newName))

					else:
						spl = currentLine.split(' ')
						name = spl[1].split('=')[1].strip()
						geartype=spl[0].split('=')[0]
						gearvalue='='.join(spl[0].split('=')[1:])
						#print(name, geartype, gearvalue)
						if (not geartype in gear):
							gear[geartype] = []
						gear[geartype].append((gearvalue,name))
						lastGear = (gearvalue,name, geartype)


		return gear

def getGearInput(gear):
	names0 = []
	names = []
	overrides = []
	talents = ''
	cl = ''
	s = ''
	basegear = ''
	for key in sorted(gear):
		
		if (key == 'trinket' or key == 'finger'):
			names.append(key)
			names.append(key)
			names0.append(key + '1')
			names0.append(key + '2')
			s += str(len(gear[key])) + ' x '
		elif (key == 'override'):
			overrides.extend(gear[key])
		elif (key == 'talents'):
			talents = gear[key]
		elif (key == 'class'):
			cl = gear[key]
		elif (key == 'base_gear'):
			basegear = gear[key]
		else:
			names.append(key)
			names0.append(key)
			s += str(len(gear[key])) + ' '
	return (s.strip(), names, names0, overrides, talents, cl, basegear)


def printToFile(f, count, tal, basegear):

	f.write(cl + '=G_' + str(count-1) + '\n')
	f.write(basegear + '\n')
	f.write('talents=' + tal + '\n')
	

def printOverrides(f, overrides):
	for o in overrides:
		f.write(o[0] + '\n')
    
start = time.clock()
gear = processInput()
inp = getGearInput(gear)
d1 = time.clock()
print()
gChoice = inp[0]
names0 = inp[1]
names = inp[2]
overrides = inp[3]
talentinput = inp[4]
cl = inp[5]
basegear = inp[6]
overrideformat = [o[0] for o in overrides]
print('   Class:', cl)
print('   Base gear file:', basegear)
print('   Gear choices:', gChoice)
print('   Gear spots:', ', '.join(names))
print('   Overrides:', ', '.join(overrideformat) or 'None')
print('   Talent choices:', talentinput)
print()
#gChoice = str(input('Input: '))
#talentinput = str(input('Talents: '))
#length = int(input('Length: '))
#names0 = ['trinket','trinket']#, 'trinket','finger','finger']
#names = ['trinket1','trinket2']#, 'trinket2','ring1','ring2']
#gear = {}
#gear['trinket'] = [',id=139321,bonus_id=1807/1482/3336', ',id=139321,bonus_id=1807/1482/3336', ',id=137349,bonus_id=1532/3412']
#trinkets = [',id=139321,bonus_id=1807/1482/3336', ',id=137306,bonus_id=3410/1512/3337', ',id=137349,bonus_id=1532/3412']
d2 = time.clock()
talentmade = talentinput.split('/')
talentchoices = genTalents(talentmade, len(talentmade)-1)



gChoice = gChoice.strip().split(' ')
lista = []

if (gChoice != ['']):
	lista = makeNums2(gChoice, len(gChoice)-1)

lista2 = [i.strip() for i in lista]

f = open('simcoutput.txt', 'w')
count = 0
d3 = time.clock()

amount = len(talentchoices)*max(1,len(lista2))
if (amount > 100):
	choice = str(input('   You\'re about to generate ' + str(amount) + ' simulation profiles, continue? (y/n): '))
	if (choice != 'y'):
		quit()

print()
d4 = time.clock()

for tal in talentchoices:
	if (len(lista2) > 0):
		for i in lista2:
			count += 1
			print('\r   ' + str(round(100*count/len(lista2)/len(talentchoices), 2)) + '%', end='')
			temp = i.split(' ')
			#print('base_gear.simc')
			printToFile(f, count, tal, basegear)
			name = 'name='
			for k,k0,v in zip(names, names0, temp):
				f.write(k+'='+gear[k0][int(v)-1][0] + '\n')
				#f.write(k+'='+k0+'_'+v+'.simc\n')
				#print(k+'='+k0+'_'+v+'.simc')
				name += gear[k0][int(v)-1][1] + ','

			printOverrides(f, overrides)
			f.write(name + '_' + tal + '\n')
			f.write('\n')
			#print('name=' + i)
			#print()
	else:
		count += 1
		print('\r   ' + str(round(100*count/len(talentchoices), 2)) +'%', end='')
		#print('base_gear.simc')
		printToFile(f, count, tal, basegear)
		printOverrides(f, overrides)
		name = 'name=base_gear,'

		f.write(name + '_' + tal + '\n')
		f.write('\n')


f.flush()
f.close()
end = time.clock()

print('\n')
print('   ' + str(max(1,len(lista2))*len(talentchoices)) + ' simulation profiles generated.')
print('   %(num).2f seconds to generate output file.' % {'num': (end-d4) + (d3-d2) + (d1-start)})
print()