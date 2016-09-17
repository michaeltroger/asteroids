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
