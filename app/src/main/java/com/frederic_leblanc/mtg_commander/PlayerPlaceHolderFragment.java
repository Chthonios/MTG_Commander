package com.frederic_leblanc.mtg_commander;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Frédéric Leblanc on 2015-08-22.
 */
public class PlayerPlaceHolderFragment extends Fragment{

	Player player;
	Uri imagePath;
	View rootView;
	TextView commanderName;
	TextView playerName;
	TextView commanderCast;
	TextView commanderDeath;
	ImageView commanderImage;

	private ListView commanderDamage;
	private static final String LIST_VIEW_KEY_NAME = "name";
	private static final String LIST_VIEW_KEY_DAMAGE = "damage";

//    Context context = PlayerPlaceHolderFragment.this;

	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	private static final String ARG_FRAGMENT_NAME = "fragment_name";
	/**
	 * Returns a new instance of this fragment for the given section
	 * number.
	 */
	public static PlayerPlaceHolderFragment newInstance(int sectionNumber) {
		PlayerPlaceHolderFragment fragment = new PlayerPlaceHolderFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		args.putString(ARG_FRAGMENT_NAME, MainActivity.getPlayers().getPlayer((sectionNumber) - 1).getName());
		fragment.setArguments(args);
		return fragment;
	}

	public PlayerPlaceHolderFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		player = MainActivity.getPlayers().getPlayer(getArguments().getInt(ARG_SECTION_NUMBER) - 1);
		imagePath = Uri.parse(MainActivity.URI_IMAGE_RESOURCE + player.getCommander().getImage());
		rootView = inflater.inflate(R.layout.fragment_main, container, false);

		commanderName = (TextView) rootView.findViewById(R.id.textViewPagerCommander);
		playerName = (TextView) rootView.findViewById(R.id.textViewPagerPlayerName);
		commanderImage = (ImageView) rootView.findViewById(R.id.imageViewPagerCommander);
		commanderCast = (TextView) rootView.findViewById(R.id.textViewCast);
		commanderDeath = (TextView) rootView.findViewById(R.id.textViewDeath);

		Log.i("PlaceHolder Create",player.getName());

		playerName.setText(player.getName());
		commanderName.setText(player.getCommander().getName());
		commanderImage.setImageURI(imagePath);
		commanderCast.setText(getString(R.string.text_view_cast)+player.getCommander().getCast());
		commanderDeath.setText(getString(R.string.text_view_death)+player.getCommander().getDeath());
		//TODO: gridview avec adapter dynamique avec setNumColumns
		return rootView;
	}

	public void onResume(){
		super.onResume();
		Log.i("PlaceHolder Resume", player.getName());
		damageListing();
	}

	public void damageListing(){
		commanderDamage = (ListView) rootView.findViewById(R.id.listViewCommanders);
		ArrayList<HashMap<String,String>> cellsList = new ArrayList<HashMap<String, String>>();
		HashMap<String,String> map;
		String[] keys = new String[] {LIST_VIEW_KEY_NAME, LIST_VIEW_KEY_DAMAGE};
		int[] views = new int[] {R.id.textViewCellCommanderName,R.id.textViewCellCommanderDamage};
		Player player;
		Iterator<Player> playerIterator = MainActivity.getPlayers().getPlayersList().iterator();
		while (playerIterator.hasNext()){
			MainActivity.getPlayers().getCommanderDamageTo();
			player = new Player(playerIterator.next());
			map = new HashMap<String, String>();
			map.put(LIST_VIEW_KEY_NAME, player.getName());
			map.put(LIST_VIEW_KEY_DAMAGE, String.valueOf(player.getLife()));
//                    map.put(ContactsDatabaseHandler.CHAMPS_ID, String.valueOf(contact.get_id()));
			cellsList.add(map);
		}
		SimpleAdapter affichage = new SimpleAdapter(getActivity().getBaseContext(),cellsList,R.layout.commander_damage_cell,keys,views);
		commanderDamage.setAdapter(affichage);
		commanderDamage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			@SuppressWarnings("unchecked")
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				HashMap<String, String> selection = (HashMap<String, String>) commanderDamage.getItemAtPosition(position);
				Toast.makeText(getActivity().getBaseContext(), selection.get(LIST_VIEW_KEY_NAME), Toast.LENGTH_LONG).show();
			}
		});
	}
}
