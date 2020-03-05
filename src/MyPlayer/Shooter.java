package MyPlayer;

import battlecode.common.*;

public class Shooter extends Building {

    public Shooter(RobotController r) throws GameActionException {
        super(r);
    }

    @Override
    public boolean takeTurn() throws GameActionException {
        super.takeTurn();

        // shoot nearby enemies
        Team enemy = rc.getTeam().opponent();
        //Team enemy = rc.getTeam();
        RobotInfo[] enemiesInRange = rc.senseNearbyRobots(GameConstants.NET_GUN_SHOOT_RADIUS_SQUARED, enemy);

        if (enemiesInRange.length > 0) {
            for (int i = 0; i < enemiesInRange.length; i++) {

                if (rc.canShootUnit(enemiesInRange[i].getID())) {
                    rc.shootUnit(enemiesInRange[i].getID());
                    System.out.println("HQ shot down" + enemiesInRange[0].getID());
                }
            }
        }
        return false;
    }
}




    //    while (true) {
    //        RobotInfo[] robots = rc.senseNearbyRobots(GameConstants.NET_GUN_SHOOT_RADIUS_SQUARED,rc.getTeam());
    //        for (RobotInfo r : robots){
    //            if(r.getType() == RobotType.DELIVERY_DRONE && rc.canShootUnit(r.ID)) {
     //               rc.shootUnit(r.ID);
     //               break; //
            //super.takeTurn();

        // shoot nearby enemies
        //Team enemy = rc.getTeam().opponent();
       // RobotInfo[] enemiesInRange = rc.senseNearbyRobots(GameConstants.NET_GUN_SHOOT_RADIUS_SQUARED, enemy);

        //for (RobotInfo e : enemiesInRange) {
          //  if (e.type == RobotType.DELIVERY_DRONE) {
               // if (rc.canShootUnit(e.ID)){
                  //  rc.shootUnit(e.ID);
                  //  break;

