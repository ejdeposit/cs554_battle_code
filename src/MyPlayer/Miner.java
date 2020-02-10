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
        //does this method get count of all design schools or just recently created ones?
        //answer just recently created.  so need a better way to see if design schools are there are not
        //have design schools broadcast location each turn like refineries.  they probably can spare the byte
        // codes to do that
        designSchoolCount += comms.getNewDesignSchoolCount();

        comms.updateSoupLocations(soupLocations);
        checkIfSoupGone();

        // not sure if this is best location for checking and building refineries. change later if needed
        //maybe need to add method similar to get design
        if(refineryLoc == null){
            refineryLoc=comms.getRefineryLocFromBlockchain();
            if(refineryLoc != null){
                numRefineries +=1;
            }
            else {
                // moved this here so miners only build refinery if one not found in blockchain
                if(tryBuild(RobotType.REFINERY, Util.randomDirection()))
                    System.out.println("created a refinery");
            }
        }


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
        if (turnCount%30 ==0){
            if(tryBuild(RobotType.FULFILLMENT_CENTER, Util.randomDirection()))
                System.out.println("created a FULFILLMENT CENTER");
                //numFulfillmentCenters++;
        }


        //use local variable instead of class attribute
        if (designSchoolCount < 1){
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