import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.Timer;

public class StructurePanel extends JPanel implements Observer, ActionListener{

	Structure struct;
	Image img;
	
	int width, height;
	int skewx;
	
	Timer t = new Timer(100, this);
	
	public StructurePanel(int width, int height, Structure struct){
		this.struct = struct;
		skewx = 0;
		
		this.width = width;
		this.height = height;
		
		struct.addObserver(this);
		
		setSize(width, height);
		setOpaque(false);
		
		loadImage();
		
		validate();
	}
	
	public void doAction(){
		
	}
	
	private void loadImage(){	
		img = ImageBank.getImage("structures/" + struct.getIndex());
	}
		
	public Structure getStructure(){
		return struct;
	}
	
	public void shakePanel(){
		t.start();
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(img, skewx, 0, width, height, null);		
	}

	@Override
	public void update(Observable o, Object arg) {
		shakePanel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(skewx == 0){
			skewx = 5;
		}
		else if(skewx == 5){
			skewx = -5;
		}
		else{
			skewx = 0;
			t.stop();
		}
		repaint();
		
	}
}
