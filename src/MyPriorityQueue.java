// your implementation of PriorityQueue interface
// header comments here

/**************************************************************************
 * @author Jimmy Botero
 * CS310 Spring 2018
 * Project 4
 * George Mason University
 * 
 * File Name: MyPriorityQueue.java
 *
 * Description: Class that implements a Priority Queue: The class maintains 
 * a max priority queue where the priority of a parent node is higher than the priority of its children.
 * 
 ***************************************************************************/

// One additional requirement is the value type T must be Comparable.
// When two items are compared to determine the order in priority queue, follow the rules below:
//  1. use the priority of them to determine the order 
//  2. if they are of the same priority, use their values (of type T) to determine the order
//  3. if they are of the same priority and same value, any order is fine

public class MyPriorityQueue<T extends Comparable<T>> implements PriorityQueue<T>
{
	
	// you decide the internal design of your class:
	//  - it must implement the provided PriorityQueue interface
	//  - it cannot have any other public members (attributes or method) other than
	//    the public constructor and the methods defined in PriorityQueue interface
	/**
	 * @param size the size of the priority queue
	 * @param pQueue internal Object array keeping track of the specific values
	 * @param priorities internal int array keeping track of the priorities for each value
	 */
	private int size;
	private Object[] pQueue;
	private int[] priorities;


	static final int INF = Integer.MAX_VALUE; // the max priority to use: infinity
    
	
	/**
     * Construct an empty PriorityQueue.
     */
    public MyPriorityQueue()
    {
    	size = 0;
    	pQueue = new Object[11];
    	priorities = new int[11];
    }//MyPriorityQueue
    
	// count how many items present in priority queue
	// return the integer count
	//
	// O(1)
    /**
     * Returns the size of the priority queue
     * 
     * @return int value representing size.
     */
    public int size() {
    	return size;
    }//size
    
	// return the top item (of the max priority) in the priority queue
	// return null if empty
	//
	// O(1)
    /**
     * Method that allows the user to look at the item with the max priority in the priority queue
     * 
     * @return Returns the item of max priority if it exists, null otherwise.
     */
    @SuppressWarnings("unchecked")
	public T peek() {
    	if(pQueue[1] == null) {
    		return null;
    	}
    	else return (T)pQueue[1];
    }//peek
    
	// remove the top item (of the max priority) from the priority queue
	// return that item to be deleted
	// return null if empty
	//
	// O(logN): N is the number of items in priority queue
    /**
     * Method that removes the item with the max priority from the priority queue.
     * 
     * @return Returns the max priority item if it exists, null otherwise.
     */
    @SuppressWarnings("unchecked")
	public T remove() {
    	T maxElement = (T)pQueue[1];//holds our max element
    	
    	if(maxElement == null) {
    		return null;
    	}//we know that there will be no max to remove if maxElement is null after being initialised
    	
    	T temp;
    	int priorityTemp;
    	
    	pQueue[1] = pQueue[size];
    	priorities[1] = priorities[size];
    	pQueue[size] = null;
    	priorities[size] = 0;
    	
    	for(int i = 1; i < size;) {//keeps looping until the end of the array is reached
    		if(priorities[i * 2] == 0 && priorities[i * 2 + 1] == 0) {
    			size--;
    			return maxElement;
    		}
    		else if(priorities[i * 2] >= priorities[i * 2 + 1]) {//swaps elements with the first child, if greater than or equal to the second child
    			temp = (T)pQueue[i];
    			priorityTemp = priorities[i];
    			
    			pQueue[i] = pQueue[i * 2];
    			priorities[i] = priorities[i * 2];
    			
    			pQueue[i * 2] = temp;
    			priorities[i * 2] = priorityTemp;
    			i *= 2;//i is set to i * 2 because we're simulating a binary tree, where this would be pQueue[i]'s first child
    		}
    		else {//swaps elements with the second child, since it has to be greater if the first check failed
    			temp = (T)pQueue[i];
    			priorityTemp = priorities[i];
    			
    			pQueue[i] = pQueue[i * 2 + 1];
    			priorities[i] = priorities[i * 2 + 1];
    			
    			pQueue[i * 2 + 1] = temp;
    			priorities[i * 2 + 1] = priorityTemp;
    			i *= 2 + 1;//i is set to i * 2 + 1 because we're simulating a binary tree, where this would be pQueue[i]'s second child
    		}
    	}
    	size--;
    	return maxElement;
    }//remove
    
