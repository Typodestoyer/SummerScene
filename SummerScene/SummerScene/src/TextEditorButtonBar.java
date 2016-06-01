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

public class TextEditorButtonBar extends JComponent{
	
    public TextEditorButtonBar()
    {
    }
    
    public void paintComponent(Graphics g)
    {
    	g.setColor(Color.RED);
    	g.fillRect(0,0,this.getWidth(),this.getHeight());
    }
    
}