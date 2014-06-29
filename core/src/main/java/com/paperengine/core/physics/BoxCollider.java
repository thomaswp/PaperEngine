package com.paperengine.core.physics;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.FixtureDef;

import playn.core.PlayN;
import playn.core.util.Clock;
import pythagoras.f.Point;

import com.paperengine.core.Component;
import com.paperengine.core.Scene;
import com.paperengine.core.Transform;
import com.paperengine.core.Transform.Data;

public class BoxCollider extends Component {

//	public final Point origin = new Point();
	public float width, height, angle;
	
	public float angularDampening, linearDampening, gravityScale = 1;
	public boolean fixedRotation, bullet;
	public BodyType bodyType = BodyType.DYNAMIC;
	
	public float density = 1, resitiution, friction = 0.2f;
	
	private transient Body body;
	private transient Fixture fixture;
	private Data lastTransform = new Data();
	private Point lastSize = new Point();
	
	@Override
	public void init() {
		super.init();
		createBody();
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
	}
	
	@Override
	public void paint(Clock clock) {
		super.paint(clock);
		if (body == null) return;
		Transform transform = gameObject().transform();
		if (!lastTransform.equals(transform)) {
			body.setTransform(new Vec2(
					transform.position.x * Scene.PHYSICS_SCALE, 
					transform.position.y * Scene.PHYSICS_SCALE), 
					transform.rotation + angle);
			body.setAwake(true);
		} else {
			transform.position.x = body.getPosition().x / Scene.PHYSICS_SCALE;
			transform.position.y = body.getPosition().y / Scene.PHYSICS_SCALE;
			transform.rotation = body.getAngle() - angle;
		}
		lastTransform.set(transform);
		
		if (lastSize.x != width * transform.scaleX || 
				lastSize.y != height * transform.scaleY) {
			createFixture();
		}
	}
	
	private void createBody() {
		if (gameObject().parent() != null) {
			PlayN.log().warn("Phyics not supported on child objects");
			return;
		}
		
		if (body != null) {
//			scene().physicsWorld().destroyBody(body);
		}
		
		BodyDef def = new BodyDef();
		def.angularDamping = angularDampening;
		def.bullet = bullet;
		def.fixedRotation = fixedRotation;
		def.gravityScale = gravityScale;
		def.linearDamping = linearDampening;
		def.type = bodyType;
		def.userData = this;

		Transform transform = gameObject().transform();		
		def.position.x = transform.position.x * Scene.PHYSICS_SCALE;
		def.position.y = transform.position.y * Scene.PHYSICS_SCALE;
		def.angle = transform.rotation + angle;
		
		body = scene().physicsWorld().createBody(def);
		lastTransform.set(transform);
		
		createFixture();
	}
	
	private void createFixture() {
		if (body == null) return;
		
		if (fixture != null) {
//			body.destroyFixture(fixture);
		}
		
		Transform transform = gameObject().transform();	
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width * transform.scaleX * Scene.PHYSICS_SCALE / 2, 
				height * transform.scaleY * Scene.PHYSICS_SCALE / 2);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = density;
		fixtureDef.restitution = resitiution;
		fixtureDef.friction = friction;
		fixtureDef.shape = shape;
		fixture = body.createFixture(fixtureDef);
		
		lastSize.set(width * transform.scaleX, height * transform.scaleY);
	}
}
