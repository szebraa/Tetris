import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import java.io.FileInputStream;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;



public class Tetris
{
	private static final int WIDTH = 480,HEIGHT =600;
	private JFrame window;
	private Board board;
	private Titlemenu titleScreen;
	
	public Tetris()
	{
		this.window = new JFrame("Alex's Tetris Game");
		//window.setSize(WIDTH,HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//need this to be true so that game window can be resized by user
		window.setResizable(false);
		
		
		//needed to set the content window size correctly (excludes boarder size)
		Container boardArea = window.getContentPane();
		Dimension boardSize = new Dimension (WIDTH,HEIGHT);
		boardArea.setPreferredSize(boardSize);
		window.pack();
		String [] title_menu_paths = {"/Title.png","/controls.png","/play_button.png"};
		this.titleScreen = new Titlemenu(this,title_menu_paths);
		//window.add(menu);
		// logic to start the game 
		//
		board = new Board(this);
		//allows us to actually get keyboard input for this board
		window.addKeyListener(board);
		//allows us to actually get mouse motion and click input for this board
		window.addMouseMotionListener(board);
		window.addMouseListener(board);
		//ensures that mouse actions can be detected in the title screen
		window.addMouseListener(titleScreen);
		window.addMouseMotionListener(titleScreen);
		
		//ensures menu is seen
		window.add(titleScreen);
		
		//allows us to actually see the board
		//window.add(board);
		//allows us to actually get keyboard input for this board
		//window.addKeyListener(board);
		
		//
		
		window.getContentPane().setSize(WIDTH,HEIGHT);
		
		window.setLocationRelativeTo(null);
		
		
		window.setVisible(true);
		
		
		//TESTING MUSIC PORTION OF GAME
		//create music obj and set the Player obj
		Music test = new Music();
		test.setMusicPlayer();
		//initiate thread for starting the music (infinitely)
		MyRunnable musicPlayerStart = new MyRunnable(test,null,true); 
		Thread t = new Thread(musicPlayerStart);
		t.start();
		
		//delay by 5 seconds
		try
		{
		    Thread.sleep(10000);
		}
		catch(InterruptedException ex)
		{
		    Thread.currentThread().interrupt();
		}
		
		//get same Player obj
		Player test2 = test.getMusicPlayer();
		
		//initiate thread for stopping the song
		MyRunnable musicPlayerStop = new MyRunnable(test,test2,false); 
		Thread t2 = new Thread(musicPlayerStop);
		t2.start();
		
	}
	
	public void startGame()
	{
		window.remove(titleScreen);
		
		//allows us to actually see the board
		window.add(board);
		//needs to be called to ensure that transition between title screen and game can be seen.. otherwise will hang on menu screen
		window.revalidate();
		board.gameLoop();

	}
	
	public JFrame getWindow()
	{
		return window;
	}
	
	public int getWidth()
	{
		return WIDTH;
	}
	
	public int getHeight()
	{
		return HEIGHT;
	}


	public static void main(String[] args) 
	{
		Tetris game_window = new Tetris();
		

	}

}
