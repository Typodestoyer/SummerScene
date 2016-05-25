import java.util.*;
public class Command implements Comparable
{
	String[] calls;
	String response;
	String help;
	public Command(String[] calls, String response, String help)
	{
		this.calls = calls;
		this.response = response;
		this.help = help;
	}
	public List<String> calls()
	{
		return Arrays.asList(calls);
	}
	public String response()
	{
		return response;
	}
	public String help()
	{
		return help;
	}
	public int compareTo(Object o)
	{
		Command other = (Command)(o);
		return (calls().get(0).compareToIgnoreCase(other.calls().get(0)));
	}
}