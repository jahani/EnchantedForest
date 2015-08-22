package razeJangal.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Window;

import java.awt.FlowLayout;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import razeJangal.components.IntArrayMethods;
import razeJangal.components.StringMethods;
import razeJangal.game.BoardCell;
import razeJangal.game.Game;
import razeJangal.game.Player;
import razeJangal.game.BoardCell;
import razeJangal.sound.Sound;

public class GUI {
	private int[] whereCanGo;

	private class DefaultCell extends JLabel {
		protected int index = 0;
		protected JLabel foreGroundLabel;
		{
			this.setSize(24, 24);
			foreGroundLabel = new JLabel();
			foreGroundLabel.setSize(30, 30);
		}
	}
	private class Cell extends DefaultCell implements MouseListener {
		{
			this.addMouseListener(this);
		}

		public Cell(int i) {
			index = i;
			
			
			foreGroundLabel.addMouseListener(this);
			
			refresh();
		}

		public void refresh() {
			String big;

			if (isCellSelectable(index)) {
				big = "Big";
				this.setSize(30, 30);
				this.setLocation(game.board.cell[index].getX() - 15,
						game.board.cell[index].getY() - 15);
			} else {
				big = "";
				this.setLocation(game.board.cell[index].getX() - 12,
						game.board.cell[index].getY() - 12);
				this.setSize(24, 24);
				
			}
			
			this.setIcon(new ImageIcon(".//src/razeJangal/files/" +big+ game.board.cell[index].getColor() +".png"));
			
			try {
				this.remove(foreGroundLabel);
			} catch (NullPointerException e) {
			}

			for (int j = 0; j < game.getPlaces().length; j++) {
				if (game.getPlaces()[j] == index) {
					foreGroundLabel.setIcon(new ImageIcon(".//src/razeJangal/files/Player"
							+ j + ".png"));
					this.add(foreGroundLabel);
				}
			}
			
			
		}
		
		public void mouseClicked(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {
			clickedOn(index);
		}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}
	
	private class BlueCell extends DefaultCell {
		private int playerNumHouse;

		public BlueCell(int i, int x, int y) {
			playerNumHouse = i;
			this.setLocation(x, y);

			foreGroundLabel.setIcon(new ImageIcon(".//src/razeJangal/files/Player"
					+ playerNumHouse + ".png"));

			refresh();
		}

		public void refresh() {
			this.setIcon(new ImageIcon(".//src/razeJangal/files/Blue.png"));
			try {
				this.remove(foreGroundLabel);				
			} catch (NullPointerException e) {
			}
			
			try {
				if (game.getPlaces()[playerNumHouse] == index) {
					this.add(foreGroundLabel);
					return;
				}
			} catch (IndexOutOfBoundsException e) {
			}
		}
	}

	class CellPartner extends JLabel implements MouseListener {
		private int index;

		{
			this.setSize(20, 20);
			this.setIcon(new ImageIcon(".//src/razeJangal/files/Star.png"));
			this.setVisible(false);
			this.addMouseListener(this);
		}

		public CellPartner(int i) {
			index = i;
			this.setLocation(game.board.cell[i].getX() - 20,
					game.board.cell[i].getY() - 20);
		}

		public void visibility(boolean state) {
			this.setVisible(state);
		}

		
		public void mouseClicked(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {
			clickedOnPartner(index);			
		}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}

	}

	private class PrivateMessage extends JButton implements MouseListener {
		private boolean firstAct;
		private String pm;
		{
			this.addMouseListener(this);
		}
		
		public void sendText (String toWhom, String pm) {
			firstAct = true;
			this.pm = pm;
			this.setText("PM to " + toWhom +"!");
			this.setVisible(true);
		}
		
		public void mouseClicked(MouseEvent e) {
			if (firstAct==true) {
				firstAct = false;
				GUI.this.print(pm);
				refreshAll();
				this.setText("Hide!");
			} else {
				GUI.this.print(":)");
				this.setVisible(false);
			}
		}
		public void mousePressed(MouseEvent e) {			}
		public void mouseReleased(MouseEvent e) {			}
		public void mouseEntered(MouseEvent e) {			}
		public void mouseExited(MouseEvent e) {				}
	}
	private class IDontKnow extends JButton implements MouseListener {
		
		{
			this.setText(" I Don't Know!");
			this.addMouseListener(this);
		}
		
