import java.io.FileInputStream;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
public class Music 
{
	private String songFileName;
	public Music()
	{

		
		try {
		    // Try With resources used here to auto close the FileInputStream
		    try (FileInputStream fis = new FileInputStream("Music/JAZZSCAPEZ - SMOOTH GROOVES (MONTH 2).mp3")) {
		        Player player = new Player(fis);
		        System.out.println(System.getProperty("user.dir"));
		        System.out.println("YourSong.mp3 is now Playing....");
		        player.play();
		        System.out.println("YourSong.mp3 is now FINISHED Playing!");
		    }
		    catch (IOException ex) {
		        ex.printStackTrace();
		    }
		}
		catch (JavaLayerException ex) {
		    ex.printStackTrace();
		}
		
		
	}
	
	public void startLoop()
	{
		
		
	}
	
	public void endLoop()
	{
		
	}

}
