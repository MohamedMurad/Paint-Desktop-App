package com.oop.paint;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyClassLoader extends ClassLoader{
    JFileChooser chooser;
    Load load ;
    public MyClassLoader() {
    	load = new Load();
    }
    public void loader() {
        try {
          final File afile = load.getDynamicFile();
          if(afile ==null){
        	  return;
          }
          final java.io.File file = new java.io.File("");
          String absolutePath = file.getAbsolutePath();
          absolutePath = absolutePath + "\\bin\\com\\oop\\paint\\";
          afile.renameTo(new File(absolutePath + afile.getName()));
        } catch (final Exception e) {
        }
    }
}