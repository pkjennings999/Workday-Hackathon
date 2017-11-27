package oops;

import java.io.IOException;

public class playSongURL
{
	public static void playNext(String playListName)
	{
		for(int i=1; i<=10;i++)
		{
			//GlobalVariables.
		}
	}
	public static void playSongOpenUrl(String link) throws IOException
	{
		String url_open =link;
		java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
	}
}