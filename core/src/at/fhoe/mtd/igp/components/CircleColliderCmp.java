/**
This file is part of Asteroid Clone by Michael Troger.

Asteroid Clone by Michael Troger is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Asteroid Clone by Michael Troger is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Asteroid Clone by Michael Troger.  If not, see <http://www.gnu.org/licenses/>.
*/
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
