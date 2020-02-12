package MyPlayer;

import battlecode.common.*;
import java.util.ArrayList;

public class Miner extends Unit {

    int numDesignSchools = 0;
    int numRefineries = 0;
    int numFulfillmentCenters = 0;
    ArrayList<MapLocation> soupLocations = new ArrayList<MapLocation>();

    public Miner(RobotController r) {
        super(r);
    }

    MapLocation refineryLoc= null;

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        /*
        int tempCount=0;
        tempCount = comms.getNewDesignSchoolCount();
        System.out.println("design school count" + Integer.toString(tempCount));
         */
        //change this to local variable since class attributes get reset or something
        int designSchoolCount=0;
        int fullfillmentCenterCount=0;
        //does this method get count of all design schools or just recently created ones?
        //answer just recently created.  so need a better way to see if design schools are there are not
        //have design schools broadcast location each turn like refineries.  they probably can spare the byte
        // codes to do that
        designSchoolCount += comms.getNewDesignSchoolCount();
        fullfillmentCenterCount += comms.getFulfillmentCenterCount();
        //getFulfillmentcenterCount not working try something else.
        MapLocation fulfillmentLocation=null;
        fulfillmentLocation=comms.getFulfillmentLocFromBlockchain();

        comms.updateSoupLocations(soupLocations);
        checkIfSoupGone();




        for (Direction dir : Util.directions)
            if (tryMine(dir)) {
                System.out.println("I mined soup! " + rc.getSoupCarrying());
                MapLocation soupLoc = rc.getLocation().add(dir);
                if (!soupLocations.contains(soupLoc)) {
                    comms.broadcastSoupLocation(soupLoc);
                }
            }
        // mine first, then when full, deposit
        for (Direction dir : Util.directions) {
            if (tryRefine(dir))
                System.out.println("I refined soup! " + rc.getTeamSoup());
        }



        //building fulfillment centers
        //modding turn count plus robot id so they don't all try to build at same time
        /*
        if (fullfillmentCenterCount < 1 && fulfillmentLocation == null && (turnCount+rc.getID())%4==0){
            if(tryBuild(RobotType.FULFILLMENT_CENTER, Util.randomDirection()))
                System.out.println("created a FULFILLMENT CENTER");
                //numFulfillmentCenters++;
        }
*/

        //use local variable instead of class attribute
        //modding turncount so they don't all build at same time
        if (designSchoolCount < 1 && (turnCount+rc.getID())%4==0){
        //if (turnCount%35 ==0){
            if(tryBuild(RobotType.DESIGN_SCHOOL, Util.randomDirection()))
                System.out.println("created a design school");
        }

        if (rc.getSoupCarrying() == RobotType.MINER.soupLimit) {
            if(refineryLoc != null) {
                if(nav.goTo(refineryLoc))
                    System.out.println("moved towards Refinery");
            }
            else{
                // time to go back to the HQ
                if(nav.goTo(hqLoc))
                    System.out.println("moved towards HQ");
            }
        } else if (soupLocations.size() > 0) {
            nav.goTo(soupLocations.get(0));
            rc.setIndicatorLine(rc.getLocation(), soupLocations.get(0), 255, 255, 0);
        } else if (nav.goTo(Util.randomDirection())) {
            // otherwise, move randomly as usual
            System.out.println("I moved randomly!");
        }
        // moved here so they try to build refinery before design school
        if(refineryLoc == null){
            refineryLoc=comms.getRefineryLocFromBlockchain();
            if(refineryLoc != null){
                numRefineries +=1;
                System.out.println("found refinery location");
            }
            else {
                // moved this here so miners only build refinery if one not found in blockchain
                //nav.goAwayFrom(hqLoc);
                if(tryBuild(RobotType.REFINERY, Util.randomDirection())) {
                    //if (tryBuild(RobotType.REFINERY, rc.getLocation().directionTo(hqLoc).opposite()))
                    System.out.println("created a refinery");
                }
            }
        }
    }


    /**
     * Attempts to mine soup in a given direction.
     *
     * @param dir The intended direction of mining
     * @return true if a move was performed
     * @throws GameActionException
     */
    boolean tryMine(Direction dir) throws GameActionException {
        if (rc.isReady() && rc.canMineSoup(dir)) {
            rc.mineSoup(dir);
            return true;
        } else return false;
    }

    /**
     * Attempts to refine soup in a given direction.
     *
     * @param dir The intended direction of refining
     * @return true if a move was performed
     * @throws GameActionException
     */
    boolean tryRefine(Direction dir) throws GameActionException {
        if (rc.isReady() && rc.canDepositSoup(dir)) {
            rc.depositSoup(dir, rc.getSoupCarrying());
            return true;
        } else return false;
    }

    void checkIfSoupGone() throws GameActionException {
        if (soupLocations.size() > 0) {
            MapLocation targetSoupLoc = soupLocations.get(0);
            if (rc.canSenseLocation(targetSoupLoc)
                    && rc.senseSoup(targetSoupLoc) == 0) {
                soupLocations.remove(0);
            }
        }
    }
}