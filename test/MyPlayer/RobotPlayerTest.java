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
	DesignSchool dsMock;
	Landscaper lsMock;
	Util utilMock;

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;


	// ------------------------------------------------------------------------------------------
	//									hq
	// ------------------------------------------------------------------------------------------

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
	public void testHQMakesMinersWhenLessThan5()throws GameActionException, NullPointerException{
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

	// ------------------------------------------------------------------------------------------
	//									design school
	// ------------------------------------------------------------------------------------------

	//test design school makes miners when turn count mode 30 is true
	@Before
	public void makeMinersSetUp() throws GameActionException{
		rcMock = mock(RobotController.class);
		dsMock = new DesignSchool(rcMock);

		// the first statement after the try doesn't execute for some reason
		when(dsMock.tryBuild(RobotType.LANDSCAPER, Direction.NORTH)).thenReturn(true);
		when(dsMock.tryBuild(RobotType.LANDSCAPER, Direction.NORTH)).thenReturn(true);
		when(dsMock.tryBuild(RobotType.LANDSCAPER, Direction.NORTHEAST)).thenReturn(true);
		when(dsMock.tryBuild(RobotType.LANDSCAPER, Direction.EAST)).thenReturn(true);
		when(dsMock.tryBuild(RobotType.LANDSCAPER, Direction.SOUTHEAST)).thenReturn(true);
		when(dsMock.tryBuild(RobotType.LANDSCAPER, Direction.SOUTH)).thenReturn(true);
		when(dsMock.tryBuild(RobotType.LANDSCAPER, Direction.SOUTHWEST)).thenReturn(true);
		when(dsMock.tryBuild(RobotType.LANDSCAPER, Direction.WEST)).thenReturn(true);
		when(dsMock.tryBuild(RobotType.LANDSCAPER, Direction.NORTHWEST)).thenReturn(true);
		System.setOut(new PrintStream(outContent));
	}
	@Test
	public void testDSMakesMinersWhenTurnCountMod30EqualsTrue()throws GameActionException, NullPointerException{
		//HQ hq =  new HQ(rcMock);
		//dsMock.numMiners=0;
		dsMock.turnCount=29;
		dsMock.comms.broadcastedCreation=true;
		dsMock.takeTurn();
		assertThat(outContent.toString(), containsString("made a landscaper"));
		//assertEquals(2, 1+1);
	}
	@After
	public void TestDSMakesMinersTearDown() {
		System.setOut(originalOut);
	}


	//test design school DOES NOT make lanscaper when turn count mode 30 is false
	@Before
	public void makedesignSchool2SetUp() throws GameActionException{
		rcMock = mock(RobotController.class);
		dsMock = new DesignSchool(rcMock);

		// the first statement after the try doesn't execute for some reason
		when(dsMock.tryBuild(RobotType.LANDSCAPER, Direction.NORTH)).thenReturn(true);
		when(dsMock.tryBuild(RobotType.LANDSCAPER, Direction.NORTH)).thenReturn(true);
		when(dsMock.tryBuild(RobotType.LANDSCAPER, Direction.NORTHEAST)).thenReturn(true);
		when(dsMock.tryBuild(RobotType.LANDSCAPER, Direction.EAST)).thenReturn(true);
		when(dsMock.tryBuild(RobotType.LANDSCAPER, Direction.SOUTHEAST)).thenReturn(true);
		when(dsMock.tryBuild(RobotType.LANDSCAPER, Direction.SOUTH)).thenReturn(true);
		when(dsMock.tryBuild(RobotType.LANDSCAPER, Direction.SOUTHWEST)).thenReturn(true);
		when(dsMock.tryBuild(RobotType.LANDSCAPER, Direction.WEST)).thenReturn(true);
		when(dsMock.tryBuild(RobotType.LANDSCAPER, Direction.NORTHWEST)).thenReturn(true);
		System.setOut(new PrintStream(outContent));
	}
	@Test
	public void testDSDoesNotMakesMinersWhenTurnCountMod30EqualsTrue()throws GameActionException, NullPointerException{
		//HQ hq =  new HQ(rcMock);
		//dsMock.numMiners=0;
		dsMock.turnCount=30;
		dsMock.comms.broadcastedCreation=true;
		dsMock.takeTurn();
		assertThat(outContent.toString(), equalTo(""));
		//assertEquals(2, 1+1);
	}
	@After
	public void TestDSMakesLandscaper2TearDown() {
		System.setOut(originalOut);
	}


	// ------------------------------------------------------------------------------------------
	//									lanscaper
	// ------------------------------------------------------------------------------------------
	//hqloc is true and can dig dirt returns true
	@Before
	public void lanscaperSetup1() throws GameActionException{
		rcMock = mock(RobotController.class);
		lsMock = new Landscaper(rcMock);
		utilMock =  new Util();

		// the first statement after the try doesn't execute for some reason
		when(rcMock.canDigDirt(Direction.NORTH)).thenReturn(true);
		when(rcMock.canDigDirt(Direction.NORTHEAST)).thenReturn(true);
		when(rcMock.canDigDirt(Direction.NORTHWEST)).thenReturn(true);
		when(rcMock.canDigDirt(Direction.SOUTH)).thenReturn(true);
		when(rcMock.canDigDirt(Direction.SOUTHEAST)).thenReturn(true);
		when(rcMock.canDigDirt(Direction.SOUTHWEST)).thenReturn(true);
		when(rcMock.canDigDirt(Direction.EAST)).thenReturn(true);
		when(rcMock.canDigDirt(Direction.WEST)).thenReturn(true);

		System.setOut(new PrintStream(outContent));
	}
	@Test
	public void testLanscaper1()throws GameActionException, NullPointerException{
		lsMock.hqLoc=null;
		Boolean result;
		result=lsMock.tryDig();
		assertThat(result, equalTo(true));
		//assertEquals(2, 1+1);
	}
	@After
	public void landscaper1TearDown() {
		System.setOut(originalOut);
	}

	// does not work, candigdirt still returning true for some reason
	//hqloc is true and can dig dirt returns FALSE
	@Before
	public void lanscaperSetup2() throws GameActionException{
		rcMock = mock(RobotController.class);
		lsMock = new Landscaper(rcMock);

		// the first statement after the try doesn't execute for some reason
		when(rcMock.canDigDirt(Direction.NORTH)).thenReturn(false);
		when(rcMock.canDigDirt(Direction.NORTH)).thenReturn(false);
		when(rcMock.canDigDirt(Direction.NORTHEAST)).thenReturn(false);
		when(rcMock.canDigDirt(Direction.NORTHWEST)).thenReturn(false);
		when(rcMock.canDigDirt(Direction.SOUTH)).thenReturn(false);
		when(rcMock.canDigDirt(Direction.SOUTHEAST)).thenReturn(false);
		when(rcMock.canDigDirt(Direction.SOUTHWEST)).thenReturn(false);
		when(rcMock.canDigDirt(Direction.EAST)).thenReturn(false);
		when(rcMock.canDigDirt(Direction.WEST)).thenReturn(false);


		System.setOut(new PrintStream(outContent));
	}
	@Test
	//public void testLanscaper2()throws GameActionException, NullPointerException{
		//lsMock.hqLoc=null;
		//Boolean result;
		//result=lsMock.tryDig();
		//System.out.println("hello?");
		//System.out.println(outContent.toString());
		//assertThat(outContent.toString(), equalTo("hello"));
		//(result, equalTo(false));
		//assertEquals(2, 1+1);
	//}
	@After
	public void landscaper2TearDown() {
		System.setOut(originalOut);
	}


	// ------------------------------------------------------------------------------------------
	//									miner
	// ------------------------------------------------------------------------------------------

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