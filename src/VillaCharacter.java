import java.io.Serializable;

public abstract class VillaCharacter implements Serializable{

	private static final long serialVersionUID = 9175927591645L;

	String name;
	String gender;
	String size;
	String species;
	int variation;
	int clothing;
	
	public String getName(){
		return name;
	}
	
	public String getSize(){
		return size;
	}
	
	public String getGender(){
		return gender;
	}
	
	public int getVariation(){
		return variation;
	}
	
	public int getClothing(){
		return clothing;
	}
	
	public String getSpecies(){
		return species;
	}
	
}
