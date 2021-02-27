package com.oip.carapp

import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.suke.widget.SwitchButton

open class BaseFragment : Fragment() {

    lateinit var toolbar: Toolbar
    lateinit var title: TextView
    lateinit var switch: SwitchButton
    lateinit var navigationIcon: ImageView
    lateinit var mactivity: AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mactivity = requireActivity() as AppCompatActivity
        toolbar = mactivity.findViewById(R.id.toolbar)!!
        title = toolbar.findViewById(R.id.title)
        switch = toolbar.findViewById(R.id.switch_button)
        navigationIcon = toolbar.findViewById(R.id.navigationIcon)
    }
}