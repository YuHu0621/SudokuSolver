/**
 * simpleSolve is the basic backtracking method
 * Backtracking method uses the Psuedo code provided by Russell&Norvig
 * @author yuhu
 *
 */
public class SimpleSolve extends Solve{

	/**
	 * constructor
	 * @param p
	 */
	public SimpleSolve(int[][] p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	/**
	 * solve the sudoku
	 * @return return true if the sudoku can be solved, return false if fails
	 */
	public boolean solve(){
		return Solver(0,0);
	}
	

	
	/**
	 * solve the sudoku recursively
	 * helper method for solver
	 * @param r row
	 * @param c col
	 * @return return true if the sudoku can be solved, return false if fails
	 */
	public boolean Solver(int r, int c){
		expandedNodes ++;
		//if the current square is prefilled, go to the next square and call recursionSolver
		if(grid[r][c].preFilled()){
			
			if(r<8) //row is less than 8, don't need to change column
				r++;	
			else //r is 8, change to the next colum, unless it's already the last one
			{
				if(c<8) 
				{
					r = 0;
					c++;
				}
				else // the last cell is prefilled, return true
				{
					return true;
				}
			}
			return Solver(r,c);
		}
		else //the square is not preFilled
		{
			//select value from 1 to 9
			int num = 1;
			while(num <10){
				//check if the value is valid to put into the square based on vert and horizontal and subSquare
				if(checkValue(r,c,num)){
					//the value can be put in, so seet the value
					grid[r][c].setValue(num);
					if(r < 8){
						int nextRow = r+1;
						//check if the next square return true
						if(Solver(nextRow,c))
							return true;
					}else{
						if(c<8){
							int nextRow = 0;
							int nextCol = c+1;
							//check if the next square return true
							if(Solver(nextRow,nextCol))
								return true;
							
						}else{
							//if it's the last cell, and a value can be set, then return true
							return true;
						}
					}
					
				//the recursionSolver of the next Square returns false, so this Square value is restored to 0
				grid[r][c].setValue(0);
				
				}
				num ++;
			}
			
			backTrackNum++;
			//if the choices is running out, the puzzle is not solvable
			return false;
			}
		}

}
