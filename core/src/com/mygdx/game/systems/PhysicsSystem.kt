package com.mygdx.game.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector3
import com.mygdx.game.components.BodyComp
import com.mygdx.game.components.BodyRenderComp
import com.mygdx.game.components.PositionComp
import org.dyn4j.dynamics.Body
import org.dyn4j.dynamics.World
import org.dyn4j.geometry.Circle
import org.dyn4j.geometry.MassType
import org.dyn4j.geometry.Rectangle
import org.dyn4j.geometry.Vector2
import com.badlogic.gdx.graphics.OrthographicCamera
import java.util.*


class PhysicsSystem : EntityListener,IteratingSystem(Family.all(PositionComp::class.java, BodyComp::class.java).get()) {

    val world: World = World()
    val camera:Camera
    init {
        world.gravity = Vector2(0.0, -98.0)
        camera = OrthographicCamera()
        val floor = Body()
        floor.addFixture(Rectangle(Gdx.graphics.width.toDouble(), 100.0))
        floor.setMass(MassType.INFINITE)
        floor.transform.translation = Vector2(Gdx.graphics.width/2.0, -50.0)
        world.addBody(floor)
        //add inputadapter
        Gdx.input.inputProcessor = object : InputAdapter() {
            override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean  {
                println("screenX: $screenX , screenY: $screenY")
//                val clickPosition = camera.unproject(Vector3(screenX.toFloat(), screenY.toFloat(), 0f))
                var entity = Entity()
                    var body = Body()
                    val radius = 10.0
                    body.addFixture(Circle(radius))
                    body.setMass(MassType.NORMAL)
                    body.transform.translationX = screenX.toDouble()
                    body.transform.translationY = screenY.toDouble() //wrong value
                    entity.add(BodyComp(body))
                    entity.add(PositionComp(body.transform.translationX.toFloat(), body.transform.translationY.toFloat()))
                    entity.add(BodyRenderComp(Circle(radius)))
                    engine.addEntity(entity)
                return true
            }
            override fun touchUp(x: Int, y: Int, pointer: Int, button: Int): Boolean {
                // My code
                return true
            }
        }
    }

    override fun entityAdded(entity: Entity?) {
        val bodyComp = entity?.getComponent(BodyComp::class.java)
        world.addBody(bodyComp?.body)

   }

    override fun entityRemoved(entity: Entity?) {
        val bodyComp = entity?.getComponent(BodyComp::class.java)
        world.removeBody(bodyComp?.body)
    }

    override fun update(dt: Float){
        world.updatev(dt.toDouble())
        engine.getEntitiesFor(family).forEach{ entity ->
            val positionComp = entity.getComponent(PositionComp::class.java)
            val bodyComp = entity.getComponent(BodyComp::class.java)
//            println(bodyComp.body.transform.translation)
            positionComp.x = bodyComp.body.transform.translationX.toFloat()
            positionComp.y = bodyComp.body.transform.translationY.toFloat()

        }
    }

    override fun processEntity(entity: Entity, dt: Float) {

    }
}
