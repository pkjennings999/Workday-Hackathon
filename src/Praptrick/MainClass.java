package Praptrick;
import processing.core.PApplet;
import processing.core.PImage;
import controlP5.*;


public class MainClass extends PApplet
{
	public static MainClass UI;
	ControlP5 cp5;
	
	PImage	background;
	PImage	appClick;
	PImage	backButton;
	PImage	createPlaylist;
	PImage	requestSong;
	PImage	playlistName;
	PImage	password;
	PImage	songName;

	int state = 0; 
	String playListNameString ="";
	String passwordString = "";
	String requestSongString = "";
	String var ="";

	public static void main(String[] args)
	{
		PApplet.main("MainClass");
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
		
		cp5 = new ControlP5(this);
		
		cp5.addTextfield("Playlist Name")
	      .setSize(200, 40)
	          .setFocus(true)
	            .setColor(color(255, 0, 0));
		
		cp5.addTextfield("Password")
	      .setSize(200, 40)
	          .setFocus(true)
	            .setColor(color(255, 0, 0));
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

		fill(255);
		background(150, 0, 100);
		image(backButton, 200, 580);
		image(playlistName, 130, 60);
		//rect(100, 160, 300, 30, 0);
		image(password, 130, 260);
		//rect(100, 360, 300, 30, 0);
		
		fill(0);
		if(GlobalVariables.createScreen)
		{
			text(cp5.get(Textfield.class,"Playlist Name").getText(), 360,130);
			text(cp5.get(Textfield.class,"Password").getText(), 360,130);
		}
	}

	public void drawRequestScreen()
	{
		fill(255);
		background(150, 0, 100);
		image(backButton, 200, 580);
		image(playlistName, 130, 60);
		rect(100, 160, 300, 30, 0);
		image(password, 130, 260);
		rect(100, 360, 300, 30, 0);
		image(songName, 130, 460);
		rect(100, 545, 300, 30, 0);
		
		fill(0);
		
		if(GlobalVariables.requestScreen)
		{
			text(cp5.get(Textfield.class,"Playlist Name").getText(), 360,130);
			text(cp5.get(Textfield.class,"Password").getText(), 360,130);
		}

	}
	
	public void keyPressed() 
	{
		 
		  if (key==ENTER||key==RETURN) {
		 
		    state++;
		  } else
		  var = var + key;
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