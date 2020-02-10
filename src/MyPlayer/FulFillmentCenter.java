package MyPlayer;

import battlecode.common.*;

public class FulFillmentCenter extends Building {
    int numDrones = 0;
    public FulFillmentCenter(RobotController r) throws GameActionException {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        if(turnCount%30 ==0) {
            for (Direction dir : Util.directions)
                if(tryBuild(RobotType.DELIVERY_DRONE, dir)){
                    System.out.println("created a drone");
                }
        }



    }

}
