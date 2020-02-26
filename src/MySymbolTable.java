// your implementation of SymbolTable interface
// header comments here
import java.util.HashMap;
/**************************************************************************
 * @author Jimmy Botero
 * CS310 Spring 2018
 * Project 4
 * George Mason University
 * 
 * File Name: MySymbolTable.java
 *
 * Description:  Maintains the table for unique symbols (addresses) from a sequence of references.
 * In the pair (SymbolType, RecordType) SymbolType represents different symbols and RecordType defines
 * the record of each symbol (address), in our case the last access time. 
 * 
 ***************************************************************************/
public class MySymbolTable<SymbolType, RecordType> implements SymbolTable<SymbolType, RecordType>{

	// you decide the internal design of your class:
	//  - it must implement the provided SymbolTable interface
	//  - it cannot have any other public members (attributes or method) other than
	//    the public constructor and the methods defined in SymbolTable interface
	int size;
	HashMap<SymbolType, RecordType> tableMap;
	
	/**
	 * Constructor method for MySymbolTable that initialises the table with a new hash map and size 0.
	 */
	public MySymbolTable(){
		size = 0;
		tableMap = new HashMap<SymbolType, RecordType>();
	}//MySymbolTable
	
	// count how many symbols present in table 
	// return the integer count
	//
	// Average O(1)
	/**
	 * Method that returns the size of the table.
	 * 
	 * @return int representing the size of the table.
	 */
	public int size() {
		return size;
	}//size
	
	// check whether the symbol s is present in table
	// return true if present
	// return false otherwise
	//
	// Average O(1)
	/**
	 * checks whether the specified symbol exists within the table.
	 * 
	 * @param s the symbol being searched for.
	 * @return True is returned if the symbol is found, false otherwise.
	 */
	public boolean hasSymbol(SymbolType s) {
		if(tableMap.containsKey(s)) {
			return true;
		}
		else return false;
	}//hasSymbol

	// find and return the record we store in table for symbol s
	// if symbol not present, return null
	//
	// Average O(1)
	/**
	 * Finds and return the record stored in the table for symbol s.
	 * 
	 * @param s The symbol being used to find the record
	 * @return the record linked to that symbol is returned if found, null otherwise.
	 */
	public RecordType getRecord(SymbolType s) {
		if(hasSymbol(s)) {
			return tableMap.get(s);
		}
		else return null;
	}//recordType
	
	// for symbol s, set the record of that symbol s as r
	// if s not present before, add s into table;
	// if s already in table, replace the record for s to be r
	// no return
	//
	// Average O(1)
	/**
	 * Sets the record of s to r. If s doesn't exist, then s is added into the table.
	 * 
	 * @param s symbol being altered or added with record r
	 * @param r record for symbol s
	 */
	public void putRecord(SymbolType s, RecordType r) {
		if(tableMap.containsKey(s)){
			tableMap.replace(s, r);
		}
		else {
			tableMap.put(s, r);
			size++;
		}
	}//putRecord
	
	// find and remove the entry of symbol s from the table
	// return the record we store in table for symbol s
	// if symbol not present, return null
	//
	// Average O(1)
	/**
	 * Finds are removes symbol s from the table, returning the record attached to it.
	 * 
	 * @param s Symbol being searched for
	 * @return The record linked to s, if found. Null is returned otherwise.
	 */
	public RecordType removeSymbol(SymbolType s) {
		if(tableMap.containsKey(s)) {
			size--;
			return tableMap.remove(s);
		}
		else return null;
	}//removeSymbol
	//------------------------------------
	// example test code... edit this as much as you want!
	public static void main(String[] args){
		MySymbolTable<String,Integer> table = new MySymbolTable<String,Integer>();
		
		if(table.size()==0 && !table.hasSymbol("a")) 	{
			System.out.println("Yay 1");
		}

		table.putRecord("a",new Integer(136));
		table.putRecord("b",new Integer(112));
		
		if(table.size()==2 && table.hasSymbol("a") && table.getRecord("b").equals(new Integer(112))) 	{
			System.out.println("Yay 2");
		}

		table.putRecord("b",new Integer(211));
		Integer v = table.removeSymbol("a");
		if(table.size()==1 && v.equals(new Integer(136)) && !table.hasSymbol("a") 
			&& table.getRecord("b").equals(new Integer(211))) 	{
			System.out.println("Yay 3");
		}
	
	}


}


