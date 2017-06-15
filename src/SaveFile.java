import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SaveFile implements Serializable{

	private static final long serialVersionUID = 429423423358L;
	
	int savenum;
	int savex, savey;
	
	long lastsave = 0;
	
	Town t;
	Player p;
	
//	List<Something> crew = new ArrayList<Something>(); 
	
	boolean isNew = true;

	public SaveFile(int i){

		this.savenum = i;
				
		t = new Town();
		p = new Player("m", "l", "Dnae");
		
		savex = 15;
		savey = 15;
		
	}
		
	public void save(){
			
		try {
			
			lastsave = System.currentTimeMillis();
			
			// Write to disk with FileOutputStream
			FileOutputStream f_out = new FileOutputStream(savenum + ".savefile");


			// Write object with ObjectOutputStream
			ObjectOutputStream obj_out = new ObjectOutputStream (f_out);

			obj_out.writeObject(this);

			obj_out.close();
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Player getPlayer(){
		return p;
	}
	
	public boolean isNew(){
		return isNew;
	}
	
	public void setSaveX(int x){
		this.savex = x;
	}
	
	public int getSaveX(){
		return savex;
	}

	public void setSaveY(int y){
		this.savey = y;
	}
	
	public int getSaveY(){
		return savey;
	}
	
	public Town getTown(){
		return t;
	}
	
	public long getTime(){
		return lastsave;
	}

	public void isNew(boolean isNew){
		this.isNew = isNew;
	}
}