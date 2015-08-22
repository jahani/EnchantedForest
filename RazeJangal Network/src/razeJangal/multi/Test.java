package razeJangal.multi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test {
	public static void main(String[] args) {
		final Socket socket[] = new Socket[2];
		
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(3305);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			socket[0] = serverSocket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			socket[1] = serverSocket.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		new GUI_Server(socket, "default");
		
	}
}
