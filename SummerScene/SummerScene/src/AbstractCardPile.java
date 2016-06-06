import java.util.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
public abstract class AbstractCardPile extends ArrayList<Card>
{
	ArrayList<JPanel> displayCards;
	public AbstractCardPile()
	{
		super();
	}
	
	public AbstractCardPile(Card c)
	{
		super();
		super.add(c);
	}
	
	public AbstractCardPile(AbstractCardPile cp)
	{
		super();
		super.addAll(cp);
	}
	public AbstractCardPile(char[] suits, int[] ranks, int[] values)
	{
		assert (ranks.length == values.length);
		for(char s : suits)
		{
			for(int i = 0; i < ranks.length; i ++)
			{
				add(new Card(s, ranks[i], values[i]));
			}
		}
	}
	
	//Methods
	
	public boolean add(List<Card> cp)
	{
		if(this.isValidAdd(cp))
		{
			for(int i = 0; i < cp.size(); i ++)
			{
				if(!forceAdd(cp.get(i)))
				{
					return false;
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public boolean add(Card c)
	{
		if(isValidAdd(c))
		{
			return super.add(c);
		}
		else
		{
			return false;
		}
	}
	
	public void remove(List<Card> cp)
	{
		for(Card c : cp)
		{
			remove(c);
		}
	}
	
	public boolean move(List<Card> cpMove, AbstractCardPile cpTo) //Moves cpMove from this to cpTo's top
	{
		if(cpTo.isValidAdd(cpMove))
		{
			for(Card c : cpMove)
			{
				if(this.contains(c) == false)
				{
					System.out.println("Card " + c + " not in list " + this + "!");
					return false;
				}
			}
			remove(cpMove);
			return (cpTo.add(cpMove));
		}
		else
		{
			return false;
		}
	}
	
	public ArrayList<Card> getUnreferencedFromTop(int numOfCardsFromTop)
	{
		ArrayList<Card> cList = new ArrayList<Card>();
		for(int i = this.size() - numOfCardsFromTop; i < this.size(); i ++)
		{
			Card c = new Card(this.get(i));
			cList.add(c);
		}
		return cList;
	}
	
	public boolean move(Card c, AbstractCardPile cpTo)
	{
		if(cpTo.isValidAdd(c))
		{
			remove(c);
			return (cpTo.add(c));
		}
		else
		{
			return false;
		}
	}
	
	public boolean forceAdd(Card c)
	{
		return (super.add(c));
	}
	
	public abstract boolean isValidAdd(List<Card> cp);
	public abstract boolean isValidAdd(Card c);
}