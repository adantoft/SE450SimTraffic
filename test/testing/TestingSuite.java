package testing;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import timeserver.TimeServerTEST;

@RunWith(Suite.class) //Runs each of the below tests in addition to tests here
@SuiteClasses({TimeServerTEST.class}) // add new tests here

public class TestingSuite {

	@Test
	public void test1() {
		Assert.assertTrue(true);
	}
}
