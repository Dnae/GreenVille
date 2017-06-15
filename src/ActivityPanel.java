import javax.swing.JLayeredPane;

public abstract class ActivityPanel extends JLayeredPane{

	final int width = 500;
	final int height = 500;
	
	public abstract void updateActivity();
}
