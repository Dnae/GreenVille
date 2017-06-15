
public class PlayerPanel extends MovablePanel{
	
	public PlayerPanel(Player p){
		
		this.vc = p;
		
		loadImages();
		
		setSize(panelwidth, panelheight * 3);
		setOpaque(false);		
		
	}

	public void movePlayer(int x, int y){
		setLocation(getX() + x, getY() + y);
	}

	public Player getPlayer(){
		return (Player)vc;
	}
	
}