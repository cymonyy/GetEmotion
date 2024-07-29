package com.opensource.getemotion.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import com.opensource.getemotion.R
import com.opensource.getemotion.listeners.OnDrawListener
import kotlin.math.abs
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min

class ExperimentView(context: Context, attrs: AttributeSet) : View(context, attrs){


    /*
    * Features:
    *   timestamp : System.currentTimeMillis, long
    *   coordinates :
    *       start : Pair()
    *       end : Pair()
    *   distance : double
    *   duration : double
    *   velocity : double
    *   acceleration : double
    *   touchCount: int
    *   pressure : double
    * */

    var countInteraction = 0


    var timestamp : MutableList<Long> = mutableListOf()
    var startCoordinates : MutableList<Pair<Double, Double>> = mutableListOf()
    var endCoordinates : MutableList<Pair<Double, Double>> = mutableListOf()
    var distance : MutableList<Double> = mutableListOf()
    var duration : MutableList<Double> = mutableListOf()

    var averageVelocityList : MutableList<Double> = mutableListOf()
    var averageVelocityXList : MutableList<Double> = mutableListOf()
    var averageVelocityYList : MutableList<Double> = mutableListOf()
    var minVelocityList : MutableList<Double> = mutableListOf()
    var maxVelocityList : MutableList<Double> = mutableListOf()
    var minVelocityXList : MutableList<Double> = mutableListOf()
    var maxVelocityXList : MutableList<Double> = mutableListOf()
    var minVelocityYList : MutableList<Double> = mutableListOf()
    var maxVelocityYList : MutableList<Double> = mutableListOf()


    var averageAccelerationList : MutableList<Double> = mutableListOf()
    var averageAccelerationXList : MutableList<Double> = mutableListOf()
    var averageAccelerationYList : MutableList<Double> = mutableListOf()
    var minAccelerationList : MutableList<Double> = mutableListOf()
    var maxAccelerationList : MutableList<Double> = mutableListOf()
    var minAccelerationXList : MutableList<Double> = mutableListOf()
    var maxAccelerationXList : MutableList<Double> = mutableListOf()
    var minAccelerationYList : MutableList<Double> = mutableListOf()
    var maxAccelerationYList : MutableList<Double> = mutableListOf()


    var touchCount : MutableList<Int> = mutableListOf()
    var pressure : MutableList<Double> = mutableListOf()


    private var velocityTracker : VelocityTracker? = null
    private var touchCounter = 0
    private var velocityCounter = 0

    private var tempPressure = 0.0

    private var lastVelocityX = 0.0
    private var lastVelocityY = 0.0
    private var minVelocityX = 0.0
    private var minVelocityY = 0.0
    private var maxVelocityX = 0.0
    private var maxVelocityY = 0.0
    private var velocityX = 0.0
    private var velocityY = 0.0

    private var lastVelocity = 0.0
    private var maxVelocity = 0.0
    private var minVelocity = 0.0
    private var tempVelocity = 0.0


    private var minAccelerationX = 0.0
    private var minAccelerationY = 0.0
    private var maxAccelerationX = 0.0
    private var maxAccelerationY = 0.0
    private var accelerationX = 0.0
    private var accelerationY = 0.0

    private var maxAcceleration = 0.0
    private var minAcceleration = 0.0
    private var tempAcceleration = 0.0

    private var currentTime = 0L
    private var startTime = 0L
    private var lastTime = 0L
    private var endTime = 0L

    private var deltaTime = 0L
    private var deltaVelocityX = 0.0
    private var deltaVelocityY = 0.0




    private var paint: Paint = Paint()
    private var path: Path = Path()
    private var canvas: Canvas? = null
    private var bitmap: Bitmap? = null
    private var isDrawing = false
    private var onDrawListener: OnDrawListener? = null



//    private var pathList = ArrayList<Pair<Double, Double>>()
//    private var velocityList = ArrayList<Double>()
//    private var accelerationList = ArrayList<Double>()
//
//    private var i = 0
//    private var totalDistance = 0.0
//    private var totalTime: Double = 0.0
//    private var lastTouchX = 0.0
//    private var lastTouchY: Double = 0.0
//    private var lastTimestamp: Long = 0
//    private var lastVelocity = 0.0

    init {
        paint.isAntiAlias = true
        paint.color = resources.getColor(R.color.background, null)
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeWidth = 5f
    }



    fun setOnDrawListener(listener: OnDrawListener?) {
        onDrawListener = listener
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap!!)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        bitmap?.let { canvas.drawBitmap(it, 0f, 0f, null) }

        canvas.drawPath(path, paint)

