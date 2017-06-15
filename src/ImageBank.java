import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class ImageBank {
	
	public ImageBank(){

	}
	
	/**
	 * Load image path from
	 * Current directory + subpath + ".png"
	 * @param path
	 * @return
	 */
	public static Image getImage(String path){
		Image img = null;

		File f = new File(System.getProperty("user.dir") + "/" + path + ".png");
		
		if(f.exists()){
			try {

				img = ImageIO.read(f);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			System.out.println(f.getPath());
		}
		
		return img;
	}

	
}
