//104403019 資管3A 古佳峻
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import java.security.*;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
public class Fish extends JLabel implements Runnable{
	private ImageIcon f1 = new ImageIcon(getClass().getResource("1.png"));
	private ImageIcon f2 = new ImageIcon(getClass().getResource("2.png"));
	private ImageIcon f3 = new ImageIcon(getClass().getResource("3.png"));
	private ImageIcon f4 = new ImageIcon(getClass().getResource("4.png"));
	private ImageIcon f5 = new ImageIcon(getClass().getResource("5.png"));
	private ImageIcon f6 = new ImageIcon(getClass().getResource("6.png"));
	private ImageIcon[][] fish = {{f1,f2},{f3,f4},{f5,f6}};
	private JPanel panel;
	private JLabel label;
	private Timer t1,t2;
	private boolean live = true,flag = true;
	private int height,width,x,y,size,Hdir,Vdir,type,num;
	private static final SecureRandom generator = new SecureRandom();
	private static int speed = generator.nextInt(150)+100;
	int HVS = generator.nextInt(3)+1; 
	

	public Fish(int width,int height,int x,int y,int size,int Hdir,int Vdir,int type) {
		this.type = type;
		this.Hdir = Hdir;
		this.Vdir = Vdir;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.size = size;
//		if(x+size>width) {x = width-size;}
		if(x-size/2<0) {x = 0;}
//		if(y+size>height) {y =height-size;}
		if(y-size/2<0) {y = 0;}
		fish[type][0].setImage(fish[type][0].getImage().getScaledInstance(size,size,Image.SCALE_DEFAULT));
		fish[type][1].setImage(fish[type][1].getImage().getScaledInstance(size,size,Image.SCALE_DEFAULT));
		this.setIcon(fish[type][Hdir]);
		this.setBounds(x,y,size,size);
		
		this.Vdir = this.Vdir + generator.nextInt(2);
		
	}	
	@Override
	public void run() {
		
		t2 = new Timer(2500, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Hdir = generator.nextInt(2);
				Vdir = generator.nextInt(3);
				speed = generator.nextInt(10)+10;
				t1.setDelay(speed);
				HVS = generator.nextInt(3)+1;
			}
							}
						);
		t2.start();
		
		if(!live) {t2.stop();}		
		t1 = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

					
				if(x<0) {Hdir = 0;}
				else if(size+x-width>0){Hdir = 1;}
				if(y<0) {Vdir = 0;}//0向下  1向上
				else if(size+y-height>0){Vdir = 1;}
				if(Vdir == 0) {y+=HVS;}
				else if(Vdir ==1) {y-=HVS;}
				if(Hdir==0) {
					x+=HVS;
					Fish.this.setIcon(fish[type][Hdir]);				
				}
				else if(Hdir==1) {
					x-=HVS;			
					Fish.this.setIcon(fish[type][Hdir]);							
				}
				Fish.this.setLocation(x,y);				
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


