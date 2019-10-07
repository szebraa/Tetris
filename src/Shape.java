import java.awt.Graphics;
import java.awt.image.BufferedImage;

//tetris shape class (used to create different tetris shapes)
public class Shape 
{
	private BufferedImage block;
	private int [][] coords;
	private Board board;
	
	private int deltaX = 0;
	private int x,y;
	
	private int normalSpeed = 600, speedDown = 70, curSpeed;
	
	private long timePassed, lastTime;
	
	public Shape(BufferedImage block, int [][] coords, Board board)
	{
		this.block = block;
		this.coords = coords;
		this.board = board;
		curSpeed = normalSpeed;
		
		timePassed = 0;
		
		lastTime = System.currentTimeMillis();
		//dummy values for now
		x = 3;
		y = 0;
	}
	
	//update shape object location on board
	public void update()
	{
		timePassed += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		//put boundary on where you can move blocks left/right (most left = -1 , most right = 10)
		if(x + deltaX + coords[0].length <= 10 && x + deltaX >= 0)
			x += deltaX;
		if(y + 1 + coords.length <= 20)
		{
			if(timePassed > curSpeed)
			{
				y++;
				timePassed = 0;
			}
		}
		deltaX = 0;
	}
	
	//used to display shapes on the board
	public void render (Graphics g)
	{
		int row = 0, col = 0;
		while (row < coords.length)
		{
			while(col < coords[row].length)
			{
				if(coords[row][col]!=0)
					g.drawImage(block,col*board.getBlockSize() + x*board.getBlockSize(),row*board.getBlockSize() + y*board.getBlockSize(),null);
				col++;
			}
			col = 0;
			row++;
		}
	}
	
	
	public void rotate(String str)
	{
		int [][] rotatedMatrix = null;
		
		rotatedMatrix = getTranspose(coords);
		rotatedMatrix = (str=="CCW")? swapRows(rotatedMatrix):swapCol(rotatedMatrix);
		
		if(x+ rotatedMatrix[0].length > 10 || y + rotatedMatrix.length > 20)
			return;
		coords = rotatedMatrix;
		
	}
	
	
	private int[][] getTranspose(int[][] matrix)
	{
		int [][] newMatrix = new int[matrix[0].length][matrix.length];
		int i = 0, j = 0;
		
		while(i<matrix.length)
		{
			while(j<matrix[i].length)
			{
				newMatrix[j][i] = matrix[i][j];
				j++;
			}
			j = 0;
			i++;
		}
		return newMatrix;
	}
	
	//rotates shape CCW
	private int[][] swapRows(int[][] matrix)
	{
		int middle = matrix.length/2;
		int i = 0;
		
		while(i<middle)
		{
			int [] m = matrix[i];
			matrix[i] = matrix[matrix.length -i -1];
			matrix[matrix.length -i -1] = m;
			i++;
		}
		return matrix;
	}
	
	//rotates shape CW (STILL NEEDS WORK!!)
	private int[][] swapCol(int[][] matrix)
	{
		int i = 0;
		
		while(i<matrix.length)
		{
			//need either try except here or some other logic to prevent array out of bounds...
			int e = matrix[i][0];
			matrix[i][0] = matrix[i][matrix.length -i -1];
			matrix[i][matrix.length -i -1] = e;
			i++;
		}
		return matrix;
	}
	
	
	public void setDeltaX(int deltaX)
	{
		this.deltaX = deltaX;
	}
	
	
	public void speedDown()
	{
		curSpeed = speedDown;
	}
	
	public void speedNorm()
	{
		curSpeed = normalSpeed;
	}
	
}
