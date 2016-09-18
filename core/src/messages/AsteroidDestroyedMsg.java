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
