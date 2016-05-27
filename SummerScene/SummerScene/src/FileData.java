/**
 * @(#)FileData.java
 *
 *
 * @author 
 * @version 1.00 2016/5/26
 */


public class FileData {
	String name;
	Thumbnail pic;
	public FileData()
	{
		this.name = "";
		pic = new Thumbnail();
	}
    public FileData(String name, Thumbnail pic)
    {
    	this.name = name;
    	this.pic = pic;
    }
	public String getName(){return name;}
	public Thumbnail getThumbnail(){return pic;}
    
}