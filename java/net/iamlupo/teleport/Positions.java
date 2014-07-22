package net.iamlupo.teleport;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import net.minecraftforge.common.config.*;

public class Positions extends ArrayList<Position> {
	private File cf_file;
	private Configuration cf;
	
	public Positions() {
		this.cf_file = new File("config/Teleport.cfg");
		
		this.cf = new Configuration(cf_file);
		this.cf.load();
		
		for (String cc_name : cf.getCategoryNames()) {
			ConfigCategory cc = cf.getCategory(cc_name);
			for (String key : cc.getValues().keySet()) {
				Property p = cc.get(key);
				double[] pos = p.getDoubleList();
				
				Position position = new Position(UUID.fromString(cc_name), key);
				position.setPosition(pos[0], pos[1], pos[2]);
				
				super.add(position);
			}
		}
	}
	
	@Override
	public boolean add(Position _pos) {
		//Remove Duplicated
		while(this.indexOf(_pos) != -1) {
			this.remove(_pos);
		}
		
		//Add Item of Config file
		addConfigItem(_pos);
		
		//Add to ArrayList
		return super.add(_pos);
	}
	
	private void addConfigItem(Position _pos) {
		String uuid = _pos.uuid.toString();
		double[] pos = {_pos.posX , _pos.posY, _pos.posZ };
		
		cf.getCategory(uuid);
		cf.get(uuid, _pos.name, pos, "");
		
		cf.save();
	}
	
	public boolean remove(Position _pos) {
		//Remove Item of Config file
		String uuid = _pos.uuid.toString();
		ConfigCategory cc = cf.getCategory(uuid);
		cc.remove(_pos.name);
		cf.save();
		
		return super.remove(_pos);
	}
}
