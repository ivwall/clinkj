#!/usr/bin/env python
# https://en.bitcoin.it/wiki/Protocol_documentation#Addresses

import hashlib
import base58

# ECDSA bitcoin Public Key
#pubkey = '0450863ad64a87ae8a2fe83c1af1a8403cb53f53e486d8511dad8a04887e5b23522cd470243453a299fa9e77237716103abc11a1df38855ed6f2ee187e9c582ba6'
pubkey =  '04678afdb0fe5548271967f1a67130b7105cd6a828e03909a67962e0ea1f61deb649f6bc3f4cef38c4f35504e51ec112de5c384df7ba0b8d578a4c702b6bf11d5f'
print(pubkey)
print(len(pubkey))
# See 'compressed form' at https://en.bitcoin.it/wiki/Protocol_documentation#Signatures
compress_pubkey = False

def hash160(hex_str):
    sha = hashlib.sha256()
    rip = hashlib.new('ripemd160')
    sha.update(hex_str)
    rip.update( sha.digest() )
    print ( "key_hash = \t" + rip.hexdigest() )
    return rip.hexdigest()  # .hexdigest() is hex ASCII


if (compress_pubkey):
    if (ord(bytearray.fromhex(pubkey[-2:])) % 2 == 0):
        pubkey_compressed = '02'
    else:
        pubkey_compressed = '03'
    pubkey_compressed += pubkey[2:66]
    hex_str = bytearray.fromhex(pubkey_compressed)
else:
    hex_str = bytearray.fromhex(pubkey)

print(hex_str)
# Obtain key:

key_hash = '00' + hash160(hex_str)

# Obtain signature:

sha = hashlib.sha256()
sha.update( bytearray.fromhex(key_hash) )
checksum = sha.digest()
sha = hashlib.sha256()
sha.update(checksum)
checksum = sha.hexdigest()[0:8]

print ( "checksum = \t" + sha.hexdigest() )
print ( "key_hash + checksum = \t" + key_hash + ' ' + checksum )
print ( "bitcoin address = \t" + (base58.b58encode( bytes(bytearray.fromhex(key_hash + checksum)) )).decode('utf-8') )