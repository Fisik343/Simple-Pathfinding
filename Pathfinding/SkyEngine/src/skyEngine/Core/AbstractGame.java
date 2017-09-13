package skyEngine.Core;

public abstract class AbstractGame
{

	/**
	 * This method should call all of the update functions for the objects in
	 * your game. It should also tell the engine which objects to update.
	 * 
	 * @param gc
	 *            A GameContainer object that will hold your game
	 * @param deltaTime
	 *            The time between updates
	 */
	public abstract void update(GameContainer gc, float deltaTime);

	/**
	 * This method should call all of the render functions for the objects in
	 * your game. It should also tell the engine which objects to render.
	 * 
	 * @param gc
	 *            A GameContainer object that will hold your game
	 * @param r
	 *            The main renderer object of your game
	 */
	public abstract void render(GameContainer gc, Renderer r);

}
