package server;

import com.badlogic.gdx.utils.Pool.Poolable;

import common.Const;
import common.net.Message;

public class MessageWrap implements Poolable {

	public Message msg;
	public int clientId;
	
	@Override
	public void reset() {
		this.msg = null;
		this.clientId = Const.NULL_INDEX;
	}
}
