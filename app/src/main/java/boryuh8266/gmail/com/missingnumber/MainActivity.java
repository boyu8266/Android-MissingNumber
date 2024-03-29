package boryuh8266.gmail.com.missingnumber;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import boryuh8266.gmail.com.missingnumber.adapter.HomeAdapter;
import boryuh8266.gmail.com.missingnumber.model.AwesomeSuccessDialog;
import boryuh8266.gmail.com.missingnumber.model.CustomDialog;
import boryuh8266.gmail.com.missingnumber.model.HeinsInputDialog;
import boryuh8266.gmail.com.missingnumber.model.Item;
import boryuh8266.gmail.com.missingnumber.model.MissingNumber;
import boryuh8266.gmail.com.missingnumber.model.interfaces.Closure;
import boryuh8266.gmail.com.missingnumber.model.interfaces.OnInputDoubleListener;

public class MainActivity extends AppCompatActivity implements HomeAdapter.ItemListener {

    final int UPDATE_TIMER = 1;
    int[] qArray;
    LinkedList<String> colors;
    private RecyclerView recyclerView;
    private ArrayList<Item> arrayList;
    private MissingNumber mn;
    private AwesomeSuccessDialog successDialog, failDialog, warnDialog;
    private int num = 0;
    private String[] initColors = {"#0ebeff", "#47cf73", "#ae63e4", "#fcd000", "#76daff"};
    private TextView timeTV;
    private long initTime;
    private long startTime;
    private long pauseTime;
    private long endTime = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TIMER:
                    endTime = System.currentTimeMillis();
                    timeTV.setText(formatTime(endTime - initTime - pauseTime));
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private long totalTime;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();
        timeTV = (TextView) findViewById(R.id.mTimer);

        initTime = System.currentTimeMillis();
        setColors();
        setGame();
        failDialog = new CustomDialog(this).getFailDialog();
        warnDialog = new CustomDialog(this).getWarningDialog();
        successDialog = new CustomDialog(this).getSuccessDialog().setPositiveButtonClick(new Closure() {
            @Override
            public void exec() {
                setGame();
            }
        });
    }

    private void setColors() {
        colors = new LinkedList<>();
        for (int i = 0; i < initColors.length; i++) {
            colors.add(initColors[i]);
        }
    }

    private void setGame() {
        Collections.shuffle(colors);
        arrayList.clear();
        if (num == 0) {
            num = 5;
            mn = new MissingNumber(num);
        } else
            num += 5;

        mn.setNewMissingNumber(num);
        qArray = mn.getQArray();

        int len = qArray.length;
        for (int i = 0; i < len; i++) {
            if (qArray[i] == -1)
                arrayList.add(new Item("？", "#F94336"));
            else
                arrayList.add(new Item(String.valueOf(qArray[i]), colors.get(i % 5)));
        }

        HomeAdapter adapter = new HomeAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        startTime = System.currentTimeMillis();
        startTime();
    }

    private void startTime() {
        if (endTime > 0)
            pauseTime = pauseTime + System.currentTimeMillis() - endTime;
        if (timer != null)
            timer.cancel();
        timer = new Timer();
        timer.schedule(new mTimer(), 1000, 1000);
    }

    @Override
    public void onItemClick(Item item) {
        if (item.getText().equals("？")) {
            HeinsInputDialog dialog = new HeinsInputDialog(this);
            dialog.setPositiveButton(new OnInputDoubleListener() {
                @Override
                public boolean onInputDouble(AlertDialog dialog, Double value) {
                    checkAnswer((int) value.doubleValue());
                    return false;
                }
            });
            dialog.setTitle(R.string.dialog_title);
            dialog.setHint(R.string.dialog_hint);
            dialog.show();
        }
    }

    private void checkAnswer(int answer) {
        String str = getResources().getString(R.string.dialog_message) + ": " + answer;
        if (answer < 1 || answer > num) {
            warnDialog.setMessage(str);
            warnDialog.show();
        } else {
            if (mn.isRightAnswer(answer)) {
                timer.cancel();
                endTime = System.currentTimeMillis();
                totalTime = endTime - startTime;
                successDialog.setMessage(
                        str + "\n" +
                                getResources().getString(R.string.dialog_time) + ": " + formatMillisTime(totalTime)
                );
                successDialog.show();
            } else {
                failDialog.setMessage(str);
                failDialog.show();
            }
        }
    }

    private String formatTime(long totalTime) {
        long T = totalTime / 1000;
        long second = T % 60;
        long minute = T / 60;
        long hour = minute / 24;
        return (hour < 10 ? "0" + hour : hour) + " : " + (minute < 10 ? "0" + minute : minute) + " : " + (second < 10 ? "0" + second : second);
    }

    private String formatMillisTime(long t) {
        long T = t / 1000;
        long second = T % 60;
        long minute = T / 60;
        return (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second) + "." + (T < 10 ? "00" + T : (T < 100 ? "0" + T : T));
    }

    /*
        @Override
        protected void onPause() {
            super.onPause();
            if (timer != null)
                timer.cancel();
            endTime = System.currentTimeMillis();
        }

        @Override
        protected void onResume() {
            super.onResume();
            startTime();
        }
    */
    private class mTimer extends TimerTask {
        @Override
        public void run() {
            Message m = new Message();
            m.what = UPDATE_TIMER;
            handler.sendMessage(m);
        }
    }

}
