package oops;

import java.util.ArrayList;

public class Widget
{

	protected int		xPosition, yPosition;
	protected int		height;
	protected int		width;
	protected boolean	canDraw	= true;

	public int getxPosition()
	{
		return xPosition;
	}

	public void setxPosition(int xPosition)
	{
		this.xPosition = xPosition;
	}

	public int getyPosition()
	{
		return yPosition;
	}

	public void setyPosition(int yPosition)
	{
		this.yPosition = yPosition;
	}

	public void setCanDraw(boolean canDraw)
	{
		this.canDraw = canDraw;
	}

	public int getHeight()
	{
		return height;
	}

	public int getWidth()
	{
		return width;
	}

	Widget()
	{
	}

	public void draw()
	{
	}

	public boolean mouseOver()
	{
		return (MainClass.UI.mouseX >= xPosition
				&& MainClass.UI.mouseX <= xPosition + width
				&& MainClass.UI.mouseY >= yPosition
				&& MainClass.UI.mouseY <= yPosition + height);
	}

}