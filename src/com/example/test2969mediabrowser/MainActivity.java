package com.example.test2969mediabrowser;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnFocusChangeListener, OnClickListener {

	private static final String TAG = "MainActivity";
	public boolean DEBUGER = true;
	private View layout_main_tittle_container;
	private View layout_tittles_five;
	private TextView tv_tittle_video;
	private TextView tv_tittle_music;
	private TextView tv_tittle_picture;
	private TextView tv_tittle_bule_ray;
	private View tv_tittle_all;
	private View layout_tittles_single;
	private TextView tv_tittle_single;
	
	private ViewPager viewpager;
	private ContainerPagerAdapter mPagerAdapter;
	private View layout_main_content_contain;
	private List<View> allPageViews;
	
	
	private boolean isFirstIn = true;
	private UnderlinePageIndicator pageview_indicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
	}

	private void initViews() {
		//======================layout_main_tittles.xml===================================
		//joe annotation : tittles layout views 
		layout_main_tittle_container = findViewById(R.id.layout_main_tittle_container);
		
		layout_tittles_five = findViewById(R.id.layout_tittles_five);
		tv_tittle_video = (TextView)findViewById(R.id.tv_tittle_video);
		tv_tittle_music = (TextView)findViewById(R.id.tv_tittle_music);
		tv_tittle_picture = (TextView)findViewById(R.id.tv_tittle_picture);
		tv_tittle_bule_ray = (TextView)findViewById(R.id.tv_tittle_bule_ray);
		tv_tittle_all = findViewById(R.id.tv_tittle_all);
		
		layout_tittles_single = findViewById(R.id.layout_tittles_single);
		tv_tittle_single = (TextView)findViewById(R.id.tv_tittle_single);
		
		List<View> allTittlesViews = new ArrayList<View>(); 
		allTittlesViews.add(layout_main_tittle_container);
		allTittlesViews.add(tv_tittle_video);
		allTittlesViews.add(tv_tittle_music);
		allTittlesViews.add(tv_tittle_picture);
		allTittlesViews.add(tv_tittle_bule_ray);
		allTittlesViews.add(tv_tittle_all);
		
		
		//========================layout_main_contain.xml==========================================
		layout_main_content_contain = findViewById(R.id.layout_main_content_contain);
		allTittlesViews.add(layout_main_content_contain);
		
		//container viewpager 
		viewpager = (ViewPager)findViewById(R.id.viewpager_container);
		pageview_indicator = (UnderlinePageIndicator)findViewById(R.id.pageview_indicator);
		
		allPageViews = updateData();
		mPagerAdapter = new ContainerPagerAdapter (allPageViews);
		viewpager.setAdapter(mPagerAdapter);
		
		pageview_indicator.setViewPager(viewpager);//bind the indicator
		
		
		
		
		
		
		
		registerFocussWithTittlesViews(allTittlesViews);
	}
	
	
	private void registerFocussWithTittlesViews(List<View> allTittleViews) {
		//joe todo : ע�� focus �ļ����¼�����tittle view focus ��ʱ��Ҫ������Եķ�ӳ
		for(View view : allTittleViews){
			view.setOnFocusChangeListener(this);
			view.setOnClickListener(this);
		}
	}
	

	@Override
	public void onFocusChange(View view, boolean hasFocus) {
		if(DEBUGER) {
			Log.d(TAG,"[joe] ======== onFocusChange  view = "+ view + "  hasFocus = "+hasFocus);
			Log.d(TAG,"[joe] onFocusChange  view.isSelected() = "+ view.isSelected());
			Log.d(TAG,"[joe] onFocusChange view.isFocused() = "+ view.isFocused());
		}
		
		int viewId = view.getId();
		if(viewId == R.id.tv_tittle_video || viewId == R.id.tv_tittle_music 
				|| viewId == R.id.tv_tittle_picture ||viewId == R.id.tv_tittle_all 
				|| viewId == R.id.tv_tittle_bule_ray){
			if(hasFocus) filterPagerDatasByTittle(view);
			changeTittleTextStyle((TextView)view,hasFocus);
		}else if(view.getId() == R.id.layout_main_tittle_container
				|| view.getId() == R.id.layout_main_content_contain){
			//layout_main_tittle ʧȥ�����ʱ�� tittle bar �ϵ� textviews ��Ҫ�仯��
			changeTittlesBar(view, hasFocus);
		}
	}

	
	/**
	 * for testing
	 */
	@Override
	public void onClick(View view) {
		int viewId = view.getId();
//		Log.d(TAG,"[joe] onClick  view.isSelected() = "+ view.isSelected());
//		Log.d(TAG,"[joe] onClick view.isFocused() = "+ view.isFocused());
		boolean hasFocus = view.isFocused();//for testing
		if(viewId == R.id.tv_tittle_video || viewId == R.id.tv_tittle_music 
				|| viewId == R.id.tv_tittle_picture ||viewId == R.id.tv_tittle_all 
				|| viewId == R.id.tv_tittle_bule_ray){
			if(hasFocus) filterPagerDatasByTittle(view);
			changeTittleTextStyle((TextView)view,hasFocus);
		}
		
		if(view.getId() == R.id.layout_main_tittle_container
				|| view.getId() == R.id.layout_main_content_contain){
			//layout_main_tittle ʧȥ�����ʱ�� tittle bar �ϵ� textviews ��Ҫ�仯��
			changeTittlesBar(view, hasFocus);
		}
	}

	
	/**
	 * 
	 * @param view  R.id.layout_main_tittle ���� focus �仯��ʱ��
	 * @param hasFocus true : ��ʾ layout_tittles_five
	 * 			false : ��ʾ layout_tittles_single
	 */
	private void changeTittlesBar(View view, boolean hasFocus) {
		if(hasFocus && layout_tittles_five != null && layout_tittles_single != null ){
			if(layout_main_tittle_container != null && view.getId() == R.id.layout_main_tittle_container ){
				layout_tittles_five.setVisibility(View.VISIBLE);
				layout_tittles_single.setVisibility(View.GONE);
			}else if(layout_main_content_contain != null && view.getId() == R.id.layout_main_content_contain){
					layout_tittles_five.setVisibility(View.GONE);
					layout_tittles_single.setVisibility(View.VISIBLE);
			}
		}
	}
	
	

	/**
	 *  �ı� text��style
	 * @param view
	 * @param hasFocus 
	 */
	private void changeTittleTextStyle(TextView textView, boolean hasFocus) {
		//joe todo :��change the textview style
		if(hasFocus){
			//set textview style
			textView.setTextAppearance(getApplication(),R.style.tittle_textview_selected);
		}else{
			textView.setTextAppearance(getApplication(),R.style.tittle_textview_unselected);
		}
		
	}

	/**
	 * ͨ�� ָ���� tittle ȥ���˲���ȡ viewpager ��������
	 * ͬʱˢ�����ҳ��
	 * @param view
	 */
	private void filterPagerDatasByTittle(View view) {
		// TODO Auto-generated method stub
		
		if(DEBUGER) Log.d(TAG,"[joe] filterPagerDatasByTittle .... ");
		
	}
	
	//layout_view_pager.xml
	int[] includeIdArray = { R.id.include_item_1, R.id.include_item_2, R.id.include_item_3, R.id.include_item_4,
			R.id.include_item_5, R.id.include_item_6, R.id.include_item_7, R.id.include_item_8,
			R.id.include_item_9, R.id.include_item_10, R.id.include_item_11, R.id.include_item_12};

	public static int pageMostFilesCount = 12;
	private List<View> updateData() {
		// TODO  get all the pages data 
		int totalFilesCount = 38;//for test
		
		//�Ƿ��� ��ҳ��
		boolean isZero = totalFilesCount % pageMostFilesCount==0 ;
		int lastPageFilesCount = totalFilesCount % pageMostFilesCount;
		int totalPageCount = totalFilesCount / pageMostFilesCount + ( isZero ? 0:1 );
		
		if(DEBUGER) Log.d(TAG, "[joe] updateData totalFilesCount = "+totalFilesCount + "  totalPageCount = "+totalPageCount);
		
		//�õ� LayoutInflater
		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
		
		ArrayList<View> allPageLayouts = new ArrayList<View>(); 
		//ÿһ��ҳ�涼��һ�� FixedGridView
		
		View layout_view_pager ;
		
		if(isFirstIn){
			//create every page content
			for(int i=0;i<totalPageCount-1;i++){
				//�������һҳ֮ǰ������
				layout_view_pager = inflater.inflate(R.layout.layout_view_pager, null);
//				View ll_pageview_items_bottom_container = layout_view_pager.findViewById(R.id.ll_pageview_items_bottom_container);
//				View ll_pageview_items_top_container = layout_view_pager.findViewById(R.id.ll_pageview_items_top_container);
				for(int includeId : includeIdArray){
					View includeLayout = layout_view_pager.findViewById(includeId);
					includeLayout.setVisibility(View.VISIBLE);
					ImageView fileImg = (ImageView) includeLayout.findViewById(R.id.iv_file_info_item);
					TextView fileName = (TextView) includeLayout.findViewById(R.id.tv_file_info_item_name);
					fileImg.setImageResource(R.drawable.file_icon_folder);
					fileName.setText("FileName");
				}
				allPageLayouts.add(layout_view_pager);
			}
			
			if(0 != lastPageFilesCount){
				//�������һҳ������
				layout_view_pager = inflater.inflate(R.layout.layout_view_pager, null);
				for(int i=0;i<lastPageFilesCount;i++){
					View includeLayout = layout_view_pager.findViewById(includeIdArray[i]);
					includeLayout.setVisibility(View.VISIBLE);
					ImageView fileImg = (ImageView) includeLayout.findViewById(R.id.iv_file_info_item);
					TextView fileName = (TextView) includeLayout.findViewById(R.id.tv_file_info_item_name);
					fileImg.setImageResource(R.drawable.file_icon_folder);
					fileName.setText("FileName");
				}
				allPageLayouts.add(layout_view_pager);
			}
			
		}
		
		return allPageLayouts;
	}

	

	/**
	 *  viewpager ��adapter
	 * @author lenovo
	 *
	 */
	private class ContainerPagerAdapter extends PagerAdapter{
		//���� page �� view �ļ���
		private List<View> mListViews;

		public ContainerPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		private void setList(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mListViews.get(position), 0);
			return mListViews.get(position);
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		// viewpageradapter.notifyDataSetChanged()�����õĹؼ�
		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
	}

}
