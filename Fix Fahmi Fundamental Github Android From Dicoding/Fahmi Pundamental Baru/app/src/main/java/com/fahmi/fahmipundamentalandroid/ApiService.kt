package com.fahmi.fahmipundamentalandroid

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiService {


//    @GET("top-headlines?country=id&category=science")
//    fun getNews(@Query("apiKey") apiKey: String): Call<NewsResponse>

    @GET("search/users")
//    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String

    ):Call<GithubResponse>

    @GET("users/{username}")
//    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String?
    ):Call<DetailUserResponse>

    @GET("users/{username}/followers")
//    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username:String
    ):Call<ArrayList<PenggunaGithub>>
//    ):Call<GithubResponse>

    @GET("users/{username}/following")
//    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username:String
    ):Call<ArrayList<PenggunaGithub>>




    @GET("detail/{id}")
    fun getRestaurant(
        @Path("id") id: String
    ): Call<GithubResponse>

//    @FormUrlEncoded
//    @Headers("Authorization: token 12345")
//    @POST("review")
//    fun postReview(
//        @Field("id") id: String,
//        @Field("name") name: String,
//        @Field("review") review: String
//    ): Call<PostReviewResponse>

}

