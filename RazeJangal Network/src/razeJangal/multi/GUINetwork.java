package razeJangal.multi;

import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.Socket;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;



public class GUINetwork {
	private class MainFrame extends JFrame {
		{
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLayout(null);
			this.setResizable(false);
			
			
			this.setSize(400, 350);
			
			this.setVisible(true);
			
			//this.setLocation(200, 100);
			
			
		}
		
		public MainFrame() {
			super("Raze Jangal - Network");
		}
	}
	
	private class ClientButton extends JButton implements MouseListener {
		{
			this.setBounds(200, 0, 200, 50);
			this.addMouseListener(this);
		}
		public ClientButton() {
			super("Client");
		}

		public void mouseClicked(MouseEvent e) {
			serverSelected = false;
			
			hideAll();
			
			ip.setVisible(true);
			ipLabel.setVisible(true);
			port.setVisible(true);
			portLabel.setVisible(true);
			map.setVisible(true);
			mapLabel.setVisible(true);
			startButton.setVisible(true);
			playerName.setVisible(true);
			playerNameLabel.setVisible(true);
		}
		public void mousePressed(MouseEvent e) {
			
		}
		public void mouseReleased(MouseEvent e) {
			
		}
		public void mouseEntered(MouseEvent e) {
			
		}
		public void mouseExited(MouseEvent e) {
			
		}
	}
	
	private class ServerButton extends JButton implements MouseListener {
		{
			this.setBounds(0, 0, 200, 50);
			this.addMouseListener(this);
		}
		public ServerButton() {
			super("Server");
		}
		public void mouseClicked(MouseEvent e) {
			serverSelected = true;
			
			hideAll();
			
			port.setVisible(true);
			portLabel.setVisible(true);
			map.setVisible(true);
			mapLabel.setVisible(true);
			numOfPlayers.setVisible(true);
			numOfPlayersLabel.setVisible(true);
			startButton.setVisible(true);
		}
		public void mousePressed(MouseEvent e) {
			
		}
		public void mouseReleased(MouseEvent e) {
			
		}
		public void mouseEntered(MouseEvent e) {
			
		}
		public void mouseExited(MouseEvent e) {
			
		}
	}
	
	private class StartButton extends JButton implements MouseListener {
		{
			this.setBounds(100, 250, 200, 50);
			this.addMouseListener(this);
		}
		public StartButton() {
			super("Start!");
		}
		public void mouseClicked(MouseEvent e) {
			System.out.println("Start Pressed!");
			extractData();
			new Thread() {
				public void run() {
					
					if (serverSelected) { // Start Server
						new SendFileOverNet(mapD, portD+1);
						new CoreServer(mapD, portD, numOfPlayersD);
					} else { // Start Client
						new GUI_Client(ipD, portD, mapD, playerNameD);
					}
					
				};
			}.start();
		}
		public void mousePressed(MouseEvent e) {
			
		}
		public void mouseReleased(MouseEvent e) {
			
		}
		public void mouseEntered(MouseEvent e) {
			
		}
		public void mouseExited(MouseEvent e) {
			
		}
	}
	
	private MainFrame mainFrame;
	
	private ServerButton serverButton;
	private ClientButton clientButton;
	
	private JTextField ip;
	private JTextField port;
	private JTextField map;
	private JTextField numOfPlayers;
	private JTextField playerName;
	
	private JLabel ipLabel;
	private JLabel portLabel;
	private JLabel mapLabel;
	private JLabel numOfPlayersLabel;
	private JLabel playerNameLabel;
	
	private String ipD;
	private int portD;
	private String mapD;
	private int numOfPlayersD;
	private String playerNameD;
	
	private boolean serverSelected;
	
	
	private StartButton startButton;
	
	public GUINetwork() {

		mainFrame = new MainFrame();
		
		serverButton = new ServerButton();
		clientButton = new ClientButton();
		
		ip = new JTextField();
		port = new JTextField();
		map = new JTextField();
		numOfPlayers = new JTextField();
		playerName = new JTextField();
		
		mainFrame.add(serverButton);
		mainFrame.add(clientButton);
		
		ipLabel = new JLabel("IP:");
		ipLabel.setBounds(70, 70, 180, 20);
		mainFrame.add(ipLabel);
		portLabel = new JLabel("Port:");
		portLabel.setBounds(70, 100, 180, 20);
		mainFrame.add(portLabel);
		mapLabel = new JLabel("Map Path:");
		mapLabel.setBounds(70, 130, 180, 20);
		mainFrame.add(mapLabel);
		numOfPlayersLabel = new JLabel("Num of Players (2-4):");
		numOfPlayersLabel.setBounds(70, 160, 180, 20);
		mainFrame.add(numOfPlayersLabel);
		playerNameLabel = new JLabel("Player Name:");
		playerNameLabel.setBounds(70, 190, 180, 20);
		mainFrame.add(playerNameLabel);
		
		ip.setBounds(200, 70, 150, 20);
		mainFrame.add(ip);
		port.setBounds(200, 100, 150, 20);
		mainFrame.add(port);
		map.setBounds(200, 130, 150, 20);
		mainFrame.add(map);
		numOfPlayers.setBounds(200, 160, 150, 20);
		mainFrame.add(numOfPlayers);
		playerName.setBounds(200, 190, 150, 20);
		mainFrame.add(playerName);
		
		startButton = new StartButton();
		mainFrame.add(startButton);
		
		
		hideAll();

		
		ip.setText("localhost");
		port.setText("30777");
		map.setText("default");
		numOfPlayers.setText("2");
		playerName.setText("Player" + new Random().nextInt(100));
		
		
		
	}
	
	private void extractData() {
		ipD = ip.getText();
		portD = Integer.parseInt(port.getText());
		mapD = map.getText();
		numOfPlayersD = Integer.parseInt(numOfPlayers.getText());
		playerNameD = playerName.getText();
	}
	
	private void hideAll() {
		ip.setVisible(false);
		ipLabel.setVisible(false);
		port.setVisible(false);
		portLabel.setVisible(false);
		map.setVisible(false);
		mapLabel.setVisible(false);
		numOfPlayers.setVisible(false);
		numOfPlayersLabel.setVisible(false);
		startButton.setVisible(false);
		playerName.setVisible(false);
		playerNameLabel.setVisible(false);
	}
}
