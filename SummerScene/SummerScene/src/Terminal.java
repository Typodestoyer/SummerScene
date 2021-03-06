import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class Terminal extends TextInputPanel
{
	ArrayList<Command> commands = new ArrayList<Command>();
	String currentFilePath = "";
	Folder currentFolder;
	boolean exit = false;
	DesktopFolder dFile;
	Desktop d;
	public Terminal()
	{
		USER_PREFIX = "> ";
		addNewLine("",USER_PREFIX);
		setBackground(Color.BLACK);
		createNewResponses();
	}
	
	public Terminal(DesktopFolder dFile)
	{
		this();
		this.dFile = dFile;
		this.d = dFile.getDesktop();
		currentFolder = dFile;
	}
	
	public void setDesktop(Desktop d){this.d = d;}
	public Scene getScene(){return Scene.TERMINAL;}
	
	@Override
	public Color getBackgroundColor()
	{
		return Color.BLACK;
	}
	
	/*---------------------------------FOR DEBUGGING-------------------------------------
	public void consoleDisplay()
	{
		System.out.println("-----" + getTextLines().size() + " " + getVisTextLines().size());
		for (int i = 0; i < getVisTextLines().size(); i ++)
		{
			JLabel jl = getVisTextLines().get(i);
			System.out.println(i + ": " + jl.getText());
		}
	}
	-----------------------------------------------------------------------------------*/
	

	public void clearScreen()
	{  
		for(int i = 0; i < 15; i ++)
		{
			System.out.println();
		}
	}

	@Override
	public void processCurrentLine()
	{
		String line = getTextLines().get(getTextLines().size()-1);
		if(line.equals(""))
		{
			addNewLine("",getPrefix());
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
						addNewLine("Commands for \'" + c.calls().get(0) + "\':", "" );
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
							addNewLine("Commands for \'" + c.calls().get(0) + "\':", "" );
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
				addNewLine("","");
				addNewLine("",getPrefix());
				return;
			}
		}
		addNewLine("Unknown command.", "");
		addNewLine("","");
		addNewLine("",USER_PREFIX);
		repaint();
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
		addCommand(new Command(new String[] {"mkdir"}, "Makes a new folder"));
		addCommand(new Command(new String[] {"cd"}, "Changes current directory"));
		addCommand(new Command(new String[] {"ls"}, "Lists everything in current directory"));
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
		addNewLine(c.path + ": " + c.help(), "");
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
					addNewLine("File " + otherArgs[0] + " added!", "");
					addFile(otherArgs);	
				}
				break;
			case "mkdir":
				if(otherArgs.length == 0)
				{
					help(c);
				}
				else
				{
					addNewLine("Folder " + concat(otherArgs, " ") + " added!", "");
					addFolder(concat(otherArgs, " "));	
				}
				break;
			case "cd":
				if(otherArgs.length == 0)
				{
					help(c);
				}
				else
				{
					changeDirectoryTo(concat(otherArgs," "));	
				}
				break;
			case "ls":
				for(FileData f : currentFolder.getContents())
				{
					addNewLine(f.getName(),"");
				}
				break;
			default:
				addNewLine(c.response, "");
		}
	}
	
	public String concat(String[] arr, String split)
	{
		String retStr = "";
		for(String s : arr)
		{
			retStr += s + split;
		}
		retStr = retStr.substring(0,retStr.length()-split.length());
		return retStr;
	}
	
	public void changeDirectoryTo(String directory)
	{
		if(directory.equals(".."))
		{
			if(currentFilePath.equals(""))
			{
				addNewLine("Already at Desktop!", "");
			}
			else
			{
				String[] files = currentFilePath.split("\\\\");
				files = Arrays.copyOfRange(files, 0, files.length-1);
				String tempFilePath = "";
				currentFolder = dFile;
				for(String s : files)
				{
					tempFilePath += s + "\\";
					currentFolder = currentFolder.getSubfolder(s);
					if(currentFolder == null)
					{
						addNewLine("That folder does not exist!","");
						return;
					}
				}
				currentFilePath = tempFilePath;
			}	
		}
		else
		{
			if(currentFolder.getSubfolder(directory) != null)
			{
				currentFilePath += directory + "\\";
				currentFolder = currentFolder.getSubfolder(directory);
			}
			else
			{
				addNewLine("No such directory " + directory + "!");
			}
		}
	}
	
	public String getPrefix()
	{
		return currentFilePath + USER_PREFIX; 
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
	
	public void addFile(String[] args)
	{
		currentFolder.addTextFile(args[0], (args.length == 1 ? new String[]{""} : Arrays.copyOfRange(args, 1,args.length)));
	}
	
	public void addFolder(String name)
	{
		currentFolder.addFolder(name);
	}
	
	public Color getTextColor(){return Color.WHITE;}
}