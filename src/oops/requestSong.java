package oops;

public class requestSong
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
			if(GlobalVariables.play1[0]==playListName)
			{
				for(int i =1; i<=10;i++)
				{
					if(GlobalVariables.play1[i]==null)
					{
						GlobalVariables.play1[i]=link;
					}
				}
			}
			
			else if(GlobalVariables.play2[0]==playListName)
			{
				for(int i =1; i<=10;i++)
				{
					if(GlobalVariables.play2[i]==null)
					{
						GlobalVariables.play2[i]=link;
					}
				}
			}
			
			else if(GlobalVariables.play3[0]==playListName)
			{
				for(int i =1; i<=10;i++)
				{
					if(GlobalVariables.play3[i]==null)
					{
						GlobalVariables.play3[i]=link;
					}
				}
			}
			
			else if(GlobalVariables.play4[0]==playListName)
			{
				for(int i =1; i<=10;i++)
				{
					if(GlobalVariables.play4[i]==null)
					{
						GlobalVariables.play4[i]=link;
					}
				}
			}
			
			else if(GlobalVariables.play5[0]==playListName)
			{
				for(int i =1; i<=10;i++)
				{
					if(GlobalVariables.play5[i]==null)
					{
						GlobalVariables.play5[i]=link;
					}
				}
			}
		}
	}
}