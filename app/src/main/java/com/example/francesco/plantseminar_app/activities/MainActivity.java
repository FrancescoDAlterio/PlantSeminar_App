package com.example.francesco.plantseminar_app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.francesco.plantseminar_app.R;
import com.example.francesco.plantseminar_app.connection.DAO;
import com.example.francesco.plantseminar_app.connection.DAOInterface;
import com.example.francesco.plantseminar_app.database.DataBase;

public class MainActivity extends AppCompatActivity implements DAOInterface {

    DataBase db;
    Button actuatorBotton;
    ImageButton temperatureButton, moistureButton,lightButton;
    TextView tw_temperature,tw_moisture,tw_light;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tw_temperature = (TextView) findViewById(R.id.tw_temperature);
        tw_moisture = (TextView) findViewById(R.id.tw_moisture);
        tw_light = (TextView) findViewById(R.id.tw_light);



        actuatorBotton = (Button) findViewById(R.id.button);
        actuatorBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                open_water();

            }
        });

        temperatureButton = (ImageButton) findViewById(R.id.imageButton_temperature);
        temperatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                read_temperature();
            }
        });

        moistureButton =(ImageButton) findViewById(R.id.imageButton_moisture);
        moistureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                read_moisture();
            }
        });

        lightButton = (ImageButton) findViewById(R.id.imageButton_light);
        lightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                read_light();
            }
        });



    }




    private void open_water() {

        DAO dao = DAO.getDAO(getApplication());
        dao.open_water(this,3);


    }

    private void read_temperature() {

        DAO dao = DAO.getDAO(getApplication());
        dao.read_sensor(this,"temperature");

    }

    private void read_moisture() {

        DAO dao =DAO.getDAO(getApplication());
        dao.read_sensor(this,"moisture");
    }


    private void read_light(){

        DAO dao = DAO.getDAO(getApplication());
        dao.read_sensor(this,"light");

    }

    @Override
    public void manageAnswer(int queryNumber) {

        switch (queryNumber) {
            case 0:
                manageAnswerWithOpenWaterMA();
                break;

            case 1:
                manageAnswerWithReadTemperatureMA();
                break;

            case 2:
                manageAnswerWithReadMoistureMA();
                break;

            case 3:
                manageAnswerWithReadLightMA();
                break;



        }
    }


    @Override
    public void manageError(int queryNumber, VolleyError error) {

        switch (queryNumber) {
            case 0:
                manageErrorWithOpenWaterMA(error);
                break;

            case 1:
               manageErrorWithReadTemperatureMA(error);
               break;

            case 2:
                manageErrorWithReadMoistureMA(error);
                break;

            case 3:
                manageErrorWithReadLightMA(error);


        }

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void  manageAnswerWithOpenWaterMA(){

        DAO dao = DAO.getDAO(getApplication());
        db = dao.getDataBase();

        Toast.makeText(getApplicationContext(), "res: " + db.getOpen_water().getSx().toString() + " time:" +db.getOpen_water().getDx(), Toast.LENGTH_SHORT).show();


    }

    private void manageAnswerWithReadTemperatureMA() {

        DAO dao = DAO.getDAO(getApplication());
        db = dao.getDataBase();

        float temp = db.getTemperature().getSx();

        tw_temperature.setText(temp + " °C");


    }

    private void manageAnswerWithReadMoistureMA() {

        DAO dao = DAO.getDAO(getApplication());
        db = dao.getDataBase();

        float moisture = db.getMoisture().getSx();

        tw_moisture.setText(moisture + " (units)"); //todo: cambiare le unità
    }


    private void manageAnswerWithReadLightMA() {

        DAO dao = DAO.getDAO(getApplication());
        db =dao.getDataBase();

        float light = db.getLight().getSx();

        tw_light.setText(light + " lm");

    }

    private void manageErrorWithOpenWaterMA(VolleyError error) {


        Toast.makeText(getApplicationContext(), "Impossible to open water", Toast.LENGTH_SHORT).show();

    }

    private void manageErrorWithReadTemperatureMA(VolleyError error) {

        Toast.makeText(getApplicationContext(), "Impossible to read temperature", Toast.LENGTH_SHORT).show();

    }

    private void manageErrorWithReadMoistureMA(VolleyError error) {

        Toast.makeText(getApplicationContext(), "Impossible to read moisture", Toast.LENGTH_SHORT).show();

    }

    private void manageErrorWithReadLightMA(VolleyError error) {

        Toast.makeText(getApplicationContext(), "Impossible to read light", Toast.LENGTH_SHORT).show();
    }


}
