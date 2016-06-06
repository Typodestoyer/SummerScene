/**
 * @(#)FolderApp.java
 *
 *
 * @author 
 * @version 1.00 2016/6/3
 */
 
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;


public class FolderApp extends Application
{	
	ArrayList<DesktopIcon> items = new ArrayList<DesktopIcon>();
	int xLoc = 20, yLoc = 20;
	
    public FolderApp()
    {
    	super();
    	setLayout(null);
		setBackground(getBackgroundColor());
		setOpaque(true);
    	
    }
    
    public FolderApp(int width, int height)
    {
    	this();
		setSize(new Dimension(width, height));
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
    	int i = 0;
		g.setColor(getBackgroundColor());
		g.fillRect(0,0,this.getWidth(),this.getHeight());
    	for(DesktopIcon item : items)
    	{
    		item.draw(this, g, item.getX(),item.getY());
    	}
    }
	
	public Scene getScene()
	{
		return Scene.FOLDER;
	}
	
	public Color getBackgroundColor()
	{
		return Color.WHITE;
	}
    
    public void addFolder(String name)
    {
    	items.add(new DesktopIcon(xLoc, yLoc, new Folder(name)));
    	xLoc += 100;
    	if(xLoc > this.getWidth() - 100)
    	{
    		xLoc = 20;
    		yLoc += 100;
    	}
    }
    
    public void addExeFile(String name, int width, int height, ArrayList<Color> colors, ArrayList<Polygon> shapes, Application app)
    {
    	items.add(new DesktopIcon(xLoc, yLoc, new ExeFile(name, new Thumbnail(width,height,colors,shapes), app)));
    	
    	xLoc += 100;
    	if(xLoc > this.getWidth() - 100)
    	{
    		xLoc = 20;
    		yLoc += 100;
    	}
    }
    
    public void addExeFile(ExeFile file)
    {
    	items.add(new DesktopIcon(xLoc,yLoc,file));
    	xLoc += 100;
    	if(xLoc > this.getWidth() - 100)
    	{
    		xLoc = 20;
    		yLoc += 100;
    	}
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
    
    public ArrayList<Rectangle> getItems()
    {
    	ArrayList<Rectangle> rectangles= new ArrayList<Rectangle>();
    	for(DesktopIcon item : items)
    	{
    		rectangles.add(new Rectangle(item.getX(),item.getY(),item.getWidth(),item.getHeight()));
    	}
    	repaint();
    	return rectangles;
    }
    
    public DesktopIcon getIcon(int i)
    {
    	return items.get(i);
    }
}
