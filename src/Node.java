//Adam Bratin
//04/15/13
//Programming Assignment 1 - Process Def

public class Node{

	private int start;
	private String name;
	private int duration;
	private double interrupt;
	private int priority;
	private int memory;
	private int runtime;
	private int inProcessor;
	private boolean inBlock;
	private boolean inMem;
	private int[] block;
	private Node next;
	
	public Node(String inname, int instart, int induration, double in_interrupt, int inpriority, int inmemory, int inIOs){
		start= instart;
		name= inname;
		duration=induration;
		interrupt=in_interrupt;
		priority=inpriority;
		memory=inmemory;
		runtime=0;
		inProcessor=0;
		block = new int[inIOs];
		inBlock=false;
	}
	
	public int getStart(){
		return start;
	}
	
	public String getName(){
		return name;
	}
	
	public int getDuration(){
		return duration;
	}
	
	public int getPriority(){
		return priority;
	}
	
	public double getInterrupt(){
		return interrupt;
	}
	
	public int getMemory(){
		return memory;
	}
	
	public int getRuntime(){
		return runtime;
	}
	
	public int getinProcessor(){
		return inProcessor;
	}
	
	public int[] getBlock(){
		return block;
	}
	
	public boolean getinBlock(){
		return inBlock;
	}
	
	public boolean getinMem(){
		return inMem;
	}
	
	public Node getNext(){
		return next;
	}

	public void setStart(int newStart){
		start= newStart;
	}
	
	public void setName(String newName){
		name= newName;
	}
	
	public void setPriority(int newPriority){
		priority = newPriority;
	}
	
	public void setInterrupt(int newInterrupt){
		interrupt = newInterrupt;
	}
	
	public void setNext(Node newNode){
		next = newNode;
	}
	
	public void setRuntime(int newRuntime){
		runtime=newRuntime;
	}
	
	public void setinProcessor(int newinProcessor){
		inProcessor=newinProcessor;
	}
	
	public void setMemory(int newMemory){
		memory = newMemory;
	}
	
	public void setBlock(int index, int cycles){
		block[index] = cycles;
	}
	
	public void setinBlock(boolean newin){
		inBlock = newin;
	}
	
	public void setinMem(boolean newin){
		inMem=newin;
	}
}