import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.security.SecureRandom;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
//104403019 資管3A 古佳峻
public class FishBowl extends JFrame{
	private final BorderLayout layout;
	private final GridBagLayout Glayout;
	private final JButton addF; //addFish
	private final JButton addT; //addTurtle
	private final JButton delete; //delete
	private final JButton clear; //clear all
	private final JLabel func; //current function
	private final JLabel FA; //fishAmount
	private final JLabel TA; //tirtleAmount
	private final JPanel control;
	private final JPanel status;
	private final JPanel bowl;
	private final Font font;
	private final ArrayList<Fish> FL = new ArrayList<>();
	private final ArrayList<Turtle> TL = new ArrayList<>();
	private final ArrayList<Thread> thread = new ArrayList<>();
	private static final SecureRandom generator = new SecureRandom();
	private static int Fcount = 0,Tcount = 0;
	private int modenum = 0,width,height;
//	private ImageIcon background = new ImageIcon(getClass().getResource("background.png"));
//	private JLabel back = new JLabel(background);
	private String[] mode = {"新增魚                                ","新增烏龜                            ","移除選取                            "};
	public FishBowl(){
		super("FishBowl");
		layout = new BorderLayout();	
		Glayout = new GridBagLayout();
		font = new Font("黑體",Font.BOLD,18);
		setLayout(layout);
		control = new JPanel();
		control.setLayout(Glayout);
		ButtonHandler handler = new ButtonHandler();
		addF = new JButton("新增魚");
		addF.addActionListener(handler);
		addF.setFont(font);
		GridBagConstraints bag1 = new GridBagConstraints();
        bag1.gridx = 0;
        bag1.gridy = 0;
        bag1.gridwidth = 5;
        bag1.gridheight = 1;
        bag1.weightx = 0.5;
        bag1.weighty = 0;
        bag1.insets = new Insets(5,0,5,2);
        bag1.fill = GridBagConstraints.BOTH;
        bag1.anchor = GridBagConstraints.CENTER;
        control.add(addF, bag1);
        
		
		delete = new JButton("移除選取");
		delete.setFont(font);
		delete.addActionListener(handler);
		GridBagConstraints bag2 = new GridBagConstraints();
        bag2.gridx = 5;
        bag2.gridy = 0;
        bag2.gridwidth = 5;
        bag2.gridheight = 1;
        bag2.weightx = 0.5;
        bag2.weighty = 0;
        bag2.insets = new Insets(5,2,5,0);
        bag2.fill = GridBagConstraints.BOTH;
        bag2.anchor = GridBagConstraints.CENTER;
        control.add(delete, bag2);
        
        addT = new JButton("新增烏龜");
        addT.setFont(font);
        addT.addActionListener(handler);
        GridBagConstraints bag3 = new GridBagConstraints();
        bag3.gridx = 0;
        bag3.gridy = 1;
        bag3.gridwidth = 5;
        bag3.gridheight = 1;
        bag3.weightx = 0.5;
        bag3.weighty = 0;
        bag3.insets = new Insets(0,0,5,2);
        bag3.fill = GridBagConstraints.BOTH;
        bag3.anchor = GridBagConstraints.CENTER;
        control.add(addT, bag3);
        
		clear = new JButton("移除全部");
		clear.setFont(font);
		clear.addActionListener(handler);
		GridBagConstraints bag4 = new GridBagConstraints();
        bag4.gridx = 5;
        bag4.gridy = 1;
        bag4.gridwidth = 5;
        bag4.gridheight = 1;
        bag4.weightx = 0.5;
        bag4.weighty = 0;
        bag4.insets = new Insets(0,2,5,0);
        bag4.fill = GridBagConstraints.BOTH;
        bag4.anchor =  GridBagConstraints.CENTER;
        control.add(clear, bag4);
        
        status = new JPanel();
        GridBagConstraints bag8 = new GridBagConstraints();
		bag8.gridx = 0;
		bag8.gridy = 3;
		bag8.gridwidth = 10;
		bag8.gridheight = 1;
		bag8.weightx = 1;
		bag8.weighty = 0;
		bag8.fill = GridBagConstraints.EAST;
		bag8.anchor = GridBagConstraints.WEST;
		control.add(status, bag8);
      	func = new JLabel("目前功能:"+mode[modenum]);
		func.setFont(font);
		status.setLayout(new FlowLayout());
		status.add(func,FlowLayout.LEFT);
		FA = new JLabel("魚數量: "+FL.size());
		FA.setFont(font);
		status.add(FA);
		TA = new JLabel("烏龜數量: "+TL.size());
		TA.setFont(font);
		status.add(TA);
		bowl = new JPanel();
		add(control,BorderLayout.NORTH);
		add(bowl,BorderLayout.CENTER);
		bowl.setLayout(null);
		bowl.setBackground(new Color(100,200,255));
		bowl.setBounds(0,0,1080,625);
		width = bowl.getWidth();
		height = bowl.getHeight();
		
		
		
		bowl.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)//當滑鼠按壓時
			{		
				int x1=e.getX();//取得滑鼠按下時的X座標
				int y1=e.getY();//取得滑鼠按下時的Y座標
//			
				if(modenum == 0) {
					FL.add(new Fish(width,height,x1,y1,generator.nextInt(50)+100,generator.nextInt(2),generator.nextInt(2),generator.nextInt(3)));
					bowl.add(FL.get(FL.size()-1));
					thread.add(new Thread(FL.get(FL.size()-1)));
					thread.get(thread.size()-1).start();
					FA.setText("魚數量: "+FL.size());
					FL.get(FL.size()-1).setNum(Fcount);
					Fcount += 1;
					FL.get(FL.size()-1).addMouseListener(new MouseAdapter()
					{
						@Override
						public void mousePressed(MouseEvent e)//當滑鼠按壓時
						{						
							 if(modenum == 2) {
								if(e.getSource() instanceof Fish) {
									Fish f = (Fish)e.getSource();
									int i = f.getNum();
									FL.get(i).stop();
									FL.remove(i);
									bowl.remove((Fish)e.getSource());;
									bowl.repaint();
									
									for(Fish fish : FL) {
										if(fish.getNum()>i) {
											fish.setNum(fish.getNum()-1);
										}
									}
									Fcount -= 1;
									FA.setText("魚數量: "+FL.size());
							}
						
						}
						}
					}
										);
				}
				else if(modenum == 1) {
					TL.add(new Turtle(width,height,x1,y1,generator.nextInt(50)+100,generator.nextInt(2)));
					bowl.add(TL.get(TL.size()-1));
					thread.add(new Thread(TL.get(TL.size()-1)));
					thread.get(thread.size()-1).start();
					TA.setText("烏龜數量: "+TL.size());
					TL.get(TL.size()-1).setNum(Tcount);
					Tcount += 1;
					TL.get(TL.size()-1).addMouseListener(new MouseAdapter()
					{
						@Override
						public void mousePressed(MouseEvent e)//當滑鼠按壓時
						{						
							 if(modenum == 2) {
								if(e.getSource() instanceof Turtle) {
									Turtle t = (Turtle)e.getSource();
									int i = t.getNum();
									TL.get(i).stop();
									TL.remove(i);
									bowl.remove((Turtle)e.getSource());;
									bowl.repaint();
									
									for(Turtle turtle : TL) {
										if(turtle.getNum()>i) {
											turtle.setNum(turtle.getNum()-1);
										}
									}
									Tcount -= 1;
									TA.setText("烏龜數量: "+TL.size());
							}
						
						}
						}
					}
										);
				}
//				else if(modenum == 2) {
////					if(e.getSource() instanceof Fish) {
//						Fish f = (Fish)e.getSource();
//						int i = f.getNum();
//						FL.get(i).stop();
//						FL.remove(i);
//						bowl.remove((Fish)e.getSource());;
//						bowl.repaint();
//						System.out.print(i);
//						
//						
//				
////					}
//					
//				}
			
			}
		
		}
							);
	}
	private class ButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getActionCommand().equals("新增魚")) {
				modenum = 0;
				func.setText("目前功能:"+mode[modenum]);
				
				

				
			}
			else if(event.getActionCommand().equals("新增烏龜")) {
				modenum = 1;
				func.setText("目前功能:"+mode[modenum]);
				
				
			}
			else if(event.getActionCommand().equals("移除全部")) {
				func.setText("目前功能:"+mode[modenum]);
				for(Fish fish:FL) {
					fish.stop();
				}
				FL.removeAll(FL);
				TL.removeAll(TL);
				FA.setText("魚數量: "+FL.size());
				TA.setText("烏龜數量: "+FL.size());
				
				bowl.removeAll();
				bowl.repaint();
			}
			else if(event.getActionCommand().equals("移除選取")) {
				modenum = 2;
				func.setText("目前功能:"+mode[modenum]);
				
				
			}
		}
		
	}
}
