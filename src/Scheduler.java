// Adam Bratin
//05/03/13
//Programming Assignment Phase 3 Memory Management

import java.util.*;
import java.io.*;

public class Scheduler{
	private static Queue ready; //ready queue
	private static Node [][] running; //array of running queues
	private static QueueA [] blocked; // array of blocked queues
	private static int IOs; //number of IO devices
	private static int processes; //number of processes
	private static int processors; //number of processors
	private static Node[] process_list; //array of processes
	private static int mem; //size of memory
	private static Memory mainMem; //Main Memory
  public static void main(String[] args){ //contains while loops to handle main menu
		String temp;
		int choice;
		boolean quitProgram = false;
		Scanner in = new Scanner(System.in);
		System.out.println("Hello welcome to the OS Scheduler Simulation");
		importData();
		while(quitProgram == false){
			System.out.println("What would you like to do?");
			System.out.println("Press 1 to add Process");
			System.out.println("Press 2 to delete Process");
			System.out.println("Press 3 to Simulate Processor(s) running FCFS");
			System.out.println("Press 4 to Simulate Processor(s) running Round Robin");
			System.out.println("Press 5 to quit");
			while (!in.hasNextInt()){
		      System.out.println("Please only enter one of the menu choices --> ");
		      temp = in.next();
		    }
			choice = in.nextInt();
		    /* This part ensures the input is between 1 and 5 after passing the while loop above 
		    making sure the input is an integer. */
		    while (choice < 1 || choice > 5){
		      System.out.println("Please only enter an integer between 1 and 5 inclusive --> ");
		      while (!in.hasNextInt()){
		        System.out.println("Please only enter one of the menu choices --> ");
	          temp = in.next();
		      }
		      choice = in.nextInt();
		    }
		    if (choice==1){
					addNode();
					processes+=1;
					choice=0;
			}
			else if (choice==2){
				int deleteIndex=deleteNode();
				if (deleteIndex!=-1){
					processes-=1;
				}
				else{
					System.out.println("There is no process with that name. Try Again.");
				}
				choice=0;
			}
			else if (choice==3){
				simulate(1);
				choice=0;
			}
			else if (choice==4){
				simulate(2);
				choice=0;
			}
			else{
				export();
				quitProgram=true;
			}
		}
		in.close();
	}
		
	public static void importData(){ //Imports already created data from text file including processes, number of processors, number of IOs, and size of memory 
		try{
			File inFile = new File("src/data.txt");
			Scanner in = new Scanner(inFile);
			int count=0;
			String line;
			int start;
			String name;
			int duration;
			double interrupt;
			int priority;
			int memory;
			processors=Integer.parseInt(in.nextLine());
			IOs=Integer.parseInt(in.nextLine());
			blocked = new QueueA[IOs];
			processes=Integer.parseInt(in.nextLine());
			mem= Integer.parseInt(in.nextLine());
			mainMem = new Memory((int) Math.pow(2,mem-20));
			process_list = new Node[processes];
			line = in.nextLine();
			while (!line.equals("")){
				name=line;
				start=Integer.parseInt(in.nextLine());
				duration=Integer.parseInt(in.nextLine());
				interrupt=Double.parseDouble(in.nextLine());
				priority=Integer.parseInt(in.nextLine());
				memory=Integer.parseInt(in.nextLine());
				Node proc = new Node(name, start, duration, interrupt, priority, memory, IOs);
				process_list[count]=proc;
				count+=1;
				line = in.nextLine();
			}
			in.close();
		}
		catch(IOException e) {
		  System.out.println("Input/output error " + e);
		}
	}
	
