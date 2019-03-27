package nilo.de.cafe.cafedenilopos.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUrl {
    private static Retrofit retrofit;
    public static final String BASE_URL = "http://192.168.100.57/CafeDeniloPOS/public/";
    //public static final String BASE_URL = "http://www.2ez4dan.com/public/";
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
        }