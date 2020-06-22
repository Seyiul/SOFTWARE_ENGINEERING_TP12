package gachon.mp2020.software_engineering_tp;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends Activity {
    Button btn;
    EditText input_start;
    EditText input_destination;
    EditText input_stopover;


    String passingRoute;
    Double passingDistance;

    StringBuffer data = new StringBuffer();
    FileInputStream fis;
    BufferedReader buffer;
    String str = "";
    FileOutputStream fos;
    PrintWriter out;
    private String total_check="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn);
        input_start = (EditText) findViewById(R.id.start);
        input_destination = (EditText) findViewById(R.id.destination);
        input_stopover = (EditText) findViewById(R.id.stopover);

        ArrayList<String> name = new ArrayList<String>();
        ArrayList<Integer> x = new ArrayList<Integer>();
        ArrayList<Integer> y = new ArrayList<Integer>();


        name.add("기숙사");      x.add(129); y.add(142);
        name.add("중앙도서관");   x.add(425); y.add(168);
        name.add("15");       x.add(410); y.add(228);
        name.add("교육대학원");    x.add(430); y.add(230);
        name.add("16");      x.add(396); y.add(375);
        name.add("17");      x.add(418); y.add(392);
        name.add("가천관");     x.add(433); y.add(386);
        name.add("24");      x.add(442); y.add(411);
        name.add("한의대");       x.add(425); y.add(433);
        name.add("공과대학2");       x.add(457); y.add(462);
        name.add("바이오나노연구원");   x.add(382); y.add(491);
        name.add("법과대학");       x.add(398); y.add(512);
        name.add("비전타워");      x.add(332); y.add(515);
        name.add("23");       x.add(320); y.add(479);
        name.add("22");       x.add(195); y.add(443);
        name.add("IT대학");      x.add(210); y.add(467);
        name.add("21");      x.add(155); y.add(425);
        name.add("글로벌센터");   x.add(135); y.add(418);
        name.add("20");       x.add(253); y.add(414);
        name.add("공과대학1");    x.add(222); y.add(400);
        name.add("19");      x.add(195); y.add(340);
        name.add("예술대학1");    x.add(159); y.add(325);
        name.add("예술대학2");    x.add(311); y.add(304);
        name.add("바이오나노대학");   x.add(357); y.add(365);
        name.add("18");       x.add(300); y.add(400);
        name.add("25");      x.add(265); y.add(230);
        name.add("26");      x.add(368); y.add(445);
        name.add("27");      x.add(446); y.add(290);
        try {
            fos = openFileOutput
                    ("temp.txt",
                            Context.MODE_APPEND);
            out = new PrintWriter(fos);
            out.println(name);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String start =input_start.getText().toString();
                if(start.equals("")){
                    start="no";
                }
                String destination =input_destination.getText().toString();
                if(destination.equals("")){
                    destination="no";
                }
                String Stopover = input_stopover.getText().toString();
                if(Stopover.equals("")){
                    Stopover="no,no";

                }
                String[] stopover = (String[]) Stopover.split(",");

                if (valid(start, stopover,destination) == 1) {
                    //출발지부터 각각의 경유지까지의 거리 구하기

                    final HashMap<String, Double> stopover_distance = new HashMap<String, Double>();

                    for (int i = 0; i < stopover.length; i++) {
                        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(start, stopover[i]);
                        double distance = dijkstra.getDistance();
                        stopover_distance.put(stopover[i], distance);
                    }
                    //경유지 우선순위 정하기
                    List<String> keySetList = new ArrayList<>(stopover_distance.keySet());
                    Collections.sort(keySetList, (o1, o2) -> (stopover_distance.get(o1).compareTo(stopover_distance.get(o2))));

                    String order = start;
                    for (String key : keySetList) {
                        order = order + " " + key + " ";
                    }
                    order = order + destination;
                    passingRoute = order;
                    // route.setText("route : "+ passingRoute);

                    ArrayList<String> route = new ArrayList<>();
                    ArrayList<String> stops = new ArrayList<>();
                    Iterator iterator = stopover_distance.keySet().iterator();
                    route.add(start);
                    int i = 1;
                    while (iterator.hasNext()) {
                        route.add((String) iterator.next());
                    }
                    route.add(destination);
                    double total = 0;
                    for (int j = 1; j < route.size() - 1; j++) {
                        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(route.get(j), route.get(j + 1));
                        double distance = dijkstra.getDistance();
                        stops = dijkstra.getStops();
                        total += distance;
                    }
                    passingDistance = total;
                    //label.setText("총 거리는 "+passingDistance+"m"+" 입니다.");

                    Intent intent = new Intent(getApplicationContext(), NewActivity.class);
                    intent.putExtra("route", passingRoute);
                    intent.putExtra("node",route);
                    intent.putExtra("stopover", stops);
                    intent.putExtra("total", passingDistance);
                    intent.putExtra("start", start);
                    intent.putExtra("destination", destination);
                    startActivity(intent);
                    stops.clear();

                } else {
                    Toast.makeText(getApplicationContext(), total_check+" 잘못되었습니다!",
                            Toast.LENGTH_SHORT).show();
                    total_check="";
                }

            }
        });

        if(getIntent() != null)
        {
            onRestart();
        }
    }
    public int valid(String start, String[] stopover, String destination){
        int count=0;
        try {
            fis = openFileInput("temp.txt");
            buffer = new BufferedReader
                    (new InputStreamReader(fis));
            str = buffer.readLine();
            while (str != null) {
                data.append(str + "\n");
                str = buffer.readLine();
            }
            buffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str= data.toString();
        if(!(str.contains(start))){
            count+=1;
            total_check+="출발지 건물명이 ";
        }
        for(int i=0;i<stopover.length;i++){
            if(!(str.contains(stopover[i]))){
                count+=1;
                total_check+="경유지 건물명이 ";
            }
        }
        if(!str.contains(destination)){
            count+=1;
            total_check+="도착지 건물명이 ";
        }
        if(count==0){
            return 1;
        }
        else{
            return 0;
        }

    }
    public void initial(View v){
        input_start.setText("");
        input_destination.setText("");
        input_stopover.setText("");
    }

}
