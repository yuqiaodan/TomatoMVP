package qiaodan.yu.tomatomvp.https


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("search/repositories")
    fun searchFromGithub(@Query("q") q:String): Call<GithubEntity>
}