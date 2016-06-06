import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
public class SummerScene extends KeyAdapter implements MouseListener, MouseMotionListener
{
	public static void main (String[] args)
	{
		SummerScene s = new SummerScene();
	}
	
	String line;
	JFrame frame;
	DesktopFolder desktopFolder;
	Desktop desktop;
	TextEditor txtEditPanel;
	TextEditorBox txtEdit;
	Terminal terminal = new Terminal();
	TetrisGame tetris = new TetrisGame();
	SolitairePanel solitaire = new SolitairePanel();
	ArrayList<JButton> buttons = new ArrayList<JButton>();
	TextEditorButtonBar tebb;
	String currentFolderPath = "";
	Folder currentFolder;
	
	ArrayList<Application> apps = new ArrayList<Application>();
	Summer summer = new Summer();
	Scene s = Scene.SUMMER;
	
	public SummerScene()
	{
		frame = new JFrame();
    	frame.setTitle("My Summer Scene");
    	frame.setPreferredSize(new Dimension(700, 600));
    	frame.setSize(700, 600);
    	frame.setResizable(false);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.addKeyListener(this);
    	frame.addMouseListener(this);
    	frame.addMouseMotionListener(this);
    	
    	apps.add(terminal);
    	apps.add(tetris);
    	apps.add(solitaire);
    	
    	
    	desktopFolder = new DesktopFolder(frame.getWidth(),frame.getHeight(), apps);
    	currentFolder = desktopFolder;
    	desktop = desktopFolder.getDesktop();
    	terminal = new Terminal(desktopFolder);
    	//Initializing computer stuff
    	buttons.add(new ClickyJButton("Save and Exit", new SaveAndExitListener()));
    	buttons.add(new ClickyJButton("Save", new SaveListener()));
    	buttons.add(new ClickyJButton("Exit", new ExitListener()));
    	tebb = new TextEditorButtonBar(buttons);
    	
    	frame.setContentPane(summer);
    	frame.setVisible(true);
    	
	}
	
