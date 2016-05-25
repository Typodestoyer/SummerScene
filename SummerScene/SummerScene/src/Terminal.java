import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class Terminal extends JPanel
{
	private final int TEXT_HEIGHT = 14;
	ArrayList<JLabel> visTextLines = new ArrayList<JLabel>();
	ArrayList<String> textLines = new ArrayList<String>();
	ArrayList<Command> commands = new ArrayList<Command>();
	boolean exit = false;
	public Terminal()
	{
		setLayout(null);
		setBackground(Color.BLACK);
		setOpaque(true);
		addNewLine("", true);
		createNewResponses();
	}
	protected void paintComponent(Graphics g)
	{
		this.removeAll();
		g.setColor(Color.BLACK);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		for(int i = visTextLines.size()-1; i >= Math.max(0,visTextLines.size()-Math.ceil((double)(this.getHeight())/TEXT_HEIGHT)); i --)
		{
			JLabel jl = visTextLines.get(i);
			jl.setFont(new Font("Lucida Console", 0, TEXT_HEIGHT - 1));
			jl.setBounds(0,(int)(TEXT_HEIGHT*(i-Math.max(0,visTextLines.size()-((double)(this.getHeight())/TEXT_HEIGHT)))),this.getWidth(),TEXT_HEIGHT);
			this.add(jl);
		}
		//consoleDisplay();
		//bug testing
		
	}
	public void backspaceCurrentLine()
	{
		if(!textLines.get(textLines.size()-1).equals(""))
		{
			setLine(textLines.size()-1,textLines.get(textLines.size()-1).substring(0,textLines.get(textLines.size()-1).length()-1),true);
		}
		repaint();
	}
	
	public void paintThis()
	{
		repaint();
	}
	/*---------------------------------FOR DEBUGGING-------------------------------------
	public void consoleDisplay()
	{
		System.out.println("-----" + textLines.size() + " " + visTextLines.size());
		for (int i = 0; i < visTextLines.size(); i ++)
		{
			JLabel jl = visTextLines.get(i);
			System.out.println(i + ": " + jl.getText());
		}
	}
	-----------------------------------------------------------------------------------*/
	public void addToCurrentLine(char c)
	{
		if(c == '\n')
		{
			newLine();
			
		}
		else
		{
			setLine(textLines.size()-1,textLines.get(textLines.size()-1) + c,true);
		}
		repaint();
	}
	public void newLine()
	{
		processCurrentLine();
		while(textLines.size() * TEXT_HEIGHT >= this.getHeight())
		{
			removeFirstLine();
		}
	}
	public void clearScreen()
	{  
		for(int i = 0; i < 15; i ++)
		{
			System.out.println();
		}
	}
	public void setLine(int index, String line, boolean user)
	{
		textLines.set(index, line);
		visTextLines.get(index).setText((user ? "> " : "") + line);
		//System.out.println(index + " " + line + " " + visTextLines.get(index).getText());
	}
	public void addNewLine(String line, boolean user)
	{
		textLines.add(line);
		visTextLines.add(new JLabel(user ? "> " : "" + line));
		JLabel jl = visTextLines.get(visTextLines.size()-1);
		jl.setBounds(0,TEXT_HEIGHT*(visTextLines.size()-1),this.getWidth(),TEXT_HEIGHT);
		jl.setForeground(Color.WHITE);
		jl.setVisible(true);
		this.add(jl);
	}
	public void processCurrentLine()
	{
		String line = textLines.get(textLines.size()-1);
		if(line.equals(""))
		{
			addNewLine("",true);
			return;
		}
		for(int i = 0; i < commands.size(); i ++)
		{
			if(commands.get(i).calls().contains(line))
			{
				addNewLine(commands.get(i).response, false);
				evalInput(i);
				addNewLine("",false);
				addNewLine("",true);
				return;
			}
		}
		addNewLine("Unknown command.", false);
		addNewLine("",false);
		addNewLine("",true);
	}
	
	private void createNewResponses()
	{
		addResponse(new String[] {"help", "help!", "Help", "Help!"}, "", "You know how to use this ya doofus");
		addResponse(new String[] {"ping", "Ping"}, "Pong!", "Back at ya!");
		addResponse(new String[] {"what-is-love", "what is love?", "Love", "love"}, "Baby, don't hurt me", "What do you think this does? Cmon, you're smart.");
		addResponse(new String[] {"hi", "Hi", "Hi!", "Hello", "Hello!", "hello"}, "Hi, how are you?", "Greets you");
		addResponse(new String[] {"exit", "letmeout","aah"}, "Exiting...", "Lets you leave ;)");
		Collections.sort(commands);
	}
	
	private void help()
	{
		removeLine();
		for(int i = 0; i < commands.size(); i ++)
		{
			addNewLine(commands.get(i).calls().get(0) + ": " + commands.get(i).help(), false);
		}
	}
	
	private void addResponse(String[] calls, String response, String help)
	{
		commands.add(new Command(calls, response, help));
	}
	
	private void evalInput(int index)
	{
		switch(commands.get(index).calls().get(0))
		{
			case "help":
				help();
				break;
			case "exit":
				exit = true;
				break;
			default:
		}
	}
	
	private void removeLine()
	{
		textLines.remove(visTextLines.size()-1);
		visTextLines.remove(visTextLines.size()-1);
	}
	
	private void removeFirstLine()
	{
		textLines.remove(0);
		this.remove(visTextLines.get(0));
		visTextLines.remove(0);
	}
	
	public boolean shouldExit()
	{
		if(exit)
		{
			exit = false;
			return true;
		}
		
		else
		{
			return false;
		}
	}
}