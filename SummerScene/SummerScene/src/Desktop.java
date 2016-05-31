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
	FileType type = FileType.GEN;
	ArrayList<Color> colors = new ArrayList<Color>();
	ArrayList<Polygon> shapes = new ArrayList<Polygon>();
	ArrayList<Application> apps;
	Terminal t = new Terminal();
    int xLoc = 20, yLoc = 20;
    
    public enum FileType
    {
    	EXE, TXT, GEN
    }
    
    public Desktop()
    {
    }
    public Desktop(int width, int height, ArrayList<Application> apps)
    {
		setLayout(null);
		setBackground(backgroundColor);
		setOpaque(true);
		setSize(new Dimension(width, height));
		this.apps = apps;
		
		type = FileType.EXE;
		colors.add(Color.BLACK);
		shapes.add(new Polygon(new int[]{0,0,60,60},new int[]{0,45,45,0},4));
		addItem("Terminal",60,45, colors, shapes, apps.remove(0));
    	colors = new ArrayList<Color>();
    	shapes = new ArrayList<Polygon>();
		
		type = FileType.EXE;
		colors.add(Color.BLACK);
		colors.add(Color.GREEN);
		shapes.add(new Polygon(new int[]{0,0,45,45},new int[]{0,60,60,0},4));
		shapes.add(new Polygon(new int[]{0,0,45,45,40,40,5,5,40,45}, new int[]{0,60,60,0,5,55,55,5,5,0},10));
		addItem("Tetris",45,60, colors, shapes, apps.remove(0));
    	colors = new ArrayList<Color>();
    	shapes = new ArrayList<Polygon>();
    	
    }
    @Override
    protected void paintComponent(Graphics g)
    {
    	int i = 0;
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
    
    public void addTextFile(String name, String[] contents)
    {
    	items.add(new DesktopIcon(xLoc, yLoc, new TextFile(name,contents)));
    	xLoc += 100;
    	if(xLoc > this.getWidth() - 100)
    	{
    		xLoc = 20;
    		yLoc += 100;
    	}
    }
    
    public void addItem(String name, int width, int height, ArrayList<Color> colors, ArrayList<Polygon> shapes, Application app)
    {
    	if(type == FileType.EXE)
    	{
    		items.add(new DesktopIcon(xLoc, yLoc, new ExeFile(name, new Thumbnail(width,height,colors,shapes), app)));
    	}
    	else
    	{
    		
    	}
    	xLoc += 100;
    	if(xLoc > this.getWidth() - 100)
    	{
    		xLoc = 20;
    		yLoc += 100;
    	}
    	type = FileType.GEN;
    }
    
    public DesktopIcon getIcon(int i)
    {
    	return items.get(i);
    }
}