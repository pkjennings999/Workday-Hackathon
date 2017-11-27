package oops;
public class createPlaylist 
{
	public static String playListName = null;
	public static void PlaylistName(String thePlayListName)
	{
		playListName = thePlayListName;
		mainForThis();
	}
	public static void mainForThis()
	{
		if(GlobalVariables.play1[0]==null)
		{
			GlobalVariables.play1[0] = playListName;
		}
		
		else if(GlobalVariables.play2[0]==null)
		{
			GlobalVariables.play2[0] = playListName;
		}
		
		else if(GlobalVariables.play3[0]==null)
		{
			GlobalVariables.play3[0] = playListName;
		}
		
		else if(GlobalVariables.play4[0]==null)
		{
			GlobalVariables.play4[0] = playListName;
		}
		
		else if(GlobalVariables.play5[0]==null)
		{
			GlobalVariables.play5[0] = playListName;
		}
	}
}
