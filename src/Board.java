import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
public class Board extends JPanel
{
	private BufferedImage blocks;
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
	}
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(blocks,0,0,null);
	}

}
