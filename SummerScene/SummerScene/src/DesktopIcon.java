/**
 * @(#)DesktopIcon.java
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


public class DesktopIcon
{
	int x;
	int y;
	FileData file;
	
    public DesktopIcon(int x, int y, FileData file)
    {
    	this.x = x;
    	this.y = y;
    	this.file = file;
    }
    public String getName(){return file.getName();}
    public int getX(){return x;}
	public int getY(){return y;}
	public Scene getScene(){return file.getScene();}
	public int getWidth(){return file.getThumbnail().getIconWidth();}
	public int getHeight(){return file.getThumbnail().getIconHeight();}
	public Application getApp(){return file.getApp();}
	public void draw(Container c, Graphics g, int x, int y)
	{
		file.getThumbnail().paintIcon(c,g,x,y);
		g.setColor(Color.BLACK);
		g.drawString(getName(),x,y+getHeight()+15);
	}
}