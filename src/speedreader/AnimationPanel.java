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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class AnimationPanel extends JPanel {

    private int x = 0;
    private int y = 0;
    private String current= "";
    private int counter = 0;

    public AnimationPanel(int speed, List<String> arr) {
    	 this.setLayout(null);
    	
    	 //pause button
		 JButton pauseButton = new JButton("Pause");
		 pauseButton.setBounds(100, 525, 100, 30);
		 
		 this.add(pauseButton);
		 
		 //timer
        Timer timer = new Timer(speed, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
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
        timer.start();
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

}
