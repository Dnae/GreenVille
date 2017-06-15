import java.io.Serializable;

public abstract class Location implements Serializable{

	private static final long serialVersionUID = 192837465L;
	
	Tile[][] ground;
	Structure[][] structures;
	
	public Tile[][] getGround(){
		return ground;
	}
	
	public Structure[][] getStructures(){
		return structures;
	}	
	
}
