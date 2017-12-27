import javax.swing.JFrame;

public class FishBowlTest {
	public static void main(String[] args) {					
		FishBowl fishBowl = new FishBowl();	    	
		fishBowl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fishBowl.setSize(1100,800);
		fishBowl.setVisible(true);
		fishBowl.setResizable(false);
	}

}
