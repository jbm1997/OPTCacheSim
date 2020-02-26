import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;
import java.io.PrintWriter;
import java.io.FileWriter;
/**************************************************************************
 * @author Jimmy Botero
 * CS310 Spring 2018
 * Project 4
 * George Mason University
 * 
 * File Name: StackDistCollector.java
 *
 * Description:  Class that calculates the (LRU) stack distance for any given sequence of accesses
 * 
 ***************************************************************************/
// class to calculate (LRU) stack distance for any given sequence of accesses
public class StackDistCollector{

	// process one access and calculate the LRU stack distance for that access
	// - if the address has never been accessed before, the distance is infinity  
	// - otherwise the distance is how many distinct addresses have been accessed 
	//    since the address was accessed last time (including itself)
	//
	// return the calculated stack distance
	// if the distance is infinity, return null
	// 
	// Hint: get the help from symbolTable and pastAccesses
	// Note: also need to perform maintenance tasks before return, including:
	// 	1. update symbolTable 
	//  2. update pastAccesses 
	//  3. advance time to the next cycle
	//
	// symbolTable:
	//   - must have one entry for every address that have been accessed
	//   - must store the lastest access time for that address in the table
	//
	// pastAccesses:
	//   - also store the lastest access time for every distinct address 
	//   - store them as an ordered collection and support efficient operation
	//
	// time:
	//   - indicate which cycle the address is accessed
	//   - move to the next cycle for a new reference
	/**
	 * Method that calculates the LRU stack distance for a particular address. If the address has never been accessed before, then its LRU
	 * stack distance is infinity.
	 * 
	 * @param address The address being accessed
	 * @return Integer representing the calculated stack distance, null is returned if the distance is infinity.
	 */
	public Integer access(int address){
		if(!symbolTable.hasSymbol(address)) {
			symbolTable.putRecord(address, new SymbolRecord(address, time));
			pastAccesses.insert(time);
			time++;
			return null;
		}
		int lastAccess = symbolTable.getRecord(address).getLastAccessTime();
		symbolTable.putRecord(address,  new SymbolRecord(address, time));
		pastAccesses.remove(lastAccess);
		pastAccesses.insert(time);
		time++;
		
		return pastAccesses.countNoSmallerThan(lastAccess);
	}


	// DO NOT MODIFY BELOW THIS LINE
    //-----------------------------------------------
    //-----------------------------------------------

	private int time; 
	// integer value to simulate clock cycle time for accesses
	// initialize as zero for the start of each sequence
	// advance by one for each access in the sequence


	MySymbolTable<Integer, SymbolRecord> symbolTable;  
	// symbol table to remember the distinct addresses we have accessed
	
	MySequence<Integer> pastAccesses; 
	// sequence to remember the time of distinct accesses in the past (before this.time)
	
	Stack<Integer> distSequence;  
	// storage of the calculated distances for each access in the sequence up to this.time
	// storage organized as a stack (FILO)
	
	// constructor
	public StackDistCollector(){
		time = 0;
		symbolTable = new MySymbolTable<Integer, SymbolRecord>();
		pastAccesses = new MySequence<Integer>();
		distSequence = new Stack<Integer>();
		
	}
	
	// accessor
	public int getTime(){
		return time;
	}
	
	// collect the stack distances for a new sequence
	// input: a plain text file with a sequence of integer numbers separated by space
	// output: another plain text file with the calculated sequence of stack distances
	//         in REVERSE order (again a sequence of integer numbers separated by space)
	// 
	public void processSequence(String inFileName, String outFileName) throws IOException{
		// reset everything to start over
		time = 0;
		pastAccesses = new MySequence<Integer>();
		symbolTable = new MySymbolTable<Integer, SymbolRecord>();
		distSequence = new Stack<Integer>();

		// open the input file
		Scanner input = new Scanner(new File(inFileName));
		
		// process accesses one by one and push the distance calculated onto a stack
		while(input.hasNext()) {
			int address = Integer.parseInt(input.next());
			// add it to distance stack
			distSequence.push(access(address));
		}
	
		// output distances into the outFile
		PrintWriter out = new PrintWriter(new FileWriter(outFileName));
		while (!distSequence.isEmpty()){
			Integer dist = distSequence.pop();
			if (dist!=null)
				out.print(dist.toString()+" ");
			else
				out.print("inf ");
		}
		out.close();
	}
	
	
	
	public static void main(String[] args) throws IOException{
	
		StackDistCollector collector = new StackDistCollector();
		
		if(collector.access(20)==null && collector.symbolTable.size() == 1
			&& collector.pastAccesses.contains(0)){
			System.out.println("Yay 1");
		}

		if(collector.access(20)==1 && collector.symbolTable.getRecord(20).getLastAccessTime() == 1
			&& collector.pastAccesses.contains(1) && !collector.pastAccesses.contains(0)){
			System.out.println("Yay 2");
		}

		if(collector.access(32)==null && collector.access(20)==2 && collector.access(20)==1
			&& collector.access(20)==1 && collector.access(32)==2){
			System.out.println("Yay 3");
		}
		
		if(collector.getTime() == 7 && collector.pastAccesses.toStringAscendingOrder().equals("5 6 ") ){
			System.out.println("Yay 4");
		}
		
		collector.processSequence("inputs/input1_reverse.txt","inputs/input1_my_distance.txt");
		// you can manually inspect the output file: inputs/input1_my_distance.txt
		// expected output should be identical to inputs/input1_distance.txt
		
		
	}
	
	
}

/**
*  
*  Class for record we want to keep for every symbol (address) from the access sequence
*
*  used in symbol table construction
*/
class SymbolRecord{
	private int address;  		//address of the symbol (as integer)
	private int lastAccessTime; //clock cycle of the latest access to the symbol
	
	// constructor
	public SymbolRecord(int address, int time){
		this.address = address;
		this.lastAccessTime = time;
	}
	
	// accessors
	public int getAddress(){
		return this.address;
	}
	
	public int getLastAccessTime(){
		return this.lastAccessTime;
	}
	
	// mutator for lastAccessTime
	public void setLastAccessTime(int newTime){
		this.lastAccessTime = newTime;
	}
	
}