		public void mouseClicked(MouseEvent e) {
			inRed=false;
			jp.unVisiblePartner();
			this.setVisible(false);
			optionsBox.unVisibleIDontKnow();
			refreshAll();
			if (secondDice==0) {
				throwDices();
			}
			refreshAll();
		}
		public void mousePressed(MouseEvent e) {			}
		public void mouseReleased(MouseEvent e) {			}
		public void mouseEntered(MouseEvent e) {			}
		public void mouseExited(MouseEvent e) {				}
	}
	private class changeGoalTreasure extends JButton implements MouseListener {
		{
			this.setText("Change Goal!");
			this.addMouseListener(this);
		}
		
		public void mouseClicked(MouseEvent e) {
			optionsBox.unVisibleChangeGoalTreasure();
			game.treasures.changeGoal();
			refreshAll();
			GUI.this.print("Round number ROUNDNUMBER, this round's goal treasure has changed to GOALTREASURE");
			throwDices();
			refreshAll();
		}
		public void mousePressed(MouseEvent e) {			}
		public void mouseReleased(MouseEvent e) {			}
		public void mouseEntered(MouseEvent e) {			}
		public void mouseExited(MouseEvent e) {				}
	}
	private class OptionsBox extends JPanel {
		
		private IDontKnow dontKnow;
		private changeGoalTreasure goalTreasure;
		public PrivateMessage privateMessage;
		
		{
			this.setSize(134, 210-20);
			this.setLocation(866, 52+338+20);
			this.setBackground(Color.white);
			//this.setLayout(new FlowLayout(FlowLayout.CENTER));
			
			dontKnow = new IDontKnow();
			this.add(dontKnow);
			dontKnow.setVisible(false);
			
			goalTreasure = new changeGoalTreasure();
			this.add(goalTreasure);
			goalTreasure.setVisible(false);
			
			privateMessage = new PrivateMessage();
			this.add(privateMessage);
			privateMessage.setVisible(false);
			
		}
		
		
		public void visibleIDontKnow() {
			dontKnow.setVisible(true);
		}
		
		public void unVisibleIDontKnow() {
			dontKnow.setVisible(false);
		}
		
		public void visibleChangeGoalTreasure() {
			goalTreasure.setVisible(true);
		}
		
		public void unVisibleChangeGoalTreasure() {
			goalTreasure.setVisible(false);
		}
		
	}
	
	private class MenuBox extends JPanel {
		private JLabel[] jlName;
		private JLabel[] jlScore;
		private JLabel jlGoalTreasure;
		private JLabel jlCurrentPlayer;
		private JLabel jlDices;
		private JLabel jlRoundNumber;
		{
			this.setSize(134, 548-210+20);
			this.setLocation(866, 52);
			this.setBackground(Color.white);
			this.setLayout(null);

			
			JLabel score = new JLabel("      Scores:");
			score.setSize(134, 20);
			score.setLocation(0, 38-15);
			this.add(score);
			
			Font boldFont = new Font(score.getFont().getName(), Font.ITALIC+Font.BOLD, score.getFont().getSize()+1);
			score.setFont(boldFont);
			
			JLabel goalTresure = new JLabel("       Goal Treasure:");
			goalTresure.setSize(134, 20);
			goalTresure.setLocation(0, 38 + 100);
			this.add(goalTresure);
			goalTresure.setFont(boldFont);

			jlGoalTreasure = new JLabel();
			jlGoalTreasure.setSize(134, 20);
			jlGoalTreasure.setLocation(0, 38 + 100 + 20);
			this.add(jlGoalTreasure);

			JLabel currentPlayer = new JLabel("       Current Player:");
			currentPlayer.setSize(134, 20);
			currentPlayer.setLocation(0, 38 + 100 + 20 + 20 +15);
			this.add(currentPlayer);
			currentPlayer.setFont(boldFont);

			jlCurrentPlayer = new JLabel();
			jlCurrentPlayer.setSize(134, 20);
			jlCurrentPlayer.setLocation(0, 38 + 100 + 20 + 20 + 20 +15);
			this.add(jlCurrentPlayer);

			
			JLabel roundNumber = new JLabel("       Round Number:");
			roundNumber.setSize(134, 20);
			roundNumber.setLocation(0, 38 + 100 + 20 + 20 + 40 + 15 +20);
			this.add(roundNumber);
			roundNumber.setFont(boldFont);

			jlRoundNumber = new JLabel();
			jlRoundNumber.setSize(134, 20);
			jlRoundNumber.setLocation(0, 38 + 100 + 20 + 20 + 40 + 20 + 30);
			this.add(jlRoundNumber);
			
			
			JLabel Dices = new JLabel("       Dices:");
			Dices.setSize(134, 20);
			Dices.setLocation(0, 38 + 100 + 20 + 20 + 40 +40 + 45);
			this.add(Dices);
			Dices.setFont(boldFont);

			jlDices = new JLabel();
			jlDices.setSize(134, 20);
			jlDices.setLocation(0, 38 + 100 + 20 + 20 + 40 + 20+40 +45);
			this.add(jlDices);
		}