	// add an item v with priority p into the priority queue
	// no checking whether a duplicate value is already in queue
	// return: nothing
	// 
	// O(logN): N is the number of items in priority queue
    /**
     * Adds an item v with priority p into the priority queue
     * 
     * @param v Item being added into the priority queue
     * @param p priority of the item being added
     */
    @SuppressWarnings("unchecked")
	public void insert(T v, int p) {
    	if(pQueue.length - 1 == size()) {//expands the arrays when necessary
        	Object[] temp = new Object[pQueue.length * 2];
        	int[] priorityTemp = new int[temp.length];
			
			for(int i = 1; i < pQueue.length; i++) {
				temp[i] = pQueue[i];
				priorityTemp[i] = priorities[i];
			}
			pQueue = temp;
    	}
    	
    	T temp;
    	int priorityTemp;
    	pQueue[size + 1] = v;
    	priorities[size + 1] = p;
    	size++;
    	
    	for(int i = size; i > 0; i /= 2) {//checks if the parent nodes are of greater priority
			temp = (T)pQueue[i];
			priorityTemp = priorities[i];
			
    		if(priorities[i] >= priorities[i / 2] && priorities[i / 2] != 0) {
    			pQueue[i] = pQueue[i / 2];
    			priorities[i] = priorities[i / 2];
    			
    			pQueue[i / 2] = temp;
    			priorities[i / 2] = priorityTemp;
    		}
    		else break;
    	}
    }//insert
    
	// perform a priority update for items in the priority queue based on the following rules
	//  - If the item is the same as v: set the priority as p
	//  - If the item is different from v, compare their priorities
	//      - For any item x with a priority <= v's priority, decrement x's priority by 1
	//      - Otherwise, do not change x's priority
	// 
	// Hint: perform necessary adjustment to ensure a valid priority queue after updating
	//
	// O(N): N is the number of items in priority queue
    /**
     * Updates the priorities for all the items in the priority queue. If the item is the same as v then 
     * the priority is set to p. If the item is different from v, then for any item x with a priority greater than or equal to
     * v's priority x's priority is decreased by 1, otherwise x's priority isn't changed.
     * 
     * @param v item which all other items in the priority queue are being compared to
     * @param p the priority of item v
     */
	public void updatePriority(T v, int p) {
		for(int i = 1; i <= size; i++) {
			if(pQueue[i].equals(v)) {
				priorities[i] = p;
			}
			else if(priorities[i] <= p) {
				priorities[i]--;
			}
		}
		maintainQueue();
	}//updatePriority
	
	/**
	 * Private method that helps make sure that the priority queue maintains its proper order.
	 * Compares the priorities of everything in the queue, swaps them if ones priority outranks the other.
	 */
	private void maintainQueue() {//this method makes sure that the queue is in the proper order
		for(int i = 1; i < size; i++) {
			if(priorities[i] < priorities[i + 1]) {//compares the priorities of everything in the queue, swaps them if one is bigger
				Object temp = pQueue[i];
				int tempPriority = priorities[i];
				
				pQueue[i] = pQueue[i + 1];
				priorities[i] = priorities[i + 1];
				
				pQueue[i + 1] = temp;
				priorities[i + 1] = tempPriority;
			}
		}
	}//maintainQueue
	
	// check whether there is a value v associated with priority p in the priority queue
	// return true if present
	// return false otherwise
	//
	// O(N): N is the number of items in priority queue
	/**
	 * Checks whether the value v with priority p exists within the priority queue.
	 * 
	 * @param v Value being searched for
	 * @param p Priority of the value being searched for
	 * 
	 * @return True if the value exists in the priority queue, false otherwise.
	 */
	public boolean contains(T v, int p) {
		for(int i = 1; i <= size; i ++) {
			if(priorities[i] == p && pQueue[i] == v) {
				return true;
			}
		}
		return false;
	}//contains
    
	// make sure you implement all methods in PriorityQueue interface

	//------------------------------------
	// example test code... edit this as much as you want!
	// note: you might want to add a method like printPQ() for debugging purpose
	
	public static void main(String[] args){
		MyPriorityQueue<String> pq = new MyPriorityQueue<String>();
		
		if(pq.size()==0 && pq.remove()==null && !pq.contains("a", 4)) 	{
			System.out.println("Yay 1");
		}
		
		pq.insert("a",4);
		pq.insert("b",10);
		pq.insert("h",2);
		
		if(pq.size()==3 && (pq.peek()).equals("b") && pq.contains("a", 4) && pq.contains("h", 2)
			&& pq.contains("b",10)) {
			System.out.println("Yay 2");
		}

		if((pq.remove()).equals("b") && !pq.contains("b",10) & pq.size()==2 
			&& (pq.peek().equals("a")) ) {
			System.out.println("Yay 3");
		}

		pq.insert("d",4);
		if ((pq.peek()).equals("d")) {System.out.println("Yay 4");}
				
		pq.insert("b",10);
		pq.insert("f",3);
		pq.updatePriority("a",3);
		if (pq.size() == 5 && pq.contains("a",3) && pq.contains("b",10) && pq.contains("d",4) 
			&& pq.contains("f",2) && pq.contains("h",1)) {
			System.out.println("Yay 5");
		}


	}


}





