import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
public class TetrisTake2
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
    	frame.setTitle("My Summer Scene");
    	frame.setSize(700, 600);
    	frame.setPreferredSize(new Dimension(700, 600));
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	TetrisGame panel = new TetrisGame();
    	frame.add(panel);
    	frame.getContentPane().add(panel);
    	frame.setVisible(true);
    	frame.validate();
		System.out.println("k");
    	panel.run();
	}
}