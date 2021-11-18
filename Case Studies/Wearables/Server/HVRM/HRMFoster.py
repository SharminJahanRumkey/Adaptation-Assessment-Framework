from bluetooth import *
import sys
import random
from bluetooth.ble import BeaconService
import thread
import time

if sys.version < '3':
    input = raw_input


class Beacon(object):
    def __init__(self, data, address):
        self._uuid = data[0]
        self._major = data[1]
        self._minor = data[2]
        self._power = data[3]
        self._rssi = data[4]
        self._address = address
                             
    def __str__(self):
        ret = "Beacon: address:{ADDR} uuid:{UUID} major:{MAJOR}"\
            " minor:{MINOR} txpower:{POWER} rssi:{RSSI}"\
            .format(ADDR=self._address, UUID=self._uuid, MAJOR=self._major,
            MINOR=self._minor, POWER=self._power, RSSI=self._rssi)
        return ret



secure = True
def changeSecurityState():
	global secure
	secure = not secure


def generateCodes():
    codes = [0] *100
    random.seed(51)
    for i in range(100):
        codes[i] = random.random()
    return codes

def scanForBeacon():
    service = BeaconService()
    devices = service.scan(2)

    for address, data in list(devices.items()):
            b = Beacon(data, address)
            print(b)

    return list(devices.items())

print scanForBeacon()

codes = generateCodes()

print codes[0]

    
def fosterWithServer():
    addr = None
    beacon = scanForBeacon()
    for i in range(len(beacon)):
        addr = beacon[0][0]
    for i in range(31):
        if i < 10:
            uuid = "2e0ca4ea-299d-4335-93eb-27fcfe7fa80" + str(i)
        else:
            uuid = "2e0ca4ea-299d-4335-93eb-27fcfe7fa8" + str(i)
        service_matches = find_service( uuid = uuid, address = addr )
        if len(service_matches) == 0:
            print("couldn't find the SampleServer service =( " + uuid)
        if len(service_matches) > 0 and str(beacon[0][1][0]) in "11111111-2222-3333-4444-555555555555":
            thread.start_new_thread(fosterConnect, (service_matches[0],))
            time.sleep(1)
	    break
            
    if len(service_matches)== 0:
        print("No matches available")
        #sys.exit(0)


def connectToServer():
    addr = None
    beacon = scanForBeacon()
    for i in range(len(beacon)):
        addr = beacon[0][0]
    for i in range(31):
        if i < 10:
            uuid = "1e0ca4ea-299d-4335-93eb-27fcfe7fa80" + str(i)
        else:
            uuid = "1e0ca4ea-299d-4335-93eb-27fcfe7fa8" + str(i)
        service_matches = find_service( uuid = uuid, address = addr )
        if len(service_matches) == 0:
            print("couldn't find the SampleServer service =( " + uuid)
        if len(service_matches) > 0 and str(beacon[0][1][0]) in "11111111-2222-3333-4444-555555555555":
            thread.start_new_thread(connect, (service_matches[0],))
            time.sleep(1)
	    break
            
    if len(service_matches)== 0:
        print("No matches available")
        #sys.exit(0)


def fosterConnect(match):
	port = match["port"]
	name = match["name"]
	host = match["host"]
	print("fostering with \"%s\" on %s" % (name, host))
	sock = BluetoothSocket(RFCOMM)
	sock.connect((host,port))
	
	#Get spawn server to listen for infoi
	portNumber = 30
    
	server_sock=BluetoothSocket( RFCOMM )
	server_sock.bind(("",portNumber))
	server_sock.listen(portNumber-1)
	port = server_sock.getsockname()[1]
	print "listening on port %d" % port
        uuid = "2e0ca4ea-299d-4335-93eb-27fcfe7fa8" + str(portNumber)
	print str(uuid)
	advertise_service( server_sock, "SampleServer", uuid )
	client_sock,address = server_sock.accept()
        data = client_sock.recv(1024)
        print data
	#respond with data gained from server
	sock.send(data)
#	sock.close()
	client_sock.close()
	#server_sock.close()

def startServer(portNumber):
    server_sock=BluetoothSocket( RFCOMM )
    #port = bluetooth.get_available_port( bluetooth.RFCOMM )
    server_sock.bind(("",portNumber))
    server_sock.listen(portNumber-1)
    port = server_sock.getsockname()[1]
    print "listening on port %d" % port
    if portNumber < 10:
        uuid = "1e0ca4ea-299d-4335-93eb-27fcfe7fa80" + str(portNumber)
    else:
        uuid = "1e0ca4ea-299d-4335-93eb-27fcfe7fa8" + str(portNumber)
    print str(uuid)
    advertise_service( server_sock, "SampleServer", uuid )
    while True:
	print("Starting loop")
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
		if "State: True" in data and data in "State: True":
		    changeSecurityState()
                else:
                    break
        except:
            pass
        client_sock.close()

def connect(match):    
    thread.start_new_thread(startServer, (1,))
    first_match = match #service_matches[0]
    port = first_match["port"]
    name = first_match["name"]
    host = first_match["host"]
    print("connecting to \"%s\" on %s" % (name, host))
        # Create the client socket
    sock=BluetoothSocket( RFCOMM )
    sock.connect((host, port))
    print("connected.  type stuff")
    sock.send("99," + str(codes[99]))
    count = 0
    while True:
        # Hold for input, may require a slight modification to allow for
        # sending existing data when initially connected.
        data = input()
        if len(data) == 0: break
	if secure:
            if os.path.isfile("storedData.txt"):
			f=open("storedData.txt", "r")
			lines = f.readlines()
    			for i in range(0, len(lines)):
				sock.send(lines[i])
				print(lines[i])
			f.close()
        		os.remove("storedData.txt")
		sock.send(str(match["port"]) + " - " +  data)
	else:
		if not os.path.isfile("storedData.txt"):
			f = open("storedData.txt", "w")	
		f.write(data + '\n')
		sock.send("")
		count = count + 1
        #time.sleep(15)
	'''if count % 10 == 0:
		changeSecurityState()'''
    sock.close()

#fosterWithServer()
connectToServer()

while 1:
    pass
