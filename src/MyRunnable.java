import java.io.FileInputStream;
import java.io.IOException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


public class MyRunnable implements Runnable
{
	private Music music;
	private Player musicPlayer;
	private boolean toPlay;
	
	public MyRunnable(Music music,Player musicPlayer,boolean toPlay)
	{
		this.music = music;
		this.musicPlayer = musicPlayer;
		this.toPlay = toPlay;
	}
	
	public void run()
	{
		if(toPlay)
			music.startLoop();
		else
			music.endLoop(musicPlayer);

	}
	

}
