package blackcat.labisland;

import blackcat.labisland.systems.PlayerInputSystem;
import blackcat.labisland.systems.RenderSystem;

import com.artemis.World;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class EscapeFromLabIslandGame extends Game
{
    static public final int WIDTH = 800;
    static public final int HEIGHT = 600;
    
    static public final float B2D_TIME_STEP = 1/60f;
    static public final int B2D_VELOCITY_ITERATIONS = 10;
    static public final int B2D_POSITION_ITERATIONS = 8;
    
    
    private World world = null;
    private OrthographicCamera camera = null;
    
    private Box2DDebugRenderer debug_renderer = null;
    private com.badlogic.gdx.physics.box2d.World b2d_world = null;
    private float b2d_time_accumulator = 0f;
    
    
    private void createWorld()
    {
        // Artemis
        world = new World();
        
        world.setSystem(new PlayerInputSystem());
        
        world.setSystem(new RenderSystem(camera));
        
        world.initialize();
        
        // Box2d
        b2d_world = new com.badlogic.gdx.physics.box2d.World(new Vector2(0.0f, -9.8f), true);
        debug_renderer = new Box2DDebugRenderer();
    }
    
    @Override
    public void create()
    {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 100, 75);
        
        createWorld();
        
        EntityFactory.createGround(world, b2d_world, 0, 0);
        
        EntityFactory.createPlatform(world, b2d_world, 20, 10);
        EntityFactory.createPlatform(world, b2d_world, 35, 20);
        EntityFactory.createPlatform(world, b2d_world, 50, 30);
        EntityFactory.createPlatform(world, b2d_world, 65, 40);
        EntityFactory.createPlatform(world, b2d_world, 80, 50);
        EntityFactory.createPlatform(world, b2d_world, 95, 60);
        
        EntityFactory.createPlayer(world, b2d_world, 5, 20);
    }
    
    /**
     * http://gafferongames.com/game-physics/fix-your-timestep/
     */
    private void doPhysicsStep(float delta_time)
    {
        // Max frame time to avoid spiral of death for slow machines
        float frame_time = Math.min(delta_time, 0.25f);
        
        b2d_time_accumulator += frame_time;
        while (b2d_time_accumulator >= B2D_TIME_STEP)
        {
            b2d_world.step(B2D_TIME_STEP, B2D_VELOCITY_ITERATIONS, B2D_POSITION_ITERATIONS);
            b2d_time_accumulator -= B2D_TIME_STEP;
        }
    }
    
    @Override
    public void render()
    {
        float delta_time = Gdx.graphics.getDeltaTime();
                
        world.setDelta(delta_time);
        world.process();
        
        doPhysicsStep(delta_time);
        
        debug_renderer.render(b2d_world, camera.combined);
    }

    @Override
    public void resize(int width, int height)
    {
        // TODO Intelligently pick aspect ratio
        float center_x = width / 2.0f;
        float center_y = height / 2.0f;
        
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.setToOrtho(false, 100, 75);
    }
}
