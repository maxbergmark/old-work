fil = open('ordlista.txt')

ordlista = fil.read().split('\n')
count = 0

print()
for element in ordlista:
    if 'uv' in element:
        print('  ' + element)
        count += 1


print()
print(count)

