import java.util.Arrays;

public class Statue extends Structure{

	private static final long serialVersionUID = 436845636L;

	public Statue(){
		
		index = 0;

		fillGrid();
	}
	
	public void specialAction(){
		SystemVariables.getVillaNova().showSave();
	}

	@Override
	public void fillGrid() {
		grid = new boolean[1][3];
		for(boolean[] b : grid){
			Arrays.fill(b, false);
		}
		grid[0][2] = true;
	}

}
