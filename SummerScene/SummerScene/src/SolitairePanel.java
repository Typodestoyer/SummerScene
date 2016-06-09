/**
 * @(#)SolitaireGame.java
 *
 *
 * @author 
 * @version 1.00 2016/6/6
 */
    	
import java.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

public class SolitairePanel extends Application implements KeyListener
{
	private static final int DECK_X = 20;
	private static final int DECK_Y = 20;
	private static final int PILES_X = 20;
	private static final int PILES_Y = 200;
	private static final int PILES_X_GAP = 95;
	private static final int PILES_Y_GAP = 30;
	private static final int TARGETS_X = 300;
	private static final int TARGETS_X_GAP = 100;
	private static final int DISCARD_X = 125;
	private static final int DISCARD_X_GAP = 20;
	private static final int CARD_WIDTH = 73;
	private static final int CARD_HEIGHT = 97;
	private static final int BOARD_WIDTH = 850;
	private static final int BOARD_HEIGHT = 600;
		
	private static final ImageIcon CARD_BACK = new ImageIcon("cards/back.GIF");
	private static final ImageIcon EMPTY_DECK = new ImageIcon("cards/emptyDeck.GIF");
	
	//Card of deck- either back of card or 
	private JLabel deckDisplay; //either displays cardBack or dashed outline/nothing
	//Array of the three displayed cards
	private ArrayList<JLabel> discardDisplay;
	//Array of ArrayList of displays- arraylist of displays is each pile, array is 7 piles
	private ArrayList<ArrayList<JLabel>> pilesDisplay;
	//Array of displays- top cards of each target pile
	private ArrayList<JLabel> targetsDisplay;
	private ArrayList<MyMouseListener> discardClicks;
	private ArrayList<ArrayList<MyMouseListener>> pilesClicks;
	private JLabel winMessage;
	private SolitaireGame game;
	
	public SolitairePanel()
	{
		game = new SolitaireGame();
		deckDisplay = new JLabel(CARD_BACK);
		discardDisplay = new ArrayList<JLabel>();
		pilesDisplay = new ArrayList<ArrayList<JLabel>>();
		targetsDisplay = new ArrayList<JLabel>();
		discardClicks = new ArrayList<MyMouseListener>();
		pilesClicks = new ArrayList<ArrayList<MyMouseListener>>();
		winMessage = new JLabel("You won!");
		winMessage.setBounds(BOARD_WIDTH/2 - 100, BOARD_WIDTH/2 - 100, 200, 200);
		initDisplay();
		repaint();
	}
	
	public Scene getScene(){return Scene.SOLITAIRE;}
	
	public void initDisplay()
	{
		this.setBackground(Color.GREEN);
		//Setup deck display JLabel
		deckDisplay.setBounds(DECK_X, DECK_Y, CARD_WIDTH, CARD_HEIGHT);
		deckDisplay.addMouseListener(new MyMouseListener());
		add(deckDisplay);
		for(int i = 0; i < game.NUM_OF_CARDS_DEALT; i ++)
		{
			discardDisplay.add(new JLabel());
			discardClicks.add(new MyMouseListener(i == game.NUM_OF_CARDS_DEALT - 1));
			discardDisplay.get(i).addMouseListener(discardClicks.get(i));
		}
		
		for(int i = 0; i < game.NUM_OF_CARDS_DEALT; i ++)
		{
			if(i != game.NUM_OF_CARDS_DEALT - 1)
			{
				discardDisplay.get(i).setBounds(DISCARD_X + (DISCARD_X_GAP * i),DECK_Y, DISCARD_X_GAP, CARD_HEIGHT);
			}
			else
			{
				discardDisplay.get(i).setBounds(DISCARD_X + (DISCARD_X_GAP * i),DECK_Y, CARD_WIDTH, CARD_HEIGHT);
			}
			add(discardDisplay.get(i));
		}
		
		for(int i = 0; i < 7; i ++)
		{
			AlternatingCardPile acp = game.getPiles()[i];
			pilesDisplay.add(new ArrayList<JLabel>());
			pilesClicks.add(new ArrayList<MyMouseListener>());
			for(int j = 0; j < acp.size(); j ++)
			{
				pilesDisplay.get(i).add(new JLabel());
				pilesClicks.get(i).add(new MyMouseListener());
				pilesDisplay.get(i).get(j).setVerticalAlignment(JLabel.TOP);
				if(j != acp.size() - 1)
				{
					pilesDisplay.get(i).get(j).setBounds(PILES_X + (PILES_X_GAP * i), PILES_Y + (PILES_Y_GAP * j), CARD_WIDTH, PILES_Y_GAP);
				}
				else
				{
					pilesDisplay.get(i).get(j).setBounds(PILES_X + (PILES_X_GAP * i), PILES_Y + (PILES_Y_GAP * j), CARD_WIDTH, CARD_HEIGHT);
				}
				pilesDisplay.get(i).get(j).addMouseListener(pilesClicks.get(i).get(j));
				add(pilesDisplay.get(i).get(j));

			}
		}
		
		for(int i = 0; i < 4; i ++)
		{
			targetsDisplay.add(new JLabel());
			targetsDisplay.get(i).setIcon(EMPTY_DECK);
			targetsDisplay.get(i).setBounds(TARGETS_X + (TARGETS_X_GAP * i), DECK_Y, CARD_WIDTH, CARD_HEIGHT);
			targetsDisplay.get(i).addMouseListener(new MyMouseListener());
			add(targetsDisplay.get(i));
		}
	}
	
