package at.fhoe.mtd.igp.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class ShipCmp extends Component {
	
	public Vector2 heading;
	public float thrust;
	public float torque;
	public int player;

	public int up;
	public int down;
	public int left;
	public int right;
	public int shoot;

	public ShipCmp(int player) {
		this(player, new Vector2(), 0, 0);
	}
	
	public ShipCmp(int player, Vector2 heading, float thrust, float torque) {
		super();
		this.player = player;
		this.heading = heading;
		this.thrust = thrust;
		this.torque = torque;

		switch (player) {
			case 2:
				left = Input.Keys.A;
				right = Input.Keys.D;
				up = Input.Keys.W;
				down = Input.Keys.S;
				shoot = Input.Keys.SPACE;
				break;
			case 1:
			default:
				left = Input.Keys.LEFT;
				right = Input.Keys.RIGHT;
				up = Input.Keys.UP;
				down = Input.Keys.DOWN;
				shoot = Input.Keys.ENTER;
		}
	}
	
	
}
