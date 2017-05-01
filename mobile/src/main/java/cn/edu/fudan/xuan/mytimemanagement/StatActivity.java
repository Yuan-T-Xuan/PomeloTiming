package cn.edu.fudan.xuan.mytimemanagement;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // collect data from DB
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().getPath() + "/my_db.db", null);
        // for plot 1
        Date today = new Date();
        long todayMilliseconds = today.getTime();
        Date oneDayBfr, twoDayBfr, threeDayBfr, fourDayBfr, fiveDayBfr, sixDayBfr;
        oneDayBfr = new Date(todayMilliseconds - 86400000);         // 86400000 is #milliseconds in one day
        twoDayBfr = new Date(todayMilliseconds - 86400000 * 2);
        threeDayBfr = new Date(todayMilliseconds - 86400000 * 3);
        fourDayBfr = new Date(todayMilliseconds - 86400000 * 4);
        fiveDayBfr = new Date(todayMilliseconds - 86400000 * 5);
        sixDayBfr = new Date(todayMilliseconds - 86400000 * 6);
        Date[] sevenDays = {sixDayBfr, fiveDayBfr, fourDayBfr, threeDayBfr, twoDayBfr, oneDayBfr, today};
        Number[] seriesNumbers = new Number[7];
        String[] labels = new String[7];
        String f = "SELECT COUNT( * ) " +
                "FROM records " +
                "WHERE YEAR = %d " +
                "AND MONTH = %d " +
                "AND DATE = %d";
        for(int i = 0; i < 7; i++) {
            String query = String.format(f, sevenDays[i].getYear(), sevenDays[i].getMonth(), sevenDays[i].getDate());
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            seriesNumbers[i] = cursor.getInt(0);
            cursor.close();
            //String label = String.format("%d.%d.%d", sevenDays[i].getYear(), sevenDays[i].getMonth(), sevenDays[i].getDate());
            String label = sevenDays[i].toString().substring(4, 10);
            Log.d("DateMNG", sevenDays[i].toString());
            labels[i] = label;
        }

        f = "SELECT COUNT(*) FROM records WHERE hour = %d OR hour = %d";
        ArrayList<Integer> Y2 = new ArrayList<>();
        ArrayList<String> Labels2 = new ArrayList<>();
        for(int i = 0; i < 12; i++) {
            Cursor cursor = db.rawQuery(String.format(f, 2*i, 2*i+1), null);
            cursor.moveToFirst();
            Y2.add(cursor.getInt(0));
            cursor.close();
            Labels2.add(String.format("%d, %d", 2*i, 2*i+1));
        }

        setContentView(R.layout.simple_xy_plot_example);
        plot1(seriesNumbers, labels);
        plot2(Y2, Labels2);
    }

    public void plot2(List<Integer> Y2, List<String> Labels2) {
        MTMPlotView plot = (MTMPlotView) findViewById(R.id.plot2);
        plot.setColor(Color.rgb(47, 165, 200));
        plot.setTitle("At Different Time Everyday");
        plot.setData(Y2, Labels2);
    }

    public void plot1(Number[] seriesNumbers, String[] labels) {
        ArrayList<Integer> Y = new ArrayList<>();
        for (Number seriesNumber : seriesNumbers) Y.add(seriesNumber.intValue());
        ArrayList<String> Labels = new ArrayList<>();
        for (String label : labels) Labels.add(label/*.substring(label.indexOf('.')+1, label.length())*/);
        MTMPlotView plot = (MTMPlotView) findViewById(R.id.plot);
        plot.setColor(Color.rgb(47, 165, 200));
        plot.setTitle("Working Periods in Recent 7 Days");
        plot.setData(Y, Labels);
    }
}
