package musicvideoalarm.example.musicvideoalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Random;

public class AlarmReceiver extends BroadcastReceiver {
    String playlist;
    int selectedVideo;

    @Override
    public void onReceive(Context context, Intent intent) {


        Intent i = new Intent(context,MainActivity2.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Random random=new Random();
        switch (intent.getStringExtra("genre")){
            case "Pop":
                playlist="PLMC9KNkIncKtPzgY-5rmhvj7fax8fdxoj";
                selectedVideo=random.nextInt(200)+1;
                break;
            case "Rap":
                playlist="PL-FVH5VWgRPHNz24zZ5_FLHQWoidN6O1d";
                selectedVideo=random.nextInt(168)+1;
                break;
            case "Reggae":
                playlist="PLwY9l4M25GOJqIx-Dn-PmYs1KjPd80-1N";
                selectedVideo=random.nextInt(183)+1;
                break;
            case "Rock":
                playlist="PLNxOe-buLm6cz8UQ-hyG1nm3RTNBUBv3K";
                selectedVideo=random.nextInt(447)+1;
                break;

            case "Classical":
                playlist="PLxvodScTx2RtAOoajGSu6ad4p8P8uXKQk";
                selectedVideo=random.nextInt(100)+1;
                break;
            case "Country":
                playlist="PLlYKDqBVDxX0Qzmoi2-vvHJjOAy3tRPQ_";
                selectedVideo=random.nextInt(200)+1;
                break;
            case "Edm":
                playlist="PLw-VjHDlEOgs658kAHR_LAaILBXb-s6Q5";
                selectedVideo=random.nextInt(120)+1;
                break;






        }
        i.putExtra("playlist",playlist);
        i.putExtra("selectedVideo",selectedVideo);
        context.startActivity(i);
        /*
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent= PendingIntent.getActivity(context,0,i,0);


        //created notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"Alarm")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Music Video Alarm")
                .setContentText("TIME TO WAKE UP")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat =NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());

         */
    }
}
