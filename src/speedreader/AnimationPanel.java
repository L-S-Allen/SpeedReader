/**This class controls the animation of the text from the chosen .txt file
*  It is based on example code from the following source: https://stackoverflow.com/a/27475129
*  it heavily relies on the code in the source.
*/

package speedreader;

import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

public class AnimationPanel extends JPanel {

    private int x = 0;
    private int y = 0;
    private String current= "";
    private int counter = 0;

    public AnimationPanel(int speed, List<String> arr) {
    	this.setLayout(null);
    	
    	//buttons
		JButton pauseButton = new JButton("Start");
		pauseButton.setBounds(0, 525, 100, 30);
		this.add(pauseButton);
		
		JButton backButton = new JButton("Back");
		backButton.setBounds(100, 525, 100, 30);
		this.add(backButton);
		
		JButton forwardButton = new JButton("Forward");
		forwardButton.setBounds(200, 525, 100, 30);
		this.add(forwardButton);
		 
		//timer
        Timer timer = new Timer(speed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	if(counter <= 0) {
            		backButton.setEnabled(false);
            	}
            	else if(counter >= arr.size()-1) {
            		forwardButton.setEnabled(false);
            	}
            	else {
            		backButton.setEnabled(true);
            		forwardButton.setEnabled(true);
            	}
            	      	
            	
            	if(counter < arr.size()) {
                	changeWord(arr.get(counter));
                	repaint();
                	counter++;
                }
                else {
                	changeWord("The End");
                	repaint();
                }
            	
            	
            }
        });
        
        //action for pause button
		pauseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopStart(pauseButton, timer);
				System.out.println("pause");
			}			
		});
		
		//action for back button
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backForward(timer, -1, arr, backButton, forwardButton);
				System.out.println("back");
			}			
		});
		
		//action for forward button
		forwardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backForward(timer, 1, arr, backButton, forwardButton);
				System.out.println("forward");
			}			
		});
		
		//styling for non animation components
		//Colors: #96C2F9 #D1EAE7 #0EC6B6
		pauseButton.setBackground(Color.decode("#96C2F9"));
		backButton.setBackground(Color.decode("#96C2F9"));
		forwardButton.setBackground(Color.decode("#96C2F9"));
		this.setBackground(Color.decode("#d9f3ff"));
		
        
    }

    protected void changeWord(String newWord) {
        current = newWord;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 500);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 25, 800, 500);
        
        //Background color - https://stackoverflow.com/a/575783 for background color
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Tahoma", 1, 40));
        
        //Centering courtesy of - https://stackoverflow.com/a/23730104
        FontMetrics fm = g2d.getFontMetrics();
        this.x = ((getWidth() - fm.stringWidth(current)) / 2);
        this.y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();   
        
        g2d.drawString(current, x, y);
        g2d.dispose();
    }
    
    protected void stopStart(JButton button, Timer timer) {
    	if(button.getText() == "Pause") {
    		button.setText("Continue");
    		timer.stop();
    	}
    	else {
    		button.setText("Pause");
    		timer.start();
    	}
    }
    
    protected void backForward(Timer timer, int direction, List<String> arr, JButton backButton, JButton forwardButton) {
    	//standard 30 word jump backwards or forwards.
    	int amount = 30;
    	amount *= direction;
    	counter += amount;
    	
    	//error checking
    	if(counter < 0) {
    		counter = 0;
    	}
    	
    	if(counter >= arr.size()) {
    		counter = arr.size()-1;
    	}
    	
    	if(counter <= 0) {
    		backButton.setEnabled(false);
    	}
    	else if(counter >= arr.size()-1) {
    		forwardButton.setEnabled(false);
    	}
    	else {
    		backButton.setEnabled(true);
    		forwardButton.setEnabled(true);
    	}
    	
    	//paint the new starting word
    	changeWord(arr.get(counter));
    	repaint();
    	counter++;   	
    }

}
