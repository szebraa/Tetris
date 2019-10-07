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
	private BufferedImage blocks;
	private final int blockSize = 30, boardWidth = 10, boardHeight = 20;
	
	private int [][] board = new int[boardWidth][boardHeight];
	
	private Shape [] shapes = new Shape [7];
	
	private Shape currentShape;
	
	private Timer timer;
	
	private final int FPS = 60;
	
	private final int delay = 1000/FPS;
	
	public Board()
	{
		try
		{
			blocks = ImageIO.read(Board.class.getResource("/tetris_block_sprite.png"));
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
		
		// I shape
		shapes [0] = new Shape (blocks.getSubimage(0, 0, blockSize, blockSize), new int [][]
				{{1,1,1,1}},this);
		// Z shape
		shapes [1] = new Shape (blocks.getSubimage(blockSize, 0, blockSize, blockSize), new int [][]
				{{1,1,0} , {0,1,1}},this);
		// S shape
		shapes [2] = new Shape (blocks.getSubimage(blockSize*2, 0, blockSize, blockSize), new int [][]
				{{0,1,1} , {1,1,0}},this);
		// T shape
		shapes [3] = new Shape (blocks.getSubimage(blockSize*3, 0, blockSize, blockSize), new int [][]
				{{1,1,1} , {0,1,0}},this);
		// J shape
		shapes [4] = new Shape (blocks.getSubimage(blockSize*4, 0, blockSize, blockSize), new int [][]
				{{1,1,1} , {0,0,1}},this);
		// L shape
		shapes [5] = new Shape (blocks.getSubimage(blockSize*5, 0, blockSize, blockSize), new int [][]
				{{1,1,1} , {1,0,0}},this);
		// box shape
		shapes [6] = new Shape (blocks.getSubimage(blockSize*6, 0, blockSize, blockSize), new int [][]
				{{1,1} , {1,1}},this);
		
		
		currentShape = shapes[4];
	}
	
	
	public void update()
	{
		currentShape.update();
		
	}
	
	
	//called implicitly (auto), and through repaint() method
	public void paintComponent(Graphics g)
	{
		//ensures that the background (non-shapes) are painted properly (i.e.: ensures other tiles dont become shapes)
		super.paintComponent(g);
		//g.drawImage(blocks,0,0,null);
		
		//paints actual shape on the board
		currentShape.render(g);
		int i = 0 , j = 0;
		
		while (i<boardHeight)
		{
			g.drawLine(0, i*blockSize, boardWidth*blockSize, i*blockSize);
			i++;
		}
		
		while (j<boardWidth)
		{
			g.drawLine(j * blockSize, 0, j*blockSize, boardHeight*blockSize);
			j++;
		}
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
