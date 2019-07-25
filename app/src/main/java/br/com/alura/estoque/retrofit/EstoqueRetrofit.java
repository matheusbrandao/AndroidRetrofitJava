package br.com.alura.estoque.retrofit;

import org.jetbrains.annotations.NotNull;

import br.com.alura.estoque.retrofit.service.ProdutoService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EstoqueRetrofit {

    private static final String URL_BASE = "http://192.168.122.1:8080/";
    private final ProdutoService produtoService;

    public EstoqueRetrofit() {
        OkHttpClient client = configuraClient();

        /*
            Instância principal para toda configuração de qualquer requisição HTTP
         */
        Retrofit retrofit = new Retrofit.Builder()
                //Com HTTP ocorre bloqueio de requisição
                //Com HTTPS é permitido
                .baseUrl(URL_BASE)
                .client(client)
                //Precisa ter um conversor
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        /*
            Configuração do Service.
            O service mantem as possíveis requisições.
            Fica no package dos serviços.
         */
        produtoService = retrofit.create(ProdutoService.class);
    }

    @NotNull
    private OkHttpClient configuraClient() {
    /*
        An OkHttp interceptor which logs HTTP request and response data.
     */
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    public ProdutoService getProdutoService() {
        return produtoService;
    }
}
