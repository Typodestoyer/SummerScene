/**
 * Card.java
 *
 * <code>Card</code> represents a playing card.
 */
import java.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

public class Card {
	private char suit;
	private int rank;
	private int value;
	boolean isSelected;
	private final static String[] SUITS = {"clubs", "diamonds", "hearts", "spades"};
	private final static String[] RANKS = {"ace","2","3","4","5","6","7","8","9","10","jack","queen","king"};
	
	public Card(char suit, int rank, int value)
	{
		this.suit = suit;
		this.rank = rank;
		this.value = value;
		isSelected = false;
	}
	
	public Card(Card c)
	{
		this.suit = c.getSuit();
		this.rank = c.getRank();
		this.value = c.getValue();
		this.isSelected = c.isSelected();
	}

	public char getSuit()
	{
		return suit;
	}
	
	public int getRank()
	{
		return rank;
	}

	public int getValue()
	{
		return value;
	}
	
	public boolean isSelected()
	{
		return isSelected;
	}
	
	public void select()
	{
		isSelected = true;
	}
	
	public void unselect()
	{
		isSelected = false;
	}
	
	//Returns name of the file to access
	public ImageIcon getImage() {
		ImageIcon ithing = new ImageIcon("cards/" + rank() + suit() + (isSelected?"S":"") + ".GIF");
		return(ithing);
	}
	
	//Returns string equivalent of the suit
	public String suit()
	{
		switch(suit)
		{
			case 'c':
				return SUITS[0];
			case 'd':
				return SUITS[1];
			case 'h':
				return SUITS[2];
			case 's':
				return SUITS[3];
			default:
				return "k";
		}
	}
	
	//Returns string equivalent of the rank
	public String rank()
	{
		return RANKS[rank - 1];
	}
	
	
	public boolean matches(Card otherCard)
	{
		return ((otherCard.getSuit() == this.getSuit()) && (otherCard.getRank() == this.getRank()) && (otherCard.getValue() == this.getValue()));
	}
	
	@Override
	public String toString()
	{
		return (rank<10?"0":"") + rank + "" + suit;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		Card c = (Card)obj;
		return(this.suit == c.getSuit() && this.rank == c.getRank() && this.value == c.getValue());
	}
}
