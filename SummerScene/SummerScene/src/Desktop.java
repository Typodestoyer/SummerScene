/**
 * @(#)Desktop.java
 *
 *
 * @author 
 * @version 1.00 2016/5/25
 */

import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class Desktop extends JPanel{

	Color backgroundColor = new Color(119,181,254);
	ArrayList<DesktopIcon> items = new ArrayList<DesktopIcon>();
    public Desktop()
    {
		setLayout(null);
		setBackground(backgroundColor);
		setOpaque(true);
    	items.add(new DesktopIcon(20, 20, 60, 45, Color.BLACK, "Terminal"));
    }
    @Override
    protected void paintComponent(Graphics g)
    {
		g.setColor(backgroundColor);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
    	for(DesktopIcon item : items)
    	{
    		g.setColor(item.getColor());
    		g.fillRect(item.getX(),item.getY(),item.getWidth(),item.getHeight());
    		g.setColor(Color.BLACK);
    		g.drawString(item.getText(), item.getX(), item.getY() + item.getHeight() + 15);
    	}
    }
    
    public ArrayList<Rectangle> getItems()
    {
    	ArrayList<Rectangle> rectangles= new ArrayList<Rectangle>();
    	for(DesktopIcon item : items)
    	{
    		rectangles.add(item.getRectangle());
    	}
    	return rectangles;
    }
}