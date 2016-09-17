package at.fhoe.mtd.igp.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PoseCmp extends Component {

		/** Visual shape of this ship. */
		//private static final float[] VERTICES = {
		//	0.0f, 1.0f, -1.0f, -1.0f, 0.0f, -0.7f, 1.0f, -1.0f
		//};
		
		public Vector2 pos;
		public float angle;
		
		public PoseCmp() {
			pos = new Vector2();
			angle = 0;
		}
		
		public PoseCmp(Vector2 pos, float angle) {
			super();
			this.pos = pos;
			this.angle = angle;
		}
		
		//public Vector2 vel = new Vector2();
		//public float angVel = 0;
		
		
		
}
