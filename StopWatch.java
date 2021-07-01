import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.Timer;

public class StopWatch implements ActionListener {
	JFrame frame        = new JFrame();
	JButton startButton = new JButton("Start");                              // Button to start the time
	JButton resetButton = new JButton("Reset");                              // Button to reset the whole Stop Watch time
	JButton lapButton   = new JButton("Lap");                                // Button to add a lap and record its time
	JLabel timeLabel    = new JLabel();                                      // Label is used to display the time in text in time format: Hrs:Mins:Secs
	DefaultListModel<String>listData = new DefaultListModel<String>();       // Hold the time Strings in a list
	JList  listHolder   = new JList();                                       // Keeps listData to display

	int elapsedTime = 0; 
	int seconds = 0;                                                        // seconds, minutes, hours keep track of int data
	int minutes = 0;
	int hours   = 0;
	int lapCount = 1;                                                        // To keep track of laps
	boolean started = false;                                                 // Use to switch between the Start and Stop button
	String seconds_String_Holder = String.format("%02d", seconds);           // Formats string (%:format) to 2(second) decimal Ex:(.00)
	String minutes_String_Holder = String.format("%02d", minutes);
	String hours_String_Holder   = String.format("%02d", hours);
	
	/*
	 *                         Timer Class explained and actions
	 * Calls the Timer Class(named timer) and its constructor(Time, action)
	 * 1000 Milliseconds is used for 1 second because 1000miliseconds == 1 second
	 * adding 1000 milliseconds to elapsedTime: Is just adding a second to the clock
	 * 3600000 Milliseconds = 1 hour(60 minutes), 60000 milliseconds = 1 minute (60 seconds), 1000 millisecond = 1 second
	 * Used the %module to make the number reset to 0 after it goes beyond 59, kinda like the time
	 * Formats string (%:format) to 2(second) decimal Ex:(.00)
	 * The timeLabel is used for  displaying the time in text in time Format: Hours:Minutess:Seconds
	 * 
	 */
	
	Timer timer = new Timer(1000, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			elapsedTime += 1000;
			hours   = (elapsedTime / 3600000);
			minutes = (elapsedTime / 60000) % 60;
			seconds = (elapsedTime / 1000)  % 60;
			seconds_String_Holder = String.format("%02d", seconds);
			minutes_String_Holder = String.format("%02d", minutes);
			hours_String_Holder   = String.format("%02d", hours);
			timeLabel.setText(hours_String_Holder + ":" + minutes_String_Holder + ":" + seconds_String_Holder);
		}
	});
	

	StopWatch(){
		
		timeLabel.setText(hours_String_Holder + ":" + minutes_String_Holder + ":" + seconds_String_Holder);
		timeLabel.setBounds(100, 100, 200, 100);
		timeLabel.setFont(new Font("TimesRoman", Font.PLAIN, 35));
		timeLabel.setOpaque(true);
		timeLabel.setBorder(BorderFactory.createSoftBevelBorder(1));
		timeLabel.setHorizontalAlignment(JTextField.CENTER);
		
		startButton.setBounds(100, 200, 100, 50);
		startButton.setFocusable(false);
		startButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		startButton.addActionListener(this);
		
		resetButton.setBounds(200, 200, 100, 50);
		resetButton.setFocusable(false);
		resetButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		resetButton.addActionListener(this);
		
		lapButton.setBounds(150,250, 100, 40 );
		lapButton.setFocusable(false);
		lapButton.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lapButton.addActionListener(this);
		
		listHolder   = new JList<String>(listData);
		listHolder.setBounds(100, 300, 200,250);
		listHolder.setFocusable(false);
		listHolder.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		
		frame.add(lapButton);
		frame.add(listHolder);
		frame.add(startButton);
		frame.add(resetButton);
		frame.add(timeLabel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(420, 600);
		frame.setLayout(null);
		frame.setVisible(true);
	}
	/*
	 *  The Stop() Method: Is used to stop the timer of the number clock
	 */
	
	public void stop() {
		timer.stop();
		//started = false;
		
	}
	
	/*
	 * The start() Method: Is used to start the timer of the number clock
	 */
	
	public void start() {
		timer.start();
	}
	
	/*
	 * The reset() Method: is used to stop the timer and reset the clock back to 00:00:00
	 * sets all variables back to 0, displays a new string back to 00:00:00, resets everything
	 *  ^time label is reformatted 
	 */
	public void reset() {
		timer.stop();
		elapsedTime = 0;
		hours       = 0;
		minutes     = 0;
		seconds     = 0; 
		lapCount = 1;
		listData.clear();
		started = false;
		startButton.setText("Start");
		seconds_String_Holder = String.format("%02d", seconds);
		minutes_String_Holder = String.format("%02d", minutes);
		hours_String_Holder   = String.format("%02d", hours);
		timeLabel.setText(hours_String_Holder + ":" + minutes_String_Holder + ":" + seconds_String_Holder);
	}
	
	/*
	 * the addLap() Method: adds a lap when the lap button is pressed
	 * Variable lapCount is increased by one and prints the time and the correlating lapCount number
	 * timeNumbers is the String that gets added to listData to hold the string to display
	 * EX: Lap 5: 00:45:25 
	 */
	public void addLap() {

		
		String timeNumbers = ("Lap " + lapCount + " :"+  hours_String_Holder + ":" + minutes_String_Holder + ":" + seconds_String_Holder);
		lapCount++;
		listData.addElement(timeNumbers);
	}
	/*
	 *  The ActionEvent for the source when ever each button is pressed 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == startButton) {
			if(started == false) {
				started = true;
				startButton.setText("Stop");
				start();
			}
			else {
				stop();
				startButton.setText("Start");
				started = false;
			}
			
			
		}
		if(e.getSource() == resetButton) {
			reset();
		}
		if(e.getSource() == lapButton) {
			addLap();
		}
		
	}

}
