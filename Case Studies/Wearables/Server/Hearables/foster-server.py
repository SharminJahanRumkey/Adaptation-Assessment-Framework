from bluetooth.ble import BeaconService
import time
import thread
from bluetooth import *
import random
import Tkinter as tk
from PIL import ImageTk, Image


def advertiseForFostering():
	service = BeaconService()
	while True:
		service.start_advertising("11111111-2222-3333-4444-555555555555",1, 1, 1, 200)
		time.sleep(15)
	service.stop_advertising()

	print("Done.")


def generateCodes():
    codes = [0] *100
    random.seed(51)
    for i in range(100):
        codes[i] = random.random()
    return codes

codes = generateCodes()

secure = True
def changeSecurityState():
	global secure
	global label
	secure = not secure
	print("State changed. Currently: " + str(secure))
	global ports
	for port in ports:
		if port in "B8:27:":
			sendData(ports[port], "State: " + str(secure))
	if secure:
		label.config(text="Secure", background="green", foreground="black")
	else:
		label.config(text="Insecure", background="yellow", foreground="black")

def sendDisconnect():
	print("Sending Disconnect Signal")
	for port in ports:
		if port in "B8:27:":
			sendData(ports[port], "State: Disconnect")
			ports[port].close()
			print(ports[port])

	label.config(text="Disconnect", background="red", foreground="black")

def receiveDisconnect():
	print("Received Disconnect Signal from Wearable")
	global label
	for port in ports:
		if port in "B8:27:":
			ports[port].close()
			print("closed " + port)
	label.config(text="Disconnected", background="red", foreground="black")	
	print("Changed Label")

def sendReconnect():
        uuid = "1e0ca4ea-299d-4335-93eb-27fcfe7fa801"
	for port in ports:
		if port in "B8:27:":
			connect(uuid, port, "State: Reconnect")
			ports[port].close()
			ports[port] = None
			print("Sent REconnect")

	label.config(text="Reconnecting", background="yellow", foreground="black")

def sendData(sock, data):
	sock.send(data)


ports ={} 
def connect(uuid, address, initialString):    
    print(address)
    service_matches = find_service( uuid = uuid, address = address  )
    print(service_matches)
    first_match = service_matches[0] #service_matches[0]
    port = first_match["port"]
    name = first_match["name"]
    host = first_match["host"]
    print("connecting to \"%s\" on %s" % (name, host))
        # Create the client socket
    sock=BluetoothSocket( RFCOMM )
    sock.connect((host, port))
    global ports
    ports[address] = sock
    sock.send("99," + str(codes[99]))
    print("connected.  type stuff")
    sock.send(initialString)

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
    global label
    global secure
    while True:
        client_sock,address = server_sock.accept()
        f = open("test.txt", "r")
        if str(address[0]) not in f.read():
            print "Device not connected before, Closing"
            client_sock.close()
            sys.exit(0)
        print "Accepted connection from ",address
	stop_advertising(server_sock)
	
	connect("1e0ca4ea-299d-4335-93eb-27fcfe7fa801", address[0], str(secure))
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
            print("Code not there")
	    client_sock.close()
        try:
	    count = 0
            while True:
                data = client_sock.recv(1024)
		result = data.split("|")
		if result[0] == "N" and secure:
			label.config(text="Secure", background="green", foreground="black")
		elif result[0] == "E":
			label.config(text="Empty", background="yellow", foreground="black")
		elif result[0] == "D":
			print(data)
			receiveDisconnect()
		if count % 5 == 0:	
                	print data
		count = count+1
        except:
            pass
	client_sock.close()
	
	advertise_service( server_sock, "SampleServer", uuid )	

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
	while True:
		client_sock,address = server_sock.accept()

		print("fostered with \"%s\" on %s" % (address, port))
		sock = BluetoothSocket(RFCOMM)
		sock.connect((address[0],port))
		
		sendData(sock, "State: " + str(secure))	
       		#sock.send("State: " + str(secure))
		data = client_sock.recv(1024)
       		# Sharmin I will acprint data
		#respond with data gained from server
		#sock.send(data)
		#sock.close()
		client_sock.close()
		print("Closed Connections after fostering")
		#changeSecurityState()	


thread.start_new_thread(advertiseForFostering,())
thread.start_new_thread(startServer, (1,))
for i in range(1,8):
	thread.start_new_thread(startServer, (i*2,))
	time.sleep(.1)
#for i in range(1,8):
thread.start_new_thread(startFosterServer, (30,))
time.sleep(.1)
print("Printing after threading")


root = tk.Tk()
root.title("Secure/Insecure")
root.columnconfigure(0, pad=3)
root.columnconfigure(1, pad=3)
root.columnconfigure(2, pad=3)
root.columnconfigure(3, pad=3)
root.rowconfigure(0, pad=3)
root.rowconfigure(1, pad=3)
root.rowconfigure(2, pad=3)
root.rowconfigure(3, pad=3)
root.rowconfigure(4, pad=3)

path = "Hearables.png"
img = ImageTk.PhotoImage(Image.open(path).resize((100,100), Image.ANTIALIAS )) 
panel = tk.Label(root, image = img, width=100, height=100)

panel.grid(row=0, column=3)

label = tk.Label(root,text="Waiting for Connection", width=35,  height=4) 
label.configure(background="green", foreground="purple", font=("Courier", 25))
label.grid(row=0, column=0, columnspan=3)


disconnect = tk.Button(root, text="Tell Wearable to Disconnect",wraplength=200, width=25, height=3, font=("Courier", 18), command=sendDisconnect)
#disconnect.configure(background="yellow", foreground="red")
disconnect.grid(row=1, column = 2)
reconnect = tk.Button(root, text="Reconnect after disconnect", wraplength=150,width=25, height=3,  font=("Courier", 18), command=sendReconnect)
reconnect.grid(row=2, column=2)
switch = tk.Button(root, text="Switch State", wraplength=150,width=25, height=3, font=("Courier", 18), command=changeSecurityState)
switch.grid(row=1, column=0)
button = tk.Button(root, text='Stop', width=25, command=root.destroy)
button.grid(row=3, column=3)
root.mainloop()
#while 1:
#	pass
