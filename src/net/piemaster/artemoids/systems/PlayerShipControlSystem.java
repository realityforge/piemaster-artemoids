package net.piemaster.artemoids.systems;

import net.piemaster.artemoids.EntityFactory;
import net.piemaster.artemoids.components.Player;
import net.piemaster.artemoids.components.Transform;
import net.piemaster.artemoids.components.Velocity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class PlayerShipControlSystem extends EntityProcessingSystem implements KeyListener
{
	private GameContainer container;
	private boolean moveRight;
	private boolean moveLeft;
	private boolean moveUp;
	private boolean moveDown;
	private boolean shoot;
	private ComponentMapper<Transform> transformMapper;

	public PlayerShipControlSystem(GameContainer container)
	{
		super(Transform.class, Player.class);
		this.container = container;
	}

	@Override
	public void initialize()
	{
		transformMapper = new ComponentMapper<Transform>(Transform.class, world.getEntityManager());
		container.getInput().addKeyListener(this);
	}

	@Override
	protected void process(Entity e)
	{
		Transform transform = transformMapper.get(e);

		if (moveLeft)
		{
			transform.addX(world.getDelta() * -0.3f);
		}
		if (moveRight)
		{
			transform.addX(world.getDelta() * 0.3f);
		}
		if (moveUp)
		{
			transform.addY(world.getDelta() * -0.3f);
		}
		if (moveDown)
		{
			transform.addY(world.getDelta() * 0.3f);
		}

		if (shoot)
		{
			Entity missile = EntityFactory.createMissile(world);
			missile.getComponent(Transform.class).setLocation(transform.getX(),
					transform.getY() - 20);
			missile.getComponent(Velocity.class).setVelocity(-0.5f);
			missile.getComponent(Velocity.class).setAngle(90);
			missile.refresh();

			shoot = false;
		}
	}

	@Override
	public void keyPressed(int key, char c)
	{
		if (key == Input.KEY_LEFT)
		{
			moveLeft = true;
			moveRight = false;
		}
		else if (key == Input.KEY_RIGHT)
		{
			moveRight = true;
			moveLeft = false;
		}
		else if (key == Input.KEY_UP)
		{
			moveUp = true;
			moveDown = false;
		}
		else if (key == Input.KEY_DOWN)
		{
			moveDown = true;
			moveUp = false;
		}
		else if (key == Input.KEY_SPACE)
		{
			shoot = true;
		}
	}

	@Override
	public void keyReleased(int key, char c)
	{
		if (key == Input.KEY_LEFT)
		{
			moveLeft = false;
		}
		else if (key == Input.KEY_RIGHT)
		{
			moveRight = false;
		}
		else if (key == Input.KEY_UP)
		{
			moveUp = false;
		}
		else if (key == Input.KEY_DOWN)
		{
			moveDown = false;
		}
		else if (key == Input.KEY_SPACE)
		{
			shoot = false;
		}
	}

	@Override
	public void inputEnded()
	{
	}

	@Override
	public void inputStarted()
	{
	}

	@Override
	public boolean isAcceptingInput()
	{
		return true;
	}

	@Override
	public void setInput(Input input)
	{
	}

}
