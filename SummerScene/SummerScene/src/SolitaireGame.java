import java.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
public class SolitaireGame
{
	
	Deck deck;
	DiscardPile discard;
	AlternatingCardPile[] piles; //piles that are dealt to at start
	TargetCardPile[] targets;    //piles that need to be filled
	AbstractCardPile selectedPile;
	
	
	int numOfSelectedCards;
	int games;
	int wins;
	
	public static int NUM_OF_CARDS_DEALT = 3;
	
	//CONSTRUCTOR
	
	public SolitaireGame()
	{
		wins = 0;
		games = 0;
		//will be updated/stored to file later
		
		numOfSelectedCards = 0;
		selectedPile = new CardPile();
		
		newGame();
	}

	public void newGame()
	{
		deck = new Deck();
		discard = new DiscardPile();
		piles = new AlternatingCardPile[7];
		for(int i = 0; i < 7; i ++)
		{
			piles[i] = new AlternatingCardPile();
		}
		targets = new TargetCardPile[4];
		
		for(int i = 0; i < 4; i ++)
		{
			targets[i] = new TargetCardPile();
		}
		
		for(int i = 0; i < 7; i ++)
		{
			for(int j = i; j < 7; j ++)
			{
				try
				{
					piles[j].forceAdd(deck.remove(deck.size() - 1));
				}
				catch(ArrayIndexOutOfBoundsException ex)
				{
					System.out.println("deck.size() == " + deck.size());
					System.exit(1);
				}
			}
		}
	}
	
	//PILE-CLICKERS--------------------------------------------------------------------------------------
	
	public boolean clickDeck()
	{
		//Note: avoids implementing move since it also reverses the order of the cards first
		ArrayList<Card> cp = new ArrayList<Card>();
		if(selectedPile.size() != 0)
		{
			return false;
		}
		if(deck.size() == 0)
		{
			deck = new Deck(discard);
			Collections.reverse(deck);
			discard = new DiscardPile();
		}
		else if(deck.size() < 3)
		{
			cp = deck.getUnreferencedFromTop(deck.size());
			Collections.reverse(cp);
			deck.move(cp, discard);
		}
		else
		{
			cp = deck.getUnreferencedFromTop(NUM_OF_CARDS_DEALT);
			Collections.reverse(cp);
			deck.move(cp, discard);
		}
		return true;
	}

	public boolean clickTargetPile(int pileNum)
	{
		return clickTargetPile(targets[pileNum]);
	}
	
	public boolean clickTargetPile(TargetCardPile tcp)
	{
		if(selectedPile.size() == 0)
		{
			if(tcp.size() == 0)
			{
				return false;
			}
			numOfSelectedCards = 1;
			selectedPile = tcp;
			selectCards(tcp, 1);
			return true;
		}
		else if(selectedPile.equals(tcp))
		{
			numOfSelectedCards = 0;
			selectedPile = new CardPile();
			unselectCards(tcp, 1);
			return true;
		}
		else
		{
			return moveSelectedCards(tcp);
		}
	}
	
	public boolean clickAlternatingPile(int pileNum, int cardFromTop) //cardFromTop > 0
	{
		return clickAlternatingPile(piles[pileNum], cardFromTop);
	}
	
	public boolean clickAlternatingPile(AlternatingCardPile acp, int cardFromTop)
	{
		if(cardFromTop > acp.getNumOfVisibleCards())
		{
			return false;
		}
		else if(selectedPile.size() == 0)
		{
			selectedPile = acp;
			numOfSelectedCards = cardFromTop;
			selectCards(acp, cardFromTop);
			return true;
		}
		else if(selectedPile.equals(acp))
		{
			if(cardFromTop != numOfSelectedCards)
			{
				return false;
			}
			else
			{
				numOfSelectedCards = 0;
				selectedPile = new CardPile();
				unselectCards(acp, cardFromTop);
				return true;
			}
			
		}
		else //try to move selectedPile to acp
		{
			return moveSelectedCards(acp);
		}
	}
	
	public boolean clickDiscardPile()
	{
		if(discard.size() == 0)
		{
			return false;
		}
		else if(selectedPile.equals(discard))
		{
			numOfSelectedCards = 0;
			selectedPile = new CardPile();
			discard.get(discard.size() - 1).unselect();
			return true;
		}
		else if(selectedPile.size() != 0)
		{
			return false;
		}
		else
		{
			selectedPile = discard;
			numOfSelectedCards = 1;
			selectCards(discard, numOfSelectedCards);
			return true;
		}
	}
	
	//UTILITY-FUNCTIONS---------------------------------------------------------------------------
	
	public void selectCards(AbstractCardPile cp, int numFromTop)
	{
		for(int i = cp.size() - 1; i > cp.size() - numFromTop - 1; i --)
		{
			cp.get(i).select();
		}
	}
	
	public void unselectCards(AbstractCardPile cp, int numFromTop)
	{
		for(int i = cp.size() - 1; i > cp.size() - numFromTop - 1; i --)
		{
			cp.get(i).unselect();
		}
	}
	
	public boolean moveSelectedCards(AbstractCardPile acp)
	{
		if(selectedPile.move(selectedPile.getUnreferencedFromTop(numOfSelectedCards), acp))
		{
			for(int i = acp.size() - 1; i > acp.size() - 1 - numOfSelectedCards; i --)
			{
				acp.get(i).unselect();
			}
			selectedPile = new CardPile();
			numOfSelectedCards = 0;
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean hasWon()
	{
		for(AlternatingCardPile acp : piles)
		{
			if(acp.size() != 0)
			{
				return false;
			}
		}
		return(deck.size() == 0 && discard.size() == 0);
	}
	
	//ACCESSORS---------------------------------------------------------------
	
	public Deck getDeck()
	{
		return deck;
	}
	
	public DiscardPile getDiscardPile()
	{
		return discard;
	}
	
	public AlternatingCardPile[] getPiles()
	{
		return piles;
	}
	
	public TargetCardPile[] getTargets()
	{
		return targets;
	}
	
	public AbstractCardPile getSelectedPile()
	{
		return selectedPile;
	}
	
	public ArrayList<AbstractCardPile> allPiles()
	{
		ArrayList<AbstractCardPile> allPiles = new ArrayList<AbstractCardPile>();
		allPiles.add(deck);
		allPiles.add(discard);
		for(AlternatingCardPile acp : piles)
		{
			allPiles.add(acp);
		}
		for(TargetCardPile tcp : targets)
		{
			allPiles.add(tcp);
		}
		return allPiles;
	}
	
	public ArrayList<Card> allCards()
	{
		ArrayList<Card> allCards = new ArrayList<Card>();
		for(AbstractCardPile acp : allPiles())
		{
			for(Card c : acp)
			{
				allCards.add(c);
			}
		}
		return allCards;
	}
}