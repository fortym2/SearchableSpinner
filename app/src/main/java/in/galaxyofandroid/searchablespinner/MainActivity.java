package in.galaxyofandroid.searchablespinner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import in.galaxyofandroid.spinnerdialog.OnSpinnerItemClick;
import in.galaxyofandroid.spinnerdialog.SpinnerDialog;

public class MainActivity extends AppCompatActivity {

    Collection<String> items;
    SpinnerDialog spinnerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView selectedItems = (TextView) findViewById(R.id.txt);

        items = new ArrayList<>(Arrays.asList("Rome", "Milan", "Los Angeles", "New York", "Delhi", "London", "Berlin"));

        spinnerDialog = new SpinnerDialog(this, items, "Select or Search City", "Maybe later", R.style.DialogAnimations_SmileWindow);

        spinnerDialog.bindOnSpinnerListener(new OnSpinnerItemClick() {
            @Override
            public void onClick(String item, int position) {
                Toast.makeText(MainActivity.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                selectedItems.setText(item + " Position: " + position);
            }
        });

        findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialog.showSpinnerDialog();
            }
        });
    }
}
