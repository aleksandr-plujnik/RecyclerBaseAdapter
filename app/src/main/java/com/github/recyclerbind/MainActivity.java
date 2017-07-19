package com.github.recyclerbind;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.github.recyclerbind.base.BaseAdapter;
import com.github.recyclerbind.base.BaseHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaseHolder.OnHolderClickListener<BurgerModel>, View.OnClickListener, BaseAdapter.OnNeedMoreItemsListener {
    private static final String TAG = "MainActivity";
    private static final int DELAY_HEADER_SHOW = 1000;
    private static final int DELAY_HEADER_DISMISS = 3000;
    private static final int DELAY_FOOTER_SHOW = 4000;
    private static final int DELAY_FOOTER_DISMISS = 5000;

    //<editor-fold desc="Burgers Init">
    private List<BurgerModel> burgers = Arrays.asList(new BurgerModel("Burger King"),
            new BurgerModel("McDonald's"),
            new BurgerModel("Subway"),
            new BurgerModel("Pizza Hut"),
            new BurgerModel("Bahama Breeze"),
            new BurgerModel("Panda Express"),
            new BurgerModel("Sushiology"),
            new BurgerModel("Brio"),
            new BurgerModel("Olive Garden"),
            new BurgerModel("LongHorn"),
            new BurgerModel("Carrabbas"),
            new BurgerModel("Seito"),
            new BurgerModel("Fridays"),
            new BurgerModel("Cheese Cake Factory"),
            new BurgerModel("Amura"),
            new BurgerModel("Chipotle"),
            new BurgerModel("WaWa"),
            new BurgerModel("Pio Pio"),
            new BurgerModel("Panera Bread"),
            new BurgerModel("Mimis Cafe"),
            new BurgerModel("McDonald's"),
            new BurgerModel("Subway"),
            new BurgerModel("Pizza Hut"),
            new BurgerModel("Bahama Breeze"),
            new BurgerModel("Panda Express"),
            new BurgerModel("Sushiology"),
            new BurgerModel("Brio"),
            new BurgerModel("Olive Garden"),
            new BurgerModel("LongHorn"),
            new BurgerModel("Carrabbas"),
            new BurgerModel("Seito"),
            new BurgerModel("Fridays"),
            new BurgerModel("Cheese Cake Factory"),
            new BurgerModel("Amura"),
            new BurgerModel("Chipotle"),
            new BurgerModel("WaWa"),
            new BurgerModel("Pio Pio"),
            new BurgerModel("Panera Bread"),
            new BurgerModel("Mimis Cafe"));
    //</editor-fold>

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private int offset;
    private static final int LIMIT = 6;
    private List<BurgerModel> endlessList;
    private BurgerAdapter endlessAdapter;
    private SimpleAdapter simpleAdapter;
    private HeaderAdapter headerAdapter;
    private HeaderAdapter headerAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRecycler();

        findViewById(R.id.btSimple).setOnClickListener(this);
        findViewById(R.id.btHeader).setOnClickListener(this);
        findViewById(R.id.btFooter).setOnClickListener(this);
        findViewById(R.id.btEndless).setOnClickListener(this);
    }

    private void initRecycler() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onHolderViewClick(@NonNull View view, @Nullable BurgerModel burgerModel) {
        Log.d(TAG, "onHolderViewClick: " + view.getId() + " :: " + burgerModel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSimple:

                simpleAdapter = new SimpleAdapter(new ArrayList<>(burgers), this);
                recyclerView.setAdapter(simpleAdapter);

                int adapterSize = simpleAdapter.getItems().size();
                int originalSize = burgers.size();
                Log.d(TAG, "onClick: " + adapterSize + " == " + originalSize);



                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BurgerModel burgerModel = burgers.get(2);
                        simpleAdapter.removeItem(burgerModel);
                    }

                }, 3000);

                break;
            case R.id.btHeader:
                headerAdapter1 = new HeaderAdapter(new ArrayList<>(burgers), this);
                headerAdapter1.showHeader();
                recyclerView.setAdapter(headerAdapter1);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        headerAdapter1.clearItems();
                    }

                }, 3000);

//                new Handler(Looper.getMainLooper()).postDelayed(headerAdapter::hideHeader, 2000);

                break;
            case R.id.btFooter:

                List<BurgerModel> footerList = new ArrayList<>(burgers.subList(0, 3));
                final FooterAdapter footerAdapter = new FooterAdapter(footerList);
                footerAdapter.showFooter();
                recyclerView.setAdapter(footerAdapter);

//                new Handler(Looper.getMainLooper()).postDelayed(footerAdapter::hideFooter, 3000);

                break;
            case R.id.btEndless:
                offset = 0;
                int toIndex = offset + LIMIT;
                endlessList = new ArrayList<>(burgers.subList(offset, toIndex));
                offset = toIndex;

                endlessAdapter = new BurgerAdapter(endlessList, this);
                recyclerView.setAdapter(endlessAdapter);

                break;
        }
    }

    private void loadMore() {
        Log.d(TAG, "loadMore: " + offset + "; ");
        endlessAdapter.showFooter();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<BurgerModel> newItems = generateNewItems();
                if (newItems == null) {
                    endlessAdapter.noMoreItems();
                    endlessAdapter.hideFooter();
                    return;
                }

                endlessAdapter.addNewItems(newItems);
                endlessAdapter.hideFooter();
            }
        }, 2000);
    }

    @Nullable
    private ArrayList<BurgerModel> generateNewItems() {
        if (offset >= burgers.size()) {
            return null;
        }
        int toIndex = offset + LIMIT;
        if (toIndex > burgers.size()) {
            toIndex = burgers.size();
        }
        ArrayList<BurgerModel> newItems = new ArrayList<>(burgers.subList(offset, toIndex));
        offset = toIndex;
        return newItems;
    }

    @Override
    public void needMoreItems() {
        loadMore();
    }
}
