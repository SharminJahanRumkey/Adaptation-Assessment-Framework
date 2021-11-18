from bluetooth import *
import sys
import random
from bluetooth.ble import BeaconService
import thread
import time
from sense_emu import *
from colorsys import hsv_to_rgb
import numpy as np
import threading
import Tkinter as tk
from subprocess import call
import time 

if sys.version < '3':
    input = raw_input

def clamp(value, min_value, max_value):
    return min(max_value, max(min_value, value))

def scale(value, from_min, from_max, to_min=0, to_max=8):
    from_range = from_max - from_min
    to_range = to_max - to_min
    return (((value - from_min) / from_range) * to_range) + to_min

def render_bar(screen, origin, width, height, color):
    # Calculate the coordinates of the boundaries
    x1, y1 = origin
    x2 = x1 + width
    y2 = y1 + height
    # Invert the Y-coords so we're drawing bottom up
    max_y, max_x = screen.shape[:2]
    y1, y2 = max_y - y2, max_y - y1
    # Draw the bar
    screen[int(y1):y2, x1:x2, :] = color

def display_readings(hat):
    # Calculate the environment values in screen coordinates
    temperature_range = (15, 50)
    pressure_range = (950, 1050)
    humidity_range = (10, 80)
    orientationRange = (0, 360)
    temperature = scale(clamp(hat.temperature, *temperature_range), *temperature_range)
    pressure = scale(clamp(hat.pressure, *pressure_range), *pressure_range)
    humidity = scale(clamp(hat.humidity, *humidity_range), *humidity_range)
   # hat.set_imu_config(True, True, True)
    raw = hat.get_orientation()
                
    roll = scale(clamp(raw["roll"], *orientationRange), *orientationRange)
    pitch = scale(clamp(raw["pitch"], *orientationRange), *orientationRange)
    yaw = scale(clamp(raw["yaw"], *orientationRange), *orientationRange)
    screen = np.zeros((8, 8, 3), dtype=np.uint8)
    render_bar(screen, (0, 0), 2, round(roll), color=(255, 0, 0))
    render_bar(screen, (3, 0), 2, round(pitch), color=(0, 255, 0))
    render_bar(screen, (6, 0), 2, round(yaw), color=(0, 0, 255))
    hat.set_pixels([pixel for row in screen for pixel in row])

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


