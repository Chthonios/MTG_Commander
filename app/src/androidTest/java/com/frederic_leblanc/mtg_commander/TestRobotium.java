package com.frederic_leblanc.mtg_commander;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;

import com.robotium.solo.Solo;

/**
 * Created by Frédéric Leblanc on 2015-08-23.
 */
public class TestRobotium extends ActivityInstrumentationTestCase2 {

	private Solo solo;
	private static final String TAG = "TestRobotium";

	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.frederic_leblanc.mtg_commander.MainActivity";

	private static Class<?> launcherActivityClass;
	static {
		try {
			launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
		} catch (ClassNotFoundException e) {
			Log.e(TAG, "Class not found");
			throw new RuntimeException(e);
		}
	}

	public TestRobotium() throws ClassNotFoundException {
		super(launcherActivityClass);
	}

	public void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
	}

	public void tearDown() throws Exception {
		solo.finishOpenedActivities();
		super.tearDown();
	}

	public void testRun() {

		solo.waitForActivity("MainActivity", 2000);
		solo.hideSoftKeyboard();
		solo.clickOnButton(0);
		solo.clickOnScreen(50f, 150f);
		solo.clickOnView(solo.getView(R.id.imageButtonCard));
		solo.clickOnView(solo.getView(R.id.buttonCastPlus));
		solo.clickOnView(solo.getView(R.id.buttonCastMinus));
		solo.clickInList(2);
		for (int i = 0; i < 10; i++)
			solo.clickOnView(solo.getView(R.id.buttonDeathPlus));
		solo.clickInList(3);
		solo.scrollToSide(Solo.RIGHT);
		solo.scrollToSide(Solo.RIGHT);
		solo.scrollToSide(Solo.RIGHT);
		solo.scrollToSide(Solo.RIGHT);
		solo.scrollToSide(Solo.LEFT);
		solo.scrollToSide(Solo.LEFT);
		solo.scrollToSide(Solo.LEFT);
		solo.scrollToSide(Solo.LEFT);
	}

}
