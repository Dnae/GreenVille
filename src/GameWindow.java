
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class GameWindow {

	int width, height;
	
	JFrame window = new JFrame();
	LoadingMenu lm = new LoadingMenu(this);
	
	SaveFile sf;
	
	public GameWindow(){
		
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setTitle("VillaNova");
		window.setUndecorated(true);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		window.getContentPane().setBackground(Color.BLACK);
		
		changeCursor();
		
		drawLoadingMenu();
		
	//	window.pack();
		window.setVisible(true);
	}
	
	private void changeCursor(){
		
		Image cursorimage = ImageBank.getImage("cursor");			
		window.getContentPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(cursorimage,new Point(0,0),"custom cursor"));

	}
	
	private void drawLoadingMenu(){
		
		window.add(lm);
		
	}
	
	public void drawTown(SaveFile save){
	
		this.sf = save;
		
		window.remove(lm);
		
		lm = null;
		VillaNova vn = new VillaNova(sf);
		SystemVariables.setVillaNova(vn);
		window.add(vn);

		SwingUtilities.updateComponentTreeUI(window);
	}
	
}