	public void keyTyped(KeyEvent e)
	{
		if(s == Scene.TERMINAL)
		{
			if(e.getKeyChar() == '\b')
			{
				terminal.backspace();
			}
			else if((int)e.getKeyChar() == KeyEvent.VK_ESCAPE)
			{
				
			}
			else
			{
				terminal.addToCurrentLine(e.getKeyChar());
				if(terminal.shouldExit())
				{
					s = Scene.FOLDER;
					frame.setContentPane(currentFolder.getApp());
					frame.repaint();
					summer.repaint();
				}
			}
		}
		
		else if(s == Scene.FOLDER)
		{
			if((int)e.getKeyChar() == KeyEvent.VK_ESCAPE)
			{
				if(currentFolderPath.equals(""))
				{
					s = Scene.SUMMER;
					frame.setContentPane(summer);
					frame.repaint();
					summer.repaint();
				}
				else
				{
					String[] folderPieces = currentFolderPath.split("\\\\");
					Folder f = desktopFolder;
					currentFolderPath = "";
					folderPieces = Arrays.copyOfRange(folderPieces,0,folderPieces.length-1);
					for(String name : folderPieces)
					{
						f = f.getSubfolder(name);
						currentFolderPath += name + "\\";
					}
					currentFolder = f;
					frame.setContentPane(f.getApp());
				}
			}
		}
		else if(s == Scene.TETRIS)
		{
			if((int)e.getKeyChar() == KeyEvent.VK_ESCAPE)
			{
				s = Scene.FOLDER;
				tetris = new TetrisGame();
				frame.setContentPane(currentFolder.getApp());
				frame.repaint();
				desktop.repaint();
			}
			else
			{
				tetris.inputKey((int)Character.toUpperCase(e.getKeyChar()));
			}
		}
		else if(s == Scene.TEXT_EDITOR)
		{
			if(e.getKeyChar() == '\b')
			{
				txtEdit.backspace();
			}
			else if((int)e.getKeyChar() == KeyEvent.VK_ESCAPE)
			{
				/*
				txtEdit.save();
				s = Scene.FOLDER;
				frame.remove(txtEditPanel);
				frame.add(desktop);
				frame.repaint();
				desktop.repaint();
				*/
			}
			else
			{
				txtEdit.addToCurrentLine(e.getKeyChar());
			}
			
			txtEditPanel.repaint();
		}
		else if(s == Scene.SOLITAIRE)
		{
			if((int)e.getKeyChar() == KeyEvent.VK_ESCAPE)
			{
				s = Scene.FOLDER;
				frame.setContentPane(currentFolder.getApp());
				frame.repaint();
				desktop.repaint();
			}
		}
		frame.validate();
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(s==Scene.SUMMER && summer.getComputer().contains(e.getX()-8,e.getY()-30))
		{
			summer.unhighlight();
			s = Scene.FOLDER;
			//frame.remove(summer);
			//frame.add(desktop);
			frame.setContentPane(desktop);
			frame.repaint();
			terminal.repaint();
			frame.validate();
		}
		if(s==Scene.FOLDER)
		{
			for(int i = 0; i < currentFolder.getFolderApp().getItems().size(); i ++)
			{
				Rectangle r = currentFolder.getFolderApp().getItems().get(i);
				if(r.contains(e.getX()-8,e.getY()-30))
				{
					s = currentFolder.getFolderApp().getIcon(i).getScene();
					switch(s)
					{
						case TERMINAL:
							frame.setContentPane(terminal);
							frame.repaint();
							terminal.repaint();
							break;
						case TETRIS:
							frame.setContentPane(tetris);
							frame.repaint();
							tetris.repaint();
							frame.validate();
							new Thread(tetris).start();
							break;
						case SOLITAIRE:
							frame.setContentPane(solitaire);
							frame.repaint();
							solitaire.repaint();
							frame.validate();
							break;
						case TEXT_EDITOR:
							txtEdit = new TextEditorBox(currentFolder.getFolderApp().getIcon(i).getFile(), frame.getWidth(),frame.getHeight());
							txtEditPanel = new TextEditor(tebb, txtEdit);
							frame.setContentPane(txtEditPanel);
							//frame.repaint();
							//txtEditPanel.repaint();
							frame.validate();
							break;
						case FOLDER:
							currentFolder = currentFolder.getSubfolder(currentFolder.getFolderApp().getIcon(i).getName());
							currentFolderPath += (currentFolder.getName() + "\\");
							frame.setContentPane(currentFolder.getApp());							
							frame.validate();
							break;
						default:
							frame.repaint();
							desktop.repaint();
					}
					frame.validate();
				}
			}			
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		if(s==Scene.SUMMER && summer.getComputer().contains(e.getX()-8,e.getY()-30))
		{
			summer.highlight();
		}
		else if(s==Scene.SUMMER)
		{
			summer.unhighlight();
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e){}
	@Override
	public void mouseReleased(MouseEvent e){}
	@Override
	public void mouseDragged(MouseEvent e){}	
	@Override
	public void mouseExited(MouseEvent e){}
	@Override
	public void mouseEntered(MouseEvent e){}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		if(s == Scene.TETRIS)
		{
			tetris.keyReleased(e);
		}
	}
	
	private class ClickyJButton extends JButton
	{
		public ClickyJButton(String text, ActionListener listener)
		{
			super(text);
			setFocusable(false);
			addActionListener(listener);
		}
	}
	
	private class ExitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			s = Scene.FOLDER;
			frame.setContentPane(currentFolder.getApp());
			frame.repaint();
		}
	}
	
	private class SaveListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			txtEdit.save();
		}
	}
	
	private class SaveAndExitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			txtEdit.save();
			s = Scene.FOLDER;
			frame.setContentPane(currentFolder.getApp());
			frame.repaint();
		}
	}
}