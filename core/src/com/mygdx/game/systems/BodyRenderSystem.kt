package com.mygdx.game.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.components.BodyComp
import com.mygdx.game.components.BodyRenderComp
import com.mygdx.game.components.PositionComp
import org.dyn4j.geometry.Circle

class BodyRenderSystem : EntityListener, IteratingSystem(Family.all(BodyRenderComp::class.java, PositionComp::class.java, BodyComp::class.java).get()) {
    var shapeRenderer: ShapeRenderer
    val camera = OrthographicCamera()
    val viewport = FitViewport(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat(), camera)

    init {
        shapeRenderer = ShapeRenderer()
    }

    override fun entityRemoved(entity: Entity?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun entityAdded(entity: Entity?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(dt: Float){
        //shapeRenderer.projectionMatrix = camera.combined
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.setColor(1.0f, 1.0f, 1.0f, 1.0f)
        engine.getEntitiesFor(family).forEach {

            val shape = it.getComponent(BodyRenderComp::class.java).shape
            val pos = it.getComponent(PositionComp::class.java)
            if(shape is Circle) {
                var radius = shape.radius
                shapeRenderer.circle(pos.x, pos.y, radius.toFloat())
            }
        }
        shapeRenderer.end()

    }
}