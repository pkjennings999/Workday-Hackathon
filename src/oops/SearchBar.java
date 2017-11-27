package oops;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import processing.core.PConstants;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class SearchBar extends Widget
{

	private String	entry			= "";
	private String	ghost_text;
	private int		cursor			= 0;
	private boolean	focused			= false;
	private int		promptWidth;
	private int		fontSize		= 17;
	private int		margin			= 2;
	private int		radius;
	private float	blinkDuration	= 2;
	private Color	promptColor		= Colors.AZUREISH_WHITE;
	private Color	backgroundColor	= Colors.PICTON_BLUE;
	private Color	textColor		= Colors.PICTON_BLUE;
	private Color	textTwoColor	= Colors.AZUREISH_WHITE;
	private int		cursorColor;
	private String	output;

	public SearchBar(int xPosition, int yPosition, int width, String output)
	{
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
		this.height = 25;
		this.fontSize = height - 2 * margin;
		this.radius = 3;
		this.ghost_text = "";
		this.output = output;

		// Register as observer for mouse and keys
		MainClass.UI.registerMethod("mouseEvent", this);
		MainClass.UI.registerMethod("keyEvent", this);
	}

	public void mouseEvent(MouseEvent e)
	{
		if (e.getAction() == MouseEvent.PRESS)
		{
			if (mouseOver())
			{
				focused = true;
			}
			else
			{
				focused = false;
			}
		}
	}

	public void keyEvent(KeyEvent keyEvent)
	{
		if (focused && keyEvent.getAction() == KeyEvent.PRESS)
		{
			int key = keyEvent.getKeyCode();
			switch (key)
			{
				case '\n':
					output = entry;
					System.out.print(output);
					reset();
					break;
				case '\b':
					delAtCursor();
					break;
				case 37:
					moveCursorLeft();
					break;
				case 39:
					moveCursorRight();
					break;
				default:
					insertAtCursor(keyEvent.getKey());
					break;
				case 38:
				case 16:
			}
		}
	}

	public void draw()
	{
		MainClass.UI.textSize(fontSize);
		MainClass.UI.strokeWeight(1);
		MainClass.UI.stroke(200);
		MainClass.UI.fill(promptColor.getRGB());
		MainClass.UI.rect(xPosition, yPosition, promptWidth, height, radius, 0,
				0, radius);
		MainClass.UI.fill((focused) ? backgroundColor.brighter().getRGB()
				: backgroundColor.getRGB());
		MainClass.UI.rect(xPosition + promptWidth, yPosition,
				width - promptWidth, height, 0, radius, radius, 0);
		MainClass.UI.fill(textColor.getRGB());
		MainClass.UI.textAlign(PConstants.LEFT, PConstants.BOTTOM);
		//  MainClass.UI.text(determinePreField(), xPosition + margin, yPosition + height);
		MainClass.UI.stroke(0);
		MainClass.UI.strokeWeight(1);
		drawSearch();
		MainClass.UI.textAlign(PConstants.LEFT, PConstants.CENTER);

	}

	private void insertAtCursor(char c)
	{
		entry = (entry.substring(0, cursor) + c
				+ entry.substring(cursor, entry.length())).toUpperCase();
		cursor++;
	}

	private void delAtCursor()
	{
		if (cursor != 0)
		{
			entry = entry.substring(0, cursor - 1)
					+ entry.substring(cursor, entry.length());
			cursor--;
		}
	}

	private void moveCursorLeft()
	{
		if (cursor != 0)
		{
			cursor--;
		}
	}

	private void moveCursorRight()
	{
		if (cursor < entry.length())
		{
			cursor++;
		}
	}

	private void setPromptWidth(String text)
	{
		this.promptWidth = (int) MainClass.UI.textWidth(text) + 2 * margin;
	}

	private void drawCursor()
	{
		int time = MainClass.UI.millis();
		cursorColor = MainClass.UI.lerpColor(textColor.getRGB(),
				backgroundColor.brighter().getRGB(),
				(time % (1000 * blinkDuration)) / (blinkDuration * 1000));
		MainClass.UI.stroke(cursorColor);
		if (!textLongerThanAreaSearchArea())
		{
			MainClass.UI.line(
					xPosition + promptWidth + margin + getCursorOffsetLength(),
					yPosition + height - margin,
					xPosition + promptWidth + margin + getCursorOffsetLength(),
					yPosition + margin);
		}
		else
		{
			MainClass.UI.line(
					xPosition + width - margin
							- (getEntryLength() - getCursorOffsetLength()),
					yPosition + margin,
					xPosition + width - margin
							- (getEntryLength() - getCursorOffsetLength()),
					yPosition + height - margin);
		}
		MainClass.UI.stroke(0);
	}

	private void drawSearch()
	{
		MainClass.UI.clip(xPosition + promptWidth + margin, yPosition,
				width - promptWidth - margin, height);
		if (entry.isEmpty())
		{
			MainClass.UI.fill(240);
			MainClass.UI.text(ghost_text, xPosition + promptWidth + margin,
					yPosition + height);
		}
		else
		{
			if (!textLongerThanAreaSearchArea())
			{
				MainClass.UI.fill(textTwoColor.getRGB());
				MainClass.UI.text(entry, xPosition + promptWidth + margin,
						yPosition + height);
			}
			else
			{
				MainClass.UI.textAlign(PConstants.RIGHT, PConstants.BOTTOM);
				MainClass.UI.text(entry, xPosition + width - margin,
						yPosition + height);
			}
		}

		if (focused)
		{
			drawCursor();
		}
		MainClass.UI.noClip();
	}

	private boolean textLongerThanAreaSearchArea()
	{
		return MainClass.UI.textWidth(entry) > width - promptWidth - margin * 2;
	}

	private float getEntryLength()
	{
		return MainClass.UI.textWidth(entry);
	}

	private float getCursorOffsetLength()
	{
		return MainClass.UI.textWidth(entry.substring(0, cursor));
	}

	private void reset()
	{
		entry = "";
		focused = false;
		cursor = 0;
	}

}
