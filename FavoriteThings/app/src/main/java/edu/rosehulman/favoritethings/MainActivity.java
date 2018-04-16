package edu.rosehulman.favoritethings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private TextView mColorTextView;
  private TextView mNumberTextView;
  private long mNumber;
  private static final String TAG = "FAVES";
  private DocumentReference mDocRef;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mColorTextView = (TextView) findViewById(R.id.color_text_view);
    mNumberTextView = (TextView) findViewById(R.id.number_text_view);
    mNumber = 17;

    findViewById(R.id.red_button).setOnClickListener(this);
    findViewById(R.id.white_button).setOnClickListener(this);
    findViewById(R.id.blue_button).setOnClickListener(this);
    findViewById(R.id.update_color_button).setOnClickListener(this);
    findViewById(R.id.increment_number_button).setOnClickListener(this);
    findViewById(R.id.decrement_number_button).setOnClickListener(this);

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    mDocRef = db.collection("favoriteThings").document("myDocId");

  }

  @Override
  public void onClick(View view) {
    Map<String, Object> favoriteThings = new HashMap<>();
    switch (view.getId()) {
      case R.id.red_button:
//        mColorTextView.setText(R.string.red);
        favoriteThings.put("color", "red");
        mDocRef.update(favoriteThings);

        return;
      case R.id.white_button:
//        mColorTextView.setText(R.string.white);
        favoriteThings.put("color", "white");
        mDocRef.update(favoriteThings);

        return;
      case R.id.blue_button:
//        mColorTextView.setText(R.string.blue);
        favoriteThings.put("color", "blue");
        mDocRef.update(favoriteThings);

        return;
      case R.id.update_color_button:
        Log.d(TAG, "Updating from Firebase");

        mDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
              DocumentSnapshot document = task.getResult();
              if (document.exists()) {
                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                mColorTextView.setText(document.getData().get("color").toString());
              } else {
                Log.d(TAG, "No such document");
              }
            } else {
              Log.d(TAG, "get failed with ", task.getException());
            }
          }
        });

        return;
      case R.id.increment_number_button:
//        mNumber++;
//        mNumberTextView.setText("" + mNumber);
        favoriteThings.put("number", mNumber + 1);
        mDocRef.update(favoriteThings);
        return;
      case R.id.decrement_number_button:
//        mNumber--;
//        mNumberTextView.setText("" + mNumber);
        favoriteThings.put("number", mNumber - 1);
        mDocRef.update(favoriteThings);
        return;

    }
  }
}
