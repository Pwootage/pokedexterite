package com.pwootage.pokedex.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Christopher on 10/27/13.
 */
public class QueryHelper {
    private static final String POKE_QUERY = "select * from pokemon_species_names where lower(name) like lower(?)";

    public static Cursor buildExecutePokeNameQuery(SQLiteDatabase db, String query) {
        return db.rawQuery(POKE_QUERY, new String[] {"%" + query + "%"});
    }
}
