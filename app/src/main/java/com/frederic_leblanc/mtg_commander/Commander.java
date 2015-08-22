package com.frederic_leblanc.mtg_commander;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dev-Lautramont on 2015-08-22.
 */
public class Commander implements Parcelable {

	private String name;
	private String image;
	private String cost;
	private int cast;
	private int death;

	public static final Parcelable.Creator<Commander> CREATOR = new Parcelable.Creator<Commander>(){

		/**
		 * Create a new instance of the Parcelable class, instantiating it
		 * from the given Parcel whose data had previously been written by
		 * {@link Parcelable#writeToParcel Parcelable.writeToParcel()}.
		 *
		 * @param source The Parcel to read the object's data from.
		 * @return Returns a new instance of the Parcelable class.
		 */
		@Override
		public Commander createFromParcel(Parcel source) {
			return new Commander(source);
		}

		/**
		 * Create a new array of the Parcelable class.
		 *
		 * @param size Size of the array.
		 * @return Returns an array of the Parcelable class, with every entry
		 * initialized to null.
		 */
		@Override
		public Commander[] newArray(int size) {
			return new Commander[size];
		}
	};

	public static final ClassLoaderCreator<Commander> LOADER_CREATOR = new ClassLoaderCreator<Commander>() {
		@Override
		public Commander createFromParcel(Parcel source, ClassLoader loader) {
			return null;
		}

		@Override
		public Commander createFromParcel(Parcel source) {
			return new Commander(source);
		}

		@Override
		public Commander[] newArray(int size) {
			return new Commander[size];
		}
	};

	public Commander(String name, String image, String cost, int cast, int death){
		setName(name);
		setImage(image);
		setCost(cost);
		setCast(cast);
		setDeath(death);
	}

	public Commander(){
		this("","","",0,0);
	}

	public Commander(Commander commander){
		this(commander.getName(),commander.getImage(),commander.getCost(),commander.getCast(),commander.getDeath());
	}

	public Commander(Parcel parcel){
		this(parcel.readString(),parcel.readString(),parcel.readString(),parcel.readInt(),parcel.readInt());
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	private void setImage(String image) {
		this.image = image;
	}

	public String getCost() {
		return cost;
	}

	private void setCost(String cost) {
		this.cost = cost;
	}

	public int getCast() {
		return cast;
	}

	public void setCast(int cast) {
		this.cast = cast;
	}

	public int getDeath() {
		return death;
	}

	public void setDeath(int death) {
		this.death = death;
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
	 *
	 * cost and image switch from original code...
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(getName());
		dest.writeString(getImage());
		dest.writeString(getCost());
		dest.writeInt(getCast());
		dest.writeInt(getDeath());
	}
}
