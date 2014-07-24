package net.iamlupo.teleport;

import java.util.UUID;

public class Request {
	public UUID player;
	public UUID target;
	
	public Request(UUID _player, UUID _target) {
		this.player = _player;
		this.target = _target;
	}
	
	@Override
    public boolean equals(Object object) {
        if (object != null && object instanceof Request)
        {
        	if(	this.target.equals(((Request) object).target))
        		return true;
        }
        return false;
    }
}
