/**
 * @(#)File.java
 *
 *
 * @author 
 * @version 1.00 2016/6/1
 */


public class MyFile extends FileData
{
	public MyFile()
	{
		super();
	}
    public MyFile(String name, Thumbnail pic)
    {
    	super(name,pic);
    }
    public MyFile(String name, Thumbnail pic, Application app)
    {
    	this(name,pic);
    	this.app = app;
	}
}