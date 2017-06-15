import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public abstract class MovablePanel extends JPanel{

	Image[][] imgs = new Image[4][2];
	int dir = 3;
	
	VillaCharacter vc;
	
	int panelwidth = SystemVariables.getPanelWidth();
	int panelheight = SystemVariables.getPanelHeight();
	
	public void setDir(int dir){
		this.dir = dir;
		repaint();
	}

	public int getDir(){
		return dir;
	}
	
	protected void loadImages(){
		try {
			
			String charactercatalog = "C:/Users/Dnae/workspace/VillaNova/characters/" + vc.getGender() + "/" + vc.getSize() + "/" + vc.getSpecies() + "/" + vc.getVariation() + "/i";
			
			for(int i = 0; i < 4; i++){
				imgs[i][0] = ImageIO.read(new File(charactercatalog + i + ".png"));
			}
			
			String clothingcatalog = "C:/Users/Dnae/workspace/VillaNova/clothing/" + vc.getGender() + "/" + vc.getSize() + "/" + vc.getClothing() + "/";
			
			
			for(int i = 0; i < 4; i++){
				imgs[i][1] = ImageIO.read(new File(clothingcatalog + i + ".png"));
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(imgs[dir][0], 0, 0, panelwidth, panelheight * 3, null);		
		g.drawImage(imgs[dir][1], 0, 0, panelwidth, panelheight * 3, null);
	}
}
