import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import java.io.FileInputStream;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;



public class Tetris
{
	private static final int WIDTH = 300,HEIGHT =600;
	private JFrame window;
	private Board board;
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
		
		
		
		board = new Board();
		window.add(board);
		//allows us to actually get keyboard input for this board
		window.addKeyListener(board);
		
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
		    Thread.sleep(5000);
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

	public static void main(String[] args) 
	{
		Tetris game_window = new Tetris();
		

	}

}
