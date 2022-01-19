package com.check24.app.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.check24.app.networking.ui.NoConnectionFragment
//import com.github.repos.memory.UserMemoryProvider
import com.check24.app.navigation.result.Result
import com.check24.app.networking.domain.InternetHelper
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var internetHelper : InternetHelper


    val noConnectionFragment: ((Boolean) -> NoConnectionFragment) = { hasPoorConnection ->
        NoConnectionFragment.newInstance(hasPoorConnection)
    }

    private var baseResultData: Intent? = null

    /**
     * This is called for every result added to the activity to be consumed.
     * @return true if the result has been consumed (and will then not be propagated to other activities) or false if not.
     */
    open fun useReturnData(resultData: Result<Parcelable>): Boolean {
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == Result.REQUEST_CODE) {
            val data = resultData ?: Intent()
            data.extras?.keySet()?.forEach {
                val result = data.extras?.get(it)
                if (result is Result<*>) {
                    if (useReturnData(result)) {
                        data.extras?.remove(it)
                    }
                }
            }
            this.baseResultData = data
        }
    }

    fun addResult(result: Result<Parcelable>) {
        baseResultData = (baseResultData ?: Intent()).apply {
            putExtra(result.key, result)
        }
        setResult(Activity.RESULT_OK, baseResultData)
    }

    override fun startActivity(intent: Intent?) {
        super.startActivityForResult(intent, Result.REQUEST_CODE)
    }

    override fun startActivity(intent: Intent?, options: Bundle?) {
        super.startActivityForResult(intent, Result.REQUEST_CODE, options)
    }

}