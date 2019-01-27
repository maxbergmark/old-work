import hashlib, binascii

key = 'bgvyzdsv'
c = 0
while True:
    m = hashlib.md5()
    m.update((key+str(c)).encode('utf-8'))
    if (binascii.hexlify(m.digest())[:6] == b'000000'):
        print(c)
        break
    c += 1