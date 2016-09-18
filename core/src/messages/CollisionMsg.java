/**
Copyright 2016 Michael Troger <https://michaeltroger.com>
This file is part of Asteroids Clone with LibGDX.

Asteroids Clone with LibGDX is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Asteroids Clone with LibGDX is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Asteroids Clone with LibGDX.  If not, see <http://www.gnu.org/licenses/>.
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
