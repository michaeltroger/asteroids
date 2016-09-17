package at.fhoe.mtd.igp.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PowerUpCmp extends Component {
	public Vector2 heading;
	public float speed;
	
	public PowerUpCmp(float speed, Vector2 heading) {
		super();
		this.speed = speed;
		this.heading = heading;
	} 
}
