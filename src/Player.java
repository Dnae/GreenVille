import java.io.Serializable;

public class Player extends VillaCharacter implements Serializable{

	private static final long serialVersionUID = 655457657L;
	
	public Player(String gender, String size, String name){
		
		this.gender = gender;
		this.species = "human";
		this.size = size;
		this.variation = 0;
		this.name = name;
		this.clothing = 0;
		
	}

}
