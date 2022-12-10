package idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit

import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.Constante
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object MegaFarmaCliente {
    private var okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        //.addInterceptor(ApiInterceptor()) //para usar el token en toda la consulta
        .build()

    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(Constante().API_MEGAFARMA_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitUsuarioService: UsuarioService by lazy {
        buildRetrofit().create(UsuarioService::class.java)
    }

    val retrofitReclamoService: ReclamoService by lazy {
        buildRetrofit().create(ReclamoService::class.java)
    }

    val retrofitMedicamentoService: MedicamentoService by lazy {
        buildRetrofit().create(MedicamentoService::class.java)
    }
}