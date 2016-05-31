import java.awt.*;
import java.applet.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.applet.*;
import java.util.*;
public class TetrisGame extends JPanel implements KeyListener, ActionListener{
	//Constants
	private static final int XOFFSET = 0; //offset of board from upper-left corner
	private static final int YOFFSET = 0; //offset of board from upper-left corner
	private static final int WIDTH = 10; //number of squares of the board horizontally
	private static final int HEIGHT = 20; //number of squares of the board vertically
	private static final int SIDELENGTH = 25; //number of pixels as the sidelength of a visual square
	private static final int BASETIMEDELAY = 1000; //number of ms that Tetris piece takes to drop initially
	private static final int XSCORE = (WIDTH + 2) * SIDELENGTH + 10; //x-coordinate to display the score
	private static final int YSCORE = 550; //y-coordinate to display the score
	private static final int SOFTDROPDELAY = 50; //how many ms it takes for a piece to drop while holding s
	//Arrays
	private int[][] pieceArray = new int[4][2]; //dimensions 4x2, 2 tells whether x or y coordinate, 4 tells which square of the piece you want
	private int[][] piecePreviewArray = new int[4][2]; //same as above but for the sake of piece preview option
	private int[][] board = new int[WIDTH + 9][HEIGHT + 2]; //array that holds the tile statuses for the actual game, the imaginary space above the game, and the space in the sidebar
		//-2 = top border (where pieces don't override visually but can be stored)
		//-1 = border
		//+0 = empty space
		//+1 = fallen piece (treated as solid but moved by row clear)
		//no +2- originally it was used for descent but it has become obsolete
		//+3 = currently falling piece
	private Color[][] boardColors = new Color[WIDTH + 9][HEIGHT + 2]; //array that holds the colors of each of the tiles
	//Variables to keep track of various things
	private int score; //score of the player
	private long timeDiff = 0;
	private char currentPiece;
	//type of piece, represented in character form
		//I = line of 4-shape
		//L = l-shape
		//l = backwards l-shape
		//T = t-shape
		//z = z-shape
		//s = backwards z-shape
		//S = square-shape
	private ArrayList<Character> randomGenPiece = new ArrayList<Character>();
	private char nextPiece;
	//type of piece for the preview and that will be chosen next
	private int totalRowsCleared;
	//how many rows the player has cleared so far- changes the speed of the game and the level which affects the score
	private int level;
	//calculated as totalRowsCleared/10 + 1, this helps calculate score
	private int rotationState;
	//Keeps track of which orientation the piece is drawn in; never used.
	private boolean gameOver = false;
	//Activates when the player fills up above the top- probably get removed for a function.
	private int keyX;
	//"Central" x-coordinate of the falling piece- calculations done with pieceArray are centered around here.
	private int keyY;
	//"Central" y-coordinate of the falling piece
	private javax.swing.Timer timerDrop;
	//Timer that activates every 1 second when a piece is falling to make it go down a square
	//If 's' is pressed, t
	private boolean spacePressed;
	//Decides whether there's a period after the piece hits the ground where you can move it
	private int speed = BASETIMEDELAY;
	//Number of ms between times that the piece descends
	private static final Color BORDER_COLOR = new Color(0,102,0);
	public TetrisGame()
	{
	}
	public void run()
	{
		//JPanel CopyPaste
    	char replay = 'n';
    	/*
    	 *What I need to do with window
    	 *
    	panel.setOpaque(true);
    	panel.setBackground(Color.BLACK); //doesn't work
    	window.add(panel);
    	window.setVisible(true);
    	
    	*/
    	//Default value of speed
		//Declaring arrays
		//Other declarations
		addKeyListener(this);
		setFocusable(true);
		//Main
		initBoard(); //fills out default values for board array
		repaint();
		do
		{
			choosePiece(); //gets a piece for piecePreview- necessary so that the second choosePiece() will put this into the currentPiece slot
    		choosePiece();
    		while(gameOver == false)
			{
	    		choosePiece(); //Moves nextPiece to currentPiece, generates currentPiece
				previewPiece();//fills out the piecePreviewArray with coordinates and updates board accordingly
    			createPiece(); //fills out the pieceArray with coordinates and updates board accordingly
    			repaint();
	   			if(checkDescent()) //is it blocked
	 			{
		  			timerDrop = new javax.swing.Timer(speed, this); //panel now has a timer that activates once every speed ms.
    				timerDrop.setInitialDelay(speed); //so it stays in initial position for speed ms.
    				timerDrop.start(); //begins running timer which descends if possible
		    	}
	   			while(checkDescent())
	   			{
		    		while(checkDescent())
		    		{
		    			//delays until can't descend anymore
		    		}
		    		if(spacePressed)
			    	{
			    		spacePressed = false; 
			    	}
		    		else
		    		{
			    		long time = System.currentTimeMillis();
			    		while(System.currentTimeMillis() < time + speed)
			    		{
		    				//Waits an extra second after a non-hard drop to allow for final movements
		    			}
		    		}
	   			}
		    	timerDrop.stop(); //stops the descending timer so it can restart w/the next piece and an updated speed if necessary
    		    solidifyPiece(); 
    			checkRows(); //are any full? if so, clears them and updates the score.
   	 			increaseSpeed(); //recalculates speed based on totalRowsCleared
	    		level = totalRowsCleared/10 + 1; //updates level
	    		repaint();
 	   		}
 	   		//Here goes 
		} while ((replay == 'y') || (replay == 'Y'));
		System.out.println("GG");		
    	//SetConsoleTextAttribute( GetStdHandle( STD_OUTPUT_HANDLE ), 0x0F );
    	repaint();
		
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		if(gameOver == true)
		{
			g.setColor(Color.GREEN);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 60));
			g.drawString("Game Over\nPress Escape to Leave");
		}
		else
		{
			g.setColor(Color.BLACK);
			g.fillRect(0,0,this.getWidth(),this.getHeight());
			//Draws physical board
			for (int x = 0; x < WIDTH + 9; x ++)
			{
   			 	for (int y = 0; y < HEIGHT + 2; y ++)
 			   	{
    				drawSquare(g, x, y, boardColors[x][y]);	
	    		}
		    }
	    	drawSquare(g, 0, 0 , BORDER_COLOR);
		}
		g.setFont(Font.getDefault());
		g.drawString("Score: " + Integer.toString(score),XSCORE,YSCORE);
	}
	
	public void actionPerformed(ActionEvent evt) //every 1 second while the piece is dropping
    {
		if (checkDescent())
    	{ 
       		descend();
       		repaint();
    	}
    	timeDiff = System.currentTimeMillis();
    }
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//SETUP AND PIECE CREATION
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void initBoard()
	{
		for (int i = 0; i < (WIDTH+2); i++)
		{
    		convertSquare(i,0,-2,BORDER_COLOR);
    		convertSquare(i,HEIGHT+1,-1,BORDER_COLOR);
    	}
    	for (int i = 0; i < (HEIGHT+2); i++)  
    	{
    		convertSquare(0,i,-1,BORDER_COLOR);
    		convertSquare(WIDTH+1,i,-1,BORDER_COLOR);
    	}
    	for(int x = 1; x <= WIDTH; x++)
    	{
    		for(int y = 1; y<=HEIGHT; y++)
	    	{
	    		convertSquare(x,y,0,Color.BLACK);
    		}
    	}
    	for(int x = WIDTH + 2; x < WIDTH + 9; x++)
    	{
    		for(int y=0;y <= HEIGHT + 1; y++)
	   		{
	    		convertSquare(x,y,0,Color.BLACK);
    		}
    	}
	}
	
	public char generateRandomPiece()
	{
		/*
		int numPieceType = (1 + (int)(Math.random()*7));
		char randomPiece;
		switch (numPieceType)
		{
			case 1:
				randomPiece = 'S'; //Square piece
				break;
			case 2:
				randomPiece = 'L'; //Normal “L”
				break;
			case 3:
				randomPiece = 'l'; //Backwards “L”
				break;
			case 4:
				randomPiece = 'z'; //Left-up-left zigzag
				break;
			case 5:
				randomPiece = 's'; //Right-up-right zigzag
				break;
			case 6:
				randomPiece = 'I'; //4-long straight piece
				break;
			case 7:
				randomPiece = 'T'; //”T”-shaped piece.
				break;
			default:
				System.out.println("What have you done?");
				randomPiece = 'Q';
		}
		return randomPiece;
		*/
		if(randomGenPiece.size() == 0)
		{
			randomGenPiece.add('S');
			randomGenPiece.add('L');
			randomGenPiece.add('l');
			randomGenPiece.add('z');
			randomGenPiece.add('s');
			randomGenPiece.add('I');
			randomGenPiece.add('T');
		}
		int index = (int)(Math.random() * randomGenPiece.size());
		char randomPiece = randomGenPiece.get(index);
		randomGenPiece.remove(index);
		return randomPiece;
		
		
	}
	public void choosePiece()
	{
		currentPiece = nextPiece;
		nextPiece = generateRandomPiece();
	}
	public void previewPiece()
	{
		int nextKeyX = WIDTH + 5;
		int nextKeyY = 3;
		convertSquare(piecePreviewArray[0][0],piecePreviewArray[0][1],0,Color.BLACK);
		convertSquare(piecePreviewArray[1][0],piecePreviewArray[1][1],0,Color.BLACK);
		convertSquare(piecePreviewArray[2][0],piecePreviewArray[2][1],0,Color.BLACK);
		convertSquare(piecePreviewArray[3][0],piecePreviewArray[3][1],0,Color.BLACK);
		switch(nextPiece)
		{
			case 'S': //square
				piecePreviewArray[0][0] = nextKeyX;
				piecePreviewArray[0][1] = nextKeyY;
				piecePreviewArray[1][0] = nextKeyX;
				piecePreviewArray[1][1] = nextKeyY + 1;
				piecePreviewArray[2][0] = nextKeyX - 1;
				piecePreviewArray[2][1] = nextKeyY;
				piecePreviewArray[3][0] = nextKeyX - 1;
				piecePreviewArray[3][1] = nextKeyY + 1;
				break;
			case 'L': //L
				piecePreviewArray[0][0] = nextKeyX;
				piecePreviewArray[0][1] = nextKeyY;
				piecePreviewArray[1][0] = nextKeyX - 1;
				piecePreviewArray[1][1] = nextKeyY;
				piecePreviewArray[2][0] = nextKeyX + 1;
				piecePreviewArray[2][1] = nextKeyY;
				piecePreviewArray[3][0] = nextKeyX + 1;
				piecePreviewArray[3][1] = nextKeyY + 1;
				break;
			case 'l': // backwards L
				piecePreviewArray[0][0] = nextKeyX;
				piecePreviewArray[0][1] = nextKeyY;
				piecePreviewArray[1][0] = nextKeyX - 1;
				piecePreviewArray[1][1] = nextKeyY;
				piecePreviewArray[2][0] = nextKeyX + 1;
				piecePreviewArray[2][1] = nextKeyY;
				piecePreviewArray[3][0] = nextKeyX - 1;
				piecePreviewArray[3][1] = nextKeyY + 1;
				break;
			case 'z': // topleft zigzag
				piecePreviewArray[0][0] = nextKeyX;
				piecePreviewArray[0][1] = nextKeyY;
				piecePreviewArray[1][0] = nextKeyX;
				piecePreviewArray[1][1] = nextKeyY + 1;
				piecePreviewArray[2][0] = nextKeyX - 1;
				piecePreviewArray[2][1] = nextKeyY;
				piecePreviewArray[3][0] = nextKeyX + 1;
				piecePreviewArray[3][1] = nextKeyY + 1;
				break;
			case 's': // topright zigzag
				piecePreviewArray[0][0] = nextKeyX;
				piecePreviewArray[0][1] = nextKeyY;
				piecePreviewArray[1][0] = nextKeyX;
				piecePreviewArray[1][1] = nextKeyY + 1;
				piecePreviewArray[2][0] = nextKeyX + 1;
				piecePreviewArray[2][1] = nextKeyY;
				piecePreviewArray[3][0] = nextKeyX - 1;
				piecePreviewArray[3][1] = nextKeyY + 1;
				break;
			case 'I': //four vertical
				piecePreviewArray[0][0] = nextKeyX;
				piecePreviewArray[0][1] = nextKeyY + 2;
				piecePreviewArray[1][0] = nextKeyX;
				piecePreviewArray[1][1] = nextKeyY;
				piecePreviewArray[2][0] = nextKeyX;
				piecePreviewArray[2][1] = nextKeyY + 1;
				piecePreviewArray[3][0] = nextKeyX;
				piecePreviewArray[3][1] = nextKeyY - 1;
				break;
			case 'T': // T
				piecePreviewArray[0][0] = nextKeyX;
				piecePreviewArray[0][1] = nextKeyY;
				piecePreviewArray[1][0] = nextKeyX;
				piecePreviewArray[1][1] = nextKeyY + 1;
				piecePreviewArray[2][0] = nextKeyX + 1;
				piecePreviewArray[2][1] = nextKeyY;
				piecePreviewArray[3][0] = nextKeyX-1;
				piecePreviewArray[3][1] = nextKeyY;
				break;
			default:
				System.out.println("wat");
		}
		
		convertSquare(piecePreviewArray[0][0],piecePreviewArray[0][1],3,typeToColor(nextPiece));
		convertSquare(piecePreviewArray[1][0],piecePreviewArray[1][1],3,typeToColor(nextPiece));
		convertSquare(piecePreviewArray[2][0],piecePreviewArray[2][1],3,typeToColor(nextPiece));
		convertSquare(piecePreviewArray[3][0],piecePreviewArray[3][1],3,typeToColor(nextPiece));
	}
	
	public void createPiece()
	{
		keyX = 5;
		keyY = 2;
		rotationState = 0;
		switch(currentPiece)
		{
			case 'S': //square
				pieceArray[0][0] = keyX;
				pieceArray[0][1] = keyY;
				pieceArray[1][0] = keyX;
				pieceArray[1][1] = keyY + 1;
				pieceArray[2][0] = keyX + 1;
				pieceArray[2][1] = keyY;
				pieceArray[3][0] = keyX + 1;
				pieceArray[3][1] = keyY + 1;
				break;
			case 'L': //L
				pieceArray[0][0] = keyX;
				pieceArray[0][1] = keyY;
				pieceArray[1][0] = keyX - 1;
				pieceArray[1][1] = keyY;
				pieceArray[2][0] = keyX + 1;
				pieceArray[2][1] = keyY;
				pieceArray[3][0] = keyX + 1;
				pieceArray[3][1] = keyY + 1;
				break;
			case 'l': // backwards L
				pieceArray[0][0] = keyX;
				pieceArray[0][1] = keyY;
				pieceArray[1][0] = keyX - 1;
				pieceArray[1][1] = keyY;
				pieceArray[2][0] = keyX + 1;
				pieceArray[2][1] = keyY;
				pieceArray[3][0] = keyX - 1;
				pieceArray[3][1] = keyY + 1;
				break;
			case 'z': // topleft zigzag
				pieceArray[0][0] = keyX;
				pieceArray[0][1] = keyY;
				pieceArray[1][0] = keyX;
				pieceArray[1][1] = keyY + 1;
				pieceArray[2][0] = keyX - 1;
				pieceArray[2][1] = keyY;
				pieceArray[3][0] = keyX + 1;
				pieceArray[3][1] = keyY + 1;
				break;
			case 's': // topright zigzag
				pieceArray[0][0] = keyX;
				pieceArray[0][1] = keyY;
				pieceArray[1][0] = keyX;
				pieceArray[1][1] = keyY + 1;
				pieceArray[2][0] = keyX + 1;
				pieceArray[2][1] = keyY;
				pieceArray[3][0] = keyX - 1;
				pieceArray[3][1] = keyY + 1;
				break;
			case 'I': //four vertical
				pieceArray[0][0] = keyX;
				pieceArray[0][1] = keyY;
				pieceArray[1][0] = keyX - 1;
				pieceArray[1][1] = keyY;
				pieceArray[2][0] = keyX + 1;
				pieceArray[2][1] = keyY;
				pieceArray[3][0] = keyX + 2;
				pieceArray[3][1] = keyY;
				break;
			case 'T': // T
				pieceArray[0][0] = keyX;
				pieceArray[0][1] = keyY;
				pieceArray[1][0] = keyX;
				pieceArray[1][1] = keyY + 1;
				pieceArray[2][0] = keyX + 1;
				pieceArray[2][1] = keyY;
				pieceArray[3][0] = keyX-1;
				pieceArray[3][1] = keyY;
				break;
			default:
				System.out.println("WAT");
		}    
		if(!checkDescent())
		{
			gameOver = true;
		}
		else
		{
			convertSquare(pieceArray[0][0],pieceArray[0][1],3,typeToColor(currentPiece));
			convertSquare(pieceArray[1][0],pieceArray[1][1],3,typeToColor(currentPiece));
			convertSquare(pieceArray[2][0],pieceArray[2][1],3,typeToColor(currentPiece));
			convertSquare(pieceArray[3][0],pieceArray[3][1],3,typeToColor(currentPiece));
		}
	}
	
	public void checkRows()
	{
		boolean rowFull;
		int rowsClearedAtOnce = 0;
		for(int i = HEIGHT; i >= 1; i--)
		{
			rowFull = true;
			for(int j = 1; j <= WIDTH; j++)
			{
				if(board[j][i] == 0)
				{
					rowFull = false;
				}
			}
			if (rowFull)
			{
				clearRow(i);
				i++;
				totalRowsCleared ++;
				rowsClearedAtOnce ++;
			}
		}
		int scoreMultiplier = 0;
		switch (rowsClearedAtOnce)
		{
			case 0:
				scoreMultiplier = 0;
				break;
			case 1:
				scoreMultiplier = 40;
				break;
			case 2:
				scoreMultiplier = 100;
				break;
			case 3:
				scoreMultiplier = 300;
				break;
			case 4:
				scoreMultiplier = 1200;
				break;
			default:
				System.out.println("You are literally Jesus.");
		}
		addScore(scoreMultiplier * level);
	}
	public void clearRow(int row)
	{
		for(int j = row; j > 1; j --)
		{
			for(int i = 1; i <= WIDTH; i ++)
			{
				{
					convertSquare(i,j,board[i][j-1],boardColors[i][j-1]);
				}
			}
		}
		for(int i = 1; i <= WIDTH; i ++)
		{
			convertSquare(i,1,0,Color.BLACK);
		}
	}

	public void increaseSpeed()
	{
		speed = BASETIMEDELAY - (8 * totalRowsCleared);
		if(speed <= 100)
		{
			speed = 100;
		}
	}
	
	public void solidifyPiece()
	{
		for(int i = 0; i <= 3; i++)
		{
			convertSquare(pieceArray[i][0],pieceArray[i][1],1,boardColors[pieceArray[i][0]][pieceArray[i][1]]);
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//INPUT/MOVEMENT FUNCTIONS
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	public void rotate()
	{
            int[][] rotateFails = new int[4][2];
            int[][] pieceArrayBackup = new int[4][2];
            boolean thatDidntWork = false;
            for(int i = 0; i <= 3; i ++)
            {
                rotateFails[i][0] = pieceArray[i][0];
                rotateFails[i][1] = pieceArray[i][1];
                pieceArrayBackup[i][0] = pieceArray[i][0];
                pieceArrayBackup[i][1] = pieceArray[i][1];  
            }
            switch(currentPiece)
            {
                //no rotation
                case 'S':
                    break;
                //quad-states
                case 'L':
                case 'l':
                case 'T':
                    for(int i = 0; i <= 3; i ++)
                    {
                        if((pieceArrayBackup[i][0] == keyX - 1)&&(pieceArrayBackup[i][1] == keyY - 1))
                        {
                            pieceArrayBackup[i][0] += 2;
                            if((board[pieceArrayBackup[i][0]][pieceArrayBackup[i][1]] == 1) || (board[pieceArrayBackup[i][0]][pieceArrayBackup[i][1]] == -1))
                                thatDidntWork = true;
                        }
                        else if((pieceArrayBackup[i][0] == keyX - 1)&&(pieceArrayBackup[i][1] == keyY))
                        {
                            pieceArrayBackup[i][0] ++;
                            pieceArrayBackup[i][1] --;
                            if((board[pieceArrayBackup[i][0]][pieceArrayBackup[i][1]] == 1) || (board[pieceArrayBackup[i][0]][pieceArrayBackup[i][1]] == -1))
                                thatDidntWork = true;
                        }
                        else if((pieceArrayBackup[i][0] == keyX - 1)&&(pieceArrayBackup[i][1] == keyY + 1))
                        {
                            pieceArrayBackup[i][1] -= 2;
                            if((board[pieceArrayBackup[i][0]][pieceArrayBackup[i][1]] == 1) || (board[pieceArrayBackup[i][0]][pieceArrayBackup[i][1]] == -1))
                                thatDidntWork = true;
                        }
                        else if((pieceArrayBackup[i][0] == keyX)&&(pieceArrayBackup[i][1] == keyY + 1))
                        {
                            pieceArrayBackup[i][0] --;
                            pieceArrayBackup[i][1] --;
                            if((board[pieceArrayBackup[i][0]][pieceArrayBackup[i][1]] == 1) || (board[pieceArrayBackup[i][0]][pieceArrayBackup[i][1]] == -1))
                                thatDidntWork = true;
                        }
                        else if((pieceArrayBackup[i][0] == keyX + 1)&&(pieceArrayBackup[i][1] == keyY + 1))
                        {
                            pieceArrayBackup[i][0] -= 2;
                            if((board[pieceArrayBackup[i][0]][pieceArrayBackup[i][1]]== 1) || (board[pieceArrayBackup[i][0]][pieceArrayBackup[i][1]] == -1))
                                thatDidntWork = true;
                        }
                        else if((pieceArrayBackup[i][0] == keyX + 1)&&(pieceArrayBackup[i][1] == keyY))
                        {
                            pieceArrayBackup[i][0] --;
                            pieceArrayBackup[i][1] ++;
                            if((board[pieceArrayBackup[i][0]][pieceArrayBackup[i][1]] == 1) || (board[pieceArrayBackup[i][0]][pieceArrayBackup[i][1]] == -1))
                                thatDidntWork = true;
                        }
                        else if((pieceArrayBackup[i][0] == keyX + 1)&&(pieceArrayBackup[i][1] == keyY - 1))
                        {
                            pieceArrayBackup[i][1] +=2;
                            if((board[pieceArrayBackup[i][0]][pieceArrayBackup[i][1]] == 1) || (board[pieceArrayBackup[i][0]][pieceArrayBackup[i][1]] == -1))
                                thatDidntWork = true;
                        }
                        else if((pieceArrayBackup[i][0] == keyX)&&(pieceArrayBackup[i][1] == keyY - 1))
                        {
                            pieceArrayBackup[i][0] ++;
                            pieceArrayBackup[i][1] ++;
                            if((board[pieceArrayBackup[i][0]][pieceArrayBackup[i][1]] == 1) || (board[pieceArrayBackup[i][0]][pieceArrayBackup[i][1]] == -1))
                                thatDidntWork = true;
                        }
                        else //center pieceArrayBackup
                        {
                            
                        }
                        if(thatDidntWork == false)
                            rotationState = ++rotationState % 4;
                    }
                    break;
                        
                //dual-states
                case 'z':
                case 's':
                    if(rotationState == 0)
                    {
                        pieceArrayBackup[1][0] ++;
                        pieceArrayBackup[1][1] --;
                        if(currentPiece == 'z')
                        {
                            pieceArrayBackup[2][0] ++;
                            pieceArrayBackup[2][1] ++;
                            pieceArrayBackup[3][1] -= 2;
                        }
                        else //currentPiece is s
                        {
                            pieceArrayBackup[2][0] --;
                            pieceArrayBackup[2][1] --;
                            pieceArrayBackup[3][0] += 2;
                        }
                    }
                    else if(rotationState == 1)
                    {
                        pieceArrayBackup[1][0] --;
                        pieceArrayBackup[1][1] ++;
                        if(currentPiece == 'z')
                        {
                            pieceArrayBackup[2][0] --;
                            pieceArrayBackup[2][1] --;
                            pieceArrayBackup[3][1] += 2;
                        }
                        else //currentPiece is s
                        {
                            pieceArrayBackup[2][0] ++;
                            pieceArrayBackup[2][1] ++;
                            pieceArrayBackup[3][0] -= 2;
                        }
                    }
                    for(int i = 0; i <= 3; i ++)
                    {    
                        if((board[pieceArrayBackup[i][0]][pieceArrayBackup[i][1]] == 1) || (board[pieceArrayBackup[i][0]][pieceArrayBackup[i][1]] == -1))
                            thatDidntWork = true;
                    }
                    if(thatDidntWork == false)
                    {    
                        rotationState ++;
                        rotationState = rotationState % 2;
                    }
                    break;
                case 'I':
                    if(rotationState == 0)
                    {
                        pieceArrayBackup[1][0] ++;
                        pieceArrayBackup[1][1] --;
                        pieceArrayBackup[2][0] --;
                        pieceArrayBackup[2][1] ++;
                        pieceArrayBackup[3][0] -= 2;
                        pieceArrayBackup[3][1] += 2;
                    }
                    else if(rotationState == 1)
                    {
                        pieceArrayBackup[1][0] --;
                        pieceArrayBackup[1][1] ++;
                        pieceArrayBackup[2][0] ++;
                        pieceArrayBackup[2][1] --;
                        pieceArrayBackup[3][0] += 2;
                        pieceArrayBackup[3][1] -= 2;
                    }
                    for(int i = 0; i <= 3; i ++)
                    {    
                        if((board[pieceArrayBackup[i][0]][pieceArrayBackup[i][1]] == 1) || (board[pieceArrayBackup[i][0]][pieceArrayBackup[i][1]] == -1))
                            thatDidntWork = true;
                    }
                    if(thatDidntWork == false)
                    {
                        rotationState ++;
                        rotationState = rotationState % 2;
                    }
                    break;
                default:
                    System.out.println("what");
            }
            for(int i = 0; i <= 3; i ++)
            {
                if(thatDidntWork)
                {
                }
                else
                {
                    convertSquare(pieceArray[i][0],pieceArray[i][1],0,Color.BLACK);
                }
            }
            if(thatDidntWork == false)
            {
                for(int i = 0; i <= 3; i ++)
                {
                	pieceArray[i][0] = pieceArrayBackup[i][0];
                	pieceArray[i][1] = pieceArrayBackup[i][1];
                    convertSquare(pieceArray[i][0],pieceArray[i][1],3,typeToColor(currentPiece));
                }
            }
            //Unsure about placement of braces here- careful.
                
            
        }
	public void addScore(int points)
	{
		score += points;
	}
	public void updateLevel()
	{
		level = (int)(((double)totalRowsCleared)/10 + 1);
	}
	public void keyPressed(KeyEvent e)
	{
		inputKey(e.getKeyCode());
	}
	public void inputKey(int key)
	{
		switch(key)
		{
			case KeyEvent.VK_W:
				rotate();
				break;
			case KeyEvent.VK_A:
				if(checkLateralMovement(-1))
					move(-1);
				break;
			case KeyEvent.VK_D:
				if(checkLateralMovement(1))
					move(1);
				break;
			case KeyEvent.VK_SPACE:
				spacePressed = true;
				while(checkDescent())
					descend();
				break;
			case KeyEvent.VK_S:
				timerDrop.stop();
				timerDrop = new javax.swing.Timer(SOFTDROPDELAY, this);
    			timerDrop.setInitialDelay(SOFTDROPDELAY);
    			timerDrop.start();
				break;
		}
		repaint();
	}
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_S)
		{
			timerDrop.stop();
			timerDrop = new javax.swing.Timer(speed, this);
    		timerDrop.setInitialDelay(SOFTDROPDELAY);
    		timerDrop.start();
		}
	}
	public void keyTyped(KeyEvent e)
	{
		
	}
	public boolean checkDescent()
	{
		for(int i = 0; i <= 3; i ++)
		{
			if((board[pieceArray[i][0]][pieceArray[i][1]+1] == 1 || board[pieceArray[i][0]][pieceArray[i][1]+1] == -1))
			{
				return false;
			}
		}
		return true;
	}
	public boolean checkLateralMovement(int direction)
	{
		for(int i = 0; i <= 3; i ++)
		{
			if((board[pieceArray[i][0] + direction][pieceArray[i][1]] == 1) || (board[pieceArray[i][0] + direction][pieceArray[i][1]] == -1))
			{
				return false;
			}
		}
		return true;
	}
	public void move(int direction) //-1 for left, 1 for right
	{
		for(int i = 0; i <= 3; i ++)
		{
			convertSquare(pieceArray[i][0],pieceArray[i][1],0,Color.BLACK);
		}
		for(int i = 0; i <= 3; i ++)
		{
			pieceArray[i][0] += direction;
			convertSquare(pieceArray[i][0],pieceArray[i][1],3,typeToColor(currentPiece));
		}
		keyX += direction;
	}
	public void descend()
	{
		for(int i = 0; i <= 3; i ++)
		{
			convertSquare(pieceArray[i][0],pieceArray[i][1],0,Color.BLACK);
		}                        
		for(int i = 0; i <= 3; i ++)
		{                        
			pieceArray[i][1] += 1;
			convertSquare(pieceArray[i][0],pieceArray[i][1],3,typeToColor(currentPiece));
		}
		keyY ++;
	}
	public Color typeToColor(char type)
	{
		Color tileColor = new Color(0,0,0);
		switch(type)
		{
			case 'S':
				tileColor = Color.RED;
				break;
			case 'L':
				tileColor = Color.BLUE;
				break;
			case 'l':
				tileColor = Color.MAGENTA;
				break;
			case 'z':
				tileColor = new Color(0,255,0);
				break;
			case 's':
				tileColor = Color.CYAN;
				break;
			case 'I':
				tileColor = Color.ORANGE;
				break;
			case 'T':
				tileColor = Color.YELLOW;
				break;
			default:
				tileColor = Color.WHITE;
		}
		return tileColor;
	}
	
	public void convertSquare(int arrayX, int arrayY, int newType, Color newColor)
	{
		board[arrayX][arrayY] = newType;
		boardColors[arrayX][arrayY] = newColor;
	}
	public void drawSquare(Graphics g, int arrayX, int arrayY, Color squareColor)
	{
		g.setColor(squareColor);
		g.fillRect(XOFFSET + arrayX * SIDELENGTH, YOFFSET + arrayY * SIDELENGTH, SIDELENGTH, SIDELENGTH);
	}
}