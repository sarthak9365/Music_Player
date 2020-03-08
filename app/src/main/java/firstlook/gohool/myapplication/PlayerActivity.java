package firstlook.gohool.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    Button btn_next,btn_previous,btn_pause;
    ImageView imageView;
    TextView songtext;
    SeekBar songseekBar;
    static MediaPlayer mediaPlayer;
    int position;
    String sname;
    ArrayList<File> mysongs;
    Thread updateseekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
       btn_next=(Button)findViewById(R.id.next);
       btn_previous=(Button)findViewById(R.id.previous);
        btn_pause=(Button)findViewById(R.id.pause);
        songtext=(TextView)findViewById(R.id.songtext);
        songseekBar=(SeekBar)findViewById(R.id.seekbaar);
        imageView=(ImageView)findViewById(R.id.image);

        //getSupportActionBar().setTitle("Now Playing");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        updateseekBar=new Thread(){
            @Override
            public void run() {

                int totalduration = mediaPlayer.getDuration();
                int currentposition = 0;
                while(currentposition<totalduration){
                    try {
                        sleep(500);
                        currentposition=mediaPlayer.getCurrentPosition();
                        songseekBar.setProgress(currentposition);
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }

                }


            }
        };
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        mysongs =(ArrayList)bundle.getParcelableArrayList("songs");
        sname=mysongs.get(position).getName().toString();
        String songname = intent.getStringExtra("songname");
        songtext.setText(songname);
        songtext.setSelected(true);

        position = bundle.getInt("pos",0);

        Uri uri =Uri.parse(mysongs.get(position).toString());

        mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);

        mediaPlayer.start();
        songseekBar.setMax(mediaPlayer.getDuration());
        updateseekBar.start();
        songseekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        songseekBar.getThumb().setColorFilter(getResources().getColor(R.color.colorPrimary),PorterDuff.Mode.SRC_IN);
        songseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songseekBar.setMax(mediaPlayer.getDuration());

                if(mediaPlayer.isPlaying()){
                    btn_pause.setBackgroundResource(R.drawable.ic_play);
                    mediaPlayer.pause();
                }
                else{
                    btn_pause.setBackgroundResource(R.drawable.ic_pause);
                    mediaPlayer.start();
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position+1)%mysongs.size());
                Uri uri = Uri.parse(mysongs.get(position).toString());

                mediaPlayer= MediaPlayer.create(getApplicationContext(),uri);
                songtext.setText(mysongs.get(position).getName().toString());
                mediaPlayer.start();
            }
        });
        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position =((position-1)<0)?(mysongs.size()-1):(position-1);
                Uri uri = Uri.parse(mysongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
                songtext.setText(mysongs.get(position).getName().toString());
                mediaPlayer.start();

            }
        });




    }
    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }


        return super.onOptionsItemSelected(item);
    }



}
