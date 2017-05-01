
import java.util.Collections;

import java.util.HashSet;

import java.util.Set;


/**
 * LCVSolver is an extended version of MRVSolve that also check the least constraining value to be assigned to a variable
 * @author yuhu
 *
 */
public class LCVSolve extends MRVSolve{
	
	public LCVSolve(int[][] p) {
		super(p);
	}



	/**
	 * get the least constratined value to be assigned to the variable
	 * the value will have the least affect on the constraint of its related cell
	 * in other words, the total constraint values will be the smallest
	 * @param c
	 * @return
	 */
	public int[] getLCV(Cell c){
		HashSet<lcvNum> constraintValue = new HashSet<lcvNum>();
	
		for(int i = 1; i < 10; i ++){
			if(!c.getConstraint().contains(i)){
				int constraintNum = countConstraintCells(c, i);
				constraintValue.add(new lcvNum(i,constraintNum));
			}
		}
		
		int[] lcv = new int[constraintValue.size()];
		int index = 0;
		while(!constraintValue.isEmpty()){
			lcvNum temp = Collections.min(constraintValue);
			lcv[index]= temp.getIndex();
			constraintValue.remove(temp);
			index ++;
		}
		
		
		return lcv;
	}
	/**
	 * count all the related cells' constraint size if value i is assigned to Cell c
	 * @param c Cell c
	 * @param i value i that is going to be assign to Cell c
	 * @return return the sum of constraint size if cell c is assigned value i
	 */
	public int countConstraintCells(Cell c, int i){
		int count = 0;
		int row = c.getRow();
		int col = c.getCol();
		for(int index = 0; index < 9; index++){
			if(index != row && !grid[index][col].preFilled()){
				if(grid[index][col].getConstraint().contains(i)){
					count += grid[index][col].getConstraint().size();
				}else{
					count += grid[index][col].getConstraint().size() + 1;
				}
			}
			if(index != col && !grid[row][index].preFilled()){
				if(grid[row][index].getConstraint().contains(i)){
					count += grid[row][index].getConstraint().size();
				}else{
					count += grid[row][index].getConstraint().size() + 1;
				}
			}
			int squareRow = row/3 * 3;
			int squareCol = col/3 * 3;
			for(int sR = squareRow; sR < squareRow + 3; sR++){
				for(int sC = squareCol; sC < squareCol + 3; sC++){
					if( sR!= row && sC != col && !grid[sR][sC].preFilled()){
						if(grid[sR][sC].getConstraint().contains(i)){
							count += grid[sR][sC].getConstraint().size();
						}else{
							count += grid[sR][sC].getConstraint().size()+1;
						}
					}
				}
			}
		}
		return count;
	}
	@Override
	/**
	 * solve uses backtracking, MRV and LCV strategies
	 * @return return true if the puzzle can be solved, return false if it cannot be solved
	 */
	public boolean solve() {
		expandedNodes++;
		Cell currentCell = getNextEmptySquare();
		if (currentCell == null)
			return true;

		// select value from 1 to 9
		Set<Integer> currentCellConstraint = currentCell.getConstraint();
		int[] lcv = getLCV(currentCell);
		for(int i = 0; i < lcv.length; i++){
			int num = lcv[i];
		
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
		
		}
		backTrackNum++;
		// if the choices is running out, the puzzle is not solvable, backtrack
		return false;

	}

	

}
