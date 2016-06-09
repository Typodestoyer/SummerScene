/**
 * @(#)ExeFile.java
 *
 *
 * @author 
 * @version 1.00 2016/5/26
 */
import javax.swing.Icon;

public class ExeFile extends FileData {
	
	public ExeFile()
	{
		
	}
	
	public ExeFile(String name, Icon pic)
	{
		super(name, pic);
		app = new SolitairePanel();
	}
	
	public ExeFile(String name, Icon pic, Application app)
	{
		super(name, pic, app);
	}
	
    
}