import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class Star
{
	private Color c;
	private int x;
	private int y;
	public static final int width = 80;
	public static final int orbitCX = 400;
	public static final int orbitCY = 400;
	public static final int orbitRadius = 500;
	public Star(int x, int y, Color c)
	{
		this.x = x;
		this.y = y;
		this.c = c;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public Color getColor()
	{
		return c;
	}
	public void setX(int x)
	{
		this.x = x;
	}
	public void setY(int y)
	{
		this.y = y;
	}
	public void setColor(Color c)
	{
		this.c = c;
	}
}