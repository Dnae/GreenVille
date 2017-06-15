import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class DialoguePanel extends ActivityPanel{

	Villager v;
	Player p;
	
	JPanel exitbutton;
	
	JPanel villagerpanel;
	Image body;
	Image clothing;
	
	Image[] faces = new Image[4];
	Image face;
	
	JPanel dialoguepanel;
	JLabel dialoguelabel = new JLabel();
	JLabel namelabel = new JLabel();
	
	JPanel choicepanel;
	
	public DialoguePanel(Villager v, Player p){
		
		setSize(width, height);
		setLocation(SystemVariables.getWidth()/2 - width/2, SystemVariables.getHeight()/2 - height/2);
		
		setOpaque(false);

		JPanel backgroundpanel = new JPanel(){
			
			{
				setSize(width, height);
			}
			
			@Override
			protected void paintComponent(Graphics g)
		    {
		        g.setColor( getBackground() );
		        g.fillRect(0, 0, getWidth(), getHeight());
		        super.paintComponent(g);
		    }
		};
		backgroundpanel.setOpaque(true);
		backgroundpanel.setBackground(new Color(0, 0, 0, 80));
		
		add(backgroundpanel, new Integer(0));
		
		this.v = v;
		this.p = p;

		drawDisplay();
	}
	
	private void drawDisplay(){
				
		exitbutton = new JPanel(){
			@Override
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				g.setColor(Color.RED);
				g.fillOval(0, 0, 50, 50);
			}
		};
		exitbutton.setSize(50, 50);
		exitbutton.setLocation(450, 0);
		exitbutton.setOpaque(false);
		
		exitbutton.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseReleased(MouseEvent e) {
				SystemVariables.getGreenVille().endActivity();
			}
		});
		
		add(exitbutton, new Integer(50));
		
		drawVillager();
		
		drawDialogue();
		
		drawChoices();
		
		repaint();
	}
	
	private void drawDialogue(){
		
		dialoguepanel = new JPanel(null){
			@Override
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				g.setColor(Color.WHITE);
				g.fillOval(0, 0, 50, 50);
				g.fillOval(getWidth() - 50, 0, 50, 50);
				g.fillOval(0, getHeight() - 50, 50, 50);
				g.fillOval(getWidth() - 50, getHeight() - 50, 50, 50);
				
				g.fillRect(25, 0, getWidth() - 50, getHeight());
				g.fillRect(0, 25, getWidth(), getHeight() - 50);
			}
		};
		dialoguepanel.setSize(230, 230);
		dialoguepanel.setLocation(10, 10);
		dialoguepanel.setOpaque(false);
		dialoguepanel.setBackground(Color.WHITE);
		
		dialoguelabel.setSize(210, 70);
		dialoguelabel.setLocation(10, 25);
		dialoguelabel.setOpaque(false);
		
		dialoguelabel.setFont(new Font("Consolas", Font.BOLD, 14)); 
		dialoguelabel.setText("Todo");		
		
		dialoguepanel.add(dialoguelabel);
		
		add(dialoguepanel, new Integer(50));
	}
	
	private void drawChoices(){
		choicepanel = new JPanel(null){
			@Override
			protected void paintComponent(Graphics g){
				super.paintComponent(g);
				g.setColor(Color.WHITE);
				g.fillOval(0, 0, 50, 50);
				g.fillOval(getWidth() - 50, 0, 50, 50);
				g.fillOval(0, getHeight() - 50, 50, 50);
				g.fillOval(getWidth() - 50, getHeight() - 50, 50, 50);
				
				g.fillRect(25, 0, getWidth() - 50, getHeight());
				g.fillRect(0, 25, getWidth(), getHeight() - 50);
			}
		};
		choicepanel.setSize(230, 230);
		choicepanel.setLocation(10, getHeight() - choicepanel.getHeight() - 10);
		choicepanel.setOpaque(false);
		choicepanel.setBackground(Color.WHITE);

		add(choicepanel, new Integer(50));
	}
	
	private void drawVillager(){
		//TODO
	}
	
	@Override
	public void updateActivity(){
		//TODO
	}
		
}
