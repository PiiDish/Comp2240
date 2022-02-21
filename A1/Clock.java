// Assignment 1 Comp 2240
// Clock Class
// Author: Jason Disher
// Student No: C3185333
// Last Modified: 1/9/2020


public class Clock
{
	private int time;
	private CPU cpu;

		//Constructor
	public Clock()
	{
		time=0;
		cpu=null;
	}
	public Clock(int q)
	{
		time=0;
		cpu=null;
	}
		//Mutators
	//---------------------------------------------------------------------------
	//Pre-Condition: CPU
	//Post_Condition: Sets the CPU
	//---------------------------------------------------------------------------
	public void setCPU(CPU processor)
	{
		cpu=processor;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: int time quantume
	//Post_Condition: Advances time quantum amount
	//---------------------------------------------------------------------------
	public void advance(int t)
	{
		time+=t;
	}
		//Queries
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns int of time
	//---------------------------------------------------------------------------
	public int getTime()
	{
		return time;
	}

}