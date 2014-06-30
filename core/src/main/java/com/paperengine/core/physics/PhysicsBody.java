package com.paperengine.core.physics;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;

import playn.core.PlayN;
import playn.core.util.Clock;

import com.paperengine.core.Component;
import com.paperengine.core.Scene;
import com.paperengine.core.Transform;
import com.paperengine.core.Transform.Data;

public class PhysicsBody extends Component {

	public float angularDampening, linearDampening, gravityScale = 1;
	public boolean fixedRotation, bullet;
	public BodyType bodyType = BodyType.DYNAMIC;

	private transient Body body;
	private Data lastTransform = new Data();
	
	public Body body() {
		return body;
	}
	
	@Override
	public void init() {
		super.init();
		createBody();
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
					transform.rotation);
			body.setAwake(true);
		} else {
			transform.position.x = body.getPosition().x / Scene.PHYSICS_SCALE;
			transform.position.y = body.getPosition().y / Scene.PHYSICS_SCALE;
			transform.rotation = body.getAngle();
		}
		lastTransform.set(transform);
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
		def.angle = transform.rotation;
		
		body = scene().physicsWorld().createBody(def);
		lastTransform.set(transform);
	}
}
