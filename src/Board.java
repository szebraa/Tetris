import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
public class Board extends JPanel implements KeyListener
{
	private BufferedImage blocks,background;
	private final int blockSize = 30, boardWidth = 10, boardHeight = 20;
	
	private int [][] board = new int[boardHeight][boardWidth];
	
	private Shape [] shapes = new Shape [7];
	
	private Shape currentShape;
	
	private Timer timer;
	
	private final int FPS = 60;
	
	private final int delay = 1000/FPS;
	
	private boolean gameOver = false;
	
	public Board()
	{
		try
		{
			blocks = ImageIO.read(Board.class.getResource("/tetris_block_sprite.png"));
			background = ImageIO.read(Board.class.getResource("/beautiful_background_image_part.jpg"));
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
			
		}
		
		timer = new Timer(delay, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//update position of shape
				update();
				//paintComponent
				repaint();
			}
		});
		
		timer.start();
		//create new tetris shapes (note that each x coord corresponds to a different color... i.e.: blocksize != blocksize*2)
		
		//shapes are set with colors betweeen 1 - 7
		// I shape
		shapes [0] = new Shape (blocks.getSubimage(0, 0, blockSize, blockSize), new int [][]
				{{1,1,1,1}},this,1);
		// Z shape
		shapes [1] = new Shape (blocks.getSubimage(blockSize, 0, blockSize, blockSize), new int [][]
				{{1,1,0} , {0,1,1}},this,2);
		// S shape
		shapes [2] = new Shape (blocks.getSubimage(blockSize*2, 0, blockSize, blockSize), new int [][]
				{{0,1,1} , {1,1,0}},this,3);
		// T shape
		shapes [3] = new Shape (blocks.getSubimage(blockSize*3, 0, blockSize, blockSize), new int [][]
				{{1,1,1} , {0,1,0}},this,4);
		// J shape
		shapes [4] = new Shape (blocks.getSubimage(blockSize*4, 0, blockSize, blockSize), new int [][]
				{{1,1,1} , {0,0,1}},this,5);
		// L shape
		shapes [5] = new Shape (blocks.getSubimage(blockSize*5, 0, blockSize, blockSize), new int [][]
				{{1,1,1} , {1,0,0}},this,6);
		// box shape
		shapes [6] = new Shape (blocks.getSubimage(blockSize*6, 0, blockSize, blockSize), new int [][]
				{{1,1} , {1,1}},this,7);
		
		
		setNextShape();
	}
	
	
	public void update()
	{
		currentShape.update();
		if(gameOver)
			timer.stop();
		
	}
	
	
	//called implicitly (auto), and through repaint() method
	public void paintComponent(Graphics g)
	{
		//ensures that the background (non-shapes) are painted properly (i.e.: ensures other tiles dont become shapes)
		super.paintComponent(g);
		g.drawImage(background,0,0,null);
		
		//paints actual shape on the board
		currentShape.render(g);
		
		//once shape ends up at the bottom, or collides with another shape, that shape will turn red (via another drawImage)
		//board array filled up w/ # other than 0 in position where shapes collided
		//need to -1 from board array to get the correct subimage (0-6)
		for(int row = 0; row < board.length; row++)
			for(int col = 0; col < board[row].length; col++)
				if(board[row][col] != 0)
					g.drawImage(blocks.getSubimage((board[row][col]-1) *blockSize,0,blockSize,blockSize), 
						col*blockSize, row*blockSize, null);
		
		int i = 0 , j = 0;
		
		while (i<boardHeight + 1)
		{
			g.drawLine(0, i*blockSize, boardWidth*blockSize, i*blockSize);
			i++;
		}
		
		while (j<boardWidth + 1)
		{
			g.drawLine(j * blockSize, 0, j*blockSize, boardHeight*blockSize);
			j++;
		}
	}
	
	
	public void setNextShape()
	{
		int index = (int)(Math.random()*shapes.length);
		Shape NewShape = new Shape(shapes[index].getBlock(),shapes[index].getCoords(),
				this,shapes[index].getColor());
		currentShape = NewShape;
		
		for(int row = 0; row < currentShape.getCoords().length; row++)
			for(int col = 0; col < currentShape.getCoords()[row].length; col++)
				if(currentShape.getCoords()[row][col] != 0)
				{
					if(board[row][col + 3] != 0)
						gameOver = true;
				}
		
	}
	
	public int[][] getBoard()
	{	
		return board;
	}
	
	public int getBlockSize()
	{
		return this.blockSize;
	}


	@Override
	public void keyTyped(KeyEvent e) 
	{
		
	}


	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			currentShape.setDeltaX(-1);
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			currentShape.setDeltaX(1);
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			currentShape.speedDown();
		
		if(e.getKeyCode() == KeyEvent.VK_Q)
			currentShape.rotate("CCW");
		if(e.getKeyCode() == KeyEvent.VK_E)
			currentShape.rotate("CW");

	}


	@Override
	public void keyReleased(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			currentShape.speedNorm();
		
	}

}
