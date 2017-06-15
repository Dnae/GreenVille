import java.io.Serializable;
import java.util.Observable;

public class Tile extends Observable implements Serializable{
	
	private static final long serialVersionUID = 35466789L;

	int index;
	Structure str = null;
	
	public Tile(int index){
		this.index = index;
	}
	
	public void setIndex(int i){
		if(i != index){
			index = i;
			setChanged();
			notifyObservers();
		}
	}
	
	public void setStructure(Structure struc){
		str = struc;
	}
	
	public Structure getStructure(){
		return str;
	}
	
	public int getIndex(){
		return index;
	}
	
}