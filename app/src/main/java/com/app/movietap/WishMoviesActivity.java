package com.app.movietap;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.app.movietap.model.Movie;
import com.app.movietap.tools.ActivityTools;
import com.app.movietap.tools.JsonTools;
import com.app.movietap.ui.MovieList;

import java.util.List;


public class WishMoviesActivity extends ListActivity
{
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_browse_movies);
    String jsonResult = "{\"page\":1,\"results\":[{\"adult\":false,\"backdrop_path\":\"/dB2rATwfCbsPGfRLIoluBnKdVHb.jpg\",\"id\":128,\"original_title\":\"もののけ姫\",\"release_date\":\"1997-07-12\",\"poster_path\":\"/gzlJkVfWV5VEG5xK25cvFGJgkDz.jpg\",\"popularity\":6.56905248558563,\"title\":\"Princess Mononoke\",\"vote_average\":7.9,\"vote_count\":491},{\"adult\":false,\"backdrop_path\":\"/hNFMawyNDWZKKHU4GYCBz1krsRM.jpg\",\"id\":550,\"original_title\":\"Fight Club\",\"release_date\":\"1999-10-14\",\"poster_path\":\"/hpt3aa5i0TrSAnEdl3VJrRrje8C.jpg\",\"popularity\":3.58533616835924,\"title\":\"Fight Club\",\"vote_average\":7.7,\"vote_count\":3065},{\"adult\":false,\"backdrop_path\":\"/yQeEMjnBOIAtFqIuEphyesH0a6y.jpg\",\"id\":603,\"original_title\":\"The Matrix\",\"release_date\":\"1999-03-30\",\"poster_path\":\"/gynBNzwyaHKtXqlEKKLioNkjKgN.jpg\",\"popularity\":3.35949892821104,\"title\":\"The Matrix\",\"vote_average\":7.5,\"vote_count\":3900},{\"adult\":false,\"backdrop_path\":\"/9Kjxr4VCU0Y4DAuXkzR2moAy7DK.jpg\",\"id\":863,\"original_title\":\"Toy Story 2\",\"release_date\":\"1999-11-18\",\"poster_path\":\"/gd12nM0VnrgbaUmZDjte4oBedGZ.jpg\",\"popularity\":3.02990658505435,\"title\":\"Toy Story 2\",\"vote_average\":6.8,\"vote_count\":1003},{\"adult\":false,\"backdrop_path\":\"/rtG5TRrQXf11jlO9WqcYTq46jKa.jpg\",\"id\":1893,\"original_title\":\"Star Wars: Episode I - The Phantom Menace\",\"release_date\":\"1999-05-19\",\"poster_path\":\"/n8V09dDc02KsSN6Q4hC2BX6hN8X.jpg\",\"popularity\":2.85563777533204,\"title\":\"Star Wars: Episode I - The Phantom Menace\",\"vote_average\":6.2,\"vote_count\":1365},{\"adult\":false,\"backdrop_path\":\"/mOXpgCVPy43L3Sbq9keft6VSQ7O.jpg\",\"id\":9732,\"original_title\":\"The Lion King 2: Simba's Pride\",\"release_date\":\"1998-10-26\",\"poster_path\":\"/d1Wj5wfzu4CWvKOIL1l42NBYnzE.jpg\",\"popularity\":2.81256706124212,\"title\":\"The Lion King 2: Simba's Pride\",\"vote_average\":6.3,\"vote_count\":202},{\"adult\":false,\"backdrop_path\":\"/fVcZErSWa7gyENuj8IWp8eAfCnL.jpg\",\"id\":597,\"original_title\":\"Titanic\",\"release_date\":\"1997-11-01\",\"poster_path\":\"/f9iH7Javzxokvnkiz2yHD1dcmUy.jpg\",\"popularity\":2.52387508596119,\"title\":\"Titanic\",\"vote_average\":6.9,\"vote_count\":2511},{\"adult\":false,\"backdrop_path\":\"/jlU4FvYxUXaxyYM0worlD7wHdQj.jpg\",\"id\":2105,\"original_title\":\"American Pie\",\"release_date\":\"1999-07-09\",\"poster_path\":\"/dxkSApFLkKkcEQgjCH2KPwTHvw4.jpg\",\"popularity\":2.40467838986344,\"title\":\"American Pie\",\"vote_average\":6.2,\"vote_count\":670},{\"adult\":false,\"backdrop_path\":\"/z5J18ByLeOZ68iORfMXmxeFuwyL.jpg\",\"id\":14,\"original_title\":\"American Beauty\",\"release_date\":\"1999-09-15\",\"poster_path\":\"/3UBQGKS8c1dxRnDiu5kUK6ej3pP.jpg\",\"popularity\":1.96211018375592,\"title\":\"American Beauty\",\"vote_average\":7.1,\"vote_count\":616},{\"adult\":false,\"backdrop_path\":\"/dATygzDbQGJWlolx2vebgqFYAKO.jpg\",\"id\":497,\"original_title\":\"The Green Mile\",\"release_date\":\"1999-12-05\",\"poster_path\":\"/3yJUlOtVa09CYJocwBU8eAryja0.jpg\",\"popularity\":1.84501049586687,\"title\":\"The Green Mile\",\"vote_average\":7.6,\"vote_count\":991},{\"adult\":false,\"backdrop_path\":\"/3qthpSSyBY6Efeu1sqkO8L1Eyyb.jpg\",\"id\":564,\"original_title\":\"The Mummy\",\"release_date\":\"1999-05-06\",\"poster_path\":\"/cftmDzVCWKynKMfY9oyFj7igFqJ.jpg\",\"popularity\":1.77658720147219,\"title\":\"The Mummy\",\"vote_average\":6.0,\"vote_count\":823},{\"adult\":false,\"backdrop_path\":\"/gOvW00ZMoEiyRwXVkHPfBictPAl.jpg\",\"id\":9487,\"original_title\":\"A Bug's Life\",\"release_date\":\"1998-11-25\",\"poster_path\":\"/5GaLUp9m37BmJRJdFUFMobB275S.jpg\",\"popularity\":1.74316833801344,\"title\":\"A Bug's Life\",\"vote_average\":6.4,\"vote_count\":503},{\"adult\":false,\"backdrop_path\":\"/8H1uNyMIIQsEQJ3LrTt0A5K01Ou.jpg\",\"id\":36647,\"original_title\":\"Blade\",\"release_date\":\"1998-08-19\",\"poster_path\":\"/kR3DscGbvJ5NnYhTOuMAlmEtYYD.jpg\",\"popularity\":1.57969337195618,\"title\":\"Blade\",\"vote_average\":6.1,\"vote_count\":648},{\"adult\":false,\"backdrop_path\":\"/4xAb2zAe5Pmno928hBQqff4RVwr.jpg\",\"id\":36643,\"original_title\":\"The World Is Not Enough\",\"release_date\":\"1999-11-21\",\"poster_path\":\"/tCarTEKvXjALk87r3wAxB4jb1ON.jpg\",\"popularity\":1.28403102357185,\"title\":\"The World Is Not Enough\",\"vote_average\":6.1,\"vote_count\":205},{\"adult\":false,\"backdrop_path\":\"/bPLMuxtQ9osxzz5q34KcFVtnOpv.jpg\",\"id\":345,\"original_title\":\"Eyes Wide Shut\",\"release_date\":\"1999-07-16\",\"poster_path\":\"/c8X6UIbzbhBBuyuHRrzVzaCMbkZ.jpg\",\"popularity\":1.26090681782344,\"title\":\"Eyes Wide Shut\",\"vote_average\":6.6,\"vote_count\":196},{\"adult\":false,\"backdrop_path\":\"/lLzrg3yP43T63CaNkDm2VqZUAFU.jpg\",\"id\":11932,\"original_title\":\"Bride of Chucky\",\"release_date\":\"1998-10-16\",\"poster_path\":\"/u5Lc1Li0Hpc452o57E2KaToezZX.jpg\",\"popularity\":1.25343080839843,\"title\":\"Bride of Chucky\",\"vote_average\":6.0,\"vote_count\":42},{\"adult\":false,\"backdrop_path\":\"/y67X8DSDEAVcfo9aqX6yipsTsrX.jpg\",\"id\":745,\"original_title\":\"The Sixth Sense\",\"release_date\":\"1999-08-02\",\"poster_path\":\"/wW1y9pvMejWjdfz0mpbB3Ag3SJf.jpg\",\"popularity\":1.19916169583568,\"title\":\"The Sixth Sense\",\"vote_average\":7.1,\"vote_count\":804},{\"adult\":false,\"backdrop_path\":\"/11XLucJQvdgtchSfO0ZTbo966pp.jpg\",\"id\":10386,\"original_title\":\"The Iron Giant\",\"release_date\":\"1999-08-06\",\"poster_path\":\"/d7nS7jdbhG6xa3DjmoBGdqTsGa6.jpg\",\"popularity\":1.19534659191444,\"title\":\"The Iron Giant\",\"vote_average\":7.1,\"vote_count\":312},{\"adult\":false,\"backdrop_path\":\"/i9A0UMFg1hI2kLyCCwnmSbpT2cd.jpg\",\"id\":73,\"original_title\":\"American History X\",\"release_date\":\"1998-10-30\",\"poster_path\":\"/fXepRAYOx1qC3wju7XdDGx60775.jpg\",\"popularity\":1.14133317225374,\"title\":\"American History X\",\"vote_average\":7.6,\"vote_count\":565},{\"adult\":false,\"backdrop_path\":\"/mrbTLFsYup7iIXgJzxJVLaitcwZ.jpg\",\"id\":637,\"original_title\":\"La vita è bella\",\"release_date\":\"1997-12-20\",\"poster_path\":\"/f7DImXDebOs148U4uPjI61iDvaK.jpg\",\"popularity\":1.12661678225438,\"title\":\"Life Is Beautiful\",\"vote_average\":7.9,\"vote_count\":724}],\"total_pages\":144,\"total_results\":2876}";
    _movies = JsonTools.getMovies(jsonResult);

    MovieList adapter = new MovieList(WishMoviesActivity.this, _movies);
    _list = (ListView) findViewById(android.R.id.list);
    _list.setAdapter(adapter);
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id)
  {
    Intent goToDetail = new Intent(WishMoviesActivity.this, MovieDetailActivity.class);
    goToDetail.putExtra("movie", _movies.get(position));
    startActivity(goToDetail);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    getMenuInflater().inflate(R.menu.menu_navigation, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    super.onOptionsItemSelected(item);

    return ActivityTools.HandleOptionsItemSelected(item, this);
  }

  private ListView _list;
  private List<Movie> _movies;
}
