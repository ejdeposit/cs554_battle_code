package MyPlayer;
import battlecode.common.*;

public class Building extends Robot {

    public Building(RobotController r) throws GameActionException {
        super(r);
        // building specific setup here
    }

    public boolean takeTurn() throws GameActionException {
        super.takeTurn();
        return false;
    }
}