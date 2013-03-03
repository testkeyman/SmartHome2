package com.eastedge.smarthome.dragview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.text.style.LineHeightSpan.WithDensity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eastedge.smarthome.R;
import com.eastedge.smarthome.adapters.DataAdapter;
import com.eastedge.smarthome.util.DensityUtil;

/**
 * @author baojianming
 *
 */
public class DragGridView extends GridView implements OnClickListener,
		OnTouchListener {
	/**拖拽开始的位置*/
	public int dragPosition;
	/**拖拽结束的位置*/
	public int dropPosition;
	/**长按发生的位置*/
	public int longPosition=3;
	ViewGroup fromView;
	int stopCount = 0;
	/**拖动页数 0表示在当前页拖动，跨页+1*/
	int moveNum;
	/**滑动到哪一页监听*/
	public G_PageListener pageListener;
	/**跨页交换*/
	public G_ItemChangeListener itemListener;
	/**滑动到哪个页面*/
	public SwitchPositionListener switchListener;
	/**当前弹出页编辑*/
	public ChildPopupOnClick childPopupOnClick;
	public int mLastX, xtox;
	boolean isCountXY = false;
	public int mLastY, ytoy;
	public Bitmap bitmap;
	
	/**判断是否开始拖拽*/
	public boolean start;
	public WindowManager windowManager;
	public WindowManager.LayoutParams windowParams;
	public TextView layout, mTextEdit, mTextDelete, mTextRename, mTextReColor;
	/**情景页面发生了移动*/
	public static final int HANDLE_LIFE_SCENE=1;
	/**电器页面发生了移动*/
	public static final int HANDLE_ELECTRIC_CTR=2;
	/**房间控制页面发生了移动*/
	public static final int HANDLE_ROOM_CTR=3;
	/**房间控制子目录*/
	public static final int HANDLE_SECOND_ROOM=4;
	/**电器控制子目录*/
	public static final int HANDLE_SECOND_ELECTRIC=5;
	/**标识是哪个页面发生移动*/
	public int whichPageMove=0;
	// 交换的图片
	private ImageView iv_drag;
	static boolean flag = false;
	private DataAdapter adapter;
	public DragGridView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public DragGridView(Context context) {
		super(context);

	}

	public void setLongFlag(boolean temp) {
		flag = temp;
	}

	public void setOnItemLongClickListener(OnItemLongClickListener listener) {
		super.setOnItemLongClickListener(listener);
	}

	/**
	 * 长按拖动
	 * 
	 * @param ev
	 * @return
	 */
	public boolean setOnItemLongClickListener(final MotionEvent ev) {

		this.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.e("DragGridView","whichPageMove---->"+whichPageMove);
				Configure.isMove = true;
				int x = (int) ev.getX();
				int y = (int) ev.getY();
				mLastX = x;
				mLastY = y;
				dragPosition = dropPosition = position;

				// 拖拽位置是否有效
				if (dragPosition == AdapterView.INVALID_POSITION) {
					return false;
				}

				// 开始拖拽的图片
				fromView = (ViewGroup) getChildAt(dragPosition
						- getFirstVisiblePosition());
				fromView.destroyDrawingCache();
				fromView.setDrawingCacheEnabled(true);
//				fromView.setDrawingCacheBackgroundColor(0xff6DB7ED);
				// 设置拖拽图片的背景颜色
				Bitmap bm = Bitmap.createBitmap(fromView.getDrawingCache());
				bitmap = Bitmap.createBitmap(bm, 8, 8, bm.getWidth() - 20,
						bm.getHeight() - 20);

				longPosition=position;
				Log.e("drag","--->"+longPosition);
				if(whichPageMove!=2){
				showView(mLastX, mLastY);
				}
				//1 2 3说明是在主页面，需要发生移动
				if(whichPageMove==1||whichPageMove==3)
				{
				start = true;
				}

				return true;
			};
		});
		return super.onInterceptTouchEvent(ev);
	}
	//首先执行
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			return setOnItemLongClickListener(ev);
		}

		return super.onInterceptTouchEvent(ev);
	}

	//其次执行
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {// TODO
		case MotionEvent.ACTION_MOVE:
				if (start) {
					Log.e("移动......", "移动.......");
					startDrag(bitmap, mLastX, mLastY);
					hideView();
			}
			break;
		case MotionEvent.ACTION_UP: // 松手时取消拖拽
			if (iv_drag != null && dragPosition != AdapterView.INVALID_POSITION) {
				start = false;
			}
			break;

		}
		return super.dispatchTouchEvent(ev);
	}
	//最后执行
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (iv_drag != null && dragPosition != AdapterView.INVALID_POSITION) {
			int x = (int) ev.getX();
			int y = (int) ev.getY();
			switch (ev.getAction()) {
			case MotionEvent.ACTION_MOVE:
				if (!isCountXY) {
					xtox = x - mLastX;
					ytoy = y - mLastY;
					isCountXY = true;
				}
				onDrag(x, y);
				break;
			case MotionEvent.ACTION_UP:
				stopDrag();
				onDrop(x, y);
				break;
			}
		}else{
			ScrollLayout layout=(ScrollLayout) getParent().getParent();
			layout.onTouchEvent(ev);
		}
		return super.onTouchEvent(ev);
	}
	

	// TODO
	/** 长按时显示编辑 */
	private void showView(int x, int y) {
		windowManager = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);// "window"
		stopDrag();
		window();
		layout = new TextView(getContext());
		layout.setId(Configure.LAYOUT);
		layout.setOnTouchListener(this);
		windowManager.addView(layout, windowParams);
		//mTextEdit的宽高获取不到
		mTextEdit = new TextView(getContext());
		mTextEdit.setBackgroundResource(R.drawable.edit_bg);
		mTextEdit.setGravity(Gravity.CENTER);
		mTextEdit.setId(Configure.EDIT);
		mTextEdit.setOnClickListener(this);
		window( DensityUtil.dip2px(getContext(), -50),DensityUtil.dip2px(getContext(), 70));
		windowManager.addView(mTextEdit, windowParams);

		mTextDelete = new TextView(getContext());
		mTextDelete.setBackgroundResource(R.drawable.delete_bg);
		mTextDelete.setGravity(Gravity.CENTER);
		mTextDelete.setId(Configure.DELETE);
		mTextDelete.setOnClickListener(this);
		window(DensityUtil.dip2px(getContext(), 20),DensityUtil.dip2px(getContext(), 120));
		windowManager.addView(mTextDelete, windowParams);

		mTextRename = new TextView(getContext());
		mTextRename.setBackgroundResource(R.drawable.rename_bg);
		mTextRename.setGravity(Gravity.CENTER);
		mTextRename.setId(Configure.RENAME);
		mTextRename.setOnClickListener(this);
		window(DensityUtil.dip2px(getContext(), 20),DensityUtil.dip2px(getContext(), 200));
		windowManager.addView(mTextRename, windowParams);

		mTextReColor = new TextView(getContext());
		mTextReColor.setBackgroundResource(R.drawable.recolor_bg);
		mTextReColor.setGravity(Gravity.CENTER);
		mTextReColor.setId(Configure.RECOLOR);
		mTextReColor.setOnClickListener(this);
		window(DensityUtil.dip2px(getContext(),-50),DensityUtil.dip2px(getContext(), 240));
		windowManager.addView(mTextReColor, windowParams);

	}
	
	/**
	 * 创建编辑项
	 * @param x
	 * @param y
	 */
	protected void window(int x, int y) {
		windowParams = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
				PixelFormat.TRANSLUCENT);

		windowParams.gravity = Gravity.TOP | Gravity.LEFT;

		// 设置创建出来图片的位置
		windowParams.x = fromView.getRight() + x;
		windowParams.y = fromView.getTop() + y;
		// 设置创建出来的图片的透明度，宽，高
		windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
	}

	/**
	 * 创建背景项
	 */
	protected void window() {
		windowParams = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
						| WindowManager.LayoutParams.FLAG_FULLSCREEN,
				PixelFormat.TRANSLUCENT);

		windowParams.gravity = Gravity.TOP | Gravity.LEFT;

		// 设置创建出来的图片的透明度，宽，高
		windowParams.height = WindowManager.LayoutParams.MATCH_PARENT;
		windowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
	}

	/**
	 * 隐藏长按时显示出来的 按钮
	 */
	public void hideView() {
		if(null!=layout&&layout.getVisibility()==View.VISIBLE){
			layout.setVisibility(View.GONE);
		}
		if(null!=mTextEdit&&mTextEdit.getVisibility()==View.VISIBLE){
		mTextEdit.setVisibility(View.GONE);
		}
		if(null!=mTextDelete&&mTextDelete.getVisibility()==View.VISIBLE){
		mTextDelete.setVisibility(View.GONE);
		}
		if(null!=mTextRename&&mTextRename.getVisibility()==View.VISIBLE){
		mTextRename.setVisibility(View.GONE);
		}
		if(null!=mTextReColor&&mTextReColor.getVisibility()==View.VISIBLE){
		mTextReColor.setVisibility(View.GONE);
		}
		start = false;
	}

	/**
	 * 
	 * @param bm
	 * @param x
	 * @param y
	 */
	private void startDrag(final Bitmap bm, final int x, final int y) {

		windowManager = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);// "window"

		// 设置背景颜色
		fromView.setVisibility(View.GONE);
		stopDrag();
		windowParams = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
		windowParams.gravity = Gravity.TOP | Gravity.LEFT;

		// TODO
		// 设置创建出来图片的位置
		windowParams.x = fromView.getLeft();
		windowParams.y = fromView.getTop()+50;
		Log.e("Params-->","--->"+windowParams.x+"..."+windowParams.y);
		// 设置创建出来的图片的透明度，宽，高
		windowParams.alpha = 1.0f;
		windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
		windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;

		iv_drag = new ImageView(getContext());
		iv_drag.setImageBitmap(bm);
		windowManager.addView(iv_drag, windowParams);
	}

	/**
	 * 拖拽
	 * @param x
	 * @param y
	 */
	private void onDrag(int x, int y) {
		if (iv_drag != null) {
			windowParams.alpha = 0.8f;
			windowParams.x = (x - mLastX - xtox) + fromView.getLeft() - moveNum
					* Configure.screenWidth;
			windowParams.y = (y - mLastY - ytoy) + fromView.getTop()
					+ (int) (40 * Configure.screenDensity) + 8;
			windowManager.updateViewLayout(iv_drag, windowParams);
		}

		if (moveNum > 0) {
			if ((x >= (moveNum + 1) * Configure.screenWidth - 20
					* Configure.screenDensity || x <= moveNum
					* Configure.screenWidth)
					&& !Configure.isChangingPage)
				stopCount++;
			else {
				stopCount = 0;
			}
			if (stopCount > 10) {
				stopCount = 0;
				if (x >= (moveNum + 1) * Configure.screenWidth - 20
						* Configure.screenDensity
						&& Configure.curentPage < Configure.countPages - 1) {
					Configure.isChangingPage = true;
					pageListener.page(0, ++Configure.curentPage);
					moveNum++;
				} else if (x <= moveNum * Configure.screenWidth
						&& Configure.curentPage > 0) {
					Configure.isChangingPage = true;
					pageListener.page(0, --Configure.curentPage);
					moveNum--;
				}
			}
		} else {
			if ((x >= (moveNum + 1) * Configure.screenWidth - 20
					* Configure.screenDensity || x <= moveNum
					* Configure.screenWidth)
					&& !Configure.isChangingPage)
				stopCount++;
			else
				stopCount = 0;
			if (stopCount > 10) {
				stopCount = 0;
				if (x >= (moveNum + 1) * Configure.screenWidth - 20
						* Configure.screenDensity
						&& Configure.curentPage < Configure.countPages - 1) {
					Configure.isChangingPage = true;
					pageListener.page(0, ++Configure.curentPage);
					moveNum++;
				} else if (x <= moveNum * Configure.screenWidth
						&& Configure.curentPage > 0) {
					Configure.isChangingPage = true;
					pageListener.page(0, --Configure.curentPage);
					moveNum--;
				}
			}
		}
	}

	/**
	 * 松手
	 * 
	 * @param x
	 * @param y
	 */
	private void onDrop(int x, int y) {
		Configure.isMove = false;
		start = false;
		int tempPosition = pointToPosition(x - moveNum * Configure.screenWidth,
				y);
		if (tempPosition != AdapterView.INVALID_POSITION) {
			dropPosition = tempPosition;
		}
		adapter = (DataAdapter) this.getAdapter();
		if (moveNum != 0) {
			itemListener.change(dragPosition, dropPosition, moveNum);
			moveNum = 0;
			return;
		}
		moveNum = 0;
		// 设置数据适配器
		fromView.setBackgroundColor(Color.TRANSPARENT);
		if(dropPosition!=AdapterView.INVALID_POSITION){
		adapter.exchange(dragPosition, dropPosition);
		switchListener.post(dragPosition, dropPosition);
		adapter.notifyDataSetChanged();
		}

	}

	private void stopDrag() {
		if (iv_drag != null) {
			windowManager.removeView(iv_drag);
			iv_drag = null;
		}
	}
	
	/**
	 * 监听滑动翻页
	 * @param pageListener
	 */
	public void setPageListener(G_PageListener pageListener) {
		this.pageListener = pageListener;
	}

	public interface G_PageListener {
		void page(int cases, int page);
	}

	/**
	 * 监听选项跨页移动
	 * @param pageListener
	 */
	public void setOnItemChangeListener(G_ItemChangeListener pageListener) {
		this.itemListener = pageListener;
	}

	public interface G_ItemChangeListener {
		void change(int from, int to, int count);
	}
	/**
	 * 监听是哪个页面的数据进行位置移动
	 */
	public interface SwitchPositionListener{
		void post(int dragPosition,int dropPosition);
	}
	public void tellChildUpdate(SwitchPositionListener switchListener ){
		this.switchListener=switchListener;
	}
	
	/**
	 * 弹出页进行编辑
	 * @author 
	 *
	 */
	public interface ChildPopupOnClick{
	 void childPopupOnClick(View v,int which,int itemPos);
	};
	public void setChildPopupOnClick(ChildPopupOnClick childPopupOnClick){
		this.childPopupOnClick=childPopupOnClick;
	}

	
	@Override
	public void onClick(View v) {
		
		childPopupOnClick.childPopupOnClick(v, whichPageMove,longPosition);
		Log.e("DragGridView---","whichpageMove----->"+whichPageMove);
		hideView();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			Log.e("DragGridView","--->onTouch+down");
			hideView();
			break;

		default:
			break;
		}
		return false;
	}
	
	
	

}