		public MenuBox() {
			jlName = new JLabel[game.player.length];
			jlScore = new JLabel[game.player.length];

			for (int i = 0; i < game.player.length; i++) {
				jlName[i] = new JLabel("     " + (i+1) + ". " + game.player[i].getName() );
				jlName[i].setSize(100, 20);
				jlName[i].setLocation(0, 38 + 20 + i * 20);
				this.add(jlName[i]);
				
				switch (i) {
				case 0:
					jlName[i].setForeground(Color.yellow);
					break;
				case 1:
					jlName[i].setForeground(Color.orange);
					break;
				case 2:
					jlName[i].setForeground(Color.blue);
					break;
				case 3:
					jlName[i].setForeground(Color.green);
					break;
				default:
					jlName[i].setForeground(Color.black);
					break;
				}

				jlScore[i] = new JLabel();
				jlScore[i].setSize(34, 20);
				jlScore[i].setLocation(100, 38 + 20 + i * 20);
				this.add(jlScore[i]);
			}

			refresh();

		}

		public void refresh() {
			for (int i = 0; i < jlScore.length; i++) {
				jlScore[i].setText("" + game.player[i].getScore());
			}

			jlGoalTreasure.setText("     " + game.treasures.getGoal());

			jlCurrentPlayer.setText("     "
					+ game.player[game.getTurn()].getName());

			jlDices.setText("     " + dice[0] + ", " + dice[1]);
			
			jlRoundNumber.setText("     " + game.treasures.getRoundNumber());
		}
	}

	private class InformationBox extends JPanel {
		private JLabel jl;
		private String pm;
		private JPanel ibjp = this;

		private LinkedList<String> pms = new LinkedList<>();
		{
			this.setSize(1000, 52);
			this.setLocation(0, 0);
			this.setBackground(Color.white);
			//this.setLayout();

			
			jl = new JLabel(":)");
			jl.setSize(1000, 52-30);
			//jl.setLocation(0, 30);
			this.add(jl);

			Font boldFont = new Font(jl.getFont().getName(), Font.CENTER_BASELINE, jl.getFont().getSize()+2);
			jl.setFont(boldFont);
		}

