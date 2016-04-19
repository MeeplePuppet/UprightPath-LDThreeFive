package com.upright.ldthreefive.logic.levelobjects;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.utils.Array;
import com.upright.ldthreefive.logic.Level;

/**
 * Created by Stygian on 4/15/2016.
 */
public abstract class MobileLevelObject extends InteractableLevelObject {
    public CanSeeRaycastCallback canSeeRaycastCallback = new CanSeeRaycastCallback();
    public ShootRaycastCallback shootRaycastCallback = new ShootRaycastCallback();
    public Array<Vector2> visionVectors = new Array<Vector2>();
    public MobileLevelObjectAction action = MobileLevelObjectAction.STANDING;
    public boolean justAttacked = false;

    public abstract int getAwareness();

    public void damage(int damage) {
        super.damage(damage);
    }

    public abstract void decideAction(Level level);

    public abstract void performAction(Level level);

    public boolean canSee(Level level, Targetable targetable) {
        visionVectors.clear();
        Vector2 curLoc = getTargetLocation();
        Vector2 dir = new Vector2(0, 1).rotate(body.getAngle() * MathUtils.radiansToDegrees);
        for (int i = 1; i < getAwareness(); i++) {
            visionVectors.add(dir.cpy().rotate(i).scl(10).add(curLoc));
            visionVectors.add(dir.cpy().rotate(-i).scl(10).add(curLoc));
        }
        visionVectors.add(dir.cpy().scl(10).add(curLoc));
        for (Vector2 vector : visionVectors) {
            canSeeRaycastCallback.reset();
            level.world.rayCast(canSeeRaycastCallback, curLoc, vector);
            if (canSeeRaycastCallback.levelObject == targetable) {
                return true;
            }
        }
        return false;
    }

    public class CanSeeRaycastCallback implements RayCastCallback {
        public LevelObject levelObject;
        public float closest = 1f;

        public void reset() {
            levelObject = null;
            closest = 1f;
        }

        @Override
        public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
            LevelObject obj = (LevelObject) fixture.getBody().getUserData();
            if (obj.blocksVision() && fixture.getBody() != MobileLevelObject.this.body) {
                if (fraction < closest) {
                    levelObject = obj;
                    closest = fraction;
                }
            }
            return -1;
        }
    }

    public class ShootRaycastCallback implements RayCastCallback {
        public LevelObject levelObject;
        public float closest = 1f;
        public Vector2 impact;

        public void reset() {
            levelObject = null;
            closest = Float.MAX_VALUE;
            impact = null;
        }

        @Override
        public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
            LevelObject obj = (LevelObject) fixture.getBody().getUserData();
            if (obj.blocksMovement() && point.dst2(MobileLevelObject.this.getTargetLocation()) < closest) {
                levelObject = obj;
                closest = fraction;
                impact = point;
            }
            return -1;
        }
    }

    public int getCurAttackCoolDown() {
        return -1;
    }
}
