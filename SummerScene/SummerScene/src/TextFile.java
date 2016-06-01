/**
 * @(#)TextFile.java
 *
 *
 * @author 
 * @version 1.00 2016/5/26
 */


public class TextFile extends FileData {
    String contents;
    public TextFile()
    {
    	super();
    }
    
    public TextFile(String name)
    {
    	super(name);
    }
    
    
    public TextFile(String name, String[] words)
    {
    	
		super(name);
		contents = "";
		for(String s : words)
		{
			contents += s + " ";
		}
		contents = contents.substring(0,contents.length()-1);
    }
    
    public Scene getScene()
    {
    	return Scene.TEXT_EDITOR;
    }
    
    public String getContents(){return contents;}
    public void setContents(String contents){this.contents = contents;}
}