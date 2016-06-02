/**
 * @(#)ButtonedApp.java
 *
 *
 * @author 
 * @version 1.00 2016/5/31
 */


import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class ButtonedApp implements LayoutManager
{
	public static int BUTTON_BAR_HEIGHT = 20;
	int panelHeight = 550, panelWidth = 700;
	ArrayList<Component> stuff = new ArrayList<Component>();
    public ButtonedApp()
    {
    	
    }
    /*
    void addLayoutComponent(Component comp, Object constraints)
    {
    	return;
    }
		//Adds the specified component to the layout, using the specified constraint object.
	float getLayoutAlignmentX(Container target)
	{
		return 1.0;
	}
		//Returns the alignment along the x axis.
	float getLayoutAlignmentY(Container target)
	{
		return 1.0;
	}
		//Returns the alignment along the y axis.
	void invalidateLayout(Container target)
	{
		return;
	}
		//Invalidates the layout, indicating that if the layout manager has cached information it should be discarded.
	Dimension maximumLayoutSize(Container target)
	{
		return new Dimension(1, 1);
	}
		//Calculates the maximum size dimensions for the specified container, given the components it contains.
	*/
	public void addLayoutComponent(String name, Component comp)
	{
		stuff.add(comp);
	}
		//If the layout manager uses a per-component string, adds the component comp to the layout, associating it with the string specified by name.
	public void layoutContainer(Container parent)
	{
		stuff.get(0).setBounds(0,0,panelWidth,BUTTON_BAR_HEIGHT);
		stuff.get(1).setBounds(0,BUTTON_BAR_HEIGHT,panelWidth,panelHeight);
	}
		//Lays out the specified container.
	public Dimension minimumLayoutSize(Container parent)
	{
		return new Dimension(BUTTON_BAR_HEIGHT,50);
	}
		//Calculates the minimum size dimensions for the specified container, given the components it contains.
	public Dimension preferredLayoutSize(Container parent)
	{
		return new Dimension(BUTTON_BAR_HEIGHT,100);
	}
		//Calculates the preferred size dimensions for the specified container, given the components it contains.
	public void removeLayoutComponent(Component comp)
	{
		
	}
		//Removes the specified component from the layout.
}