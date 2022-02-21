// Assignment 1 Comp 2240
// CPU Class
// Author: Jason Disher
// Student No: C3185333
// Last Modified: 1/9/2020


public class CPU
{
	private Clock clock;
	private SimProcess process;
	private boolean idle;

		//Constructors
	public CPU()
	{
		process=null;
		clock=null;
		idle=true;
	}
	public CPU(Clock clk)
	{
		process=null;
		clock=clk;
		idle=true;
	}
		//Mutators
	//---------------------------------------------------------------------------
	//Pre-Condition: SimProcess
	//Post_Condition: Sets process and cpu idle false
	//---------------------------------------------------------------------------
	public void setProcess(SimProcess p)
	{
		process=p;
		idle=false;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: State of idle
	//---------------------------------------------------------------------------
	public void pulse()
	{
		if(process.execute())
			idle=false;
		else
			idle=true;
	}
		//Queries
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns compleded Simprocess
	//---------------------------------------------------------------------------
	public SimProcess release()
	{
		process.complete(clock.getTime());
		return process;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns Interrupted SimProcess
	//---------------------------------------------------------------------------
	public SimProcess irq()
	{
		idle=true;
		return process;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns integer value of process priority
	//---------------------------------------------------------------------------
	public int processPriority()
	{
		return process.getPriority();
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns integer value of process priority
	//---------------------------------------------------------------------------
	public int getProcessServiceTime()
	{
		return process.getServiceTime();
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns boolean state of idle
	//---------------------------------------------------------------------------
	public boolean isIdle()
	{
		return idle;
	}
}