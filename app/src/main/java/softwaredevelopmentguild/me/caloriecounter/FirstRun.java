package softwaredevelopmentguild.me.caloriecounter;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.view.View.OnClickListener;
import android.content.Intent;


public class FirstRun extends ActionBarActivity implements OnClickListener {
    ToggleButton MaleButton;
    ToggleButton FemaleButton;
    Button SaveButton;
    NumberPicker FeetNumberPicker;
    NumberPicker InchesNumberPicker;
    SeekBar ExecriseBar;
    TextView ExecriseText;
    EditText Age;
    EditText Weight;
    String gender;
    String Execrise;
    public static final String PrefsFile = "Prefs";

    public void setupWidgets(){
        String feetoptions[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] inchesoptions = {"0","1","2","3","4","5","6","7","8","9","10","11"};
        FeetNumberPicker = (NumberPicker)findViewById(R.id.FeetNumberPicker);
        InchesNumberPicker = (NumberPicker) findViewById(R.id.InchesNumberPicker);
        FeetNumberPicker.setMinValue(1);
        FeetNumberPicker.setMaxValue(9);
        InchesNumberPicker.setMinValue(0);
        InchesNumberPicker.setMaxValue(11);
        FeetNumberPicker.setDisplayedValues(feetoptions);
        InchesNumberPicker.setDisplayedValues(inchesoptions);

        Age = (EditText) findViewById(R.id.Age);
        Weight = (EditText) findViewById(R.id.Weight);


        MaleButton = (ToggleButton)findViewById(R.id.MaleButton);
        FemaleButton = (ToggleButton)findViewById(R.id.FemaleButton);
        MaleButton.setTextOn("Male");
        MaleButton.setTextOff("Male");
        MaleButton.setChecked(true);
        FemaleButton.setTextOn("Female");
        FemaleButton.setTextOff("Female");
        FemaleButton.setChecked(false);
        gender = "Male";
        SaveButton = (Button)findViewById(R.id.SaveButton);
        ExecriseBar = (SeekBar) findViewById(R.id.seekBar);
        ExecriseText = (TextView) findViewById(R.id.ExecriseBar);

        MaleButton.setOnClickListener(this);
        FemaleButton.setOnClickListener(this);
        SaveButton.setOnClickListener(this);
        ExecriseBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress <= 20){
                    Execrise = "None";
                    ExecriseText.setText("No to Little Execrise");
                }
                else if(progress >= 21 && progress <= 40){
                    Execrise = "Light";
                    ExecriseText.setText("Light Execrise");
                }
                else if(progress >= 41 && progress <= 60){
                    Execrise = "Moderate";
                    ExecriseText.setText("Moderate Execrise");
                }
                else if(progress >= 61 && progress <= 80){
                    Execrise = "Heavy";
                    ExecriseText.setText("Heavy Execrise");
                }
                else{
                    Execrise = "Extra Heavy";
                    ExecriseText.setText("Extra Heavy Execrise");
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void save(){
        int age = Integer.parseInt(Age.getText().toString());
        long weight =  (long)(Integer.parseInt(Weight.getText().toString())/2.205);
        long height = (long)(((FeetNumberPicker.getValue() * 12) + InchesNumberPicker.getValue()) * 2.54);
        long BMR;
        long intake;
        if(gender == "Male"){
            BMR = (long)(88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age));
        }
        else{
            BMR = (long)(447.593 + (9.247 * weight) + (3.098 * height ) - (4.330*age));
        }
        switch(Execrise){
            case "None":
                intake = (long)(BMR * 1.2);
                break;
            case "Light":
                intake = (long)(BMR * 1.375);
                break;
            case "Moderate":
                intake = (long)(BMR * 1.55);
                break;
            case "Heavy":
                intake = (long)(BMR * 1.725);
                break;
            case "Extra Heavy":
                intake = (long)(BMR * 1.9);
                break;
            default:
                intake = BMR;
        }
        SharedPreferences prefs = getSharedPreferences(PrefsFile,0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("first_run",false);
        editor.putInt("Age", age);
        editor.putLong("Weight", weight);
        editor.putLong("Height",height);
        editor.putString("Gender",gender);
        editor.putString("Execrise",Execrise);
        editor.putLong("BMR",BMR);
        editor.putLong("Intake",intake);
        editor.commit();

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);



    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);
        setupWidgets();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first_run, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v){
        int id = v.getId();
        switch (id){
            case R.id.MaleButton:
                FemaleButton.setChecked(false);
                gender = "Male";
                break;
            case R.id.FemaleButton:
                MaleButton.setChecked(false);
                gender = "Female";
                break;
            case R.id.SaveButton:
                save();
                break;
            default:
                break;
        }
    }

}
