/**
 * @(#)Folder.java
 *
 *
 * @author 
 * @version 1.00 2016/5/26
 */
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class Folder extends FileData
{
	ArrayList<FileData> folderContents = new ArrayList<FileData>();
    public Folder()
    {
    	super();
    }
    
    public Folder(String name)
    {
    	super(name, getPic());
    }
    
    public static Thumbnail getPic()
    {
    	ArrayList<Color> colors = new ArrayList<Color>();
    	ArrayList<Polygon> shapes = new ArrayList<Polygon>();
    	colors.add(new Color(240,230,140));
    	colors.add(new Color(255,250,205));
    	shapes.add(new Polygon(new int[]{0,55,55,5},new int[]{10,0,50,60},4));
    	shapes.add(new Polygon(new int[]{10,60,55,5},new int[]{15,5,50,60},4));
    	return new Thumbnail(45,60,colors,shapes);
    }
    
    
}