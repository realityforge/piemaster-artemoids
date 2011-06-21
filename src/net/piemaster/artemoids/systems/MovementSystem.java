package net.piemaster.artemoids.systems;

import net.piemaster.artemoids.components.Transform;
import net.piemaster.artemoids.components.Velocity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;
import com.artemis.utils.TrigLUT;

public class MovementSystem extends EntityProcessingSystem
{
	private GameContainer container;
	private Graphics g;
	private ComponentMapper<Velocity> velocityMapper;
	private ComponentMapper<Transform> transformMapper;

	public MovementSystem(GameContainer container)
	{
		super(Transform.class, Velocity.class);
		this.container = container;
	}

	@Override
	public void initialize()
	{
		velocityMapper = new ComponentMapper<Velocity>(Velocity.class, world.getEntityManager());
		transformMapper = new ComponentMapper<Transform>(Transform.class, world.getEntityManager());
	}

	@Override
	protected void process(Entity e)
	{
		Velocity velocity = velocityMapper.get(e);
		float v = velocity.getVelocity();

		Transform transform = transformMapper.get(e);

		float r = velocity.getAngleAsRadians();

		float xn = transform.getX() + (TrigLUT.cos(r) * v * world.getDelta());
		float yn = transform.getY() + (TrigLUT.sin(r) * v * world.getDelta());

		transform.setLocation(xn, yn);
	}

}
