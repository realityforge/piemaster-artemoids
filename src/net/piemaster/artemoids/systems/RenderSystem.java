package net.piemaster.artemoids.systems;

import net.piemaster.artemoids.components.SpatialForm;
import net.piemaster.artemoids.components.Transform;
import net.piemaster.artemoids.spatials.AsteroidSpatial;
import net.piemaster.artemoids.spatials.Explosion;
import net.piemaster.artemoids.spatials.Missile;
import net.piemaster.artemoids.spatials.PlayerImageShip;
import net.piemaster.artemoids.spatials.PlayerShip;
import net.piemaster.artemoids.spatials.Spatial;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.artemis.utils.Bag;

public class RenderSystem extends EntityProcessingSystem
{
	private Graphics graphics;
	private Bag<Spatial> spatials;
	private ComponentMapper<SpatialForm> spatialFormMapper;
	private ComponentMapper<Transform> transformMapper;
	private GameContainer container;

	@SuppressWarnings("unchecked")
	public RenderSystem(GameContainer container)
	{
		super(Transform.class, SpatialForm.class);
		this.container = container;
		this.graphics = container.getGraphics();

		spatials = new Bag<Spatial>();
	}

	@Override
	public void initialize()
	{
		spatialFormMapper = new ComponentMapper<SpatialForm>(SpatialForm.class,
				world);
		transformMapper = new ComponentMapper<Transform>(Transform.class, world);
	}

	@Override
	protected void process(Entity e)
	{
		Spatial spatial = spatials.get(e.getId());
		Transform transform = transformMapper.get(e);

		if (transform.getX() >= 0 && transform.getY() >= 0
				&& transform.getX() < container.getWidth()
				&& transform.getY() < container.getHeight() && spatial != null
				&& spatialFormMapper.get(e).isVisible())
		{
			spatial.render(graphics);
		}
	}

	@Override
	protected void added(Entity e)
	{
		Spatial spatial = createSpatial(e);
		if (spatial != null)
		{
			spatial.initalize();
			spatials.set(e.getId(), spatial);
		}
	}

	@Override
	protected void removed(Entity e)
	{
		spatials.set(e.getId(), null);
	}

	private Spatial createSpatial(Entity e)
	{
		SpatialForm spatialForm = spatialFormMapper.get(e);
		String spatialFormFile = spatialForm.getSpatialFormFile();

		if ("PlayerShip".equalsIgnoreCase(spatialFormFile))
		{
			return new PlayerShip(world, e);
		}
		else if ("PlayerImageShip".equalsIgnoreCase(spatialFormFile))
		{
			return new PlayerImageShip(world, e);
		}
		else if ("Missile".equalsIgnoreCase(spatialFormFile))
		{
			return new Missile(world, e);
		}
		else if ("Asteroid".equalsIgnoreCase(spatialFormFile))
		{
			return new AsteroidSpatial(world, e);
		}
		else if ("BulletExplosion".equalsIgnoreCase(spatialFormFile))
		{
			return new Explosion(world, e, 10);
		}
		else if ("ShipExplosion".equalsIgnoreCase(spatialFormFile))
		{
			return new Explosion(world, e, 30);
		}

		return null;
	}

}
