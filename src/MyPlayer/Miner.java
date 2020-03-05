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

    int designSchoolsNearBy = 0;
    int refineriesNearBy = 0;
    int fullfillmentCenterNearBy = 0;
    int netgunNearBy = 0;
    int vaporatorNearBy = 0;

    @Override
    public boolean takeTurn() throws GameActionException {
        super.takeTurn();

        //look at nearby robots and record robots that are seen
        //These could be changed to arrays of id's if we wanted to keep track of which was seen
        //int provides somewhat of a measure of the frequenfy of the robots seen

        RobotInfo robotInfo[];
        robotInfo = rc.senseNearbyRobots(-1, rc.getTeam());
        for (RobotInfo robo: robotInfo){
            if(robo.type == RobotType.REFINERY){
                refineriesNearBy += 1;
                refineryLoc = robo.getLocation();
            }
            else if(robo.type == RobotType.DESIGN_SCHOOL){
                designSchoolsNearBy += 1;
            }
            else if(robo.type == RobotType.FULFILLMENT_CENTER){
                fullfillmentCenterNearBy += 1;
            }
            else if(robo.type == RobotType.NET_GUN) {
                netgunNearBy +=1 ;
            }
            else if(robo.type == RobotType.VAPORATOR) {
                vaporatorNearBy +=1 ;
            }
        }

       // numDesignSchools += comms.getNewDesignSchoolCount();

        comms.updateSoupLocations(soupLocations);
        checkIfSoupGone();

        /*
        if(refineryLoc == null){
            refineryLoc=comms.getRefineryLocFromBlockchain();
            if(refineryLoc != null){
                numRefineries +=1;
            }
        }
        */

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

        // not sure why this if statement is causing miners not to build refineries
        //if (numRefineries < 1){
        if (refineriesNearBy < 1){
            if(tryBuild(RobotType.REFINERY, Util.randomDirection()))
                System.out.println("created a refinery");
        }
        //building fulfillment centers

        if (fullfillmentCenterNearBy < 1){
            if(tryBuild(RobotType.FULFILLMENT_CENTER, Util.randomDirection()))
                System.out.println("created a FULFILLMENT CENTER");
                //numFulfillmentCenters++;
        }


        //this iff statement does not limit design schools correctly for some reason
        //if (numDesignSchools < 1){
        if (designSchoolsNearBy < 1){
            if(tryBuild(RobotType.DESIGN_SCHOOL, Util.randomDirection()))
                System.out.println("created a design school");
        }

        if(netgunNearBy < 1) {
            if(tryBuild(RobotType.NET_GUN,Util.randomDirection()))
                System.out.println("created a netgun");
        }

        if(vaporatorNearBy < 1) {
            if(tryBuild(RobotType.VAPORATOR,Util.randomDirection()))
                System.out.println("created a vaporator");
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
        return false;
    }

    /**
     * Attempts to mine soup in a given direction.
     *
     * @param dir The intended direction of mining
     * @return true if a move was performed
     * @throws GameActionException
     */
    boolean tryMine(Direction dir) throws GameActionException {
        boolean isready=rc.isReady();
        boolean canMine=rc.canMineSoup(dir);
        if (isready && canMine) {
        //if (rc.isReady() && rc.canMineSoup(dir)) {
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