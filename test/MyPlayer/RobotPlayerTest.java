package MyPlayer;
import battlecode.common.*;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RobotPlayerTest {
	RobotController rcMock;

	@Test
	public void testSanity() {
		assertEquals(2, 1+1);
		//assertEquals(2, 1);
	}
	
	@Before
	public void create(){
		rcMock = mock(RobotController.class);
	}
	//Doesn't throw exception under these conditions, but would need to add this line if it was expected to throw exception
	//@Test (expected = GameActionException.class)
	@Test
	public void testTryMine()throws GameActionException{
		// I think I was essentially trying to create my own mock class, so it seemed like it might be simpler to just use mockito
		//RobotController rc = new RobotControllerTester();
		Miner miner =  new Miner(rcMock);
		miner.checkIfSoupGone();
	}

}
