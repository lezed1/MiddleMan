package com.lezed1.middlemanv2

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_chooser.*

class IntentMiddleman : AppCompatActivity() {
    private val TAG = "INTENT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        Remove this activity from the intent
        intent.component = null

        val chooserChoices = packageManager.queryIntentActivities(intent, intent.flags)
                .filter { it.activityInfo.name != componentName.className }
                .map {
                    ChooserChoiceAdapter.ChooserChoice(
                            it.loadIcon(packageManager),
                            ComponentName(it.activityInfo.packageName, it.activityInfo.name),
                            it.loadLabel(packageManager)
                    )
                }

        Log.d(TAG, "onCreate Got " + chooserChoices.size + " activities.")
        Log.d(TAG, "onCreate $chooserChoices")

        if (chooserChoices.size == 1) {
            launchChoice(chooserChoices[0])
        } else {
            setContentView(R.layout.activity_chooser)
            window.attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
            window.attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT
            window.attributes.gravity = Gravity.BOTTOM

            setFinishOnTouchOutside(true)

            title = "Complete action using"

            rv_chooser_grid.layoutManager = GridLayoutManager(this, 3)
            rv_chooser_grid.adapter = ChooserChoiceAdapter(chooserChoices, this, ::launchChoice)
        }
    }

    private fun launchChoice(chooserChoice: ChooserChoiceAdapter.ChooserChoice) {
        Log.d(TAG, intent.toString())
        intent.component = chooserChoice.componentName
        intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT)
        startActivity(intent)
        finish()
    }
}
