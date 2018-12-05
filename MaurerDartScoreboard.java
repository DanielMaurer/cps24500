import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* Dart ScoreBoard
 * 
 * 
 * Written by: Danny Maurer
 * 
 * The purpose of this project is to create an application that keeps track of the point totals for three dart game variants.
 * If the user selects the game 301 or 501, they will input the target that they hit, along with any multiplier, and the app
 * will keep track of the total. The winner will receive a notification when they reach 0 and win.
 * 
 * If the user selects the game cricket, they will input values in a similar fashion and the view will display which targets are 
 * hit.
 * 
 * The model classes are the User, point and game classes. 
 * 
 * The view classes are ScorePanel, 
 * 
 * The controller class is
 * 
 * Serialization:
 * 
 * Future Improvements: 
 * Add more game variants
 * 
 * 	Allow the user to click on a picture of a dart board and react to the location at which they pressed, creating a more user 
 * friendly GUI. 
 *
 *
 *File is located in dannymaurer folder
 *to push file back have to be in the same folder
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
	
	// constructor
	public Attempt(int number, int multiplier) {
		setNumber(number);
		setMultiplier(multiplier);
	}
}

class Player{
	// declare variables
	private ArrayList<Attempt> attempts;
	private int currentScore;
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
		currentScore = 0;// SUM OF THE ATTEMPTS
	}	
}

class Match{
	private Player player1;
	private Player player2;
	private int game;
	
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
	public int getGame() {
		return game;
	}
	public void setGame(int game) {
		this.game = game;
	}

	public Match(Player player1, Player player2, int game) {
		setPlayer1(player1);
		setPlayer2(player2);
		setGame(game);
	}
}

//========================================== The View ============================================

class MessagePanel extends JPanel{
	private String message;

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public MessagePanel(String message) {
		setMessage(message);
		setPreferredSize(new Dimension(100, 20));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString("Welcome to the Dart Scoreboard!", 15, 15); // ADJUST THIS TO FIT IN THE CENTER OF THE SCREEN
		g.drawString(message, 15, 30);
	}
	
}

class ScorePanel extends JPanel{
	private Player player1;
	private Player player2;
	private int yLoc;
	private String update;
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
	
	public ScorePanel(Player player1, Player player2) {
		setPlayer1(player1);
		setPlayer2(player2);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// g.setFont
		yLoc = 25;
		update = String.format("%s has %d! Only %d more to win!", player1.getName(), player1.getCurrentScore(), player1.getRemainder());
		g.drawString(update, 100, yLoc);
		yLoc = yLoc + 50;
		update = String.format("%s has %d! Only %d more to win!", player2.getName(), player2.getCurrentScore(), player2.getRemainder());
		
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
		setBounds(100,100,300,300);
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
		btn501.addActionListener(new ActionListener() {
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
	
	private Player player1;
	private Player player2;
	
	private String message;
	private String name1;
	private String name2;
	private String cName;
	
	private int game;
	private int count;
	private int number;
	private int multiplier;
	
	public void configureUI(JButton[] pointButtons, JButton[] enterButtons) {
		FindInfo fi = new FindInfo(this, true);
		fi.setVisible(true);
		fi.dispose();
		game = fi.getGame();
		name1 = fi.getName1();
		name2 = fi.getName2();
		
		if(count % 2 == 0) {
			cName = name1;
		} else {
			cName = name2;
		}
		
		if(!name1.equals(null) && !name1.equals("")) {
			player1 = new Player(attempts, 0, name1, game);
		} 
		if(!name2.equals(null) && !name2.equals("")) {
			player2 = new Player(attempts, 0, name2, game);
		} 
		
		// CREATE A NEW MATCH
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100,100,500,500);
		setTitle("Dart Scoreboard v 0.1");
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		span = new ScorePanel(player1, player2);
		c.add(span, BorderLayout.CENTER);
		message = String.format("%s, enter your points for this turn:", cName);
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
					number = number + Integer.parseInt(btn.getText()); // add the value of the button to the potential points
				}
			});
		}
		for(int i = 0; i < 20; i++) {
			panCenter.add(pointButtons[i]);
		}
		panSouth.add(panCenter, BorderLayout.CENTER);
		
		JPanel panEast = new JPanel();
		panEast.setLayout(new GridLayout(4,1));
		enterButtons[0] = new JButton("Miss");
		enterButtons[1] = new JButton("Double");
		enterButtons[2] = new JButton("Triple");
		enterButtons[3] = new JButton("Enter"); // PICK UP AT THIS POINT, CREATE ACTION LISTENERS FOR EACH OF THE FOUR BUTTONS
		
		enterButtons[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				number = 0;
			}
		});
		enterButtons[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				multiplier = 2;
			}
		});
		enterButtons[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				multiplier = 3;
			}
		});
		enterButtons[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				attempts.add(new Attempt(number, multiplier));	
				// adjust the current value of the point for the specified user
				span.repaint();
				}
		});
		
		// clear the attempts array so that the next series of attempts can be added
		if(attempts.size() > 3) {
			attempts.clear();
		}
		
		for(int i = 0; i < 4; i++) {
			panEast.add(enterButtons[i]);
		}
		panSouth.add(panEast, BorderLayout.EAST);
		c.add(panSouth, BorderLayout.SOUTH);
	}
	
	
	public DartFrame (JButton[] pointButtons, JButton[] enterButtons, ArrayList<Attempt> attempts) {
		this.attempts = attempts; // ALWAYS DO THIS FIRST!!!
		configureUI(pointButtons, enterButtons);
	}
}

public class MaurerDartScoreboard {

	public static void main(String[] args) {
		JButton[] pointButtons = new JButton[20];
		JButton[] enterButtons = new JButton[4];
		ArrayList<Attempt> attempts = new ArrayList<Attempt>();
		
		DartFrame dfrm = new DartFrame(pointButtons, enterButtons, attempts);
		dfrm.setVisible(true);

	}

}
