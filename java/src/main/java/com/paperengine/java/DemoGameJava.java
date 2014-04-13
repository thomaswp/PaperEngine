package com.paperengine.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import com.paperengine.core.DemoGame;

public class DemoGameJava {

  public static void main(String[] args) {
    JavaPlatform.Config config = new JavaPlatform.Config();
    // use config to customize the Java platform, if needed
    JavaPlatform.register(config);
    PlayN.run(new DemoGame());
  }
}
