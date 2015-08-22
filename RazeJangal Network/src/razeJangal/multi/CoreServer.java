package razeJangal.multi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import razeJangal.game.Player;
import razeJangal.sound.Sound;

public class CoreServer {
	private LinkedList<Socket> sockets = new LinkedList<>();
	private ServerSocket serverSocket;
	public CoreServer(final String mapPath,final int port, final int numOfPlayers) {
		
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Core ServerScoket Created!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Thread() {
			public void run() {
				while(true) {
					try {
						sockets.addLast(serverSocket.accept());
						sockets.getLast().setSoTimeout(0);
					} catch (Exception e) {
						
					}
				}
			};
		}.start();
		
		new Thread() {
			public void run() {
				while(true) {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
					}
					if (sockets.size()>=numOfPlayers) {
						Socket[] tempSocket = new Socket[numOfPlayers];
						for (int i = 0; i < tempSocket.length; i++) {
							tempSocket[i] = sockets.removeFirst();
						}
						new GUI_Server(tempSocket, mapPath);
					}
				}
			};
		}.start();
	}
}
