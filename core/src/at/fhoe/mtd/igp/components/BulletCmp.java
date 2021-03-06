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
import com.badlogic.gdx.math.Vector2;

public class BulletCmp extends Component {
	public float speed;
	public float lifeTime; // in seconds
	public Vector2 heading; 
	

	public BulletCmp(float speed, float lifeTime, Vector2 heading) {
		super();
		this.speed = speed;
		this.lifeTime = lifeTime;
		this.heading = heading;
	}
	
	
}
