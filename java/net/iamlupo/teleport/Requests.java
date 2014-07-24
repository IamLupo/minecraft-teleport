package net.iamlupo.teleport;

import java.util.ArrayList;

import net.minecraftforge.common.config.ConfigCategory;

public class Requests  extends ArrayList<Request> {
	
	@Override
	public boolean add(Request _request) {
		//Remove Duplicated
		while(this.indexOf(_request) != -1) {
			super.remove(_request);
		}
		
		//Add to ArrayList
		return super.add(_request);
	}
}
