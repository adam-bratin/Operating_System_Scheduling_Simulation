// Adam Bratin
//04/15/13
//Program 3 - Queues (variable)

public class Queue{
	//creates Queue Linked List and counter variables
	private Node front;
	private Node tail;
	private int count;

	//initializes stacks Linked List
	public Queue(){
	}

	//returns number of Nodes in queue
	public int getCount(){
		return count;	
	}

	//checks the count variable to determine if stack is empty
	public boolean isEmpty(){
		return count==0;
	}

	//removes and returns top of queue
	public Node dequeue(){
		Node temp=front;
		front = front.getNext();
		temp.setNext(null);
		count-=1;
		return temp;
	} 

	//adds node to end of queue
	public void enqueue(Node inNode){
		if (front==null){
			front=inNode;
			tail=front;
			count+=1;
		}
		else{
			tail.setNext(inNode);
			tail=tail.getNext();
			count+=1;
		}
	}

	//returns front of queue
	public Node front(){
		return front;
	}
	
	//returns end of queue
	public Node tail(){
		return tail;
	}
	

	// printQueue method for QueueLL
  public String printQueue() {
		String data="";
		Node temp = front;
    for(int i = 0; i < count; i++){
      System.out.print(temp.getName()+" ");
      data+=(temp.getName() + " ");
			temp = temp.getNext();
		}
		return data;      
  }
}