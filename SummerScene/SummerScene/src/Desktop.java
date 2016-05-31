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

public class Desktop extends JPanel implements NextScene{
	
	Scene sceneSwitch = Scene.DESKTOP;
	Color backgroundColor = new Color(119,181,254);
	ArrayList<DesktopIcon> items = new ArrayList<DesktopIcon>();
	ArrayList<Color> colors = new ArrayList<Color>();
	ArrayList<Polygon> shapes = new ArrayList<Polygon>();
    int xLoc = 20, yLoc = 20;
    public Desktop()
    {
    }
    public Desktop(int width, int height)
    {
		setLayout(null);
		setBackground(backgroundColor);
		setOpaque(true);
		setSize(new Dimension(width, height));
		
		colors.add(Color.BLACK);
		shapes.add(new Polygon(new int[]{0,0,60,60},new int[]{0,45,45,0},4));
		addItem(xLoc,yLoc,"Terminal",60,45);
		
		colors.add(Color.BLACK);
		shapes.add(new Polygon(new int[]{0,0,60,60},new int[]{0,45,45,0},4));
		addItem(xLoc,yLoc,"Terminal",60,45);
    	
    }
    @Override
    public Scene upcomingScene(){return sceneSwitch;}
    @Override
    protected void paintComponent(Graphics g)
    {
		g.setColor(backgroundColor);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
    	int xLoc = 20, yLoc = 20;
    	for(DesktopIcon item : items)
    	{
    		item.draw(this, g, xLoc, yLoc);
    		xLoc += 100;
    		if(xLoc > this.getWidth() - 100)
    		{
    			xLoc = 20;
    			yLoc += 100;
    		}
    	}
    }
    
    public ArrayList<Rectangle> getItems()
    {
    	ArrayList<Rectangle> rectangles= new ArrayList<Rectangle>();
    	for(DesktopIcon item : items)
    	{
    		rectangles.add(new Rectangle(item.getX(),item.getY(),item.getWidth(),item.getHeight()));
    	}
    	return rectangles;
    }
    
    public void addItem(int x, int y, String name, int width, int height)
    {
    	items.add(new DesktopIcon(x, y, new FileData(name, new Thumbnail(width,height,colors,shapes))));
    	colors = new ArrayList<Color>();
    	shapes = new ArrayList<Polygon>();
    	xLoc += 100;
    	if(xLoc > this.getWidth() - 100)
    	{
    		xLoc = 20;
    		yLoc += 100;
    	}
    }
}