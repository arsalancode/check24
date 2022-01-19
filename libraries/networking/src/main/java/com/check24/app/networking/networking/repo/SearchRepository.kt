package com.check24.app.networking.networking.repo

import com.check24.app.networking.networking.repo.model.RepoModel
import com.check24.app.networking.networking.repo.model.SearchResult
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    @FlowPreview
    suspend fun fetchProducts(): Flow<RepoModel>
}
