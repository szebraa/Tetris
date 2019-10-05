import javax.swing.JFrame;

public class Tetris 
{
	private static final int WIDTH = 300,HEIGHT =600;
	private JFrame window;
	private Board board;
	public Tetris()
	{
		this.window = new JFrame("Alex's Tetris Game");
		window.setSize(WIDTH, HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//need this to be true so that game window can be resized by user
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		board = new Board();
		window.add(board);
		
		window.setVisible(true);
	}

	public static void main(String[] args) 
	{
		Tetris game_window = new Tetris();
		

	}

}
