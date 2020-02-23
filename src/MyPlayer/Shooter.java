package MyPlayer;

import battlecode.common.*;

public class Shooter extends Building {

    public Shooter(RobotController rc) throws GameActionException {
        super(rc);
    }

    @Override
    public boolean takeTurn() throws GameActionException {
        while (true) {
            RobotInfo[] robots = rc.senseNearbyRobots(GameConstants.NET_GUN_SHOOT_RADIUS_SQUARED,rc.getTeam());
            for (RobotInfo r : robots){
                if(r.getType() == RobotType.DELIVERY_DRONE && rc.canShootUnit(r.ID)) {
                    rc.shootUnit(r.ID);
                    break;
            //super.takeTurn();

        // shoot nearby enemies
        //Team enemy = rc.getTeam().opponent();
       // RobotInfo[] enemiesInRange = rc.senseNearbyRobots(GameConstants.NET_GUN_SHOOT_RADIUS_SQUARED, enemy);

        //for (RobotInfo e : enemiesInRange) {
          //  if (e.type == RobotType.DELIVERY_DRONE) {
               // if (rc.canShootUnit(e.ID)){
                  //  rc.shootUnit(e.ID);
                  //  break;
                }
            }
            Clock.yield();
        }
    }
}
