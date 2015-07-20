package in.gotech.intelligation;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by anirudh on 20/07/15.
 */
public class SignUp extends ActionBarActivity {
    int numberOfParts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
    }

    public void increment(View v) {
        numberOfParts++;
        updateNumberOfPartsTextView(numberOfParts);

    }

    public void decrement(View v) {
        numberOfParts--;
        updateNumberOfPartsTextView(numberOfParts);

    }

    private void updateNumberOfPartsTextView(int numberOfParts) {
        TextView numberOfPartsTextView = (TextView) findViewById(R.id.number_of_parts);
        numberOfPartsTextView.setText("" + numberOfParts);
    }


}
