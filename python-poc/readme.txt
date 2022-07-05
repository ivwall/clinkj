objective: 
From "04678afdb0fe5548271967f1a67130b7105cd6a828e03909a67962e0ea1f61deb649f6bc3f4cef38c4f35504e51ec112de5c384df7ba0b8d578a4c702b6bf11d5f",
Generate address 1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa
by a python3 program
because the current ( frik'n ) java version doesn't produce the right answer.

action:
sudo apt install python3-pip

code
#!/usr/bin/env python
# https://en.bitcoin.it/wiki/Protocol_documentation#Addresses

import hashlib
import base58

# ECDSA bitcoin Public Key
pubkey = '0450863ad64a87ae8a2fe83c1af1a8403cb53f53e486d8511dad8a04887e5b23522cd470243453a299fa9e77237716103abc11a1df38855ed6f2ee187e9c582ba6'
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





setup:
dlt01@dlt01:~/git/clinkj/python-poc$ pip3 install base58
Defaulting to user installation because normal site-packages is not writeable
Collecting base58
  Downloading base58-2.1.1-py3-none-any.whl (5.6 kB)
Installing collected packages: base58
  WARNING: The script base58 is installed in '/home/dlt01/.local/bin' which is not on PATH.
  Consider adding this directory to PATH or, if you prefer to suppress this warning, use --no-warn-script-location.
Successfully installed base58-2.1.1

export PATH=

pip3 install hashlib


Hashlib uses OpenSSL for ripemd160 and apparently OpenSSL disabled some older crypto algos around version 3.0 in November 2021. All the functions are still there but require manual enabling. See issue 16994 of OpenSSL github project for details.

To quickly enable it, find the directory that holds your OpenSSL config file or a symlink to it, by running the below command:

openssl version -d
You can now go to the directory and edit the config file (it may be necessary to use sudo):

nano openssl.cnf
Make sure that the config file contains following lines:

openssl_conf = openssl_init

[openssl_init]
providers = provider_sect

[provider_sect]
default = default_sect
legacy = legacy_sect

[default_sect]
activate = 1

[legacy_sect]
activate = 1
Tested on: OpenSSL 3.0.2, Python 3.10.4, Linux Ubuntu 22.04 LTS aarch64, I have no access to other platforms at the moment.


compare link:
https://gobittest.appspot.com/Address


reference links:
code 
https://gist.github.com/circulosmeos/ef6497fd3344c2c2508b92bb9831173f
https://gist.github.com/circulosmeos
circulosmeos/easy-bitcoin-address-from-public-key.py

https://stackoverflow.com/questions/58608285/getting-a-list-of-keys-from-block-0

work through an hash install error
https://stackoverflow.com/questions/72409563/unsupported-hash-type-ripemd160-with-hashlib-in-python
https://stackoverflow.com/questions/72409563/unsupported-hash-type-ripemd160-with-hashlib-in-python


https://learnmeabitcoin.com/technical/p2sh
https://learnmeabitcoin.com/technical/p2pk
https://learnmeabitcoin.com/technical/p2pkh
https://developer.bitcoin.org/devguide/transactions.html
https://rosettacode.org/wiki/Bitcoin/public_point_to_address

