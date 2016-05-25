import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;

public class Summer extends JPanel implements ActionListener
{
	
	//WINDOW
	private static final int WY = 25;	//Y OF UPPER LEFT
	private static final int WX = 100;	//X ""
	private static final int WW = 100;	//WIDTH
	private static final int WH = 100;	//HEIGHT
	
	//DESK
	private static final int DX = 450;	//X OF UPPER LEFT
	private static final int DY = 150;	//Y ""
	private static final int DH = 150;	//HEIGHT
	private static final int DW = 125;	//WIDTH
	private static final int DSX = 100;	//HORIZONTAL SLOPE DISTANCE
	private static final int DVO = 10;	//VERTICAL 3D OFFSET
	private static final int DLOX = 12;	//HOW FAR IN LEG IS X
	private static final int DLOY = 12;	//"" Y
	private static final int DLW = 10;	//LEG WIDTH
	private static final double DSLOPE = (double)DH/DSX;//SLOPE OF TABLE 3DNESS
	private static final int DSLY = (int)(DLW*DSLOPE);	//VERTICAL LEG SLOPE
	private static final int DLH = 150;	//DESK LEG HEIGHT
	
	//CHAIR
	private static final int CX = 440;	//CHAIR X
	private static final int CY = 275;	//CHAIR Y
	private static final int CW = 75;	//CHAIR WIDTH
	private static final int CH = 50;	//CHAIR HEIGHT
	private static final int CLH = 70;	//CHAIR LEG HEIGHT
	private static final int CLOX = 0;	//CHAIR LEG OFFSET X
	private static final int CLOY = 0;	//CHAIR LEG OFFSET Y
	private static final int CSX = (int)(1/DSLOPE * CH);	//CHAIR SLOPE X
	private static final int CLW = 7;	//CHAIR LEG WIDTH
	private static final int CSLY = (int)(CLW*DSLOPE);	//CHAIR SLOPE LEG Y
	private static final int CBH = 50;	//CHAIR BACK HEIGHT
	private static final double CBWF = 0.75;	//CHAIR BACK WIDTH FACTOR
	
	//PERSON
	private static final int PTSX = 20;
	private static final int PTW = 20;
	private static final int PYO = 60;
	private static final int PXO = (CSX-(PTSX/2))/2;
	private static final int PTX = CX + PXO;
	private static final int PTY = CY - CBH - PYO + (int)(PXO * DSLOPE);
	private static final int PTH = CBH + PYO;
	private static final int PTSY = (int)(PTSX*DSLOPE);
	
	//PERSON LEGS
	private static final int PLH = 20;
	private static final int PLW = 65;
	private static final int PLSX = 20;
	private static final int PLX = PTX + PTSX;
	private static final int PLY = PTY + PTH - PLH;
	private static final int PLSY = (int)(PLSX*DSLOPE);
	
	//PERSON LEGS BELOW KNEES
	private static final int PLKH = 90;
	private static final int PLKW = PLH;
	private static final int PLKX = PLX + PLW;
	private static final int PLKY = PLY;
	
	//PERSON HEAD
	private static final int PHW = PTW + 10;
	private static final int PHH = 35;
	private static final int PHXO = -3;
	private static final int PHYO = -(int)(PHXO*DSLOPE);
	private static final int PHWO = 5;
	private static final int PHHO = (int)(PHWO * DSLOPE);
	
	private static final int PHSX = 12;
	private static final int PHSY = (int)(PHSX*DSLOPE);
	private static final int PHX = PTX + PHWO + PHXO;
	private static final int PHY = PTY - PHH + PHHO + PHYO;
	
	//PERSON RIGHT ARM
	private static final int PARSX = 5;
	private static final int PARW = 60;
	private static final int PARH = 10;
	private static final int PARSY = (int)(DSLOPE * PARSX);
	private static final int PARY = PTY + PTSY + 20;
	private static final int PARX = PTX + PTSX;
	
	//PERSON LEFT ARM
	private static final int PALSX = PARSX;
	private static final int PALW = PARW;
	private static final int PALH = PARH;
	private static final int PALSY = PARSY;
	private static final int PALY = PTY + 20;
	private static final int PALX = PTX;
	
