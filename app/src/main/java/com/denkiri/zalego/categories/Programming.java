package com.denkiri.zalego.categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.denkiri.zalego.MainActivity;
import com.denkiri.zalego.R;
import com.denkiri.zalego.adapters.CourseAdapter;
import com.denkiri.zalego.models.Courses;
import com.denkiri.zalego.network.EndPoints;
import com.denkiri.zalego.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Programming extends AppCompatActivity {
    TextView Username,Email;
    public static Programming ma;
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
        setContentView(R.layout.activity_programming);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(Programming.this));
        progressDialog = new ProgressDialog(this);
        listItems = new ArrayList<>();
        ma = this;
        categories();

    }
    public void categories(){
        listItems.clear();
        adapter = new CourseAdapter(listItems,getApplicationContext());
        recyclerView.setAdapter(adapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        progressDialog.setMessage("Loading Courses");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.PROGRAMMING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try{

                    progressDialog.hide();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("prog");

                    Toast.makeText(Programming.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
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

                        adapter = new CourseAdapter(listItems,getApplicationContext());
                        recyclerView.setAdapter(adapter);

                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Toast.makeText(Programming.this, "Failed To Load Courses",Toast.LENGTH_SHORT).show();

            }
        }){
            protected Map<String , String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("name", "kl");
                return params;
            }
        };
        VolleySingleton.getInstance(Programming.this).addToRequestQueue(stringRequest);
    }

}
