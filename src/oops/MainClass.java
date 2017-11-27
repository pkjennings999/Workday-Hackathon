package oops;

import processing.core.PApplet;
import processing.core.PImage;

public class MainClass extends PApplet
{
	public static MainClass UI;

	PImage	background;
	PImage	appClick;
	PImage	backButton;
	PImage	createPlaylist;
	PImage	requestSong;
	PImage	playlistName;
	//PImage	password;
	PImage	songName;
	PImage playButton;
	
	Client client;

	SearchBar	playList;
	//SearchBar	passwordString;
	SearchBar	requestName;

	public static void main(String[] args)
	{
		PApplet.main("oops.MainClass");
	}

	public void settings()
	{
		UI = this;
		size(GlobalConstants.SCREEN_WIDTH, GlobalConstants.SCREEN_HEIGHT);

	}

	public void setup()
	{
		background = loadImage("basicApp.jpg");
		appClick = loadImage("app.png");
		backButton = loadImage("backButton.png");
		createPlaylist = loadImage("createPlaylist.PNG");
		requestSong = loadImage("requestSong.PNG");
		playlistName = loadImage("PlaylistName.PNG");
		//password = loadImage("password.png");
		songName = loadImage("SongName.png");
		playButton = loadImage("PlayButton.png");

		playList = new SearchBar(100, 160, 300, GlobalVariables.playListOut);
		//passwordString = new SearchBar(100, 360, 300,
				//GlobalVariables.passwordStringOut);
		requestName = new SearchBar(100, 545, 300,
				GlobalVariables.requestNameOut);
		client = null;
	}

	public void draw()
	{
		if (GlobalVariables.homescreen)
		{
			drawHomeScreen();
		}
		else if (GlobalVariables.createRequestScreen)
		{
			drawCreateRequestScreen();
		}
		else if (GlobalVariables.createScreen)
		{
			drawCreateScreen();
			
		}
		else if (GlobalVariables.requestScreen)
		{
			drawRequestScreen();
			if (GlobalVariables.placeholderForSomethingChanged)
			{
				if (client == null)
				{
					client = new Client(Client.DEFAULT_DST_NODE, Client.DEFAULT_DST_PORT, Client.DEFAULT_SRC_PORT);
				}
				try
				{
					client.send(GlobalVariables.requestNameOut);
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			

		}
		else if(GlobalVariables.play)
		{
			drawPlayScreen();
		}
	}

	public void drawHomeScreen()
	{
		background(background);
		image(appClick, 155, 630);
	}

	public void drawCreateRequestScreen()
	{
		fill(255);
		background(100, 20, 225);
		noStroke();
		image(appClick, 25, 780);
		rect(150, 200, 200, 100, 0);
		image(createPlaylist, 200, 200);
		rect(150, 450, 200, 100, 0);
		image(requestSong, 200, 450);
		rect(150, 700, 200, 100);
		image(playButton, 220, 700);
	}

	public void drawCreateScreen()
	{
		background(150, 0, 100);
		image(backButton, 200, 580);
		image(playlistName, 130, 60);
		//image(password, 130, 260);
		image(appClick, 25, 780);
		playList.draw();
		//passwordString.draw();
	}
	
	public void drawPlayScreen()
	{
		background(150, 0, 100);
		image(backButton, 200, 580);
		fill(0);
		if(GlobalVariables.play1[0]!=null)
		{
			fill(255);
			rect(100,80,150,50);
			fill(0);
			text(GlobalVariables.play1[0].toString(), 100, 100);
		}
		if(GlobalVariables.play2[0]!=null)
		{
			fill(255);
			rect(100,180,150,50);
			fill(0);
			text(GlobalVariables.play2[0].toString(), 100, 200);
		}
		if(GlobalVariables.play3[0]!=null)
		{
			fill(255);
			rect(100,280,150,50);
			fill(0);
			text(GlobalVariables.play3[0].toString(), 100, 300);
		}
		if(GlobalVariables.play4[0]!=null)
		{
			fill(255);
			rect(100,380,150,50);
			fill(0);
			text(GlobalVariables.play4[0].toString(), 100, 400);
		}
		if(GlobalVariables.play5[0]!=null)
		{
			fill(255);
			rect(100,480,150,50);
			fill(0);
			text(GlobalVariables.play5[0].toString(), 100, 500);
		}
	}

	public void drawRequestScreen()
	{
		fill(255);
		background(150, 0, 100);
		image(appClick, 25, 780);
		image(backButton, 200, 580);
		image(playlistName, 130, 60);
		//image(password, 130, 260);
		text("Please enter the link", 130,425);
		text("to the song on Youtube", 130, 445);
		image(songName, 130, 460);

		playList.draw();
		//passwordString.draw();
		requestName.draw();
	}

	public void mouseClicked()
	{
		if (GlobalVariables.homescreen)
		{
			if (mouseX <= 230 && mouseX >= 155 && mouseY <= 730
					&& mouseY >= 630)
			{
				GlobalVariables.createRequestScreen = true;
				GlobalVariables.homescreen = false;
			}
		}
		else if (GlobalVariables.createRequestScreen)
		{
			if (mouseX <= 350 && mouseX >= 150 && mouseY <= 300
					&& mouseY >= 200)
			{
				GlobalVariables.createScreen = true;
				GlobalVariables.createRequestScreen = false;
				GlobalVariables.play = false;
			}
			else if (mouseX <= 350 && mouseX >= 150 && mouseY <= 550
					&& mouseY >= 450)
			{
				GlobalVariables.requestScreen = true;
				GlobalVariables.createRequestScreen = false;
				GlobalVariables.play = false;
			}
			else if(mouseX <= 350 && mouseX >= 150 && mouseY <= 800
					&& mouseY >= 700)
			{
				GlobalVariables.requestScreen = false;
				GlobalVariables.createScreen = false;
				GlobalVariables.createRequestScreen = false;
				GlobalVariables.play =true;
			}
		}
		else if (GlobalVariables.createScreen)
		{
			if (mouseX <= 500 && mouseX >= 200 && mouseY <= 880
					&& mouseY >= 580)
			{
				GlobalVariables.createScreen = false;
				GlobalVariables.createRequestScreen = true;
				GlobalVariables.play = false;
			}
		}
		else if (GlobalVariables.requestScreen)
		{
			if (mouseX <= 500 && mouseX >= 200 && mouseY <= 880
					&& mouseY >= 580)
			{
				GlobalVariables.requestScreen = false;
				GlobalVariables.createRequestScreen = true;
				GlobalVariables.play = false;
			}
		}
		else if (GlobalVariables.play)
		{
			if (mouseX <= 500 && mouseX >= 200 && mouseY <= 880
					&& mouseY >= 580)
			{
				GlobalVariables.requestScreen = false;
				GlobalVariables.createRequestScreen = true;
				GlobalVariables.play = false;
			}
			else if (mouseX <= 350 && mouseX >= 100 && mouseY <= 880
					&& mouseY >= 580)
			{
				
			}
			else if (mouseX <= 350 && mouseX >= 100 && mouseY <= 880
					&& mouseY >= 580)
			{
				
			}
			else if (mouseX <= 350 && mouseX >= 100 && mouseY <= 880
					&& mouseY >= 580)
			{
				
			}
			else if (mouseX <= 350 && mouseX >= 100 && mouseY <= 880
					&& mouseY >= 580)
			{
				
			}
			
		}
	}
}