import java.util.*;
public class DiscardPile extends AbstractCardPile
{
	public boolean isValidAdd(List<Card> cp)
	{
		return true;
	}
	
	public boolean isValidAdd(Card c)
	{
		return true;
	}
	@Override
	public String toString()
	{
		if(this.size() <= SolitaireGame.NUM_OF_CARDS_DEALT)
		{
			return super.toString();
		}
		else
		{
			String s = "[";
			for(int i = this.size() - 3; i < this.size()-1; i ++)
			{
				s += this.get(i) + ",";
			}
			s += this.get(this.size() - 1) + "]";
			return s;
		}
	}
}