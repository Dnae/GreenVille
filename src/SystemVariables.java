import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Random;

public final class SystemVariables {

	static int height;
	static int width;
	
	static int panelwidth;
	static int panelheight;
	
	static VillaNova vilnov;
	
	static Random rand = new Random();
	static Random seedrand;
	
	public SystemVariables(){
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		
		width = gd.getDisplayMode().getWidth();
		height = gd.getDisplayMode().getHeight();	
		
		panelwidth = 90;
		panelheight = 60;
		
	}
	
	public static int getWidth(){
		return width;
	}
	
	public static int getHeight(){
		return height;
	}
	
	public static VillaNova getVillaNova(){
		return vilnov;
	}
	
	public static void setVillaNova(VillaNova vn){
		vilnov = vn;
	}
	
	public static int getRandomNum(int i){
		return rand.nextInt(i);
	}
	
	public static boolean getRandomBol(){
		return rand.nextBoolean();
	}
	
	public static int getPanelWidth(){
		return panelwidth;
	}
	
	public static int getPanelHeight(){
		return panelheight;
	}
	
}