#!/usr/bin/env bash


# Took from https://gist.github.com/destan/b708d11bd4f403506d6d5bb5fe6a82c5

openssl genrsa -out private_key.pem 4096
openssl rsa -pubout -in private_key.pem -out public_key.pem

# convert private key to pkcs8 format in order to import it from Java
openssl pkcs8 -topk8 -in private_key.pem -inform pem -out private_key_pkcs8.pem -outform pem -nocrypt