package musicvideoalarm.example.musicvideoalarm;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.delanotracey.musicvideoalarm.R;
import com.delanotracey.musicvideoalarm.databinding.ActivityMain2Binding;
import com.delanotracey.musicvideoalarm.databinding.ActivityMainBinding;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MainActivity2 extends YouTubeBaseActivity {

    private static final String TAG ="Activity2";
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    ActivityMain2Binding activityMain2Binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMain2Binding= DataBindingUtil.setContentView(this, R.layout.activity_main2);

        youTubePlayerView=findViewById(R.id.youtubeview);
        mOnInitializedListener= new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Intent intent= getIntent();
                youTubePlayer.loadPlaylist(intent.getStringExtra("playlist"),intent.getIntExtra("selectedVideo",0),0);
                Log.d(TAG," youtube player finished initializing ");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        youTubePlayerView.initialize(YoutubeConfig.getApiKey(),mOnInitializedListener);

    }
}