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
