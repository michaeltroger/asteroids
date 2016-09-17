package messages;

import com.badlogic.ashley.core.Entity;

public class AsteroidDestroyedMsg {

	public static final int MSG_ID = 0x004A; // arbitrary but unique message id

		private float x;
		private float y;
		private int size;

	public AsteroidDestroyedMsg(float x, float y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}

	public int getSize() {
		return this.size;
	}
	
}