	//KEYBOARD
	private static final int KX = PALX + PALW - 20;
	private static final int KY = PALY - 5;
	private static final int KW = 30;
	private static final int KH = 2;
	private static final int KSX = 20;
	private static final int KSY = (int)(DSLOPE * KSX);
	private static final int KLGAPX = 5;
	private static final int KLGAPY = 5;
	
	//MOUSE
	private static final int MX = PARX + PLW - 5;
	private static final int MY = PARY + 5;
	private static final int MW = 20;
	private static final int MH = 5;
	private static final int MSX = 5;
	private static final int MSY = (int)(DSLOPE*MSX);
	
	//MOUSE WHEEL
	private static final int MWX = MX + MSX/2 + MW/2; 
	private static final int MWH = 2;
	private static final int MWY = MY + MSY/2 - MWH/2;
	private static final int MWW = MW/2;
	private static final int MWSX = 1;
	private static final int MWSY = (int)(DSLOPE*MWSX);
	
	//PERSON FINGER
	private static final int PFX = PARX + PARW;
	private static final int PFY = PARY + 2;
	private static final int PFW = MW/2 + 7;
	private static final int PFH = 4;
	private static final int PFSX = 1;
	private static final int PFSY = (int)(DSLOPE*PFSX);
	
	//MONITOR
	private static final int CMW = 40;
	private static final int CMH = 60;
	private static final int CMX = KX + KW + 5;
	private static final int CMY = KY - CMH;
	private static final int CMSX = 20;
	private static final int CMSY = (int)(DSLOPE*CMSX);
	private static final int CMGX = 3;
	private static final int CMGY = 5;
	
	//private static final int 
	//private static final int 
	//private static final int 
	//private static final int 
	//private static final int 
	//private static final int 

	
	//GENERAL
	private static final int LY = 250;
	private static final int LX = DX + DW;
	
	//COLOR
	private static final Color CC = new Color(161,80,8);
	private static final Color WC = new Color(242,236,148);
	private static final Color FC = new Color(200,200,200);
	private static final Color WLC = new Color(222,216,128);
	private static final int THREE_D_COLOR = 20;
	private static final Color DC = new Color(255,218,185);
	private static final Color PC = new Color(30,30,150);
	private static final Color SKINCOLOR = new Color(222,171,127);
	private static final Color MC = new Color(211,211,211);
	private static final Color MWC = new Color(128,128,128);
	private static final Color KC = new Color(150,150,150);
	private static final Color KLC = MWC;
	private static final Color CMC = new Color(100,100,100);
	private static final Color CSC = new Color(64,224,208);
	private static final Color SSC = Color.YELLOW;
	private static final Color SMC = Color.WHITE;
	//private static final Color 
	
	//Computer
	private final Polygon computer = new Polygon(new int[]{CMX,CMX,CMX+CMSX,CMX+CMSX+CMW,CMX+CMSX+CMW,CMX+CMW}, new int[]{CMY,CMY+CMH,CMY+CMH+CMSY,CMY+CMH+CMSY,CMY+CMSY,CMY}, 6);
	boolean highlighted = false;
	
	//Timekeeping
	private int hour = 12;
	private int tick = 0;
	private javax.swing.Timer t;
	private boolean armForward = false;
	
	//ANIMATION VARIABLES	
	private int armStretch = 0;
	private boolean isClicked = false;
	private int night = 0;
	private Star sun, moon;
	private double thetaDeg = 0;
	
	//FUNCTIONS------------------------------------------------------------------------------
	
	public Summer()
	{
		t = new javax.swing.Timer(25,this);
		t.setInitialDelay(200);
		t.start();
		sun = new Star(0,0,SSC);
		moon = new Star(100,0,SMC);
	}
	
	public Polygon getComputer()
	{
		return computer;
	}
	
	public void highlight()
	{
		highlighted = true;
	}
	
	public void unhighlight()
	{
		highlighted = false;
	}
	
