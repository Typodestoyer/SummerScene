/**
 * @(#)TextFile.java
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

public class TextFile extends MyFile
{
    String contents;
    public TextFile()
    {
    	super();
    }
    
    public TextFile(String name)
    {
    	super(name, getPic());
    }
    
    
    public TextFile(String name, String[] words)
    {
    	
		super(name,getPic());
		contents = "";
		for(String s : words)
		{
			contents += s + " ";
		}
		contents = contents.substring(0,contents.length()-1);
    }
    
    public static Thumbnail getPic()
    {
		ArrayList<Color> colors = new ArrayList<Color>();
    	ArrayList<Polygon> shapes = new ArrayList<Polygon>();
    	colors.add(new Color(200,200,200));
    	shapes.add(new Polygon(new int[]{0,0,45,45}, new int[]{0,60,60,0}, 4));
    	return new Thumbnail(45,60,colors,shapes);
    }
    
    public Scene getScene()
    {
    	return Scene.TEXT_EDITOR;
    }
    
    public String getContents(){return contents;}
    public void setContents(String contents){this.contents = contents;}
}