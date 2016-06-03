/**
 * @(#)DesktopFolder.java
 *
 *
 * @author 
 * @version 1.00 2016/6/3
 */
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class DesktopFolder extends Folder
{
	Desktop d;
    public DesktopFolder(int width, int height, ArrayList<Application> apps)
    {
    	super("");
    	d = new Desktop(width, height, apps);
    }
    @Override
    public Application getApp()
    {
    	return d;
    }
    
    public Desktop getDesktop()
    {
    	return d;
    }
}