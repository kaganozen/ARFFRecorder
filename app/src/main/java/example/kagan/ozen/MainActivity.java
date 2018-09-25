package example.kagan.ozen;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.ArrayAdapter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;

import static example.kagan.ozen.R.id.save;


public class MainActivity extends AppCompatActivity implements SensorEventListener{


    private static final String TAG = "MainActivity";

    private SensorManager sensorManager;
    Sensor accelerometer;
    TextView xValue, yValue, zValue, allValue, time;
    ArrayList<String> ArrayX = new ArrayList<String>();
    ArrayList<String> ArrayY = new ArrayList<String>();
    ArrayList<String> ArrayZ = new ArrayList<String>();
    ListView showX, showY, showZ;
    Button add, save;
    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
    String date = df.format(Calendar.getInstance().getTime());
    FileInputStream fis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        xValue = (TextView) findViewById(R.id.xValue);
        yValue = (TextView) findViewById(R.id.yValue);
        zValue = (TextView) findViewById(R.id.zValue);
        time =(TextView) findViewById(R.id.time);

        allValue = (TextView) findViewById(R.id.allValue);
        showX = (ListView) findViewById(R.id.ListX);
        showY = (ListView) findViewById(R.id.ListY);
        showZ = (ListView) findViewById(R.id.ListZ);
        ToggleButton status = (ToggleButton) findViewById(R.id.toggleButton);
        time.setText("" + date);

        add =(Button) findViewById(R.id.button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getInputX = xValue.getText().toString();
                ArrayX.add(getInputX);
                ArrayAdapter<String> adapterX = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, ArrayX);
                showX.setAdapter(adapterX);
                showX.setSelection(adapterX.getCount() - 1);

                String getInputY = yValue.getText().toString();
                ArrayY.add(getInputY);
                ArrayAdapter<String> adapterY = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, ArrayY);
                showY.setAdapter(adapterY);
                showY.setSelection(adapterY.getCount() - 1);

                String getInputZ = xValue.getText().toString();
                ArrayZ.add(getInputZ);
                ArrayAdapter<String> adapterZ = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, ArrayZ);
                showZ.setAdapter(adapterZ);
                showZ.setSelection(adapterZ.getCount() - 1);

            }
        });

        save =(Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileInputStream inputStream = openFileInput("anan1.txt");
                    ObjectInputStream in = new ObjectInputStream(inputStream);
                    ArrayX = (ArrayList<String>) in.readObject();
                    in.close();
                    inputStream.close();

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });



        Log.d(TAG, "onCreate: Initializing Sensor Services");
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        Log.d(TAG, "onCreate: Registered accelerometer listener");

        status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked) {
                    sensorManager.registerListener(MainActivity.this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);


                }
                else{
                    sensorManager.unregisterListener(MainActivity.this, accelerometer);
                }

            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Log.d(TAG, "onSensorChanged: X: " + sensorEvent.values[0] + "Y: " + sensorEvent.values[1] + "Z: " + sensorEvent.values[2]);

        xValue.setText("" + sensorEvent.values[0]);
        yValue.setText("" + sensorEvent.values[1]);
        zValue.setText("" + sensorEvent.values[2]);
        allValue.setText("" + sensorEvent.values[0] +" "+ sensorEvent.values[1] +" "+ sensorEvent.values[2]);

    }

}
