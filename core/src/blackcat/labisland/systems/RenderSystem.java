package blackcat.labisland.systems;

import javax.swing.text.Position;

import blackcat.labisland.components.Box2dComponent;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class RenderSystem extends EntitySystem
{
    private ComponentMapper<Box2dComponent> b2d_mapper = null;
    
    private OrthographicCamera camera = null;
    private ShapeRenderer renderer = null;
    
    
    @SuppressWarnings("unchecked")
    public RenderSystem(OrthographicCamera camera)
    {
        super(Filter.allComponents(Box2dComponent.class));
        
        this.camera = camera;
        
        renderer = new ShapeRenderer();
        renderer.begin(ShapeType.Filled);
        renderer.setColor(0.5f, 0.5f, 1.0f, 1.0f);
    }

    @Override
    public void initialize()
    {
        b2d_mapper = world.getMapper(Box2dComponent.class);
    }

    @Override
    protected void processEntities(Array<Entity> entities)
    {
        // Clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Update camera and shape renderer
        camera.update();
//        renderer.setProjectionMatrix(camera.combined);
//        renderer.identity();
        
//        // Render each entity
//        for (Entity entity : entities)
//        {
//            Box2dComponent box = b2d_mapper.get(entity);
//            
//            Vector2 pos = box.getBody().getPosition();
//            renderer.circle(pos.x, pos.y, 2f);
//        }
    }
}
