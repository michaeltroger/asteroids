package at.fhoe.mtd.igp.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class AsteroidCmp extends Component {
	public float speed;
	public Vector2 heading;
	public int size;
	
	public AsteroidCmp(int size, float speed, Vector2 heading) {
		super();
		this.size = size;
		this.speed = speed;
		this.heading = heading;
	} 
	
		
	
}
