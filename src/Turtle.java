import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
//104403019 ¸êºÞ3A ¥j¨Î®m
public class Turtle  extends JLabel implements Runnable{
	private JPanel panel;
	private JLabel label;
	private ImageIcon w = new ImageIcon(getClass().getResource("w.png"));
	private ImageIcon w2 = new ImageIcon(getClass().getResource("w2.png"));
	private ImageIcon[][] turtle = {{w,w2}};
	private int length,width,height,x,y,size,Hdir,num;
	private static final SecureRandom generator = new SecureRandom();
	private static int speed = generator.nextInt(150)+100;
	private Timer t1,t2 ;
	private boolean live = true;

		
	public Turtle(int width,int height,int x,int y,int size,int Hdir) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.Hdir = Hdir; // 0 right 1 left
		this.size = size;
		turtle[0][0].setImage(turtle[0][0].getImage().getScaledInstance(size,size,Image.SCALE_DEFAULT));
		turtle[0][1].setImage(turtle[0][1].getImage().getScaledInstance(size,size,Image.SCALE_DEFAULT));
		this.setIcon(turtle[0][Hdir]);
		this.setBounds(x,y,size,size);
	}

	@Override
	public void run() {
		
t2 = new Timer(3000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Hdir = generator.nextInt(2);
				speed = generator.nextInt(10)+10;
				t1.setDelay(speed);
			}
							}
						);
		t2.start();
		if(!live) {t2.stop();}		
		t1 = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int HVS = generator.nextInt(3)+1;
					
				if(x<0) {Hdir = 0;}
				else if(size+x-width>0){Hdir = 1;}
				
				if(size/2+10+y-height<0){y+=HVS;}
				else {
					if(Hdir==0) {
						x+=HVS;
						Turtle.this.setIcon(turtle[0][Hdir]);				
					}
					else if(Hdir==1) {
						x-=HVS;			
						Turtle.this.setIcon(turtle[0][Hdir]);							
					}
				}
				Turtle.this.setLocation(x,y);				
			}
				
													}
						);
			
			t1.start();	
			
			
			if(!live) {t1.stop();}
		
				
												
					
	
			
		
	}
	public void stop() {
		live = false;
	}
	public void setNum(int i) {
		this.num = i;
	}
	public int getNum() {
		return num;
	}
}

