package com.dandelionrace.game.States

import android.hardware.SensorManager.GRAVITY_EARTH
import android.media.MediaPlayer
import android.os.AsyncTask
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dandelionrace.game.AndroidLauncher
import com.dandelionrace.game.R
import com.dandelionrace.game.dandelionrace
import com.dandelionrace.game.sprites.Bird
import com.dandelionrace.game.sprites.GameItems
import com.dandelionrace.game.sprites.GameTubes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.dandelionrace.game.sprites.*
import kotlin.collections.ArrayList

open class PlayState(gsm: GameStateManager, finaltubes: ArrayList<GameTubes>, finalitems: ArrayList<GameItems>, gameName: String, enemy: String) : State(gsm) {

    private val TUBE_SPACING: Float = 125f
    //TUBE_COUNT: ANZAHL AN TUBES IM LEVEL
    private var counter: Int = 0

    var inCheck: Boolean = false
    private val bird: Bird
    private val enemyBird: Bird
    private val bg: Texture
    private val secondBg: Texture
    private val win: Texture
    private val enemy = enemy
    private var bgStartOld: Float
    private var bgEndOld: Float
    var enemyWon: Boolean = false

    var testBot: Texture
    var testTop: Texture
    var dandelion: Texture

    private var bgStart: Float
    private var bgEnd: Float

    val app_width: Float
    val app_height: Float

    var nextBg: Boolean = false

    var effectOn: Boolean = false

    val tubes: ArrayList<GameTubes> = finaltubes
    val items: ArrayList<GameItems> = finalitems

    var startTime: Long

    val leavesObstacle: Texture
    var leavesOn: Boolean = false
    var isGhost: Boolean = false

    var obstacleHeight: Float = 0f

    //val newBatch: SpriteBatch

    val game: String = gameName
    val database = FirebaseDatabase.getInstance()
    val myPosX: DatabaseReference
    val myPosY: DatabaseReference


    val myItem: DatabaseReference
    val myFinish: DatabaseReference

    private var mAuth = FirebaseAuth.getInstance()
    val mymail = mAuth?.currentUser?.email.toString()

