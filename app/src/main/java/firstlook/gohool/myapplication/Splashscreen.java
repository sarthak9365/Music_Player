package firstlook.gohool.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;

public class Splashscreen extends AppCompatActivity {
    ImageView img;
    TextView txt;
    Animation myanim1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        img = (ImageView) findViewById(R.id.imageview);
        txt = (TextView) findViewById(R.id.txt1);
        myanim1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        img.startAnimation(myanim1);
        txt.startAnimation(myanim1);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splashscreen.this, MainActivity.class);
                startActivity(i);
            }
        }, 5000);
    }
}