// your implementation of Sequence interface
// header comments here
import java.util.TreeSet;
/**************************************************************************
 * @author Jimmy Botero
 * CS310 Spring 2018
 * Project 4
 * George Mason University
 * 
 * File Name: MySequence.java
 *
 * Description:  Stores a collection of values as a sorted sequence with no duplicates. Supports inserting
 * and removing values, checking if a value is contained inside the sequence, checking the size of the sequence,
 * creating a string representation of all values in ascending order, and counting all the values greater than or
 * equal to a specified value.
 * 
 ***************************************************************************/
public class MySequence<T extends Comparable<T>> implements Sequence<T>{

	// you decide the internal design of your class:
	//  - it must implement the provided Sequence interface
	//  - it cannot have any other public members (attributes or method) other than
	//    the public constructor and the methods defined in Sequence interface
	TreeSet<T> mySet;
	int size;
	
	/**
	 * Constructor for MySequence. Initialises a new TreeSet and sets the size to 0
	 */
	public MySequence(){
		mySet = new TreeSet<T>();
		size = 0;
	}//MySequence
	
	// add a new value v into the collection
	// return true if added successfully
	// return false if value is already present (no duplicate added)
	//
	// O(logN) given N as the number of values stored in sequence
	/**
	 * Method that inserts a new value into the collection.
	 * 
	 * @param v Value being inserted
	 * @return True is returned if the value is added, false if it's already present.
	 */
	public boolean insert (T v){
		if(mySet.contains(v)){
			return false;
		}
		else{
			mySet.add(v);
			size++;
			return true;
		}
	}//insert
	
	// delete value v from the collection
	// return true if removed successfully
	// return false if value is not present
	//
	// O(logN) given N as the number of values stored in sequence
	/**
	 * Method that removes a value from the collection.
	 * 
	 * @param v Value being removed
	 * @return True is returned if the value is removed, false if it isn't present.
	 */
	public boolean remove (T v){
		if(!mySet.contains(v)){
			return false;
		}
		else{
			mySet.remove(v);
			size--;
			return true;
		}
	}//remove


	// find whether value v is present in collection
	// return true if value is present
	// return false otherwise
	//
	// O(logN) given N as the number of values stored in sequence
	/**
	 * Checks if a specific value is present in the collection.
	 * 
	 * @param v Value being searched for.
	 * @return True is returned if found, false otherwise.
	 */
	public boolean contains (T v){
		if(mySet.contains(v)){
			return true;
		}
		else return false;
	}//contains


	// count how many values in collection are greater than or equal to v
	// return the integer count
	//
	//  Note: T must implement .compareTo(T another) to define how to determine 
	//        which one is greater than the other one between any two values of type T
	//
	// O(logN) given N as the number of values stored in sequence
	/**
	 * Counts how many values in the collection are greater than or equal to v
	 * 
	 * @param v the value all others are being compared to
	 * @return int representing the number of values greater than or equal to v.
	 */
	public int countNoSmallerThan (T v){
		int tailSize = mySet.tailSet(v, true).size();
		
		return tailSize;
	}//countNoSmallerThan
	
	// count how many values present in collection 
	// return the integer count
	//
	// O(1)
	/**
	 * Method that returns the size of the collection
	 * 
	 * @return int representing the size of the collection.
	 */
	public int size(){
		return size;
	}//size

	// return a string representation of all values in collection, in ascending order
	// - exactly one space should be appended after every value (including the last value)
	//
	// O(N) given N as the number of values stored in sequence
	/**
	 * Returns a string representation of all values in collection, in ascending order
	 * 
	 * @return Properly formatted string representation of the collection.
	 */
	public String toStringAscendingOrder(){
		if(mySet.toString().equals("[]")){
			return "";
		}
		else{
			return mySet.toString().replace("[", "").replace("]", "").replaceAll(",", "") + " ";
		}
	}//toStringAscendingOrder


	//------------------------------------
	// example test code... edit this as much as you want!
	public static void main(String[] args){
		MySequence<Integer> seq = new MySequence<Integer>();
		
		if(seq.size()==0 && !seq.contains(11) && seq.countNoSmallerThan(10) == 0 
			&& seq.toStringAscendingOrder().equals("")) 	{
			System.out.println("Yay 1");
		}
		
		seq.insert(11);
		seq.insert(5);
		
		if(seq.insert(200) && seq.size()==3 && seq.contains(11) 
			&& seq.countNoSmallerThan(10) == 2 && !seq.remove(20)) 	{
			System.out.println("Yay 2");
		}
		
		seq.insert(112);
		seq.insert(50);
		seq.insert(20);
		
		if(seq.remove(20) && !seq.contains(20) && !seq.insert(200)
			&& seq.countNoSmallerThan(50) == 3 
			&& seq.toStringAscendingOrder().equals("5 11 50 112 200 ")) 	{
			System.out.println("Yay 3");
		}

	}
}


