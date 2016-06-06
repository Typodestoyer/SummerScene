import java.util.*;

public class AlternatingCardPile extends AbstractCardPile
{
	int numOfVisibleCards;
	public AlternatingCardPile()
	{
		super();
		numOfVisibleCards = 1;
	}
	//Precondition: cp is AlternatingCardPile
	public boolean isValidAdd(List<Card> cp)
	{
		boolean cpAlternates = true;
		for(int i = 1; i < cp.size(); i ++)
		{
			switch(cp.get(i).getSuit())
			{
				case 'c':
				case 's':
					if(cp.get(i-1).getSuit() == 'c' || cp.get(i-1).getSuit() == 's')
					{
						cpAlternates = false;
					}
					break;
				case 'd':
				case 'h':
					if(cp.get(i-1).getSuit() == 'd' || cp.get(i-1).getSuit() == 'h')
					{
						cpAlternates = false;
					}
					break;
				default:
					throw new RuntimeException("Error: card " + cp.get(i) + " has an improper suit.");
			}
			if(cp.get(i).getRank() != cp.get(i-1).getRank() - 1)
			{
				cpAlternates = false;
			}
		}
		
	
		
		if(cpAlternates)
		{
			return isValidAdd(cp.get(0));
		}
		else
		{
			throw new IllegalArgumentException("Tried to add the wrong type of pile to an alternating pile!");
		}
	}
	
	public boolean isValidAdd(Card c)
	{
		if(this.size() == 0)
		{
			return(c.getRank() == 13);
		}
		switch(c.getSuit())
		{
			case 'c':
			case 's':
				if(this.getTopCard().getSuit() == 'c' || this.getTopCard().getSuit() == 's')
				{
					return false;
				}
				break;
			case 'd':
			case 'h':
				if(this.getTopCard().getSuit() == 'd' || this.getTopCard().getSuit() == 'h')
				{
					return false;
				}
				break;
			default:
				throw new RuntimeException("Error: card " + c + " has an improper suit.");
		}
		if(c.getRank() != this.getTopCard().getRank() - 1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	@Override
	public void remove(List<Card> cp)
	{
		super.remove(cp);
		numOfVisibleCards -= cp.size();
		if(numOfVisibleCards == 0 && this.size() != 0)
		{
			numOfVisibleCards = 1; //reveal new card
		}
	}
	
	@Override
	public boolean add(List<Card> cp)
	{
		if(super.add(cp))
		{
			numOfVisibleCards += cp.size();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean add(Card c)
	{
		if(numOfVisibleCards == 0)
		{
			super.add(c);
			return true;
		}
		if(super.add(c))
		{
			numOfVisibleCards ++;
			return true;
		}
		return false;
	}
	
	public Card getTopCard()
	{
		return this.get(this.size() - 1);
	}
	
	public String toString()
	{
		return("[" + (this.size() - numOfVisibleCards) + "]" + this.subList(this.size() - numOfVisibleCards, this.size()).toString());
	}
	
	public int getNumOfVisibleCards()
	{
		return numOfVisibleCards;
	}
}
