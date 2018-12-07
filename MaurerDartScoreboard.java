import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* Dart ScoreBoard
 * 
 * 
 * Written by: Danny Maurer
 * 
 * The purpose of this project is to create an application that keeps track of the point totals for two dart game variants.
 * If the user selects the game 301 or 501, they will input the target that they hit, along with any multiplier, and the app
 * will keep track of the total. The winner will receive a notification when they reach 0 and win.
 * 
 * To use this app, the player or players will select the game that they want to play as well as their names followed by pressing 
 * enter. To input your attempts, you either press the number that you hit or miss, followed by the potential multiplier. If you 
 * didn't hit a multiplier, then you just select the number. Once you have your potential number and multiplier, you press enter. 
 * THE SCORE PANEL WILL NOT UPDATE UNTIL YOU INSERT ALL 3 OF YOUR ATTEMPTS!!! If there are two players, then after there are three
 * attempts the score panel will update showing whose turn it is. 
 * 
 * 
 * The model classes are Attempt, Player, Match, and ScoreChecker. 
 * 
 * The view classes are MessagePanel, ScorePanel, and FindInfo 
 * 
 * The controller class is DartFrame.
 * 
 * Serialization:
 * 
 * Future Improvements: 
 * 	-Add more game variants
 * 	-Allow the user to click on a picture of a dart board and react to the location at which they pressed, creating a more user 
 * friendly GUI. 
 *
 *
 *git status
 *git add .
 *git commit -m "description"
 *git push
 */

//========================================== The Model ============================================
class Attempt{
	// variables
	private int number;
	private int multiplier;
	private int score = 0;
	
	// getters and setters
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getMultiplier() {
		return multiplier;
	}
	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	// constructor
	public Attempt(int number, int multiplier) {
		setNumber(number);
		setMultiplier(multiplier);
	}
	
	public int calculateScore() {
		return score = number * multiplier;
	}
}

class Player{
	// declare variables
	private ArrayList<Attempt> attempts;
	private int currentScore;
	private int prevScore;
	private int game;
	private int remainder;
	private String name;
	
	
	// getters and setters
	public ArrayList<Attempt> getAttempts() {
		return attempts;
	}
	public void setAttempts(ArrayList<Attempt> attempts) {
		this.attempts = attempts;
	}
	public int getCurrentScore() {
		return currentScore;
	}
	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}
	public int getPrevScore() {
		return prevScore;
	}
	public void setPrevScore(int prevScore) {
		this.prevScore = prevScore;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGame() {
		return game;
	}
	public void setGame(int game) {
		this.game = game;
	}
	
	// constructor
	public Player(ArrayList<Attempt> attempts, int currentScore, String name, int game) {
		setAttempts(attempts);
		setCurrentScore(currentScore);
		setName(name);
		setGame(game);
	}
	
	// various functions
	public int getRemainder() {
		return remainder = game - currentScore;
	}
	public void throwDart(int number, int multiplier) {
		attempts.add(new Attempt(number, multiplier));
		if(attempts.size() > 2) {
			for(Attempt a : attempts) {
				currentScore = currentScore + a.calculateScore();
			}
			attempts.clear();
		}
	}	
	
}

class Match{
	private String name1;
	private String name2;
	private int game;
	ArrayList<Attempt> attempts;
	private Player player1;
	private Player player2;
	
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public int getGame() {
		return game;
	}
	public void setGame(int game) {
		this.game = game;
	}
	public ArrayList<Attempt> getAttempts() {
		return attempts;
	}
	public void setAttempts(ArrayList<Attempt> attempts) {
		this.attempts = attempts;
	}
	public Player getPlayer1() {
		return player1;
	}
	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}
	public Player getPlayer2() {
		return player2;
	}
	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}
	public Match(String name1, String name2, int game, ArrayList<Attempt> attempts) {
		setName1(name1);
		setName2(name2);
		setGame(game);
		this.attempts = attempts;
	}
	
	public void startMatch() {
		// creates the players
		player1 = new Player(attempts, 0, name1, game);
		player2 = new Player(attempts, 0, name2, game);
	}
	public Player changePlayer(Player cPlayer) {
		if(cPlayer.equals(player1)) {
			return cPlayer = player2;
		} else {
			return cPlayer = player1;
		}
	}
}

