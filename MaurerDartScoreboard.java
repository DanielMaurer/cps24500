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
class User{
	// declare variables
	private String name;

	// get and set functions
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	//constructor
	public User(String name){
		setName(name);
	}
	
	//toString function
	public String toString() {
		return String.format("%d", name);
	}
}

class Point extends User{
	//declare variables
	private int current; // current point value for each user
	private int start; // starting value for each user, dependent on the game chosen
	private int remainder;

	// get and set functions
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getRemainder() {
		return remainder;
	}
	public void setRemainder(int remainder) {
		this.remainder = remainder;
	}

	//remaining value function 
	public int remainingValue(int current, int start) {
		remainder = start - current;
		return remainder;
	}

	// constructor
	public Point(String name, int start) {
		super(name);
		setStart(start);
	}
	
	// toString function
	public String toString() {
		return String.format("%d has %d", super.toString(), current); 
	}
	
	public boolean isWinner(int current) {
		if(current == 0) {
			return true;
		} else {
			return false;
		}
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
		g.drawString(message, 15, 15); // ADJUST THIS TO FIT IN THE CENTER OF THE SCREEN
	}
	
}

class ScorePanel extends JPanel{
	private ArrayList<Point> points;
	private int xLoc;
	private String update;
	//add a game variable if you want to include multiple game variants
	
	public ArrayList<Point> getPoints() {
		return points;
	}
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}
	
	public ScorePanel(ArrayList<Point> points) {
		this.points = points;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// g.setFont
		for(Point p : points) {
			xLoc = 100;
			update = String.format("%s has %d!\n\n Only %d more to win!", p.getName(), p.getCurrent(), p.getRemainder());
			g.drawString(update, xLoc, 25);
			xLoc = xLoc + 100;
		}
	}	
}

class FindInfo extends JDialog{
	private String user;
	private int game;

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
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
		JLabel lblUser = new JLabel("Enter the name of user 1: ");
		JTextField txtUser = new JTextField(20);
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setUser(txtUser.getText());
				setVisible(false);
			}
		});
		c.add(lblUser);
		c.add(txtUser);
		c.add(btnEnter);
	}
	public FindInfo(JFrame owner, boolean modal) {
		super(owner, modal);
		configureUI();
	}
}	

class DartFrame extends JFrame{
	JButton[] pointButtons = new JButton[20];
	JButton[] enterButtons = new JButton[4];
	private ArrayList<Point> points;
	private ScorePanel span;
	
	private String message;
	private String user1;
	private String user2;
	private int game;
	private int total = 0;
	private int potential = 0;
	
	public void configureUI(JButton[] pointButtons, JButton[] enterButtons) {
		FindInfo fi = new FindInfo(this, true);
		fi.setVisible(true);
		fi.dispose();
		game = fi.getGame();
		user1 = fi.getUser();
		points.add(new Point(user1, game));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100,100,500,500);
		setTitle("Dart Scoreboard v 0.1");
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		span = new ScorePanel(points);
		c.add(span, BorderLayout.CENTER);
		message = "Welcome to the Dart Scoreboard";
		MessagePanel mpan = new MessagePanel(message);
		c.add(mpan, BorderLayout.NORTH);
		JPanel panSouth = new JPanel();
		panSouth.setLayout(new BorderLayout());
		
	// Formatting South Panel of the container
		JPanel panCenter = new JPanel();
		panCenter.setLayout(new GridLayout(5,4));
		// naming the point buttons to their respective point values
		for(int i = 0; i < 20; i++) {
			pointButtons[i] = new JButton("" + (char)(i + 1));
		}
		// adding the value of the button to the total
		for(int i = 0; i < 20; i++) {
			pointButtons[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JButton btn = (JButton)(e.getSource()); // typecasts the source of each button to a variable
					potential = potential + Integer.parseInt(btn.getText()); // add the value of the button to the potential points
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
				potential = 0;
			}
		});
		enterButtons[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				potential = potential * 2;
			}
		});
		enterButtons[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				potential = potential * 3;
			}
		});
		enterButtons[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				total = potential;
			}
		});
		
		for(int i = 0; i < 4; i++) {
			panEast.add(enterButtons[i]);
		}
		panSouth.add(panEast, BorderLayout.EAST);
		c.add(panSouth, BorderLayout.SOUTH);
	}
	
	
	public DartFrame (JButton[] pointButtons, JButton[] enterButtons, ArrayList<Point> points) {
		this.points = points;
		configureUI(pointButtons, enterButtons);
	}
}

public class MaurerDartScoreboard {

	public static void main(String[] args) {
		JButton[] pointButtons = new JButton[20];
		JButton[] enterButtons = new JButton[4];
		ArrayList<Point> points = new ArrayList<Point>();
		
		DartFrame dfrm = new DartFrame(pointButtons, enterButtons, points);
		dfrm.setVisible(true);

	}

}
