package com.denkiri.zalego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.denkiri.zalego.adapters.CourseAdapter;
import com.denkiri.zalego.adapters.RegisteredCoursesAdapter;
import com.denkiri.zalego.auth.LoginActivity;
import com.denkiri.zalego.models.Courses;
import com.denkiri.zalego.models.Student;
import com.denkiri.zalego.network.EndPoints;
import com.denkiri.zalego.network.VolleySingleton;
import com.denkiri.zalego.storage.PreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class RegisteredCoursesActivity extends AppCompatActivity {
    public static RegisteredCoursesActivity ma;
    final Student student = PreferenceManager.getInstance(this).getStudent();
    protected Cursor cursor;
    ArrayList<Courses> thelist;
    ListView listview;
    List<Courses> listItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_courses);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        findViewById(R.id.backtomain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(RegisteredCoursesActivity.this));
        progressDialog = new ProgressDialog(this);
        listItems = new ArrayList<>();
        ma = this;
        refresh_list();
    }
    public void refresh_list(){
        final LinearLayout mainLayout=(LinearLayout)this.findViewById(R.id.main);
        final LinearLayout  emptyLayout=(LinearLayout)this.findViewById(R.id.empty_layout);
        final LinearLayout  emptyLay=(LinearLayout)this.findViewById(R.id.empty_lay);
        listItems.clear();
        adapter = new RegisteredCoursesAdapter(listItems,getApplicationContext());
        recyclerView.setAdapter(adapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        progressDialog.setMessage("Loading Courses");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.URL_SELECT_ID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                emptyLayout.setVisibility(View.GONE);
                mainLayout.setVisibility(View.VISIBLE);

                try{
                    progressDialog.hide();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray==null || jsonArray.length()==0){
                        emptyLay.setVisibility(View.VISIBLE);
                        Button button = (Button) findViewById(R.id.button);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }
                        });

                    }
                    else{
                    Toast.makeText(RegisteredCoursesActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject o = jsonArray.getJSONObject(i);

                        Courses item = new Courses(
                                o.getInt("id"),
                                o.getString("name"),
                                o.getString("description"),
                                o.getString("price"),
                                o.getString("campus"),
                                o.getString("duration"),
                                o.getString("rating"),
                                o.getString("image"),
                                o.getInt("category_id")
                        );
                        listItems.add(item);

                            adapter = new RegisteredCoursesAdapter(listItems, getApplicationContext());
                            recyclerView.setAdapter(adapter);


                    }
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                emptyLayout.setVisibility(View.VISIBLE);
                mainLayout.setVisibility(View.GONE);
                Button button = (Button) findViewById(R.id.empty_button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refresh_list();

                    }
                });

            }
        }){
            protected Map<String , String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("student",(String.valueOf(student.getId()) ));
                return params;
            }
        };
        VolleySingleton.getInstance(RegisteredCoursesActivity.this).addToRequestQueue(stringRequest);
    }
}

