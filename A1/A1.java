// Assignment 1 Comp 2240
// Author: Jason Disher
// Student No: C3185333
// Last Modified: 1/9/2020

import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.LinkedList;
import java.io.FileNotFoundException;
import java.io.File;


public class A1
{
	private int disp;
	private Queue<SimProcess> new_processes;

		//Constructor
	public A1()
	{
		new_processes = new LinkedList<SimProcess>();
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: Queued list of new processes
	//Post_Condition: Returns a list of new prosesses
	//---------------------------------------------------------------------------
	public Queue<SimProcess> getList()
	{
		return new_processes;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Creates SimProcess objects from file and adds them to a list of new processes
	//---------------------------------------------------------------------------
	public void processInput(String filename)
	{
		Scanner instream;

		try
		{
			instream = new Scanner(new File(filename));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File does not exsist");
			return;
		}

		if(instream.next().equals("BEGIN"))
		{
			do
			{
				if(instream.next().equals("DISP:"))
					disp=Integer.parseInt(instream.next());
			}while(!instream.next().equals("END"));
				
			boolean eof=false;
			boolean end=false;

			while(!eof)
			{
				String id="";
				int arrive=0, service=0, priority=0;
				do
				{	
					String temp=instream.next();
					
					if(temp.equals("ID:"))
					{
						id=instream.next();
						temp=instream.next();
					}
					if(temp.equals("Arrive:"))
					{
						arrive=Integer.parseInt(instream.next());
						temp=instream.next();
					}
					if(temp.equals("ExecSize:"))
					{
						service=Integer.parseInt(instream.next());
						temp=instream.next();
					}
					if(temp.equals("Priority:"))
					{
						priority=Integer.parseInt(instream.next());
						temp=instream.next();
					}
					if(temp.equals("END"))
					{
						new_processes.add(new SimProcess(id, service, arrive, priority));
						end=true;
					}
					else if(temp.equals("EOF"))
						eof=true;

				}while(!end);
			}
			instream.close();	
		}
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns the value of DISP
	//---------------------------------------------------------------------------
	public int getDisp()
	{
		return disp;
	}

	//---------------------------------------------------------------------------
	//------------------               Main                 ---------------------
	//---------------------------------------------------------------------------
	public static void main(String args[])
	{
		A1 sim = new A1();
		sim.processInput(args[0]);
		int time_quantum=4;

		Schedule fcfs = new FCFS(sim.getList(), sim.getDisp());
		Schedule spn = new SPN(sim.getList(), sim.getDisp());
		Schedule pp = new PP(sim.getList(), sim.getDisp());
		Schedule prr = new PRR(sim.getList(), sim.getDisp(),  time_quantum);
		
		fcfs.runSim();
		fcfs.displayStats();
		spn.runSim();
		spn.displayStats();
		pp.runSim();
		pp.displayStats();
		prr.runSim();
		prr.displayStats();

		System.out.println("\n\nSummary:");
		System.out.println(String.format("%10s %24s %20s", "Algorithim", "Average Turn-around", "Average waiting"));
		System.out.println(fcfs);
		System.out.println(spn);
		System.out.println(pp);
		System.out.println(prr);
	}
}