connected = True
secure = True
def changeSecurityState(state):
	global secure
        global label
        print("State: " + state)
        if "False" in state:
	    secure = False
            label.config(text="Get Signal", background="green", foreground="black")

        if "True" in state:
            secure = True
            label.config(text="Get Signal", background="green", foreground="black")


        if not secure:
            multRes = [[1 for x in range(3)] for y in range(5)]  
            print(os.getcwd())
            os.chdir("insulinpumppns/IP_R1_A1/src/")
            for i in range(3):
                for j in range(5):
                    if i > 0 and j > 0:
                        print(os.getcwd())
                        os.chdir("../../IP_R"+ str(i) + "_A" + str(j) + "/src/")
                        os.system("java IP" )
                        #call(["java", "IP"])
                        print("Ran program")
                        token = open("redToken_R"+ str(i) + "_A" + str(j)+".txt")
                        redToken = {}
                        redToken = token.readlines()
                        multRes[j][i] = 1
                        for redTok in redToken:
                            temp = redTok.split(";")
                            for val in temp:
                                multRes[j][i] = multRes[j][i] * float(val)
	    #print("I am security")
            os.chdir("../../AOM_Operators/src/")
	    os.system("java SAC")
	    result =[0 for x in range(5)] 
            print(range(len(multRes[1])))
            for i in range(len(multRes[1])):
                for j in range(len(multRes)):
                  #  print( "i:" + str(i) + " j:" + str(j))
                    if multRes[j][i] != 1:
                        result[j] = result[j]+multRes[j][i]
                      #  print(result)
                    #    print multRes[j][i]
                #print result[j]
            #Skip result 1, as it is the default
	    

            
            # os.chdir("../../../")
            # print(os.getcwd())
	    
	    os.chdir("../../AOM_Operators/src/")
	    os.system("java SAC)
		
	
	    
	    print("interaction protocol")
	    os.chdir("../../Client_HRVM_SAC/")
            os.system("sudo python interaction.py" )

 	   

	    print("Plan 1: Stay connected, Send empty packets, Fostering is allowed")
	    label.config(text="Stay connected, Send empty packets, Fostering is allowed", background="yellow", foreground="black")
            thread.start_new_thread(startFosterServer,(30,))
	    

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
        addr = beacon[i][0]
        print(beacon[i])
        print("attempting to foster with " + str(beacon[i][0]))
        uuid = "2e0ca4ea-299d-4335-93eb-27fcfe7fa830"
        service_matches = find_service( uuid = uuid, address = addr )
        for i in service_matches:
            print i
        if len(service_matches) == 0:
            print("couldn't find the SampleServer service =( " + uuid)
        if len(service_matches) > 0:
            thread.start_new_thread(fosterConnect, (service_matches[0],))
            time.sleep(1)
	    break
            
    if len(service_matches)== 0:
        print("No matches available")
        #sys.exit(0)




def connectToServer():
    addr = None
    beacon = scanForBeacon()
    
    print("Connecting to Server")
    print(beacon)
    for i in range(len(beacon)):
	print(beacon[i][0])
    	if "B8:27:" in beacon[i][0]:
		addr = beacon[i][0]


#Set here to only connect to CWPi1, change to connect to a different system
    if "B8:27:" in addr:
	print("Found correct beacon, connecting")
        for i in range(31):
            if i < 10:
                uuid = "1e0ca4ea-299d-4335-93eb-27fcfe7fa80" + str(i)
            else:
                uuid = "1e0ca4ea-299d-4335-93eb-27fcfe7fa8" + str(i)
            service_matches = find_service( uuid = uuid, address = addr )
            if len(service_matches) == 0:
                print("couldn't find the SampleServer service =( " + uuid)
            if len(service_matches) > 0 and str(beacon[0][1][0]) in "11111111-2222-3333-4444-555555555555":
                connect(service_matches[0])
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
        global toldFalse
        if "False" in data:
            toldFalse = True
        changeSecurityState(data)
	#respond with data gained from server
	sock.send(data)
#	sock.close()
	client_sock.close()
	#server_sock.close()
        client_sock.close()

def startFosterServer(portNumber):
    #portNumber = 10
    server_sock=BluetoothSocket( RFCOMM )
    server_sock.bind(("",portNumber))
    server_sock.listen(portNumber-1)
    port = server_sock.getsockname()[1]
    print "listening on port %d" % port
    uuid = "2e0ca4ea-299d-4335-93eb-27fcfe7fa8" + str(portNumber)
    print str(uuid)
    advertise_service( server_sock, "SampleServer", uuid )
    global secure
    while not secure:
        client_sock,address = server_sock.accept()
        print("fostered with \"%s\" on %s" % (address, port))
        sock = BluetoothSocket(RFCOMM)
        sock.connect((address[0],port))
                                                                                            
        sendData(sock, "State: " + str(secure)) 
        #sock.send("State: " + str(secure))
        data = client_sock.recv(1024)
        print data
        client_sock.close()
    print("Closed Connections after fostering")

toldFalse = False

def startServer(portNumber):
    global secure
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
    global lock
    global toldFalse
    while True:
	print("Starting loop")
        client_sock,address = server_sock.accept()
        print("after server accept in startServer" + str(address[0]))
        f = open("test.txt", "r")
        if str(address[0]) not in f.read():
            print "Device not connected before, Closing"
            client_sock.close()
            sys.exit(0)
        f.close()
        print "Accepted connection from ",address
        #check initial message is correct
        check = False
        data = client_sock.recv(1024)
        print("server send data " +data)
        code = data.split(",")
        '''for i in range(100):
            tempStr = (str(i) + "," + str(codes[i]))
            if data in tempStr and len(data) == len(tempStr):
                check = True
                break
        '''
        print("next "+ str(codes[int(code[0])]))
        print("next1 " + str(code[1]))
        if str(codes[int(code[0])]) not in str(code[1]):
            print("Not correct code")
            client_sock.close()
        try:
            while True:
                data = client_sock.recv(1024)
		print("server secure state data " +data)
		print("Loop data " +data)
                if "True" in data:
                    toldFalse = False
                    changeSecurityState("True")
		    print("I Loop data " +data)
                if "False" in data:
                    print("told to change security state")
                    toldFalse = True
	            changeSecurityState("False")
                    
                if "Disconnect" in data:
                    disconnectDevice()
                    client_sock.close()
                    break
                if "Reconnect" in data:
                    print("In Reconnect")
                    print("WHALE")
                    reconnect()
                    changeSecurityState(str(True))
                
                                    #else:
                #    print("Hit final else")
                #    break
        except Exception as e:
            print(e)
            pass
            
        print("Whale")
        
    client_sock.close()
ports = {}
hat = SenseHat()
disconnected = False
hat.clear()
hat.set_rotation(180)

def getDisconnected():
    global disconnected
    return disconnected

def setDisconnected(state):
    global disconnected
    global label
    disconnected = state
    if disconnected:
        label.config(text="Wearable Disconnected", background="red", foreground="black")
    else:
        label.config(text="Get Signal", background="green", foreground="black")

def disconnectDevice():
    global label
    setDisconnected(True)
    #changeSecurityState(str(False))
    time.sleep(.5)
    print("In disconnect")
    for port in ports:
        ports[port].send("D|State: Disconnect")
        ports[port].close()
        ports[port] = None
        print("disconnecting")
    time.sleep(.25)               
    hat.show_letter("U", text_colour=[255,0,0])
    label.config(text="Disconnected", background="red", foreground="black")
        
                    

def reconnect():
    setDisconnected(False)
    changeSecurityState(str(True))
    print("Trying to reconnect")
    hat.show_letter("S", text_colour=[0,255,0])
    for port in ports:
        print(port)
        print(ports[port])
        ports[port].connect((port, 1))
        print("connect attempt successful")
        ports[port].send("99," + str(codes[99]))
        time.sleep(.5)

    print("reconnecting done")
    


sharedBuffer = {}

def connect(match):   
    print("starting server")
    global ports
    
    #thread.start_new_thread(connect((host, port))
    port = match["port"]
    name = match["name"]
    host = match["host"]
    

    print("connected.  type stuff")
    count = 0
    sock = None
    global connected
    global hat
    global secure
    global sharedBuffer
    global toldFalse
    while True:
      #  hat.set_imu_config(True, True, True)
        raw = hat.get_orientation()
            
        if secure and not getDisconnected():
		
                if sock == None:
                    sock = BluetoothSocket( RFCOMM )
                    ports[host] = sock
                    sock.connect((host, port))
                    sock.send("99," + str(codes[99]))
                    time.sleep(.5)
                    print(sock)
                    print("Whale")
                
           
                sock.send("Rumkey")
			
                sharedBuffer = {}
                humidity = hat.humidity
                temp = hat.temperature
                pressure = hat.pressure
                #data = '%s' % (raw['roll'])
		data = str(pressure)
                if raw['roll'] > 200 and raw['roll'] < 300 and secure:
                    
                    hat.show_letter("U", text_colour=[255,0,0])
                    changeSecurityState(str(False))
                    sock.send("E|"+data)
                    label.config(text="Empty", background="yellow", foreground="black")
	        else:
                    hat.show_letter("S", text_colour=[0,255,0])
                    sock.send("N|" + data)
                    label.config(text="Get Signal", background="green", foreground="black")
    			
                 #input()
		if len(data) == 0: 
			connected = False
			break    
 		#sock.send(data)
                
                time.sleep(.25)
	else:
                if (raw['roll'] < 200 or raw['roll'] > 300) and not getDisconnected() and not toldFalse:
                    changeSecurityState(str(True))
                    hat.show_letter("S", text_colour=[0,255,0])
                    sock.send("N|" + data)
	

                humidity = hat.humidity

                temp = hat.temperature
                pressure = hat.pressure
                #data =  'Humidity: %.1f%%; Temperature: %.1fC; Pressure: %.1fmBar; Accelerometer pitch: %s, roll: %s, yaw: %s' % (humidity, temp, pressure, raw['pitch'], raw['roll'], raw['yaw'])
                data = '%s' % (raw['roll'])
		if len(data) == 0: 
			connected = False
			break    
 		sharedBuffer[count] = data
                if not getDisconnected() and sock != None:
                    sock.send("")
                else:
                    sock = None
		count = count + 1
                time.sleep(.25)
        #display_readings(hat)
        #time.sleep(15)
	#if count % 10 == 0:
		#changeSecurityState()
    sock.close()


lock = threading.Lock()
thread.start_new_thread(startServer, (1,))
#fosterWithServer()
thread.start_new_thread(connectToServer,())

root = tk.Tk()
root.title("Wearable Adaptation Simulation")
root.columnconfigure(0, pad=3)
root.columnconfigure(1, pad=3)
root.columnconfigure(2, pad=3)
root.columnconfigure(3, pad=3)
root.rowconfigure(0, pad=3)
root.rowconfigure(1, pad=3)
root.rowconfigure(2, pad=3)
root.rowconfigure(3, pad=3)
root.rowconfigure(4, pad=3)

label = tk.Label(root,text="Connecting to Base Station", width=70,  height=4) 
label.configure(background="green", foreground="purple", font=("Courier", 20))
label.grid(row=0, column=0, columnspan=4)

disconnect = tk.Button(root, text="Wearable decides to Disconnect", wraplength=150, width=25, height=5,  font=("Courier", 18), command=disconnectDevice)
disconnect.grid(row=1, column = 2)

#reconnect = tk.Button(root, text="Reconnect after disconnect", width=25, height=5, command=sendReconnect)
#reconnect.grid(row=1, column=2)

switch = tk.Button(root, text="Wearable decides to switch state", wraplength=150, width=25, height=5,font=("Courier", 18),command=lambda:changeSecurityState(str(not secure)))
switch.grid(row=1, column=1)

foster = tk.Button(root, text="Foster with other device", wraplength=150, width=25, height=5, font=("Courier", 18),command=fosterWithServer)
foster.grid(row=1, column=3)

button = tk.Button(root, text='Stop', width=25, command=root.destroy)
button.grid(row=3, column=3)

root.mainloop()


