import bluetooth

bd_addr = "B8:27:EB:78:F3:5E"

port = 1

sock=bluetooth.BluetoothSocket( bluetooth.RFCOMM )
sock.connect((bd_addr, port))
while True:
	sock.send("hello!!")
	data = sock.recv(1024)
	print("I am recieveing "+ data)

sock.close()