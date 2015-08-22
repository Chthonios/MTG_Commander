package com.frederic_leblanc.mtg_commander;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Frédéric Leblanc on 2015-08-22.
 */
public class PlayersDataBaseHandler extends SQLiteOpenHelper {

	private static final int VERSION_BD = 1;
	private static final String NOM_BD = "handlePlayers";
	public static final String TABLE_PLAYERS = "Players";
	public static final String TABLE_COMMANDERS = "Commanders";

	// for players
	public static final String COL_PLAYER_ID = "PLAYER_ID";
	public static final String COL_NAME = "name";
	public static final String COL_PLAYED = "played";
	// for commanders
	public static final String COL_COMMANDER_ID = "COMMANDER_ID";
	public static final String COL_COMMANDER = "commander";
	public static final String COL_IMAGE = "image";
	public static final String COL_COST = "cost";

	public PlayersDataBaseHandler(Context context) {
		super(context,NOM_BD,null,VERSION_BD);
	}

	/**
	 * Called when the database is created for the first time. This is where the
	 * creation of tables and the initial population of the tables should happen.
	 *
	 * @param db The database.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_PLAYERS+" ("+COL_PLAYER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
				""+COL_NAME+" TEXT NOT NULL,"+COL_COMMANDER+" TEXT NOT NULL,"+COL_PLAYED+" INTEGER)";
		db.execSQL(sql);
		sql = "CREATE TABLE IF NOT EXISTS "+TABLE_COMMANDERS+" ("+COL_COMMANDER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
				""+COL_COMMANDER+" TEXT NOT NULL," +
				""+COL_COST+" TEXT NOT NULL,"+COL_IMAGE+" TEXT)";
		db.execSQL(sql);
		initTables(db);
	}

	/**
	 * Called when the database needs to be upgraded. The implementation
	 * should use this method to drop tables, add tables, or do anything else it
	 * needs to upgrade to the new schema version.
	 * <p/>
	 * <p>
	 * The SQLite ALTER TABLE documentation can be found
	 * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
	 * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
	 * you can use ALTER TABLE to rename the old table, then create the new table and then
	 * populate the new table with the contents of the old table.
	 * </p><p>
	 * This method executes within a transaction.  If an exception is thrown, all changes
	 * will automatically be rolled back.
	 * </p>
	 *
	 * @param db         The database.
	 * @param oldVersion The old database version.
	 * @param newVersion The new database version.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_PLAYERS);
		db.execSQL("DROP TABLE IF EXISTS "+TABLE_COMMANDERS);
		onCreate(db);
	}

	public void addNewPlayer(SQLiteDatabase sql,Player player) {
		ContentValues values = new ContentValues();
		values.put(COL_NAME,player.getName());
		values.put(COL_COMMANDER, player.getCommander().getName());
		values.put(COL_PLAYED, 1);
		sql.insert(TABLE_PLAYERS, COL_PLAYER_ID, values);
	}

	public void addNewCommander(SQLiteDatabase sql,Commander commander){
		ContentValues values = new ContentValues();
		values.put(COL_COMMANDER, commander.getName());
		values.put(COL_COST,commander.getCost());
		values.put(COL_IMAGE, commander.getImage());
		sql.insert(TABLE_COMMANDERS, COL_COMMANDER_ID, values);
	}

	public  void updatePlayer(SQLiteDatabase sql, Player player,int played,String id) {
		ContentValues values = new ContentValues();
		values.put(COL_NAME,player.getName());
		values.put(COL_COMMANDER, player.getCommander().getName());
		values.put(COL_PLAYED, played);
		sql.update(TABLE_PLAYERS, values, COL_PLAYER_ID + " = ?", new String[]{id});
	}

	public  void updateCommander(SQLiteDatabase sql, Commander commander,String id) {
		ContentValues values = new ContentValues();
		values.put(COL_COMMANDER, commander.getName());
		values.put(COL_COST,commander.getCost());
		values.put(COL_IMAGE, commander.getImage());
		sql.update(TABLE_COMMANDERS, values, COL_COMMANDER_ID + " = ?", new String[]{id});
	}

	public void deletePlayer(SQLiteDatabase sql, String id) {
		sql.delete(TABLE_PLAYERS, COL_PLAYER_ID + " = ?", new String[]{id});
	}

	public void deleteCommander(SQLiteDatabase sql, String id) {
		sql.delete(TABLE_COMMANDERS, COL_COMMANDER_ID + " = ?", new String[]{id});
	}

	public Player getPlayer(SQLiteDatabase sql,String id){
		Player player = null;
		Commander commander = null;
		Cursor cursorPlayer = sql.rawQuery("SELECT * FROM "+TABLE_PLAYERS+" WHERE "+COL_PLAYER_ID+" = ?", new String[]{id});
		if (cursorPlayer.getCount() > 0) {
			cursorPlayer.moveToFirst();

			commander = getCommander(sql,cursorPlayer.getString(cursorPlayer.getColumnIndex(COL_COMMANDER)));
			player = new Player(cursorPlayer.getString(cursorPlayer.getColumnIndex
					(COL_NAME)),commander,0,0);
		}
		cursorPlayer.close();
		return player;
	}

	public Commander getCommander(SQLiteDatabase sql, String commanderName){
		Commander commander = null;
		Cursor cursorCommander = sql.rawQuery("SELECT * FROM "+TABLE_COMMANDERS+" WHERE "+COL_COMMANDER+" = ?", new String[]{commanderName});
		if (cursorCommander.getCount() > 0) {
			cursorCommander.moveToFirst();
			commander = new Commander(cursorCommander.getString(cursorCommander.getColumnIndex(COL_COMMANDER)),
					cursorCommander.getString(cursorCommander.getColumnIndex(COL_IMAGE)),cursorCommander.getString(cursorCommander.getColumnIndex
					(COL_COST)),0,0);
		}
		cursorCommander.close();
		return commander;
	}

	public void initTables(SQLiteDatabase sql) {
		Player player;
		player = new Player("frederic",new Commander("Marath, Will of the Wild","marath","rgw",0,0),0,0);
		addNewPlayer(sql, player);
		addNewCommander(sql, player.getCommander());
		player = new Player("guillaume",new Commander("Lazav, Dimir Mastermind","lazav","uubb",0,0),0,0);
		addNewPlayer(sql, player);
		addNewCommander(sql, player.getCommander());
		player = new Player("jean-françois",new Commander("Derevi, Empyrial Tactician","derevi","gwu",0,0),0,0);
		addNewPlayer(sql, player);
		addNewCommander(sql, player.getCommander());
		player = new Player("benoît",new Commander("Kaervek the Merciless","kaervek","5br",0,0),0,0);
		addNewPlayer(sql, player);
		addNewCommander(sql, player.getCommander());
	}
}
