package Praptrick;
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
	PImage	password;
	PImage	songName;

	SearchBar playList;
	SearchBar passwordString;
	SearchBar requestName;

	public static void main(String[] args)
	{
		PApplet.main("Praptrick.MainClass");
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
		password = loadImage("password.png");
		songName = loadImage("SongName.png");
		
		playList = new SearchBar(100,160,300);
		passwordString = new SearchBar(100,360,300);
		requestName = new SearchBar(100,545,300);
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
		rect(150, 200, 200, 100, 0);
		image(createPlaylist, 200, 200);
		rect(150, 450, 200, 100, 0);
		image(requestSong, 200, 450);
	}

	public void drawCreateScreen()
	{
		background(150, 0, 100);
		image(backButton, 200, 580);
		image(playlistName, 130, 60);
		image(password, 130, 260);
		playList.draw();
		passwordString.draw();
	}

	public void drawRequestScreen()
	{
		fill(255);
		background(150, 0, 100);
		image(backButton, 200, 580);
		image(playlistName, 130, 60);
		image(password, 130, 260);
		image(songName, 130, 460);
		
		playList.draw();
		passwordString.draw();
		requestName.draw();
	}
	
	public void mouseClicked()
	{
		if (GlobalVariables.homescreen)
		{
			if (mouseX <= 200 && mouseX >= 155 && mouseY <= 730
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
			}
			else if (mouseX <= 350 && mouseX >= 150 && mouseY <= 550
					&& mouseY >= 450)
			{
				GlobalVariables.requestScreen = true;
				GlobalVariables.createRequestScreen = false;
			}
		}
		else if (GlobalVariables.createScreen)
		{
			if (mouseX <= 500 && mouseX >= 200 && mouseY <= 880
					&& mouseY >= 580)
			{
				GlobalVariables.createScreen = false;
				GlobalVariables.createRequestScreen = true;
			}
		}
		else if (GlobalVariables.requestScreen)
		{
			if (mouseX <= 500 && mouseX >= 200 && mouseY <= 880
					&& mouseY >= 580)
			{
				GlobalVariables.requestScreen = false;
				GlobalVariables.createRequestScreen = true;
			}
		}
	}
}