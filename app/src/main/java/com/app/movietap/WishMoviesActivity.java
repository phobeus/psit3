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

    //String jsonResult = JsonTools.getJSON("http://api.themoviedb.org/3/search/company?api_key=ec098c57b57433b5b52376f6425884ef&include_adult=true&sort_by=popularity.desc&query=digital");
    String jsonResult = "{\"page\":1,\"results\":[{\"adult\":false,\"backdrop_path\":\"/xu9zaAevzQ5nnrsXN6JcahLnG4i.jpg\",\"id\":157336,\"original_title\":\"Interstellar\",\"release_date\":\"2014-11-05\",\"poster_path\":\"/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg\",\"popularity\":53.9883564398947,\"title\":\"Interstellar\",\"vote_average\":8.8,\"vote_count\":626},{\"adult\":false,\"backdrop_path\":\"/83nHcz2KcnEpPXY50Ky2VldewJJ.jpg\",\"id\":131631,\"original_title\":\"The Hunger Games: Mockingjay - Part 1\",\"release_date\":\"2014-11-21\",\"poster_path\":\"/cWERd8rgbw7bCMZlwP207HUXxym.jpg\",\"popularity\":51.3331072549105,\"title\":\"The Hunger Games: Mockingjay - Part 1\",\"vote_average\":7.7,\"vote_count\":149},{\"adult\":false,\"backdrop_path\":\"/1eh71zFRnblNsnb2NCU7plqFfd3.jpg\",\"id\":118340,\"original_title\":\"Guardians of the Galaxy\",\"release_date\":\"2014-08-01\",\"poster_path\":\"/9gm3lL8JMTTmc3W4BmNMCuRLdL8.jpg\",\"popularity\":43.7177913113442,\"title\":\"Guardians of the Galaxy\",\"vote_average\":8.6,\"vote_count\":1210},{\"adult\":false,\"backdrop_path\":\"/uD5jAPoIrditaG83dKbsRmG83vH.jpg\",\"id\":240832,\"original_title\":\"Lucy\",\"release_date\":\"2014-07-25\",\"poster_path\":\"/rwn876MeqienhOVSSjtUPnwxn0Z.jpg\",\"popularity\":32.6869912551011,\"title\":\"Lucy\",\"vote_average\":6.6,\"vote_count\":725},{\"adult\":false,\"backdrop_path\":\"/rjUl3pd1LHVOVfG4IGcyA1cId5l.jpg\",\"id\":119450,\"original_title\":\"Dawn of the Planet of the Apes\",\"release_date\":\"2014-07-11\",\"poster_path\":\"/2EUAUIu5lHFlkj5FRryohlH6CRO.jpg\",\"popularity\":20.3914416282171,\"title\":\"Dawn of the Planet of the Apes\",\"vote_average\":7.8,\"vote_count\":781},{\"adult\":false,\"backdrop_path\":\"/2BXd0t9JdVqCp9sKf6kzMkr7QjB.jpg\",\"id\":177572,\"original_title\":\"Big Hero 6\",\"release_date\":\"2014-11-07\",\"poster_path\":\"/3zQvuSAUdC3mrx9vnSEpkFX0968.jpg\",\"popularity\":19.2655238889083,\"title\":\"Big Hero 6\",\"vote_average\":8.3,\"vote_count\":61},{\"adult\":false,\"backdrop_path\":\"/aBkkrhQS4rO3u1OTahywtSXu3It.jpg\",\"id\":127585,\"original_title\":\"X-Men: Days of Future Past\",\"release_date\":\"2014-05-23\",\"poster_path\":\"/qKkFk9HELmABpcPoc1HHZGIxQ5a.jpg\",\"popularity\":15.9933855242292,\"title\":\"X-Men: Days of Future Past\",\"vote_average\":7.8,\"vote_count\":1241},{\"adult\":false,\"backdrop_path\":\"/ocnCspmaIbS1XAkz1G95EfSWokM.jpg\",\"id\":100042,\"original_title\":\"Dumb and Dumber To\",\"release_date\":\"2014-11-14\",\"poster_path\":\"/Ekw3ijq9L6RiKvv5m2tPOEklHF.jpg\",\"popularity\":15.2276345996654,\"title\":\"Dumb and Dumber To\",\"vote_average\":7.2,\"vote_count\":30},{\"adult\":false,\"backdrop_path\":\"/6UPlIYKxZqUR6Xbpgu1JKG0J7UC.jpg\",\"id\":49017,\"original_title\":\"Dracula Untold\",\"release_date\":\"2014-10-03\",\"poster_path\":\"/qGnh1B8rm5pmlxVSdUcTPm0RQPB.jpg\",\"popularity\":14.6537487823627,\"title\":\"Dracula Untold\",\"vote_average\":6.9,\"vote_count\":219},{\"adult\":false,\"backdrop_path\":\"/es5xJSuwtMyFRVx0d8Dx44Mu39B.jpg\",\"id\":91314,\"original_title\":\"Transformers: Age of Extinction\",\"release_date\":\"2014-06-27\",\"poster_path\":\"/ykIZB9dYBIKV13k5igGFncT5th6.jpg\",\"popularity\":14.6200459433301,\"title\":\"Transformers: Age of Extinction\",\"vote_average\":6.4,\"vote_count\":798},{\"adult\":false,\"backdrop_path\":\"/khzRw7ZDE3NIrYpe0RYyB864FPT.jpg\",\"id\":189,\"original_title\":\"Sin City: A Dame to Kill For\",\"release_date\":\"2014-08-22\",\"poster_path\":\"/k80qKrJ0qQ6ocVo5N932stNSg6j.jpg\",\"popularity\":13.2819529935107,\"title\":\"Sin City: A Dame to Kill For\",\"vote_average\":6.6,\"vote_count\":212},{\"adult\":false,\"backdrop_path\":\"/oSno71x3xS7ic311ktzzpVjxzEF.jpg\",\"id\":254904,\"original_title\":\"The November Man\",\"release_date\":\"2014-08-27\",\"poster_path\":\"/lcg1bkriMVvc8m0jz5zk0yvzVTA.jpg\",\"popularity\":13.1694059812819,\"title\":\"The November Man\",\"vote_average\":6.1,\"vote_count\":65},{\"adult\":false,\"backdrop_path\":\"/dkbX258MMMSG4oeKWLjQyVeGcUz.jpg\",\"id\":98566,\"original_title\":\"Teenage Mutant Ninja Turtles\",\"release_date\":\"2014-08-08\",\"poster_path\":\"/oDL2ryJ0sV2bmjgshVgJb3qzvwp.jpg\",\"popularity\":11.8129354321077,\"title\":\"Teenage Mutant Ninja Turtles\",\"vote_average\":6.5,\"vote_count\":313},{\"adult\":false,\"backdrop_path\":\"/jboSjg1obRJPKNyMUFW4afgHSRX.jpg\",\"id\":272158,\"original_title\":\"劇場版 仮面ライダー鎧武 サッカー大決戦!黄金の果実争奪杯!\",\"release_date\":\"2014-06-19\",\"poster_path\":\"/kYJbGCPe2UaRZE6UE1ngStrJzwr.jpg\",\"popularity\":11.5696511507973,\"title\":\"Kamen Rider Gaim the Movie: The Great Soccer Match! The Golden Fruit Cup!\",\"vote_average\":4.0,\"vote_count\":1},{\"adult\":false,\"backdrop_path\":\"/cHTJxPcq3hlshzOgHDRMCF8Wb9K.jpg\",\"id\":82702,\"original_title\":\"How to Train Your Dragon 2\",\"release_date\":\"2014-06-13\",\"poster_path\":\"/Ak8fnAmoTBkXsdYULWXAZA79XB6.jpg\",\"popularity\":10.6980612963243,\"title\":\"How to Train Your Dragon 2\",\"vote_average\":8.2,\"vote_count\":527},{\"adult\":false,\"backdrop_path\":\"/5ldMfEYNk0fea8QQ0mjyrksIr1M.jpg\",\"id\":138103,\"original_title\":\"The Expendables 3\",\"release_date\":\"2014-08-15\",\"poster_path\":\"/fLfpaNso9leKIMp72CjvHEC7VLv.jpg\",\"popularity\":10.0072198359251,\"title\":\"The Expendables 3\",\"vote_average\":6.6,\"vote_count\":405},{\"adult\":false,\"backdrop_path\":\"/7mgKeg18Qml5nJQa56RBZO7dIu0.jpg\",\"id\":137113,\"original_title\":\"Edge of Tomorrow\",\"release_date\":\"2014-06-06\",\"poster_path\":\"/tpoVEYvm6qcXueZrQYJNRLXL88s.jpg\",\"popularity\":9.52716805095836,\"title\":\"Edge of Tomorrow\",\"vote_average\":7.8,\"vote_count\":963},{\"adult\":false,\"backdrop_path\":\"/4qfXT9BtxeFuamR4F49m2mpKQI1.jpg\",\"id\":100402,\"original_title\":\"Captain America: The Winter Soldier\",\"release_date\":\"2014-04-04\",\"poster_path\":\"/pH4oeiZAh9a40tly4D0l6aAB3ms.jpg\",\"popularity\":9.44226775361736,\"title\":\"Captain America: The Winter Soldier\",\"vote_average\":7.8,\"vote_count\":1218},{\"adult\":false,\"backdrop_path\":\"/c07g3JVBJ6dSO50PsrNG1N5VYmu.jpg\",\"id\":101299,\"original_title\":\"The Hunger Games: Catching Fire\",\"release_date\":\"2013-11-22\",\"poster_path\":\"/tAhSyLxpaZJCr1oc2a3flvC2B7x.jpg\",\"popularity\":8.55926493519264,\"title\":\"The Hunger Games: Catching Fire\",\"vote_average\":7.7,\"vote_count\":1320},{\"adult\":false,\"backdrop_path\":\"/5CdgKsD4Rg87XIXmQSSomod5VCH.jpg\",\"id\":184315,\"original_title\":\"Hercules\",\"release_date\":\"2014-07-25\",\"poster_path\":\"/wGAWxOnVjwXqvoiKX7Pf6XOWnPJ.jpg\",\"popularity\":8.45099025136937,\"title\":\"Hercules\",\"vote_average\":6.0,\"vote_count\":288}],\"total_pages\":10100,\"total_results\":201992}";
    _movies = JsonTools.getMovies(jsonResult);

    MovieList adapter = new MovieList(WishMoviesActivity.this, _movies);
    _list = (ListView) findViewById(android.R.id.list);
    _list.setAdapter(adapter);
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id)
  {
      Intent goToDetail = new Intent(WishMoviesActivity.this, MovieDetailActivity.class);
      goToDetail.putExtra("title", _movies.get(position).getTitle());
      goToDetail.putExtra("poster", _movies.get(position).getPoster());
      goToDetail.putExtra("release", _movies.get(position).getReleaseDate());
      goToDetail.putExtra("vote", _movies.get(position).getVoteAverage());
      startActivity(goToDetail);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_navigation, menu);
    return true;
  }


  /**
   * react to actionbar events by forwarding user to desired activity.
   */
  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    super.onOptionsItemSelected(item);

    return ActivityTools.HandleOptionsItemSelected(item, this);
  }

  private ListView _list;
  private List<Movie> _movies;
}
