
public class lcvNum implements Comparable<lcvNum>{

	int value;
	int constraintNum;
	public lcvNum(int v, int c){
		value = v;
		constraintNum = c;
	}
	@Override
	public int compareTo(lcvNum o) {
		// TODO Auto-generated method stub
		if(o.constraintNum > constraintNum){
			return -1;
		}else if(o.constraintNum < constraintNum){
			return 1;
		}else{
			return 0;
		}
	}
	
	public int getConstraint(){
		return constraintNum;
	}

	public int getIndex(){
		return value;
	}
}
