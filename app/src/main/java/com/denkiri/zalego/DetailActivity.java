package com.denkiri.zalego;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.denkiri.zalego.auth.LoginActivity;
import com.denkiri.zalego.models.Student;
import com.denkiri.zalego.network.EndPoints;
import com.denkiri.zalego.network.VolleySingleton;
import com.denkiri.zalego.storage.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
public class DetailActivity extends AppCompatActivity {
    TextView courseName, courseDescription,coursePrice,courseCampus,courseDuration,courseRating;
    String Id,studentId;
    Button button;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (!PreferenceManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        final Student student = PreferenceManager.getInstance(this).getStudent();
        courseName = (TextView) findViewById(R.id.courseName);
        courseDescription = (TextView) findViewById(R.id.courseDesName);
        coursePrice = (TextView) findViewById(R.id.coursePrice);
        courseCampus = (TextView) findViewById(R.id.campus);
        courseDuration = (TextView) findViewById(R.id.courseDuration);
        courseRating= (TextView) findViewById(R.id.courseRating);
        findViewById(R.id.backtomain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });
        progressDialog = new ProgressDialog(this);
        studentId= (String.valueOf(student.getId()));
        Id = getIntent().getStringExtra("id");
        courseName.setText(getIntent().getStringExtra("name"));
        courseDescription.setText(getIntent().getStringExtra("description"));
        coursePrice.setText(getIntent().getStringExtra("price"));
        courseCampus.setText(getIntent().getStringExtra("campus"));
        courseDuration.setText(getIntent().getStringExtra("duration"));
        courseRating.setText(getIntent().getStringExtra("rating"));


    }
}
