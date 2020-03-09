/**
/*
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.*;

/*
/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("TOP LEVEL FRAME");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel(); 
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        // Main playing area
        final BoardArray court = new BoardArray(status);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);
        
  
  

        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            			String name = JOptionPane.showInputDialog(frame, "What's the snake's name?"
            					+ "(for last game).", "USER INPUT", JOptionPane.PLAIN_MESSAGE);
            			while (name.equals("")) {
            				name = JOptionPane.showInputDialog(frame, "C'mon it has a name", 
            										"USER INPUT", JOptionPane.PLAIN_MESSAGE);
            			}
            			try {
            				Writer out = new FileWriter("Scores.txt", true);
            				out.write(court.getScore() + ":" + name + "\n");
            				out.flush();
            				out.close();
            			} catch (IOException e2) {
            				System.out.println(e2);
            			}
            		
            	
                court.reset();
            }
            
            });  
        control_panel.add(reset);
        //This is the instruction button
        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String message = "Press arrow keys to move. If you eat a piece, you gain a piece"+
        				" If you fill up the board, you win! However, if you go outside the board" +
        				" or hit yourself, you lose"
        				+ " if you would like to input your score, press reset and enter" 
        				+ " your snake's name. Also, be careful of black squares...";
        		JOptionPane.showMessageDialog(frame, message);
        	}
        });
        control_panel.add(instructions);
        
        
        //This is the high scores button
        final JButton hscores = new JButton("Scores");
        hscores.addActionListener( new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		LinkedList<Score> list = new LinkedList<Score>();
        		try {
        			BufferedReader breader = new BufferedReader(new FileReader("Scores.txt"));
        			String l = breader.readLine();
        			while (l !=null) {
        				l.trim();
        				String[] arr = l.split(":");
        				
        				if (arr.length == 2) {
        					Score newscore = new Score(Integer.parseInt(arr[0]), arr[1]);
        					list.add(newscore);
        				} else {
        					String message = "Incorrect file input, please input correct form score:username"
        	        				+ "located in you txt file.";
        	        		JOptionPane.showMessageDialog(frame, message);
        				}
        				l = breader.readLine();
        			}
        			breader.close();
        			Collections.sort(list);
        	} catch (IOException e2) {
        		System.out.println(e2);
        	} catch(NumberFormatException e3) {
        		String message = "Incorrect file input, please input correct form score:username"
        				+ "located in you txt file.";
        		JOptionPane.showMessageDialog(frame, message);
        	}
        		
        		String display = null;
        		if (list.size() == 0) {
        		display = "1. " + "\n" + "2 " + "\n" + "3. " + "\n";
        		} else if (list.size() == 1) {
        			display = "1. Score: " + list.get(list.size() - 1).getPoints() + " UserName: " + 
        					list.get(list.size() - 1).getUser() +"\n" + "2 " + "\n" + "3. " + "\n";
        		} else if (list.size() == 2) {
        			display = "1. Score: " + list.get(list.size() - 1).getPoints() + " UserName: " + 
        					list.get(list.size() - 1).getUser() +"\n" + "2. Score: "
        				+ list.get(list.size() -2).getPoints() + " UserName: " + 
        				list.get(list.size() - 2).getUser() + "\n" + "3. " + "\n";
        		} else {
        			display = "1. Score: " + list.get(list.size() - 1).getPoints() + " UserName: " + 
        					list.get(list.size() - 1).getUser() +"\n" + "2 Score: "
        				+ list.get(list.size() -2).getPoints() + " UserName: " + 
        				list.get(list.size() - 2).getUser() + "\n" + "3. Score: " + 
        				list.get(list.size() -3).getPoints() + " UserName: "
        						+ "" + 
        				list.get(list.size() -3).getUser();
        		}
        		JOptionPane.showMessageDialog(frame, display, "HIGH SCORES", JOptionPane.PLAIN_MESSAGE);
        	}
        		});
        control_panel.add(hscores);
        
        
        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
            
    
    
    }
    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
    
}