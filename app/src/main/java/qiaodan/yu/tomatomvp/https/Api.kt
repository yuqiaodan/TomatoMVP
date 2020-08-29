package qiaodan.yu.tomatomvp.https

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("search/repositories")
    fun searchFromGithub(@Query("q") q:String):Observable<GithubEntity>
}