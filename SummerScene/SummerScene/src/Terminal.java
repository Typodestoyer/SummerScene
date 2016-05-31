import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class Terminal extends Application
{
	private final int TEXT_HEIGHT = 14;
	ArrayList<JLabel> visTextLines = new ArrayList<JLabel>();
	ArrayList<String> textLines = new ArrayList<String>();
	ArrayList<Command> commands = new ArrayList<Command>();
	boolean exit = false;
	Desktop d;
	public Terminal()
	{
		setBackground(Color.BLACK);
		addNewLine("", true);
		createNewResponses();
	}
	public void setDesktop(Desktop d){this.d = d;}
	public Scene getScene(){return Scene.TERMINAL;}
	
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
		String[] words = line.split(" ");
		for(int i = 0; i < commands.size(); i ++)
		{
			if(commands.get(i).calls().contains(words[0]))
			{
				Command c = commands.get(i);
				words = Arrays.copyOfRange(words, 1, words.length);
				boolean eval = true;
				while(c.sub().size() > 0)
				{
					if(words.length == 0 || words[0].equals("?") || words[0].equals("help"))
					{
						addNewLine("Commands for \'" + c.calls().get(0) + "\':", false );
						help(c.sub());
						eval = false;
						break;
					}
					else
					{
						boolean broke = false;
						for(int j = 0; j < c.sub().size(); j ++)
						{
							if(c.sub().get(j).calls().contains(words[0]))
							{
								c = c.sub().get(j);
								words = Arrays.copyOfRange(words, 1, words.length);
								broke = true;
								break;
							}
						}
						if(!broke)
						{
							addNewLine("Commands for \'" + c.calls().get(0) + "\':", false );
							help(c.sub());
							eval = false;
							break;
						}
					}
				}
				if(eval)
				{
					evalInput(c, words);
				}
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
		addCommand(new Command(new String[] {"help", "help!", "Help", "Help!"}, "", "You know how to use this ya doofus"));
		addCommand(new Command(new String[] {"ping", "Ping"}, "Pong!", "Back at ya!"));
		addCommand(new Command(new String[] {"what-is-love", "what is love?", "Love", "love"}, "Baby, don't hurt me", "What do you think this does? Cmon, you're smart."));
		addCommand(new Command(new String[] {"hi", "Hi", "Hi!", "Hello", "Hello!", "hello"}, "Hi, how are you?", "Greets you"));
		addCommand(new Command(new String[] {"exit", "letmeout","aah"}, "Exiting...", "Lets you leave ;)"));
		addCommand(new Command(new String[] {"test"}, "Tests multiword commands"));
		addCommand(new Command(new String[] {"touch"}, "Creates new file"));
		addCommand("test", new Command(new String[] {"this"}, "", "Tests this!", "test"));
		addCommand("test this", new Command(new String[]{"functionality!"}, "Hello", "This works!", "test this"));
		
		Collections.sort(commands);
	}
	
	public void addCommand(Command c)
	{
		commands.add(c);
	}
	
	public void addCommand(String sourcePath, Command command)
	{
		if(sourcePath.equals(""))
		{
			commands.add(command);
			return;
		}
		String[] commandNames = sourcePath.split(" ");
		Command[] parentCommands = new Command[commandNames.length];
		parentCommands[0] = find(commandNames[0], commands);
		for(int i = 1; i < parentCommands.length; i ++)
		{
			parentCommands[i] = find(commandNames[i], parentCommands[i-1].sub());
		}
		parentCommands[parentCommands.length - 1].sub().add(command);
	}
	
	public Command find(String call, ArrayList<Command> commandsToSearch)
	{
		for(Command c : commandsToSearch)
		{
			if(c.calls().contains(call))
			{
				return c;
			}
		}
		return null;
	}
	private void help(ArrayList<Command> commandsToHelp)
	{
		for(Command c : commandsToHelp)
		{
			help(c);
		}
	}
	
	private void help(Command c)
	{
		addNewLine(c.path + ": " + c.help(), false);
	}
	
	private void evalInput(Command c, String[] otherArgs)
	{
		String[] path = c.path().split(" ");
		switch(path[0])
		{
			case "help":
				help(commands);
				break;
			case "exit":
				exit = true;
				break;
			case "touch":
				if(otherArgs.length == 0)
				{
					help(c);
				}
				else
				{
					addNewLine("File " + otherArgs[0] + " added!", false);
					addFile(otherArgs[0]);	
				}
				break;
			default:
				addNewLine(c.response, false);
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
	
	public void addFile(String name)
	{
		
	}
}