class ScoreChecker{
	private Player cPlayer;
	private int game;
	
	public Player getcPlayer() {
		return cPlayer;
	}
	public void setcPlayer(Player cPlayer) {
		this.cPlayer = cPlayer;
	}
	public int getGame() {
		return game;
	}
	public void setGame(int game) {
		this.game = game;
	}
	
	public ScoreChecker(Player cPlayer, int game) {
		this.cPlayer = cPlayer;
		setGame(game);
	}
	
	public boolean isWinner() {
		if(cPlayer.getCurrentScore() == game) {
			return true;
		} else {
			return false;
		}
	}
}

//========================================== The View ============================================

class MessagePanel extends JPanel{
	private String message;
	private String welcome = "Welcome to the Dart Scoreboard!";

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public MessagePanel(String message) {
		setMessage(message);
		setPreferredSize(new Dimension(100, 50));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//FontMetrics fm = g.getFontMetrics();
		Font title = new Font("Times New Roman", Font.BOLD, 24);
		g.setFont(title);
		g.drawString(welcome, 65, 20); // ADJUST THIS TO FIT IN THE CENTER OF THE SCREEN
		Font sub = new Font("Times New Roman",Font.PLAIN, 14);
		g.setFont(sub);
		g.drawString(message, 15, 40);
	}
}

class ScorePanel extends JPanel{
	private Player player1;
	private Player player2;
	private Player cPlayer;
	private int xLoc;
	private int yLoc;
	private String update;
	private boolean twoPlayer;
	//add a game variable if you want to include multiple game variants
	
	public Player getPlayer1() {
		return player1;
	}
	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}
	public Player getPlayer2() {
		return player2;
	}
	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}
	public Player getcPlayer() {
		return cPlayer;
	}
	public void setcPlayer(Player cPlayer) {
		this.cPlayer = cPlayer;
	}
	
	public ScorePanel(Player player1, Player player2, Player cPlayer, boolean twoPlayer) {
		setPlayer1(player1);
		setPlayer2(player2);
		setcPlayer(cPlayer);
		this.twoPlayer = twoPlayer;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Font name = new Font("Times New Roman", Font.BOLD, 26);
		Font score = new Font("Times New Roman", Font.PLAIN, 16);
		
		// Adjusting for Player 1
		if(player1.getName() != "") {
			xLoc = 100;
			yLoc = 50;
			update = String.format("%s", player1.getName());
		
			g.setFont(name);
			g.drawString(update, xLoc, yLoc);
			FontMetrics fm = g.getFontMetrics();
			int xrect = xLoc-10;
			int yrect = yLoc - fm.getAscent();
			int width = fm.stringWidth(update)+20;
			int height = fm.getAscent() + fm.getDescent();
			if(cPlayer.getName().equals(player1.getName())){
				g.setColor(Color.RED);
				g.drawRect(xrect, yrect, width, height);
				g.setColor(Color.BLACK);
			}	
			xLoc = xLoc - 20;
			yLoc = yLoc + 30;
			update = String.format("Current: %d", player1.getCurrentScore());
			g.setFont(score);
			g.drawString(update, xLoc, yLoc);
			
			yLoc = yLoc + 30;
			update = String.format("Remainder: %d", player1.getRemainder());
			g.drawString(update, xLoc, yLoc);
		}
		
		// for Player 2
		if(twoPlayer == true) {
			xLoc = 300;
			yLoc = 50;
			update = String.format("%s", player2.getName());
			
			g.setFont(name);
			g.drawString(update, xLoc, yLoc);
			FontMetrics fm = g.getFontMetrics();
			int xrect = xLoc-10;
			int yrect = yLoc - fm.getAscent();
			int width = fm.stringWidth(update)+20;
			int height = fm.getAscent() + fm.getDescent();
			if(cPlayer.getName().equals(player2.getName())){
				g.setColor(Color.RED);
				g.drawRect(xrect, yrect, width, height);
				g.setColor(Color.BLACK);
			}	
			xLoc = xLoc - 20;
			yLoc = yLoc + 30;
			update = String.format("Current: %d", player2.getCurrentScore());
			g.setFont(score);
			g.drawString(update, xLoc, yLoc);
			
			yLoc = yLoc + 30;
			update = String.format("Remainder: %d", player2.getRemainder());
			g.drawString(update, xLoc, yLoc);
			
		}
	}	
}

