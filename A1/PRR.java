// Assignment 1 Comp 2240
// Priority Round Robin Class
// Author: Jason Disher
// Student No: C3185333
// Last Modified: 10/9/2020

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.LinkedList;


public class PRR extends Schedule
{
	private int disp;
	private int quantum;
	private int irq_timer;
	private Clock clock;
	private CPU cpu;
	private Queue<SimProcess> new_processes;
	private Queue<SimProcess> ready_queue;
	private LinkedList<TimeStampPair> processor_record;
	private PriorityQueue<SimProcess> completed_processes;
	private double turnaround_average;
	private double waiting_average;

		//Constructor
	public PRR(Queue<SimProcess> p, int d, int q)
	{
		disp=d;
		quantum=q;
		clock = new Clock();
		cpu = new CPU(clock);
		ready_queue = new LinkedList<SimProcess>();
		processor_record = new LinkedList<TimeStampPair>();
		new_processes = new LinkedList<SimProcess>(p);
		completed_processes = new PriorityQueue<SimProcess>();
		turnaround_average=0.0;
		waiting_average=0.0;
		irq_timer=0;
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Dispacthes processes from ready queue to CPU
	//---------------------------------------------------------------------------
	private void dispatch()
	{
		SimProcess p = ready_queue.remove();

		irq_timer=clock.getTime()+p.getQuanta();

		cpu.setProcess(p);
		clock.advance(disp);

		p.startProcess(clock.getTime());
		processor_record.add(new TimeStampPair(clock.getTime(), new String(p.getID()), p.getPriority()));

		checkNewProcesses();
	}
	//---------------------------------------------------------------------------
	//Pre-Condition:NIL
	//Post_Condition:Node created next, previous and data = NULL
	//---------------------------------------------------------------------------
	public void runSim()
	{
		checkNewProcesses();

		while(!ready_queue.isEmpty())
		{
			checkNewProcesses();

			if(cpu.isIdle())
				dispatch();

			while(!cpu.isIdle())
			{
				if(clock.getTime()==irq_timer && !ready_queue.isEmpty())
				{
					if(cpu.getProcessServiceTime()>quantum)
					{
						cpu.pulse();
						clock.advance(1);
						checkNewProcesses();
						ready_queue.add(cpu.irq());
						
						dispatch();
					}
				}
				
				cpu.pulse();
				
				if(cpu.isIdle())
					completed_processes.add(cpu.release());
				else
					clock.advance(1);

				checkNewProcesses();
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

		System.out.println("\nPRR:");
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
	//Post_Condition: Returns NIL
	//---------------------------------------------------------------------------
	public void checkNewProcesses()
	{
		while(!new_processes.isEmpty() && new_processes.peek().getArrivalTime()==clock.getTime())
		{
			SimProcess p=new_processes.remove();
			int q=2;

			if(p.getPriority()<=2)
				p.setQuantum(quantum);
			else
				p.setQuantum(q);

			ready_queue.add(p);
		}
	}
	//---------------------------------------------------------------------------
	//Pre-Condition: NIL
	//Post_Condition: Returns String
	//---------------------------------------------------------------------------
	public String toString()
	{
		return String.format("%-4s %25.2f %20.2f", "PRR", turnaround_average, waiting_average);
	}
}