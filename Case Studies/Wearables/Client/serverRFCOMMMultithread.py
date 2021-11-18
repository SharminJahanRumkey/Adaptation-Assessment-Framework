from bluetooth import *
import thread
import subprocess
import random


codes = [0] * 100
random.seed(51)
for i in range(100):
    codes[i] = random.random()


print str(codes[1])

def startServer(portNumber):
    server_sock=BluetoothSocket( RFCOMM )

    #port = bluetooth.get_available_port( bluetooth.RFCOMM )
    server_sock.bind(("",portNumber))
    server_sock.listen(0)
    port = server_sock.getsockname()[1]
    print "listening on port %d" % port

    uuid = "1e0ca4ea-299d-4335-93eb-27fcfe7fa84" + str(portNumber)
    print str(uuid)
    advertise_service( server_sock, "SampleServer", uuid )

    client_sock,address = server_sock.accept()
    f = open("test.txt", "r")
    if str(address[0]) not in f.read():
        print "Device not connected before, Closing"
        client_sock.close()
        sys.exit(0)
    print "Accepted connection from ",address

    #check initial message is correct
    check = False
    data = client_sock.recv(1024)
    print data
    code = data.split(",")
    '''for i in range(100):
        tempStr = (str(i) + "," + str(codes[i]))
        if data in tempStr and len(data) == len(tempStr):
            check = True
            break
    '''
    print str(codes[int(code[0])])
    print str(code[1])
    if str(codes[int(code[0])]) not in str(code[1]):
        client_sock.close()

    try:
        while True:
            data = client_sock.recv(1024)
            if len(data) > 0:
                print data
            else:
                break
    except:
        pass

    client_sock.close()
    server_sock.close()

#print "Checking for Devices"
#print discover_devices()
startServer(1)