	protected void paintComponent(Graphics g2)
	{
		Graphics2D g = (Graphics2D)g2;
		int[] x = {};
		int[] y = {};		
		
		//Behind Window
		g.setColor(n(n(Color.CYAN)));
		g.fillRect(WX,WY,WW,WH);
		
		sun.setX((int)(Star.orbitCX - Star.orbitRadius*Math.cos(Math.toRadians(thetaDeg+45))));
		sun.setY((int)(Star.orbitCY - Star.orbitRadius*Math.sin(Math.toRadians(thetaDeg+45))));
		moon.setX((int)(Star.orbitCX - Star.orbitRadius*Math.cos(Math.toRadians(thetaDeg-135))));
		moon.setY((int)(Star.orbitCY - Star.orbitRadius*Math.sin(Math.toRadians(thetaDeg-135))));
		
    	g.setColor(sun.getColor());
    	g.fillOval(sun.getX(),sun.getY(),Star.width,Star.width);
    	g.setColor(moon.getColor());
    	g.fillOval(moon.getX(),moon.getY(),Star.width,Star.width);
		
		g.setColor(n(WC));
		//g.fillRect(0,0,this.getWidth(),LY);
		x = new int[] {0,LX,LX,WX+WW,WX+WW,WX,WX,WX+WW,LX,0};
		y = new int[] {0,0,LY,WY+WH,WY,WY,WY+WH,WY+WH,LY,LY};
		g.fillPolygon(x,y,10);
		
		g.setColor(n(FC));
		g.fillRect(0,LY,this.getWidth(),(this.getHeight()-LY));
		
		g.setColor(n(WC));
    	x = new int[] {LX, LX, this.getWidth(), this.getWidth()};
    	y = new int[] {LY, 0, 0, (int)(LY + (DSLOPE*(this.getWidth()-LX)))};
    	g.fillPolygon(x,y,4);
		//Window Outline
		g.setColor(n(Color.GRAY));
		g.setStroke(new BasicStroke(7));
    	g.drawRect(WX,WY,WW,WH);
    	//Window Bars
    	g.setStroke(new BasicStroke(2));
    	g.drawLine(WX + WW/2, WY, WX + WW/2, WY + WH);
    	g.drawLine(WX, WY + WH/2, WX + WW, WY + WH/2);
    	//"Horizon" Line in House
    	g.setStroke(new BasicStroke(1));
    	g.setColor(n(Color.BLACK));
    	g.drawLine(0,LY,LX,LY);
    	g.drawLine(LX,0,LX,LY);
    	g.drawLine(LX,LY,this.getWidth(),(int)(LY + (DSLOPE*(this.getWidth()-LX))));
    	
    	//RECTANGLES!--------------------------------------------------------------------------------------
    	
    	//LEGS
    	chairLeg(g, CLOX, CLOY);																	//BACK LEFT CHAIR LEG
    	chairLeg(g, CW - CLOX - 2*DLW, CLOY);														//BACK RIGHT CHAIR LEG
    	
    	deskLeg(g, DLOX, DLOY);																		//BACK LEFT DESK LEG
    	deskLeg(g, DW - DLOX - 2*DLW, DLOY);														//BACK RIGHT DESK LEG (INVISIBLE)
    	
    	rectPrism(g,PLKX,PLKY,PLKW,PLKH,PLSX,PLSY,SKINCOLOR,d(SKINCOLOR),b(SKINCOLOR));				//PERSON'S LEGS UNDER THE TABLE BELOW THE KNEE
    	
    	chairLeg(g, CW + CSX - CLOX - 2*CLW, CH - CLOY - CSLY);										//FRONT RIGHT CHAIR LEG
    	chairLeg(g, CSX + CLOX - CLW, CH - CLOY - CSLY);											//FRONT LEFT CHAIR LEG
    	
    	rectPrism(g,CX,CY,CW,DVO,CSX,CH,CC,d2(CC),b(CC));											//CHAIR BASE
    	
    	rectPrism(g,PLX,PLY,PLW,PLH,PLSX,PLSY,PC,d(PC),b(PC));										//PERSON'S LEGS UNDER THE TABLE ABOVE KNEE (BLUE)    	
    	
    	g.setColor(Color.BLACK);
    	g.setStroke(new BasicStroke(2));
    	g.drawLine(PLX+PLW/2,PLY+(int)((PLSX/2)*DSLOPE),PLX+PLW,PLY+(int)((PLSX/2)*DSLOPE));		//LINE SEPARATING LEGS
    	
    	deskLeg(g, DW + DSX - DLOX - 2*DLW, DH - DLOY - DSLY);										//FRONT RIGHT DESK LEG
    	deskLeg(g, DSX + DLOX - DLW, DH - DLOY - DSLY);												//FRONT LEFT DESK LEG
    	
    	rectPrism(g,DX,DY,DW,DVO,DSX,DH,DC,d(DC),br(DC));											//TOP OF DESK
    	
    	rectPrism(g,KX,KY,KW,KH,KSX,KSY,KC,d(KC),b(KC));											//KEYBOARD
    	g.setStroke(new BasicStroke(1));
    	g.setColor(n(KLC));
    	for(int i = KX + KLGAPX; i < KX + KW; i += KLGAPX)
    	{
    		g.drawLine(i,KY, i+KSX,KY + KSY);
    	}
    	for(int j = KY + KLGAPY; j < KY + KSY; j += KLGAPY)
    	{
    		g.drawLine(KX + (int)(1/DSLOPE * (j-KY)),j,KX + KW + (int)(1/DSLOPE * (j-KY)),j);
    	}
    	
    	
    	
		rectPrism(g,PALX,PALY,PALW,PALH,PALSX,PALSY,SKINCOLOR,d(SKINCOLOR),b(SKINCOLOR));			//PERSON'S BACK ARM
    	
    	rectPrism(g,PTX,PTY,PTW,PTH,PTSX,PTSY,PC,d(PC),b(PC));										//PERSON'S TORSO
		
    	rectPrism(g,CX,CY,CLW,DVO,CSX,CH,CC,d2(CC),b(CC));											//REDRAWING EDGE OF CHAIR TO COVER UP PERSON PIXELS
		
		rectPrism(g,PHX,PHY,PHW,PHH,PHSX,PHSY,SKINCOLOR,d(SKINCOLOR),b(SKINCOLOR));					//PERSON'S HEAD
    	
    	//BACK OF CHAIR    	
    	rectPrism(g,CX,-CLH+CY,CLW,CLH,CLW,CSLY,CC,d2(CC),b(CC));									//CHAIR BACK HORIZONTAL TOP BAR
    	rectPrism(g,(CSX-CLW)/2+CX,-CLH+(CH-DVO)/2+CY,CLW,CLH,CLW,CSLY,CC,d2(CC),b(CC));			//CHAIR BACK VERTICAL LEFT BAR
    	rectPrism(g,CSX-CLW+CX,CH-CLH-DVO+CY,CLW,CLH,CLW,CSLY,CC,d2(CC),b(CC));						//CHAIR BACK VERTICAL MIDDLE BAR
		rectPrism(g,CX,CY-CLH,CLW,DVO,CSX,CH,CC,d2(CC),b(CC));										//CHAIR BACK VERTICAL RIGHT BAR
		//END OF BACK OF CHAIR
		
		rectPrism(g,MX + armStretch,MY,MW,MH,MSX,MSY,MC,d(MC),b(MC));											//MOUSE
    	rectPrism(g,PFX + armStretch,PFY+(isClicked? 1 : 0),PFW,PFH,PFSX,PFSY,SKINCOLOR,d(SKINCOLOR),b(SKINCOLOR));				//PERSON'S RIGHT FINGER
		rectPrism(g,MWX + armStretch,MWY,MWW,MWH,MWSX,MWSY,MWC,d(MWC),b(MWC));									//MOUSE WHEEL
    	
		rectPrism(g,PARX,PARY,PARW + armStretch,PARH,PARSX,PARSY,SKINCOLOR,d(SKINCOLOR),b(SKINCOLOR));			//PERSON'S RIGHT ARM
    	rectPrism(g,PARX,PTY+PTSY,PTW,(PARY-PTY-PTSY)+PARH,PARSX,PARSY,PC,d(PC),b(PC));							//PERSON'S RIGHT SHOULDER
    	
    	rectPrism(g,CMX,CMY,CMW,CMH,CMSX,CMSY,CMC,d(CMC),b(CMC));
    	g.setColor(CSC);
    	g.fillPolygon(new int[]{CMX + CMGX, CMX + CMSX - CMGX, CMX + CMSX - CMGX, CMX + CMGX}, new int[]{CMY + CMGY + (int)(DSLOPE * CMGX), CMY + CMSY + CMGY - (int)(DSLOPE * CMGX), CMY + CMSY + CMH - CMGY - (int)(DSLOPE * CMGX), CMY + CMH - CMGY + (int)(DSLOPE * CMGX)}, 4);
    	
    	if(highlighted)
    	{
    		g.setStroke(new BasicStroke(2));
    		g.setColor(Color.YELLOW);
    		g.drawPolygon(computer);
    	}
	}
	private void deskLeg(Graphics2D g, int x, int y)
	{
		rectPrism(g,x+DX,y+DY,DLW,DLH,DLW,DSLY,DC,d(DC),br(DC));
	}
	private void chairLeg(Graphics2D g, int x, int y)
	{
		rectPrism(g,x+CX,y+CY,CLW,CLH,CLW,CSLY,CC,d2(CC),b(CC));
	}
	
