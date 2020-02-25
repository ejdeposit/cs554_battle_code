package MyPlayer;

import battlecode.common.*;

public class FulFillmentCenter extends Building {
    int numDrones = 0;
    public FulFillmentCenter(RobotController r) throws GameActionException {
        super(r);
        System.out.println("HI THERE");
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();
        if(turnCount%50 ==0) {
            for (Direction dir : Util.directions)
                if(tryBuild(RobotType.DELIVERY_DRONE, dir)){
                    System.out.println("created a drone");
                }
        }
        else{
            System.out.println("Doing Nothing");
        }
        System.out.println("I do this every turn.");
    }

}
