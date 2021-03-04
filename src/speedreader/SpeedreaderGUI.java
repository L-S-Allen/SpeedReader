// This project is being created with help from the following - https://www.javatpoint.com/java-swing

package speedreader;

import java.awt.Color;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SpeedreaderGUI extends JFrame {
	
	private static int speed;
	private static String file = "src\\speedreader\\example.txt"; 
	private static List<String> arr;

	public SpeedreaderGUI(){
		//help for this animation from: https://stackoverflow.com/questions/27475048/simple-java-animation-using-jframe-and-jpanel
		 JFrame frame = new JFrame("Speed Reader");
		 
		 frame.add(new AnimationPanel(speed, arr));//loads the animation into the frame
         frame.pack();
         frame.setLocationRelativeTo(null); // makes it centered
         frame.setVisible(true);
		 frame.setSize(800, 600);

	}
	
	public static void main(String[] args) throws InterruptedException {
		
		//Create the JFrame
		JFrame f = new JFrame("SpeedReader");
		
		//Create and position the components in the frame
		
		//Labels
		JLabel speedLabel = new JLabel("Choose the speed / words per minute");
		speedLabel.setBounds(85,20,400,30);
		JLabel fileLabel = new JLabel("Choose the file to read (.txt file only)");
		fileLabel.setBounds(85,120,400,30);
		JLabel filePath = new JLabel("File Chosen:");
		filePath.setBounds(50,280,400,30);
				
		//Buttons
		JButton readButton = new JButton("Read");
		readButton.setBounds(140,210,100,30);
		//readButton.setEnabled(false); //disabled until a file has been chosen
		JButton fileButton = new JButton("Choose a file");
		fileButton.setBounds(140,160,100,30);
		
		//Slider
		JSlider slider = new JSlider(100, 500, 300);
		slider.setMinorTickSpacing(20);  
		slider.setMajorTickSpacing(100);  
		slider.setPaintTicks(true); 
		slider.setPaintLabels(true);  
		
		//Panel for slider		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(60,85,10,50));//A border is needed in order to position the slider properly.
		panel.setLocation(100, 100);
		panel.add(slider); //panel is added to frame (below), slider is added to panel.
		
		
		JFileChooser fc = new JFileChooser();
		//restrict to .txt files help taken from https://stackoverflow.com/a/18575926
		FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt");
		fc.setFileFilter(filter);
		
		//When fileButton is pressed, open JFileChooser and get absolute path of the file to read 
		fileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = fc.showOpenDialog(f);
				if(result == JFileChooser.APPROVE_OPTION) {
					file = fc.getSelectedFile().getAbsolutePath();
					filePath.setText("File Chosen: " + file);
					//add escaping \ to absolute path - https://stackoverflow.com/a/7553676
					file = file.replace("\\", "\\\\");
					readButton.setEnabled(true);
				}
				else {
					filePath.setText("File not found");
				}
			}			
		});
		
		/*	When readButton is pressed, get the speed value from the slider, process the text file, and start the animation 
		*	Note: reading in each word from the text file as needed and animating directly may be faster but it messes 
		*	with the speed consistency, I will look for a way to address this */
		readButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e)  {
											
				speed = slider.getValue();
				speed = 60000/speed; //minute(60 seconds) / number of words * 1000 to make it milliseconds that should be between each word displayed
				
				//create list to hold the words in the text file
				arr = new ArrayList<String>();
				
				//take in text file and load it into an ArrayList 
				//using scanner rather than buffered reader so it can do word by word without splitting
				Scanner in;
				String next = "";
				try {
					in = new Scanner(new File(file));
										
					while(in.hasNext()) {
						next = in.next();
						arr.add(next);
					}
				} catch (FileNotFoundException error) {
					System.out.println("Error finding your file");
					error.printStackTrace();
				} 
					
				//animation help from https://stackoverflow.com/a/27475129
				new SpeedreaderGUI();			
			}
			
		});
		
	//Set appearance properties
		//references: 	https://stackoverflow.com/a/1081491
		//				https://stackoverflow.com/a/39961146
		
		f.getContentPane().setBackground(Color.white);
		panel.setBackground(Color.white);
		fileButton.setBackground(Color.decode("#d9f3ff"));
		fileButton.setBorder(BorderFactory.createEmptyBorder());
		readButton.setBackground(Color.decode("#d9f3ff"));
		readButton.setBorder(BorderFactory.createEmptyBorder());
		slider.setBackground(Color.white);
		
	
	//add contents to the JFrame.
		f.add(speedLabel);
		f.add(fileLabel);
		f.add(fileButton);
		f.add(filePath);
		f.add(readButton);
		f.add(panel);
		f.pack();
		f.setSize(400,400);
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
}
