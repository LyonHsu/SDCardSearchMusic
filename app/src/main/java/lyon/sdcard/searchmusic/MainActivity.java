package lyon.sdcard.searchmusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int index;
    private int totalCount;
    private ArrayList<String> result = new ArrayList<String>();
    private TextView textView;

    /**
     * Ref.
     * https://weselyong.pixnet.net/blog/post/46162610-%5Bandroid%5D-%E5%8F%96%E5%BE%97sd%E5%8D%A1%E5%85%A7%E6%89%80%E6%9C%89%E9%9F%B3%E6%A8%82%E8%A9%B3%E7%B4%B0%E8%B3%87%E6%96%99
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(Permission.checReadExternalStoragePermission(this)){
            setContentView(R.layout.activity_main);
            textView = (TextView) findViewById(R.id.textView1);
            Context ctx = MainActivity.this;
            ContentResolver resolver = ctx.getContentResolver();
            Cursor c = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
            String dispStr = "";
            c.moveToFirst();
            totalCount = c.getCount();

            for (int i = 0; i < totalCount; i++) {
                int index = c.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE);
                String src = c.getString(index);
                result.add(src);
                dispStr = dispStr + (i+1)+". \n";
                dispStr = dispStr + "Title:   " + src + "\n";

                index = c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST);
                src = c.getString(index);
                dispStr = dispStr + "Artist:  " + src + "\n";

                index = c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM);
                src = c.getString(index);
                dispStr = dispStr + "Album:  " + src + "\n";

                index = c.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
                src = c.getString(index);
                int size = Integer.parseInt(src);
                size = size / 1024;
                dispStr = dispStr + "Size:  " + size + " kB\n";

                index = c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                src = c.getString(index);
                dispStr = dispStr + "Path:  " + src + "\n";

                index = c.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
                int length = c.getInt(index);
                length = length / 1000; // length in sec
                int sec = length % 60;
                length = length - sec;
                int min = length / 60;
                if (sec < 10)
                    dispStr = dispStr + "Duration:  " + min + ":0" + sec + "\n";
                else
                    dispStr = dispStr + "Duration:  " + min + ":" + sec + "\n";

                index = c.getColumnIndexOrThrow(MediaStore.Audio.Media.MIME_TYPE);
                src = c.getString(index);
                dispStr = dispStr + "Data Type:  " + src + "\n\n-------------------------------------\n";
                c.moveToNext();
            }
            c.close();
            textView.setText(dispStr);
        }else{
            Toast.makeText(this,"no permission",Toast.LENGTH_LONG).show();
        }
    }
}
