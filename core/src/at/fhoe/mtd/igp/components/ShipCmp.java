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
