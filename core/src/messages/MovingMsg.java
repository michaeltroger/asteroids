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

public class MovingMsg {
	
	public static final int MSG_ID = 0x001A; // arbitrary but unique message id
	
	private int key;
	private boolean down;
	
	public MovingMsg(int key, boolean down) {
		this.key = key;
		this.down = down;
	}


	public int getKey() {
		return key;
	}

	public boolean isDown() {
		return down;
	}
	
	
	
}
