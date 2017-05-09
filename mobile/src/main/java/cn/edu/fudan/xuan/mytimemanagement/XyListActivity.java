package cn.edu.fudan.xuan.mytimemanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XyListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xy_list);

        final ListView listview = (ListView) findViewById(R.id.mylistview);
        final ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(getFilesDir().getPath() + "/my_db.db", null, SQLiteDatabase.OPEN_READONLY);
        String sql = "SELECT year, month, date, hour, minute FROM records";
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        Log.d("DBDBDBHHH", Integer.toString(cursor.getCount()));
        while(!cursor.isAfterLast()) {
            int year, month, date, hour, minute;
            year = cursor.getInt(0)+1900;
            month = cursor.getInt(1);
            date = cursor.getInt(2);
            hour = cursor.getInt(3);
            minute = cursor.getInt(4);
            String oneline = String.format("%4d/%2d/%2d %2d:%2d", year, month, date, hour, minute);
            list.add(oneline);
            cursor.moveToNext();
        }
        cursor.close();

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener((parent, view, position, id) -> {
            String item = (String) parent.getItemAtPosition(position);
            // e.g. 2017/ 4/ 9 12:37
            item = item.replace('/', ' ').replace(':', ' ').replace("  ", " ");
            String ts[] = item.split(" ");
            String q = "SELECT lat, lon FROM records WHERE";
            q += " year = " + (Integer.parseInt(ts[0])-1900);
            q += " AND month = " + ts[1];
            q += " AND date = " + ts[2];
            q += " AND hour = " + ts[3];
            q += " AND minute = " + ts[4];
            Cursor cur = db.rawQuery(q, null);
            cur.moveToFirst();
            double lat = cur.getDouble(0);
            double lon = cur.getDouble(1);
            cur.close();
            if(lat == 0 || lon == 0) {
                new AlertDialog.Builder(this).setTitle("NO Location Info")
                        .setMessage("User did not save location info during this record.")
                        .setNegativeButton("Close", (dialog, which) -> {
                            //do nothing - it will close on its own
                        })
                        .show();
            } else {
                Log.d("LOC INFO", " " + lat + " " + lon);
                Intent intent = new Intent(this, XyMapActivity.class);
                intent.putExtra("cn.edu.fudan.xuan.LAT", lat);
                intent.putExtra("cn.edu.fudan.xuan.LON", lon);
                startActivity(intent);
            }
        });
    }

}


