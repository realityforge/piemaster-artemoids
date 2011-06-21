package net.piemaster.artemoids;

import net.piemaster.artemoids.components.Enemy;
import net.piemaster.artemoids.components.Expires;
import net.piemaster.artemoids.components.Health;
import net.piemaster.artemoids.components.SpatialForm;
import net.piemaster.artemoids.components.Transform;
import net.piemaster.artemoids.components.Velocity;
import net.piemaster.artemoids.components.Weapon;

import com.artemis.Entity;
import com.artemis.World;

public class EntityFactory
{
	public static Entity createMissile(World world)
	{
		Entity missile = world.createEntity();
		missile.setGroup("BULLETS");

		missile.addComponent(new Transform());
		missile.addComponent(new SpatialForm("Missile"));
		missile.addComponent(new Velocity());
		missile.addComponent(new Expires(2000));

		return missile;
	}

	public static Entity createEnemyShip(World world)
	{
		Entity e = world.createEntity();
		e.setGroup("SHIPS");

		e.addComponent(new Transform());
		e.addComponent(new SpatialForm("EnemyShip"));
		e.addComponent(new Health(10));
		e.addComponent(new Weapon());
		e.addComponent(new Enemy());
		e.addComponent(new Velocity());

		return e;
	}

	public static Entity createBulletExplosion(World world, float x, float y)
	{
		Entity e = world.createEntity();

		e.setGroup("EFFECTS");

		e.addComponent(new Transform(x, y));
		e.addComponent(new SpatialForm("BulletExplosion"));
		e.addComponent(new Expires(1000));

		return e;
	}

	public static Entity createShipExplosion(World world, float x, float y)
	{
		Entity e = world.createEntity();

		e.setGroup("EFFECTS");

		e.addComponent(new Transform(x, y));
		e.addComponent(new SpatialForm("ShipExplosion"));
		e.addComponent(new Expires(1000));

		return e;
	}

}