	private void rectPrism(Graphics2D g, int x, int y, int width, int height, int xslope, int yslope, Color topColor, Color frontColor, Color sideColor)
	{
		g.setColor(n(frontColor));
    	g.fillRect(x + xslope, y+yslope, width, height);
		g.setColor(n(sideColor));
		int[] x2 = {x, x + xslope, x + xslope, x};
    	int[] y2 = {y, y + yslope, y + height + yslope, y + height};
    	g.fillPolygon(x2,y2,4);
    	g.setColor(n(topColor));
    	x2 = new int[] {x,x+width,x+width+xslope,x+xslope};
    	y2 = new int[] {y,y,y+yslope,y+yslope};
    	g.fillPolygon(x2,y2,4);
	}
	
	private Color b(Color c)
	{
		return new Color((int)(c.getRed()+THREE_D_COLOR),(int)(c.getGreen()+THREE_D_COLOR),(int)(c.getBlue()+THREE_D_COLOR));
	}
	private Color br(Color c)
	{
		return new Color((int)(c.getRed()),(int)(c.getGreen()+THREE_D_COLOR),(int)(c.getBlue()+THREE_D_COLOR));
	}
	
	private Color d(Color c)
	{
		return new Color((int)(c.getRed()-THREE_D_COLOR),(int)(c.getGreen()-THREE_D_COLOR),(int)(c.getBlue()-THREE_D_COLOR));
	}
	private Color d2(Color c)
	{
		return new Color((int)(c.getRed()-THREE_D_COLOR),(int)(c.getGreen()-THREE_D_COLOR),(int)(c.getBlue()));
	}
	
