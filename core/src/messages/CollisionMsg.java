package messages;

import com.badlogic.ashley.core.Entity;

public class CollisionMsg {
	
	public static final int MSG_ID = 0x002A; // arbitrary but unique message id
	
	private Entity first;
	private Entity second;
	
	public CollisionMsg(Entity first, Entity second) {
		this.first = first;
		this.second = second;
		
	}
	
	public Entity getFirst() {
		return this.first;
	}
	
	public Entity getSecond() {
		return this.second;
	}
	
}
