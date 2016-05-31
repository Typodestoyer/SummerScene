/**
 * @(#)TextEditor.java
 *
 *
 * @author 
 * @version 1.00 2016/5/31
 */
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;


public class TextEditor extends Application
{
	TextFile file;
	String fileName;
	String contents;
	public TextEditor(FileData inputFile)
	{
		TextFile newFile = (TextFile)inputFile;
		this.file = newFile;
		this.fileName = newFile.getName();
		this.contents = newFile.getContents();
    }
    
    public Scene getScene(){return Scene.TEXT_EDITOR;}
    
    protected void paintComponent(Graphics g)
    {
    	g.setColor(Color.WHITE);
    	g.fillRect(0,0,this.getWidth(),this.getHeight());
    	g.setColor(Color.BLACK);
    	g.drawString(contents, 50, 50);
    }
}