# file: rfcomm-server.py
# auth: Albert Huang <albert@csail.mit.edu>
# desc: simple demonstration of a server application that uses RFCOMM sockets
#
# $Id: rfcomm-server.py 518 2007-08-10 07:20:07Z albert $

from bluetooth import *
import thread

def newServer(portNumber):
    server_sock=BluetoothSocket( RFCOMM )
    server_sock.bind(("",portNumber))
    server_sock.listen(portNumber)
    print "server listening on port " + str(portNumber)

    port = server_sock.getsockname()[1]
    uuid = "1e0ca4ea-299d-4335-93eb-27fcfe7fa84" + str(portNumber) 
    
    print uuid
    
    advertise_service( server_sock, "SampleServer",
                       service_id = uuid,
                       service_classes = [ uuid, SERIAL_PORT_CLASS ],
                       profiles = [ SERIAL_PORT_PROFILE ], 
                       protocols = [ OBEX_UUID ] 
                        )
    
    print "Advertising"
               
    print("Waiting for connection on RFCOMM channel %d" % port)
    client_sock, client_info = server_sock.accept()
    print("Accepted connection from ", client_info)
    
    try:
        while True:
            data = client_sock.recv(1024)
            if len(data) == 0: break
            print("received [%s]" % data)
    except IOError:
        pass
    print("disconnected")

    client_sock.close()
    server_sock.close()
    print("all done")

    
newServer(0) 
