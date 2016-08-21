package ittraining.revision.com.kinbech;

/**
 * Created by Akash on 13/02/2016.
 */
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class circle_activity extends Activity {

    private ImageView imageViewRound;
    private ImageView imageViewOval;
    private ImageView imageViewTriangle;
    private ImageView imageViewHexaGon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circlr);
        imageViewRound=(ImageView)findViewById(R.id.imageView_round);

        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.handshake);

        imageViewRound.setImageBitmap(icon);



    }
}
