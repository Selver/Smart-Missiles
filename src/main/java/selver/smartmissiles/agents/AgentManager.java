package selver.smartmissiles.agents;

import selver.smartmissiles.debug.Debug;
import selver.smartmissiles.physics.Vector2;
import selver.smartmissiles.ui.gPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class AgentManager {

    public static final Vector2 targetPos = new Vector2(gPanel.width/2,60);

    public static final float targetRadious = 50;
    public static CopyOnWriteArrayList<Agent> agents = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Obstacle> obstacles = new CopyOnWriteArrayList<>();
    private static  CopyOnWriteArrayList<Agent> matingPool = new CopyOnWriteArrayList<>();
    public static Integer totalLife = 300;
    public static Integer DNASize = 40;
    public static Integer lifeCycle=0;
    private static Integer generation=0;
    private static String text="Year of randomness";
    public static void updateAgents()
    {

        if (lifeCycle.equals(totalLife))
        {
            generation++;
            //End of simulation - begining of genetic algorithm
            //1 calculate score of agents
            float max = 0;
            ArrayList<Float> scores=new ArrayList<>();
            scores.clear();
            float total = 0;
            for (Agent a:agents)
            {
                a.score=calculateScore(a);
                scores.add(a.score);
                total+=a.score;
                if (a.score>max ) max= a.score;

            }
            for (float score:scores)
            {
                score/=max*10;
            }
            text ="Year "+Integer.toString(generation)+" mean population fitness : " + Float.toString(total/agents.size());
            //2 Create mating pool with score as weight
            matingPool.clear();
            for (int i =0;i<agents.size();i++)
            {
                for(Integer k=0;k<scores.get(i);k++)
                {
                    matingPool.add(agents.get(i));
                }
            }
            //3 reproduction
            Integer size = agents.size();
            agents.clear();
            Debug.print(Integer.toString(agents.size()));
            for(Integer l=0;l<size;l++)
            {
                Integer random = (int)(Math.floor(Math.random()*(matingPool.size()-1)));
                Integer random2 = (int)(Math.floor(Math.random()*(matingPool.size()-1)));
                registerAgent(matingPool.get(random).mate(matingPool.get(random2)));
            }
            Debug.print(Integer.toString(agents.size()));
            lifeCycle = 0;

        }else{
            for (Agent a:agents)
            {
//                Debug.print("Updating");
                a.update();
            }
            lifeCycle++;
        }
//        Debug.print(Integer.toString(lifeCycle));
    }

    public static void draw(Graphics2D g)
    {
        drawTarget(g);
        drawObstacle(g);
        drawInfo(g);
        for (Agent a:agents) {
            a.draw(g);
        }
    }

    public static void registerAgent(Agent a)
    {
        agents.add(a);
    }

    public static void initAgents(Integer numMissiles)
    {
        for (int i = 0; i< numMissiles;i++)
        {
            registerAgent(new Missile(gPanel.width/2,gPanel.height-20,90,totalLife,DNASize));
        }

    }

    public static void resetAgents()
    {
        Integer size = agents.size();
        agents.clear();
        initAgents(size);
    }

    private static void drawTarget(Graphics2D g)
    {
        g.setPaint(new Color(200,0,0));
        g.fillOval((int)targetPos.x,(int)targetPos.y,(int)targetRadious,(int)targetRadious);
    }
    private static void drawObstacle(Graphics2D g)
    {
        for (Obstacle o:obstacles) {
            g.setPaint(new Color(250, 100, 150));
            g.fillRect((int) o.pos.x, (int) o.pos.y, (int) o.size.x, (int) o.size.y);
        }

    }
    private static void drawInfo(Graphics2D g)

    {
        Font font = new Font("Serif", Font.BOLD, 25);
        g.setFont(font);
        g.setPaint(new Color(0));
        g.drawString(text,15,30);
    }

    private static float calculateScore(Agent a)
    {
        float score;
        score = (gPanel.height- a.pos.distToAbs(targetPos))/10;
        if (a.collided)
        {
            score = score/5;
        }
        if (a.pos.distToAbs(targetPos)<targetRadious)
        {
            score =  score*3+(totalLife-((Missile)a).arrivalTick)*5f;
        }
        if (score <0 ) score= 0;
        return score;
    }

    public  static void addNewObstacle(Obstacle o)
    {
        obstacles.add(o);
    }
}
