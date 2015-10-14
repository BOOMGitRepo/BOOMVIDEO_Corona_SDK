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
public class PrerollWrapper implements NamedJavaFunction, BoomVideoTrackerInf {
	private static final String TAG = PrerollWrapper.class.getSimpleName();
	Activity mActivity;
	String mEventName;
	LuaState mLuaState;

	public PrerollWrapper(Activity activity) {
		mActivity = activity;
	}

	@Override
	public int invoke(LuaState L) {
		return preroll(L);
	}

	@Override
	public String getName() {
		return "preroll";
	}

	public int preroll(LuaState l) {
		mLuaState = l;
		final String boomGuid = l.checkString(1);
		Log.v("Lualoader", "boomGuid...." + boomGuid);

		final BoomVideoResourceManager boomVideoResourceManager = BoomVideoResourceManager
				.getInstance();
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				boomVideoResourceManager.showVideoAds(boomGuid,
						PrerollWrapper.this,
						BoomVideoResourceManager.VIDEO_PLAYER_TYPE.PREROLL);
			}
		});

		return 1;
	}

	public int register(LuaState luaState) {
		// https://forums.coronalabs.com/topic/46402-a-new-dirty-and-amazingly-efficient-way-to-call-a-lua-function-from-your-native-android-code/
		mEventName = luaState.checkString(1);
		mLuaState = luaState;
		Log.v(TAG, "functionName...." + mEventName);
		return 1;
	}

	@Override
	public void onVideoTrackEvent(OperationResult operationResult) {
		String mEventName = RegisterWrapper.getEventName();
		Log.v(TAG, "functionName...." + mEventName);
		String errorInfo;
		if(mLuaState.isOpen())
		{
		mLuaState.load("function CallFunction_fromNative() " + mEventName
				+ "(\"" + "onVideoTrackEvent" + "\") end", "=simple");
		switch (operationResult.getResultMessages()) {

		case AD_VIEW_LOADED:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "VIDEO_UNAVAILABLE" + "\") end", "=simple");
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
			
		case SUCCESSFUL_SHARED_ON_FACEBOOK:
			mLuaState
					.load("function CallFunction_fromNative() " + mEventName
							+ "(\"" + "FACEBOOK_SHARE_COMPLETED" + "\") end",
							"=simple");

			break;
		case SUCCESSFUL_SHARED_ON_GOOGLEPLUS:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "GOOGLE_SHARE_COMPLETED" + "\") end", "=simple");
			break;
		case SUCCESSFUL_SHARED_ON_TWITTER:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "TWITTER_SHARE_COMPLETED" + "\") end", "=simple");
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
