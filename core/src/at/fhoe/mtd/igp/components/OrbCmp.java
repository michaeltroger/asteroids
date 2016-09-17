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

/**
 * Created by Michael on 6/9/2015.
 */
public class OrbCmp extends Component {

    public float distance;

    public OrbCmp(float distance) {
        this.distance = distance;
    }
}
