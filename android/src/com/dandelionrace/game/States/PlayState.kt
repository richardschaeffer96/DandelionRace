package com.dandelionrace.game.States

import android.hardware.SensorManager.GRAVITY_EARTH
import android.os.AsyncTask
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.dandelionrace.game.dandelionrace
import com.dandelionrace.game.sprites.*

class PlayState(gsm: GameStateManager, finaltubes: ArrayList<GameTubes>, finalitems: ArrayList<GameItems>) : State(gsm) {

    private val TUBE_SPACING: Float = 125f
    //TUBE_COUNT: ANZAHL AN TUBES IM LEVEL
    private var counter: Int = 0

    private val bird: Bird
    private val bg: Texture
    private val win: Texture

    val app_width: Float
    val app_height: Float

    val tubes: ArrayList<GameTubes> = finaltubes
    val items: ArrayList<GameItems> = finalitems

    init {
        app_height = Gdx.app.graphics.height.toFloat()
        app_width = Gdx.app.graphics.width.toFloat()
        cam.setToOrtho(false, app_width, app_height)
        bird = Bird(100,500)
        bg = Texture("bg.png")
        win = Texture("win.jpg")

    }

    override fun handleInput() {
        if(Gdx.input.justTouched()){
            bird.jump()
        }
    }


    override fun update(dt: Float) {
        someTask(bird).execute()
        handleInput()
        bird.update(dt)
        cam.position.set(bird.position.x + 80, cam.viewportHeight/2,0f)


        if(cam.position.x - (cam.viewportWidth/2) > tubes[counter].posTopTube.x + tubes[counter].topTube.width){
            counter = counter.inc()
            System.out.println("COUNTER:"+counter)
        }

        for(tube in tubes){
            if(tube.collides(bird.getBound())){
                bird.status = "trapped"
                if (bird.position.y - 700 > tube.posBotTube.y) {
                    System.out.println("IST TOP TUBE!")
                    bird.trappedTube = "top"

                } else {
                    bird.trappedTube = "bot"
                }
            }
        }

        for(item in items){
            if(item.collides(bird.getBound())){
                item.posItem.set(-100f,-100f)
                item.bounds.set(-100f,-100f,0f,0f)

                if(item.effect == "EFFEKT IST 1"){
                    bird.status = "slow"
                }
                if(item.effect == "EFFEKT IST 2"){
                    bird.status = "speed"
                }
            }
        }



        /* !!! CODE FOR REPOSITION OF TUBES FOR DYNAMIC LEVEL !!!
        for(tube in tubes){
           // if(cam.position.x - (cam.viewportWidth/2) > tube.posTopTube.x + tube.topTube.width)
                tube.reposition(tube.posTopTube.x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT))
            if(tube.collides(bird.getBound()))
               // gsm.set(PlayState(gsm))
                gsm.set(MenuState(gsm))
        }
        */
        cam.update()

        if(counter==tubes.size){
            gsm.set(WinGame(gsm))
            dispose();
        }
    }

    override fun render(sb: SpriteBatch) {
        sb.projectionMatrix.set(cam.combined)
        //test
        sb.begin()
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2 ), 0f, dandelionrace.WIDTH.toFloat(), dandelionrace.HEIGHT.toFloat())

        for(tube in tubes) {
            sb.draw(tube.topTube, tube.posTopTube.x, tube.posTopTube.y)
            sb.draw(tube.bottomTube, tube.posBotTube.x, tube.posBotTube.y)

        }

        for(item in items){
            sb.draw(item.itemPic, item.posItem.x, item.posItem.y)
        }
        //sb.draw(bg, 0f, 0f, dandelionrace.WIDTH.toFloat(), dandelionrace.HEIGHT.toFloat())
        sb.draw(bird.bird, bird.position.x, bird.position.y)
        //sb.draw(tube.topTube, tube.posTopTube.x, tube.posTopTube.y)
        //sb.draw(tube.bottomTube, tube.posBotTube.x, tube.posBotTube.y)
        sb.end()
    }

    override fun dispose() {

    }

    class someTask(bird: Bird) : AsyncTask<Void, Void, String>(){

        val new_bird: Bird

        init {
            new_bird = bird

        }

        override fun doInBackground(vararg params: Void?): String? {
            if(new_bird.status=="trapped"){

                val xGrav = Gdx.input.accelerometerX / GRAVITY_EARTH
                val yGrav = Gdx.input.accelerometerY / GRAVITY_EARTH
                val zGrav = Gdx.input.accelerometerZ / GRAVITY_EARTH

                // gForce will be close to 1 when there is no movement.
                val gForce = Math.sqrt((xGrav * xGrav + yGrav * yGrav + zGrav * zGrav).toDouble()).toFloat()

                if (gForce>1.7) {
                    System.out.println("SHAKE DETECTED")
                    new_bird.status = "free"
                    if(new_bird.trappedTube == "bot"){
                        new_bird.position = Vector3(new_bird.position.x, new_bird.position.y + 100f, 0f)
                        new_bird.trappedTube = ""
                        new_bird.jump()
                    } else {
                        new_bird.position = Vector3(new_bird.position.x, new_bird.position.y - 400f, 0f)
                        new_bird.trappedTube = ""
                    }

                }

                return "Free"
            } else {
                /*

                //RECOGNITION OF AUDIO

                val audioBuffer = ShortArray(44100 * 1)

                val recorder = Gdx.audio.newAudioRecorder(44100, true)
                var blow_value: Int = 0
                var blow_string: String

                recorder.read(audioBuffer, 0, audioBuffer.size);
                for (s in audioBuffer) {
                    if (Math.abs(s.toInt()) > 500) {
                        blow_value = Math.abs(s.toInt());
                        //System.out.println("Blow Value= "+blow_value);
                    }
                }
                //val audioDevice = Gdx.audio.newAudioDevice(44100, true)
                //audioDevice.writeSamples(audioBuffer, 0, audioBuffer.size)
                //audioDevice.dispose()
                if (blow_value > 550) {
                    new_bird.jump()
                    //System.out.println("JUMP!!!!!!!!!!!!!!!!")
                }
                recorder.dispose()
                blow_string = blow_value.toString()
                //System.out.println("Blow STRING= "+blow_string);
                return blow_string
                */
                return "Free"

            }
        }

        override fun onPreExecute() {
            super.onPreExecute()
            // ...
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            // ...
        }
    }

}
