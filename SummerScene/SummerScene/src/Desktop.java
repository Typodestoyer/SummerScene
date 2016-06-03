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
    	this();
    	
		colors.add(Color.BLACK);
		shapes.add(new Polygon(new int[]{0,0,60,60},new int[]{0,45,45,0},4));
		addExeFile("Terminal",60,45, colors, shapes, apps.remove(0));
    	colors = new ArrayList<Color>();
    	shapes = new ArrayList<Polygon>();
		
		colors.add(Color.BLACK);
		colors.add(Color.GREEN);
		shapes.add(new Polygon(new int[]{0,0,45,45},new int[]{0,60,60,0},4));
		shapes.add(new Polygon(new int[]{0,0,45,45,40,40,5,5,40,45}, new int[]{0,60,60,0,5,55,55,5,5,0},10));
		addExeFile("Tetris",45,60, colors, shapes, apps.remove(0));
    	colors = new ArrayList<Color>();
    	shapes = new ArrayList<Polygon>();
    	
    	addTextFile("Test 1",new String[]{"Hello, world!"});
    	addFolder("Test Folder");    	
    }
    
}