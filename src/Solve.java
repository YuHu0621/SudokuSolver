import java.util.HashSet;
import java.util.Set;

/**
 * super abstract class of all the backtracking methods
 * 
 * @author yuhu
 *
 */
public abstract class Solve {
	Cell[][] grid;
	protected int backTrackNum;
	protected int expandedNodes;
	protected int emptyNum = 0;
	/**
	 * constructor put the input array into a Cell array
	 * 
	 * @param p
	 */
	public Solve(int[][] p) {
		grid = new Cell[9][9];
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				grid[r][c] = new Cell(r, c, p[r][c]);
				if(!grid[r][c].preFilled()){
					emptyNum ++;
				}
			}
		}
		backTrackNum = 0;
		expandedNodes = 0;
	}

	/**
	 * abstract solve method that solve the sudoku grid
	 * 
	 * @return return true if the sudoku can be solved
	 */
	public abstract boolean solve();

	/**
	 * get the depth of the goal
	 * @return
	 */
	public int getDoG(){
		return emptyNum;
	}
	/**
	 * print the grid
	 */
	public String toString() {
		String str = "";
		for (int r = 0; r < 9; r++) {

			for (int c = 0; c < 9; c++) {
				str += grid[r][c].getValue() + " ";
			}
			str += "\n";
		}
		return str;
	}

	/**
	 * getter method get back track number
	 * 
	 * @return return backTrackNum
	 */
	public int getBackTrackNum() {
		return backTrackNum;
	}

	/**
	 * getter method get expanded node number
	 * 
	 * @return return expandedNodes
	 */
	public int getExpandedNodes() {
		return expandedNodes;
	}

	/**
	 * set the constraint value of its neighbours, Cell s itself is not included
	 * 
	 * @param r
	 *            row
	 * @param c
	 *            col
	 * @param v
	 *            value
	 * @return return the set of cell whose constraint set is modified after a
	 *         value is assigned to a variable
	 */
	public Set<Cell> setConstraint(Cell s, int v) {
		Set<Cell> modifiedSquare = new HashSet<Cell>();
		int row = s.getRow();
		int col = s.getCol();
		for(int i = 0; i < 9; i++)
		{
			if(i != row && !grid[i][col].preFilled()){
				if(grid[i][col].setConstraint(v)){
					modifiedSquare.add(grid[i][col]);
				}
			}
			if(i != col && !grid[row][i].preFilled()){
				if(grid[row][i].setConstraint(v)){
					modifiedSquare.add(grid[row][i]);
				}
			}
		}
		// sub square
		int squareRow = s.getRow() / 3 * 3;
		int squareCol = s.getCol() / 3 * 3;
		for (int sR = squareRow; sR < squareRow + 3; sR++) {
			for (int sC = squareCol; sC < squareCol + 3; sC++) {
				if (sR != row && sC != col && !grid[sR][sC].preFilled()) {
					if (grid[sR][sC].setConstraint(v))
						modifiedSquare.add(grid[sR][sC]);
				}
			}
		}
		return modifiedSquare;
	}

	/**
	 * Remove constraint v from the set of cell s
	 * 
	 * @param s
	 *            set of cell
	 * @param v
	 *            variable v
	 */
	public void removeConstraint(Set<Cell> s, int v) {
		if(s != null){
			java.util.Iterator<Cell> iterator = s.iterator();
			while (iterator.hasNext()) {
				Cell temp = iterator.next();
				temp.getConstraint().remove(new Integer(v));
			}
		}
	}
	
	/**
	 * check if the value can be assigned to the cell
	 * @param r row
	 * @param c col
	 * @param v value
	 * @return return true if the value can be put into the cell without violating the sudoku rule
	 */
	public boolean checkValue(int r, int c, int v){
		for(int i = 0; i < 9; i ++){
			if((i != r && grid[i][c].getValue()==v) || (c!= i && grid[r][i].getValue()== v)){
				return false;
			}
		}
		
		int squareRow = r/3 * 3;
		int squareCol = c/3 * 3;
		for(int sR = squareRow; sR < squareRow + 3; sR++){
			for(int sC = squareCol; sC < squareCol + 3; sC++){
				if( sR!= r && sC != c && grid[sR][sC].getValue()== v){
					return false;
				}
			}
		}
		return true;
		
	}

}
