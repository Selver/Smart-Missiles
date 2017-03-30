package selver.smartmissiles.ui;

import selver.smartmissiles.agents.AgentManager;
import selver.smartmissiles.agents.Obstacle;
import selver.smartmissiles.debug.Debug;
import selver.smartmissiles.physics.Vector2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyManager implements KeyListener, MouseListener {

    private  static Vector2 click = new Vector2();
    private  static Vector2 release = new Vector2();
    private static boolean pressed = false;
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyChar() == 'r')
        {
            AgentManager.resetAgents();
        }
        if (keyEvent.getKeyChar() == 'x')
        {
            AgentManager.obstacles.clear();
        }
        if (keyEvent.getKeyChar() == '+')
        {
//            gPanel.FPS +=10;
        }
        if (keyEvent.getKeyChar() == '-')
        {
//            gPanel.FPS -=10;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        if(mouseEvent.getButton() == MouseEvent.BUTTON1)
        {
            Debug.print("pressed button1");
            click.x = mouseEvent.getPoint().x;
            click.y = mouseEvent.getPoint().y;

        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseEvent.BUTTON1)
        {
            Debug.print("released button1");
            release.x = mouseEvent.getPoint().x;
            release.y = mouseEvent.getPoint().y;
        }
        Vector2 size;
        Vector2 pos= new Vector2();
        if (release.x > click.x && release.y > click.y)
        {
            pos.x = click.x;
            pos.y = click.y;
            size = new Vector2(release.x-click.x,release.y-click.y);
        } else  if (release.x < click.x && release.y > click.y)
        {
            pos.y = click.y;
            pos.x = release.x;
            size = new Vector2(click.x-release.x,release.y-click.y);
        } else  if (release.x > click.x && release.y < click.y)
        {
            pos.y = release.y;
            pos.x = click.x;
            size = new Vector2(release.x-click.x,click.y-release.y);
        }
        else
        {
            pos.y = release.y;
            pos.x = release.x;
            size = new Vector2(click.x-release.x,click.y-release.y);
        }
        Obstacle obstacle = new Obstacle(pos,size);
        Debug.print(obstacle.toString());
        AgentManager.addNewObstacle(obstacle);
        click = new Vector2();
        release = new Vector2();

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
