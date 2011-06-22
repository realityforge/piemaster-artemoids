package net.piemaster.artemoids;

import java.util.Random;

import net.piemaster.artemoids.components.Respawn;
import net.piemaster.artemoids.components.Transform;
import net.piemaster.artemoids.components.Velocity;
import net.piemaster.artemoids.systems.AsteroidMovementSystem;
import net.piemaster.artemoids.systems.CollisionSystem;
import net.piemaster.artemoids.systems.ExpirationSystem;
import net.piemaster.artemoids.systems.HudRenderSystem;
import net.piemaster.artemoids.systems.MovementSystem;
import net.piemaster.artemoids.systems.PlayerLifeSystem;
import net.piemaster.artemoids.systems.PlayerShipControlSystem;
import net.piemaster.artemoids.systems.PlayerShipMovementSystem;
import net.piemaster.artemoids.systems.RenderSystem;
import net.piemaster.artemoids.systems.RespawnSystem;

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
	private EntitySystem hudRenderSystem;
	private EntitySystem controlSystem;
	private EntitySystem movementSystem;
	private EntitySystem asteroidMovementSystem;
	private EntitySystem playerShipMovementSystem;
	private EntitySystem collisionSystem;
	private EntitySystem expirationSystem;
	private EntitySystem respawnSystem;
	private EntitySystem playerLifeSystem;

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
		controlSystem = systemManager.setSystem(new PlayerShipControlSystem(container));
		movementSystem = systemManager.setSystem(new MovementSystem(container));
		asteroidMovementSystem = systemManager.setSystem(new AsteroidMovementSystem(container));
		playerShipMovementSystem = systemManager.setSystem(new PlayerShipMovementSystem(container));
		collisionSystem = systemManager.setSystem(new CollisionSystem());
		expirationSystem = systemManager.setSystem(new ExpirationSystem());
		respawnSystem = systemManager.setSystem(new RespawnSystem());
		playerLifeSystem = systemManager.setSystem(new PlayerLifeSystem());
		
		renderSystem = systemManager.setSystem(new RenderSystem(container));
		hudRenderSystem = systemManager.setSystem(new HudRenderSystem(container));

		systemManager.initializeAll();

		initPlayerShip();
		initAsteroids();
	}

	private void initAsteroids()
	{
		Random r = new Random();
		int w3 = container.getWidth()/3;
		int h3 = container.getHeight()/3;
		int startX, startY;
		
		for (int i = 0; 10 > i; i++)
		{
			// Start somewhere not in the middle third of both axes
			// TODO Handle this more efficiently
			do
			{
				startX = r.nextInt(container.getWidth());
				startY = r.nextInt(container.getHeight());
			}
			while(startX > w3 && startX < 2*w3 && startY > h3 && startY < 2*h3);

			Entity e = EntityFactory.createAsteroid(world, startX, startY, 5);

			e.getComponent(Velocity.class).setVelocity(0.05f);
			e.getComponent(Velocity.class).setAngle(r.nextInt(360));

			e.refresh();
		}
	}

	private void initPlayerShip()
	{
		Entity player = EntityFactory.createPlayerShip(world);

		player.getComponent(Transform.class).setLocation(container.getWidth() / 2,
				container.getHeight() / 2);
		player.getComponent(Respawn.class).setRespawnLocation(container.getWidth() / 2,
				container.getHeight() / 2);

		player.refresh();
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
		playerLifeSystem.process();
		respawnSystem.process();
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException
	{
		renderSystem.process();
		hudRenderSystem.process();
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
