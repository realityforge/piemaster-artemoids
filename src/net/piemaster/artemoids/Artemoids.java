package net.piemaster.artemoids;

import java.util.Random;

import net.piemaster.artemoids.components.Health;
import net.piemaster.artemoids.components.Player;
import net.piemaster.artemoids.components.SpatialForm;
import net.piemaster.artemoids.components.Transform;
import net.piemaster.artemoids.components.Velocity;
import net.piemaster.artemoids.systems.AsteroidMovementSystem;
import net.piemaster.artemoids.systems.CollisionSystem;
import net.piemaster.artemoids.systems.ExpirationSystem;
import net.piemaster.artemoids.systems.MovementSystem;
import net.piemaster.artemoids.systems.PlayerShipControlSystem;
import net.piemaster.artemoids.systems.PlayerShipMovementSystem;
import net.piemaster.artemoids.systems.RenderSystem;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.SystemManager;
import com.artemis.World;

public class Artemoids extends BasicGame
{
	private World world;
	private GameContainer container;

	private EntitySystem renderSystem;
	private EntitySystem controlSystem;
	private EntitySystem movementSystem;
	private EntitySystem asteroidMovementSystem;
	private EntitySystem playerShipMovementSystem;
	private EntitySystem collisionSystem;
	private EntitySystem expirationSystem;

	public Artemoids()
	{
		super("Artemoids");
	}

	@Override
	public void init(GameContainer container) throws SlickException
	{
		this.container = container;

		world = new World();

		SystemManager systemManager = world.getSystemManager();
		renderSystem = systemManager.setSystem(new RenderSystem(container));
		controlSystem = systemManager.setSystem(new PlayerShipControlSystem(container));
		movementSystem = systemManager.setSystem(new MovementSystem(container));
		asteroidMovementSystem = systemManager.setSystem(new AsteroidMovementSystem(container));
		playerShipMovementSystem = systemManager.setSystem(new PlayerShipMovementSystem(container));
		collisionSystem = systemManager.setSystem(new CollisionSystem());
		expirationSystem = systemManager.setSystem(new ExpirationSystem());

		systemManager.initializeAll();

		initPlayerShip();
		initAsteroids();
	}
	
	private void initAsteroids()
	{
		Random r = new Random();
		for (int i = 0; 10 > i; i++)
		{
			Entity e = EntityFactory.createAsteroid(world, r.nextInt(container.getWidth()), r.nextInt(container.getHeight()), 5);

			e.getComponent(Velocity.class).setVelocity(0.05f);
			e.getComponent(Velocity.class).setAngle(r.nextInt(360));

			e.refresh();
		}
	}

	private void initPlayerShip()
	{
		Entity e = world.createEntity();
		e.setGroup("SHIPS");
		e.setTag("PLAYER");
		e.addComponent(new Transform(container.getWidth() / 2, container.getHeight() / 2));
		e.addComponent(new Velocity());
		e.addComponent(new SpatialForm("PlayerImageShip"));
		e.addComponent(new Health(30));
		e.addComponent(new Player());

		e.refresh();
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException
	{
		world.loopStart();

		world.setDelta(delta);

		controlSystem.process();
		movementSystem.process();
		asteroidMovementSystem.process();
		playerShipMovementSystem.process();
		collisionSystem.process();
		expirationSystem.process();
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException
	{
		renderSystem.process();
	}

	public static void main(String[] args) throws SlickException
	{
		Artemoids game = new Artemoids();
		AppGameContainer container = new AppGameContainer(game);
		container.setDisplayMode(1024, 768, false);
		container.setAlwaysRender(true);
		// container.setMinimumLogicUpdateInterval(1);
		// container.setMaximumLogicUpdateInterval(1);
		// container.setTargetFrameRate(60);
		container.start();
	}
}
