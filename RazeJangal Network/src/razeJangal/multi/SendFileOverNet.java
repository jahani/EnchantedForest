/**
 * 
 * http://stackoverflow.com/questions/4687615/how-to-achieve-transfer-file-between-client-and-server-using-java-socket
 * 
 */
package razeJangal.multi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SendFileOverNet extends Thread{
	private String filePath;
	private int port;
	public SendFileOverNet(String filePath, int port) {
		this.filePath = filePath;
		this.port = port;
		this.start();
	}
	
	public void run() {
		while (true) {
			try {
				new TCPServer(filePath, port);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	class TCPServer {

	    //private final String fileToSend = "C:\\test1.pdf";

	    public TCPServer(String fileToSend, int port) {

	        while (true) {
	            ServerSocket welcomeSocket = null;
	            Socket connectionSocket = null;
	            BufferedOutputStream outToClient = null;

	            try {
	                welcomeSocket = new ServerSocket(port);
	                connectionSocket = welcomeSocket.accept();
	                outToClient = new BufferedOutputStream(connectionSocket.getOutputStream());
	            } catch (IOException ex) {
	                // Do exception handling
	            }

	            if (outToClient != null) {
	                File myFile = new File( fileToSend );
	                byte[] mybytearray = new byte[(int) myFile.length()];

	                FileInputStream fis = null;

	                try {
	                    fis = new FileInputStream(myFile);
	                } catch (FileNotFoundException ex) {
	                    // Do exception handling
	                }
	                BufferedInputStream bis = new BufferedInputStream(fis);

	                try {
	                    bis.read(mybytearray, 0, mybytearray.length);
	                    outToClient.write(mybytearray, 0, mybytearray.length);
	                    outToClient.flush();
	                    outToClient.close();
	                    connectionSocket.close();

	                    // File sent, exit the main method
	                    return;
	                } catch (IOException ex) {
	                    // Do exception handling
	                }
	            }
	        }
	    }
	}
}
