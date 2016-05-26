import java.util.*;
public class Command implements Comparable
{
	String[] calls;
	String response;
	String help;
	String path;
	ArrayList<Command> subcommands = new ArrayList<Command>();
	
	
	public Command(String[] calls, String help)
	{
		this.calls = calls;
		this.help = help;
		this.path = calls[0];
	}
	
	
	public Command(String[] calls, String response, String help)
	{
		this.calls = calls;
		this.response = response;
		this.help = help;
		this.path = calls[0];
	}
	
	
	public Command(String[] calls, String response, String help, String path)
	{
		this.calls = calls;
		this.response = response;
		this.help = help;
		this.path = path + " " + calls[0];
	}
	
	public int compareTo(Object o)
	{
		Command other = (Command)(o);
		return (calls().get(0).compareToIgnoreCase(other.calls().get(0)));
	}
	
	public ArrayList<Command> sub(){return subcommands;}
	public List<String> calls(){return Arrays.asList(calls);}
	public String response(){return response;}
	public String path(){return path;}
	public String help(){return help;}
}