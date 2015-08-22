package com.frederic_leblanc.mtg_commander;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Dev-Lautramont on 2015-08-22.
 */
public class Players {

	private LinkedList<Player> players;
	private HashMap<String, Integer> commanderDamageTo;
	private HashMap<String, Integer> commanderDamageFrom;

	public Players(){
		players = new LinkedList<Player>();
		commanderDamageTo = new HashMap<String, Integer>();
		commanderDamageFrom = new HashMap<String, Integer>();
	}

	public void setPlayer(Player player){
		players.add(player);
		commanderDamageTo.put(player.getName(),0);
		commanderDamageFrom.put(player.getName(),0);
	}

	public Player getPlayer(int index){
		return players.get(index);
	}

	public HashMap<String, Integer> getCommanderDamageTo(){
		return commanderDamageTo;
	}

	public HashMap<String, Integer> getCommanderDamageFrom(){
		return commanderDamageFrom;
	}

	public void setCommanderDamageTo(String player,int damage){

	}

	public void setCommanderDamageFrom(String player, int damage) {

	}

	public LinkedList<Player> getPlayersList(){
		return players;
	}

	public int getSize(){
		return players.size();
	}

	public void clear(){
		players.clear();
		commanderDamageTo.clear();
		commanderDamageFrom.clear();
	}
}
