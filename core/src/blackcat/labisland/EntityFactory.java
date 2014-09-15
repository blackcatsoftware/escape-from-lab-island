package blackcat.labisland;

import blackcat.labisland.components.Box2dComponent;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class EntityFactory
{
    static public Entity createPlayer(World world, com.badlogic.gdx.physics.box2d.World b2d_world, float x, float y)
    {
        Entity entity = world.createEntity();
        
        // Box2d
        {
            // Body
            BodyDef body_def = new BodyDef();
            body_def.type = BodyType.DynamicBody;
            body_def.position.set(x, y);
            
            Body body = b2d_world.createBody(body_def);
            body.setUserData(entity);
            
            // Fixture
            CircleShape circle = new CircleShape();
            circle.setRadius(0.1f);
            
            FixtureDef fix_def = new FixtureDef();
            fix_def.shape = circle;
            fix_def.density = 1.0f;
            fix_def.friction = 1.0f;
            fix_def.restitution = 0.3f;
            
            body.createFixture(fix_def);
            
            circle.dispose();
            
            // Entity
            Box2dComponent box2d = new Box2dComponent(body);
            
            entity.addComponent(box2d);
        }
        
        world.addEntity(entity);
        
        return entity;
    }
    
    static public Entity createGround(World world, com.badlogic.gdx.physics.box2d.World b2d_world, float x, float y)
    {
        Entity entity = world.createEntity();
        
        // Box2d
        {
            // Body
            BodyDef body_def = new BodyDef();
            body_def.type = BodyType.StaticBody;
            body_def.position.set(x, y);
            
            Body body = b2d_world.createBody(body_def);
            body.setUserData(entity);
            
            // Fixture
            PolygonShape rect = new PolygonShape();
            rect.setAsBox(EscapeFromLabIslandGame.DEFAULT_SCENE_WIDTH, 0.5f);
            
            body.createFixture(rect, 0.0f);
            
            rect.dispose();
            
            // Entity
            Box2dComponent box2d = new Box2dComponent(body);
            
            entity.addComponent(box2d);
        }
        
        world.addEntity(entity);
        
        return entity;
    }
    
    static public Entity createPlatform(World world, com.badlogic.gdx.physics.box2d.World b2d_world, float x, float y)
    {
        Entity entity = world.createEntity();
        
        // Box2d
        {
            // Body
            BodyDef body_def = new BodyDef();
            body_def.type = BodyType.StaticBody;
            body_def.position.set(x, y);
            
            Body body = b2d_world.createBody(body_def);
            body.setUserData(entity);
            
            // Fixture
            PolygonShape rect = new PolygonShape();
            rect.setAsBox(0.5f, 0.05f);
            
            body.createFixture(rect, 0.0f);
            
            rect.dispose();
            
            // Entity
            Box2dComponent box2d = new Box2dComponent(body);
            
            entity.addComponent(box2d);
        }
        
        world.addEntity(entity);
        
        return entity;
    }
    
    
    private EntityFactory() {}
}
