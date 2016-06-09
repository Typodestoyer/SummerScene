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
	Icon pic;
	Application app;
	public FileData()
	{
		this.name = "";
		pic = new Thumbnail();
	}
    public FileData(String name, Icon pic)
    {
    	this.name = name;
    	this.pic = pic;
    }
    public FileData(String name, Icon pic, Application app)
    {
    	this(name,pic);
    	this.app = app;
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
	public Icon getThumbnail(){return pic;}
	
	public String toString()
	{
		return (name + "\n" + pic + "\n" + app + "\n");
	}
}