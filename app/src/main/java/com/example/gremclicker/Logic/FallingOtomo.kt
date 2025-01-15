package com.example.gremclicker.Logic

import android.animation.ObjectAnimator
import android.app.Activity
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.example.gremclicker.R
import java.security.SecureRandom

class FallingOtomo{

    //This whole thing took like 3 hours to implement

    companion object {
        fun createFallingOtomo(act: Activity) {

            var layoutPar = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            var metrics = DisplayMetrics()
            act.windowManager.defaultDisplay.getMetrics(metrics)

            var otomo = createOtomo(act,metrics)


            act.addContentView(otomo, layoutPar)

            var fallingSpeed= fallingSpeed(otomo,metrics)
            fallingSpeed.start()

            var rotation = rotationSpeed(otomo)
            rotation.start()

            otomo.setOnClickListener {
                fallingSpeed.cancel()
                rotation.cancel()
                (otomo.parent as ViewGroup).removeView(otomo)
            }
        }

        fun fallingSpeed(imageView: ImageView, dp: DisplayMetrics): ObjectAnimator {
            var o: ObjectAnimator = ObjectAnimator.ofFloat(
                imageView,
                "translationY",
                -200 - (imageView.height.toFloat()),
                dp.heightPixels.toFloat() + imageView.height+1,
            )
            o.setDuration((SecureRandom().nextInt(10000)+2000).toLong())
            return o;
        }

        fun rotationSpeed(imageView: ImageView): ObjectAnimator {
            var o: ObjectAnimator = ObjectAnimator.ofFloat(
                imageView,
                "rotation",
                0f, 360f
            )
            o.interpolator = LinearInterpolator()
            o.repeatCount = Animation.INFINITE
            o.setDuration((SecureRandom().nextInt(3000)+400).toLong())
            return o;
        }

        fun createOtomo(act: Activity, dp:DisplayMetrics): ImageView {
            var otomo = ImageView(act)
            otomo.maxWidth = SecureRandom().nextInt(200) + 100
            otomo.maxHeight = SecureRandom().nextInt(200) + 100
            otomo.x = (SecureRandom().nextInt(dp.widthPixels)-100).toFloat();
            otomo.adjustViewBounds = true
            otomo.setImageResource(R.drawable.otomo2);
            return otomo;
        }
    }
}