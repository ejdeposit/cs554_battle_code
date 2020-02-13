package MyPlayer;

import battlecode.common.*;

public class Drone extends Unit {
    public Drone(RobotController r) {
        super(r);
    }

    public void takeTurn() throws GameActionException {
        super.takeTurn();

        Team enemy = rc.getTeam().opponent();
        if (!rc.isCurrentlyHoldingUnit()) {

            RobotInfo[] enemiesInRange = rc.senseNearbyRobots(GameConstants.DELIVERY_DRONE_PICKUP_RADIUS_SQUARED, enemy);
            //RobotInfo[] enemiesInRange = rc.senseNearbyRobots(GameConstants.NET_GUN_SHOOT_RADIUS_SQUARED, enemy);
            if (enemiesInRange.length > 0) {
                //pick up a first enemy robot within striking range
                rc.pickUpUnit(enemiesInRange[0].getID());
                System.out.println("I picked up" + enemiesInRange[0].getID() + "!");

            }
        }
        else {
            rc.move(Util.randomDirection());
        }
        //rc.move(rc.getLocation().directionTo(new MapLocation(10, 10)));
        //rc.pickUpUnit(0);
        //System.out.println("I picked up" + enemiesInRange[0].getID());
    }

    //if (enemiesInRange.length > 0) {
    // Pick up a first robot within range
    //rc.pickUpUnit(enemiesInRange[0].getID());
    //System.out.println("I picked up " + enemiesInRange[0].getID() + "!");



}
