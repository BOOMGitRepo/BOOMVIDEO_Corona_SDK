package plugin.library;

import android.app.Activity;
import android.util.Log;

import com.boomvideo.framework.common.Logger;
import com.boomvideo.framework.dto.OperationResult;
import com.boomvideo.videotracker.BoomVideoResourceManager;
import com.boomvideo.videotracker.BoomVideoTrackerInf;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.NamedJavaFunction;

/** Implements the library.test() Lua function. */
public class OfferListWrapper implements NamedJavaFunction, BoomVideoTrackerInf {
	private static final String TAG = OfferListWrapper.class.getSimpleName();
	Activity mActivity;
	LuaState mLuaState;

	public OfferListWrapper(Activity activity) {
		mActivity = activity;
	}

	@Override
	public int invoke(LuaState L) {
		return offer(L);
	}

	@Override
	public String getName() {
		return "offer";
	}

	public int offer(LuaState l) {
		mLuaState = l;
		final String boomGuid = l.checkString(1);
		Log.v("Lualoader", "boomGuid...." + boomGuid);
		final BoomVideoResourceManager boomVideoResourceManager = BoomVideoResourceManager
				.getInstance();
		
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				boomVideoResourceManager.showVideoAds(boomGuid,
						OfferListWrapper.this,
						BoomVideoResourceManager.VIDEO_PLAYER_TYPE.OFFERLIST);
			}
		});

		return 1;
	}

	@Override
	public void onVideoTrackEvent(OperationResult operationResult) {
		String mEventName = RegisterWrapper.getEventName();
		Log.v(TAG, "functionName...." + mEventName);
		String points;
		String errorInfo;
		
		if(mLuaState.isOpen())
		{
		mLuaState.load("function CallFunction_fromNative() " + mEventName
				+ "(\"" + "onVideoTrackEvent" + "\") end", "=simple");
		switch (operationResult.getResultMessages()) {

		case AD_VIEW_LOADED:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "AD_VIEW_LOADED" + "\") end", "=simple");
			break;
		case AD_FAILED:
			errorInfo = operationResult.getErrorInfo().toString();
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "AD_FAILED" +errorInfo+ "\") end",
					"=simple");
			break;
		case AD_VIEW_CLOSED:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "AD_VIEW_CLOSED" + "\") end", "=simple");
			break;
		case POINTS_REVEALED:
			points = String.valueOf(operationResult.getPoints());
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "POINTS_REVEALED" + points
					+ "\") end", "=simple");
			break;							
		case SUCCESSFUL_SHARED_ON_FACEBOOK:
			mLuaState
					.load("function CallFunction_fromNative() " + mEventName
							+ "(\"" + "SUCCESSFUL_SHARED_ON_FACEBOOK" + "\") end",
							"=simple");
			break;
		case SUCCESSFUL_SHARED_ON_GOOGLEPLUS:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "SUCCESSFUL_SHARED_ON_GOOGLEPLUS" + "\") end", "=simple");
			break;
		case SUCCESSFUL_SHARED_ON_TWITTER:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "SUCCESSFUL_SHARED_ON_TWITTER" + "\") end", "=simple");
			break;
		default:
			break;
		}

		mLuaState.call(0, 0);  
		mLuaState.getGlobal("CallFunction_fromNative");  
		mLuaState.call(0, 0);
		}else
		{
			Logger.e(TAG, "Lua state is Closed");
		}
	}

	@Override
	public Activity getContext() {
		return mActivity;
	}

}
