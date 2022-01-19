package com.check24.app.networking.networking.repo

import com.check24.app.networking.networking.repo.model.RepoModel
import retrofit2.http.GET

interface RepoService {

    companion object {
        const val PRODUCTS_PATH = "products-test.json"
    }

    /**
     *  Method to fetch products form API
     * http://app.check24.de/products-test.json
     */

    @GET(PRODUCTS_PATH)
    suspend fun fetchProducts(): RepoModel

}