package razeJangal.multi;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ReciveFileOverNet {
	ReciveFileOverNet (String fileOutput, String ip, int port)  {
		new TCPClient(fileOutput, ip, port);
	}
}

class TCPClient {
	
	
	private String serverIP = "127.0.0.1";
	private int serverPort = 3248;
	private String fileOutput = "C:\\testout.pdf";
	
	public TCPClient(String fileOutput, String ip, int port) {
		this.fileOutput = fileOutput;
		this.serverIP = ip;
		this.serverPort = port;
		
		
		byte[] aByte = new byte[1];
		int bytesRead;
		
		Socket clientSocket = null;
		InputStream is = null;
		
		try {
			clientSocket = new Socket( serverIP , serverPort );
			is = clientSocket.getInputStream();
		} catch (IOException ex) {
			// Do exception handling
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		if (is != null) {
			
			FileOutputStream fos = null;
			BufferedOutputStream bos = null;
			try {
				fos = new FileOutputStream( fileOutput );
				bos = new BufferedOutputStream(fos);
				bytesRead = is.read(aByte, 0, aByte.length);
				
				do {
					baos.write(aByte);
					bytesRead = is.read(aByte);
				} while (bytesRead != -1);
				
				bos.write(baos.toByteArray());
				bos.flush();
				bos.close();
				clientSocket.close();
			} catch (IOException ex) {
				// Do exception handling
			}
		}
	}
}