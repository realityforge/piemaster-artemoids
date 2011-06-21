package net.piemaster.artemoids.spatials;

import net.piemaster.artemoids.components.Transform;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.World;

public class Missile extends Spatial
{
	private Transform transform;

	public Missile(World world, Entity owner)
	{
		super(world, owner);
	}

	@Override
	public void initalize()
	{
		ComponentMapper<Transform> transformMapper = new ComponentMapper<Transform>(
				Transform.class, world.getEntityManager());
		transform = transformMapper.get(owner);
	}

	@Override
	public void render(Graphics g)
	{
		g.setColor(Color.white);
		g.setAntiAlias(true);
		g.fillRect(transform.getX() - 1, transform.getY() - 3, 2, 6);
	}

}
