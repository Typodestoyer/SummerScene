/**
 * @(#)TextFile.java
 *
 *
 * @author 
 * @version 1.00 2016/5/26
 */


public class TextFile extends FileData {
    public TextFile()
    {
    	
    }
    
    public TextFile(String name)
    {
    	super(name);
    }
    
    public Scene getScene()
    {
    	return Scene.TEXT_EDITOR;
    }
}