package com.mygdx.game

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.ashley.core.PooledEngine
import com.mygdx.game.components.BodyComp
import com.mygdx.game.components.BodyRenderComp
import com.mygdx.game.components.PositionComp
import com.mygdx.game.systems.BodyRenderSystem
import com.mygdx.game.systems.PhysicsSystem
import org.dyn4j.dynamics.Body
import org.dyn4j.geometry.Circle
import org.dyn4j.geometry.MassType
import java.util.*


class MyGdxGame : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var img: Texture

    private var engine = PooledEngine()
    private lateinit var physicsSys: PhysicsSystem
    private lateinit var bodyRenderSys: BodyRenderSystem



    override fun create() {
        batch = SpriteBatch()
        img = Texture("cupofTea.png")

        physicsSys = PhysicsSystem()
        bodyRenderSys = BodyRenderSystem()

        engine.addSystem(physicsSys)
        engine.addSystem(bodyRenderSys)
        engine.addEntityListener(physicsSys.family, physicsSys)

        val rand = Random()
        for(i in 1..100) {
            var body = Body()
            var entity = Entity()

            val radius = rand.nextDouble()*20.0 + 5.0
            body.addFixture(Circle(radius))
            body.setMass(MassType.NORMAL)
            body.transform.translationX = Gdx.graphics.width*rand.nextDouble()
            body.transform.translationY = Gdx.graphics.height*rand.nextDouble()+25.0
            //body.linearVelocity.set(10.0, 0.0)

            entity.add(BodyComp(body))
            entity.add(PositionComp(body.transform.translationX.toFloat(), body.transform.translationY.toFloat()))
            entity.add(BodyRenderComp(Circle(radius)))
            engine.addEntity(entity)
        }

    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        engine.update(Gdx.graphics.deltaTime)

        //batch.begin()
        //batch.draw(img, 0f, 0f)
        //batch.end()
    }

    override fun dispose() {
        batch.dispose()
        img.dispose()
    }
}
