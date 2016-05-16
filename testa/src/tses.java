import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Vector;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class WavExample extends JFrame implements  LineListener {
	   /* Swing 디자인 부분 */
	   private JPanel pan = new JPanel();
	   
	   
	   /* 음악 재생을 돕는 녀석 */
	   private Clip clip;
	   
	   /* 스탑버튼 클릭여부 */
	   private boolean isStop = false;
	   
	   /* wav 파일 지정*/
	   private File wavFile; 
	   /* wav 파일을 Stream으로 만들어 주는 녀석*/
	   private AudioInputStream ais;
	   
	   public WavExample(String filePath) {
		  wavFile = new File(filePath);
	      this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	      this.setBounds(100, 100, 200, 80);
	      this.setTitle("WavExample");	      

	      /* Swing 디자인 부분 */
	      
	      this.add(pan);
	      
	      try {
	         /* wav파일 stream으로 로딩 */
	         ais = AudioSystem.getAudioInputStream(wavFile);
	         /* clip 가져옴 */
	         clip = AudioSystem.getClip();
	         
	         /* AudioInputStream 로딩 */
	         clip.open(ais);
	         
	         /* 무한 반복을 위해 LineEvent 등록 > update 호출함*/
	         clip.addLineListener(this);
	         clip.start();
	      } catch (Exception e) {
	      }
	      
	      this.setVisible(false);
	   }

	   @Override
	   public void update(LineEvent le) {
	      /* Stop 버튼을 누르지 않았을 경우, 그리고 STOP 되었을 경우 */
	      if(!isStop && le.getType() == LineEvent.Type.STOP ) {
	         try {
	            /* wav파일 시작위치 맨 앞으로 이동 */
	            clip.setMicrosecondPosition(0);
	            /* wav 시작 */
	            clip.start();
	         } catch(Exception e) {
	         }
	      }
	   }
	}

public class tses extends JFrame {
	String filePath = "";
	xmlread xml;
	Node blockGameNode;
	JButton start,end;
	JFrame j = new JFrame();
	public ImageIcon imgIcon = new ImageIcon("images/mainbackground.png");
	public Image img = imgIcon.getImage();
	public tses() {
		setTitle("블록게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new bon());
		start = new JButton(new ImageIcon("images/gamestart.png"));
		start.setSize(150,50);
		start.setLocation(250, 350);
		
		end = new JButton(new ImageIcon("images/gameexit.png"));
		end.setSize(150,50);
        end.setLocation(420, 350);
        
		JMenuBar menuBar = new JMenuBar();
		JMenu newmenu = new JMenu("gameload");
		menuBar.add(newmenu);
		JMenuItem mload = new JMenuItem("Load");
		newmenu.add(mload);
		mload.addActionListener(new ActionListener() {
			JFileChooser chooser = new JFileChooser();

			public void actionPerformed(ActionEvent e) {
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"XML", "xml", "XML");
				chooser.setFileFilter(filter);
				int ret = chooser.showOpenDialog(null);
				if (ret != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다", "경고",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				filePath = chooser.getSelectedFile().getPath();
				filePath = filePath.replace("\\", "/");
				int file = filePath.lastIndexOf("/");
				int ext = filePath.lastIndexOf(".");
				int length = filePath.length();
				String filename = filePath.substring(file + 1, ext);

				String extname = filePath.substring(ext + 1, length);
				filePath = filename + "." + extname;
				xml = new xmlread(filePath);
				blockGameNode = xml.getBlockGameElement();
				System.out.println(filePath);
				
			}
		});
		
		
		//this.repaint();
		
		
		add(start);
		add(end);
		start.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(filePath.equals(""))
					System.exit(0);
				j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				j.setSize(800, 600);
				j.setVisible(true);
				j.setContentPane(new gamepanel(xml.getGamePanelElement()));
				repaint();
				setVisible(false);
				//new WavExample();
			}
		});
		end.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		setJMenuBar(menuBar);
		setSize(800, 600);
		setVisible(true);
	}
	
	class bon extends JPanel
	{
		public bon()
		{
			setLayout(null);
		}
		public void paintComponent(Graphics g)
		{
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}
	/*
	 * void menu() { JMenuBar menuBar = new JMenuBar(); JMenu newmenu = new
	 * JMenu("gameload"); menuBar.add(newmenu); JMenuItem mload = new
	 * JMenuItem("Load"); newmenu.add(mload); mload.addActionListener(new
	 * ActionListener(){ JFileChooser chooser = new JFileChooser();
	 * 
	 * public void actionPerformed(ActionEvent e) { FileNameExtensionFilter
	 * filter = new FileNameExtensionFilter( "XML", "xml","XML");
	 * chooser.setFileFilter(filter); int ret = chooser.showOpenDialog(null); if
	 * (ret != JFileChooser.APPROVE_OPTION) {
	 * JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다", "경고",
	 * JOptionPane.WARNING_MESSAGE); return; }
	 * 
	 * filePath = chooser.getSelectedFile().getPath(); filePath =
	 * filePath.replace("\\","/"); int file = filePath.lastIndexOf("/"); int ext
	 * = filePath.lastIndexOf("."); int length = filePath.length(); String
	 * filename = filePath.substring(file+1,ext);
	 * 
	 * String extname = filePath.substring(ext+1,length); xml = new
	 * xmlread(filePath); blockGameNode = xml.getBlockGameElement();
	 * System.out.println(filePath); filePath = filename+"."+extname;
	 * this.setContentPane(new gamepanel(xml.getGamePanelElement())); } });
	 * this.setJMenuBar(menuBar); }
	 */
	public static void main(String[] args) {
		new tses();
	}
}

