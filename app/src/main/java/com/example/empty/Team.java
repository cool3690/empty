package com.example.empty;

import android.widget.Button;

/**
 * Created by kstanoev on 1/14/2015.
 */
public class Team {
    private String name;

    public Team(String name )
    {
        this.setName(name);

       
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }



}
