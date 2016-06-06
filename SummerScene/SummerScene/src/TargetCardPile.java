import java.util.*;
public class TargetCardPile extends AbstractCardPile
{
	public boolean isValidAdd(List<Card> cp)
	{
		if(cp.size() == 1)
		{
			return isValidAdd(cp.get(0));
		}
		else
		{
			return false;
		}
	}
	public boolean isValidAdd(Card c)
	{
		return((this.size() == 0 && c.getRank() == 1) || (this.size() == c.getRank()-1 && this.get(0).getSuit() == c.getSuit()));
	}
}