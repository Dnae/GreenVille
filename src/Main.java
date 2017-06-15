
import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] arg){
		
		new SystemVariables();
		new ImageBank();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GameWindow();
			}
		});
	}
}
