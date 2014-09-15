package blackcat.labisland;

import blackcat.labisland.systems.PlayerInputSystem;
import blackcat.labisland.systems.RenderSystem;

import com.artemis.World;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class EscapeFromLabIslandGame extends Game
{
    static public final int DEFAULT_VIEWPORT_WIDTH = 800;
    static public final int DEFAULT_VIEWPORT_HEIGHT = 600;
    
    static public final float DEFAULT_SCENE_WIDTH = 10f;
    static public final float DEFAULT_SCENE_HEIGHT = 7.5f;
    static public final float DEFAULT_SCENE_ASPECT_RATIO = DEFAULT_SCENE_WIDTH / DEFAULT_SCENE_HEIGHT;
    
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
        resize(DEFAULT_VIEWPORT_WIDTH, DEFAULT_VIEWPORT_HEIGHT);
        
        createWorld();
        
        EntityFactory.createGround(world, b2d_world, DEFAULT_SCENE_WIDTH / 2, 0);
        
        EntityFactory.createPlatform(world, b2d_world, 2.0f, 1.0f);
        EntityFactory.createPlatform(world, b2d_world, 3.5f, 2.0f);
        EntityFactory.createPlatform(world, b2d_world, 5.0f, 3.0f);
        EntityFactory.createPlatform(world, b2d_world, 6.5f, 4.0f);
        EntityFactory.createPlatform(world, b2d_world, 8.0f, 5.0f);
        EntityFactory.createPlatform(world, b2d_world, 9.5f, 6.0f);
        
        EntityFactory.createPlayer(world, b2d_world, 0.5f, 2.0f);
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
        // Target viewport is 100x75, so scale the first dimension to "clip"
        // to that ratio, and let the other one float larger
        
        float aspect = (float) width / (float) height;
        
        float scene_width = DEFAULT_SCENE_WIDTH;
        float scene_height = DEFAULT_SCENE_HEIGHT;
        
        Vector2 default_scene_center = new Vector2(scene_width / 2, scene_height / 2);
        
        if (aspect > DEFAULT_SCENE_ASPECT_RATIO)
        {
            // Too wide
            scene_width = aspect * scene_height;
        }
        else
        {
            // Too tall
            scene_height = scene_width / aspect;
        }
        
        Vector2 scene_center = new Vector2(scene_width / 2, scene_height / 2);
        Vector2 slide = default_scene_center.sub(scene_center);
        
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.setToOrtho(false, scene_width, scene_height);
        camera.translate(slide);
    }
}
