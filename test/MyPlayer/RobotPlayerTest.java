package MyPlayer;
import battlecode.common.*;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class RobotPlayerTest {
	RobotController rcMock;
	HQ hqMock;
	Miner minerMock;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	//this test tests if hq can make miners.  The Hq makes miners in almost all directions.
	//strangly it does not make a miner in the north direction even though that is the first direction that is tested.
	//also it makes more than 7 miners, but that is because it checks for 5 miners before attempting to build in all directions
	//so if there are less then 5 then it should attempt to build 8 miners
	@Before
	public void create1() throws GameActionException{
		rcMock = mock(RobotController.class);
		hqMock = new HQ(rcMock);

		// the first statement after the try doesn't execute for some reason
		when(hqMock.tryBuild(RobotType.MINER, Direction.NORTH)).thenReturn(true);
		when(hqMock.tryBuild(RobotType.MINER, Direction.NORTH)).thenReturn(true);
		when(hqMock.tryBuild(RobotType.MINER, Direction.NORTHEAST)).thenReturn(true);
		when(hqMock.tryBuild(RobotType.MINER, Direction.EAST)).thenReturn(true);
		when(hqMock.tryBuild(RobotType.MINER, Direction.SOUTHEAST)).thenReturn(true);
		when(hqMock.tryBuild(RobotType.MINER, Direction.SOUTH)).thenReturn(true);
		when(hqMock.tryBuild(RobotType.MINER, Direction.SOUTHWEST)).thenReturn(true);
		when(hqMock.tryBuild(RobotType.MINER, Direction.WEST)).thenReturn(true);
		when(hqMock.tryBuild(RobotType.MINER, Direction.NORTHWEST)).thenReturn(true);

		System.setOut(new PrintStream(outContent));
	}
	@Test
	public void testHQMakesMinersWhenLessThan5()throws GameActionException{
		//HQ hq =  new HQ(rcMock);
		//hqMock.numMiners=0;
		hqMock.turnCount=2;
		hqMock.takeTurn();

		//assertThat(outContent.toString(), equalTo("I made a miner!"));
		assertThat(outContent.toString(), containsString("I made a miner!"));
	}
	@After
	public void restoreStreams() {
		System.setOut(originalOut);
	}


	@Before
	public void MinersWithSoupMoveTowardRefineryWhenFoundSetUp() throws GameActionException{
		rcMock = mock(RobotController.class);
		minerMock = new Miner(rcMock);
		MapLocation hqLoc = new MapLocation(15,15);

		//miner.refineryLoc= miner.adjacentLocation(Direction.NORTH);

		// the first statement after the try doesn't execute for some reason
		when(rcMock.getSoupCarrying()).thenReturn(RobotType.MINER.soupLimit);
		when(rcMock.getSoupCarrying()).thenReturn(RobotType.MINER.soupLimit);
		when(minerMock.comms.getNewDesignSchoolCount()).thenReturn(1);

		minerMock.hqLoc= hqLoc;

		System.setOut(new PrintStream(outContent));
	}
	@Test
	public void MinersWithSoupMoveTowardRefineryWhenFoundand()throws GameActionException{
		//Miner miner =  new Miner(rcMock);
		MapLocation minerLoc = new MapLocation(1,1);
		MapLocation refineryLoc = new MapLocation(10,10);
		minerMock.refineryLoc=refineryLoc;
		minerMock.turnCount=2;

		minerMock.takeTurn();
		//assertThat(outContent.toString(), equalTo("I made a miner!"));
		assertThat(outContent.toString(), containsString("Moved towards refinery."));
	}
	@After
	public void tearDownMinersWithSoupMoveTowardRefineryWhenFound() {
		System.setOut(originalOut);
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

	@Test
	public void testSanity() {
		assertEquals(2, 1+1);
		//assertEquals(2, 1);
	}

}