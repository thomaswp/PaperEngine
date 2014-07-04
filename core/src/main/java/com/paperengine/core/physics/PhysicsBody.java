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

	public float angularDamping, linearDamping, gravityScale = 1;
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
			updateBodyTransform();
		} else {
			transform.position.x = body.getPosition().x / Scene.PHYSICS_SCALE;
			transform.position.y = body.getPosition().y / Scene.PHYSICS_SCALE;
			transform.rotation = body.getAngle();
			lastTransform.set(transform);
		}
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		updateBodyDef();
	}

	protected void updateBodyDef() {
		boolean update = false;
		if (body.m_angularDamping != angularDamping) {
			body.setAngularDamping(angularDamping);
			update = true;
		}
		if (body.isBullet() != bullet) {
			body.setBullet(bullet);
			update = true;
		}
		if (body.isFixedRotation() != fixedRotation) {
			body.setFixedRotation(fixedRotation);
			update = true;
		}
		if (body.m_gravityScale != gravityScale) {
			body.setGravityScale(gravityScale);
			update = true;
		}
		if (body.m_linearDamping != linearDamping) {
			body.setLinearDamping(linearDamping);
			update = true;
		}
		if (body.getType() != bodyType) {
			body.setType(bodyType);
			update = true;
		}
		if (update) body.setAwake(true);
	}
	
	protected void updateBodyTransform() { 
		Transform transform = gameObject().transform();
		body.setTransform(new Vec2(
				transform.position.x * Scene.PHYSICS_SCALE, 
				transform.position.y * Scene.PHYSICS_SCALE), 
				transform.rotation);
		body.setAwake(true);	
		lastTransform.set(transform);
	}
	
	private void createBody() {
		if (gameObject().parent() != null) {
			PlayN.log().warn("Phyics not supported on child objects");
			return;
		}
		
		if (body == null) {
			body = scene().physicsWorld().createBody(new BodyDef());
			body.setUserData(this);
		}
		
		updateBodyDef();
		updateBodyTransform();
		
	}
}
