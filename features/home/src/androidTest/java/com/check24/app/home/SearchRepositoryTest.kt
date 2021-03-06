package com.check24.app.home

import android.content.Context
import android.net.ConnectivityManager
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.check24.app.networking.domain.InternetHelper
import com.check24.app.networking.networking.repo.RepoService
import com.check24.app.networking.networking.repo.SearchRepository
import com.check24.app.networking.networking.repo.SearchRepositoryImpl
import com.check24.app.networking.networking.repo.model.Header
import com.check24.app.networking.networking.repo.model.RepoModel
import com.check24.app.networking.provider.RetrofitBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.ArrayList

@Suppress("IllegalIdentifier")
@ExperimentalCoroutinesApi
@ExperimentalSerializationApi
class SearchRepositoryTest {
    private val mockWebServer = MockWebServer()

    lateinit var connectivityManager: ConnectivityManager
    lateinit var internetHelper: InternetHelper
    lateinit var retrofitInstance : RepoService
    lateinit var searchRepo: SearchRepository

    @Before
    fun initialise(){
        connectivityManager = getApplicationContext<Context>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        internetHelper = InternetHelper(connectivityManager)
        retrofitInstance = RetrofitBuilder().check24Instance.create(RepoService::class.java)
        searchRepo = SearchRepositoryImpl(internetHelper, retrofitInstance)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    @Test
    fun fetchGitRepos_VerifyItemsSize() = runTest {

        mockWebServer.start()
        val expected = 30

        searchRepo.fetchProducts()
            .collect {
            assertEquals(expected, it.products.size)
        }
    }



}
