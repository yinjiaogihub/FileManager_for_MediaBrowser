package com.example.test2969mediabrowser;

import java.io.IOException;
import java.util.List;


import android.content.Context;
import android.database.DataSetObserver;
import android.media.ExifInterface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class FixedGridView extends GridView {

	public FixedGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public FixedGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public FixedGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction()==MotionEvent.ACTION_MOVE) return true;

		return super.dispatchTouchEvent(ev);
	}
	
	
	
	/**
	 * 此 GridView 一整页 最多只能 容纳 12 个view
	 * @author lenovo
	 *
	 */

}
