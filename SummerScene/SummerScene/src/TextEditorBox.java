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
		USER_PREFIX = "";
		TextFile newFile = (TextFile)inputFile;
		this.file = newFile;
		this.fileName = newFile.getName();
		this.contents = newFile.getContents();
		process(contents);
		setSize(width,height-ButtonedApp.BUTTON_BAR_HEIGHT);
    }
    
    public String getPrefix()
    {
    	return "";
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
		String[] lines = split(contents,'\n');
		for(String line : lines)
		{
			addNewLine(line);
		}
	}
	
	@Override
	public void backspace()
	{
		if(getTextLines().get(getTextLines().size()-1).equals(""))
		{
			if(getTextLines().size() > 1)
			{
				removeLine(getTextLines().size()-1);				
			}		
		}
		else
		{
			setLine(getTextLines().size()-1,getTextLines().get(getTextLines().size()-1).substring(0,getTextLines().get(getTextLines().size()-1).length()-1),USER_PREFIX);
		}
	}
	
	private String[] split(String s, char splitChar)
	{
		ArrayList<String> contents = new ArrayList<String>();
		contents.add("");
		for(int i = 0; i < s.length(); i ++)
		{
			char c = s.charAt(i);
			if(c == splitChar)
			{
				contents.add("");
			}
			else
			{
				contents.set(contents.size()-1,contents.get(contents.size()-1) + c);
			}
		}
		String[] arr = new String[contents.size()];
		arr = contents.toArray(arr);
		return arr;
	}
}