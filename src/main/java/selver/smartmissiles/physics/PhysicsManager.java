package selver.smartmissiles.physics;

import selver.smartmissiles.agents.Agent;
import selver.smartmissiles.agents.AgentManager;
import selver.smartmissiles.agents.Obstacle;
import selver.smartmissiles.ui.gPanel;

import java.util.concurrent.CopyOnWriteArrayList;

public class PhysicsManager {
    private static CopyOnWriteArrayList<Agent> agents = AgentManager.agents;
    public final static Vector2 gravity = new Vector2(0, 0.0098f);
    private static float maxSpeed = 4;

    static public void updatePhysics() {
        for (Agent a : agents) {
            if (a.hasPhysics && !a.collided) {
                applyStaticForces(a);
                Vector2 acc;
                if (a.mass != 0) {
                    acc = a.force.scalarDiv(a.mass);
                } else {
                    acc = a.force;
                }
                a.vel = a.vel.add(acc);
                if (a.vel.magnitude() > maxSpeed) {
                    a.vel = a.vel.setMagnitude(maxSpeed);
                }
                a.pos = a.pos.add(a.vel);
                if (a.pos.x > gPanel.width || a.pos.x < 0 || a.pos.y > gPanel.height || a.pos.y < 0) {
                    a.collided = true;
                }
                if (agentCollided(a, AgentManager.targetPos, AgentManager.targetRadious, AgentManager.targetRadious)) {
                    a.pos.x = AgentManager.targetPos.x + 15;
                    a.pos.y = AgentManager.targetPos.y + 15;
                    a.hasPhysics = false;
                }
                for (Obstacle o:AgentManager.obstacles) {
                    if (agentCollided(a,o.pos,o.size.y,o.size.x))
                    {
                        a.collided = true;
                        break;
                    }
                }

                a.force = new Vector2();
            }
        }
    }

    private static void applyStaticForces(Agent a) {
        a.force = a.force.add(gravity.scalarMult(a.mass));
    }

    public static boolean agentCollided(Agent a, Vector2 pos, float height, float width)
    {
        return  (a.pos.x>pos.x &&
                a.pos.x<(pos.x+width) &&
                a.pos.y>pos.y &&
                a.pos.y<pos.y+height);

    }

}
