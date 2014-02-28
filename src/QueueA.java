// Adam Bratin
//04/15/13
//Queues (Array)

public class QueueA{
	//creates Queue array and counter variables
	private Node[] queue;
	private int front;
	private int tail;
	private int size;
	private int count;

	//initializes stacks array to be length paramater inSize
	public QueueA(int inSize){
		queue= new Node[inSize];
		front=0;
		tail=0;
		size=inSize;
	}

	//checks the count variable to determine if stack is empty
	public boolean isEmpty(){
		return count==0;
	}
	
	public int getCount(){
		return count;
	}

	//removes and returns top of queue
	public Node dequeue(){
		Node temp=queue[front];
		front = (front+1)%size;
		count-=1;
		return temp;
	} 

	//adds node to end of queue
	public void enqueue(Node inNode){
		queue[tail]=inNode;
		count+=1;
		tail = (tail+1)%size;
	}

	//returns front of queue
	public Node front(){
		return queue[front];
	}
	
	//returns tail of queue
	public Node tail(){
		return queue[tail];
	}

	// printQueue method for QueueA
  public String printQueue() {
		String data="";
    for(int i = 0; i < count; i++){
			int index= (front+i)%size;
      System.out.print(queue[index].getName() + " ");
      data+=(queue[index].getName() + " ");
		}
		return data;         
  }
}
