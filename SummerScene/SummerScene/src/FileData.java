/**
 * @(#)FileData.java
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

public abstract class FileData {
	String name;
	Thumbnail pic;
	Application app;
	public FileData()
	{
		this.name = "";
		pic = new Thumbnail();
	}
    public FileData(String name, Thumbnail pic)
    {
    	this.name = name;
    	this.pic = pic;
    }
    public FileData(String name, Thumbnail pic, Application app)
    {
    	this(name,pic);
    	this.app = app;
	}
	public FileData(String name) //assumes txt
	{
		ArrayList<Color> colors = new ArrayList<Color>();
    	ArrayList<Polygon> shapes = new ArrayList<Polygon>();
    	colors.add(new Color(200,200,200));
    	shapes.add(new Polygon(new int[]{0,0,45,45}, new int[]{0,60,60,0}, 4));
    	this.name = name;
    	this.pic =  new Thumbnail(45,60,colors,shapes);
    	/*new TextEditor(name)*/
	}
	
	public Application getApp()
	{
		return app;
	}
	
	public void setApp(Application app)
	{
		this.app = app;
	}
	
	public Scene getScene()
	{
		return app.getScene();
	}
	public String getName(){return name;}
	public Thumbnail getThumbnail(){return pic;}
}