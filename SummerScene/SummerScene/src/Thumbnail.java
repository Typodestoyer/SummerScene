/**
 * @(#)Thumbnail.java
 *
 *
 * @author 
 * @version 1.00 2016/5/27
 */
 
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class Thumbnail implements Icon{
	private int height;
	private int width;
	private ArrayList<Color> colors = new ArrayList<Color>();
	private ArrayList<Polygon> polygons= new ArrayList<Polygon>();
    public Thumbnail()
    {
    	width = 0;
    	height = 0;
    }
    
    public Thumbnail(int w, int h)
    {
    	width = w;
    	height = h;
    }
    
    public Thumbnail(int w, int h, ArrayList<Color> colors, ArrayList<Polygon> polygons)
    {
    	width = w;
    	height = h;
    	this.colors = colors;
    	this.polygons = polygons;
    }
    @Override
    public int getIconHeight(){return height;}
    @Override
    public int getIconWidth(){return width;}
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y)
    {
    	for(int i = 0; i < polygons.size(); i ++)
    	{
    		g.setColor(colors.get(i));
    		Polygon p = polygons.get(i);
    		p.translate(x,y);
    		g.fillPolygon(p);
    		p.translate(-x,-y);
    	}
    }
}