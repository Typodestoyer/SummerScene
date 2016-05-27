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
	Rectangle r;
	
    public DesktopIcon(int x, int y, int width, int height, Color c, String text)
    {
    	this.x = x;
    	this.y = y;
    	this.width = width;
    	this.height = height;
    	this.r = new Rectangle(x,y,width,height);
    	this.c = c;
    	this.text = text;
    }
    public Rectangle getRectangle(){return r;}
    public Color getColor(){return c;}
    public String getText(){return text;}
    public int getX(){return x;}
	public int getY(){return y;}
	public int getWidth(){return width;}
	public int getHeight(){return height;}
}