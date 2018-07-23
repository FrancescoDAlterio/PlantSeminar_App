package com.example.francesco.plantseminar_app.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
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
                //open_water(duration);
            }
        });







    }


    private void check_for_rain(){

        DAO dao = DAO.getDAO(getApplication());
        dao.read_sensor(this,"rain");


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

            case 4:
                manageAnswerWithReadRainWPA();
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

            case 4:
                manageErrorWithReadRainWPA(error);
                break;



        }

    }


    public void  manageAnswerWithOpenWaterWPA(){

        DAO dao = DAO.getDAO(getApplication());
        db = dao.getDataBase();

        int code = db.getOpen_water().getSx();

        if (code == 0) goBackToMain(0);

        else goBackToMain(1);

    }

    public void manageAnswerWithReadRainWPA(){

        DAO dao = DAO.getDAO(getApplication());
        db = dao.getDataBase();

        int rain_value = db.getRain().getSx();

            if (rain_value ==1) {

                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        //set icon
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        //set title
                        .setTitle("it is raining")
                        //set message
                        .setMessage("Do you really want to water the plant?")
                        //set positive button
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked
                                Toast.makeText(getApplicationContext(), "POSITIVO", Toast.LENGTH_LONG).show();
                                open_water(duration);


                            }
                        })
                        //set negative button
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked
                                Toast.makeText(getApplicationContext(), "NEGATIVO", Toast.LENGTH_LONG).show();
                                goBackToMain(2);
                            }
                        })
                        .show();

            }


            else if (rain_value == 2){


                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        //set icon
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        //set title
                        .setTitle("it is raining HARD")
                        //set message
                        .setMessage("Do you really want to water the plant?")
                        //set positive button
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked
                                open_water(duration);
                            }
                        })
                        //set negative button
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked
                                goBackToMain(2);
                            }
                        })
                        .show();

            }


        else {
            open_water(duration);

        }



    }

    public void manageErrorWithOpenWaterWPA(VolleyError error){

        Toast.makeText(getApplicationContext(), "Impossible to water the plant", Toast.LENGTH_SHORT).show();
    }

    public void manageErrorWithReadRainWPA(VolleyError error){

        Toast.makeText(getApplicationContext(), "Impossible to water the plant", Toast.LENGTH_SHORT).show();


    }


    private void goBackToMain(int situation) {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("situation",situation);
        setResult(Activity.RESULT_OK,returnIntent);

        finish();



    }




}
