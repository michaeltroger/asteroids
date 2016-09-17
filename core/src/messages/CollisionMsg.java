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
