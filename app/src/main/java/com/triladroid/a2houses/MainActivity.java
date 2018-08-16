package com.triladroid.a2houses;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;

public class MainActivity extends AppCompatActivity implements TagsViewInterface {

    TagsPresenter presenter;
    private TagsAdapter adapter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new TagsAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        presenter = new TagsPresenter(this);
        findViewById(R.id.go).setOnClickListener(view -> presenter.getTags(getInputTag()));
        findViewById(R.id.lucky).setOnClickListener(view -> presenter.getLucky30Tags(getInputTag()));
    }

    private String getInputTag() {
        return ((EditText) findViewById(R.id.tag)).getText().toString();
    }

    @Override public void showTags(List<String> list) {
        adapter.setData(list);
        String tagsString = String.join(" ", list);
        ((EditText)findViewById(R.id.tags)).setText(tagsString);
    }

    @Override public void copyTagsToClipboard(List<String> list) {
        String tagsString = String.join(" ", list);
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("tags", tagsString);
        clipboard.setPrimaryClip(clip);
    }
}
