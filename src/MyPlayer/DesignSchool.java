package MyPlayer;

import battlecode.common.*;

public class DesignSchool extends Building {
    public DesignSchool(RobotController r) throws GameActionException{
        super(r);
    }

    @Override
    public boolean takeTurn() throws GameActionException {
        super.takeTurn();

        // will only actually happen if we haven't already broadcasted the creation
        //comms.broadcastDesignSchoolCreation(rc.getLocation());
        if(turnCount%30==0) {
            for (Direction dir : Util.directions) {
                if (tryBuild(RobotType.LANDSCAPER, dir)) {
                    System.out.println("made a landscaper");
                }
            }
        }
        return false;
    }
}