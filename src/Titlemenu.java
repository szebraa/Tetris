import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Titlemenu extends Menu
{
	private BufferedImage title, controls, play_buttons;
	private BufferedImage [] title_imgs;
	private int pos_x,pos_y, window_width, window_height;
	private Timer timer;
	private Tetris window;
	private Rectangle bounds;
	private final int FPS = 60;
	
	private boolean leftClicked;
	
	
	private BufferedImage [] playButton = new BufferedImage[2];
	private final int delay = 1000/FPS;
	
	
	
	
	public Titlemenu(Tetris window,String [] title_menu_paths)
	{
		super(window,title_menu_paths);
		title_imgs = super.getImages();
		
		//call repaint (paintComponent) constantly (on a timer)
		timer = new Timer(delay, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//paintComponent
				repaint();
			}
		});
		
		timer.start();
		
		//init mouse starting position
		pos_x = 0;
		pos_y = 0;
		
		//get changing play buttons (changes color when scrolled over)
		playButton[0] = title_imgs[2].getSubimage(0, 0, 100, 80);
		playButton[1] = title_imgs[2].getSubimage(100, 0, 100, 80);
		this.window = window;
		window_width = window.getWidth();
		window_height = window.getHeight();
		
		//get bounds of the play button
		bounds = new Rectangle(window_width/2 - 50, window_height/2 - 100, 100, 80);
		
	}
	
	
	//called implicitly (auto), and through repaint() method
	public void paintComponent(Graphics g)
	{
		//ensures that the background (non-shapes) are painted properly (i.e.: ensures other tiles dont become shapes)
		super.paintComponent(g);
		
		//go to main game if start button is clicked
		if(leftClicked && bounds.contains(pos_x, pos_y))
		{
			timer.stop();
			window.startGame();
		}
		
		//set whole background to black
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, window_width, window_height);
		

		g.drawImage(title_imgs[0], window_width/2 - title_imgs[0].getWidth()/2, window_height/2 - title_imgs[0].getHeight()/2 - 200, null);
		g.drawImage(title_imgs[1], window_width/2 - title_imgs[1].getWidth()/2,
				window_height/2 - title_imgs[2].getHeight()/2 + 50, null);
		
		//changes color of play button depending on if hover over it or not
		if(bounds.contains(pos_x, pos_y))
			g.drawImage(playButton[0], window_width/2 - 50, window_height/2 - 100, null);
		else
			g.drawImage(playButton[1], window_width/2 - 50, window_height/2 - 100, null);
		
	}
	
	//check if mouse left click is clicked
	@Override
	public void mousePressed(MouseEvent e) 
	{
		if(e.getButton() == MouseEvent.BUTTON1)
			leftClicked = true;

	}
	//check if mouse left click is released
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		if(e.getButton() == MouseEvent.BUTTON1)
			leftClicked = false;

	}
	
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		//if we include this that means that you can click off the play button, drag to it, then the game will start (incorrect function)
		//pos_x = e.getX();
		//pos_y = e.getY();

	}

	//update mouse position
	@Override
	public void mouseMoved(MouseEvent e) 
	{
		pos_x = e.getX();
		pos_y = e.getY();

	}
	
	
	
	
	

	@Override
	public void mouseClicked(MouseEvent e) 
	{	
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	
	
	

}