	public static void addNode(){ //adds Process to process list
		Scanner in = new Scanner(System.in);
		String tempIn2;
		int start=0;
		String name;
		int duration=0;
		double interrupt=0;
		int priority=0;
		int memory=0;
		//loops to get data on process from the user and check to make sure the data is valid format
		System.out.println("Please enter the process name: ");
		name = in.next();
		while (start<=0){
			System.out.println("please enter the start cycle: ");
			while (!in.hasNextInt()){
				System.out.print("Please enter an integer as the start cycle: ");
				tempIn2 = in.next();
			}
			start = in.nextInt();
		}
		while (duration<=0){
			System.out.println("please enter the process duration (in cycles): ");
			while (!in.hasNextInt()){
				System.out.print("Please enter an integer as the duration: ");
				tempIn2 = in.next();
			}
			duration = in.nextInt();
		}
		while (interrupt<=0 || interrupt>1){
			System.out.println("Please enter a number between 0 and 1 for the probability of an I/O event: ");
			while (!in.hasNextDouble()){
				System.out.print("Please enter a number between 0 and 1 for the probability of an I/O event: ");
				tempIn2 = in.next();
			}
			interrupt = in.nextDouble();
		}
		while (priority<1 || priority>20){
			System.out.println("Please enter a number between 1 and 20 for the priority of the process: ");
			while (!in.hasNextDouble()){
				System.out.print("Please enter a number between 1 and 20 for the priority of the process: ");
				tempIn2 = in.next();
			}
			priority = in.nextInt();
		}
		while (memory<=0){
			System.out.println("please enter the memory size of process (in pages): ");
			while (!in.hasNextInt()){
				System.out.print("Please enter an integer as the memory size: ");
				tempIn2 = in.next();
			}
			memory = in.nextInt();
		}
		Node proc = new Node(name, start, duration, interrupt, priority, memory, IOs);
		Node temp=proc;
		Node temp1;
		//handles if the process_list array cannot fit the incoming process the array is recreated with more space and the information is copied from the old array to the new array 
		if (processes+1>process_list.length){
			Node[] process_listnew = new Node[process_list.length+100];
			for(int i=0;i<=processes;i++){
				if (i==processes){
					process_listnew[i]=temp;
				}
				else if (temp.getStart()<process_list[i].getStart()){
					temp1=process_list[i];
					process_listnew[i]=temp;
					temp=temp1;
				}
				else{
					process_listnew[i]=process_list[i];
				}
			}
			process_list=process_listnew;
		}
		else{
			for(int i=0;i<=processes;i++){
				if (i==processes){
					process_list[i]=temp;
				}
				else if (temp.getStart()<process_list[i].getStart()){
					temp1=process_list[i];
					process_list[i]=temp;
					temp=temp1;
				}
			}
		}
		in.close();
	}
	
	public static int deleteNode(){ //deletes process from process list given a name from user
		Scanner in = new Scanner(System.in);
		String name = in.next();
		int deleteIndex=-1;
		for(int i=0;i<=processes-1;i++){
			if (name == process_list[i].getName()){
				deleteIndex = i;
			}
		}
		in.close();
		if (deleteIndex==-1){
			return deleteIndex;
		}
		else{ //moves processes in list down after process is deleted
			for(int i=deleteIndex;i<=processes-2;i++){
					process_list[i]=process_list[i+1];
			}
			process_list[processes-1]=null;
			return deleteIndex;
		}
	}
	
	public static void export(){ // writes all data on processes, number of processors, number of IOs, and size of memory to txt file 
		try{
			PrintWriter out = new PrintWriter("data.txt");
			String data="";
            if(running!=null){
			    data+= (running.length + "\n");
            }
            if (blocked!=null){
                data+= (blocked.length + "\n");
            }
            if (processes!=0){
                data+= (processes + "\n");
                data+= (mem + "\n");
                for (int i=0; i<processes; i++){
                    data+=(process_list[i].getName() + "\n");
                    data+=(process_list[i].getStart() + "\n");
                    data+=(process_list[i].getDuration() + "\n");
                    data+=(process_list[i].getInterrupt() + "\n");
                    data+=(process_list[i].getPriority() + "\n");
                    data+=(process_list[i].getMemory() + "\n");
                }
            }
            if (data!=""){
			    data+="\n";
			    out.print(data);
            }
			out.close();
		}
		catch(IOException e) {
	    System.out.println("Input/output error " + e);
		}	
	}
	