class Block extends JPanel implements Runnable {
	Image img;
	int x, y, w, h, type, dx = 1;
	int stoncount = 0;
	Thread th;

	public Block(int x, int y, int w, int h, int type, ImageIcon icon) {
		this.x = x;
		this.w = w;
		this.y = y;
		this.h = h;
		this.type = type;
		this.setBounds(this.x, this.y, w, h);
		img = icon.getImage();

		if (this.type == 5)
			stoncount = 1;
		th = new Thread(this);
		th.start();
	}

	public void run() {
		while (true) {
			try {
				if(type == 0)
					Thread.sleep(100);
				else if(type == 1)
					Thread.sleep(30);
				else if(type == 2)
					Thread.sleep(50);
				else if(type == 3)
					Thread.sleep(5);
				else if(type == 4)
					Thread.sleep(10);
				else if(type == 5)
					Thread.sleep(80);
			} catch (InterruptedException e) {
				return;
			}

			if (x > 760) {
				dx *= -1;
			}
			if (x < 0)
				dx *= -1;
			x += dx;

			this.setBounds(x, y, w, h);
			// repaint();
		}
	}

	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}

class gamepanel extends JPanel {
	ImageIcon bgImg, barImg, ballImg, lifeImg;
	Node barNode, ball, life;
	int bax, ballx, bally, lifetype;
	int ballinitx, ballinity, score = 0;
	
	public static Vector<Block> bloc = new Vector<Block>();

	public gamepanel(Node gmaePanelNode) {
		setLayout(null);
		Node bgm = xmlread.getNode(gmaePanelNode, xmlread.E_BGM);
		String filePath = bgm.getTextContent();
		filePath = filePath.replace("\\", "/");
	    int file = filePath.lastIndexOf("/");
	    int ext = filePath.lastIndexOf(".");
	    int length = filePath.length();
	    String filename = filePath.substring(file + 1, ext);
	    String extname = filePath.substring(ext + 1, length);
	    filePath = "sound/" +filename + "." + extname;
		System.out.println(filePath);
		
		new WavExample(filePath);
		Node bgNode = xmlread.getNode(gmaePanelNode, xmlread.E_BG);
		bgImg = new ImageIcon(bgNode.getTextContent());

		barNode = xmlread.getNode(gmaePanelNode, xmlread.E_BAR);
		barImg = new ImageIcon(xmlread.getAttr(barNode, "img"));
		bax = Integer.parseInt(xmlread.getAttr(barNode, "x"));

		ball = xmlread.getNode(gmaePanelNode, xmlread.E_BALL);
		ballImg = new ImageIcon(xmlread.getAttr(ball, "img"));

		life = xmlread.getNode(gmaePanelNode, xmlread.E_LIFE);
		lifeImg = new ImageIcon(xmlread.getAttr(life, "img"));
		
		Node blockNode = xmlread.getNode(gmaePanelNode, xmlread.E_BLOCK);
		NodeList nodeList = blockNode.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() != Node.ELEMENT_NODE)
				continue;

