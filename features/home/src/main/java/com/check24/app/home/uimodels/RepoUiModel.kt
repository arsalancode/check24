package com.check24.app.home.uimodels

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.check24.app.home.R
import com.check24.app.core.lists.DataBoundModel
import com.check24.app.networking.networking.repo.model.RepoModel

class RepoUiModel(repoModel: RepoModel, private val onClick: (() -> Unit)?) :
    DataBoundModel(R.layout.repo_row) {

    init {
        Log.i("RepoUiModel", repoModel.toString())
    }

    val ownerAvatarUrl = MutableLiveData<String>().apply { postValue(repoModel.owner.ownerAvatar) }
    val ownerName = MutableLiveData<String>().apply { postValue(repoModel.owner.ownerName) }
    val repoName = MutableLiveData<String>().apply { postValue(repoModel.repoName) }
    val repoTitle = MutableLiveData<String>().apply { postValue(repoModel.repoTitle) }
    val repoDesc = MutableLiveData<String>().apply { postValue(repoModel.repoDesc) }
    val repoUrl = MutableLiveData<String>().apply { postValue(repoModel.repoUrl) }

    fun onClick(clickedView: View) {
        onClick?.invoke()
    }

}
