package Jeux;
import java.applet.Applet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Jeux extends Applet implements Runnable, KeyListener, MouseListener{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//TextField text = new TextField(10);
	//Choice c = new Choice();
	//Label l1 = new Label("enter ur name");
	Color ball = Color.blue;
	Color fond = Color.green;
	String game = "Menu";
//	Menu menu;
	//File file = new File("147_full_deux-dynamix_0192_preview.wav");
	File file2 = new File("Game Over Sound Effect.wav");
	
	Clip clip;
	Clip clip2;
	int i;
	int speed = 7;
	int d = 30;
	int y = 1;
	ArrayList<Integer> list = new ArrayList<Integer>();
	Random r = new Random();
	int res ;
	int x = r.nextInt(800);
	int longuer, largeur; // longuer et largeur de la fenaitre
	int dx = -1; // translation with x
	int seconde;
	int dy = 1; //translation with y
	int xx, yy; // position of reactangle
	Thread t ; //what is Thread ? and Runnable
	Timer timer = new Timer();
	String secondes ;
	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			seconde++;
			System.out.println(seconde);
			if (seconde % 20 == 0) {
				if(speed > 1)
					speed -= 1;
				}
		}
	};
	@Override
	public void init() {
		this.setSize(800, 600);
	//	add(l1);
	//	add(text);
		try {
			//AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(file2);

			clip = AudioSystem.getClip();
			//clip.open(audioStream);
			clip.start();
			clip2 = AudioSystem.getClip();
			clip2.open(audioStream2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}		
		list.add(1);
		list.add(-1);
		res = list.get(r.nextInt(list.size()));
		dx = res * dx;
		this.addKeyListener(this);
		this.addMouseListener(this);
		longuer = getWidth();
		largeur = getHeight();
		xx = 375;
		yy = largeur - 30;
		this.setBackground(Color.BLACK);
	}
	@Override
	public void start() {
		
	}
	
	// paint not in Applet but in Graphics
	
	// on utlise paint pour designer
	@Override
	public void paint(Graphics g) {
		Font myFont = new Font("Courier New", 1, 40);
		if(game.equals("Menu")) {
			g.setFont(myFont);
			g.setColor(Color.white);
			g.drawString("jeux de raquet", 230, 70);
			g.fillOval(380, 91, 40, 40);
			g.fillRect(350, 130, 100, 20);
			g.drawRect(300, 180, 200, 100);
			g.drawString("PLAY", 350 , 240);
			g.drawRect(300, 300, 200, 100);
			g.drawString("LEVEL", 340 , 360);
			g.drawRect(300, 420, 200, 100);
			g.drawString("COULEUR", 315 , 480);
		}
		if(game.equals("level")) {
			g.setColor(Color.white);
			g.drawRect(300, 180, 200, 100);
			g.setFont(myFont);
			g.drawString("LEVEL 1", 320 , 240);
			g.drawRect(300, 300, 200, 100);
			g.drawString("LEVEL 2", 320 , 360);
		}
		if(game.equals("couleur")) {
			g.setColor(Color.white);
			g.setFont(myFont);
			g.drawString("raquet et la balle: ", 0, 200);
			g.fillRect(470, 170, 40, 40);
			g.setColor(Color.BLUE);
			g.fillRect(530, 170, 40, 40);
			g.setColor(Color.white);
			g.drawString("le fond  : ", 0, 300);
			g.setColor(Color.green);
			g.fillRect(300, 270, 40, 40);
			g.setColor(Color.yellow);
			g.fillRect(370, 270, 40, 40);
		}
		if(game.equals("game")) {
			if(i == 0) {
				t = new Thread(this);
				t.start();
				i = 1;
				timer.scheduleAtFixedRate(task, 1000, 1000);
			}
			this.setBackground(fond);
			g.setColor(ball);
			g.fillOval(x, y, d, d);
			g.fillRect(xx, yy, 110, 20);
			if((x >= xx + 110 || x <= xx - d) && y >= largeur - d) {
				clip.close();
				clip2.start();
				timer.cancel();
				this.setBackground(Color.black);
				g.setColor(Color.RED);
				//Font myFont = new Font("Courier New", 1, 40);
				g.setFont(myFont);
				g.drawString("GAME OVER", longuer/2-110, largeur/2-30);
				g.drawString("votre score :", longuer/2-140, largeur/2+20);
				int score = seconde * 3;
				secondes = String.valueOf(score);
				g.drawString(secondes, longuer/2-40, largeur/2+50);
			}
			g.setColor(Color.RED);
		//	Font myFont = new Font("Courier New", 10, 40); // (police, somke, taille)
			g.setFont(myFont);
			secondes = String.valueOf(seconde);
			g.drawString(secondes, longuer - 70, 50);
			g.drawString("s", longuer - 25, 50);
		}
	}
	@Override
	public void run() {

		// TODO Auto-generated method stub
		
		while (true) {
			    if(game.equals("game")) {
					if(y + d  >= largeur ) {
						if(x >= xx + 110 || x <= xx - d)
							break;
					}
					if(y + d + 27 >= largeur && (x <= xx + 110 && x >= xx - d))
						dy = -dy;
					if(x + d >= longuer) dx = -dx;
					if(x <= 0) dx = -dx;
					if(y  <= 0) dy = -dy;
					x = x+dx;
					y = y+dy;
				try {
					Thread.sleep(speed); //parceque sleep methode sleep methode statique pn peut aussi faire t.sleep
					
				}
				catch(Exception e) {
					System.out.println(e);
				}
			    }
			repaint();
		}
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		System.out.println(k);
		if(k == 39 && xx + 110 <= longuer) 
			xx += 10;
		if(k == 37 && xx >= 0) 
			xx-=10;
		
		
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if(mx > x && mx < x + width) {
			if(my > y && my < y + height)
				return true;
			else return false;
		}return false;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		if(game.equals("Menu")) {
		if (mouseOver(mx,my, 300, 180, 200, 100)) {
			game = "game";
			repaint();
		}
		if (mouseOver(mx,my, 300, 300, 200, 100)) {
			game = "level";
			repaint();
		}
		if (mouseOver(mx,my, 300, 420, 200, 100)) {
			game = "couleur";
			repaint();
		}
		
		}
		else if(game.equals("level")) {
			if (mouseOver(mx,my, 300, 180, 200, 100)) {
				speed = 7;
				game = "Menu";
				repaint();
			}
			if (mouseOver(mx,my, 300, 300, 200, 100)) {
				speed = 3;
				game = "Menu";
				repaint();
			}
		}
		else if(game.equals("couleur")) {
			if (mouseOver(mx,my, 470, 170, 40, 40)) {
				ball = Color.WHITE;
				game = "Menu";
				repaint();
			}
			else if(mouseOver(mx,my, 530, 170, 40, 40)) {
				ball = Color.blue;
				game = "Menu";
				repaint();
			}
			else if (mouseOver(mx,my, 300, 270, 40, 40)) {
				fond = Color.green;
				game = "Menu";
				repaint();
			}
			else if (mouseOver(mx,my, 370, 270, 40, 40)) {
				fond = Color.yellow;
				game = "Menu";
				repaint();
			}
		}
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}