        // Notify the listener about drawing status
        onDrawListener?.onDrawStatusChanged(isDrawing)
    }

    @SuppressLint("ClickableViewAccessibility", "Recycle")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)

                //initialize velocity
                if(velocityTracker == null) velocityTracker = VelocityTracker.obtain()
                else velocityTracker!!.clear()
                velocityTracker?.addMovement(event);

                //store start coordinates
                startCoordinates.add(Pair(x.toDouble(), y.toDouble()))

                //store temp pressure
                tempPressure = max(tempPressure, event.size.toDouble())

                //store start time
                startTime = System.currentTimeMillis()
                lastTime = startTime

                //store time to timestamp
                timestamp.add(startTime)

                //reset touch count
                touchCounter = 0
                touchCounter++


//                pathList.add(Pair(x.toDouble(), y.toDouble()))

//                lastTouchX = x.toDouble();
//                lastTouchY = y.toDouble();
//                lastTimestamp = event.downTime
//
//                i++
//                if (i == 1){
//                    startCoordinates = Pair(x.toDouble(), y.toDouble())
//                }
//
//                pressure = max(pressure, event.size.toDouble())
            }
            MotionEvent.ACTION_MOVE -> {
                if (x >= 0 && y >= 0){
                    path.lineTo(x, y)



                    //add touch count
                    touchCounter++

                    //add velocity movement
                    velocityTracker?.addMovement(event)

                    //calculate velocity and acceleration
                    calculateVelocityAndAcceleration()

                    //replace pressure
                    tempPressure = max(tempPressure, event.getSize(event.getPointerId(event.actionIndex)).toDouble())


//                    pathList.add(Pair(x.toDouble(), y.toDouble()))
//                    calculateVelocity(x.toDouble(), y.toDouble(), event.eventTime);
//                    Log.d("FINGER SIZE", "Size: ${event.getSize(event.getPointerId(event.actionIndex))}")
//
//                    //get scale of touched area as finger moves
//                    pressure = max(pressure, event.getSize(event.getPointerId(event.actionIndex)).toDouble())
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {

                //increment interaction count
                countInteraction++

                //calculate end coordinates
                endCoordinates.add(Pair(x.toDouble(), y.toDouble()))

                //add end time
                endTime = System.currentTimeMillis()

                //calculate duration
                duration.add((endTime - startTime).toDouble())

                //add touch count
                touchCount.add(touchCounter)
//                touchCounter = 0

                //calculate average x, y, and combined velocity
                averageVelocityXList.add(velocityX / velocityCounter)
                averageVelocityYList.add(velocityY / velocityCounter)
                averageVelocityList.add(tempVelocity / velocityCounter)

                //store min and max velocity
                minVelocityXList.add(minVelocityX)
                minVelocityYList.add(minVelocityY)
                minVelocityList.add(minVelocity)
                maxVelocityXList.add(maxVelocityX)
                maxVelocityYList.add(maxVelocityY)
                maxVelocityList.add(maxVelocity)


                //calculate average x, y, and combined acceleration
                averageAccelerationXList.add(accelerationX / velocityCounter)
                averageAccelerationYList.add(accelerationY / velocityCounter)
                averageAccelerationList.add(tempAcceleration / velocityCounter)

                //store min and max velocity
                minAccelerationXList.add(minAccelerationX)
                minAccelerationYList.add(minAccelerationY)
                minAccelerationList.add(minAcceleration)
                maxAccelerationXList.add(maxAccelerationX)
                maxAccelerationYList.add(maxAccelerationY)
                maxAccelerationList.add(maxAcceleration)


                //calculate distance
                distance.add(
                    hypot(
                        (endCoordinates.last().first - startCoordinates.last().first),
                        (endCoordinates.last().second - startCoordinates.last().second)
                    )

                )

                //add max pressure
                pressure.add(tempPressure)

                //reset all
                touchCounter = 0
                velocityCounter = 0

                tempPressure = 0.0

                lastVelocityX = 0.0
                lastVelocityY = 0.0
                minVelocityX = 0.0
                minVelocityY = 0.0
                maxVelocityX = 0.0
                maxVelocityY = 0.0
                velocityX = 0.0
                velocityY = 0.0

                lastVelocity = 0.0
                maxVelocity = 0.0
                minVelocity = 0.0
                tempVelocity = 0.0


                minAccelerationX = 0.0
                minAccelerationY = 0.0
                maxAccelerationX = 0.0
                maxAccelerationY = 0.0
                accelerationX = 0.0
                accelerationY = 0.0

                maxAcceleration = 0.0
                minAcceleration = 0.0
                tempAcceleration = 0.0

                currentTime = 0L
                startTime = 0L
                lastTime = 0L
                endTime = 0L

                deltaTime = 0L
                deltaVelocityX = 0.0
                deltaVelocityY = 0.0


                //UI related processes
                isDrawing = true
                canvas!!.drawPath(path, paint)
                path.reset()

                isDrawing = true;


//                //calculate the end coordinates
//                endCoordinates = pathList.last()
//
//                //calculate velocity of the path
//                velocity = totalDistance / totalTime
//                Log.d("OverallVelocity", "Overall Velocity: $velocity")
//
//                //calculate acceleration of the path
//                acceleration = accelerationList.average()
//
//                //calculate duration of the interaction
//                duration = totalTime
//
//                //calculate count touches
//                countTouches = pathList.size.toDouble()
//
//                //calculate euclidean distance
//                euclidean = sqrt(((endCoordinates.first - startCoordinates.first).pow(2).toDouble())+((endCoordinates.second - startCoordinates.second).pow(2).toDouble()))

            }
        }

        // Invalidate the view to force a redraw
        invalidate()
        return true
    }

    private fun calculateVelocityAndAcceleration() {

        var temp: Double


        //get change in time
        currentTime = System.currentTimeMillis()
        deltaTime = currentTime - lastTime

        //store current time to last time
        lastTime = currentTime

        //compute velocity
        velocityTracker?.computeCurrentVelocity(1)

        /*
        * Velocity X:
        *   1. store velocity x
        *   2. get min velocity x
        *   3. get max velocity x
        *   4. get change of velocity x
        *   5. get acceleration (delta velocity / delta time)
        *   6. store current velocity x to last velocity x
        * */
        velocityTracker?.xVelocity?.let { x ->
            temp = abs(x.toDouble())

            //velocity
            velocityX += temp
            minVelocityX = if(minVelocityX != 0.0) min(minVelocityX, temp) else temp
            maxVelocityX = max(maxVelocityX, temp)

            //acceleration
            deltaVelocityX = abs(temp - lastVelocityX)
            temp = deltaVelocityX / deltaTime

            accelerationX += temp
            minAccelerationX = if(minAccelerationX != 0.0) min(minAccelerationX, temp) else temp
            maxAccelerationX = max(maxAccelerationX, temp)

            lastVelocityX = x.toDouble()
        }

        /*
        * Velocity Y:
        *   1. store velocity y
        *   2. get min velocity y
        *   3. get max velocity y
        *   4. get change of velocity y
        *   5. get acceleration (delta velocity / delta time)
        *   6. store current velocity y to last velocity y
        * */
        velocityTracker?.yVelocity?.let { y ->
            temp = abs(y.toDouble())

            //velocity
            velocityY += temp
            minVelocityY = if(minVelocityY != 0.0) min(minVelocityY, temp) else temp
            maxVelocityY = max(maxVelocityY, temp)

            //acceleration
            deltaVelocityY = abs(temp - lastVelocityY)
            temp = deltaVelocityY / deltaTime

            accelerationY += temp
            minAccelerationY = if(minAccelerationY != 0.0) min(minAccelerationY, temp) else temp
            maxAccelerationY = max(maxAccelerationY, temp)

            lastVelocityY = y.toDouble()
        }

        //calculate euclidean velocity
        val currVelocity =  velocityTracker?.yVelocity?.let{
            velocityTracker?.xVelocity?.let { it1 ->
                hypot(
                    it1.toDouble(), it.toDouble()
                )
            }
        }!!

        //get velocity, min velocity, max velocity
        tempVelocity += currVelocity
        minVelocity = if(minVelocity != 0.0) min(minVelocity, currVelocity) else currVelocity
        maxVelocity = max(maxVelocity, currVelocity)

        //get acceleration, min acceleration, max acceleration
        temp = (abs(currVelocity - lastVelocity) / deltaTime)
        tempAcceleration += temp
        minAcceleration = if(minAcceleration != 0.0) min(minAcceleration, temp) else temp
        maxAcceleration = max(maxAcceleration, temp)

        lastVelocity = currVelocity
        velocityCounter++

    }

//    private fun calculateVelocity(touchX: Double, touchY: Double, timestamp: Long) {
//        // Calculate distance moved
//        val distance = sqrt(
//            (touchX - lastTouchX).toDouble().pow(2.0) + (touchY - lastTouchY).toDouble().pow(2.0)
//        ).toFloat()
//
//        // Calculate time elapsed
//        val timeElapsed = (timestamp - lastTimestamp) / 1000.000000
//
//        // Update total distance and total time
//        totalDistance += distance;
//        totalTime += timeElapsed;
//
//        // Calculate velocity
//        val velocity = distance / timeElapsed
//
//        // Do something with the velocity
//        velocityList.add(velocity)
//        Log.d("Velocity", "Velocity: $velocity")
//
//        // Calculate acceleration
//        // Calculate acceleration
//        val acceleration = (velocity - lastVelocity) / timeElapsed
//        accelerationList.add(acceleration)
//        Log.d("Acceleration", "Acceleration: $acceleration")
//
//        // Update last values
//        lastTouchX = touchX
//        lastTouchY = touchY
//        lastTimestamp = timestamp
//        lastVelocity = velocity;
//    }
}