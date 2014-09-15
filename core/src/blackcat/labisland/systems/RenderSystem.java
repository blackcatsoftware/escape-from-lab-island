package blackcat.labisland.systems;

import blackcat.labisland.components.Position;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

public class RenderSystem extends EntitySystem
{
    private ComponentMapper<Position> pos_mapper = null;
    
    private OrthographicCamera camera = null;
    private ShapeRenderer renderer = null;
    
    
    @SuppressWarnings("unchecked")
    public RenderSystem(OrthographicCamera camera)
    {
        super(Filter.allComponents(Position.class));
        
        this.camera = camera;
        
        renderer = new ShapeRenderer();
        renderer.begin(ShapeType.Filled);
        renderer.setColor(0.5f, 0.5f, 1.0f, 1.0f);
    }

    @Override
    public void initialize()
    {
        pos_mapper = world.getMapper(Position.class);
    }

    @Override
    protected void processEntities(Array<Entity> entities)
    {
        // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Update camera and shape renderer
        camera.update();
        renderer.setProjectionMatrix(camera.combined);
        renderer.identity();
        
        // Render each entity
        for (Entity entity : entities)
        {
            Position pos = pos_mapper.get(entity);
            renderer.circle(pos.getX(), pos.getY(), 12.0f);
        }
    }
}
