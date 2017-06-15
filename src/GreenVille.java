import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public final class GreenVille extends JLayeredPane implements ActionListener{

	SaveFile sf;
	
	WorldPanel wp;
	ActivityPanel ap;
	
	JPanel savepanel;
	
	long lasttime;
	
	Timer gametimer = new Timer(20, this);
	boolean pause = false;
	
	int xDir, yDir;
	
	public GreenVille(SaveFile sf){
		this.sf = sf;
		
		keyActions();
		
		setBackground(Color.GREEN);
		add(new JLabel(sf.getPlayer().getName()), new Integer(2));
		
		startGameEvents();
		
		wp = new WorldPanel(sf.getTown(), sf.getPlayer(), sf.getSaveX(), sf.getSaveY());

		add(wp, new Integer(1));
		
		gametimer.start();
	}
	
	private void startGameEvents(){
		
		if(System.currentTimeMillis() - sf.getTime() > 86400000){
			sf.getTown().addNewVillager();
		}
	}
	
	public void villagerDialogue(Villager v, Player p){
		pause = true;
		
		ap = new DialoguePanel(v, p);
				
		add(ap, new Integer(3));
		
		wp.setClickable(false);
	}
	
	public void endActivity(){
		remove(ap);
		ap = null;
		
		pause = false;
		wp.setClickable(true);
		repaint();
	}
	
	public void showSave(){
		pause = true;
		
		savepanel = new JPanel();
		savepanel.setLayout(null);
		savepanel.setSize(400, 400);
		savepanel.setLocation(SystemVariables.getWidth()/2 - 200, SystemVariables.getHeight()/2 - 200);
		
		JLabel jl = new JLabel("Do you want to save?");
		jl.setSize(350, 30);
		jl.setLocation(30, 30);
		
		
		JPanel b1 = new JPanel();
		b1.setLayout(new GridBagLayout());
		b1.setBackground(Color.GREEN);
		b1.setSize(100, 100);
		b1.setLocation(30, 250);
		
		b1.addMouseListener(new MouseAdapter() {
			@Override
		    public void mouseReleased(MouseEvent e) {
				sf.setSaveX(wp.getPlayerX());
				sf.setSaveY(wp.getPlayerY());
				sf.save();	
				
				removeSave();
		    }
		});	
		
		JLabel jpb1 = new JLabel("Yes");
		jpb1.setSize(200, 50);
		jpb1.setLocation(20, 100);
		
		b1.add(jpb1);
				
		
		JPanel b2 = new JPanel();
		b2.setLayout(new GridBagLayout());
		b2.setBackground(Color.RED);
		b2.setSize(100, 100);
		b2.setLocation(200, 250);
		
		b2.addMouseListener(new MouseAdapter() {
			@Override
		    public void mouseReleased(MouseEvent e) {
				removeSave();
		    }
		});	
		JLabel jpb2 = new JLabel("No");
		jpb2.setSize(200, 50);
		jpb2.setLocation(20, 100);
		
		b2.add(jpb2);
		
		savepanel.add(jl);
		savepanel.add(b1);
		savepanel.add(b2);
		
		savepanel.validate();
		add(savepanel, new Integer(3));
		
		wp.setClickable(false);
		
		repaint();
	}
	
	private void removeSave(){
		remove(savepanel);
		savepanel = null;
		
		pause = false;
		repaint();
		wp.setClickable(true);
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == gametimer){
			if(!pause){				
				lasttime = System.currentTimeMillis();
				
				wp.updateWorld(xDir, yDir);
			}
			if(ap != null){
				ap.updateActivity();
			}
		}
	}
	
	private void keyActions(){
		
		Action doW = new AbstractAction() {
			@Override
		    public void actionPerformed(ActionEvent e) {
		        yDir = -1;
		    }
		};
		
		Action doA = new AbstractAction() {
			@Override
		    public void actionPerformed(ActionEvent e) {
				xDir = -1;
		    }
		};
		
		Action doS = new AbstractAction() {
			@Override
		    public void actionPerformed(ActionEvent e) {
				yDir = 1;
		    }
		};
		
		Action doD = new AbstractAction() {
			@Override
		    public void actionPerformed(ActionEvent e) {
				xDir = 1;
		    }
		};
	
		
		Action undoW = new AbstractAction() { //TODO
			@Override
		    public void actionPerformed(ActionEvent e) {
		        yDir = 0;
		    }
		};
		
		Action undoA = new AbstractAction() {
			@Override
		    public void actionPerformed(ActionEvent e) {
				xDir = 0;
		    }
		};
		
		Action undoS = new AbstractAction() {
			@Override
		    public void actionPerformed(ActionEvent e) {
				yDir = 0;
		    }
		};
		
		Action undoD = new AbstractAction() {
			@Override
		    public void actionPerformed(ActionEvent e) {
				xDir = 0;
		    }
		};
		
		Action undoShift = new AbstractAction() {
			@Override
		    public void actionPerformed(ActionEvent e) {
				wp.changeSpeed();
		    }
		};
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false),"doW");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false),"doA");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false),"doS");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false),"doD");
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true),"undoW");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true),"undoA");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true),"undoS");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true),"undoD");
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, 0, true),"undoShift");
		
		getActionMap().put("doW",	doW);
		getActionMap().put("doA",	doA);
		getActionMap().put("doS",	doS);
		getActionMap().put("doD",	doD);

		getActionMap().put("undoW",	undoW);
		getActionMap().put("undoA",	undoA);
		getActionMap().put("undoS",	undoS);
		getActionMap().put("undoD",	undoD);
		getActionMap().put("undoShift",	undoShift);
		
	}
	
}