	protected void paintComponent(Graphics g)
	{
		if(game.getDeck().size() != 0)
		{
			deckDisplay.setIcon(CARD_BACK);
		}
		else
		{
			deckDisplay.setIcon(EMPTY_DECK);
		}
		deckDisplay.setVisible(true);
		
		for(int i = 0; i < game.NUM_OF_CARDS_DEALT; i ++)
		{
			int cardOffsetNumber = (game.NUM_OF_CARDS_DEALT - game.getDiscardPile().size());
			if(cardOffsetNumber > 0)
			{
				if (i > game.getDiscardPile().size() - 1)
					discardDisplay.get(i).setVisible(false);
				else
				{
					discardDisplay.get(i).setIcon(game.getDiscardPile().get(game.getDiscardPile().size() - game.NUM_OF_CARDS_DEALT + i + (cardOffsetNumber > 0? cardOffsetNumber : 0)).getImage());
					discardDisplay.get(i).setVisible(true);
				}
			}
			else
			{
				discardDisplay.get(i).setIcon(game.getDiscardPile().get(game.getDiscardPile().size() - game.NUM_OF_CARDS_DEALT + i + (cardOffsetNumber > 0? cardOffsetNumber : 0)).getImage());
				discardDisplay.get(i).setVisible(true);
			}
			if(i == game.getDiscardPile().size() - 1 || (i == discardDisplay.size() - 1 && game.getDiscardPile().size() > 3))
			{
				discardDisplay.get(i).setBounds(DISCARD_X + (DISCARD_X_GAP * i),DECK_Y, CARD_WIDTH, CARD_HEIGHT);
				discardClicks.get(i).activate();
			}
			else
			{
				discardDisplay.get(i).setBounds(DISCARD_X + (DISCARD_X_GAP * i),DECK_Y, DISCARD_X_GAP, CARD_HEIGHT);
				discardClicks.get(i).deactivate();
			}
		}
		
		for(int i = 0; i < 7; i ++)
		{
			AlternatingCardPile acp = game.getPiles()[i];
			{
				for(int j = 0; j < acp.size() || j < pilesDisplay.get(i).size(); j ++)
				{
					if(j >= acp.size())
					{
						if(j == 0)
						{
							pilesDisplay.get(i).get(0).setIcon(EMPTY_DECK);
							pilesDisplay.get(i).get(0).setBounds(PILES_X + (PILES_X_GAP * i), PILES_Y + (PILES_Y_GAP * j), CARD_WIDTH, CARD_HEIGHT);
						}
						else
						{
							this.remove(pilesDisplay.get(i).get(j));
							pilesDisplay.get(i).remove(j);
							pilesClicks.get(i).remove(j);
							j --;
						}
					}
					else
					{
						if(j >= pilesDisplay.get(i).size())
						{
							if(j == 0)
							{
								pilesClicks.get(i).set(0, new MyMouseListener());
								pilesDisplay.get(i).set(0, new JLabel());
							}
							else
							{
								pilesClicks.get(i).add(new MyMouseListener());
								pilesDisplay.get(i).add(new JLabel());
								pilesDisplay.get(i).get(j).addMouseListener(pilesClicks.get(i).get(j));
							}
							pilesDisplay.get(i).get(j).setVerticalAlignment(JLabel.TOP);
							add(pilesDisplay.get(i).get(j));
						}
						if(j < acp.size() - acp.getNumOfVisibleCards())
						{
							pilesDisplay.get(i).get(j).setIcon(CARD_BACK);
							pilesClicks.get(i).get(j).deactivate();
						}
						else
						{
							pilesDisplay.get(i).get(j).setIcon(game.getPiles()[i].get(j).getImage());
							pilesClicks.get(i).get(j).activate();
						}
						if((acp.size()  - 1)* PILES_Y_GAP + PILES_Y + CARD_HEIGHT >= BOARD_HEIGHT - 100)
						{
							int newGap = (BOARD_HEIGHT - 100 - PILES_Y)/(acp.size() + CARD_HEIGHT / PILES_Y_GAP);
							if(j != acp.size() - 1)
							{
								pilesDisplay.get(i).get(j).setBounds(PILES_X + (PILES_X_GAP * i), PILES_Y + (newGap * j), CARD_WIDTH, newGap);
							}
							else
							{
								pilesDisplay.get(i).get(j).setBounds(PILES_X + (PILES_X_GAP * i), PILES_Y + (newGap * j), CARD_WIDTH, CARD_HEIGHT);
							}
						}
						else
						{
							if(j != acp.size() - 1)
							{
								pilesDisplay.get(i).get(j).setBounds(PILES_X + (PILES_X_GAP * i), PILES_Y + (PILES_Y_GAP * j), CARD_WIDTH, PILES_Y_GAP);
							}
							else
							{
								pilesDisplay.get(i).get(j).setBounds(PILES_X + (PILES_X_GAP * i), PILES_Y + (PILES_Y_GAP * j), CARD_WIDTH, CARD_HEIGHT);
							}
						}
					}		
				}
			}
		}
		
		for(int i = 0; i < 4; i ++)
		{
			TargetCardPile tcp = game.getTargets()[i];
			if (tcp.size() == 0)
			{
				targetsDisplay.get(i).setIcon(EMPTY_DECK);
			}
			else
			{
				targetsDisplay.get(i).setIcon(tcp.get(tcp.size() - 1).getImage());
			}
			targetsDisplay.get(i).setVisible(true);
		}
		
		if(hasWon())
		{
			winMessage.setVisible(true);
		}
		else
		{
			winMessage.setVisible(false);
		}
		super.paintComponent(g);
	}
	
