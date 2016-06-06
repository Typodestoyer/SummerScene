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
	FolderApp foldApp;
	ArrayList<FileData> folderContents = new ArrayList<FileData>();
    public Folder()
    {
    	super();
    	foldApp = (FolderApp)getApp();
    	System.out.println(folderContents);
    }
    
    public Folder(String name)
    {
    	super(name, getPic(), new FolderApp(700,600));
    	foldApp = (FolderApp)getApp();
    }
    
    public Folder(String name, int width, int height)
    {
    	super(name, getPic(), new FolderApp(width,height));
    	foldApp = (FolderApp)getApp();
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
    
    //@Override
    public FolderApp getFolderApp()
    {
    	foldApp = (FolderApp)getApp();
    	if(foldApp == null)
    	{
    		throw new RuntimeException("Foldapp broken!");
    	}
    	return foldApp;
    }
    
    public void add(ExeFile file)
    {
    	folderContents.add(file);
    	getFolderApp().addExeFile(file);
    }
    
    public void addTextFile(String name, String[] contents)
    {
    	folderContents.add(new TextFile(name, contents));
    	getFolderApp().addTextFile(name, contents);
    }
    
    public void addFolder(String name)
    {
    	folderContents.add(new Folder(name));
    	getFolderApp().addFolder(name);
    }
    
    public FileData get(String name)
    {
    	for(FileData file : folderContents)
    	{
    		if(file.getName().equals(name))
    		{
    			return file;
    		}
       	}
       	throw new RuntimeException("That folder cannot be found!");
    }
    
    public Folder getSubfolder(String name)
    {
    	for(FileData file : folderContents)
    	{
    		if(file instanceof Folder)
    		{
    			if(file.getName().equals(name))
    			{
    				return (Folder)file;
    			}
    		}
    	}
       	return null;
    }
    
    public ArrayList<FileData> getContents()
    {
    	return folderContents;
    }
}