	private Color db(Color c)
	{
		return new Color(c.getRed(),c.getGreen(),(int)(c.getBlue()+THREE_D_COLOR));
	}
	private Color bb(Color c)
	{
		return new Color(c.getRed(),c.getGreen(),(int)(c.getBlue()-THREE_D_COLOR));
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(tick%32 == 0)
		{
			switch(hour/2)
			{
				case 0:
				case 1:
				case 11:
					night = 3;
					break;
				case 2:
				case 10:
					night = 2;
					break;
				case 3:
				case 9:
					night = 1;
					break;
				default:
					night = 0;
			}
			hour = (++hour%24);
		}
		if(tick%2 == 0 && tick%40 < 30)
		{
			if(armStretch == 0 || armStretch == 15)
			{
				armForward = !armForward;
			}
			if(armForward)
			{
				armStretch++;
			}
			else
			{
				armStretch--;
			}
		}
		if(tick%40 == 30)
		{
			isClicked = true;
		}
		if(tick%40 == 34)
		{
			isClicked = false;
		}
		//For all ticks
		thetaDeg = (thetaDeg + (double)360/768);
		if(Math.abs(thetaDeg - 360) < 0.5)
		{
			thetaDeg = 0;
		}
		tick++;
		repaint();
	}
	private Color n(Color c)
	{
		for(int i = 0; i < night; i ++)
		{
			c = c.darker();
		}
		return c;
	}
}