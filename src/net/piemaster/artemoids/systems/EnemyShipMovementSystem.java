package net.piemaster.artemoids.systems;

import net.piemaster.artemoids.components.Enemy;
import net.piemaster.artemoids.components.Transform;
import net.piemaster.artemoids.components.Velocity;

import org.newdawn.slick.GameContainer;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class EnemyShipMovementSystem extends EntityProcessingSystem
{
	private GameContainer container;
	private ComponentMapper<Transform> transformMapper;
	private ComponentMapper<Velocity> velocityMapper;

	public EnemyShipMovementSystem(GameContainer container)
	{
		super(Transform.class, Enemy.class, Velocity.class);
		this.container = container;
	}

	@Override
	public void initialize()
	{
		transformMapper = new ComponentMapper<Transform>(Transform.class, world.getEntityManager());
		velocityMapper = new ComponentMapper<Velocity>(Velocity.class, world.getEntityManager());
	}

	@Override
	protected void process(Entity e)
	{
		Transform transform = transformMapper.get(e);
		Velocity velocity = velocityMapper.get(e);

		if (transform.getX() > container.getWidth() || transform.getX() < 0)
		{
			velocity.addAngle(180);
		}
	}

}
