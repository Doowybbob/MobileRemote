package com.doowybbob.MRClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	PrintWriter out;
	BufferedReader in;
	Socket server;
	
	/**
	 * Connect to the server and initialize the input and output streams.
	 * @param host Server IP
	 * @param port The port that the server listens on
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Client (String host, int port) throws UnknownHostException, IOException {
		this.server = new Socket (host, port);
		this.out = new PrintWriter(server.getOutputStream(), true);
	    this.in = new BufferedReader(new InputStreamReader(server.getInputStream()));
	}
	
	/**
	 * Safely shut the client down. Close all input and output streams and exit.
	 * @throws IOException
	 */
	private void exit () throws IOException {
		out.close();
		in.close();
		server.close();
		System.exit(0);
	}
	
	/**
	 * Send a command to the server. This method will handle all mouse commands.
	 * @param cmd The command to send to the server.
	 * @throws IOException
	 */
	public void send (String cmd) throws IOException {
		System.out.println(cmd);
		out.write(cmd + "\n");
		
		out.flush();
		in.readLine();
		
		if (cmd.equals("exit")) {
			exit();
		}
	}
	
	/**
	 * Send a string of text to the server. First send the command wr to tell the 
	 * server that we're sending a string.
	 * @param s The string of text to send to the server
	 * @throws IOException
	 */
	public void sendText (String s) throws IOException{
		System.out.println(s);
		out.write("wr\n");
		out.write(s + "\n");
		out.flush();
		in.readLine();
	}
	
	/**
	 * Connect to a server and then send commands that are entered
	 * on the command line. 
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		System.out.println("Starting Client");

		Client c = new Client("192.168.0.21", 5555);
		System.out.println("Sending");
		
		BufferedReader br = 
                new BufferedReader(new InputStreamReader(System.in));

        String input;

        while((input=br.readLine())!=null){
        	System.out.println(input);
        	if (input.contains("wr")) { // The write command (wr) expects a newline before the string being sent is entered
        		c.sendText(br.readLine());
        	} else {
        		c.send(input);
        	}
        }
		
	}

}
