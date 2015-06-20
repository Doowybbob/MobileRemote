# MobileRemote
A client server application that allows remote control of a computer from an android phone.

### About
The MobileRemote application allows for the mouse and keyboard on a desktop computer to be emulated using an android phone. 
This is done by running a server on the host desktop computer and a client on an android phone. The client connects to the server and 
sends commands to the server based on the user's input to the app. The server then handles the commands and sends some input to the desktop 
computer to emulate either the mouse or keybord.

##### Server
The server source code is found in the desktop/MobileRemote/src directory.
It consists of two files Server.java and ServerLib.java. The server file handles all communication with the client and uses the methods in ServerLib to 
interact directly with the host computer. The ServerLib file contains methods that only interact with the host computer and emulate most of the functionality 
of a mouse and keyboard.

##### Client
There are two versions of the client that can be run, an android application and a desktop commandline interface. 

The main client would be the android application. It allows a user to move the mouse using a phone's touchscreen as a track pad. Make left and right clicks 
and send text to be typed on the host computer. The client will handle these gestures and inputs and send them as commands to the server. 
This client can be found in android/MobileRemote/src.

The desktop client can be found in the desktop/MobileRemote/src directory. This is a command line interface that the user 
can use to manually send commands to the server. This client is really just for testing the server functionality and debugging.



