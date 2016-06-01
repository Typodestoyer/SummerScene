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
	
    public TextEditorButtonBar(ArrayList<JButton> buttons)
    {
    	setLayout(new GridLayout(1,5));
    	for(JButton button : buttons)
    	{
    		add(button);
    	}
    	while(getComponentCount()<6)
    	{
    		add(new JLabel());
    	}
    }
    
    public void paintComponent(Graphics g)
    {
    	g.setColor(new Color(200,200,200));
    	g.fillRect(0,0,this.getWidth(),this.getHeight());
    }
    
}