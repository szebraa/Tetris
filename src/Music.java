import java.io.FileInputStream;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
public class Music extends Thread
{
	private String songFileName;
	private boolean toLoop;
	private Player musicPlayer;
	
	//set loop to true, and path/fileName
	public Music()
	{
		toLoop = true;
		songFileName = "Music/JAZZSCAPEZ - SMOOTH GROOVES (MONTH 2).mp3";

		/*
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
		*/
		
	}
	
	public void startLoop()
	{
		   toLoop = true;
	       try 
	       {
	            do 
	            {

	                musicPlayer.play();
	                
	            } while (toLoop);
	        } 
	       catch (Exception ioe) 
	       {
	            // TODO error handling
	       }

	}
	
	public void endLoop(Player x)
	{
        toLoop = false;
        x.close();
        this.interrupt();
		
	}
	
	
	public void setMusicPlayer()
	{
		try 
		{
	        FileInputStream buff = new FileInputStream(songFileName);
	        musicPlayer = new Player(buff);
        } 
		catch (Exception ioe) 
		{
            // TODO error handling
        }

	}
	public Player getMusicPlayer()
	{
		return musicPlayer;
	}

}
