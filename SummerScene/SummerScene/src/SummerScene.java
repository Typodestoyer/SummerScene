import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class SummerScene extends FocusTraversalPolicy
{
	public static void main (String[] args)
	{
		SummerScene mainScene = new SummerScene();
	}
	
	
	String line;
	JFrame frame;
	Desktop desktop;
	TetrisGame tetris = new TetrisGame();
	
	Terminal terminal;
	Summer summer;
	Scene s = Scene.SUMMER;
	
	public SummerScene()
	{
		frame = new JFrame();
    	frame.setTitle("My Summer Scene");
    	frame.setSize(700, 600);
    	frame.setPreferredSize(new Dimension(700, 600));
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	InputDetector input = new InputDetector();
    	frame.addMouseListener(input);
    	frame.addKeyListener(input);
    	
    	terminal = new Terminal();
    	summer = new Summer();
    	desktop = new Desktop(frame.getWidth(),frame.getHeight());
    	
    	frame.getContentPane().add(summer);
    	frame.setVisible(true);
    	
	}
	/*
	public void keyTyped(KeyEvent e)
	{
		if(s == Scene.TERMINAL)
		{
			if(e.getKeyChar() == '\b')
			{
				terminal.backspaceCurrentLine();
			}
			else if((int)e.getKeyChar() == KeyEvent.VK_ESCAPE)
			{
				
			}
			else
			{
				terminal.addToCurrentLine(e.getKeyChar());
				if(terminal.shouldExit())
				{
					s = Scene.DESKTOP;
					frame.remove(terminal);
					frame.add(desktop);
					frame.repaint();
					summer.repaint();
				}
			}
		}
		
		else if(s == Scene.DESKTOP)
		{
			if((int)e.getKeyChar() == KeyEvent.VK_ESCAPE)
			{
				if(s == Scene.DESKTOP)
				{
					s = Scene.SUMMER;
					frame.remove(terminal);
					frame.add(summer);
					frame.repaint();
					summer.repaint();
				}
			}
		}
		frame.validate();
	}*/
	
	private class InputDetector extends KeyAdapter implements MouseListener
	{
		Scene upcomingScene = Scene.SUMMER;
		private void updateUpcomingScene()
		{
			System.out.println("Checking for updates!");
			switch(s)
			{
				case SUMMER:
					upcomingScene = summer.upcomingScene();
					break;
				case DESKTOP:
					upcomingScene = desktop.upcomingScene();
					break;
				case TERMINAL:
					upcomingScene = terminal.upcomingScene();
					break;
				case TETRIS:
					upcomingScene = tetris.upcomingScene();
					break;
				
			}
		}
		@Override
		public void mouseClicked(MouseEvent e){updateUpcomingScene();}
		@Override
		public void keyTyped(KeyEvent e){
			if((int)e.getKeyChar() == KeyEvent.VK_ESCAPE || e.getKeyChar() == '\n')
				updateUpcomingScene();
		}
		@Override
		public void mouseReleased(MouseEvent e){}
		@Override
		public void mousePressed(MouseEvent e){}
		@Override
		public void mouseEntered(MouseEvent e){}
		@Override
		public void mouseExited(MouseEvent e){}
	}
	
	/*
	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(s==Scene.SUMMER && summer.getComputer().contains(e.getX()-8,e.getY()-30))
		{
			summer.unhighlight();
			s = Scene.DESKTOP;
			frame.remove(summer);
			frame.add(desktop);
			frame.repaint();
			terminal.repaint();
			frame.validate();
		}
		
		if(s==Scene.DESKTOP)
		{
			for(int i = 0; i < desktop.getItems().size(); i ++)
			{
				Rectangle r = desktop.getItems().get(i);
				if(r.contains(e.getX()-8,e.getY()-30))
				{
					s = Scene.TERMINAL;
					frame.remove(desktop);
					frame.add(terminal);
					frame.repaint();
					terminal.repaint();
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
	*/
}

/*CODE ARCHIVES
 *
 *
 *
	
	private void polygon(Graphics2D g, int[] x, int[] y, Color fill)
	{
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(2));
		g.drawPolygon(x,y,x.length);
		g.setColor(fill);
		g.fillPolygon(x,y,x.length);
	}
	
	private void rect(Graphics2D g, int x, int y, int w, int h, Color fill)
	{
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(2));
		g.drawRect(x,y,w,h);
		g.setColor(fill);
		g.fillRect(x,y,w,h);
	}
 *
 *
 *
 *
 *
    	
    //CURL AT BACK
    int slope = (int)((double)-1/(DSLOPE));
    int midX = (int)(CX + (double)CSX/2);
    int midY = (int)(CY + CLH - (double)CH/2);
    int midL = (int)Math.sqrt(Math.pow(CSX/2,2) + Math.pow(CH/2,2));
    int radius = 100;
    int lDist = (int)Math.sqrt(radius * radius - midL * midL);
    int xCenter = midX - lDist*1;
 *
 *
 *
 *
 **/
 		/*
		 *
		 *  rectPrism(g,CX,CY-CLH,CLW,DVO,CSX,CH,CC,d2(CC),b(CC));    	    	//TOP OF BACK
    		rectPrism(g,CX,-CLH+CY,(int)(CLW*CBWF),CLH,(int)(CLW*CBWF),(int)(CSLY*CBWF),CC,d2(CC),b(CC));
    		rectPrism(g,(CSX-(int)(CLW*CBWF))/2+CX,-CLH+(CH-DVO)/2+CY,(int)(CLW*CBWF),CLH,(int)(CLW*CBWF),(int)(CSLY*CBWF),CC,d2(CC),b(CC));
    		rectPrism(g,CSX-(int)(CLW*CBWF)+CX,CH-CLH-DVO+CY,(int)(CLW*CBWF),CLH,(int)(CLW*CBWF),(int)(CSLY*CBWF),CC,d2(CC),b(CC));
		 *
		 *
		 **/