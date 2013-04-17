package com.oe.mobile;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;

public class AChartActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achart);
        
        try{
            System.out.println("before new");

            Intent achartIntent = new AChartExample(new double[]{3,2,1}).execute(this);
            System.out.println("before startactivity");
            startActivity(achartIntent);
            System.out.println("after startactivity");
            }catch (Exception e){
                 
                Log.d("oncreate",e.getMessage());
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_achart, menu);
        return true;
    }
}
