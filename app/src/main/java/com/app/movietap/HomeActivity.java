package com.app.movietap;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.app.movietap.util.SystemUiHider;

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
