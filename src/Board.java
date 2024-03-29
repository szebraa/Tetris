import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
public class Board extends JPanel implements KeyListener, MouseListener, MouseMotionListener
{
	private BufferedImage blocks,background,menuButton,menuButtonBlack;
	private String [] gameMenuImgpaths = {"/resume_button.png","/restart_button.png","/exit_button.png","/beautiful_background_image_part.jpg"}; 
	private final int blockSize = 30, boardWidth = 10, boardHeight = 20, menuButtonx1 = 333, menuButtonx2 = 463, menuButtony1 = 504, menuButtony2 = 546;
	private int pos_x,pos_y;
	private boolean leftClicked;
	private int [][] board = new int[boardHeight][boardWidth];
	private Gamemenu game_menu;
	
	private Shape [] shapes = new Shape [7];
	
	private Shape currentShape,nextShape;
	
	private Tetris window;
	
	private Timer timer;
	
	private JFrame jframe_window;
	
	private final int FPS = 60;
	
	private final int delay = 1000/FPS;
	
	private boolean gameOver = false;
	
	private int score = 0;
	
	public Board(Tetris window)
	{
		try
		{
			blocks = ImageIO.read(Board.class.getResource("/tetris_block_sprite.png"));
			background = ImageIO.read(Board.class.getResource("/beautiful_background_image_part.jpg"));
			menuButton = ImageIO.read(Board.class.getResource("/menu-button.png"));
			menuButtonBlack = ImageIO.read(Board.class.getResource("/menu-button_black.png"));
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
		
		//init mouse starting position
		pos_x = 0;
		pos_y = 0;
		
		this.window = window;
		game_menu = new Gamemenu(this.window,gameMenuImgpaths, this);
		jframe_window = this.window.getWindow();
		jframe_window.addMouseListener(game_menu);
		jframe_window.addMouseMotionListener(game_menu);
		
		game_menu.setTimer();
		
	}
	
	
	
	public void gameLoop()
	{
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
		
		determineNextShape();
		setNextShape();
		determineNextShape();
		
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
		
		
		//go to Game Menu if Menu button is clicked
		if(leftClicked && pos_x <= menuButtonx2 && pos_x >= menuButtonx1 && pos_y <= menuButtony2 && pos_y >= menuButtony1)
		{
			//System.out.println("here");
			timer.stop();
			//should call some method in GameMenu to open menu
			game_menu.setMouseCoords();
			game_menu.startTimer();
			//ensures menu is seen
			jframe_window.remove(this);
			jframe_window.add(game_menu);
			jframe_window.revalidate();
		}
		
		
		//paints actual shape on the board
		currentShape.render(g);
		nextShape.dispShape(g,11,5);
		currentShape.dispShape(g,11,9);
		//text image settings
		g.setFont(new Font("TimesRoman", Font.BOLD, 20));
		g.setColor(Color.WHITE);
		g.drawString("Next Shape:", 11*blockSize,4*blockSize);
		g.drawString("Current Shape:", 11*blockSize,8*blockSize);
		//displays score
		g.drawString("Score: " + Integer.toString(score), 11*blockSize,13*blockSize);
		//rest of the objects color (i.e.: gridlines, etc)
		g.setColor(Color.BLACK);

		if(pos_x <= menuButtonx2 && pos_x >= menuButtonx1 && pos_y <= menuButtony2 && pos_y >= menuButtony1)
			g.drawImage(menuButtonBlack,11*blockSize,16*blockSize,null);
		else
			g.drawImage(menuButton,11*blockSize,16*blockSize,null);

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
		/* THIS IS HOW U WOULD STOP AND START GAME WHEN EXITING TO A MENU 
		timer.stop();
		timer.restart();
		*/
	}
	
	//sets the current shape to the determined next shape
	public void setNextShape()
	{
		/*
		int index = (int)(Math.random()*shapes.length);
		Shape NewShape = new Shape(shapes[index].getBlock(),shapes[index].getCoords(),
				this,shapes[index].getColor());
		currentShape = NewShape;*/
		currentShape = nextShape;
		
		for(int row = 0; row < currentShape.getCoords().length; row++)
			for(int col = 0; col < currentShape.getCoords()[row].length; col++)
				if(currentShape.getCoords()[row][col] != 0)
				{
					if(board[row][col + 3] != 0)
						gameOver = true;
				}
		
	}
	
	//determines next shape in tetris game
	public void determineNextShape()
	{
		int index = (int)(Math.random()*shapes.length);
		nextShape = new Shape(shapes[index].getBlock(),shapes[index].getCoords(),
				this,shapes[index].getColor());
	}
	
	public void incScore()
	{
		score++;
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
	
	
	
	
	//check if mouse left click is clicked
	@Override
	public void mousePressed(MouseEvent e) 
	{
		if(e.getButton() == MouseEvent.BUTTON1)
			leftClicked = true;

	}
	//check if mouse left click is released
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		if(e.getButton() == MouseEvent.BUTTON1)
			leftClicked = false;

	}
	
	@Override
	public void mouseDragged(MouseEvent e) 
	{

	}

	//update mouse position
	@Override
	public void mouseMoved(MouseEvent e) 
	{
		pos_x = e.getX();
		pos_y = e.getY();

	}
	
	
	
	
	

	@Override
	public void mouseClicked(MouseEvent e) 
	{	
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
