package com.doowybbob.MRServer;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	BufferedReader in;
	PrintWriter out;
	
	/**
	 * Start the server on a given port. The server will block until one client connects. 
	 * To function nicely, there should only be one client so the server stops accepting new
	 * connections. The server then waits for commands to be received and handles them.
	 * @param port The port to listen on
	 * @throws IOException
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	Server (int port) throws IOException, AWTException, InterruptedException {
		System.out.println("Starting Server");
		ServerSocket socket = new ServerSocket(port);
		Socket client = socket.accept(); // accept one client
		System.out.println("Connected");
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintWriter(client.getOutputStream(),true);
		
		listen(); // listen until the client exits. Then exit gracefully as well.
		System.out.println("Closing");
		in.close();
		out.close();
		socket.close();
		client.close();
	}
	
	/**
	 * The server will listen for new commands and then handle them using the 
	 * ServerLib helper methods.
	 * @throws IOException
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public void listen() throws IOException, AWTException, InterruptedException {
		String inputLine;
		String [] args;
		System.out.println("Listening");

		while ((inputLine = in.readLine()) != null) { // block until a command is received
			
			System.out.println(inputLine);
	        if (inputLine.equals("exit")) { // The client is shutting down so the server should also exit
	        	out.write("OK\n");
	            break;
	        }
	        else if (inputLine.contains("mm")) { // Move mouse - mm:X:Y
	        	args = inputLine.split(":");
	        	// args[1] will be the X amount to move, args[2] will be the Y amount to move.
	        	ServerLib.moveMouseBy(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
	        	System.out.println("move to: " + args[1] + " " + args[2]);
	        	out.write("OK\n");
	        }
	        else if (inputLine.contains("lc")) { // left click
	        	ServerLib.click(1); // 1 is left click
	        	System.out.println("Left Click");
	        	out.write("OK\n");
	        }
	        else if (inputLine.contains("rc")) { // right click
	        	ServerLib.click(2); // 2 is right click
	        	System.out.println("Right Click");
	        	out.write("OK\n");
	        }
	        else if (inputLine.contains("sd")) { // scroll down
	        	ServerLib.scroll(1); // scroll down by 1 "wheel click"
	        	System.out.println("Scroll Down");
	        	out.write("OK\n");
	        }
	        else if (inputLine.contains("su")) { // scroll up
	        	ServerLib.scroll(-1); // scroll up by 1 "wheel click"
	        	System.out.println("Scroll Up");
	        	out.write("OK\n");
	        }
	        else if (inputLine.contains("wr")) { // type a string 
	        	String text = in.readLine(); // The string is on the next line of the buffer
	        	ServerLib.typeString(text);
	        	System.out.println("Type: " + text);
	        	out.write("OK\n");
	        }
	        else {
	        	out.write("BAD\n"); // something went wrong
	        }
	        out.flush();
	    }
		
		System.out.println("Exiting");
		return;
	}
	
	/**
	 * Create a new server that listens on port 5555.
	 * @param args
	 * @throws AWTException 
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws AWTException, InterruptedException, IOException {
		Server s = new Server(5555);
	}

}
