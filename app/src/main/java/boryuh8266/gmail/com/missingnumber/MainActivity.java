package boryuh8266.gmail.com.missingnumber;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import boryuh8266.gmail.com.missingnumber.adapter.HomeAdapter;
import boryuh8266.gmail.com.missingnumber.model.HeinsInputDialog;
import boryuh8266.gmail.com.missingnumber.model.Item;
import boryuh8266.gmail.com.missingnumber.model.MissingNumber;
import boryuh8266.gmail.com.missingnumber.model.interfaces.OnInputDoubleListener;

public class MainActivity extends AppCompatActivity implements HomeAdapter.ItemListener {

    private RecyclerView recyclerView;
    private ArrayList<Item> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();

        int num = 5;

        MissingNumber mn = new MissingNumber(num);
        int[] qArray = mn.getQArray();
        Log.e("mMsg", String.valueOf(mn.getMissingNumber()));

        String[] colors = {"#09A9FF", "#3E51B1", "#673BB7", "#4BAA50", "#0A9B88"};

        int len = qArray.length;
        for (int i = 0; i < len; i++) {
            arrayList.add(new Item(String.valueOf(qArray[i]), colors[i % 5]));
        }
        arrayList.add(new Item("？", "#F94336"));

        HomeAdapter adapter = new HomeAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }


    @Override
    public void onItemClick(Item item) {
        if(item.getText().equals("？")) {
            HeinsInputDialog dialog = new HeinsInputDialog(this);
            dialog.setPositiveButton(new OnInputDoubleListener() {
                @Override
                public boolean onInputDouble(AlertDialog dialog, Double value) {
                    Log.d("Debug", String.valueOf(value));
                    return false;//return if consume event
                }
            });
            dialog.setTitle("Missing number");
            dialog.setHint("The Answaer is ..");
            dialog.show();
        }
    }
}
