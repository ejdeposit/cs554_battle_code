package MyPlayer;

import battlecode.common.*;

public class HQ extends Building {
    static int numMiners = 0;

    public HQ(RobotController r) throws GameActionException{
        super(r);
    }

    @Override
    public boolean takeTurn() throws GameActionException {
        super.takeTurn();
/*
        if(turnCount == 1) {
            comms.sendHqLoc(rc.getLocation());
        }

 */
        if(numMiners < 5) {
            for (Direction dir : Util.directions)
                if(tryBuild(RobotType.MINER, dir)){
                    numMiners++;
                    System.out.println("I made a miner!");
                }
        }
        return false;
    }
}