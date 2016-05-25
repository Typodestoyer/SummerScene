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


public class DesktopIcon {
	
	Color c;
	String text;
	int x;
	int y;
	int width;
	int height;
	Polygon p;
	
    public DesktopIcon(int x, int y, int width, int height, Color c, String text)
    {
    	this.x = x;
    	this.y = y;
    	this.width = width;
    	this.height = height;
    	this.p = new Polygon(new int[]{x,x+width,x+width,x}, new int[]{y,y,y+height,y+height},4);
    	this.c = c;
    	this.text = text;
    }
    public Polygon getPolygon()
    {
    	return p;
    }
    public Color getColor()
    {
    	return c;
    }
    public String getText()
    {
    	return text;
    }
    public int getX()
    {
    	return x;
    }
	public int getY()
	{
		return y;
	}
	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}
}