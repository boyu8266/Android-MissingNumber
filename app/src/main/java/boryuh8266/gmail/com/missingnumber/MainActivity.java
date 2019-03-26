package boryuh8266.gmail.com.missingnumber;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TimerTask;

import boryuh8266.gmail.com.missingnumber.adapter.HomeAdapter;
import boryuh8266.gmail.com.missingnumber.model.AwesomeSuccessDialog;
import boryuh8266.gmail.com.missingnumber.model.HeinsInputDialog;
import boryuh8266.gmail.com.missingnumber.model.Item;
import boryuh8266.gmail.com.missingnumber.model.MissingNumber;
import boryuh8266.gmail.com.missingnumber.model.interfaces.Closure;
import boryuh8266.gmail.com.missingnumber.model.interfaces.OnInputDoubleListener;

public class MainActivity extends AppCompatActivity implements HomeAdapter.ItemListener {

    int[] qArray;
    private RecyclerView recyclerView;
    private ArrayList<Item> arrayList;
    private MissingNumber mn;
    private AwesomeSuccessDialog successDialog, failDialog, warnDialog;
    private int num = 0;
    private String[] colors = {"#09A9FF", "#3E51B1", "#673BB7", "#4BAA50", "#0A9B88"};

    private TextView timeTV;
    private long startTime;
    private long endTime;
    private long totalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();
        timeTV = (TextView) findViewById(R.id.mTimer);

        setGame();
        initDailog();
    }

    private void setGame() {
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
                arrayList.add(new Item(String.valueOf(qArray[i]), colors[i % 5]));
        }
        //arrayList.add(new Item("？", "#F94336"));

        HomeAdapter adapter = new HomeAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        startTime = System.currentTimeMillis();
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
                endTime = System.currentTimeMillis();
                totalTime = totalTime + endTime - startTime;
                successDialog.setMessage(str + ", " + totalTime);
                successDialog.show();
            } else {
                failDialog.setMessage(str);
                failDialog.show();
            }
        }
    }

    private void initDailog() {
        successDialog = new AwesomeSuccessDialog(MainActivity.this)
                .setTitle(R.string.success)
                .setColoredCircle(R.color.dialogSuccessBackgroundColor)
                .setDialogIconAndColor(R.drawable.ic_success, R.color.white)
                .setCancelable(false)
                .setPositiveButtonText(getString(R.string.dialog_game_button))
                .setPositiveButtonbackgroundColor(R.color.dialogSuccessBackgroundColor)
                .setPositiveButtonTextColor(R.color.white)
                .setPositiveButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        setGame();
                    }
                });

        failDialog = new AwesomeSuccessDialog(MainActivity.this)
                .setTitle(R.string.fail)
                .setColoredCircle(R.color.dialogErrorBackgroundColor)
                .setDialogIconAndColor(R.drawable.ic_dialog_error, R.color.white)
                .setCancelable(false)
                .setNegativeButtonText(getString(R.string.fail_negative_button))
                .setNegativeButtonbackgroundColor(R.color.dialogErrorBackgroundColor)
                .setNegativeButtonTextColor(R.color.white)
                .setNegativeButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        //click
                    }
                });

        warnDialog = new AwesomeSuccessDialog(MainActivity.this)
                .setTitle(R.string.warn)
                .setColoredCircle(R.color.dialogWarningBackgroundColor)
                .setDialogIconAndColor(R.drawable.ic_dialog_warning, R.color.white)
                .setCancelable(false)
                .setNegativeButtonText(getString(R.string.fail_negative_button))
                .setNegativeButtonbackgroundColor(R.color.dialogWarningBackgroundColor)
                .setNegativeButtonTextColor(R.color.white)
                .setNegativeButtonClick(new Closure() {
                    @Override
                    public void exec() {
                        //click
                    }
                });
    }

    private class mTimer extends TimerTask {
        @Override
        public void run() {

        }
    }

}
