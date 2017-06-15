import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Villager extends VillaCharacter implements Serializable{

	private static final long serialVersionUID = 2383832838237L;
	
	int friendship = 0;
	int attraction = 0;
	
	boolean activated = false;
	
	public Villager(){
		
		name = "Default";
		clothing = 0;
		
		randomizeVillager();
		

	}
	
	private void randomizeVillager(){
		
		//RANDOMIZE GENDER
		if(SystemVariables.getRandomBol()){
			gender = "m";
		}
		else{
			gender = "f";
		}

		//RANDOMIZE BODY
		String[] bodies = {"s", "m", "l"};
		size = bodies[SystemVariables.getRandomNum(bodies.length)];		

		//RANDOMIZE SPECIES
		if(gender == "m"){
			if(size == "s"){
				String[] specieses = {"fox"};
				species = specieses[SystemVariables.getRandomNum(specieses.length)];		
			}
			else if (size == "m"){
				String[] specieses = {"pig"};
				species = specieses[SystemVariables.getRandomNum(specieses.length)];		
			}
			else{
				String[] specieses = {"horse", "beaver"};
				species = specieses[SystemVariables.getRandomNum(specieses.length)];		
			}
		}
		else{
			if(size == "s"){
				String[] specieses = {"bunny"};
				species = specieses[SystemVariables.getRandomNum(specieses.length)];		
			}
			else if (size == "m"){
				String[] specieses = {"pig"};
				species = specieses[SystemVariables.getRandomNum(specieses.length)];		
			}
			else{
				String[] specieses = {"cow"};
				species = specieses[SystemVariables.getRandomNum(specieses.length)];		
			}
		}
		
		//TODO
		variation = 0;		

		name = getVillagerName();
	}
	
	private String getVillagerName(){
		
		String line = "Error";

		int nameint = SystemVariables.getRandomNum(4);
		
		try {
			line = Files.readAllLines(Paths.get("C:/Users/Dnae/workspace/VillaNova/characters/" + getGender() + "/" + getSize() + "/" + getSpecies() + "/" + "names.txt")).get(nameint);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return line;
	}

	public void activateVillager(){
		activated = true;
	}
	
	public boolean isActivated(){
		return activated;
	}
	
	public int getFriendship(){
		return friendship;
	}
	
	public void addFriendship(int i){
		friendship += i;
	}
	
	public int getAttraction(){
		return attraction;
	}
	
	public void addAttraction(int i){
		attraction += i;
	}
	
}
