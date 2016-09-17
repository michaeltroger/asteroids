package at.fhoe.mtd.igp.components;

import com.badlogic.ashley.core.Component;

public class CircleColliderCmp extends Component {

	public final static int CATEGORY_PLAYER = 0x0001;  // 0000000000000001 in binary
	public final static int CATEGORY_BULLET = 0x0002; // 0000000000000010 in binary
	public final static int CATEGORY_ASTEROID = 0x0004; // 0000000000000100 in binary
	public final static int CATEGORY_POWERUP = 0x0008;
	public final static int CATEGORY_ORB = 0x00016;

	//final int MASK_PLAYER = CATEGORY_BULLET | CATEGORY_ASTEROID; // or ~CATEGORY_PLAYER
	//final int MASK_ASTEROID = CATEGORY_BULLET | CATEGORY_PLAYER; // or ~CATEGORY_ASTEROID
	//final inz MASK_SCENERY = -1;
	
	public float radius;
	public int flag;
	public int mask;
	
	public CircleColliderCmp(float radius, int flag) {
		this.radius = radius;
		this.flag = flag;
		this.mask =  ~this.flag; // collide with every but this type
	}
	
	public CircleColliderCmp(float radius, int flag, int mask) {
		this.radius = radius;
		this.flag = flag;
		this.mask =  mask; // collide with every but this type
	}

	
	/*
	 
	public CircleColliderCmp radius(float radius) {
		this.radius = radius;
		
		return this;
	}
	
	 */
	
}
