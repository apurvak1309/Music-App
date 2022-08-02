    package com.ak.madproject;

    import android.media.MediaPlayer;
    import android.os.Bundle;
    import android.os.Handler;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ImageButton;
    import android.widget.ListView;
    import android.widget.SeekBar;

    import androidx.appcompat.app.AppCompatActivity;

    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
ListView lv1;
private Runnable mRunnable;
Handler mHandler = new Handler();
ImageButton i1,i2,i3,i4;
List<Integer> test = new ArrayList<Integer>();
ArrayList<String> a1,a2;
String[] song ={"Shayad","Photo","Haan Main Galat","Duniyaa"};
String[] artist ={"Love Aaj Kal","Luka Chupi","Love Aaj Kal","Luka Chupi"};
int length=0;
MediaPlayer mp1;
int position1=0,position2=0;
Thread thread;
SeekBar s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv1=(ListView)findViewById(R.id.listView1);
        mp1=new MediaPlayer();
        i1=(ImageButton)findViewById(R.id.imageButton);
        i2=(ImageButton)findViewById(R.id.imageButton1);
        i3=(ImageButton)findViewById(R.id.imageButton2);
        i4=(ImageButton)findViewById(R.id.imageButton3);
        i1.setOnClickListener(this);
        i2.setOnClickListener(this);
        i3.setOnClickListener(this);
        i4.setOnClickListener(this);
        test.add(R.raw.shayad);
        test.add(R.raw.photo);
        test.add(R.raw.haanmaingalat);
        test.add(R.raw.duniyaa);
        s=(SeekBar)findViewById(R.id.seekBar);
        mp1=new MediaPlayer();
        a1=new ArrayList<String>();
        a2=new ArrayList<String>();
        List<String> dataTemp = Arrays.asList(song);
        List<String> dataTemp1 = Arrays.asList(artist);
        a1.addAll(dataTemp);
        a2.addAll(dataTemp1);
        Adapter adp = new Adapter(this, R.layout.activity_second,a1,a2);
        lv1.setAdapter(adp);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               position1=position;
                position2=position;
                mp1.reset();// stops any current playing song
                mp1 = MediaPlayer.create(getApplicationContext(), test.get(position));
                mp1.start(); // starting mediaplayer
            }
        });
    s.setMax(mp1.getDuration());
    s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(mp1 != null && fromUser){
                mp1.seekTo(progress * 1000);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    });
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.imageButton:
                mp1.stop();
                mp1.release();
                position2 =(position2-1<0)?test.size()-1:position2-1;
                mp1 = MediaPlayer.create(getApplicationContext(),test.get(position2));
                mp1.start();
                break;
            case R.id.imageButton1:
                mp1.pause();
                length=mp1.getCurrentPosition();
                break;
            case R.id.imageButton2:
                mp1.seekTo(length);
                mp1.start();
                break;
            case R.id.imageButton3:
                mp1.stop();
                mp1.release();
                position1 =(position1+1)%test.size();
                mp1 = MediaPlayer.create(getApplicationContext(),test.get(position1));
                mp1.start();
                break;
        }
        getAudioStats();
        // Initialize the seek bar
        initializeSeekBar();
    }
    protected void getAudioStats () {
        int duration = mp1.getDuration() / 1000; // In milliseconds
        int due = (mp1.getDuration() - mp1.getCurrentPosition()) / 1000;
        int pass = duration - due;
    }

    protected void initializeSeekBar () {
        s.setMax(mp1.getDuration() / 1000);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mp1 != null) {
                    int mCurrentPosition = mp1.getCurrentPosition() / 1000; // In milliseconds
                    s.setProgress(mCurrentPosition);
                    getAudioStats();
                }
                mHandler.postDelayed(mRunnable, 1000);
            }
        };
        mHandler.postDelayed(mRunnable, 1000);
    }
}
