package MyPlayer;

import battlecode.common.*;

public class Drone extends Unit {
    boolean haveCow = false;

    public Drone(RobotController r) {
        super(r);
    }

    @Override
    public boolean takeTurn() throws GameActionException {
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
            pickupcow();
        } else {
            rc.move(Util.randomDirection());
        }
        //find cow

        return false;
    }


    public boolean pickupcow() throws GameActionException {
        MapLocation cowloc = null;
        int cowid = -1;
        RobotInfo[] robots = rc.senseNearbyRobots(rc.getCurrentSensorRadiusSquared());
        for (RobotInfo e : robots) {
            if (e.type == RobotType.COW) {
                cowid=e.getID();
                cowloc = new MapLocation(e.getLocation().x, e.getLocation().y);
                break;
            }
        }
        if (cowid >= 0) {
            if (rc.getLocation().distanceSquaredTo(cowloc) <= 2) {
                //pick up cow
                if (rc.canPickUpUnit(cowid)) {
                    rc.pickUpUnit(cowid);
                    haveCow = true;
                    return true;
                }
            } else {
                nav.goTo(cowloc);
                return false;
            }
        }


        //rc.move(rc.getLocation().directionTo(new MapLocation(10, 10)));
        //rc.pickUpUnit(0);
        //System.out.println("I picked up" + enemiesInRange[0].getID());

        //if (enemiesInRange.length > 0) {
        // Pick up a first robot within range
        //rc.pickUpUnit(enemiesInRange[0].getID());
        //System.out.println("I picked up " + enemiesInRange[0].getID() + "!");

        return true;
    }
}

