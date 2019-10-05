import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Shape 
{
	private BufferedImage block;
	private int [][] coords;
	private Board board;
	
	public Shape(BufferedImage block, int [][] coords, Board board)
	{
		this.block = block;
		this.coords = coords;
		this.board = board;
	}
	
	public void update()
	{
		
	}
	
	public void render (Graphics g)
	{
		int row = 0, col = 0;
		while (row < coords.length)
		{
			while(col < coords[row].length)
			{
				if(coords[row][col]!=0)
					g.drawImage(block,col*board.getBlockSize(),row*board.getBlockSize(),null);
				col++;
			}
			col = 0;
			row++;
		}
	}

}
