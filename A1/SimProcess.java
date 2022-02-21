// Assignment 1 Comp 2240
// Process Class
// Author: Jason Disher
// Student No: C3185333
// Last Modified: 1/9/2020


public class SimProcess implements Comparable<SimProcess>
{
	private final String pID;
	private int service_time;
	private int arrival_time;
	private int start_time;
	private int finish_time;
	private int turn_around_time;
	private int remaining_service_time;
	private int quantum;
	private int remaining_quanta;
	private int priority;
	private int current_priority;
	private boolean processed;
	private boolean hasPriority;

		//Constructors
	public SimProcess(String id)
	{
		pID=id;
		service_time=0;
		arrival_time=0;
		start_time=0;
		finish_time=0;
		turn_around_time=0;
		remaining_service_time=service_time;
		quantum=0;
		remaining_quanta=0;
		priority=0;
		current_priority=0;
		processed=false;
		hasPriority=false;

	}
	//---------------------------------------------------------------------------
	//Pre-Condition: int int
	//Post_Condition: SimProcess object
	//---------------------------------------------------------------------------
	public SimProcess(String id, int sTime)
	{
		pID=id;
		service_time=sTime;
		arrival_time=0;
		start_time=0;
		finish_time=0;
		turn_around_time=0;
		remaining_service_time=service_time;
		quantum=0;
		remaining_quanta=0;
		priority=0;
		current_priority=0;
		processed=false;
		hasPriority=false;
	}
	public SimProcess(String id, int sTime, int aTime)
	{
		pID=id;
		service_time=sTime;
		arrival_time=aTime;
		start_time=0;
		finish_time=0;
		turn_around_time=0;
		remaining_service_time=service_time;
		quantum=0;
		remaining_quanta=0;
		priority=0;
		current_priority=0;
		processed=false;
		hasPriority=false;
	}
	public SimProcess(String id, int sTime, int aTime, int p)
	{
		pID=id;
		service_time=sTime;
		arrival_time=aTime;
		start_time=0;
		finish_time=0;
		turn_around_time=0;
		remaining_service_time=service_time;
		quantum=0;
		remaining_quanta=0;
		priority=p;
		current_priority=priority;
		processed=false;
		hasPriority=false;
	}

		//Mutators
	//---------------------------------------------------------------------------
	//Pre-Condition: int
	//Post_Condition: Sets Arrival time int
	//---------------------------------------------------------------------------
	public void setArivalTime(int aTime)
	{
		arrival_time=aTime;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: int
	//Post_Condition: Sets Quantum int
	//---------------------------------------------------------------------------
	public void setQuantum(int q)
	{
		quantum=q;
		remaining_quanta=q;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: int
	//Post_Condition: Sets process starting time int
	//---------------------------------------------------------------------------
	public void startProcess(int t)
	{
		start_time=t;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns boolean of remaining service time
	//---------------------------------------------------------------------------
	public boolean execute()
	{
		if(remaining_service_time==0)
			return false;
		else
		{
			remaining_service_time--;
			return true;
		}
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Resets all private varaibles to otiginal states
	//---------------------------------------------------------------------------
	public void reset()
	{
		start_time=0;
		finish_time=0;
		turn_around_time=0;
		remaining_quanta=0;
		remaining_service_time=service_time;
		remaining_quanta=quantum;
		current_priority=priority;
		processed=false;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: int
	//Post_Condition: Sets finish time of process
	//---------------------------------------------------------------------------
	public void complete(int t)
	{
		finish_time=t;
		turn_around_time=finish_time-arrival_time;
		processed=true;
		hasPriority=false;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Reduces remaining IRQ time quantum 
	//---------------------------------------------------------------------------
	public void reduceQuanta()
	{
		if(remaining_quanta<=2)
			remaining_quanta=2;
		else
			remaining_quanta--;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Increases value of processes priority
	//---------------------------------------------------------------------------
	public void reducePriority()
	{
		if(current_priority>=5)
			current_priority=5;
		else 
			current_priority++;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Sets priority TRUE
	//---------------------------------------------------------------------------
	public void hasPriority()
	{
		hasPriority=true;
	}

		//Queries
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns int of remaing IRQ time quantum
	//---------------------------------------------------------------------------
	public int getQuanta()
	{
		return remaining_quanta;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns int of current priority
	//---------------------------------------------------------------------------
	public int getPriority()
	{
		return current_priority;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns String of process name
	//---------------------------------------------------------------------------
	public String getID()
	{
		return pID;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns int of Arrivial time
	//---------------------------------------------------------------------------
	public int getArrivalTime()
	{
		return arrival_time;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns int of Service time
	//---------------------------------------------------------------------------
	public int getServiceTime()
	{
		return service_time;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns int of Turn-around time
	//---------------------------------------------------------------------------
	public int getTurnaroundTime()
	{
		return turn_around_time;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns boolean
	//---------------------------------------------------------------------------
	public boolean isFinished()
	{
		return remaining_service_time==0;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: SimProcess
	//Post_Condition: Returns int of caparision
	//---------------------------------------------------------------------------
	public int compareTo(SimProcess p) 
	{
		final int less_than=-1;
		final int equal=0;
		final int more_than=1;

		if(hasPriority)
		{
			if(priority==p.getPriority())
				return equal;
			else if(priority<p.getPriority())
				return less_than;
			else
				return more_than;
		}
		else if(!processed)
		{
			if(service_time==p.getServiceTime())
				return equal;
			else if(service_time<p.getServiceTime())
				return less_than;
			else
				return more_than;
		}
		else
		{
			int a=Integer.parseInt(pID.substring(1));
			int b=Integer.parseInt(p.getID().substring(1));

			if(a==b)
				return equal;
			else if(a<b)
				return less_than;
			else
				return more_than;
		}
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns String representation of SimProcess
	//---------------------------------------------------------------------------
	public String toString()
	{
		String str;

		return String.format("%s %15d %15d", pID, turn_around_time, turn_around_time-service_time);
	}
}