import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/* Dart ScoreBoard
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
	public Point(String name, int total, int start) {
		super(name);
		setCurrent(total);
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
			update = String.format("%d has %d!\n\n Only %d more to win!", p.getName(), p.getCurrent(), p.getRemainder());
			g.drawString(update, xLoc, 25);
			xLoc = xLoc + 100;
		}
	}	
}


	

class DartFrame extends JFrame{
	JButton[] pointButtons = new JButton[20];
	JButton[] enterButtons = new JButton[4];
	
	private ArrayList<Point> points;
	private ScorePanel span;
	
	private String message;
	private int total = 0;
	
	public void ConfigureUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100,100,500,500);
		setTitle("Dart Scoreboard v 0.1");
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		span = new ScorePanel(points);
		c.add(span, BorderLayout.CENTER);
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
					total = total + (int)(btn.getText());
				}
			});
		}
		for(int i = 0; i < 20; i++) {
			panCenter.add(pointButtons[i]);
		}
		panSouth.add(panCenter, BorderLayout.CENTER);
		
		JPanel panRight = new JPanel();
		panRight.setLayout(new GridLayout(1,4));
		enterButtons[0] = new JButton("Miss");
		enterButtons[0] = new JButton("Double");
		enterButtons[0] = new JButton("Triple");
		enterButtons[0] = new JButton("Enter"); // PICK UP AT THIS POINT, CREATE ACTION LISTENERS FOR EACH OF THE FOUR BUTTONS
	}
}

public class MaurerDartScoreboard {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
