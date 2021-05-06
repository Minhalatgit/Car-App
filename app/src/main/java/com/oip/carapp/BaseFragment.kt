package com.oip.carapp

import android.os.Bundle
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity

open class BaseFragment : Fragment() {

    lateinit var activity: AppCompatActivity
    lateinit var window: Window

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity = requireActivity() as AppCompatActivity
        window = activity.window
    }
}