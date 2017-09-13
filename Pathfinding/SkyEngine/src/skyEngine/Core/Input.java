package skyEngine.Core;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class Input
{
	private static boolean[] keys = new boolean[Keyboard.KEYBOARD_SIZE];
	private static boolean[] keysLast = new boolean[Keyboard.KEYBOARD_SIZE];

	private static boolean[] buttons = new boolean[10];
	private static boolean[] buttonsLast = new boolean[10];

	public static int leftMouseButton = 0;
	public static int rightMouseButton = 1;
	public static int middleMouseButton = 2;

	/**
	 * The method that updates the boolean arrays and checks for keyboard and
	 * mouse updates
	 */
	public void update()
	{
		keysLast = keys.clone();
		buttonsLast = buttons.clone();
		keyboardUpdate();
		mouseUpdate();
	}

	/**
	 * This checks for key events and determines if the key was pressed or
	 * released
	 */
	private void keyboardUpdate()
	{
		while (Keyboard.next())
		{
			if (Keyboard.getEventKey() <= Keyboard.getKeyCount())
			{
				if (Keyboard.getEventKeyState())
				{
					keyDown(Keyboard.getEventKey());
				} else
				{
					keyUp(Keyboard.getEventKey());
				}
			}
		}
	}

	/**
	 * This checks for mouse events and determines if the button was pressed or
	 * released
	 */
	private void mouseUpdate()
	{
		if (Mouse.getEventButton() <= Mouse.getButtonCount())
		{
			if (Mouse.isButtonDown(Mouse.getEventButton()))
			{
				buttonDown(Mouse.getEventButton());
			} else
			{
				buttonUp(Mouse.getEventButton());
			}
		}
	}

	/**
	 * Determines if a key is down
	 * 
	 * @param keyCode
	 *            The key that is being checked
	 * @return Whether or not the key is down
	 */
	public static boolean isKey(int keyCode)
	{
		return keys[keyCode];
	}

	/**
	 * Determines if a key has just been pressed
	 * 
	 * @param keyCode
	 *            The key that is being checked
	 * @return Whether or not the key has just been pressed
	 */
	public static boolean isKeyPressed(int keyCode)
	{
		return keys[keyCode] && !keysLast[keyCode];
	}

	/**
	 * Determines if a key has just been released
	 * 
	 * @param keyCode
	 *            The key that is being checked
	 * @return Whether or not the key has just been released
	 */
	public static boolean isKeyReleased(int keyCode)
	{
		return !keys[keyCode] && keysLast[keyCode];
	}

	/**
	 * Determines if a button is down
	 * 
	 * @param button
	 *            The button that is being checked
	 * @return Whether or not the button is down
	 */
	public static boolean isButton(int button)
	{
		return buttons[button];
	}

	/**
	 * Determines if a button has just been pressed
	 * 
	 * @param button
	 *            The button that is being checked
	 * @return Whether or not the button has just been pressed
	 */
	public static boolean isButtonPressed(int button)
	{
		return buttons[button] && !buttonsLast[button];
	}

	/**
	 * Determines if a button has just been released
	 * 
	 * @param button
	 *            The button that is being checked
	 * @return Whether or not the button has just been pressed
	 */
	public static boolean isButtonReleased(int button)
	{
		return !buttons[button] && buttonsLast[button];
	}

	/**
	 * Alters the keys boolean array to show which key is down
	 * 
	 * @param keycode
	 *            The key that went down
	 */
	private void keyDown(int keycode)
	{
		keys[keycode] = true;
	}

	/**
	 * Alters the keys boolean array to show which key was released
	 * 
	 * @param keycode
	 *            The key that was released
	 */
	private void keyUp(int keycode)
	{
		keys[keycode] = false;
	}

	/**
	 * Alters the buttons boolean array to show which button is pressed
	 * 
	 * @param buttoncode
	 *            The button that went down
	 */
	private void buttonDown(int buttoncode)
	{
		buttons[buttoncode] = true;
	}

	/**
	 * Alters the buttons boolean array to show which button was released
	 * 
	 * @param buttoncode
	 *            The button that was released
	 */
	private void buttonUp(int buttoncode)
	{
		buttons[buttoncode] = false;
	}

	/**
	 * Gets the X-coordinate of the mouse in the window
	 * 
	 * @return The X-coordinate of the mouse
	 */
	public static int getMouseX()
	{
		if ((double) Display.getWidth() / (double) Display.getHeight() == (double) GameContainer.getDesignWidth()
				/ (double) GameContainer.getDesignHeight())
		{
			return (int) (Mouse.getX() * ((double) GameContainer.getDesignWidth() / (double) Display.getWidth()));
		} else if ((double) Display.getWidth() / (double) Display.getHeight() < (double) GameContainer.getDesignWidth()
				/ (double) GameContainer.getDesignHeight())
		{
			return (int) (Mouse.getX() * ((double) GameContainer.getDesignWidth() / (double) Display.getWidth()));
		} else
		{
			return (int) ((Mouse.getX() * ((double) GameContainer.getDesignHeight() / (double) Display.getHeight()))
					- (((double) Display.getWidth() - (double) GameContainer.getDesignWidth()
							* (double) Display.getHeight() / (double) GameContainer.getDesignHeight()) / (double) 2)
							* ((double) GameContainer.getDesignHeight() / (double) Display.getHeight()));
		}
	}

	/**
	 * Gets the Y-coordinate of the mouse in the window
	 * 
	 * @return The Y-coordinate of the mouse
	 */
	public static int getMouseY()
	{
		if ((double) Display.getWidth() / (double) Display.getHeight() == (double) GameContainer.getDesignWidth()
				/ (double) GameContainer.getDesignHeight())
		{
			return (int) ((Display.getHeight() - Mouse.getY())
					* ((double) GameContainer.getDesignHeight() / (double) Display.getHeight()));
		} else if ((double) Display.getWidth() / (double) Display.getHeight() < (double) GameContainer.getDesignWidth()
				/ (double) GameContainer.getDesignHeight())
		{
			return (int) (((Display.getHeight() - Mouse.getY())
					* ((double) GameContainer.getDesignWidth() / (double) Display.getWidth()))
					- (((double) Display.getHeight() - (double) GameContainer.getDesignHeight()
							* (double) Display.getWidth() / (double) GameContainer.getDesignWidth()) / (double) 2)
							* ((double) GameContainer.getDesignWidth() / (double) Display.getWidth()));
		} else
		{
			return (int) ((Display.getHeight() - Mouse.getY())
					* ((double) GameContainer.getDesignHeight() / (double) Display.getHeight()));
		}
	}

	/**
	 * Sets the mouse's position along both the X-axis and Y-axis
	 * 
	 * @param posX
	 *            The desired position along the X-axis
	 * @param posY
	 *            The desired position along the Y-axis
	 */
	public static void setMousePosition(int posX, int posY)
	{
		int x;
		int y;

		if ((double) Display.getWidth() / (double) Display.getHeight() == (double) GameContainer.getDesignWidth()
				/ (double) GameContainer.getDesignHeight())
		{
			x = (int) (posX / ((double) GameContainer.getDesignWidth() / (double) Display.getWidth()));
			y = (int) ((Display.getHeight()
					- (double) posY / ((double) GameContainer.getDesignHeight() / (double) Display.getHeight())));
		} else if ((double) Display.getWidth() / (double) Display.getHeight() < (double) GameContainer.getDesignWidth()
				/ (double) GameContainer.getDesignHeight())
		{
			x = (int) (posX / ((double) GameContainer.getDesignWidth() / (double) Display.getWidth()));
			y = (int) ((double) Display.getHeight()
					- (((double) posY * ((double) Display.getWidth() / (double) GameContainer.getDesignWidth()))
							+ (((double) Display.getHeight() - ((double) GameContainer.getDesignHeight()
									* ((double) Display.getWidth() / (double) GameContainer.getDesignWidth())))
							/ (double) 2)));
		} else
		{
			x = (int) (((double) posX * ((double) Display.getHeight() / (double) GameContainer.getDesignHeight()))
					+ (((double) Display.getWidth() - ((double) GameContainer.getDesignWidth()
							* ((double) Display.getHeight() / (double) GameContainer.getDesignHeight())))
							/ (double) 2));
			y = (int) ((Display.getHeight()
					- (double) posY / ((double) GameContainer.getDesignHeight() / (double) Display.getHeight())));
		}
		Mouse.setCursorPosition(x, y);
	}
}
