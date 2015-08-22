package com.frederic_leblanc.mtg_commander;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;


public class MainActivity extends Activity {

	private Context context = MainActivity.this;

	protected static final String URI_IMAGE_RESOURCE = "android.resource://com.frederic_leblanc.mtg_commander/drawable/";
	//com/frederic_leblanc/mtg_commander/drawable/
	private static HashMap<String, String> manaLogo = new HashMap<String, String>();

	public static Players players;

	private PlayersDataBaseHandler playersDataBaseHandler;
	private SQLiteDatabase handlerPlayers;

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link FragmentPagerAdapter} derivative, which will keep every
	 * loaded fragment in memory. If this becomes too memory intensive, it
	 * may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	PlayersPagerAdapter mPlayersPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	protected static ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initializeLogo();
		players = new Players();
		playersDataBaseHandler = new PlayersDataBaseHandler(context);
		handlerPlayers = playersDataBaseHandler.getWritableDatabase();
		chargerDB();

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mPlayersPagerAdapter = new PlayersPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mPlayersPagerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
			case R.id.action_settings: {
				return true;
			}
			case R.id.action_quitter: {
				activiteQuitter();
				return true;
			}
			case R.id.action_initialize: {
				playersDataBaseHandler.onUpgrade(handlerPlayers, 1, 1);
				chargerDB();
				return true;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	public void onResume(){
		super.onResume();
	}

	public void onDestroy(){
		super.onDestroy();
		handlerPlayers.close();
		playersDataBaseHandler.close();
	}

	private final class CancelOnClickListener implements DialogInterface.OnClickListener {


		@Override
		public void onClick(DialogInterface dialog,
		                    int which) {
		}

	}
	private final class OkOnClickListener implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog,
		                    int which) {
			fermerActivite();
		}

	}
	public void activiteQuitter() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.dialog_quitter_titre));
		builder.setMessage(getString(R.string.dialog_quitter_question));
		builder.setIcon(R.mipmap.icon_commander_launcher);
		builder.setPositiveButton(getString(R.string.dialog_quitter_reponse_oui),
				new OkOnClickListener());
		builder.setNegativeButton(getString(R.string.dialog_quitter_reponse_non),
				new CancelOnClickListener());
		builder.show();
	}
	public void fermerActivite() {
		MainActivity.this.finish();
	}

	public void chargerDB(){
		Cursor curseur = handlerPlayers.rawQuery("SELECT * FROM "+PlayersDataBaseHandler.TABLE_PLAYERS,null);
		Log.i("Console", String.valueOf(curseur.getCount()));
		if (curseur.getCount()>0) {
			curseur.moveToFirst();
			while (!curseur.isAfterLast()) {
				players.setPlayer(playersDataBaseHandler.getPlayer(handlerPlayers, String.valueOf(curseur.getInt(curseur.getColumnIndex(PlayersDataBaseHandler
						.COL_PLAYER_ID)))));
				curseur.moveToNext();
			}
		}
		curseur.close();
	}

	public void initializeLogo(){
		manaLogo.put("b",URI_IMAGE_RESOURCE+"b");
		manaLogo.put("w",URI_IMAGE_RESOURCE+"w");
		manaLogo.put("r",URI_IMAGE_RESOURCE+"r");
		manaLogo.put("g",URI_IMAGE_RESOURCE+"g");
		manaLogo.put("u",URI_IMAGE_RESOURCE+"u");
	}

	public void onClickLayoutPlayer(View v){
		final Dialog dialogCommander = new Dialog(context);
		dialogCommander.setContentView(R.layout.dialog_commander);
		dialogCommander.setCancelable(true);
		dialogCommander.setCanceledOnTouchOutside(true);
		ImageButton card = (ImageButton) dialogCommander.findViewById(R.id.imageButtonCard);
		card.setImageURI(Uri.parse(URI_IMAGE_RESOURCE + players.getPlayer(mViewPager.getCurrentItem()).getCommander().getImage()));
		card.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogCommander.cancel();
			}
		});
		dialogCommander.show();
	}

	public void onClickButtonCastDeath(View v){
		switch (v.getId()) {
			case R.id.buttonCastPlus: {
				TextView cast = (TextView) getVisibleChild().findViewById(R.id.textViewCast);
				players.getPlayer(mViewPager.getCurrentItem()).getCommander().setCast(players.getPlayer(mViewPager.getCurrentItem()).getCommander().getCast
						() + 1);
				cast.setText(getString(R.string.text_view_cast) + players.getPlayer(mViewPager.getCurrentItem()).getCommander().getCast());
				break;
			}
			case R.id.buttonCastMinus: {
				TextView cast = (TextView) getVisibleChild().findViewById(R.id.textViewCast);
				players.getPlayer(mViewPager.getCurrentItem()).getCommander().setCast(players.getPlayer(mViewPager.getCurrentItem()).getCommander().getCast
						() - 1);
				cast.setText(getString(R.string.text_view_cast) + players.getPlayer(mViewPager.getCurrentItem()).getCommander().getCast());
				break;
			}
			case R.id.buttonDeathPlus: {
				TextView death = (TextView) getVisibleChild().findViewById(R.id.textViewDeath);
				players.getPlayer(mViewPager.getCurrentItem()).getCommander().setDeath(players.getPlayer(mViewPager.getCurrentItem()).getCommander()
						.getDeath() + 1);
				death.setText(getString(R.string.text_view_death) + players.getPlayer(mViewPager.getCurrentItem()).getCommander().getDeath());
				break;
			}
			case R.id.buttonDeathMinus: {
				TextView death = (TextView) getVisibleChild().findViewById(R.id.textViewDeath);
				players.getPlayer(mViewPager.getCurrentItem()).getCommander().setDeath(players.getPlayer(mViewPager.getCurrentItem()).getCommander()
						.getDeath() - 1);
				death.setText(getString(R.string.text_view_death) + players.getPlayer(mViewPager.getCurrentItem()).getCommander().getDeath());
				break;
			}
		}
	}

	public static Players getPlayers() {
		return players;
	}

	public View getVisibleChild(){
		for(int i = 0;i <= mViewPager.getChildCount(); i++){
			if (players.getPlayer(mViewPager.getCurrentItem()).getName().equalsIgnoreCase(((TextView) mViewPager.getChildAt(i).findViewById(R.id
					.textViewPagerPlayerName)).getText().toString())) {
				Log.i("Visible Child", players.getPlayer(mViewPager.getCurrentItem()).getName() + " : " + i);
				return mViewPager.getChildAt(i);
			}
		}
		return mViewPager.getChildAt(0);
	}

}
