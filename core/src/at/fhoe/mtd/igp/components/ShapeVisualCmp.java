package at.fhoe.mtd.igp.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ShapeVisualCmp extends Component {

	
	/** Visual shape of this ship. */
	public float[] vertices;
	public Color color;
	public float offsetToTop;
	public TextureRegion textureRegion;

	public ShapeVisualCmp(float[] vertices, TextureRegion textureRegion, Color color, float offsetToTop) {
		super();
		this.vertices = vertices;
		this.textureRegion = textureRegion;
		this.color = color;
		this.offsetToTop = offsetToTop;
	}

}
