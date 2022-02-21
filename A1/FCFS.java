// Assignment 1 Comp 2240
// FCFS Class
// Author: Jason Disher
// Student No: C3185333
// Last Modified: 1/9/2020

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.LinkedList;


public class FCFS extends Schedule
{
	int disp;
	private Clock clock;
	private CPU cpu;
	private Queue<SimProcess> new_processes;
	private Queue<SimProcess> ready_queue;
	private LinkedList<TimeStampPair> processor_record;
	private LinkedList<SimProcess> completed_processes;
	private double turnaround_average;
	private double waiting_average;

		//Constructor
	public FCFS(Queue<SimProcess> p, int d)
	{
		disp=d;
		clock = new Clock();
		cpu = new CPU(clock);
		ready_queue = new LinkedList<SimProcess>();
		processor_record = new LinkedList<TimeStampPair>();
		new_processes = new LinkedList<SimProcess>(p);
		completed_processes = new LinkedList<SimProcess>();
		turnaround_average=0.0;
		waiting_average=0.0;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Dispacthes processes from ready queue to CPU
	//---------------------------------------------------------------------------
	private void dispatch()
	{
		SimProcess p = ready_queue.remove();

		cpu.setProcess(p);
		clock.advance(disp);

		p.startProcess(clock.getTime());
		processor_record.add(new TimeStampPair(clock.getTime(), new String(p.getID()), p.getPriority()));

		while(new_processes.peek()!=null && new_processes.peek().getArrivalTime()==clock.getTime())
			ready_queue.add(new_processes.remove());
	}
	//---------------------------------------------------------------------------
	//Pre-Condition:NIL
	//Post_Condition:
	//---------------------------------------------------------------------------
	public void runSim()
	{
		while(new_processes.peek()!=null && new_processes.peek().getArrivalTime()==clock.getTime())
			ready_queue.add(new_processes.remove());

		while(!ready_queue.isEmpty())
		{
			if(cpu.isIdle())
				dispatch();

			while(!cpu.isIdle())
			{
				cpu.pulse();
				if(cpu.isIdle())
					completed_processes.add(cpu.release());
				else
					clock.advance(1);

				while(new_processes.peek()!=null && new_processes.peek().getArrivalTime()==clock.getTime())
					ready_queue.add(new_processes.remove());
			}
		}
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Outputs to screen statistics of schedule
	//---------------------------------------------------------------------------
	public void displayStats()
	{
		int count=completed_processes.size();
		double turnaround_total=0.0;
		double waiting_total=0.0;
		SimProcess p;

		System.out.println("FCFS:");
		while(!processor_record.isEmpty())
			System.out.println(processor_record.remove());
		System.out.println(String.format("\nProcess  Turnaround Time   Waiting Time"));
		while(!completed_processes.isEmpty())
		{
			p=completed_processes.remove();
			turnaround_average+=p.getTurnaroundTime();
			waiting_average+=p.getTurnaroundTime()-p.getServiceTime();
			System.out.println(p);
			p.reset();
		}

		turnaround_average=turnaround_average/count;
		waiting_average=waiting_average/count;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns double of Turn-around average
	//---------------------------------------------------------------------------
	public double getTurnaroundAverage()
	{
		return turnaround_average;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns double of Wating time average
	//---------------------------------------------------------------------------
	public double getWaitingAverage()
	{
		return waiting_average;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns String
	//---------------------------------------------------------------------------
	public String toString()
	{
		return String.format("%4s %25.2f %20.2f", "FCFS", turnaround_average, waiting_average);
	}
}