import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;

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
		new Music();
	}

	public static void main(String[] args) 
	{
		Tetris game_window = new Tetris();
		

	}

}
