package com.example.anshubhardwaj.cinemabuzz.api;

import com.example.anshubhardwaj.cinemabuzz.object.CastResponse;
import com.example.anshubhardwaj.cinemabuzz.object.MovieDetailObject;
import com.example.anshubhardwaj.cinemabuzz.object.MovieResponse;
import com.example.anshubhardwaj.cinemabuzz.object.MovieReviewResponse;
import com.example.anshubhardwaj.cinemabuzz.object.PeopleDetailResponse;
import com.example.anshubhardwaj.cinemabuzz.object.PeopleMovieResponse;
import com.example.anshubhardwaj.cinemabuzz.object.PeopleResponse;
import com.example.anshubhardwaj.cinemabuzz.object.PeopleTvShowResponse;
import com.example.anshubhardwaj.cinemabuzz.object.ProfileImageResponse;
import com.example.anshubhardwaj.cinemabuzz.object.SeasonObject;
import com.example.anshubhardwaj.cinemabuzz.object.TvShowDetailObject;
import com.example.anshubhardwaj.cinemabuzz.object.TvShowResponse;
import com.example.anshubhardwaj.cinemabuzz.object.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(@Query("api_key") String API_KEY, @Query("page") int page, @Query("region") String region);
    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String API_KEY,@Query("page") int page,@Query("region") String region);
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovie(@Query("api_key") String API_KEY,@Query("page") int page);
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovie(@Query("api_key") String API_KEY,@Query("page") int page);
    @GET("movie/{id}")
    Call<MovieDetailObject> getMovieDetail(@Path("id") int id, @Query("api_key") String API_KEY);
    @GET("movie/{id}/videos")
    Call<VideoResponse> getMovieTrailer(@Path("id") int id, @Query("api_key") String API_KEY);
    @GET("movie/{id}/credits")
    Call<CastResponse> getMovieCast(@Path("id") int id, @Query("api_key") String API_KEY);
    @GET("movie/{id}/reviews")
    Call<MovieReviewResponse> getMovieReview(@Path("id") int id, @Query("api_key") String API_KEY);
    @GET("movie/{id}/similar")
    Call<MovieResponse> getSimilarMovie(@Path("id") int id,@Query("api_key") String API_KEY,@Query("page") int page);
    @GET("person/popular")
    Call<PeopleResponse> getPopularPeople(@Query("api_key") String API_KEY, @Query("page") int page);
    @GET("person/{person_id}")
    Call<PeopleDetailResponse> getpeopleDetail(@Path("person_id") int id, @Query("api_key") String API_KEY);
    @GET("person/{person_id}/images")
    Call<ProfileImageResponse> getPeopleImages(@Path("person_id") int id, @Query("api_key") String API_KEY);
    @GET("person/{person_id}/movie_credits")
    Call<PeopleMovieResponse> getPeopleMovieList(@Path("person_id") int id, @Query("api_key") String API_KEY);
    @GET("person/{person_id}/tv_credits")
    Call<PeopleTvShowResponse> getPeopleTvShowList(@Path("person_id") int id, @Query("api_key") String API_KEY);
    @GET("tv/airing_today")
    Call<TvShowResponse> getAiringTodayShow(@Query("api_key") String API_KEY, @Query("page") int page);
    @GET("tv/on_the_air")
    Call<TvShowResponse> getOnTheAirShow(@Query("api_key") String API_KEY,@Query("page") int page);
    @GET("tv/popular")
    Call<TvShowResponse> getPopularTvShow(@Query("api_key") String API_KEY,@Query("page") int page);
    @GET("tv/top_rated")
    Call<TvShowResponse> getTopRatedTvShow(@Query("api_key") String API_KEY,@Query("page") int page);
    @GET("tv/{tv_id}")
    Call<TvShowDetailObject> getTvShowDetail(@Path("tv_id") int id, @Query("api_key") String API_KEY);
    @GET("tv/{tv_id}/videos")
    Call<VideoResponse> getVideos(@Path("tv_id") int id,@Query("api_key") String  API_KEY);
    @GET("tv/{tv_id}/similar")
    Call<TvShowResponse> getSimilarTvShow(@Path("tv_id") int id,@Query("api_key") String API_KEY,@Query("page") int page);
    @GET("tv/{tv_id}/credits")
    Call<CastResponse> getCastList(@Path("tv_id") int id,@Query("api_key") String API_KEY);
    @GET("tv/{tv_id}/season/{season_number}")
    Call<SeasonObject> getSeasonList(@Path("tv_id") int id, @Path("season_number") int seasonNumber, @Query("api_key") String API_KEY);

    @GET("search/movie")
    Call<MovieResponse> searchMovies(@Query("api_key") String API_KEY, @Query("query") String query, @Query("page") int page,@Query("region") String region);

    @GET("search/tv")
    Call<TvShowResponse> searchTvShow(@Query("api_key") String API_KEY, @Query("query") String query, @Query("page") int page);

    @GET("search/person")
    Call<PeopleResponse> searchPopularPeople(@Query("api_key") String API_KEY, @Query("query") String query, @Query("page") int page,@Query("region") String region);

}
