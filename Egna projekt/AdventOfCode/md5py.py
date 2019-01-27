import hashlib, binascii
import math
from struct import *

def lR(x, c):
    x &= 0xffffffff
    return ((x << c)|(x >> (32-c)))&0xffffffff


def F(b, c, d):
    return (b & c) | (~b & d)

def G(b, c, d):
    return (d & b) | (~d & c)

def H(x, y, z):
    return x ^ y ^ z

def I(x, y, z):
    return y ^ (x | (~z))

def md5_to_hex(digest):
    raw = digest.to_bytes(16, byteorder='little')
    return '{:032x}'.format(int.from_bytes(raw, byteorder='big'))

s = [7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,
    5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20,
    4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,
    6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21]

init_values = [0x67452301, 0xefcdab89, 0x98badcfe, 0x10325476]

K = [int(abs(math.sin(i+1)) * 2**32) & 0xFFFFFFFF for i in range(64)]



def md5(message):

    a0 = 0x67452301
    b0 = 0xefcdab89
    c0 = 0x98badcfe
    d0 = 0x10325476

    message = bytearray(message)

    olen = (8*len(message)) #not modulo (fix)
    message.append(0x80)
    while len(message)%64 != 56:
        message.append(0)
    message += olen.to_bytes(8,byteorder='little')


    hash_pieces = init_values[:]

    for ch_o in range(0,len(message),64):
        A,B,C,D = hash_pieces
        chunk = message[ch_o:ch_o+64]

        for i in range(64):

            if i < 16:
                f = F(B,C,D)
                g = i
            elif i < 32:
                f = G(B,C,D)
                g = (5*i+1)%16
            elif i < 48:
                f = H(B,C,D)
                g = (3*i+5)%16
            else:
                f = I(B,C,D)
                g = (7*i)%16
            rot = A+f+K[i] + int.from_bytes(chunk[4*g:4*g+4], byteorder='little')           

            newB = (B + lR(rot, s[i])) & 0xffffffff

            A,B,C,D = D,newB,B,C

        for i, val in enumerate([A,B,C,D]):
            hash_pieces[i] += val
            hash_pieces[i] &= 0xffffffff


    return sum(x<<(32*i) for i, x in enumerate(hash_pieces))



    
    
    print(format(a0,'x'), format(b0,'x'), format(c0,'x'), format(d0,'x'))
    print(format(output,'x'))
    
def md5_to_hex(digest):
    raw = digest.to_bytes(16, byteorder='little')
    return '{:032x}'.format(int.from_bytes(raw, byteorder='big'))

c = 0
key = b'abcdef'
while True:
    print(c)
    tempkey = key + bytes(c)
    if (md5_to_hex(md5(tempkey))[:5] == '00000'):
        print(c)
        break
    c+=1


