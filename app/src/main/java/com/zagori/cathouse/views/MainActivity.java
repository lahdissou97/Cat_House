package com.zagori.cathouse.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zagori.cathouse.R;
import com.zagori.cathouse.models.Breed;
import com.zagori.cathouse.models.Image;

import com.zagori.cathouse.models.Response;
import com.zagori.cathouse.utils.Constants;
import com.zagori.cathouse.viewmodels.BreedsViewModel;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<String> spinnerBreedList;
    private List<Breed> breeds;
    private List<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView breedName = findViewById(R.id.breed_name);
        final TextView breedDescription = findViewById(R.id.breed_description);
        final TextView breedWeight = findViewById(R.id.breed_weight);
        final TextView breedLifeSpan = findViewById(R.id.breed_life_span);
        final TextView breedOrigin = findViewById(R.id.breed_origin);
        final TextView breedTemperament = findViewById(R.id.breed_temperament);
        final ImageView breedImage = findViewById(R.id.breed_image);

        spinnerBreedList = new ArrayList<>();
        images = new ArrayList<>();

        // supply the spinner with the breeds array
        Spinner spinner = findViewById(R.id.breeds_spinner);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, spinnerBreedList){
            @Override
            public boolean isEnabled(int position) {
                // disable the 1st item, as it is used as a hint
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView dropdownItem = (TextView) view;
                dropdownItem.setTextColor(position == 0 ? Color.GRAY : Color.WHITE);
                return view;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final BreedsViewModel breedsViewModel = new ViewModelProvider(this).get(BreedsViewModel.class);
        breedsViewModel.init();
        breedsViewModel.getBreeds().observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {
                switch (response.getStatus()) {
                    case SUCCESS:

                        breeds = response.getData();

                        if (breeds == null){
                            Toast.makeText(MainActivity.this, getString(R.string.global_error), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        spinnerBreedList.clear();

                        for (Breed breed : breeds) {
                            spinnerBreedList.add(breed.getName());
                        }

                        spinnerBreedList.add(0, getString(R.string.spinner_breed_hint));
                        adapter.notifyDataSetChanged();

                        findViewById(R.id.progress_bar).setVisibility(View.GONE);
                        break;

                    case ERROR:
                        findViewById(R.id.progress_bar).setVisibility(View.GONE);
                        Log.e(TAG, getString(R.string.global_error).concat(": ") + response.getError());
                        Toast.makeText(MainActivity.this, getString(R.string.global_error), Toast.LENGTH_SHORT).show();
                        break;

                    case LOADING:
                        findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        breedsViewModel.getBreedImages().observe(this, new Observer<Response>() {
            @Override
            public void onChanged(Response response) {
                switch (response.getStatus()){

                    case SUCCESS:

                        if (response.getData() == null){
                            Toast.makeText(MainActivity.this, getString(R.string.global_error), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        for (Image image : (List<Image>)response.getData()) {
                            images.add(image.getUrl());
                        }

                        Picasso.get().load(images.get(0))
                                .placeholder(R.drawable.ic_cat_placeholder_light).into(breedImage);
                        break;

                    case ERROR:
                        Log.e(TAG, getString(R.string.global_error).concat(": ") + response.getError());
                        Toast.makeText(MainActivity.this, getString(R.string.global_error), Toast.LENGTH_SHORT).show();
                        break;

                    case LOADING:
                        break;
                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i==0){
                    findViewById(R.id.breed_detail_layout).setVisibility(View.GONE);
                    return;
                }

                Breed selectedBreed = null;

                for (Breed breed : breeds) {
                    if (breed.getName().equals(adapterView.getItemAtPosition(i))){
                        selectedBreed = breed;
                        break;
                    }
                }

                if (selectedBreed == null)return;

                breedsViewModel.loadBreedImages(selectedBreed.getId(), Constants.IMAGES_LIMIT,
                        Constants.Image_SIZE_MED, getResources().getStringArray(R.array.meme_types));

                breedName.setText(selectedBreed.getName());
                breedDescription.setText(selectedBreed.getDescription());
                breedWeight.setText(getString(R.string.breed_weight, selectedBreed.getWeight().getMetric()));
                breedLifeSpan.setText(getString(R.string.breed_life_span, selectedBreed.getLife_span()));
                breedOrigin.setText(selectedBreed.getOrigin());
                breedTemperament.setText(selectedBreed.getTemperament());

                findViewById(R.id.breed_detail_layout).setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }

    public void saveBreed(View view){
        Toast.makeText(this, "Feature is being developed", Toast.LENGTH_SHORT).show();
    }
}
