package com.check24.app.splash

import android.os.Bundle
import androidx.activity.viewModels
import com.check24.app.base.BaseActivity
import com.check24.app.core.utils.hideSystemUI
import com.check24.app.navigation.AppNavigation
import com.check24.app.splash.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemUI()
        setContentView(ActivitySplashBinding.inflate(layoutInflater).root)

        splashViewModel.launchHomeScreen.observe(this, { launch ->

            if (launch){
                startActivity(AppNavigation.openHome())
            }

        })

    }
}
