package com.fisher.utils.app;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Fisher on 5/9/2016 at 4:28
 */
public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

	private SparseArray<View> mViews;
	private View mConvertView;

	private BaseRecyclerViewHolder ( View itemView, ViewGroup parent) {
		super(itemView);
		mConvertView = itemView;
		mViews = new SparseArray<>();
	}


	public static BaseRecyclerViewHolder getItemView ( ViewGroup parent, int layoutId) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
		return new BaseRecyclerViewHolder(itemView, parent);
	}

	public View getView(){
		return  mConvertView;
	}
	public <T extends View> T get ( int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	public void setText( int viewId, CharSequence msg) {
		TextView textView = get(viewId);
		textView.setText(msg);
	}

	public void setImageResource(int viewId, int resId) {
		ImageView imageView = get(viewId);
		imageView.setImageResource(resId);
	}

}