			if (node.getNodeName().equals(xmlread.E_OBJ)) {
				int x = Integer.parseInt(xmlread.getAttr(node, "x"));
				int y = Integer.parseInt(xmlread.getAttr(node, "y"));
				int w = Integer.parseInt(xmlread.getAttr(node, "w"));
				int h = Integer.parseInt(xmlread.getAttr(node, "h"));
				int type = Integer.parseInt(xmlread.getAttr(node, "type"));

				ImageIcon icon = new ImageIcon(xmlread.getAttr(node, "img"));

				Block block = new Block(x, y, w, h, type, icon);
				add(block);
				bloc.addElement(block);
			}
		}

		addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {

				bax = e.getX();

				/*
				 * switch (keycode) { case KeyEvent.VK_RIGHT: bax += 5; break;
				 * case KeyEvent.VK_LEFT: bax -= 5; break; }
				 */
				repaint();
			}
		});
		ballx = Integer.parseInt(xmlread.getAttr(ball, "x"));
		bally = Integer.parseInt(xmlread.getAttr(ball, "y"));
		ballinitx = Integer.parseInt(xmlread.getAttr(ball, "x"));
		ballinity = Integer.parseInt(xmlread.getAttr(ball, "y"));
		lifetype = Integer.parseInt(xmlread.getAttr(life, "type"));
		ballthread th = new ballthread(bloc, ball, barNode);
		th.start();
	}

	public void paintComponent(Graphics g) {
		g.drawImage(bgImg.getImage(), 0, 0, 800, 600, this);
		g.drawImage(barImg.getImage(), bax,
				Integer.parseInt(xmlread.getAttr(barNode, "y")),
				Integer.parseInt(xmlread.getAttr(barNode, "w")),
				Integer.parseInt(xmlread.getAttr(barNode, "h")), this);
		g.drawImage(ballImg.getImage(), ballx, bally,
				Integer.parseInt(xmlread.getAttr(ball, "w")),
				Integer.parseInt(xmlread.getAttr(ball, "h")), this);
		g.drawImage(lifeImg.getImage(), 0, 0,
				Integer.parseInt(xmlread.getAttr(life, "w")),
				Integer.parseInt(xmlread.getAttr(life, "h")), this);
		g.setFont(new Font("Arial", Font.ITALIC, 30));
		g.setColor(Color.BLACK);
		if(lifetype == -1)
		{
			g.drawString("X" + lifetype, 25, 25);
		}
		else
			g.drawString("X" + lifetype, 25, 25);
		g.drawString("SCORE " + score, 600, 25);
		if(bloc.size() == 0)
		{
			g.setFont(new Font("Arial", Font.ITALIC, 100));
			g.setColor(Color.RED);
			g.drawString("Game Win", 150, 200);
		}
		
		if(lifetype == -1)
		{
			g.setFont(new Font("Arial", Font.ITALIC, 100));
			g.setColor(Color.BLUE);
			g.drawString("Game End", 150, 200);
		}
	}

	class ballthread extends Thread {
		Node ball, bar;
		int select, bay, speed = 10;
		int vx = 3, vy = -3;
		Vector<Block> bloc;

		public ballthread(Vector<Block> bloc, Node ball, Node bar) {
			this.ball = ball;
			this.bloc = bloc;
			this.bar = bar;

			bay = Integer.parseInt(xmlread.getAttr(bar, "y"));
			repaint();
		}

		public void run() {
			while (true) {
				
				if(bloc.size() == 0)
				{
					repaint();
					return;
				}
				if (hit()) {
					if (targetContains(ballx + 10 - 1, bally + 10 - 1)
							|| targetContains(ballx, bally + 10 - 1)) {
						vx *= 1;
						vy = -3;
					} else {
						vx *= 1;
						vy = 3;
					}
					if (bloc.get(select).type == 0) 
					{
						bloc.get(select).setVisible(false);
						bloc.remove(select);
					} 
					else if (bloc.get(select).type == 1) 
					{
						if (bloc.size() == 1) 
						{
							bloc.get(select).setVisible(false);
							bloc.remove(select);
						} 
						else 
						{
							bloc.get(select).setVisible(false);
							bloc.remove(select);
							
							
							int random = (int) (Math.random() * bloc.size());;
							bloc.get(random).setVisible(false);
							bloc.remove(random);
						}
					} 
					else if (bloc.get(select).type == 2) 
					{
						speed -= 3;
						bloc.get(select).setVisible(false);
						bloc.remove(select);
					} 
					else if (bloc.get(select).type == 3) 
					{
						lifetype++;
						bloc.get(select).setVisible(false);
						bloc.remove(select);
					} 
					else if (bloc.get(select).type == 4) 
					{
						speed += 3;
						bloc.get(select).setVisible(false);
						bloc.remove(select);
					}
					else if (bloc.get(select).type == 5) 
					{
						if (bloc.get(select).stoncount == 0) {
							bloc.get(select).setVisible(false);
							bloc.remove(select);
						} else {
							bloc.get(select).stoncount--;
						}
					}
					score += 10;
				}
				// ((bax <= ballx+9) && (bax + +5 >= ballx+9)) && ((bay <= bally+9) && (bay+15 >= bally+9))
				// ((bax <= ballx) && (bax + +5 >= ballx)) && ((bay <= bally+9) && (bay+15 >= bally+9))
				if (barhit()) 
				{
					if(((bax <= ballx+9) && (bax +10 >= ballx+9)) && ((bay <= bally+9) && (bay+15 >= bally+9))||
							((bax <= ballx) && (bax +10 >= ballx)) && ((bay <= bally+9) && (bay+15 >= bally+9)))
					{
						vx = -5;
						vy = -3;
					}
					else if(((bax+55 <= ballx+9) && (bax + 65 >= ballx+9)) && ((bay <= bally+9) && (bay+15 >= bally+9))||
							((bax+55 <= ballx) && (bax + 65 >= ballx)) && ((bay <= bally+9) && (bay+15 >= bally+9)))
					{
						vx = 5;
						vy = -3;
					}
					else
					{
						vx /= Math.abs(vx);
						vx *= 3;
						vy = -3;
					}
				}

				if (ballx >= 790) {
					vx = -3;
				}
				if (ballx < 0) {
					vx = 3;
				}
				if (bally < 0) {
					vy = 3;
				}
				if (bally > 600) {
					lifetype--;
					ballx = ballinitx;
					bally = ballinity;
					vy *= -1;
					repaint();
				}
				if (lifetype == -1) {
					repaint();
					return;
				}

				try {
					sleep(speed);
				} catch (InterruptedException e) {
					return;
				}

				ballx += vx;
				bally += vy;
				repaint();
			}
		}

		private boolean hit() {
			if (targetContains(ballx, bally)
					|| targetContains(ballx + 10 - 1, bally)
					|| targetContains(ballx + 10 - 1, bally + 10 - 1)
					|| targetContains(ballx, bally + 10 - 1))
				return true;
			else
				return false;
		}

		private boolean targetContains(int x, int y) {
			for (int i = 0; i < bloc.size(); i++) {
				if (((bloc.get(i).getLocation().x <= x) && (bloc.get(i)
						.getLocation().x + 40 >= x))
						&& ((bloc.get(i).getLocation().y <= y) && (bloc.get(i)
								.getLocation().y + 20 >= y))) {
					select = i;
					return true;
				}
			}
			return false;
		}

		private boolean barhit() {
			if (barcontain(ballx, bally) || barcontain(ballx + 10 - 1, bally)
					|| barcontain(ballx + 10 - 1, bally + 10 - 1)
					|| barcontain(ballx, bally + 10 - 1))
				return true;
			else
				return false;
		}

		private boolean barcontain(int x, int y) {
			if (((bax <= x) && (bax + 65 - 1 >= x))
					&& ((bay <= y) && (bay + 15 - 1 >= y))) {
				return true;
			} else
				return false;
		}
	}
}