package com.example.francesco.plantseminar_app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.francesco.plantseminar_app.R;
import com.example.francesco.plantseminar_app.connection.DAO;
import com.example.francesco.plantseminar_app.connection.DAOInterface;
import com.example.francesco.plantseminar_app.database.DataBase;

public class WaterPlantActivity extends AppCompatActivity implements DAOInterface {

    DataBase db;
    String lastTimeWater;
    ImageButton waterPlant_button;
    SeekBar seekbar_duration;
    int duration = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open__water);

        seekbar_duration = (SeekBar) findViewById(R.id.seekBar_sec_OW);



        waterPlant_button = (ImageButton) findViewById(R.id.WaterPlant_ImageButton);
        waterPlant_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                duration = seekbar_duration.getProgress()+1;
                check_for_rain();
                open_water(duration);
            }
        });







    }


    private void check_for_rain(){

        


    }



    private void open_water(int duration) {


        DAO dao = DAO.getDAO(getApplication());
        dao.open_water(this,duration);


    }

    @Override
    public void manageAnswer(int queryNumber) {

        switch (queryNumber){

            case 0:
                manageAnswerWithOpenWaterWPA();
                break;

            default:
                Toast.makeText(getApplicationContext(), "WaterPlantActivity default in manage answer", Toast.LENGTH_SHORT).show();



        }

    }

    @Override
    public void manageError(int queryNumber, VolleyError error) {

        switch (queryNumber){

            case 0:
                manageErrorWithOpenWaterWPA(error);
                break;



        }

    }


    public void  manageAnswerWithOpenWaterWPA(){

        DAO dao = DAO.getDAO(getApplication());
        db = dao.getDataBase();




    }

    public void manageErrorWithOpenWaterWPA(VolleyError error){

        Toast.makeText(getApplicationContext(), "Impossible to water the plant", Toast.LENGTH_SHORT).show();
    }




}
