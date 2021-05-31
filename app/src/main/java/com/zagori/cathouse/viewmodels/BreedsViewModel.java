package com.zagori.cathouse.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zagori.cathouse.models.Breed;
import com.zagori.cathouse.models.Image;
import com.zagori.cathouse.models.Response;
import com.zagori.cathouse.models.StateLiveData;
import com.zagori.cathouse.repositories.CatApi;
import com.zagori.cathouse.repositories.CatRemoteService;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("unchecked")
public class BreedsViewModel extends ViewModel {

    private static final String TAG = BreedsViewModel.class.getSimpleName();

    private CatApi catApi;
    private StateLiveData breeds;
    private StateLiveData breedImages;

    public void init(){
        catApi = CatRemoteService.createService();
        breeds = new StateLiveData();
        breedImages = new StateLiveData();

        loadBreeds();
    }

    public LiveData<Response> getBreeds(){
        /*if (breeds == null){
            breeds = new StateLiveData();
            catApi = CatRemoteService.createService();
            loadBreeds();
        }*/

        return breeds;
    }

    public LiveData<Response> getBreedImages(){
        /*if (breedImages == null){
            breedImages = new StateLiveData();
            loadBreedImages();
        }*/

        return breedImages;
    }

    /*
     * Perform async operation to fetch breeds
     * */
    private void loadBreeds(){
        catApi.getBreeds()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Breed>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        breeds.postLoading();
                    }

                    @Override
                    public void onSuccess(List<Breed> breedList) {
                        breeds.postSuccess(breedList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        breeds.postError(e);
                        e.printStackTrace();
                    }
                });
    }

    /*
    * Perform async operation to fetch images for a specific breed
    * */
    public void loadBreedImages(String breedId, int imageLimit, String size, String[] memeTypes){
        catApi.getBreedImages(breedId, imageLimit, size, memeTypes)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Image>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        breedImages.postLoading();
                    }

                    @Override
                    public void onSuccess(List<Image> images) {
                        breedImages.postSuccess(images);

                    }

                    @Override
                    public void onError(Throwable e) {
                        breedImages.postError(e);
                        e.printStackTrace();
                    }
                });
    }

}
