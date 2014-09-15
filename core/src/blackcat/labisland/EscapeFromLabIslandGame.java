package blackcat.labisland;

import blackcat.labisland.components.Position;
import blackcat.labisland.components.Velocity;
import blackcat.labisland.systems.DragSystem;
import blackcat.labisland.systems.MovementSystem;
import blackcat.labisland.systems.OutOfBoundsSystem;
import blackcat.labisland.systems.PlayerInputSystem;
import blackcat.labisland.systems.RenderSystem;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class EscapeFromLabIslandGame extends Game
{
    static public final int WIDTH = 800;
    static public final int HEIGHT = 600;
    
    
    private World world = null;
    private OrthographicCamera camera = null;
    
    
    private void createWorld()
    {
        world = new World();
        
        world.setSystem(new PlayerInputSystem());
        world.setSystem(new MovementSystem());
        world.setSystem(new OutOfBoundsSystem());
        world.setSystem(new DragSystem());
        
        world.setSystem(new RenderSystem(camera));
        
        world.initialize();
    }
    
    private void createEntity(float x, float y, float dx, float dy)
    {
        Entity entity = world.createEntity();
        
        Position pos = world.createComponent(Position.class);
        pos.setPosition(x, y);
        entity.addComponent(pos);
        
        Velocity vel = world.createComponent(Velocity.class);
        vel.setVelocity(dx, dy);
        entity.addComponent(vel);
        
        entity.addToWorld();
    }
    
    @Override
    public void create()
    {
        camera = new OrthographicCamera();
        
        createWorld();
        
        // TODO Move to EntityFactory
        createEntity(200, 200, PlayerInputSystem.MAX_VELOCITY_X, PlayerInputSystem.MAX_VELOCITY_Y);
    }

    @Override
    public void render()
    {
        world.setDelta(Gdx.graphics.getDeltaTime());
        world.process();
    }

    @Override
    public void resize(int width, int height)
    {
        float center_x = width / 2.0f;
        float center_y = height / 2.0f;
        
        camera.position.set(center_x, center_y, 0);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
    }
}
