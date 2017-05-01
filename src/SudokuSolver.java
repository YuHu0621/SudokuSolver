import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * SudokuSolver contains the main method
 * call from the terminal, args[0] has to be the filename, args[1] is the method
 * if args[1] is not assigned value, call basic backtracking method, otherwise, call FC or MRV FC depends on the call
 * @author yuhu
 *
 */
public class SudokuSolver {
	

	private static final long MEGABYTE = 1024L * 1024L;
	
	/**
	 * file parser method, read the file.txt and turn it into an int[][] 
	 * 
	 * @param filename file name
	 * @return return the grid
	 * @throws IOException file not found exception will be thrown if the file cannot be found
	 */
	public static int[][] getGrid(String filename) throws IOException{
		
		String str = "";
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        line = br.readLine();
		    }
		     str = sb.toString();
		} finally {
		    br.close();
		}
		return gridBuilder(str);
	}
	
	/**
	 * helper method of getGrid(String filename)
	 * grid builder turn string into a 2d int array, trimming all the spaces
	 * @param str input string
	 * @return return 2d int array
	 */
	public static int[][] gridBuilder(String str){
		int[][] grid = new int[9][9];
		char[] numArray = str.toCharArray();
		
		int row = 0;
		int col = 0;
		int j = 0;
		while(row <9 && col < 9){
			if(numArray[j]>= 48 && numArray[j]<=57){
				grid[row][col] = Character.getNumericValue(numArray[j]);
				if(col < 8){
					col ++;
				}else{
					col = 0;
					row ++;
				}
			}
			j++;
			
			
		}
		return grid;
	}
	
	/**
	 * main method
	 * use FC, MRV or BT to solve sudoku, calculate time and memory.
	 * @param args the first args is the filename, the second args is the method type
	 * 
	 * @throws IOException IOException will be thrown if the file cannot be found
	 */
	public static void main(String[] args) throws IOException {
		
		String filename = null;
		if(args.length > 0) 
			filename = args[0];
		
	
		int[][] grid = getGrid(filename);
		Solve myGrid = null;
		long totalStartTime = System.nanoTime();
		if(args.length == 1){
			myGrid = new SimpleSolve(grid);
		}else if (args.length >=2){
			if(args[1].equals("MRV")){
				myGrid = new MRVSolve(grid);
			}else if(args[1].equals("FC")){
				myGrid = new fwdCheckingSolve(grid);
			}else if(args[1].equals("LCV")){
				myGrid = new LCVSolve(grid);
			}
		}
		
		//before
		System.out.println(myGrid.toString());
		if(args.length >= 2){
			System.out.println("Method: " + args[1]);
		}else if (args.length == 1){
			System.out.println("Method: BT" );
		}
		
		//start solving
		long startTime = System.nanoTime();
		boolean solved = myGrid.solve();
		long endTime = System.nanoTime();
		
		//print
		System.out.println("Solved: "+ solved);
		System.out.println(myGrid.toString());
		System.out.println("total time duration: "+((endTime - totalStartTime)/1000000));
		System.out.println("Search time duration: "+((endTime - startTime)/1000000));
		System.out.println("Backtracking number: "+ myGrid.getBackTrackNum());
		System.out.println("expanded nodes number: "+ myGrid.getExpandedNodes());
		
		Runtime rt = Runtime.getRuntime();
		System.out.printf("Memory used: %.2f MB%n", (rt.totalMemory() - rt.freeMemory())/(float)MEGABYTE);
		System.out.println("effective branching factor: " + Math.pow(myGrid.getExpandedNodes(), (double)(1.0/(double)myGrid.getDoG())));
	
	}
}
