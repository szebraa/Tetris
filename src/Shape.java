import java.awt.Graphics;
import java.awt.image.BufferedImage;

//tetris shape class (used to create different tetris shapes)
public class Shape 
{
	private BufferedImage block;
	private int [][] coords, constCoords;
	private Board board;
	
	private int deltaX = 0;
	private int x,y;
	
	private boolean collision = false, moveX = false;
	
	private int normalSpeed = 600, speedDown = 70, curSpeed, color;
	
	private long timePassed, lastTime;
	
	public Shape(BufferedImage block, int [][] coords, Board board, int color)
	{
		this.block = block;
		this.coords = coords;
		//need this to display current and next shape w/o changing when shape is rotated
		this.constCoords = coords;
		this.board = board;
		this.color = color;
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
		
		//fills out occupied board spaces occupied by shapes (fills out board with a number between 1 -7 (for block colors) where shapes already exist)
		if(collision)
		{
			for(int row = 0; row < coords.length; row++)
				for(int col = 0; col < coords[row].length; col++)
					if(coords[row][col] != 0)
						board.getBoard()[y + row][x + col] = color;
			checkLine();
			board.setNextShape();
			board.determineNextShape();
			
		}
		
		
		//put boundary on where you can move blocks left/right (most left = -1 , most right = 10)
		if(x + deltaX + coords[0].length <= 10 && x + deltaX >= 0)
		{
			for(int row = 0; row < coords.length; row++)
				for(int col = 0; col < coords[row].length; col++)
					if(coords[row][col] != 0)
					{
						//ensures that shapes dont move into occupied board spaces (checks x movements)
						if(board.getBoard()[y + row][x + deltaX + col] != 0)
							moveX = false;
					}
			
			if(moveX)
				x += deltaX;
			
		}
		//only move shapes down if they have not hit the y boundary of 20
		if(y + 1 + coords.length <= 20)
		{
			//ensures that shapes actually collide(i.e.: stack) - checks y movements
			for(int row = 0; row < coords.length; row++)
				for(int col = 0; col < coords[row].length; col++)
					if(coords[row][col] != 0)
					{
						if(board.getBoard()[y + row + 1][col + x] != 0)
							collision = true;
					}
						
			
			if(timePassed > curSpeed)
			{
				y++;
				timePassed = 0;
			}
		}
		//catch cases where the shape still moves beyond boundary
		else
			collision = true;
		
		//ensures shape doesnt move indefinitely left or right
		deltaX = 0;
		//resets moveX so that shapes can move if there's left or right if no collision
		moveX = true;
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
	
	public void dispShape(Graphics g, int x_offset, int y_offset)
	{
		int row = 0, col = 0;
		while (row < constCoords.length)
		{
			while(col < constCoords[row].length)
			{
				if(constCoords[row][col]!=0)
					g.drawImage(block,col*board.getBlockSize() + x_offset*board.getBlockSize(),row*board.getBlockSize() + y_offset*board.getBlockSize(),null);
				col++;
			}
			col = 0;
			row++;
		}
		
	}
	

	
	//used to get rid of completed rows 
	private void checkLine()
	{
		int height = board.getBoard().length - 1;
		for(int i = height; i > 0 ; i--)
		{
			int count = 0;
			for(int j = 0; j < board.getBoard()[0].length; j++)
			{
				
				if(board.getBoard()[i][j] !=0)
					count++;
				//rows will only be replaced iff there's a full row at least once
				board.getBoard()[height][j] = board.getBoard()[i][j];
			}
			
			//if no rows to be deleted, then height and i will stay same val
			if(count < board.getBoard()[0].length)
				height--;
			else
				board.incScore();
			
		}
	}
	
	public void rotate(String str)
	{
		//prevents objects from hanging when rotating
		if(collision)
			return;
		int [][] rotatedMatrix = null;
		
		rotatedMatrix = getTranspose(coords);
		rotatedMatrix = (str=="CCW")? swapRows(rotatedMatrix):swapCol(rotatedMatrix);
		
		if(x+ rotatedMatrix[0].length > 10 || y + rotatedMatrix.length > 20)
			return;
		//checks if rotating shape will collide w/ another shape
		for(int row = 0; row<rotatedMatrix.length; row++)
		{
			//if there's collsion, prevent coords of shape to be updated to rotated coords
			for(int col = 0; col<rotatedMatrix[0].length;col++)
				if(board.getBoard()[y+row][x+col] !=0)
					return;
		}
		
		
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
	
	//rotates shape CW
	private int[][] swapCol(int[][] matrix)
	{
		int i = 0;
		
		while(i<matrix.length)
		{

			int e = matrix[i][0];
			matrix[i][0] = matrix[i][matrix[i].length - 1];
			matrix[i][matrix[i].length -1] = e;

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
	
	public int [][] getCoords()
	{
		return coords;
	}
	
	public BufferedImage getBlock()
	{
		return block;
	}
	
	public int getColor()
	{
		return color;
	}
	
}
