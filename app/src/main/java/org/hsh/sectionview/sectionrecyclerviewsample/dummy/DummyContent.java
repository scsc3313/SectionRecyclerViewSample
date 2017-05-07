package org.hsh.sectionview.sectionrecyclerviewsample.dummy;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;

import java.util.ArrayList;
import java.util.List;

public class DummyContent {

    public static final List<DummyItem> ITEMS = new ArrayList<>();

    public static final SimpleArrayMap<String, List<DummyItem>> ITEM_MAP = new SimpleArrayMap<>();

    public static final String[] HEADERS = {"A", "B", "C", "D"};

    private static final int COUNT = 25;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);

        List<DummyItem> dummyItemList = ITEM_MAP.get(item.header);
        if (dummyItemList == null) {
            dummyItemList = new ArrayList<>();
        }
        dummyItemList.add(item);
        ITEM_MAP.put(item.header, dummyItemList);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position), HEADERS[position % 4]);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;
        public final String header;

        public DummyItem(String id, String content, String details, String header) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.header = header;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
