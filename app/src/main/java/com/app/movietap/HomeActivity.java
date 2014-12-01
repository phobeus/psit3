package com.app.movietap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends BaseActivity
{
  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_home);
  }

  public void DummyButtonClick(View view)
  {
    Intent intent = new Intent(this, BrowseMoviesActivity.class);
    startActivity(intent);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState)
  {
    super.onPostCreate(savedInstanceState);
  }
}
