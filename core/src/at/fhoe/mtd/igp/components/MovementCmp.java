package at.fhoe.mtd.igp.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class MovementCmp extends Component {
	
		public Vector2 vel = new Vector2();
		public float angVel = 0;
		
		public Vector2 acc = new Vector2();
		public float angAcc = 0;
		
		public float damping = 0;
		public float angDamping = 0;

}
