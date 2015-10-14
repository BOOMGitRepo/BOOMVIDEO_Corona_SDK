package plugin.library;

import android.app.Activity;
import android.util.Log;

import com.naef.jnlua.LuaState;
import com.naef.jnlua.NamedJavaFunction;

/** Implements the library.test() Lua function. */
public class RegisterWrapper implements NamedJavaFunction {
	Activity mActivity;
	static String mEventName;

	
	@Override
	public int invoke(LuaState L) {
		return register(L);
	}

	@Override
	public String getName() {
		return "register";
	}
	
	public static String getEventName()
	{
		return mEventName;
	}
	
	public int register(LuaState luaState) {
		 mEventName = luaState.checkString(1);
		Log.v("TestWrapper", "functionName...." + mEventName);
		return 1;
	}

}
