package oops;

public class sendName
{
	public static String link = null;
	public static String playListName = null;
	public static void songLink(String theLink)
	{
		link = theLink;
		mainForThis();
	}
	public static void PlaylistName(String thePlayListName)
	{
		playListName = thePlayListName;
		mainForThis();
	}
	public static void mainForThis()
	{
		if((link!=null) && playListName!=null)
		{
			System.out.println("Request Sent/Added on");
		}
	}
}