class FindInfo extends JDialog{
	private String name1;
	private String name2;
	private int game;

	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public int getGame() {
		return game;
	}
	public void setGame(int game) {
		this.game = game;
	}
	
	public void configureUI() {
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setBounds(100,100,300,220);
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		JLabel lblGame = new JLabel("What game would you like to play?");
		JButton btn501 = new JButton("501");
		btn501.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game = 501;
			}
		});
		JButton btn301 = new JButton("301");
		btn301.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game = 301; 
			}
		});
		c.add(lblGame);
		c.add(btn501);
		c.add(btn301);
		JLabel lblPlayer1 = new JLabel("Enter the name of Player 1: ");
		JTextField txtPlayer1 = new JTextField(20);
		JLabel lblPlayer2 = new JLabel("Enter the name of Player 2: ");
		JTextField txtPlayer2 = new JTextField(20);
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setName1(txtPlayer1.getText());
				setName2(txtPlayer2.getText());
				setVisible(false);
			}
		});
		c.add(lblPlayer1);
		c.add(txtPlayer1);
		c.add(lblPlayer2);
		c.add(txtPlayer2);
		c.add(btnEnter);
	}
	public FindInfo(JFrame owner, boolean modal) {
		super(owner, modal);
		configureUI();
	}
}	

//========================================== The Controller ============================================

class DartFrame extends JFrame{
	JButton[] pointButtons = new JButton[20];
	JButton[] enterButtons = new JButton[4];
	private ArrayList<Attempt> attempts;
	private ScorePanel span;
	private MessagePanel mpan;
	private ScoreChecker sc;
	private Attempt a = new Attempt(0,0);
	
	private Player player1;
	private Player player2;
	private Player cPlayer;
	
	private String message;
	private String name1;
	private String name2;
	private String sAgain;
	
	private int game;
	private int count;
	private int number;
	private int multiplier = 1;
	private int again;
	
	private boolean twoPlayer = true;
	private boolean isWinner = false;
	private boolean playAgain = true;
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public void configureUI(JButton[] pointButtons, JButton[] enterButtons) {
		if(playAgain = true){ // for multiple games
			FindInfo fi = new FindInfo(this, true);
			fi.setVisible(true);
			fi.dispose();
			game = fi.getGame(); // get the value of the game selected
			name1 = fi.getName1(); // as well as the players name
			name2 = fi.getName2(); // and potential second players name
			
			if(name1.isEmpty() && !name2.isEmpty()) {
				name1 = name2;
				twoPlayer = false;
			}
			
			// Create a new match
			Match match = new Match(name1, name2, game, attempts); // COMMENT FROM HERE
			match.startMatch();
			
			//Get the values of the players
			player1 = match.getPlayer1();
			player2 = match.getPlayer2();
			
			// checks to see if there is only one player
			if(name1.equals(name2)) {
				cPlayer = player1;
			} else if(player2.getName().equals("")){
				twoPlayer = false;
				cPlayer = player1;
			}
					
			// sets the player whose turn it is
			if(count % 2 == 0 && twoPlayer == true) {
				cPlayer = player1;
			} else if(count % 2 == 1 && twoPlayer == true) {
				cPlayer = player2;
			}
			
			sc = new ScoreChecker(cPlayer, game);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setBounds(100,100,500,500);
			setTitle("Dart Scoreboard v 0.1");
			Container c = getContentPane();
			c.setLayout(new BorderLayout());
			span = new ScorePanel(player1, player2, cPlayer, twoPlayer);
			c.add(span, BorderLayout.CENTER);
			message = String.format("%s, enter your points for this turn:", cPlayer.getName());
			mpan = new MessagePanel(message);
			c.add(mpan, BorderLayout.NORTH);
			JPanel panSouth = new JPanel();
			panSouth.setLayout(new BorderLayout());
			
		// Formatting South Panel of the container
			JPanel panCenter = new JPanel();
			panCenter.setLayout(new GridLayout(5,4));
			// naming the point buttons to their respective point values
			for(int i = 0; i < 20; i++) {
				pointButtons[i] = new JButton("" + (i + 1));
			}
			// adding the value of the button to the total
			for(int i = 0; i < 20; i++) {
				pointButtons[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JButton btn = (JButton)(e.getSource()); // typecasts the source of each button to a variable
						setNumber(Integer.parseInt(btn.getText())); // add the value of the button to the potential points
					}
				});
			}
			for(int i = 0; i < 20; i++) {
				panCenter.add(pointButtons[i]);
			}
			panSouth.add(panCenter, BorderLayout.CENTER);
			