		public InformationBox() {
			new Thread() {
				
				@Override
				public void run() {
					
					while (true) {
						if (!pms.isEmpty()) {
							jl.setText(pms.removeFirst());
							try {
								sleep(1500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						try {
							sleep(500);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			}.start();

		}

		public void setPM(String str) {
			pms.addLast(str);
		}

	}

	private class MainPanel extends JPanel {
		private Cell cellButton[];
		private CellPartner orangePartner[];
		private CellPartner violetPartner[];
		private BlueCell blueCellButton[] = new BlueCell[4];
		
		{
			this.setSize(866, 548);
			this.setLocation(0, 52);
			this.setBackground(Color.white);
			this.setLayout(null);
			

			blueCellButton[0] = new BlueCell(0, 58, 332-9);
			blueCellButton[1] = new BlueCell(1, 38, 302-9);
			blueCellButton[2] = new BlueCell(2, 47, 266-9);
			blueCellButton[3] = new BlueCell(3, 81, 251-9);
			
			for (BlueCell bc : blueCellButton) {
				this.add(bc);
			}
			
			int count = 0;
			for (int i = 0; i < game.board.cell.length; i++) {
				if (game.board.cell[i].getColor().equals(
						BoardCell.getOrangeColor()))
					count++;
			}
			orangePartner = new CellPartner[count];
			count = 0;
			for (int i = 0; i < game.board.cell.length; i++) {
				if (game.board.cell[i].getColor().equals(
						BoardCell.getOrangeColor())) {
					orangePartner[count] = new CellPartner(i);
					this.add(orangePartner[count]);
					count++;
				}
			}
			
			count = 0;
			for (int i = 0; i < game.board.cell.length; i++) {
				if (game.board.cell[i].getColor().equals(
						BoardCell.getVioletColor()))
					count++;
			}
			violetPartner = new CellPartner[count];
			count = 0;
			for (int i = 0; i < game.board.cell.length; i++) {
				if (game.board.cell[i].getColor().equals(
						BoardCell.getVioletColor())) {
					violetPartner[count] = new CellPartner(i);
					this.add(violetPartner[count]);
					count++;
				}
			}
			
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			for (int i = 2; i < game.board.cell.length; i++) {
				int startIndex = i;
				int endIndex[] = game.board.cell[i].getBranches();

				int startX = game.board.cell[startIndex].getX();
				int startY = game.board.cell[startIndex].getY();
				int endX[] = new int[endIndex.length];
				int endY[] = new int[endIndex.length];
				for (int j = 0; j < endY.length; j++) {
					endX[j] = game.board.cell[endIndex[j]].getX();
					endY[j] = game.board.cell[endIndex[j]].getY();
				}

				for (int j = 0; j < endY.length; j++) {
					g.drawLine(startX, startY, endX[j], endY[j]);
				}
			}

		}

		public MainPanel() {
			cellButton = new Cell[game.board.cell.length];

			for (int i = 1; i < cellButton.length; i++) {
				cellButton[i] = new Cell(i);
				this.add(cellButton[i]);

			}

		}

		public void refresh() {
			for (int i = 1; i < cellButton.length; i++) {
				cellButton[i].refresh();
			}
			
			for (BlueCell bc : blueCellButton) {
				bc.refresh();
			}
		}

		public void unVisiblePartner() {
			for (CellPartner cp : orangePartner) {
				cp.visibility(false);
			}
			for (CellPartner cp : violetPartner) {
				cp.visibility(false);
			}
		}
		
		public void visibleOrange() {
			for (CellPartner cp : orangePartner) {
				cp.visibility(true);
			}
		}
		
		public void visibleViolet() {
			for (CellPartner cp : violetPartner) {
				cp.visibility(true);
			}
		}

	}

	private class MainFrame extends JFrame {
		{
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setLayout(null);
			this.setResizable(false);
			//this.setVisible(false);
			//this.setOpacity(0.5f);
			//this.setBackground();
			

			jpMB = new MenuBox();
			this.add(jpMB);

			jp = new MainPanel();
			this.add(jp);

			jpIB = new InformationBox();
			this.add(jpIB);
			
			optionsBox = new OptionsBox();
			this.add(optionsBox);
			
			this.setSize(1007, 635);
			
			this.setVisible(true);
			
			this.setLocation(200, 100);
		}

		public MainFrame() {
			super("Raze Jangal");
		}
	}

	private Game game;
	private int numberOfPlayers;
	private int[] dice = { 0, 0 };
	private JFrame jf;
	private InformationBox jpIB;
	private MenuBox jpMB;
	private MainPanel jp;
	private OptionsBox optionsBox;
	
	private boolean sameDice = false;
	private int secondDice = 0;
	private boolean inRed = false;

	public GUI() {
		numberOfPlayers = 0;

		while (!(numberOfPlayers >= 2 && numberOfPlayers <= 4)) {
			try {
			numberOfPlayers = Integer.parseInt(JOptionPane.showInputDialog(
					"How many players are going to play?", 2));
			} catch (Exception e) {
				
			}
		}
		game = new Game(numberOfPlayers);
		for (int i = 0; i < game.player.length; i++) {
			game.player[i].setName(JOptionPane.showInputDialog("Player "
					+ (i + 1) + " Name:"));
			if (game.player[i].getName()==null)
				i--;
		}

		jf = new MainFrame();
		
		
		print("Round number ROUNDNUMBER started, this round's goal treasure is GOALTREASURE");
		
		for (int i = 1; i < game.player.length; i++) {
			game.changeTurn();
		}
		throwDices();
		refreshAll();
		
		new Thread() {
			public void run() {
				boolean flag = true;
				while (flag) {
					try {
						sleep(1500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if (game.treasures.finishedTreasures()) {
						Sound.playClip("finnishedGame.wav");
						flag = false;
						int max = 0;
						int playerNum = 0;
						for (Player pl : game.player)
							if (pl.getScore()>max)
								max = pl.getScore();
						for (int i = 0; i < game.player.length; i++) {
							if (max == game.player[i].getScore())
								playerNum = i;
						}
						
						JOptionPane.showMessageDialog(null, "Game has been finnished.\n" + "Congratulations " + game.player[playerNum].getName() + "!");
						print(game.player[playerNum].getName() + "won the game with the score: " + game.player[playerNum].getScore());
						inRed=true;
					}
					
				}
			};
		}.start();
		
		

	}

	private void takePlaceAction() {
		for (int i = 0; i < game.treasures.treasureNumbers(); i++)
			if (game.getPlaces()[game.getTurn()] == game.treasures
					.getTreasureIndexes()[i]) {
				/*
				JOptionPane.showMessageDialog(null, "Private Message to " + game.player[game.getTurn()].getName() + "!");
				print("Cell PLAYERINDEX's treasure as seen by player PLAYERNUM (" + game.player[game.getTurn()].getName() + ") is "
						+ game.treasures.index2Treasure(game.treasures
								.getTreasureIndexes()[i]));
				print("Cell PLAYERINDEX's treasure as seen by player PLAYERNUM (" + game.player[game.getTurn()].getName() + ") is "
						+ game.treasures.index2Treasure(game.treasures
								.getTreasureIndexes()[i]));
				print(":)");
				*/
				optionsBox.unVisibleChangeGoalTreasure();
				optionsBox.privateMessage.sendText(game.player[game.getTurn()].getName(), "Cell " + game.player[game.getTurn()].getPlace() + "'s treasure as seen by player PLAYERNUM (" + game.player[game.getTurn()].getName() + ") is "
						+ game.treasures.index2Treasure(game.player[game.getTurn()].getPlace()));
			}
		
		
		if (game.getPlaces()[game.getTurn()] == game.getRedCellIndex()
				&& game.treasures.finishedTreasures() == false) {
			
			inRed = true;
			print("Player PLAYERNUM (" + game.player[game.getTurn()].getName() + ") is in Red, and can attempt to guess goal treasure's place!");
			jp.visibleOrange();
			optionsBox.visibleIDontKnow();
			
		}
		
		refreshAll();
	}

	private void refreshAll() {
		if (secondDice != 0)
			whereCanGo = game.wherePlayerCanGo(game.getTurn(), secondDice);
		else
			whereCanGo = IntArrayMethods.combine(
					game.wherePlayerCanGo(game.getTurn(), dice[0]),
					game.wherePlayerCanGo(game.getTurn(), dice[1]));
		jp.refresh();
		jpMB.refresh();
	}

	private void clickedOn(int index) {
		//optionsBox.unVisibleChangeGoalTreasure();
		if (inRed==true)
			return;
		if (secondDice==0) {
			int dice0Moves[] = game.wherePlayerCanGo(game.getTurn(), dice[0]);
			int dice1Moves[] = game.wherePlayerCanGo(game.getTurn(), dice[1]);
			for (int i : dice0Moves)
				if (i==index) {
					moveCurrentPlayer(index);
					jp.unVisiblePartner();
					secondDice = dice[1];
					refreshAll();
					optionsBox.unVisibleChangeGoalTreasure();
					takePlaceAction();
					return;
				}
			for (int i : dice1Moves)
				if (i==index) {
					moveCurrentPlayer(index);
					jp.unVisiblePartner();
					secondDice = dice[0];
					refreshAll();
					optionsBox.unVisibleChangeGoalTreasure();
					takePlaceAction();
					return;
				}
		} else {
			int diceMoves[] = game.wherePlayerCanGo(game.getTurn(), secondDice);
			for (int i : diceMoves)
				if (i==index) {
					moveCurrentPlayer(index);
					refreshAll();
					secondDice = 0;
					takePlaceAction();
					if (inRed==false)
						throwDices();
					refreshAll();
				}
		}
	}
	
	private void clickedOnPartner(int index) {
		optionsBox.unVisibleChangeGoalTreasure();
		if (sameDice==true) {
			moveCurrentPlayer(index);
			refreshAll();
			takePlaceAction();
			sameDice=false;
			throwDices();
		} else {
			moveCurrentPlayer(index);
			takePlaceAction();
			optionsBox.unVisibleIDontKnow();
			if (game.treasures.getGoal().equals(game.treasures.index2Treasure(index))) {
				game.wonTreasure(game.getTurn(), game.treasures.getGoal());
				print("Player PLAYERNUM (" + game.player[game.getTurn()].getName() + ") has won this round's goal treasure, GOALTREASURE");
				game.treasures.changeGoal();
				print("Round number ROUNDNUMBER started, this round's goal treasure is GOALTREASURE");
				moveCurrentPlayer(game.getRedCellIndex());
				takePlaceAction();
			} else {
				moveCurrentPlayer(0);
				throwDices();
			}
			refreshAll();
		}
	}
	
	private void throwDices() {
		optionsBox.unVisibleChangeGoalTreasure();
		jp.unVisiblePartner();
		optionsBox.unVisibleIDontKnow();
		sameDice = false;
		inRed = false;
		secondDice = 0;
		dice = Game.diceThrow();

		if (true) { // true: player can change dices
			dice[0] = Integer.parseInt(JOptionPane.showInputDialog("Dice 1:", ""
					+ dice[0]));
			while (!(dice[0] >= 1 && dice[0] <= 6)) {
				dice[0] = Integer.parseInt(JOptionPane.showInputDialog(
						"Dice 1:", "" + dice[0]));
			}
			dice[1] = Integer.parseInt(JOptionPane.showInputDialog("Dice 2:",
					"" + dice[1]));
			while (!(dice[1] >= 1 && dice[1] <= 6)) {
				dice[1] = Integer.parseInt(JOptionPane.showInputDialog(
						"Dice 2:", "" + dice[1]));
			}
		}
		
		
		if (dice[0]==dice[1]) {
			jp.visibleOrange();
			jp.visibleViolet();
			optionsBox.visibleChangeGoalTreasure();
			sameDice = true;
			Sound.playClip("sameDice.wav");
		}
		
		game.changeTurn();
		refreshAll();
		
	}
	
	private boolean isCellSelectable(int index) {
		if (inRed == true)
			return false;
		try {
			for (int i : whereCanGo)
				if (i == index)
					return true;
		} catch (NullPointerException e) {

		}
		return false;
	}

	private void moveCurrentPlayer(int index) {
		int deadPlayer = game.movePlayer(game.getTurn(), index);
		refreshAll();
		if (deadPlayer != -1) {
			print("Player PLAYERNUM (" + game.player[game.getTurn()].getName() + ") is moved to Blue cells.", deadPlayer);
			Sound.playClip("Back2Blue.wav");
		} else {			
			Sound.playClip("Move.wav");
		}
	}

	public void printCurentPositions() {
		String pm;
		pm = "Current Positions:";
		int[] positions = game.getPlaces();
		for (int i = 0; i < positions.length; i++)
			pm = pm + (" " + (i + 1) + "->" + positions[i]);
		jpIB.setPM(pm);
	}

	public void print(String sentence) {
		String fromWord[] = { "PLAYERNUM", "ROUNDNUMBER", "GOALTREASURE",
				"PLAYERINDEX" };
		String toWord[] = { "" + (game.getTurn() + 1),
				"" + game.treasures.getRoundNumber(), game.treasures.getGoal(),
				"" + game.getPlaces()[game.getTurn()] };
		for (int i = 0; i < toWord.length; i++)
			sentence = StringMethods.replaceWord(sentence, fromWord[i],
					toWord[i]);
		jpIB.setPM(sentence);
	}

	public void print(int index, String sentence) {
		String fromWord = "INDEX";
		if (index == game.getRedCellIndex())
			sentence = StringMethods.replaceWord(sentence, fromWord, index
					+ "(Red)");
		else if (index == game.getVioletCellIndex())
			sentence = StringMethods.replaceWord(sentence, fromWord, index
					+ "(Violet)");
		for (int i = 0; i < game.treasures.getTreasureIndexes().length; i++)
			if (index == game.treasures.getTreasureIndexes()[i])
				sentence = StringMethods.replaceWord(sentence, fromWord, index
						+ "(Orange)");
		sentence = StringMethods.replaceWord(sentence, fromWord, index + "");
		jpIB.setPM(sentence);
	}

	public void print(String sentence, int playerNum) {
		String fromWord = "PLAYERNUM";
		String toWord = "" + (playerNum + 1);
		sentence = StringMethods.replaceWord(sentence, fromWord, toWord);
		jpIB.setPM(sentence);
	}

	public void print(String sentence, int dice1, int dice2) {
		String fromWord[] = { "PLAYERNUM", "DICE1", "DICE2" };
		String toWord[] = { "" + (game.getTurn() + 1), "" + dice1, "" + dice2 };
		for (int i = 0; i < toWord.length; i++)
			sentence = StringMethods.replaceWord(sentence, fromWord[i],
					toWord[i]);
		jpIB.setPM(sentence);
	}

}
