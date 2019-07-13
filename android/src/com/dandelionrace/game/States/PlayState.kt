package com.dandelionrace.game.States

import android.hardware.SensorManager.GRAVITY_EARTH
import android.os.AsyncTask
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.dandelionrace.game.dandelionrace
import com.dandelionrace.game.sprites.Bird
import com.dandelionrace.game.sprites.GameItems
import com.dandelionrace.game.sprites.GameTubes
import com.dandelionrace.game.sprites.Tube

class PlayState(gsm: GameStateManager, finaltubes: ArrayList<GameTubes>, finalitems: ArrayList<GameItems>) : State(gsm) {

    private val TUBE_SPACING: Float = 125f
    //TUBE_COUNT: ANZAHL AN TUBES IM LEVEL
    private var counter: Int = 0

    private val bird: Bird
    private val bg: Texture
    private val secondBg: Texture
    private val win: Texture

    private var bgStartOld: Float
    private var bgEndOld: Float

    private var bgStart: Float
    private var bgEnd: Float

    val app_width: Float
    val app_height: Float

    var nextBg: Boolean = false

    val tubes: ArrayList<GameTubes> = finaltubes
    val items: ArrayList<GameItems> = finalitems

    //val newBatch: SpriteBatch

    init {
        app_height = Gdx.app.graphics.height.toFloat()
        app_width = Gdx.app.graphics.width.toFloat()
        cam.setToOrtho(false, app_width, app_height)
        bird = Bird(100,500)
        bg = Texture("newbg.jpg")
        secondBg = Texture("newbg.jpg")
        win = Texture("win.jpg")

        bgStartOld = -400f
        bgEndOld = dandelionrace.HEIGHT.toFloat() * 1.25f

        bgStart = bgEndOld -400f
        bgEnd = bgStart + dandelionrace.HEIGHT.toFloat() * 1.25f

        //newBatch = SpriteBatch()

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

        //System.out.println("Background ist zu Ende bei: " + bgEnd)
        //System.out.println("Rechte Seite der Kamera ist bei: " + cam.position.x + (cam.viewportWidth/2))

        /*ZWEITERSCREEN
        if(cam.position.x + (cam.viewportWidth/2) >= bgEnd) {
            bgStart = bgEnd
            bgEnd = bgStart + bg.width.toFloat()
            nextBg = true

            /*OLD
            bgStart = bgEnd
            bgEnd = bgStart + bg.width.toFloat()
            nextBg = true
            */
            System.out.println("BACKGROUND ZU ENDE")
        }
        */

        if(cam.position.x - (cam.viewportWidth/2) > bgEndOld){
            bgStartOld = bgEnd
            bgEndOld = bgStartOld + dandelionrace.HEIGHT.toFloat() * 1.25f
            System.out.println("ERSTER BACKGROUND WEG")
        }

        if(cam.position.x - (cam.viewportWidth/2) > bgEnd){
            bgStart = bgEndOld
            bgEnd = bgStart + dandelionrace.HEIGHT.toFloat() * 1.25f
            System.out.println("ZWEITER BACKGROUND WEG")
        }


        for(tube in tubes){
            if(tube.collides(bird.getBound())){
                bird.status = "trapped"
                if (bird.position.y - 700 > tube.posBotTube.y) {
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
        //sb.draw(bg, cam.position.x - (cam.viewportWidth / 2 ), 0f, dandelionrace.WIDTH.toFloat(), dandelionrace.HEIGHT.toFloat())

        sb.draw(bg, bgStartOld, 0f, dandelionrace.HEIGHT.toFloat() * 1.25f, dandelionrace.HEIGHT.toFloat())
        //if(nextBg){
        sb.draw(secondBg, bgStart, 0f, dandelionrace.HEIGHT.toFloat() * 1.25f, dandelionrace.HEIGHT.toFloat())
        //}

        for(tube in tubes) {
            sb.draw(tube.topTube, tube.posTopTube.x, tube.posTopTube.y)
            sb.draw(tube.bottomTube, tube.posBotTube.x, tube.posBotTube.y)

        }

        for(item in items){
            sb.draw(item.itemPic, item.posItem.x, item.posItem.y)
        }
        //sb.draw(bg, 0f, 0f, dandelionrace.WIDTH.toFloat(), dandelionrace.HEIGHT.toFloat())
        sb.draw(bird.birdAnimation.getFrame(), bird.position.x, bird.position.y)
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
                        new_bird.position = Vector3(new_bird.position.x, new_bird.position.y + 200, 0f)
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
