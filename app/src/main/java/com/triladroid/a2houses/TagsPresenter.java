package com.triladroid.a2houses;

import android.annotation.SuppressLint;
import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import java.util.*;
import java.util.regex.*;

public class TagsPresenter {

    private TagsViewInterface view;
    private RetrofitConfig retrofitConfig = new RetrofitConfig();

    public TagsPresenter(TagsViewInterface view) {
        this.view = view;
    }

    @SuppressLint("CheckResult") public void getTags(String tag) {
        getListObservable(tag)
                .subscribe(allTags -> view.showTags(allTags), error -> Log.d("tags", error.getMessage()));
    }

    @SuppressLint("CheckResult") public void getLucky30Tags(String tag) {
        getListObservable(tag)
                .map(this::get30)
                .subscribe(tags -> view.copyTagsToClipboard(tags), error -> Log.d("tags", error.getMessage()));
    }

    private Observable<List<String>> getListObservable(String tag) {
        return retrofitConfig.getInstaService()
                .getTagsPage(tag)
                .subscribeOn(Schedulers.io())
                .map(response -> extractAllTags(response.string()))
                .observeOn(AndroidSchedulers.mainThread());
    }

    List<String> extractAllTags(String htmlString) {
        ArrayList<String> tags = new ArrayList<>();
        Pattern pattern = Pattern.compile("#\\S+?(?=\\s|#|\")");
        Matcher matcher = pattern.matcher(htmlString);
        Map<String, Integer> tagCounts = new HashMap<>();

        while (matcher.find()) {
            tags.add(matcher.group());
        }
        for (String tag : tags) {
            if (tagCounts.containsKey(tag)) {
                int count = tagCounts.get(tag) + 1;
                tagCounts.put(tag, count);
            } else {
                tagCounts.put(tag, 1);
            }
        }
        ArrayList<Map.Entry<String, Integer>> sortedList = new ArrayList<>(tagCounts.entrySet());
        Collections.sort(sortedList, (entry1, entry2) -> entry2.getValue()
                .compareTo(entry1.getValue()));

        ArrayList<String> allTags = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sortedList) {
            allTags.add(entry.getKey());
        }

        return allTags;
    }

    List<String> get30(List<String> list) {
        if (list.size() <= 30) {
            return list;
        } else {
            ArrayList<String> tags30 = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                tags30.add(list.get(i));
            }
            return tags30;
        }
    }
}
