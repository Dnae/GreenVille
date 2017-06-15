import java.util.Arrays;

public class Tree extends Structure{

	private static final long serialVersionUID = 923422847L;

	public Tree(){
		
		index = 1;

		fillGrid();
	}
	
	public void specialAction(){
		setChanged();
		notifyObservers();
	}

	@Override
	public void fillGrid() {
		
		grid = new boolean[3][6];
		for(boolean[] b : grid){
			Arrays.fill(b, false);
		}
		grid[1][5] = true;
	}

}
