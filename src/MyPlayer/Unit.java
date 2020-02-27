package MyPlayer;
import battlecode.common.*;

public class Unit extends Robot {

    Navigation nav;

    MapLocation hqLoc;

    public Unit(RobotController r) {
        super(r);
        nav = new Navigation(rc);
    }

    public boolean takeTurn() throws GameActionException {
        super.takeTurn();

        findHQ();
        return false;
    }

    public void findHQ() throws GameActionException {
        if (hqLoc == null) {
            // search surroundings for HQ
            RobotInfo[] robots = rc.senseNearbyRobots();
            for (RobotInfo robot : robots) {
                if (robot.type == RobotType.HQ && robot.team == rc.getTeam()) {
                    hqLoc = robot.location;
                }
            }
            /*
            if(hqLoc == null) {
                // if still null, search the blockchain
                hqLoc = comms.getHqLocFromBlockchain();
            }

             */
        }
    }
}