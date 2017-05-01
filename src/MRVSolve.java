import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * backtracking using both forward checking and minimum remaining value
 * @author yuhu
 *
 */
public class MRVSolve extends Solve {
	Set<Cell> unassignedSquares = new HashSet<Cell>();

	public MRVSolve(int[][] p) {
		super(p);
		// TODO Auto-generated constructor stub
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (grid[r][c].getValue()== 0) {
					unassignedSquares.add(grid[r][c]);
				} else {
					setConstraint(grid[r][c], grid[r][c].getValue());
				}
			}
		}
	}

	/**
	 * get the next MRV square
	 * 
	 * @return return the next MRV square
	 */
	public Cell getNextEmptySquare() {
		if (unassignedSquares.isEmpty()) {
			return null;
		} else {
			return Collections.max(unassignedSquares);

		}
	}

	/**
	 * remove a cell from the emptyCell set
	 * 
	 * @param s
	 *            the cell that need to be removed
	 */
	public void removeEmptyCell(Cell s) {
		unassignedSquares.remove(s);
	}

	/**
	 * add a cell back to the emptyCell set
	 * 
	 * @param s
	 *            the cell that need to be added
	 */
	public void addCell(Cell s) {
		unassignedSquares.add(s);
	}

	/**
	 * MRV checking Assign values to empty square in the order of rows
	 * 
	 * @param r row
	 * @param c col
	 * @return return true if the puzzle can be solved
	 */
	public boolean solve() {
		expandedNodes++;
		Cell currentCell = getNextEmptySquare();
		if (currentCell == null)
			return true;

		// select value from 1 to 9
		Set<Integer> currentCellConstraint = currentCell.getConstraint();
		for (int num = 1; num <= 9; num++) {
			//if the number is not constrained
			if (!currentCellConstraint.contains(num)) {
				
				//if(checkValue(currentCell.getRow(), currentCell.getCol(), num)){
				//keep the previous value
				int prevVal = currentCell.getValue();
				//set new value
				currentCell.setValue(num);
				//remove the current square from the emptyCell set
				
				Set<Cell> modified = setConstraint(currentCell, num);
				java.util.Iterator<Cell> iterator = modified.iterator();
				
				//valid check if value is assigned, no cell has empty domain
				boolean valid = true;
				while (iterator.hasNext()) {
					Cell temp = iterator.next();
					if (temp.emptyDomain()) {
						valid = false;
						break;
					}
				}
				if(valid){
					removeEmptyCell(currentCell);
					if(solve()){
						return true;
					}else{
						addCell(currentCell);
					}
				}
				
				//restore 
				
				removeConstraint(modified, num);
				currentCell.setValue(prevVal);
			}
		//	}
		}
		backTrackNum++;
		// if the choices is running out, the puzzle is not solvable, backtrack
		return false;

	}

}
