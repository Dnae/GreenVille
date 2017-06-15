import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadingMenu extends JPanel{

	JPanel loadingpanel = new JPanel();
	
	JPanel title;
	
	JPanel menubuttons = new JPanel();
	LoadingPanel save1;
	LoadingPanel save2;
	LoadingPanel save3;
	LoadingPanel save4;
	
	GameWindow gw;
	
	public LoadingMenu(GameWindow gw){
		
		this.gw = gw;
		
		this.setLayout(null);
		setAlignmentY(CENTER_ALIGNMENT);
		setOpaque(true);
		setBackground(Color.BLACK);
		
		drawMenu();
	
	}
	
	private void drawMenu(){
		
		loadingpanel.setLayout(null);
		loadingpanel.setSize(1000, 600);
		loadingpanel.setLocation(SystemVariables.getWidth()/2 - 500, SystemVariables.getHeight()/2 - 300);
		loadingpanel.setOpaque(false);
		
		title = new JPanel(){
			
			Image img = null;
			
			{
				setSize(1000, 200);
				setLocation(0, 0);
				setOpaque(false);
				img = ImageBank.getImage("title");
			}
			
			@Override
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(img, 0, 0, null);
			}
		};
		
		menubuttons.setSize(1000, 400);
		menubuttons.setLocation(0, 200);
		menubuttons.setLayout(null);
		menubuttons.setOpaque(true);		
		menubuttons.setAlignmentX(CENTER_ALIGNMENT);
		
		save1 = new LoadingPanel(1);
		save1.setLocation(0, 0);
		
		save2 = new LoadingPanel(2);
		save2.setLocation(250, 0);
		
		save3 = new LoadingPanel(3);
		save3.setLocation(500, 0);
		
		save4 = new LoadingPanel(4);
		save4.setLocation(750, 0);

		menubuttons.add(save1);
		menubuttons.add(save2);
		menubuttons.add(save3);
		menubuttons.add(save4);
		
		loadingpanel.add(title);
		loadingpanel.add(menubuttons);
		
		add(loadingpanel);
		
	}
	
	public class LoadingPanel extends JPanel{
		
		int index;
		SaveFile sf;
		
		public LoadingPanel(int i){
			
			this.index = i;
			
			setLayout(null);
			setSize(250, 400);
			setBackground(Color.WHITE);
			
			File file = new File(index + ".savefile");
			
			if(file.exists()){
				sf = loadFile(file);
				LoadingCharacter lc = new LoadingCharacter(sf);
				lc.setLocation(45, 0);
				this.add(lc);
			}
			else{
				sf = null;
				LoadingCharacter lc = new LoadingCharacter(null);
				lc.setLocation(45, 0);
				this.add(lc);
			}
			
			this.addMouseListener(new MouseAdapter() {

				@Override
			    public void mouseClicked(MouseEvent e) {
					setBackground(Color.WHITE);
					
					if(sf != null){
						gw.drawTown(sf);
					}
					else{
						gw.drawTown(new SaveFile(index));
					}
					
			    }
			    
			    @Override
			    public void mouseEntered(MouseEvent e) {    	
			    	setBackground(Color.GREEN);
			    }
			    
			    @Override
			    public void mouseExited(MouseEvent e) {
			    	setBackground(Color.WHITE);
			    }
			});				
		}
		
		private SaveFile loadFile(File file){

			SaveFile sf = null;

			FileInputStream f_in;

			try {
				f_in = new FileInputStream(file);


				ObjectInputStream obj_in = 	new ObjectInputStream(f_in);

				Object obj = null;
				try {
					obj = obj_in.readObject();
					obj_in.close();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

				if (obj instanceof SaveFile)
				{
					sf = (SaveFile) obj;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return sf;
		}
		
		public class LoadingCharacter extends JPanel{
			
			Image[] characterimages = new Image[3];
			
			public LoadingCharacter(SaveFile sf){
				setSize(200, 400);
				setOpaque(false);
				
				if(sf == null){
					characterimages[0] = ImageBank.getImage("characters/new");
				}
				else{
					Player p = sf.getPlayer();
					
					//TODO
				}
				
			}
			
			@Override
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(characterimages[0], 0, 0, null);
				g.drawImage(characterimages[1], 0, 0, null);
				g.drawImage(characterimages[2], 0, 0, null);
			}
		}
	}
}