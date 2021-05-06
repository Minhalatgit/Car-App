package com.oip.carapp

import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.suke.widget.SwitchButton

open class BaseFragment : Fragment() {

    lateinit var activity: AppCompatActivity
    lateinit var window: Window

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity = requireActivity() as AppCompatActivity
        window = activity.window
    }
}