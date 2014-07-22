package net.iamlupo.teleport;

import java.util.UUID;

public class Position {
	public UUID uuid;
	public String name;
	public double posX;
	public double posY;
	public double posZ;
	
	public Position(UUID _uuid, String _name) {
		this.uuid = _uuid;
		this.name = _name;
	}
	
	public void setPosition(double _posX, double _posY, double _posZ) {
		this.posX = _posX;
		this.posY = _posY;
		this.posZ = _posZ;
	}
	
	@Override
    public boolean equals(Object object) {
        if (object != null && object instanceof Position)
        {
        	
        	if(	this.name.equals(((Position) object).name) &&
        		this.uuid.equals(((Position) object).uuid))
            return true;
        }
        return false;
    }
}
