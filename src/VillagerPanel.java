
public class VillagerPanel extends MovablePanel{
	
	boolean ismoving = false; 
	
	public VillagerPanel(Villager v){
		this.vc = v;
		
		setSize(panelwidth, panelheight * 3);
		setOpaque(false);
		
		loadImages();
		
		if(v.isActivated()){
			dir = 3;
		}
		else{
			dir = 0;
		}
	}
	
	public Villager getVillager(){
		return (Villager)vc;
	}
	
	public boolean isMoving(){
		return ismoving;
	}
	
	public void changeMoving(){
		ismoving = !ismoving;
	}
	
}
