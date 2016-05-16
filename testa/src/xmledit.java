import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.StreamResult;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Vector;

public class xmledit extends JFrame {
	String filePath;
	String mp3Path = "sound/bgm.wav";
	Vector<JLabel> label = new Vector<JLabel>();
	JLabel imageLabel[] = new JLabel[7];
	ImageIcon image[] = new ImageIcon[8];
	ImageIcon ball[] = new ImageIcon[3];
	JLabel copy;
	JButton Life;
	String life, ballpath, lifetype="1";
	JTextField tf = new JTextField(lifetype,2);
	int count = 0;
	int inx[][] = { { 700, 30 }, { 700, 80 }, { 700, 130 }, { 700, 180 },
			{ 700, 230 }, { 700, 280 }, { 300, 500 } };
	String pa[] = { "images/baseS.png", "images/bombS.png",
			"images/flameS.png", "images/HealS.png", "images/MonsterS.png",
			"images/stoneS.png", "images/bar.png" };

	public xmledit() {
		// 메뉴 설계
		setTitle("블록게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 큰 레이아웃
		setSize(800, 600);
		setContentPane(new gamepanel());
		menu();
		setVisible(true);

	}

	// gamepanel 설계
	class gamepanel extends JPanel {
		mouselistner listner = new mouselistner();

		public gamepanel() {
			setLayout(null);

			for (int i = 0; i < 7; i++) {
				image[i] = new ImageIcon(pa[i]);
				imageLabel[i] = new JLabel(image[i]);
				imageLabel[i].setLocation(inx[i][0], inx[i][1]);
				if (i != 6)
					imageLabel[i].setSize(40, 20);
				else
					imageLabel[i].setSize(60, 20);
				imageLabel[i].addMouseListener(listner);
				imageLabel[i].addMouseMotionListener(listner);

				add(imageLabel[i]);
			}
			Life = new JButton("Life");

			Life.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					life = JOptionPane.showInputDialog("목숨 값을 입력하세요(10 미만)");
					if (life != null)
					{
						tf.setText(life);
						lifetype = tf.getText();
					}
				}
			});

