/**
 * @(#)Application.java
 *
 *
 * @author 
 * @version 1.00 2016/5/26
 */
import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public abstract class Application extends JPanel {
	
    public Application()
    {
    	setLayout(null);
		setOpaque(true);
    }
    
    public abstract Scene getScene();
}