	public boolean hasWon()
	{
		return game.hasWon();
	}
	
	public SolitaireGame getGame()
	{
		return game;
	}
	
	private class MyMouseListener implements MouseListener {

		/**
		 * Handle a mouse click on a card by toggling its "selected" property.
		 * Each card is represented as a label.
		 * @param e the mouse event.
		 */
		private boolean isActive;
		public MyMouseListener()
		{
			isActive = true;
		}
		public MyMouseListener(boolean isActive)
		{
			this.isActive = isActive;
		}
		public boolean isActive()
		{
			return isActive;
		}
		public void deactivate()
		{
			isActive = false;
		}
		public void activate()
		{
			isActive = true;
		}
		public void mouseClicked(MouseEvent e)
		{
		}

		/**
		 * Ignore a mouse exited event.
		 * @param e the mouse event.
		 */
		public void mouseExited(MouseEvent e) {
		}

		/**
		 * Ignore a mouse released event.
		 * @param e the mouse event.
		 */
		public void mouseReleased(MouseEvent e) {
		}

		/**
		 * Ignore a mouse entered event.
		 * @param e the mouse event.
		 */
		public void mouseEntered(MouseEvent e) {
		}

		/**
		 * Ignore a mouse pressed event.
		 * @param e the mouse event.
		 */
		public void mousePressed(MouseEvent e)
		{
			if(!isActive)
			{
				return;
			}
			//Deck
			if(e.getSource().equals(deckDisplay))
			{
				game.clickDeck();
				repaint();
				return;
			}
			//Discard
			int temp = 0;
			for(int i = 0; i < discardDisplay.size(); i ++)
			{
				if(discardDisplay.get(i).isVisible())
				{
					temp = i;
				}
			}
			if(e.getSource().equals(discardDisplay.get(temp)))
			{
				game.clickDiscardPile();
				repaint();
				return;
			}
			//ACP
			for (ArrayList<JLabel> cards : pilesDisplay)
			{
				for(JLabel card : cards)
				{
					AlternatingCardPile acp = game.getPiles()[pilesDisplay.indexOf(cards)];
					if (e.getSource().equals(card)) //card is faceup
					{
						game.clickAlternatingPile(acp, acp.size() - cards.indexOf(card));
						repaint();
						return;
					}
				}
			}
			//Targets
			for (JLabel card : targetsDisplay)
			{
				if(e.getSource().equals(card))
				{
					game.clickTargetPile(targetsDisplay.indexOf(card));
					repaint();
					return;
				}
			}
		}
	}
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		//System.out.println(KeyEvent.VK_R + " " + key);
		if(key == KeyEvent.VK_R)
		{
			game = new SolitaireGame();
			//initDisplay();
			winMessage.setVisible(false);
			repaint();
		}
		if(key == KeyEvent.VK_Z)
		{
			
		}
	}
	public void keyTyped(KeyEvent e)
	{
		
	}
	public void keyReleased(KeyEvent e)
	{
	
	}
}