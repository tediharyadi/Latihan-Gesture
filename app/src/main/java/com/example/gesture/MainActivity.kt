package com.example.gesture

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

abstract class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener {

    var gestureDetector: GestureDetectorCompat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.gestureDetector = GestureDetectorCompat(this, this)
        gestureDetector?.setOnDoubleTapListener(this)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        this.gestureDetector?.OnTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onLongPress(p0: MotionEvent?) {
        gesture_status.setOnLongClickListener() {
            val popupMenu = PopupMenu(this, it)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_share -> {
                        val intent = Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.facebook.com")
                        )
                        startActivity(intent)
                        true
                    }
                    R.id.menu_info -> {
                        Toast.makeText(this, "Ada Toast!", Toast.LENGTH_LONG).show()
                        true
                    }
                    else -> false
                }
            }

            popupMenu.inflate(R.menu.main_main)

            try {
                val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible = true
                val mPopup = fieldMPopup.get(popupMenu)
                mPopup.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(mPopup, true)
            } catch (e: Exception) {
                Log.e("Main", "Error showing menu icon.", e)
            } finally {
                popupMenu.show()
            }
            true
        }
    }
}

private fun GestureDetectorCompat?.OnTouchEvent(event: MotionEvent?) {

}