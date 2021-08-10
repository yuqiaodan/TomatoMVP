package qiaodan.yu.tomatomvp.https

import com.fly.tomato.common.http.HttpUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by qiaodan on 2021/8/10
 * Description:
 */
object ApiManager {

    private val myRetrofit: Retrofit =
        Retrofit.Builder().client(HttpUtils.getBaseHttpInterceptor().build())
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()


    private val callService: Api = myRetrofit.create(Api::class.java)

    fun searchFromGithub(key: String, action: (result: GithubEntity?) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val call = callService.searchFromGithub(key)
                val response = call.execute()
                val result = response.body()
                if (response.isSuccessful && result != null) {
                    withContext(Dispatchers.Main) {
                        action.invoke(result)
                    }
                }else{
                    withContext(Dispatchers.Main) {
                        action.invoke(null)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    action.invoke(null)
                }
            }


        }


    }

}