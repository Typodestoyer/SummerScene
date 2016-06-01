/**
 * @(#)TextEditor.java
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

public class TextEditor extends Application{
	TextEditorBox teb;
	TextEditorButtonBar tebb;
    public TextEditor(TextEditorButtonBar tebb, TextEditorBox txt)
    {
    	setLayout(new ButtonedApp());
    	this.tebb = tebb;
    	teb = txt;
    	add("buttonBar",tebb);
    	add("panel",teb);
    	repaint();
    }
    
    protected void paintComponent(Graphics g)
    {
    	tebb.repaint();
    	teb.repaint();
    }
    
    public Scene getScene()
    {
    	return Scene.TEXT_EDITOR;
    }
}