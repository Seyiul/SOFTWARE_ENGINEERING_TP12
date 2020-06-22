package gachon.mp2020.software_engineering_tp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NewActivity extends Activity {

    String passedRoute;
    Double passedDistance;
    ArrayList<String> stops;
    String start;

    ImageView image;
    TextView route;
    TextView label;
    ArrayList<String> passNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        route = findViewById(R.id.result_route);
        label = findViewById(R.id.result_label);
        image = findViewById(R.id.image);


        Intent intent = getIntent();
        if (intent != null) {
            Bundle passed = intent.getExtras();
            passedRoute = passed.getString("route");
            passedDistance = passed.getDouble("total");
            stops = passed.getStringArrayList("stopover");
            start = passed.getString("start");
            passNode = passed.getStringArrayList("node");
        }

        for (int j = 0; j < passNode.size() - 1; j++) {
            MyPath path = new MyPath(passNode);

            int[] xpoints = new int[path.xArray.size()];
            for (int i = 0; i < xpoints.length; i++) {
                xpoints[i] = path.xArray.get(i).intValue();
            }
            int[] ypoints = new int[path.yArray.size()];
            for (int i = 0; i < ypoints.length; i++) {
                ypoints[i] = path.yArray.get(i).intValue();
            }

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.school);
            Bitmap newImage = Bitmap.createBitmap(bitmap).copy(Bitmap.Config.ARGB_8888, true);
            Canvas canvas = new Canvas(newImage);

            Paint MyPaint = new Paint();
            MyPaint.setStrokeWidth(10f);
            MyPaint.setStyle(Paint.Style.STROKE);
            MyPaint.setColor(Color.RED);

            //원 그리기

            for (int i = 0; i < xpoints.length; i++) {
                canvas.drawCircle(xpoints[i], ypoints[i], 100, MyPaint);
            }

            //canvas.drawPath(drawPath, MyPaint);
            canvas.drawBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), MyPaint);
            image.setImageDrawable(new BitmapDrawable(getResources(), newImage));

        }
            Log.d("test", "setText 전 단계");
            route.setText("순서 : " + passedRoute);
            label.setText("총 이동거리는 약 " + passedDistance + " m 입니다");

        }

    }