package at.fhoe.mtd.igp.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class BulletCmp extends Component {
	public float speed;
	public float lifeTime; // in seconds
	public Vector2 heading; 
	

	public BulletCmp(float speed, float lifeTime, Vector2 heading) {
		super();
		this.speed = speed;
		this.lifeTime = lifeTime;
		this.heading = heading;
	}
	
	
}
