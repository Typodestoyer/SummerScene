/**
 * @(#)ButtonBar.java
 *
 *
 * @author 
 * @version 1.00 2016/5/31
 */

import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class TextEditorButtonBar extends JPanel{
	
    public TextEditorButtonBar()
    {
    	setLayout(new GridLayout(1,10));
    	add(new JButton("Save"));
    	add(new JButton("Exit"));
    	add(new JPanel());
    	add(new JPanel());
    	add(new JPanel());
    	add(new JPanel());
    	add(new JPanel());
    	add(new JPanel());
    	add(new JPanel());
    	add(new JPanel());
    }
    
    public void paintComponent(Graphics g)
    {
    	g.setColor(Color.RED);
    	g.fillRect(0,0,this.getWidth(),this.getHeight());
    }
    
}