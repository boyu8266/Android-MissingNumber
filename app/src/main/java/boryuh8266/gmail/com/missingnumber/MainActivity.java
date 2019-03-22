package boryuh8266.gmail.com.missingnumber;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import boryuh8266.gmail.com.missingnumber.adapter.HomeAdapter;
import boryuh8266.gmail.com.missingnumber.model.Item;

public class MainActivity extends AppCompatActivity implements HomeAdapter.ItemListener {

    private RecyclerView recyclerView;
    private ArrayList<Item> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        arrayList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            arrayList.add(new Item("Item 1", "#09A9FF"));
            arrayList.add(new Item("Item 2", "#3E51B1"));
            arrayList.add(new Item("Item 3", "#673BB7"));
            arrayList.add(new Item("Item 4", "#4BAA50"));
            arrayList.add(new Item("Item 5", "#F94336"));
            arrayList.add(new Item("Item 6", "#0A9B88"));
        }
        HomeAdapter adapter = new HomeAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
    }


    @Override
    public void onItemClick(Item item) {

    }
}
