// Assignment 1 Comp 2240
// Timestamp Pair Class
// Author: Jason Disher
// Student No: C3185333
// Last Modified: 2/9/2020



public class TimeStampPair
{
	private int time;
	private String pID;
	private int priority;

		//Constructor
	public TimeStampPair()
	{
		time=0;
		pID=null;
		priority=0;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: int String
	//Post_Condition: Sets time and process name fopr record
	//---------------------------------------------------------------------------
	public TimeStampPair(int t, String id, int p)
	{
		time=t;
		pID=id;
		priority=p;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns String representation of TimeStampPair object
	//---------------------------------------------------------------------------
	public String toString()
	{
		return String.format("T%02d: %4s(%d)", time, pID, priority);
	}
}