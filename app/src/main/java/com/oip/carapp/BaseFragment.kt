package com.oip.carapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

open class BaseFragment : Fragment() {

    lateinit var toolbar: Toolbar
    lateinit var title: TextView
    lateinit var mActivity: AppCompatActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as AppCompatActivity
        toolbar = activity?.findViewById(R.id.toolbar)!!
        title = toolbar.findViewById(R.id.title)
    }

}