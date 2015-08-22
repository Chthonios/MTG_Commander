package com.frederic_leblanc.mtg_commander;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.Locale;

/**
 * Created by Frédéric Leblanc on 2015-08-22.
 */
public class PlayersPagerAdapter extends FragmentPagerAdapter {

	public PlayersPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		// Return a PlaceholderFragment (defined as a static inner class below).
		return PlayerPlaceHolderFragment.newInstance(position + 1);
	}

	@Override
	public int getCount() {
		// Show 3 total pages.
		return MainActivity.players.getSize();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();

		return MainActivity.players.getPlayer(position).getName();
	}
}
