/**
 * @(#)ExeFile.java
 *
 *
 * @author 
 * @version 1.00 2016/5/26
 */


public class ExeFile extends MyFile {
	
	public ExeFile()
	{
		
	}
	
	public ExeFile(String name, Thumbnail pic)
	{
		super(name, pic);
		app = new SolitairePanel();
	}
	
	public ExeFile(String name, Thumbnail pic, Application app)
	{
		super(name, pic, app);
	}
	
    
}