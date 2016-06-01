/**
 * @(#)TextInputPanel.java
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

public abstract class TextInputPanel extends Application
{
	protected String USER_PREFIX;
	public static final int TEXT_HEIGHT = 14;
	int xCursor = 0, yCursor = 0;
	ArrayList<JLabel> visTextLines = new ArrayList<JLabel>();
	ArrayList<String> textLines = new ArrayList<String>();
	javax.swing.Timer blinkyCursorTimer;
	boolean isCursorVisible = true;
	
	public TextInputPanel(){
		super();
		blinkyCursorTimer = new javax.swing.Timer(750,new BlinkyCursorListener());
		blinkyCursorTimer.start();
	}
	
	public void addNewLine(String line){addNewLine(line, "");}
	
	public void addNewLine(String line, String prefix)
	{
		getTextLines().add(line);
		getVisTextLines().add(new JLabel(prefix + line));
		JLabel jl = getVisTextLines().get(getVisTextLines().size()-1);
		jl.setBounds(0,TEXT_HEIGHT*(getVisTextLines().size()-1),this.getWidth(),TEXT_HEIGHT);
		jl.setForeground(getTextColor());
		jl.setVisible(true);
		this.add(jl);
		repaint();
	}
	
	public abstract Color getTextColor();
	public ArrayList<JLabel> getVisTextLines(){return visTextLines;}
	public ArrayList<String> getTextLines(){return textLines;}
	
	protected void paintComponent(Graphics g)
	{
		this.removeAll();
		
		g.setColor(getBackgroundColor());
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		
		xCursor = (visTextLines.get(visTextLines.size()-1).getText().length()) * 8;
		yCursor = ((visTextLines.size()-1)*TEXT_HEIGHT);
		if(isCursorVisible)
		{
			g.setColor(getTextColor());
			g.fillRect(xCursor,yCursor,1,TEXT_HEIGHT);
		}
		for(int i = getVisTextLines().size()-1; i >= Math.max(0,getVisTextLines().size()-Math.ceil((double)(this.getHeight())/TextInputPanel.TEXT_HEIGHT)); i --)
		{
			JLabel jl = getVisTextLines().get(i);
			jl.setFont(new Font("Lucida Console", 0, TextInputPanel.TEXT_HEIGHT - 1));
			jl.setBounds(0,(int)(TextInputPanel.TEXT_HEIGHT*(i-Math.max(0,getVisTextLines().size()-((double)(this.getHeight())/TextInputPanel.TEXT_HEIGHT)))),this.getWidth(),TextInputPanel.TEXT_HEIGHT);
			this.add(jl);
		}
		//consoleDisplay();
		//bug testing
	}
	
	public void backspace()
	{
		if(!getTextLines().get(getTextLines().size()-1).equals(""))
		{
			setLine(getTextLines().size()-1,getTextLines().get(getTextLines().size()-1).substring(0,getTextLines().get(getTextLines().size()-1).length()-1),USER_PREFIX);
		}
	}
	
	public void addToCurrentLine(char c)
	{
		if(c == '\n')
		{
			newLine();
		}
		else
		{
			setLine(getTextLines().size()-1,getTextLines().get(getTextLines().size()-1) + c,USER_PREFIX);
		}
	}
	
	public void setLine(int index, String line, String prefix)
	{
		getTextLines().set(index, line);
		getVisTextLines().get(index).setText(prefix + line);
	}
	
	public void newLine()
	{
		processCurrentLine();
		while(getTextLines().size() * TextInputPanel.TEXT_HEIGHT >= this.getHeight())
		{
			removeLine(0);
		}
		repaint();
	}
	
	public void processCurrentLine()
	{
		addNewLine("","");
	}
	
	protected void removeLine(int index)
	{
		getTextLines().remove(index);
		getVisTextLines().remove(index);
	}
	
	public abstract Color getBackgroundColor();
	
	private class BlinkyCursorListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			isCursorVisible = !isCursorVisible;
			repaint();
		}
	}
}