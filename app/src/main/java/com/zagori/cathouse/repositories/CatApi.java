package com.zagori.cathouse.repositories;

import com.zagori.cathouse.models.Breed;
import com.zagori.cathouse.models.Image;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CatApi {

    @GET("breeds")
    Single<List<Breed>> getBreeds();

    @GET("images/search")
    Single<List<Image>> getBreedImages(@Query("breed_id") String breed_id,
                                       @Query("limit") int limit,
                                       @Query("size") String size,
                                       @Query("mime_types") String[] mime_types);
}