	public static void simulate(int algorithm){ //simulates the OS scheduling using either FCFS or Round Robin while managing memory
		String data="";
		int proc_number = processes;
		ready = new Queue();
		running = new Node[processors][10];
		blocked = new QueueA[IOs];
		for(int i=0; i<blocked.length; i++){
			blocked[i]= new QueueA(proc_number);
		}
		int quota=3;
		int clock=0;
		boolean [] reachedQuota = new boolean[processors];
		for (int i = 0; i<reachedQuota.length; i++){
			reachedQuota[i]=false;
		}
		//runs until all processes have completed
		while (proc_number!=0){ 
			clock+=1;		
			if (running[0].length==clock){ //checks to see if running queues are too small for current clock cycle and increases the size of the queues
				Node[][] tempRun = new Node[processors][running[0].length+10];
				for(int a =0; a<running.length;a++){
					for(int b=0; b<running[0].length;b++){
						tempRun[a][b]=running[a][b];
					}
				}
				running=tempRun;
			}
			for(int i=0; i<proc_number; i++){ //checks process list for processes starting at current clock cycle and adding them to ready queue
				if (process_list[i].getStart()==clock){
					ready.enqueue(process_list[i]);
				}
			}
			for(int a=0;a<running.length; a++){ //chooses a process for each processor to run
				Node process=null;
				if (algorithm==1){
					process=grabProcess1(clock, a);
				}
				else{
					process=grabProcess2(reachedQuota[a], clock, a);
				}
				if (process!=null){ //checks to see if those processes are blocked and if so moves them to blocked queue and takes them out of memory
					boolean inIO=false;
					double checkIO;
					for(int j=0; j<blocked.length; j++){
						checkIO= Math.random();
						if (checkIO<=process.getInterrupt()){
							blocked[j].enqueue(process);
							process.setBlock(j,3);
							process.setinBlock(true);
							mainMem.deleteProcess(process);
							inIO=true;
						}
					}
						if (inIO==true){ // if a process is blocked then a new one is chosen from the ready queue to be run
							if (algorithm==1){
								process=grabProcess1(clock, a);
							}
							else{
								process=grabProcess2(reachedQuota[a], clock, a);
							}
						}
					if (process!=null){	//adds process chosen either originally or after handling IOs and is put on the processor then adds the process to memory if not already there			
						running[a][clock]=(process);
						process.setRuntime(process.getRuntime()+1);
						process.setinProcessor(process.getinProcessor()+1);
						if (process.getinMem()==false){
							mainMem.addProcess(process, ready);
						}
						if(process.getDuration()-process.getRuntime()==0){ //decrements number of processes after one is completed
							proc_number-=1;
						}
					}
				}
			}
			if (algorithm==2){
				for (int i = 0; i<reachedQuota.length; i++){
					reachedQuota[i]=false;
				}
			}
			//prints log of everything done at current clock cycle
			System.out.println(" Clock cycle " + clock + ":");
			data+=("Clock cycle " + clock + ":");
			System.out.print("\n Running :");
			data+="\n Running :";
			for(int a=0;a<running.length;a++){
				System.out.print("\n Processor " + (a+1) + ": ");
				data+=("\n Processor " + (a+1) + ": ");
				if (running[a][clock]!=null){
					System.out.print(running[a][clock].getName());
					data+=running[a][clock].getName();
				}
				else{
					System.out.print("none");
					data+="none";
				}
			}
			System.out.print("\n Ready : ");
			data+="\n Ready : ";
			if(ready.isEmpty()==false){
				data+=ready.printQueue();
			}
			else{
				System.out.print("none");
				data+="none";
			}
			System.out.print("\n Blocked: ");
			data+="\n Blocked: ";
			for(int i=0; i<blocked.length; i++){
				System.out.print("\n B" + (i+1) + ": ");
				data+=("\n B" + (i+1) + ": ");
				if (blocked[i].isEmpty()==false){
					data+=blocked[i].printQueue();
				}
				else{
					System.out.print("none");
					data+="none";
				}
				System.out.print("\n");
				data+="\n";
			}
			System.out.print("\n Memory: \n");
			data+=("\n Memory: \n");
			for(int j=0; j<process_list.length; j++){
				System.out.print(process_list[j].getName() + ": ");
				data+=process_list[j].getName() + ": ";
				if (process_list[j].getinMem()==true){
					System.out.print("In Memory \n");
					data+="In Memory \n";
				}
				else{
					System.out.print("Not in Memory \n");
					data+="Not in Memory \n";
				}
			}
			System.out.println();
			data+="\n";
			//handles decrimenting the time left in blocked queues for the head of each queue 
			for(int k=0; k<blocked.length; k++){
				if (!blocked[k].isEmpty()){
					blocked[k].front().setBlock(k, blocked[k].front().getBlock()[k]-1);
					if(blocked[k].front().getBlock()[k] == 0){
						Node process=blocked[k].dequeue();
						process.setinBlock(false);
						ready.enqueue(process);
					}
				}
			}
			for(int b=0;b<running.length; b++){ //deletes a process from memory if the process has completed
				if (running[b][clock]!=null && running[b][clock].getDuration()-running[b][clock].getRuntime()==0){
					mainMem.deleteProcess(running[b][clock]);
				}
			}
			if (algorithm==2 && clock>1){ //checks to see if process in each process has reached time quota if it has then it is added to the ready queue
				for(int a=0;a<running.length; a++){
					if (running[a][clock-1]!=null){
						if(running[a][clock-1].getinProcessor()==quota && (running[a][clock-1].getRuntime()-running[a][clock-1].getDuration())!=0){
							running[a][clock-1].setinProcessor(0);
							ready.enqueue(running[a][clock-1]);
							reachedQuota[a]=true;
						}
					}
				}
			}
		}
		data+="\n";
		for (int i=0;i<=clock; i++){
			if (i<10){
				data+=("0" + i + " ");
			}
			else{
				data+=(i + " ");
			}
		}
		data+="\n";
		for (int a=0; a<running.length; a++){
			for (int b=0; b<=clock; b++){
				if (running[a][b]!=null){
					data+=(running[a][b].getName() +" ");
				}
				else {
					data+= "   ";
				}
			}
		}
		try{ //writes to txt file log of simulation 
			String filename="";
			if (algorithm==1){
				filename="FCFS.txt";
			}
			else{
				filename="RoundRobin.txt";
			}
			PrintWriter out = new PrintWriter(filename);
			data+="\n";
			out.print(data);
			out.close();
		}
		catch(IOException e) {
	    System.out.println("Input/output error " + e);
		}
	}
	
	private static Node grabProcess1(int clock, int a){ //determines what process to pick for FCFC
		Node process = null;
			if (clock>1){ 
				if (running[a][clock-1]!=null && (running[a][clock-1].getRuntime()-running[a][clock-1].getDuration())!=0 && running[a][clock-1].getinBlock()==false){
					process = running[a][clock-1];
				}
				else if (ready.isEmpty()==false){
					process = ready.dequeue();
				}
			}
			else{
				if (ready.isEmpty()==false){
					process = ready.dequeue();
				}
			}
		return process;
	}
	 
	private static Node grabProcess2(boolean reachedQuota, int clock, int a){//determins what process to pick for Round Robin
		Node process = null;
			if (clock>1){ 
				if (reachedQuota==false && running[a][clock-1]!=null && (running[a][clock-1].getRuntime()-running[a][clock-1].getDuration())!=0 && running[a][clock-1].getinBlock()==false){
					process = running[a][clock-1];
				}
				else if (ready.isEmpty()==false){
					process = ready.dequeue();
				}
			}
			else{
				if (ready.isEmpty()==false){
					process = ready.dequeue();
				}
			}
		return process;
	}
}	