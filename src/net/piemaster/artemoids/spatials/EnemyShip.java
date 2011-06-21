package net.piemaster.artemoids.spatials;

import net.piemaster.artemoids.components.Transform;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

public class EnemyShip extends Spatial
{

	private Transform transform;
	private Polygon ship;

	public EnemyShip(World world, Entity owner)
	{
		super(world, owner);
	}

	@Override
	public void initalize()
	{
		ComponentMapper<Transform> transformMapper = new ComponentMapper<Transform>(
				Transform.class, world.getEntityManager());
		transform = transformMapper.get(owner);

		ship = new Polygon();
		ship.addPoint(-10, -10);
		ship.addPoint(10, -10);
		ship.addPoint(0, 10);
		ship.setClosed(true);
	}

	@Override
	public void render(Graphics g)
	{
		g.setColor(Color.red);
		g.setAntiAlias(true);
		ship.setLocation(transform.getX(), transform.getY());
		g.fill(ship);
	}

}
