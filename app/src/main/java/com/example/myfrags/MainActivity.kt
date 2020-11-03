package com.example.myfrags

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.util.*

class MainActivity : FragmentActivity(), Fragment1.OnButtonClickListener {
    private var frames: IntArray? = null
    private var hidden: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            frames = intArrayOf(R.id.frame1, R.id.frame2, R.id.frame3, R.id.frame4)
            hidden = false

            val fragments = arrayOf(Fragment1(), Fragment2(), Fragment3(), Fragment4())
            val fragmentManager = supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            fragments.forEachIndexed { index, fragment -> transaction.add(frames!![index], fragment) }
            transaction.addToBackStack(null)
            transaction.commit()
        } else {
            frames = savedInstanceState.getIntArray("FRAMES")
            hidden = savedInstanceState.getBoolean("HIDDEN")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val inputText = findViewById<EditText>(R.id.editTextNumber)
        outState.putString("editText", inputText.text.toString())
        outState.putIntArray("FRAMES", frames)
        outState.putBoolean("HIDDEN", hidden!!)
    }

    override fun onButtonClickShuffle() {
        val list = arrayListOf(frames!![0], frames!![1], frames!![2], frames!![3])
        list.shuffle()
        for (i in 0 until 4) {
            frames!![i] = list[i]
        }
        newFragments()
    }

    override fun onButtonClickClockwise() {
        val t = frames!![0]
        frames!![0] = frames!![1]
        frames!![1] = frames!![2]
        frames!![2] = frames!![3]
        frames!![3] = t

        newFragments()
    }

    override fun onButtonClickHide() {
        if (hidden!!) {
            return
        }

        val fragmentManager = supportFragmentManager

        for (fragment in fragmentManager.fragments) {
            if (fragment is Fragment1) continue

            val transaction = fragmentManager.beginTransaction()
            transaction.hide(fragment)

            transaction.addToBackStack(null)
            transaction.commit()
        }
        hidden = true
    }

    override fun onButtonClickRestore() {
        if (!hidden!!) return

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        for (fragment in fragmentManager.fragments) {
            if (fragment is Fragment1) continue
            transaction.show(fragment)
        }
        transaction.addToBackStack(null)
        transaction.commit()

        hidden = false
    }

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)

        if (fragment is Fragment1) {
            fragment.setOnButtonClickListener(this)
        }
    }

    private fun newFragments() {
        val newFragments = arrayOf(Fragment1(), Fragment2(), Fragment3(), Fragment4())

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        for (i in 0 until 4) {
            transaction.replace(frames!![i], newFragments[i])
            if (hidden!! && newFragments[i] !is Fragment1) {
                transaction.hide(newFragments[i])
            }
        }
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
