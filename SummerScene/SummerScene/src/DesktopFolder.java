/**
 * @(#)DesktopFolder.java
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

public class DesktopFolder extends Folder
{
	Desktop d;
    public DesktopFolder(int width, int height, ArrayList<Application> apps)
    {
    	super("");
    	d = new Desktop(width, height, apps);
    	
    	
    	ArrayList<Color> colors = new ArrayList<Color>();
    	ArrayList<Polygon> shapes = new ArrayList<Polygon>();
		colors.add(Color.BLACK);
		shapes.add(new Polygon(new int[]{0,0,60,60},new int[]{0,45,45,0},4));
		add(new ExeFile("Terminal",new Thumbnail(60,45, colors, shapes), apps.remove(0)));
    	colors = new ArrayList<Color>();
    	shapes = new ArrayList<Polygon>();
		
		colors.add(Color.BLACK);
		colors.add(Color.GREEN);
		shapes.add(new Polygon(new int[]{0,0,45,45},new int[]{0,60,60,0},4));
		shapes.add(new Polygon(new int[]{0,0,45,45,40,40,5,5,40,45}, new int[]{0,60,60,0,5,55,55,5,5,0},10));
		add(new ExeFile("Tetris", new Thumbnail(45,60, colors, shapes), apps.remove(0)));
    	colors = new ArrayList<Color>();
    	shapes = new ArrayList<Polygon>();
    	
    	colors.add(Color.WHITE);
    	shapes.add(new Polygon(new int[]{0,0,45,45}, new int[]{0,60,60,0}, 4));
    	add(new ExeFile("Solitaire", new Thumbnail(45,60,colors, shapes), apps.remove(0)));
    	
    	colors = new ArrayList<Color>();
    	shapes = new ArrayList<Polygon>();
    	
    	addTextFile("Test 1",new String[]{"Hello, world!"});
    	addFolder("Test Folder");    
    }
    @Override
    public Application getApp()
    {
    	return d;
    }
    
    public Desktop getDesktop()
    {
    	return d;
    }
}