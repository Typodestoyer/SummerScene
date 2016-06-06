import java.util.*;
public class Deck extends AbstractCardPile
{
	public Deck()
	{
		super();
		int[] ranks = {1,2,3,4,5,6,7,8,9,10,11,12,13};
		char[] suits = {'c', 'd', 's', 'h'};
		for(int i : ranks)
		{
			for(char c : suits)
			{
				super.forceAdd(new Card(c, i, i));
			}
		}
		shuffle();
	}
	public Deck(AbstractCardPile cp)
	{
		super(cp);
	}
	public void shuffle()
	{
		for (int k = this.size() - 1; k > 0; k--)
		{
			int howMany = k + 1;
			int start = 0;
			int randPos = (int) (Math.random() * howMany) + start;
			Card temp = this.get(k);
			this.set(k, this.get(randPos));
			this.set(randPos, temp);
		}
	}
	public boolean isValidAdd(List<Card> cp)
	{
		return false;
	}
	
	public boolean isValidAdd(Card c)
	{
		return false;
	}
	
	
	//Alternate, more general constructors:
	/*
	public Deck(Card c)
	{
		super(c);
		shuffle();
	}
	
	public Deck(AbstractCardPile cp)
	{
		super(cp);
		shuffle();
	}
	public Deck(char[] suits, int[] ranks, int[] values)
	{
		super(suits, ranks, values);
		shuffle();
	}
	*/
}
