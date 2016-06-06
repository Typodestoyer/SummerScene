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

public class Desktop extends FolderApp
{
	public Color getBackgroundColor()
	{
		return new Color(119,181,254);
	}
	
	
	//For Constructing Pre-existing Files
	ArrayList<Color> colors = new ArrayList<Color>();
	ArrayList<Polygon> shapes = new ArrayList<Polygon>();
	
	Terminal t = new Terminal();
    int xLoc = 20, yLoc = 20;
    
    public Desktop()
    {
    	super();
    }
    public Desktop(int width, int height, ArrayList<Application> apps)
    {
    	super(width, height);	
    }
    
}