    init {
        app_height = Gdx.app.graphics.height.toFloat()
        app_width = Gdx.app.graphics.width.toFloat()
        cam.setToOrtho(false, app_width, app_height)
        bird = Bird(100,500, 1)
        enemyBird = Bird(100,500, 2)


        bg = Texture("newbg.jpg")
        secondBg = Texture("newbg.jpg")
        win = Texture("win.jpg")
        dandelion = Texture("dandelion.png")

        AndroidLauncher.mp.isLooping = true
        AndroidLauncher.mp.setVolume(0.3f,0.3f)
        AndroidLauncher.mp.start()

        bgStartOld = -400f
        bgEndOld = dandelionrace.HEIGHT.toFloat() * 1.25f

        bgStart = bgEndOld -400f
        bgEnd = bgStart + dandelionrace.HEIGHT.toFloat() * 1.25f

        startTime = System.currentTimeMillis();

        leavesObstacle = Texture("leavesobstacle.png")

        //newBatch = SpriteBatch()

        testBot = Texture("badlogic.jpg")
        testTop = Texture("badlogic.jpg")

        myPosX = database.getReference("playersInGame/"+game+"/"+mymail.replace(".","")+"/posx")
        myPosY = database.getReference("playersInGame/"+game+"/"+mymail.replace(".","")+"/posy")

        myItem = database.getReference("playersInGame/"+game+"/"+mymail.replace(".","")+"/xitem")
        myFinish = database.getReference("playersInGame/"+game+"/"+mymail.replace(".","")+"/xxzfinish")

        print(finalitems)
        println("Test")
        println("Enemy: "+enemy)

        if(AndroidLauncher.isSingle==false){
            //is true if the other player gets a pos switch item -> override y pos
            var overridePos = database.getReference("playersInGame/"+game+"/"+mymail.replace(".","")+"/xxReadPos")

            //New Location set by other Player in case of pos switch item
            var newYPos = database.getReference("playersInGame/"+game+"/"+mymail.replace(".","")+"/xxypos")

            overridePos.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.getValue().toString().toBoolean()) {
                        newYPos.addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onDataChange(dataSnapshotY: DataSnapshot) {
                                val posY: Float = dataSnapshotY.getValue().toString().toFloat()
                                bird.position.y = posY
                                overridePos.setValue("false")
                            }

                            override fun onCancelled(error: DatabaseError) {
                            }
                        })

                    }
                }
            })

            for (s in enemy.split(",")){
                println("Split: "+s)
                if (s != mymail){
                    val enemyPosX = database.getReference("playersInGame/"+game+"/"+s.replace(".","")+"/posx")
                    println(enemyPosX)
                    enemyPosX.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            enemyBird.position.x = dataSnapshot.getValue().toString().toFloat()
                        }
                    })
                    val enemyPosY = database.getReference("playersInGame/"+game+"/"+s.replace(".","")+"/posy")
                    println(enemyPosY)
                    enemyPosY.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            enemyBird.position.y = dataSnapshot.getValue().toString().toFloat()
                        }
                    })

                    val  enemyItem = database.getReference("playersInGame/"+game+"/"+s.replace(".","")+"/xitem")
                    enemyItem.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val enemyEffect : String = dataSnapshot.getValue().toString()
                            println("ENEMY: "+enemyEffect)
                            if(enemyEffect == "slow"){
                                enemyBird.birdAnimation = Animations(TextureRegion(enemyBird.bird), 2, 0.5f)
                            }
                            if(enemyEffect == "speed"){
                                enemyBird.birdAnimation = Animations(TextureRegion(enemyBird.bird), 2, 0.5f)
                            }
                            if(enemyEffect == "leaves"){
                                startTime = System.currentTimeMillis()
                                effectOn=true
                                leavesOn=true
                            }
                            if(enemyEffect == "ghost"){
                                enemyBird.birdAnimation = Animations(TextureRegion(enemyBird.bird), 2, 0.5f)
                            }
                            if(enemyEffect == "switch"){
                            }
                        }
                    })

                    val  enemyFinish = database.getReference("playersInGame/"+game+"/"+s.replace(".","")+"/xxzfinish")
                    print(enemyFinish)
                    enemyFinish.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot) {

                                enemyWon = dataSnapshot.getValue().toString().toBoolean()==true

                        }
                    })

                }
            }

        }

        println("START STATUS: "+bird.status)

    }

    override fun handleInput() {
        if(Gdx.input.justTouched()){
            bird.jump()
        }
    }


    override fun update(dt: Float) {
        if (!inCheck) {
            if(effectOn){
                if(System.currentTimeMillis()>startTime+5000){
                    effectOn=false
                    leavesOn=false
                    isGhost=false
                    bird.status="free"
                    bird.move = 150
                    bird.birdAnimation = Animations(TextureRegion(Texture("bugredanimation.png")), 2, 0.5f)
                    //TODO: @FELIX SEND to database that the effect of the enemy is over and you can use the standard texture again


                }
            }

            someTask(bird, obstacleHeight).execute()
            handleInput()
            bird.update(dt)
            cam.position.set(bird.position.x + 80, cam.viewportHeight/2,0f)

            // Write a message to the database
            myPosX.setValue(bird.position.x);
            myPosY.setValue(bird.position.y);

            if(cam.position.x - (cam.viewportWidth/2) > tubes[counter].posTopTube.x + tubes[counter].topTube.width){
                counter = counter.inc()
                System.out.println("COUNTER:"+counter)
            }

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


            if(isGhost==false) {
                for (tube in tubes) {
                    if (tube.collides(bird.getBound())) {

                        bird.status = "trapped" //"trapped" ///"trapped"                                                     ////CHANGEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
                        if (bird.position.y - 700 > tube.posBotTube.y) {
                            bird.trappedTube = "top"
                            obstacleHeight = tube.boundsTop.height

                        } else {
                            bird.trappedTube = "bot"
                            obstacleHeight = tube.boundsBot.height
                        }
                    }
                }
            }

            for(item in items){

                if(item.collides(bird.getBound())){
                    AndroidLauncher.pointer.start()
                    item.posItem.set(-100f,-100f)
                    item.bounds.set(-100f,-100f,0f,0f)
                    //TODO: SET THE EFFECTS

                    myItem.setValue(item.effect)

                    if(item.effect == "slow"){
                    bird.move = 50
                        bird.birdAnimation = Animations(TextureRegion(Texture("bugblueanimation.png")), 2, 0.5f)
                        startTime = System.currentTimeMillis()
                        effectOn=true
                    }
                    if(item.effect == "speed"){
                        bird.move = 400
                        bird.birdAnimation = Animations(TextureRegion(Texture("buggreenanimation.png")), 2, 0.5f)
                        startTime = System.currentTimeMillis()
                        effectOn=true
                    }
                    if(item.effect == "leaves"){

                    }
                    if(item.effect == "ghost"){
                        isGhost=true
                        bird.birdAnimation = Animations(TextureRegion(Texture("bugghostanimation.png")), 2, 0.5f)
                        startTime = System.currentTimeMillis()
                        effectOn=true
                    }
                    if(item.effect == "switch"){
                        //no skin change, switch positions of player
                        startTime = System.currentTimeMillis()
                        effectOn=true

                        //gets the enemys gamename for database
                        var enemyArray = enemy.split(",")
                        var onlyEnemy = ""
                        for (e in enemyArray) {
                            if (e != mymail) {
                                onlyEnemy = e
                            }
                        }
                        database.getReference("playersInGame/"+game+"/"+onlyEnemy.replace(".","")+"/xxypos").setValue(bird.position.y)
                        val enemyPosY = database.getReference("playersInGame/"+game+"/"+onlyEnemy.replace(".","")+"/posy")
                        //Read ypos of enemy and set own pos to it
                        enemyPosY.addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onDataChange(dataSnapshotY: DataSnapshot) {
                                val posY: Float = dataSnapshotY.getValue().toString().toFloat()
                                bird.position.y = posY
                            }

                            override fun onCancelled(error: DatabaseError) {
                            }
                        })
                        val enemyReadPos = database.getReference("playersInGame/"+game+"/"+onlyEnemy.replace(".","")+"/xxReadPos")
                        enemyReadPos.setValue("true")

                    }

                }
            }
            cam.update()

            if(counter==tubes.size){
                //TODO: SET WINNER AND LOOSER

                myFinish.setValue(true)
                //AndroidLauncher().mp.stop()

                gsm.set(WinGame(gsm, false))
                dispose();


                if(enemyWon){
                    //myFinish.setValue(true)
                    AndroidLauncher.mp.stop()
                    println("YOU LOST")
                    gsm.set(WinGame(gsm, false))

                    dispose();
                }else{
                    AndroidLauncher.mp.stop()
                    println("YOU WON")
                    myFinish.setValue(true)
                    gsm.set(WinGame(gsm, true))
                    dispose();
                }



            } else if (counter < tubes.size) {

            }
        }



    }

    override fun render(sb: SpriteBatch) {
        sb.projectionMatrix.set(cam.combined)
        //test
        sb.begin()

        sb.draw(bg, bgStartOld, 0f, dandelionrace.HEIGHT.toFloat() * 1.25f, dandelionrace.HEIGHT.toFloat())

        sb.draw(secondBg, bgStart, 0f, dandelionrace.HEIGHT.toFloat() * 1.25f, dandelionrace.HEIGHT.toFloat())


        for(tube in tubes) {
            sb.draw(tube.topTube, tube.posTopTube.x, tube.posTopTube.y)
            sb.draw(tube.bottomTube, tube.posBotTube.x, tube.posBotTube.y)
            if(tube == tubes[tubes.size-1])
                sb.draw(dandelion, tube.posBotTube.x+900, 0f)
        }

        for(item in items){
            sb.draw(item.itemPic, item.posItem.x, item.posItem.y)
        }
        sb.draw(bird.birdAnimation.getFrame(), bird.position.x, bird.position.y)
        if(AndroidLauncher.isSingle==false){
            sb.draw(enemyBird.birdAnimation.getFrame(), enemyBird.position.x, enemyBird.position.y)
        }

        if(leavesOn){
            sb.draw(leavesObstacle, cam.position.x-500f, cam.position.y-500f)
        }



        sb.end()
    }


    override fun dispose() {

    }


    class someTask(bird: Bird, obstacleH: Float) : AsyncTask<Void, Void, String>(){

        val new_bird: Bird
        var obstacleHeight: Float

        init {
            new_bird = bird
            obstacleHeight = obstacleH
        }

        override fun doInBackground(vararg params: Void?): String? {
            if(new_bird.status=="trapped"){

                val xGrav = Gdx.input.accelerometerX / GRAVITY_EARTH
                val yGrav = Gdx.input.accelerometerY / GRAVITY_EARTH
                val zGrav = Gdx.input.accelerometerZ / GRAVITY_EARTH

                val gForce = Math.sqrt((xGrav * xGrav + yGrav * yGrav + zGrav * zGrav).toDouble()).toFloat()

                if (gForce>1.7) {
                    System.out.println("SHAKE DETECTED")
                    new_bird.status = "free"
                    if(new_bird.trappedTube == "bot"){
                        new_bird.bushjump()
                        new_bird.jump()
                    } else {
                        new_bird.invertjump()
                    }

                }

                return "Free"
            } else {
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