			JPanel panEast = new JPanel();
			panEast.setLayout(new BorderLayout());
			enterButtons[0] = new JButton("Bullseye");
			enterButtons[1] = new JButton("Miss");
			enterButtons[2] = new JButton("Double");
			enterButtons[3] = new JButton("Triple");
			enterButtons[4] = new JButton("Enter"); 
			
			enterButtons[0].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setNumber(25);
				}
			});
			enterButtons[1].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setNumber(0);
					multiplier = 0;
				}
			});
			enterButtons[2].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					multiplier = 2;
				}
			});
			enterButtons[3].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					multiplier = 3;
				}
			});
			enterButtons[4].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cPlayer.setPrevScore(cPlayer.getCurrentScore());
					cPlayer.throwDart(number, multiplier);
					setNumber(0);
					multiplier = 1;
					span.repaint();
					isWinner = sc.isWinner();
					count++; // see if this count works
					if(isWinner == true) {
						message = String.format("Hold the phone, we have a winner!!! Good job %s", cPlayer.getName());
						mpan.setMessage(message);
						mpan.repaint();
						for(int i = 0; i < 20; i++) {
							pointButtons[i].setEnabled(false);
						}
						for(int i = 0; i < 4; i++) {
							enterButtons[i].setEnabled(false);
						}
						sAgain = String.format("%s won that one... Rematch?", cPlayer.getName());
						again = JOptionPane.showConfirmDialog(null, sAgain, null, 1);
						
						if(again == 0) { // CHANGE THIS FOR THE FINAL GAME
							playAgain = true;
							DartFrame dfrm = new DartFrame(pointButtons, enterButtons, attempts);
							dfrm.setVisible(true);
						} else {
							playAgain = false;
						}
						
					}
					if(cPlayer.getCurrentScore() > game) {
						message = String.format("You thought you were good %s, but you went over", cPlayer.getName());
						mpan.setMessage(message);
						mpan.repaint();
						cPlayer.setCurrentScore(cPlayer.getPrevScore());
					}
					if(count > 2) {
						count = 0;
						if(twoPlayer == true) {
							cPlayer = match.changePlayer(cPlayer);
							message = String.format("%s, enter your points for this turn:", cPlayer.getName());
							mpan.setMessage(message);
							mpan.repaint();
							sc.setcPlayer(cPlayer);
							span.setcPlayer(cPlayer);
						}
					}
				}
			});
			
			JPanel panEastCenter = new JPanel();
			panEastCenter.setLayout(new GridLayout(2,2));
			
			for(int i = 0; i < 4; i++) {
				panEastCenter.add(enterButtons[i], BorderLayout.CENTER);
			}
			panEast.add(panEastCenter, BorderLayout.CENTER);
			panEast.add(enterButtons[4], BorderLayout.SOUTH);
			panSouth.add(panEast, BorderLayout.EAST);
			c.add(panSouth, BorderLayout.SOUTH);
		}
	} 
	
	
	public DartFrame (JButton[] pointButtons, JButton[] enterButtons, ArrayList<Attempt> attempts) { // dartframe constructor 
		this.attempts = attempts; // ALWAYS DO THIS FIRST!!!
		configureUI(pointButtons, enterButtons); // run this function 
	}
}

public class MaurerDartScoreboard {

	public static void main(String[] args) {
		JButton[] pointButtons = new JButton[20]; // Create an array of buttons for the points
		JButton[] enterButtons = new JButton[5]; // create an array of buttons for bullseye, multipliers and enter
		ArrayList<Attempt> attempts = new ArrayList<Attempt>(); // create an arraylist of attempts for each player
		
		DartFrame dfrm = new DartFrame(pointButtons, enterButtons, attempts); // create a dartframe passing in the buttons and attempts
		dfrm.setVisible(true); // show the frame on the screen

	}

}
