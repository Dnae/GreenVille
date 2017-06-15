import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class WorldPanel extends JLayeredPane{
	
	Tile[][] currentground;
	Structure[][] currentstructures;
	MovablePanel[][] occupiedgrid;
	
	Chunk[][] chunkgrid;
	
	Player player;
	
	int player_x, player_y;
	int player_x_count, player_y_count;
	
	int last_x, last_y;
	int chunksize;
	
	int renderdistance = 4;
	
	int movingspeed = 4;
	
	boolean clickable = true;
	
	//THE DIFFERENT GROUND IMAGES
	Image[] tiles = new Image[15];

	int panelwidth = SystemVariables.getPanelWidth();
	int panelheight = SystemVariables.getPanelHeight();
	
	Image[] playerimgs = new Image[4];
	
	List<VillagerPanel> villagers = new ArrayList<VillagerPanel>();
	
	PlayerPanel playerpanel;
	
	public WorldPanel(Location loc, Player p, int x, int y){
		
		this.player_x = x;
		this.player_y = y;
				
		chunksize = 5;
		
		this.last_x = player_x - (player_x % chunksize) + chunksize/2;
		this.last_y = player_y - (player_y % chunksize) + chunksize/2;
		
		player = p;
		
		playerpanel = new PlayerPanel(p);
		
		loadGroundImages();
		
		changeLocation(loc);
		
		initiatePanels();
		
		drawLocation();
		
		mouseStuff();
	}
	
	private void initiatePanels(){
		setLayout(null);		
		setLocation(-player_x * panelwidth - panelwidth / 2 + SystemVariables.getWidth() / 2, -player_y * panelheight + SystemVariables.getHeight() / 2);
		setSize(currentground.length * panelwidth, currentground[0].length * panelheight);
//		setOpaque(true);
		setBackground(Color.RED);
	}
	
	private void mouseStuff(){
		addMouseListener(new MouseAdapter(){

			@Override
			public void mouseReleased(MouseEvent e) {
				if(clickable){
					int i = playerpanel.getDir();
					
					if(i == 0){
						doAction(player_x, player_y - 1);
					}
					else if(i == 1){
						doAction(player_x - 1, player_y);
					}
					else if(i == 2){
						doAction(player_x + 1, player_y);
					}
					else if(i == 3){
						doAction(player_x, player_y + 1);
					}
					else{
						
					}
				}
			}

		});
	}
	
	private void doAction(int x, int y){

		if(occupiedgrid[x][y] == null){
			if(currentground[x][y].getStructure() == null){			
				if(currentground[x][y].getIndex() == 0){
					currentground[x][y].setIndex(4);
				}
				else{
					currentground[x][y].setIndex(0);
				}

				repaint();
			}
			else{
				currentground[x][y].getStructure().specialAction();
			}
		}
		else{
			if(occupiedgrid[x][y] instanceof VillagerPanel){
				
				SystemVariables.getGreenVille().villagerDialogue(((VillagerPanel)occupiedgrid[x][y]).getVillager(), playerpanel.getPlayer());

			}
		}
	}
	
	public void changeLocation(Location loc){
		
		changeGround(loc.getGround());		
		changeStructures(loc.getStructures());
		changePeople(loc);
		
		chunkgrid = new Chunk[currentground.length / chunksize][currentground[0].length / chunksize];
		for(int i = 0; i < chunkgrid.length; i++){
			for(int j = 0; j < chunkgrid[0].length; j++){
				chunkgrid[i][j] = new Chunk();
			}
		}		
	}
	
	public void changeGround(Tile[][] ground){
		currentground = ground;
	}
	
	public void changeStructures(Structure[][] structures){
		currentstructures = structures;
	}
	
	public void changePeople(Location loc){
		
		occupiedgrid = new MovablePanel[currentground.length][currentground[0].length];

		playerpanel.setLocation(player_x * panelwidth - panelwidth/2, player_y * panelheight - panelheight * 3);
		add(playerpanel, new Integer(playerpanel.getY() + playerpanel.getHeight()));
		
		if(loc instanceof Town){
			Town t = (Town)loc;
			
			for(Villager v : t.getVillagers()){
				
				VillagerPanel vp = new VillagerPanel(v);
				vp.setLocation(20 * panelwidth, 20 * panelheight - panelheight * 5/2);
				
				occupiedgrid[(vp.getX() + vp.getWidth()/2)/panelwidth][(vp.getY()+vp.getHeight())/panelheight] = vp;
				
				add(vp, new Integer(vp.getY() + vp.getHeight()));
				
				villagers.add(vp);
				
				if(v.isActivated()){
					
				}
				else{
					
				}
			}
		}
	}	

	private void drawLocation(){


		for(int i = 0; i < chunkgrid.length; i++){
			for(int j = 0; j < chunkgrid[0].length; j++){

				final int i_f = i;
				final int j_f = j;

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {

						if(Math.abs((player_x / chunksize) - i_f) < renderdistance && Math.abs((player_y / chunksize) - j_f) < renderdistance){

							if(chunkgrid[i_f][j_f].isEmpty()){
								for(int k = i_f * chunksize; k < i_f * chunksize + chunksize; k++){
									for(int l = j_f * chunksize; l < j_f * chunksize + chunksize; l++){

										TilePanel tp = new TilePanel(currentground[k][l]);
										tp.setLocation(k * panelwidth, l * panelheight);

										chunkgrid[i_f][j_f].add(tp);

										add(tp, 0);
										tp.validate();

										Structure str = currentstructures[k][l];

										if(str != null){

											StructurePanel sp = str.getPanel(panelwidth, panelheight);
											sp.setLocation(k * panelwidth, l* panelheight);

											chunkgrid[i_f][j_f].add(sp);
											add(sp, new Integer(sp.getY() + sp.getHeight()));

											sp.validate();
										}		
									}
								}
							}
						}
						else{
							chunkgrid[i_f][j_f].removeAll();
						}
					}
				});
			}	
		}
		
		revalidate();
		repaint();
	}
	

	public void updateWorld(int x, int y){
		if(x != 0 || y != 0){
			
			updatePlayer(x, y);		

			int tile_x = (player_x * panelwidth + player_x_count + x * movingspeed)/panelwidth;
			int tile_y = (player_y * panelheight + player_y_count + y * movingspeed)/panelheight;
			
			if(canMove(tile_x, tile_y, false)){
				
			}
			else if(canMove(tile_x, player_y, false)){
				y = 0;
			}
			else if(canMove(player_x, tile_y, false)){
				x = 0;
			}		
			else{
				x = 0;
				y = 0;
			}
			
			setLocation(getX() -x * movingspeed, getY() - y * movingspeed);
			playerpanel.movePlayer(x * movingspeed, y * movingspeed);
			setLayer(playerpanel, new Integer(playerpanel.getY() + playerpanel.getHeight() + 1));

			updateCoordinates(x * movingspeed, y * movingspeed);
			
			if(Math.abs(last_x - player_x) > chunksize/2 || Math.abs(last_y - player_y) > chunksize/2){
				
				last_x = player_x - (player_x % chunksize) + chunksize/2;
				last_y = player_y - (player_y % chunksize) + chunksize/2;
				
				drawLocation();

			}
			
//			currentground[player_x][player_y].setIndex(2);

			
		}
		
		updateVillagers();
		
	}
	
	private void updateCoordinates(int x, int y){
		
		player_x_count += x;
		player_y_count += y;
		
		if(player_x_count < 0){
			player_x--;
			player_x_count += panelwidth;
		}
		else{
			player_x += player_x_count / panelwidth;
			player_x_count %= panelwidth;
		}
		
		if(player_y_count < 0){
			player_y--;
			player_y_count += panelheight;
		}
		else{
			player_y += player_y_count / panelheight;
			player_y_count %= panelheight;
		}
	
	}
	
	private void updatePlayer(int x, int y){
		if(x == -1){
			playerpanel.setDir(1);
		}
		else if(x == 1){
			playerpanel.setDir(2);
		}
		
		if(y == -1){
			playerpanel.setDir(0);
		}
		else if(y == 1){
			playerpanel.setDir(3);
		}
	}
	
	private void updateVillagers(){
		
		for(VillagerPanel vp : villagers){
			
			int dir = vp.getDir();
			int x = 0;
			int y = 0;
			
			switch (dir) {
            case 0:  y = -1;
                     break;
            case 1:  x = -1;
                     break;
            case 2:  x = 1;
                     break;
            case 3:  y = 1;
                     break;
			}
			
			if(SystemVariables.getRandomNum(100) == 0){
				
				vp.changeMoving();
				
				if(vp.isMoving()){
					vp.setDir(SystemVariables.getRandomNum(4));
				}
			}
			
			int vx0 = (vp.getX() + vp.getWidth()/2);
			int vy0 = (vp.getY() + vp.getHeight());
			
			int px0 = vx0/panelwidth;
			int py0 = vy0/panelheight;
			
			int vx1 = vx0 + x;
			int vy1 = vy0 + y;
			
			int px1 = vx1/panelwidth;
			int py1 = vy1/panelheight;
			
			if(vp.isMoving()){
				
				if(px1 != px0 || py1 != py0){
					if(canMove(px1, py1, false)){
						vp.setLocation(vp.getX() + x, vp.getY() + y);
						setLayer(vp, new Integer(vp.getY() + vp.getHeight() + 1));
						
						occupiedgrid[px0][py0] = null;
						occupiedgrid[px1][py1] = vp;
						
					}
				}
				else{
					vp.setLocation(vp.getX() + x, vp.getY() + y);
					setLayer(vp, new Integer(vp.getY() + vp.getHeight() + 1));

					occupiedgrid[px1][py1] = vp;

				}
			}
		}
	}
	
	private boolean canMove(int x, int y, boolean isPlayer){
		
		if(currentground[x][y].getStructure() != null || currentground[x][y].getIndex() == 0 || (!isPlayer && occupiedgrid[x][y] != null)){
			return false;
		}
		else{
			return true;
		}
	}
	
	public void changeSpeed(){
		if(movingspeed == 4){
			movingspeed = 6;
		}
		else{
			movingspeed = 4;
		}
	}

	public int getPlayerX(){
		return player_x;
	}
	
	public int getPlayerY(){
		return  player_y;
	}

	private void loadGroundImages(){
		
		for(int i = 0; i < 5; i++){
			tiles[i] = ImageBank.getImage("groundtiles/" + i);
		}
				
	}
	
	public void setClickable(boolean bol){
		this.clickable = bol;
	}
	
	private class TilePanel extends JPanel implements Observer{
		
		Image img;
		Tile t;
		
		private TilePanel(Tile t){
			this.t = t;
			this.img = tiles[t.getIndex()];
			t.addObserver(this);
			
			setSize(panelwidth, panelheight);
		
		}
		
		@Override
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(img, 0, 0, panelwidth, panelheight, null);
		}

		@Override
		public void update(Observable o, Object arg) {
			this.img = tiles[t.getIndex()];
			repaint();
		}
	}
	
	private class Chunk{
		
		List<Component> items = new ArrayList<Component>();
		
		private Chunk(){
			
		}
		
		private void add(Component c){
			items.add(c);
		}
		
		private boolean isEmpty(){
			return items.isEmpty();
		}
		
		private void removeAll(){
			
			for(Component c : items){
				remove(c);
			}
			items.clear();
		}
		
	}
	
}
