
import java.util.LinkedList;
import java.util.Set;

/**
 * sudoku solver uses forward checking set constraints of vertial, horizontal
 * and subgrid cells when a variable is assigned value
 * 
 * @author yuhu
 *
 */
public class fwdCheckingSolve extends Solve {
	LinkedList<Cell> unassignedCells = new LinkedList<Cell>();

	public fwdCheckingSolve(int[][] p) {
		super(p);
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (grid[r][c].getValue() == 0) {
					unassignedCells.add(grid[r][c]);
				} else {
					setConstraint(grid[r][c], grid[r][c].getValue());
				}
			}
		}

	}

	/**
	 * remove cell from the Empty cell set
	 * 
	 * @param c
	 *            the cell that need to be removed
	 */
	public void removeEmptyCell() {
		unassignedCells.removeFirst();
	}

	/**
	 * add cell to the empty cell
	 * 
	 * @param c
	 *            the cell that need to be added
	 */
	public void addCell(Cell c) {
		if (!c.preFilled())
			unassignedCells.addFirst(c);
	}

	

	/**
	 * get the next empty cell
	 * @return return the next empty cell
	 */
	public Cell getNextEmptyCell() {
		if (unassignedCells.isEmpty()) {
			return null;
		}
		return unassignedCells.getFirst();
	}

	/**
	 * Forward checking Assign values to empty square in the order of rows
	 *
	 * @param r
	 *            row
	 * @param c
	 *            col
	 * @return return true if the puzzle can be solved
	 */
	public boolean solve() {
		expandedNodes++;
		Cell currentCell = getNextEmptyCell();

		if (currentCell == null) {
			return true;
		}

		// select value from 1 to 9
		Set<Integer> currentCellConstraint = currentCell.getConstraint();
		for (int num = 1; num <= 9; num++) {
			// if the number is not constrained
			if (!currentCellConstraint.contains(num)) {

				// keep the previous value
				int prevVal = currentCell.getValue();
				// set new value
				currentCell.setValue(num);

				Set<Cell> modified = setConstraint(currentCell, num);
				java.util.Iterator<Cell> iterator = modified.iterator();
				removeEmptyCell();
				// valid check if value is assigned, no cell has empty domain
				boolean valid = true;
				while (iterator.hasNext()) {
					Cell temp = iterator.next();
					if (temp.emptyDomain()) {
						valid = false;
						break;
					}
				}
				// if the value can be assigned, recursion
				if (valid && this.solve()) {
					return true;
				}
				// restore
				addCell(currentCell);
				removeConstraint(modified, num);
				currentCell.setValue(prevVal);
			}
		}
		backTrackNum++;
		// if the choices is running out, the puzzle is not solvable, backtrack
		return false;

	}

}
