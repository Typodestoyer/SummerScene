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


public class TextEditorBox extends TextInputPanel
{
	TextFile file;
	String fileName;
	String contents;
	
	public TextEditorBox(FileData inputFile, int width, int height)
	{
		super("");
		TextFile newFile = (TextFile)inputFile;
		this.file = newFile;
		this.fileName = newFile.getName();
		this.contents = newFile.getContents();
		process(contents);
		setSize(width,height-ButtonedApp.BUTTON_BAR_HEIGHT);
    }
    
    public Scene getScene(){return Scene.TEXT_EDITOR;}
    /*
    @Override
    protected void paintComponent(Graphics g)
    {
    	g.setColor(Color.WHITE);
    	g.fillRect(0,0,this.getWidth(),this.getHeight());
    	g.setColor(Color.BLACK);
    	g.drawString(contents, 50, 50);
    }
    */
	@Override
	public Color getBackgroundColor()
	{
		return Color.WHITE;
	}
	public Color getTextColor(){return Color.BLACK;}
	
	public void save()
	{
		contents = "";
		for(String line : getTextLines())
		{
			contents += line + '\n';
		}
		contents = contents.substring(0,contents.length()-1);
		file.setContents(contents);
	}
	
	public void process(String contents)
	{
		String[] lines = contents.split("\n");
		for(String line : lines)
		{
			addNewLine(line);
		}
	}
}