import java.util.HashSet;


public class Cell implements Comparable<Cell>{

	private HashSet<Integer> constraint;
	private static int DEF = 0;
	private int value;
	private boolean preFilled = false;
	private int row;
	private int col;
	
	/**
	 * constructor of cell in the grid
	 * @param v value
	 * @param r row
	 * @param c col
	 */
	public Cell(int r, int c, int v){
		constraint = new HashSet<Integer>();
		value = v;
		row = r;
		col = c;
		if(v != DEF){
			preFilled = true;
		}
	}
	
	/**
	 * check if the square need to fill in value
	 * @return return true if the square need to fill in value
	 */
	public boolean preFilled(){
		return preFilled;
	}
	
	/**
	 * set the value of the Square
	 * @param v value
	 */
	public void setValue(int v){
		value = v;
	}
	
	/**
	 * get the value of the square
	 * @return return the value
	 */
	public int getValue(){
		return value;
	}
	
	
	/**
	 * get the set of constraint 
	 * @return return 
	 */
	public HashSet<Integer> getConstraint(){
		return constraint;
	}
	/**
	 * toString method
	 * @return return the value of the square
	 */
	public String toString(){
		String s = "value: "+ value + ".  constraint: " + constraint.toString();
		return s;
	}
	
	/**
	 * set constraint to the square
	 * @param v value
	 * @return return true if you can set the constraint, return false if the domain is empty after the new constraint is set
	 */
	public boolean setConstraint(int v){
		return constraint.add(new Integer(v));
		
	}
	
	public void removeConstraint(int v){
		constraint.remove(new Integer(v));
	}
	/**
	 * check if the cell has an empty domain
	 * @return
	 */
	public boolean emptyDomain(){
		if(constraint.size()<9)
			return false;
		return true;
	}



	/**
	 * compare the square and the square o based on constraint size
	 */
	@Override
	public int compareTo(Cell o) {
		if(constraint.size()> o.getConstraint().size()){
			return 1;
		}else if(constraint.size()< o.getConstraint().size()){
			return -1;
		}else{
			return 0;
		}
	}

	/**
	 * get row of the square
	 * @return return row num
	 */
	public int getRow(){
		return row;
	}
	
	/**
	 * get col of the square
	 * @return return col num
	 */
	public int getCol(){
		return col;
	}
}
