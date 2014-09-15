package blackcat.labisland.systems;

import blackcat.labisland.components.Box2dComponent;

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PlayerInputSystem extends EntityProcessingSystem implements InputProcessor
{
    static public final float MAX_VELOCITY_X = 5f;
    static public final float ACCELERATION_X = 0.5f;
    
    static public final float VELOCITY_Y_THRESHOLD = 0.01f;
    static public final float IMPULSE_Y = 0.15f;
    
    
    private ComponentMapper<Box2dComponent> b2d_mapper = null;
    
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;
    
    
    @SuppressWarnings("unchecked")
    public PlayerInputSystem()
    {
        super(Filter.allComponents(Box2dComponent.class));
    }
    
    @Override
    public void initialize()
    {
        Gdx.input.setInputProcessor(this);
        
        b2d_mapper = world.getMapper(Box2dComponent.class);
    }

    @Override
    protected void process(Entity entity)
    {
        Box2dComponent box = b2d_mapper.get(entity);
        
        // TODO Need better way to target player components only... Maybe InputControllableComponent extends Box2dComponent?
        if (BodyType.DynamicBody != box.getBody().getType()) return;
        
        Vector2 velocity = box.getBody().getLinearVelocity();
        
        if (up && Math.abs(velocity.y) < VELOCITY_Y_THRESHOLD)
        {
            box.getBody().applyLinearImpulse(new Vector2(0, IMPULSE_Y), box.getBody().getPosition(), true);
        }
        
        if (left && velocity.x > -MAX_VELOCITY_X)
        {
            box.getBody().applyForceToCenter(-ACCELERATION_X, 0, true);
        }
        
        if (right && velocity.x < MAX_VELOCITY_X)
        {
            box.getBody().applyForceToCenter(ACCELERATION_X, 0, true);
        }
    }
    
    @Override
    public boolean keyDown(int keycode)
    {
        switch (keycode)
        {
            case Input.Keys.LEFT :
            {
                left = true;
                break;
            }
            case Input.Keys.RIGHT :
            {
                right = true;
                break;
            }
            case Input.Keys.UP :
            {
                up = true;
                break;
            }
            case Input.Keys.DOWN :
            {
                down = true;
                break;
            }
        }
        
        return true;
    }

    @Override
    public boolean keyUp(int keycode)
    {
        switch (keycode)
        {
            case Input.Keys.LEFT :
            {
                left = false;
                break;
            }
            case Input.Keys.RIGHT :
            {
                right = false;
                break;
            }
            case Input.Keys.UP :
            {
                up = false;
                break;
            }
            case Input.Keys.DOWN :
            {
                down = false;
                break;
            }
        }
        
        return true;
    }

    @Override
    public boolean keyTyped(char character)
    {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button)
    {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY)
    {
        return false;
    }

    @Override
    public boolean scrolled(int amount)
    {
        return false;
    }
}
