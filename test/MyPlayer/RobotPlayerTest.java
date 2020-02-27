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

	Landscaper lsMock;
	Util utilMock;
	Miner minerMock;
	Miner minerMock2;

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
	private final ByteArrayOutputStream ds1outContent = new ByteArrayOutputStream();
	DesignSchool dsMock1;
	RobotController dsRC1;
	@Before
	public void setUpDSmakesLandscapers() throws GameActionException{
		dsRC1 = mock(RobotController.class);
		dsMock1 = new DesignSchool(dsRC1);

		// the first statement after the try doesn't execute for some reason
		when(dsMock1.tryBuild(RobotType.LANDSCAPER, Direction.NORTH)).thenReturn(true);
		when(dsMock1.tryBuild(RobotType.LANDSCAPER, Direction.NORTH)).thenReturn(true);
		when(dsMock1.tryBuild(RobotType.LANDSCAPER, Direction.NORTHEAST)).thenReturn(true);
		when(dsMock1.tryBuild(RobotType.LANDSCAPER, Direction.EAST)).thenReturn(true);
		when(dsMock1.tryBuild(RobotType.LANDSCAPER, Direction.SOUTHEAST)).thenReturn(true);
		when(dsMock1.tryBuild(RobotType.LANDSCAPER, Direction.SOUTH)).thenReturn(true);
		when(dsMock1.tryBuild(RobotType.LANDSCAPER, Direction.SOUTHWEST)).thenReturn(true);
		when(dsMock1.tryBuild(RobotType.LANDSCAPER, Direction.WEST)).thenReturn(true);
		when(dsMock1.tryBuild(RobotType.LANDSCAPER, Direction.NORTHWEST)).thenReturn(true);
		System.setOut(new PrintStream(outContent));
		//System.setOut(new PrintStream(ds1outContent));

		//rc.isReady() && rc.canBuildRobot(type, dir)
	}
	@Test
	public void testDSMakesLanscapersWhenTurnCountMod30EqualsTrue()throws GameActionException, NullPointerException{
		dsMock1.turnCount=29;
		dsMock1.comms.broadcastedCreation=true;
		dsMock1.takeTurn();
		//assertThat(ds1outContent.toString(), (containsString("made a landscaper")));
		assertThat(outContent.toString(), (containsString("made a landscaper")));
		assertEquals(2, 1+1);
	}
	@After
	public void TestDSMakesMinersTearDown() {
		System.setOut(originalOut);
	}



	private final ByteArrayOutputStream ds2outContent = new ByteArrayOutputStream();
	DesignSchool dsMock2;
	RobotController dsRC2;
	//test design school DOES NOT make lanscaper when turn count mode 30 is false
	@Before
	public void makedesignSchool2SetUp() throws GameActionException{
		dsRC2 = mock(RobotController.class);
		dsMock2 = new DesignSchool(dsRC2);

		// the first statement after the try doesn't execute for some reason
		when(dsMock2.tryBuild(RobotType.LANDSCAPER, Direction.NORTH)).thenReturn(true);
		when(dsMock2.tryBuild(RobotType.LANDSCAPER, Direction.NORTH)).thenReturn(true);
		when(dsMock2.tryBuild(RobotType.LANDSCAPER, Direction.NORTHEAST)).thenReturn(true);
		when(dsMock2.tryBuild(RobotType.LANDSCAPER, Direction.EAST)).thenReturn(true);
		when(dsMock2.tryBuild(RobotType.LANDSCAPER, Direction.SOUTHEAST)).thenReturn(true);
		when(dsMock2.tryBuild(RobotType.LANDSCAPER, Direction.SOUTH)).thenReturn(true);
		when(dsMock2.tryBuild(RobotType.LANDSCAPER, Direction.SOUTHWEST)).thenReturn(true);
		when(dsMock2.tryBuild(RobotType.LANDSCAPER, Direction.WEST)).thenReturn(true);
		when(dsMock2.tryBuild(RobotType.LANDSCAPER, Direction.NORTHWEST)).thenReturn(true);
		System.setOut(new PrintStream(ds2outContent));
	}
	@Test
	public void testDSDoesNotLandscaperssWhenTurnCountMod30EqualsTrue()throws GameActionException, NullPointerException{
		dsMock2.turnCount=30;
		dsMock2.comms.broadcastedCreation=true;
		dsMock2.takeTurn();
		//assertThat(ds2outContent.toString(), equalTo(""));
		assertThat(ds2outContent.toString(), not(containsString("made a landscaper")));

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
	/*
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
	*/

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
	public void testcheckIFSoupgone()throws GameActionException{
		// I think I was essentially trying to create my own mock class, so it seemed like it might be simpler to just use mockito
		//RobotController rc = new RobotControllerTester();
		Miner miner =  new Miner(rcMock);
		miner.checkIfSoupGone();
	}


	//case 1: test try refine returns true when
	@Before
	public void tryrefinesetup1() throws GameActionException{
		rcMock = mock(RobotController.class);
		minerMock = new Miner(rcMock);

		//mock methods
		// the first statement after the try doesn't execute for some reason
		//when(rcMock.isReady()).thenReturn(true);
		//when(rcMock.isReady()).thenReturn(true);
		//when(rcMock.canDepositSoup(Direction.NORTH)).thenReturn(true);
		when(rcMock.isReady()).thenReturn(true);
		when(rcMock.isReady()).thenReturn(true);
		when(rcMock.canDepositSoup(Direction.NORTH)).thenReturn(true);
	}
	@Test
	public void tryRefineReturnsTrueWhenIsReadyandcanDepositTrue()throws GameActionException, NullPointerException{
		Boolean result;
		result=minerMock.tryRefine(Direction.NORTH);
		assertThat(result, equalTo(true));
		//assertEquals(2, 1+1);
	}
	@After
	public void miner1teardown() {
		//trying to reset for next test.  doesn't actually work
		when(rcMock.isReady()).thenReturn(false);
		when(rcMock.isReady()).thenReturn(false);
		when(rcMock.canDepositSoup(Direction.NORTH)).thenReturn(false);
		rcMock=null;
		minerMock=null;
	}


	//case 2: test try refine returns false when
	@Before
	public void tryrefinesetup2() throws GameActionException{
		rcMock = mock(RobotController.class);
		minerMock2 = new Miner(rcMock);

		//mock methods
		// the first statement after the try doesn't execute for some reason
		when(rcMock.isReady()).thenReturn(false);
		when(rcMock.isReady()).thenReturn(false);
		when(rcMock.canDepositSoup(Direction.NORTH)).thenReturn(false);
	}
	@Test
	public void tryRefineReturnsFalseWhenIsReadyandcanDepositFalse()throws GameActionException, NullPointerException{
		Boolean result;
		result=minerMock2.tryRefine(Direction.NORTH);
		assertThat(result, equalTo(false));
		//assertEquals(2, 1+1);
	}
	@After
	public void miner2teardown() {
		rcMock=null;
		minerMock=null;

	}

	Miner tryMineMiner1;
	RobotController tryMineRC1;
	@Before
	public void tryMineWhenisReadyandCanMineTrueSetUp() throws GameActionException{
		tryMineRC1 = mock(RobotController.class);
		tryMineMiner1 = new Miner(tryMineRC1);

		//mock methods
		// the first statement after the try doesn't execute for some reason
		when(tryMineRC1.isReady()).thenReturn(true);
		when(tryMineRC1.isReady()).thenReturn(true);
		when(tryMineRC1.canMineSoup(Direction.NORTH)).thenReturn(true);

	}
	@Test
	public void testTryMineWhenisReadyandCanMineTrue()throws GameActionException, NullPointerException{
		Boolean result;
		result=tryMineMiner1.tryMine(Direction.NORTH);
		assertThat(result, equalTo(true));
		//assertEquals(2, 1+1);
	}

	Miner tryMineMiner2;
	RobotController tryMineRC2;
	@Before
	public void tryMineWhenisReadyandCanMineFalseSetUp() throws GameActionException{
		tryMineRC2 = mock(RobotController.class);
		tryMineMiner2 = new Miner(tryMineRC2);

		//mock methods
		// the first statement after the try doesn't execute for some reason
		when(tryMineRC2.isReady()).thenReturn(false);
		when(tryMineRC2.isReady()).thenReturn(false);
		when(tryMineRC2.canMineSoup(Direction.NORTH)).thenReturn(false);

	}
	@Test
	public void testTryMineWhenisReadyandCanMineFalse()throws GameActionException, NullPointerException{
		Boolean result;
		result=tryMineMiner2.tryMine(Direction.NORTH);
		assertThat(result, equalTo(false));
		//assertEquals(2, 1+1);
	}

	// ---------------------------------------------------------------------------------------
	// 					Fullfillment Center
	// ---------------------------------------------------------------------------------------
	FulFillmentCenter fullfillmentCent1;
	RobotController fullFillmentRC1;
	private final ByteArrayOutputStream fullfillmentOutContent1 = new ByteArrayOutputStream();

	@Before
	public void setupFullfillmentCenterTakesTurn() throws GameActionException{
		fullFillmentRC1 = mock(RobotController.class);
		fullfillmentCent1 = new FulFillmentCenter(fullFillmentRC1);

		Direction dir = null;

		when(fullFillmentRC1.isReady()).thenReturn(true);

		dir = Direction.NORTH;
		//when(fullfillmentCent1.tryBuild(RobotType.DELIVERY_DRONE, dir)).thenReturn(true);
		//when(fullfillmentCent1.tryBuild(RobotType.DELIVERY_DRONE, dir)).thenReturn(true);
		when(fullFillmentRC1.canBuildRobot(RobotType.DELIVERY_DRONE, dir)).thenReturn(true);
		when(fullFillmentRC1.canBuildRobot(RobotType.DELIVERY_DRONE, dir)).thenReturn(true);

		dir = Direction.NORTHWEST;
		//when(fullfillmentCent1.tryBuild(RobotType.DELIVERY_DRONE, dir)).thenReturn(true);
		when(fullFillmentRC1.canBuildRobot(RobotType.DELIVERY_DRONE, dir)).thenReturn(true);

		dir = Direction.NORTHEAST;
		//when(fullfillmentCent1.tryBuild(RobotType.DELIVERY_DRONE, dir)).thenReturn(true);
		when(fullFillmentRC1.canBuildRobot(RobotType.DELIVERY_DRONE, dir)).thenReturn(true);

		dir = Direction.SOUTH;
		//when(fullfillmentCent1.tryBuild(RobotType.DELIVERY_DRONE, dir)).thenReturn(true);
		when(fullFillmentRC1.canBuildRobot(RobotType.DELIVERY_DRONE, dir)).thenReturn(true);

		dir = Direction.SOUTHWEST;
		//when(fullfillmentCent1.tryBuild(RobotType.DELIVERY_DRONE, dir)).thenReturn(true);
		when(fullFillmentRC1.canBuildRobot(RobotType.DELIVERY_DRONE, dir)).thenReturn(true);

		dir =  Direction.SOUTHWEST;
		//when(fullfillmentCent1.tryBuild(RobotType.DELIVERY_DRONE, dir)).thenReturn(true);
		when(fullFillmentRC1.canBuildRobot(RobotType.DELIVERY_DRONE, dir)).thenReturn(true);

		dir = Direction.EAST;
		//when(fullfillmentCent1.tryBuild(RobotType.DELIVERY_DRONE, dir)).thenReturn(true);
		when(fullFillmentRC1.canBuildRobot(RobotType.DELIVERY_DRONE, dir)).thenReturn(true);

		dir = Direction.WEST;
		//when(fullfillmentCent1.tryBuild(RobotType.DELIVERY_DRONE, dir)).thenReturn(true);
		when(fullFillmentRC1.canBuildRobot(RobotType.DELIVERY_DRONE, dir)).thenReturn(true);
		System.setOut(new PrintStream(fullfillmentOutContent1));
	}
	@Test
	public void testFullfillmentCenterTakesTurn()throws GameActionException, NullPointerException{
		Boolean result;
		fullfillmentCent1.turnCount=9;
		result=fullfillmentCent1.takeTurn();
		assertThat(result, equalTo(false));
		assertThat(outContent.toString(), containsString("created a drone"));
		//assertEquals(2, 1+1);
	}
	@After
	public void tearDownFullfillmentCenterTakesTurn() {
		System.setOut(originalOut);
	}



	@Test
	public void testSanity() {
		assertEquals(2, 1+1);
		//assertEquals(2, 1);
	}

}
/*
		Direction dir = null;
		dir = Direction.NORTH;
		dir = Direction.NORTHWEST;
		dir = Direction.NORTHEAST;
		dir = Direction.SOUTH;
		dir = Direction.SOUTHWEST;
		dir =  Direction.SOUTHWEST;
		dir = Direction.EAST;
		dir = Direction.WEST;

 */