			Life.setLocation(630, 350);
			Life.setSize(60, 30);
			tf.setLocation(700, 350);
			tf.setSize(20, 30);
			tf.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JTextField t = (JTextField) e.getSource();
					tf.setText(t.getText());
					lifetype = tf.getText();
				}
			});
			add(Life);
			add(tf);

			// 볼 라디오 버튼
			ButtonGroup g = new ButtonGroup();

			JRadioButton ball1 = new JRadioButton("ball1", true);
			JRadioButton ball2 = new JRadioButton("ball2");
			JRadioButton ball3 = new JRadioButton("ball3");

			ball[0] = new ImageIcon("images/ball1.jpg");
			ball[1] = new ImageIcon("images/ball2.jpg");
			ball[2] = new ImageIcon("images/ball3.jpg");

			JLabel bal1 = new JLabel(ball[0]);
			bal1.setSize(10, 10);
			bal1.setLocation(685, 420);
			add(bal1);
			bal1.setVisible(true);

			JLabel bal2 = new JLabel(ball[1]);
			bal2.setSize(10, 10);
			bal2.setLocation(685, 460);
			add(bal2);
			bal2.setVisible(false);

			JLabel bal3 = new JLabel(ball[2]);
			bal3.setSize(10, 10);
			bal3.setLocation(685, 500);
			add(bal3);
			bal3.setVisible(false);

			g.add(ball1);
			g.add(ball2);
			g.add(ball3);
			ball1.setBounds(610, 400, 70, 50);
			ball2.setBounds(610, 440, 70, 50);
			ball3.setBounds(610, 480, 70, 50);

			ballpath = "images/ball1.jpg";

			ball1.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						bal1.setVisible(true);
						ballpath = "images/ball1.jpg";
					} else {
						bal1.setVisible(false);
					}
				}
			});

			ball2.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						bal2.setVisible(true);
						ballpath = "images/ball2.jpg";
					} else {
						bal2.setVisible(false);
					}
				}
			});
			ball3.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						bal3.setVisible(true);
						ballpath = "images/ball3.jpg";

					} else {
						bal3.setVisible(false);
					}
				}
			});

			add(ball1);
			add(ball2);
			add(ball3);

		}

		public void paintComponent(Graphics g) { // 백그라운드
			super.paintComponent(g);
			image[7] = new ImageIcon("images/main.jpg");
			Image img = image[7].getImage();
			g.drawImage(img, 0, 0, 600, 600, this);
		}

		class mouselistner extends MouseAdapter {

			public void mousePressed(MouseEvent e) {
				for (int i = 0; i < 7; i++) {
					if (e.getSource() == imageLabel[i]) {
						copy = new JLabel(image[i]);
						copy.setSize(40, 20);
						copy.setLocation(imageLabel[i].getLocation().x,
								imageLabel[i].getLocation().y);

						add(copy);
					}
				}

				// 구현바람
				for (int i = 0; i < label.size(); i++) {
					if (e.getSource() == label.get(i)) {
						label.get(i).setLocation(
								label.get(i).getLocation().x + e.getX() - 20,
								label.get(i).getLocation().y + e.getY() - 10);
					}
				}
			}

			public void mouseReleased(MouseEvent e) {
				for (int i = 0; i < 7; i++) {
					if (e.getSource() == imageLabel[i]) {
						copy.setLocation(
								imageLabel[i].getLocation().x + e.getX() - 20,
								imageLabel[i].getLocation().y + e.getY() - 10);
						if (copy.getLocation().x + 40 > 600
								|| copy.getLocation().y + 20 > 400) {
							copy.setLocation(inx[i][0], inx[i][1]);
							remove(copy);
						} else {
							label.add(copy);
							label.get(count).addMouseListener(listner);
							label.get(count).addMouseMotionListener(listner);
							count++;
						}
					}

				}

				for (int i = 0; i < label.size(); i++) {
					JLabel sa = (JLabel) e.getSource();
					if (sa == label.get(i)) {
						label.get(i).setLocation(label.get(i).getLocation().x + e.getX() - 20, label.get(i).getLocation().y + e.getY() - 10);
						if (label.get(i).getLocation().x + 40 > 600 || label.get(i).getLocation().y + 20 > 400) 
						{
							for(int j=0; j<7; j++)
							{
								if(sa.getIcon().toString().equals(pa[j]))
								{
									label.get(i).setLocation(inx[0][0], inx[0][1]);// label
																			// 초기위치
																			// 저장
																			// 설정
									label.get(i).removeMouseListener(listner);
									label.get(i).removeMouseMotionListener(listner);
									label.remove(i);
									count--;
								}
							}
						} 
						else 
						{
							label.get(i).setLocation(label.get(i).getLocation().x + e.getX() - 20, label.get(i).getLocation().y + e.getY() - 10);
						}
					}
				}
			}

			public void mouseDragged(MouseEvent e) {
				for (int i = 0; i < 7; i++) {
					if (e.getSource() == imageLabel[i]) {
						copy.setLocation(
								imageLabel[i].getLocation().x + e.getX() - 20,
								imageLabel[i].getLocation().y + e.getY() - 10);
					}
				}
				for (int i = 0; i < label.size(); i++) {
					if (e.getSource() == label.get(i)) {
						label.get(i).setLocation(
								label.get(i).getLocation().x + e.getX() - 20,
								label.get(i).getLocation().y + e.getY() - 10);

					}
				}
			}

		}

	}

	void menu() {

		JMenuBar menuBar = new JMenuBar();
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		JMenuItem mntmSave = new JMenuItem("Save");
		mnNewMenu.add(mntmSave);
		mntmSave.addActionListener(new ActionListener() {
			JFileChooser chooser = new JFileChooser();

			public void actionPerformed(ActionEvent e) {
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"XML", "XML", "xml");
				chooser.setFileFilter(filter);

				int ret = chooser.showSaveDialog(null);
				if (ret != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다", "경고",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				File sav = chooser.getSelectedFile();

				// 이제 xml 만드는 코드
				try {
					DocumentBuilderFactory documentFactory = DocumentBuilderFactory
							.newInstance();
					DocumentBuilder documentBuilder = documentFactory
							.newDocumentBuilder();
					Document document = documentBuilder.newDocument();

					// root element
					Element root = document.createElement("Blockgame");
					document.appendChild(root);

					// child element

					Element gamepanel = document.createElement("gamepanel");
					root.appendChild(gamepanel);
					
					Element bgm = document.createElement("bgm");
					bgm.appendChild(document.createTextNode(mp3Path));
					gamepanel.appendChild(bgm);

					Element bg = document.createElement("bg");
					bg.appendChild(document.createTextNode("images/main.jpg")); // bgpath
					gamepanel.appendChild(bg);

					Element ball = document.createElement("ball");
					gamepanel.appendChild(ball);
					Attr bx = document.createAttribute("x");
					bx.setValue("430");
					ball.setAttributeNode(bx);
					Attr by = document.createAttribute("y");
					by.setValue("490");
					ball.setAttributeNode(by);
					Attr bw = document.createAttribute("w");
					bw.setValue("10");
					ball.setAttributeNode(bw);
					Attr bh = document.createAttribute("h");
					bh.setValue("10");
					ball.setAttributeNode(bh);
					Attr bimg = document.createAttribute("img");
					bimg.setValue(ballpath);
					ball.setAttributeNode(bimg);
					

					Element bar = document.createElement("bar");
					gamepanel.appendChild(bar);
					Attr bax = document.createAttribute("x");
					bax.setValue("400");
					bar.setAttributeNode(bax);
					Attr bay = document.createAttribute("y");
					bay.setValue("500");
					bar.setAttributeNode(bay);
					Attr baw = document.createAttribute("w");
					baw.setValue("65");
					bar.setAttributeNode(baw);
					Attr bah = document.createAttribute("h");
					bah.setValue("10");
					bar.setAttributeNode(bah);
					Attr baimg = document.createAttribute("img");
					baimg.setValue(pa[6]);
					bar.setAttributeNode(baimg);
					
					Element life = document.createElement("life");
					gamepanel.appendChild(life);
					Attr lx = document.createAttribute("x"); // life의 x, y, w, h
					lx.setValue("0");
					life.setAttributeNode(lx);
					Attr ly = document.createAttribute("y");
					ly.setValue("0");
					life.setAttributeNode(ly);
					Attr lw = document.createAttribute("w");
					lw.setValue("25");
					life.setAttributeNode(lw);
					Attr lh = document.createAttribute("h");
					lh.setValue("25");
					life.setAttributeNode(lh);
					Attr lt = document.createAttribute("type");
					lt.setValue(lifetype);
					life.setAttributeNode(lt);
					Attr limg = document.createAttribute("img");
					limg.setValue("images/life.jpg");
					life.setAttributeNode(limg);

					Element block = document.createElement("block");
					gamepanel.appendChild(block);
					
					// block child element
					//Element obj = document.createElement("obj");
					Element obj[] = new Element[label.size()];
					
					for(int i=0; i<label.size(); i++)
					{
						int x;
						String xx;
						
						obj[i] = document.createElement("obj");
						x= label.get(i).getLocation().x;
						xx = Integer.toString(x);
						Attr obx = document.createAttribute("x");
						obx.setValue(xx);
						obj[i].setAttributeNode(obx);
						
						x= label.get(i).getLocation().y;
						xx = Integer.toString(x);
						Attr oby = document.createAttribute("y");
						oby.setValue(xx);
						obj[i].setAttributeNode(oby);
						
						x= label.get(i).getWidth();
						xx = Integer.toString(x);
						Attr obw = document.createAttribute("w");
						obw.setValue(xx);
						obj[i].setAttributeNode(obw);
						
						x= label.get(i).getHeight();
						xx = Integer.toString(x);
						Attr obh = document.createAttribute("h");
						obh.setValue(xx);
						obj[i].setAttributeNode(obh);
						
						Attr obt = document.createAttribute("type");
						for(int j=0; j<7; j++)
						{
							if(label.get(i).getIcon().toString().equals(pa[j]))
								obt.setValue(Integer.toString(j));
						}
						obj[i].setAttributeNode(obt);
						
						Attr obimg = document.createAttribute("img");
						obimg.setValue(label.get(i).getIcon().toString());
						obj[i].setAttributeNode(obimg);
						
						block.appendChild(obj[i]);
					}
					
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					DOMSource domSource = new DOMSource(document);
					StreamResult streamResult = new StreamResult(sav.getPath()); // save
																					// url

					transformer.transform(domSource, streamResult);

				} catch (ParserConfigurationException pce) {
					pce.printStackTrace();
				} catch (TransformerException tfe) {
					tfe.printStackTrace();
				}

				/*
				 * try { FileWriter fw = new FileWriter(sav.getPath()); //
				 * fw.write(ca); //xml 파일 콜해서 쓰는거임 fw.flush(); fw.close(); }
				 * catch (Exception e1) { e1.getMessage(); }
				 */
			}
		});
/* 파일 로드 구현 부분
		JMenuItem mntmLoad = new JMenuItem("Load");
		mnNewMenu.add(mntmLoad);
		mntmLoad.addActionListener(new ActionListener() {
			JFileChooser chooser = new JFileChooser();

			public void actionPerformed(ActionEvent e) {
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"XML", "xml","XML");
				chooser.setFileFilter(filter);
				int ret = chooser.showOpenDialog(null);
				if (ret != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다", "경고",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				String filePath = chooser.getSelectedFile().getPath();
				xmlread xml = new xmlread(filePath);
			}
		});
*/
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnNewMenu.add(mntmExit);
		mntmExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		JMenu mnBgm = new JMenu("Bgm");
		menuBar.add(mnBgm);

		JMenuItem mntmLoad_1 = new JMenuItem("Load");
		mnBgm.add(mntmLoad_1);
		mntmLoad_1.addActionListener(new ActionListener() {
			JFileChooser chooser = new JFileChooser();

			public void actionPerformed(ActionEvent e) {
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"MP3 file", "mp3");
				chooser.setFileFilter(filter);
				int ret = chooser.showOpenDialog(null);
				if (ret != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다", "경고",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				mp3Path = chooser.getSelectedFile().getPath();
				System.out.println(mp3Path);
			}
		});

		this.setJMenuBar(menuBar);
	}

	public static void main(String[] args) {
		new xmledit();
	}
}