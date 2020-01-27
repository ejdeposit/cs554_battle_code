package MyPlayer;

import battlecode.common.*;

public class Refinery extends Building {
    public Refinery(RobotController r) throws GameActionException {
        super(r);
    }

    @Override
    public void takeTurn() throws GameActionException {
        super.takeTurn();

        for(Direction dir : Util.directions){
            if(tryBuild(RobotType.MINER,dir)) {
                System.out.println("made a miner");
            }
        }
    }
}