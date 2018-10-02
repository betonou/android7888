package br.com.caelum.casadocodigo.webservices;

import java.util.ArrayList;

import br.com.caelum.casadocodigo.converter.LivroServiceConverterFactory;
import br.com.caelum.casadocodigo.interfaces.LivroDelegate;
import br.com.caelum.casadocodigo.modelo.Livro;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WebClient {

    private LivroDelegate delegate;

    private static final String URL = "http://cdcmob.herokuapp.com/";

    public WebClient(LivroDelegate delegate) {
        this.delegate = delegate;
    }

    public void buscaLivros(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(new LivroServiceConverterFactory())
                .build();


        LivroService service = retrofit.create(LivroService.class);

        Call<ArrayList<Livro>> chamada = service.buscaLivros();

        chamada.enqueue(new Callback<ArrayList<Livro>>() {
            @Override
            public void onResponse(Call<ArrayList<Livro>> call, Response<ArrayList<Livro>> response) {
                delegate.lidaCom(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Livro>> call, Throwable t) {
                delegate.lidaCom(t);

            }
        });
    }
}
