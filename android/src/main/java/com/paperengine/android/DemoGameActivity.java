package com.paperengine.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import com.paperengine.core.PaperGame;

public class DemoGameActivity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new PaperGame());
  }
}
