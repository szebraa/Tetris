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
import java.awt.Cursor;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gamemenu extends Menu
{
	private Board board;
	private BufferedImage [] gamemenuimgs;
	private Timer timer;
	private Tetris window;
	private final int FPS = 60;
	private final int delay = 1000/FPS;
	private int pos_x, pos_y, window_width, window_height;
	public Gamemenu(Tetris window, String [] game_menu_paths, Board board)
	{
		super(window,game_menu_paths);
		//need this in order to resume the game once user selects "resume" option
		this.board = board;
		this.window = window;
		gamemenuimgs = super.getImages();
		window_width = window.getWidth();
		window_height = window.getHeight();
	}
	
	public void setTimer()
	{
		timer = new Timer(delay, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//paintComponent
				repaint();
			}
		});
		
	}
	
	public void setMouseCoords()
	{
		pos_x = 0;
		pos_y = 0;
	}
	
	
	//called implicitly (auto), and through repaint() method
	public void paintComponent(Graphics g)
	{
		//ensures that the background (non-shapes) are painted properly (i.e.: ensures other tiles dont become shapes)
		super.paintComponent(g);
		
		//set whole background to black
		//g.setColor(Color.BLACK);
		//g.fillRect(0, 0, window_width, window_height);
		g.drawImage(gamemenuimgs[3],0,0,null);
		g.drawImage(gamemenuimgs[0], window_width/2 - gamemenuimgs[0].getWidth()/2, 
				    window_height/2 - gamemenuimgs[0].getHeight()/2 - 200, null);
		g.drawImage(gamemenuimgs[1], window_width/2 - gamemenuimgs[1].getWidth()/2,
				window_height/2 - gamemenuimgs[1].getHeight()/2 - 140, null);
		
		g.drawImage(gamemenuimgs[2], window_width/2 - gamemenuimgs[2].getWidth()/2,
				window_height/2 - gamemenuimgs[2].getHeight()/2 - 80, null);
		System.out.println(pos_y);
		//issue with window_height
		System.out.println(window_height/2 - gamemenuimgs[0].getHeight()/2 - 200);
		if(pos_x >= window_width/2 - gamemenuimgs[0].getWidth()/2 && 
			pos_x <= gamemenuimgs[0].getWidth() + window_width/2 - gamemenuimgs[0].getWidth()/2 && 
			pos_y >= window_height/2 - gamemenuimgs[0].getHeight()/2 - 200 && 
			pos_y <=  window_height/2 - gamemenuimgs[0].getHeight()/2 - 200 + gamemenuimgs[0].getHeight())
		{
			setCursor (Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		
		else
		{
			setCursor (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		/*
		//go to main game if start button is clicked
		if(leftClicked && bounds.contains(pos_x, pos_y))
		{
			stopTimer();
			window.startGame();
		}
		

		


		
		//changes color of play button depending on if hover over it or not
		if(bounds.contains(pos_x, pos_y))
			g.drawImage(playButton[0], window_width/2 - 50, window_height/2 - 100, null);
		else
			g.drawImage(playButton[1], window_width/2 - 50, window_height/2 - 100, null);
		*/
	}
	
	
	
	
	
	
	public void startTimer()
	{
		timer.start();
	}
	
	public void stopTimer()
	{
		timer.stop();
	}
	
	
	//check if mouse left click is clicked
	@Override
	public void mousePressed(MouseEvent e) 
	{


	}
	//check if mouse left click is released
	@Override
	public void mouseReleased(MouseEvent e) 
	{


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
