import java.io.Serializable;
import java.util.Observable;

public abstract class Structure extends Observable implements Serializable{

	private static final long serialVersionUID = 234682365482L;
	
	int index;
	boolean[][] grid;
	
	public StructurePanel getPanel(int panelsizex, int panelsizey){		
		return new StructurePanel(grid.length * panelsizex, grid[0].length * panelsizey, this);
	}
	
	public abstract void specialAction();
	
	public int getIndex(){
		return index;
	}
	
	public boolean[][] getGrid(){
		return grid;
	}

	public abstract void fillGrid();
	
}