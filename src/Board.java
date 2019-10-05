import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
public class Board extends JPanel
{
	private BufferedImage blocks;
	private final int blockSize = 30, boardWidth = 10, boardHeight = 20;
	
	private int [][] board = new int[boardWidth][boardHeight];
	
	private Shape [] shapes = new Shape [7];
	
	private Shape currentShape;
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
				{{0,1,1} , {0,1,1}},this);
		
		
		currentShape = shapes[6];
	}
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//g.drawImage(blocks,0,0,null);
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

}
