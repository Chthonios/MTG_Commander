package com.frederic_leblanc.mtg_commander;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dev-Lautréamont on 2015-08-22.
 */
public class Player implements Parcelable{

	private String name;
	private Commander commander;
	private int life;
	private int poison;

	public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {

		@Override
		public Player createFromParcel(Parcel source) {
			return new Player(source);
		}

		@Override
		public Player[] newArray(int size) {
			return new Player[size];
		}
	};

	public Player(String name, Commander commander, int life, int poison){
		setName(name);
		setCommander(commander);
		setLife(life);
		setPoison(poison);
	}

	public Player() {
		this("", new Commander(),0,0);
	}

	public Player(Player player){
		this(player.getName(),player.getCommander(),player.getLife(),player.getPoison());
	}

	public Player(Parcel parcel) {
		this(parcel.readString(), (Commander) parcel.readParcelable(Commander.class.getClassLoader()),parcel.readInt(),parcel.readInt());
	}

	public String getName() {
		return name;
	}

	public  void setName(String name) {
		this.name = name;
	}

	public Commander getCommander() {
		return commander;
	}

	public void setCommander(Commander commander) {
		this.commander = commander;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getPoison() {
		return poison;
	}

	public void setPoison(int poison) {
		this.poison = poison;
	}

	/**
	 * Describe the kinds of special objects contained in this Parcelable's
	 * marshalled representation.
	 *
	 * @return a bitmask indicating the set of special object types marshalled
	 * by the Parcelable.
	 */
	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 * Flatten this object in to a Parcel.
	 *
	 * @param dest  The Parcel in which the object should be written.
	 * @param flags Additional flags about how the object should be written.
	 *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(getName());
		dest.writeParcelable(getCommander(), PARCELABLE_WRITE_RETURN_VALUE);
		dest.writeInt(getLife());
		dest.writeInt(getPoison());
	}
}
