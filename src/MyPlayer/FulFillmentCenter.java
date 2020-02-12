package MyPlayer;

import battlecode.common.*;

public class FulFillmentCenter extends Building {
    int numDrones = 0;
    public FulFillmentCenter(RobotController r) throws GameActionException {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        comms.broadcastFulfillmentCenterCreation(rc.getLocation());
        comms.sendFulfillmentLoc(rc.getLocation());

        if(true) {
            for (Direction dir : Util.directions)
                if(tryBuild(RobotType.DELIVERY_DRONE, dir)){
                    System.out.println("created a drone");
                }
        }



    }

}
