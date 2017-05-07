package org.hsh.sectionview.sectionrecyclerviewsample;

import android.support.v4.util.SimpleArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.hsh.sectionview.sectionrecyclerviewsample.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

public class SectionRecyclerViewAdapter<K, V extends List<DummyItem>> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 1;
    private static final int TYPE_COMMENT = 2;

    private final SimpleArrayMap<K, V> mValues;
    private List<Integer> mHeaderPositions;
    private int mCount;


    public SectionRecyclerViewAdapter(SimpleArrayMap<K, V> items) {
        mValues = items;
        mHeaderPositions = new ArrayList<>();
        mHeaderPositions.add(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;

        switch (viewType) {
            case TYPE_HEADER: {
                View headerView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.section_header, parent, false);
                viewHolder = new HeaderViewHolder(headerView);
                break;
            }
            case TYPE_COMMENT: {
                View commentView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.section_item, parent, false);
                viewHolder = new CommentViewHolder(commentView);
                break;
            }
            default: {
                break;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CommentViewHolder) {
            final CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
            final DummyItem item = getItem(position);
            commentViewHolder.mIdView.setText(item.id);
            commentViewHolder.mContentView.setText(item.content);
            commentViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(commentViewHolder.mView.getContext(),
                            "comment id : " + item.id, Toast.LENGTH_SHORT).show();
                }
            });
        } else if (holder instanceof HeaderViewHolder) {
            final HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            final K key = getKey(position);
            headerViewHolder.mTitleView.setText((String) key);
            headerViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(headerViewHolder.mView.getContext(),
                            "header id : " + key, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private DummyItem getItem(int position) {
        int index = mValues.indexOfKey(getKey(position));

        for (int i = 0; i < index; i++) {
            position -= mValues.valueAt(i).size();
        }

        position -= (index + 1);

        return mValues.valueAt(index).get(position);
    }

    @Override
    public int getItemViewType(int position) {
        int type;

        if (mHeaderPositions.contains(position)) {
            type = TYPE_HEADER;
        } else {
            type = TYPE_COMMENT;
        }

        return type;
    }

    @Override
    public int getItemCount() {
        if (mCount == 0) {
            for (int i = 0; i < mValues.size(); i++) {
                mCount += mValues.valueAt(i).size() + 1;
                mHeaderPositions.add(mCount);
            }
        }
        return mCount;
    }

    public K getKey(int position) {
        K key = null;
        int sumPosition = 0;
        for (int i = 0; i < mValues.size(); i++) {
            sumPosition += mValues.valueAt(i).size() + 1;
            if (position < sumPosition) {
                key = mValues.keyAt(i);
                break;
            }
        }
        return key;
    }

    private static class CommentViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;

        public CommentViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;

        public HeaderViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.title);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
