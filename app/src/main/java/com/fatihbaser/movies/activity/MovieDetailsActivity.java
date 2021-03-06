package com.fatihbaser.movies.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fatihbaser.movies.R;
import com.fatihbaser.movies.database.MovieData;
import com.fatihbaser.movies.database.RoomDB;
import com.fatihbaser.movies.model.FavoriteListModel;
import com.fatihbaser.movies.model.MovieDetailsModel;
import com.fatihbaser.movies.service.ApiService;
import com.fatihbaser.movies.service.RetroIntance;
import com.fatihbaser.movies.view.fragment.FavoriteFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsActivity extends AppCompatActivity {

    ImageView movieDetailsPoster;
    ImageView movieDetailsTitleImage;
    TextView titleDetails;
    TextView languageDetails;
    TextView voteDetails;
    TextView overviewDetails;
    Button homepageButton;
    TextView homepageLink;
    Button trailerButton;
    ImageView detailsFavorite;

    public String BASE_URL = "https://api.themoviedb.org";
    public String API_KEY = "1bf3c5469807a4b8cb7a0a8a888014b0";
    public static final String BASE_PHOTO_URL = "https://image.tmdb.org/t/p/w500";
    public Retrofit retrofit;
    int id = 0;
    String posterPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieDetailsPoster = findViewById(R.id.movieDetailsPosterImage);
        movieDetailsTitleImage = findViewById(R.id.movieDetailsTitleImage);
        titleDetails = findViewById(R.id.titleDetails);
        languageDetails = findViewById(R.id.languageDetails);
        voteDetails = findViewById(R.id.voteDetails);
        overviewDetails = findViewById(R.id.overviewDetails);
        homepageButton = findViewById(R.id.homepageButton);
        homepageLink = findViewById(R.id.homepageLinkTitle);
        trailerButton = findViewById(R.id.trailerButton);
        detailsFavorite = findViewById(R.id.detailsActivityFavorite);



        Intent intent = getIntent();
        id = intent.getIntExtra("movie_id",791373);

        ApiService apiService = RetroIntance.getRetroClient().create(ApiService.class);

        Call<MovieDetailsModel> call = apiService.getDetails(id,API_KEY);

        call.enqueue(new Callback<MovieDetailsModel>() {
            @Override
            public void onResponse(Call<MovieDetailsModel> call, Response<MovieDetailsModel> response) {

                if(response.isSuccessful()){

                    MovieDetailsModel model = response.body();

                    posterPath = model.getPoster_path();

                    if (posterPath == null) {
                        movieDetailsPoster.setImageDrawable(MovieDetailsActivity.this.getDrawable(R.drawable.no_poster));
                    } else {
                        Glide.with(MovieDetailsActivity.this).load(BASE_PHOTO_URL + posterPath).into(movieDetailsPoster);
                    }

                    titleDetails.setText(model.getOriginal_title());
                    languageDetails.setText("Language : " + model.getOriginal_language());
                    voteDetails.setText("Vote : " + model.getVote_average());
                    overviewDetails.setText(model.getOverview());
                    homepageLink.setText(model.getHomepage());

                }
            }

            @Override
            public void onFailure(Call<MovieDetailsModel> call, Throwable t) {
                Toast.makeText(MovieDetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        homepageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToWebsite();
            }
        });

        trailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goTrailer();
            }
        });

        detailsFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favorite();
            }
        });

    }


    public void goToWebsite(){
        if(homepageLink.getText().toString().isEmpty()){
            Toast.makeText(MovieDetailsActivity.this, "No Homepage of movie!", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(homepageLink.getText().toString()));
            startActivity(intent1);
        }
    }

    public void goTrailer(){
        String movie = titleDetails.getText().toString() + " trailer";
        Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query=" + movie));
        startActivity(intent1);
    }

    public void favorite(){
        MovieData movieData = new MovieData(id,titleDetails.getText().toString(),posterPath);
        int id = movieData.getMovie_id();

        int checkInDatabase = RoomDB.database.dao().getDatabaseMovie(id);

        if(checkInDatabase == 0){
            FavoriteFragment.viewModel.insert(movieData);
            Toast.makeText(getApplicationContext(), "Added!", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(getApplicationContext(), "You already have that movie in list!", Toast.LENGTH_SHORT).show();
        }
    }

}