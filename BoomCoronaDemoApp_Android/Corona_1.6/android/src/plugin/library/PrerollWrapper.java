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
				.getInstance(this.getContext());
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
		String points;
		
		if(mLuaState.isOpen())
		{
		mLuaState.load("function CallFunction_fromNative() " + mEventName
				+ "(\"" + "onVideoTrackEvent" + "\") end", "=simple");
		switch (operationResult.getResultMessages()) {

		case VIDEO_UNAVAILABLE:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "VIDEO_UNAVAILABLE" + "\") end", "=simple");
			break;
		case PLAYER_INITAILIZATION_ERROR:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "PLAYER_INITAILIZATION_ERROR" + "\") end",
					"=simple");
			break;
		case VIDEO_FIRST_PLAY:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "VIDEO_FIRST_PLAY" + "\") end", "=simple");
			break;
		case VIDEO_END_PLAY:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "VIDEO_END_PLAY" + "\") end", "=simple");
			break;

		case ANNOTATION_CLICK:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "ANNOTATION_CLICK" + "\") end", "=simple");

			break;
		case VIDEO_CALLBACK_FIRETIME_COMPLETED:
			points = String.valueOf(operationResult.getPoints());
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "VIDEO_CALLBACK_FIRETIME_COMPLETED" + points
					+ "\") end", "=simple");
			break;

		case FACEBOOK_SHARE_COMPLETED:
			mLuaState
					.load("function CallFunction_fromNative() " + mEventName
							+ "(\"" + "FACEBOOK_SHARE_COMPLETED" + "\") end",
							"=simple");

			break;
		case GOOGLE_SHARE_COMPLETED:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "GOOGLE_SHARE_COMPLETED" + "\") end", "=simple");
			break;
		case TWITTER_SHARE_COMPLETED:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "TWITTER_SHARE_COMPLETED" + "\") end", "=simple");
			break;
		case VIDEO_PAUSED:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "VIDEO_PAUSED" + "\") end", "=simple");
			break;

		case INSTAGRAM_URL:
			points = String.valueOf(operationResult.getPoints());
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "INSTAGRAM_URL" + "," + points + "\") end",
					"=simple");

			break;

		case SLIDESHARE_URL:
			points = String.valueOf(operationResult.getPoints());
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "SLIDESHARE_URL" +points+ "\") end", "=simple");
			break;
		case SURVEY_COMPLETED:
			points = String.valueOf(operationResult.getPoints());
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "SURVEY_COMPLETED" +points+"\") end", "=simple");
			break;
		case BLOG_URL:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "SURVEY_COMPLETED" +"\") end", "=simple");
			break;
		case APP_INSTALLED:
			
			points = String.valueOf(operationResult.getPoints());
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "APP_INSTALLED" + points+"\") end", "=simple");
			break;

		case VIDEO_PROGRESS:
			int progressRule = (Integer) operationResult.getResult();
			if (progressRule == OperationResult.VIDEO_COMPLETED_25P) {
				mLuaState.load("function CallFunction_fromNative() "
						+ mEventName + "(\"" + "VIDEO_COMPLETED_25P"
						+ "\") end", "=simple");
			} else if (progressRule == OperationResult.VIDEO_COMPLETED_50P) {
				mLuaState.load("function CallFunction_fromNative() "
						+ mEventName + "(\"" + "VIDEO_COMPLETED_50P"
						+ "\") end", "=simple");

			} else if (progressRule == OperationResult.VIDEO_COMPLETED_75P) {
				mLuaState.load("function CallFunction_fromNative() "
						+ mEventName + "(\"" + "VIDEO_COMPLETED_75P"
						+ "\") end", "=simple");
			}
			break;

		case INTERNET_UNAVAILABLE:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "INTERNET_UNAVAILABLE" + "\") end", "=simple");
			break;

		case OFFER_AVAILED_ALREADY:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "OFFER_AVAILED_ALREADY" + "\") end", "=simple");
			break;

		case INTERSTITIAL_LOADED:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "INTERSTITIAL_LOADED" + "\") end", "=simple");
			break;

		case INTERSTITIAL_CLICKED:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "INTERSTITIAL_CLICKED" + "\") end", "=simple");
			break;

		case INTERSTITIAL_CLOSED:
			mLuaState.load("function CallFunction_fromNative() " + mEventName
					+ "(\"" + "INTERSTITIAL_CLOSED" + "\") end", "=simple");
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
