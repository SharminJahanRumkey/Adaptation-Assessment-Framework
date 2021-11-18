# file: rfcomm-client.py
# auth: Albert Huang <albert@csail.mit.edu>
# desc: simple demonstration of a client application that uses RFCOMM sockets
#       intended for use with rfcomm-server
#
# $Id: rfcomm-client.py 424 2006-08-24 03:35:54Z albert $

from bluetooth import *
import sys
import random

codes = [0] *100
random.seed(51)
for i in range(100):
    codes[i] = random.random()

print codes[1]

if sys.version < '3':
    input = raw_input

addr = None

if len(sys.argv) < 2:
    print("no device specified.  Searching all nearby bluetooth devices for")
    print("the SampleServer service")
else:
    addr = sys.argv[1]
    print("Searching for SampleServer on %s" % addr)

# search for the SampleServer service
for i in range(9):
    uuid = "1e0ca4ea-299d-4335-93eb-27fcfe7fa84" + str(i)
    service_matches = find_service( uuid = uuid, address = addr )

    if len(service_matches) == 0:
        print("couldn't find the SampleServer service =( " + uuid)
        #sys.exit(0)
    if len(service_matches) > 0:
        break


first_match = service_matches[0]
port = first_match["port"]
name = first_match["name"]
host = first_match["host"]

print("connecting to \"%s\" on %s" % (name, host))

# Create the client socket
sock=BluetoothSocket( RFCOMM )
sock.connect((host, port))

print("connected.  type stuff")
sock.send("99," + str(codes[99]))

while True:
    data = input()
    if len(data) == 0: break
    sock.send(data)

sock.close()
