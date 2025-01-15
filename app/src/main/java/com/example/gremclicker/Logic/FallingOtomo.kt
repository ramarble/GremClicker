package com.example.gremclicker.Logic

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.Activity
import android.media.SoundPool
import android.provider.Settings.Secure
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import com.example.gremclicker.Currency.Currency
import com.example.gremclicker.R
import java.security.SecureRandom
import kotlin.math.abs

class FallingOtomo{

    //This whole thing took like 3 hours to implement

    companion object {
        fun createFallingOtomo(act: Activity) {

            var layoutPar = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            var metrics = DisplayMetrics()
            act.windowManager.defaultDisplay.getMetrics(metrics)

            //this is the sound
            var soundPool = SoundPool.Builder().build()
            var soundToPlay = soundPool.load(act, chooseRandomSound(abs(SecureRandom().nextInt()%300)), 1)

            var otomo = createOtomo(act,metrics)
            act.addContentView(otomo, layoutPar)

            var fallingSpeed= fallingSpeed(otomo,metrics)
            fallingSpeed.start()

            fallingSpeed.doOnEnd{
                (otomo.parent as ViewGroup).removeView(otomo)
            }

            var rotation = rotationSpeed(otomo)
            rotation.start()

            otomo.setOnClickListener {
                Currency.addCurrency(10);
                soundPool.play(soundToPlay, 100F, 100F, 1, 0, 1F )
                fallingSpeed.removeAllListeners()
                (otomo.parent as ViewGroup).removeView(otomo)
            }

        }

        fun chooseRandomSound(int: Int) : Int{
            if (int > 150) {
                return R.raw.pop1
            } else if (int > 10) {
                return R.raw.pop2
            }else return R.raw.pop3;
        }
        fun chooseRandomOtomo(int: Int) : Int{
            when(int) {
                1 -> return R.drawable.otomo2
                0 -> return R.drawable.otomo3
                else -> return R.drawable.otomo2
            }
        }

        fun fallingSpeed(imageView: ImageView, dp: DisplayMetrics): ObjectAnimator {
            var o: ObjectAnimator = ObjectAnimator.ofFloat(
                imageView,
                "translationY",
                -200 - (imageView.height.toFloat()),
                dp.heightPixels.toFloat() + imageView.height+1,
            )
            o.setDuration((SecureRandom().nextInt(1)+2000).toLong())
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
            otomo.maxWidth = SecureRandom().nextInt(100) + 170
            otomo.maxHeight = SecureRandom().nextInt(100) + 170
            otomo.x = (SecureRandom().nextInt(dp.widthPixels)-100).toFloat();
            otomo.adjustViewBounds = true
            otomo.setImageResource(chooseRandomOtomo(abs(SecureRandom().nextInt()%2)));
            return otomo;
        }
    }
}