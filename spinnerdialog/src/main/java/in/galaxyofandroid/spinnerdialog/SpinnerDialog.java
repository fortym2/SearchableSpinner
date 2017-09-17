package in.galaxyofandroid.spinnerdialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Md Farhan Raja on 2/23/2017.
 * Forked by Davide Quaranta on 9/17/2017
 */
public class SpinnerDialog {

    private ArrayList<String> items;
    private Activity activity;
    private String dTitle;
    private String dCloseText;
    private OnSpinnerItemClick onSpinnerItemClick;
    private AlertDialog alertDialog;

    private int pos;
    private int style;

    /**
     * Default constructor for a dialog with a custom title.
     * @param activity the {@link Activity}
     * @param items a {@link Collection} of {@link String} objects
     * @param dialogTitle the title of the dialog
     */
    public SpinnerDialog(Activity activity, Collection<String> items, String dialogTitle) {
        this.items = new ArrayList<>(items);
        this.activity = activity;
        this.dTitle = dialogTitle;
    }

    /**
     * Constructor for a dialog with a custom title and close button text.
     * @param activity the {@link Activity}
     * @param items a {@link Collection} of {@link String} objects
     * @param dialogTitle the title of the dialog
     * @param dialogCloseText the text on the dialog's close button
     */
    public SpinnerDialog(Activity activity, Collection<String> items, String dialogTitle, String dialogCloseText) {
        this(activity, items, dialogTitle);
        this.dCloseText = dialogCloseText;
    }

    /**
     * Constructor for a dialog with a custom title and a style.
     * @param activity the {@link Activity}
     * @param items a {@link Collection} of {@link String} objects
     * @param dialogTitle the title of the dialog
     * @param style the integer representing the dialog animation
     */
    public SpinnerDialog(Activity activity, Collection<String> items, String dialogTitle, int style) {
        this(activity, items, dialogTitle);
        this.style = style;
    }

    /**
     * Constructor for a dialog with custom title, close button text and style.
     * @param activity the {@link Activity}
     * @param items a {@link Collection} of {@link String} objects
     * @param dialogTitle the title of the dialog
     * @param dialogCloseText the text on the dialog's close button
     * @param style the integer representing the dialog animation
     */
    public SpinnerDialog(Activity activity, Collection<String> items, String dialogTitle, String dialogCloseText, int style) {
        this(activity, items, dialogTitle, dialogCloseText);
        this.style = style;
    }

    /**
     * Sets the {@link OnSpinnerItemClick} for items in the dialog
     * @param onSpinnerItemClick1 an {@link OnSpinnerItemClick} object
     */
    public void bindOnSpinnerListener(OnSpinnerItemClick onSpinnerItemClick1) {
        this.onSpinnerItemClick = onSpinnerItemClick1;
    }

    /**
     * Shows the spinner dialog.
     */
    public void showSpinnerDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(activity);
        View v = LayoutInflater.from(activity).inflate(R.layout.dialog_layout, null);
        TextView rippleViewClose = (TextView) v.findViewById(R.id.close);
        TextView title = (TextView) v.findViewById(R.id.spinerTitle);
        title.setText(dTitle);
        if (dCloseText != null) rippleViewClose.setText(dCloseText);
        final ListView listView = (ListView) v.findViewById(R.id.list);
        final EditText searchBox = (EditText) v.findViewById(R.id.searchBox);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, R.layout.items_view, items);
        listView.setAdapter(adapter);
        adb.setView(v);
        alertDialog = adb.create();
        alertDialog.getWindow().getAttributes().windowAnimations = style;//R.style.DialogAnimations_SmileWindow;
        alertDialog.setCancelable(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                TextView t = (TextView) view.findViewById(R.id.text1);
                for (int j=0; j<items.size(); j++) {
                    if (t.getText().toString().equalsIgnoreCase(items.get(j))) pos = j;
                }
                onSpinnerItemClick.onClick(t.getText().toString(), pos);
                alertDialog.dismiss();
            }
        });

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.getFilter().filter(searchBox.getText().toString());
            }
        });

        rippleViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

}
