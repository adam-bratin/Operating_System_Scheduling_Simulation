// Adam Bratin
//05/03/13
//Memory Class (Array)

public class Memory {
	private int count;
	private String[] mem;
	private int nextFree;
	
	public Memory(int size){
		mem = new String[size];
		count=0;
		nextFree=0;
	}
	
	/*insert a process into memory given the size of the process in MB, the process itself, 
	 * and the ready queue in case processes need to be removed from memory 
	 * (end or ready queue processes are removed first).	
	 * If the process is larger than the last free block of Memory compaction is run
	 * and then the process is added. */
	public void addProcess(Node process, Queue ready){
		int size = process.getMemory();
		int removed_counter=0;
		int loop = 1;
		int freeSpace=mem.length-(nextFree+1);
		while (freeSpace<size){
			if (loop!=1){
				Node temp1=ready.front();
				for(int i=1; i<=ready.getCount()-(removed_counter+1); i++){
					temp1=temp1.getNext();
				}
				deleteProcess(temp1);
				removed_counter+=1;
			}
			compact();
			loop+=1;
			freeSpace=mem.length-(nextFree+1);
		}
		for (int i=1; i<=size; i++){
			mem[nextFree+i] = process.getName();
		}
		process.setinMem(true);
		nextFree+=size;
		count+=1;
	}
	
	/* removes a process from memory by searching for the name of process in memory and setting the space
	 * to empty.*/
	public void deleteProcess(Node process){
		String name=process.getName();
		for(int i=0; i<mem.length; i++){
			if (mem[i]==name){
				mem[i]=null;
			}
		}
		count+=1;
		process.setinMem(false);
	}
	
	/*Compaction removes all the empty space between processes in Memory created from external
	 * fragmentation. Afterwards processes are removed from memory as needed to make room for an
	 * incoming process*/
	private void compact(){
		String[] temp = new String[mem.length];
		int new_count=0;
		for(int i=0; i<mem.length; i++){
			if (mem[i]!=null){
				temp[new_count]=mem[i];
				new_count+=1;
			}	
		}
		mem=temp;
		nextFree=new_count;
	}
	
	//returns number of processes in memory 
	public int getCount(){
		return count;
	}
}
