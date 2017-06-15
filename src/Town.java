import java.util.ArrayList;
import java.util.List;

public class Town extends Location{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 118472364758L;
	
	List<Villager> villagers = new ArrayList<Villager>();
	
	public Town(){
		ground = new Tile[100][100];
		structures = new Structure[100][100];
		drawNewGround();
		addNewStuff();
	}
	
	public Tile[][] getGround(){
		return ground;
	}
	
	private void drawNewGround(){
		
		//DRAW WATER
		for(int i = 0; i < ground.length; i++){
			for(int j = 0; j < ground[0].length; j++){
				ground[i][j] = new Tile(0);
			}
		}
		
		//DRAW GRASS
		for(int i = 15; i < ground.length - 15; i++){
			for(int j = 15; j < ground[0].length - 15; j++){
				ground[i][j] = new Tile(1);
			}
		}
		
	}

	private void addNewStuff(){

		//DRAW TREES :)
		for(int i = 20; i < ground.length - 20; i++){
			for(int j = 20; j < ground[0].length - 20; j++){
				if(SystemVariables.getRandomNum(20) == 0){
					Tree t = new Tree();
					addStructure(t, i, j);
				}
			}
		}

		Statue st = new Statue();
		addStructure(st, 20, 20);

	}
	
	public void addStructure(Structure str, int x, int y){
	
		structures[x][y] = str;
		
		boolean[][] grid = str.getGrid();
		
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[0].length; j++){
				if(grid[i][j]){
					ground[x + i][y + j].setStructure(str);
				}
			}
		}
	}
	
	public void addNewVillager(){
		villagers.add(new Villager());
	}

	public List<Villager> getVillagers(){
		return villagers;
	}
	
	public int getTownX(){
		return 100;
	}
	
	public int getTownY(){
		return 100;
	}
}
