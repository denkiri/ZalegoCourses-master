package com.denkiri.zalego;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.denkiri.zalego.adapters.CourseAdapter;
import com.denkiri.zalego.models.Courses;
import com.denkiri.zalego.models.Student;
import com.denkiri.zalego.network.EndPoints;
import com.denkiri.zalego.network.VolleySingleton;
import com.denkiri.zalego.storage.PreferenceManager;
import android.text.Layout;
import android.view.View;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView Username,Email;
    public static MainActivity ma;
    protected Cursor cursor;
    ArrayList<Courses> thelist;
    ListView listview;
    List<Courses> listItems;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    ProgressDialog progressDialog;
    Button button;
    Layout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView username = (TextView)hView.findViewById(R.id.username);
        TextView email = (TextView)hView.findViewById(R.id.myEmail);
        Student student = PreferenceManager.getInstance(this).getStudent();
        username.setText((String.valueOf(student.getUsername()) ));
        email.setText((String.valueOf(student.getEmail()) ));
       hView.findViewById(R.id.profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(),Profile.class));

            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        progressDialog = new ProgressDialog(this);
        listItems = new ArrayList<>();
        ma = this;
        refresh_list();


    }
    public void refresh_list(){
        final LinearLayout mainLayout=(LinearLayout)this.findViewById(R.id.main);
        final LinearLayout  emptyLayout=(LinearLayout)this.findViewById(R.id.empty_layout);
        listItems.clear();
        adapter = new CourseAdapter(listItems,getApplicationContext());
        recyclerView.setAdapter(adapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        progressDialog.setMessage("Loading Courses");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.URL_SELECT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                progressDialog.dismiss();
                emptyLayout.setVisibility(View.GONE);
                mainLayout.setVisibility(View.VISIBLE);
                try{

                    progressDialog.hide();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("courses");

                    Toast.makeText(MainActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
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
                params.put("name", "kl");
                return params;
            }
        };
        VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
    }

public void programming(){
    final LinearLayout mainLayout=(LinearLayout)this.findViewById(R.id.main);
    final LinearLayout  emptyLayout=(LinearLayout)this.findViewById(R.id.empty_layout);
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
            emptyLayout.setVisibility(View.GONE);
            mainLayout.setVisibility(View.VISIBLE);

            try{

                progressDialog.hide();
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("courses");

                Toast.makeText(MainActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
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

            emptyLayout.setVisibility(View.VISIBLE);
            mainLayout.setVisibility(View.GONE);
            Button button = (Button) findViewById(R.id.empty_button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    programming();

                }
            });
        }
    }){
        protected Map<String , String> getParams() throws AuthFailureError {
            Map<String , String> params = new HashMap<>();
            params.put("name", "kl");
            return params;
        }
    };
    VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
}
   public void business(){
       final LinearLayout mainLayout=(LinearLayout)this.findViewById(R.id.main);
       final LinearLayout  emptyLayout=(LinearLayout)this.findViewById(R.id.empty_layout);
       listItems.clear();
       adapter = new CourseAdapter(listItems,getApplicationContext());
       recyclerView.setAdapter(adapter);

       recyclerView.setItemAnimator(new DefaultItemAnimator());
       progressDialog.setMessage("Loading Courses");
       progressDialog.show();

       StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.BUSINESS, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               progressDialog.dismiss();
               emptyLayout.setVisibility(View.GONE);
               mainLayout.setVisibility(View.VISIBLE);
               try{

                   progressDialog.hide();
                   JSONObject jsonObject = new JSONObject(response);
                   JSONArray jsonArray = jsonObject.getJSONArray("courses");

                   Toast.makeText(MainActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
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
               emptyLayout.setVisibility(View.VISIBLE);
               mainLayout.setVisibility(View.GONE);
               Button button = (Button) findViewById(R.id.empty_button);
               button.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       business();

                   }
               });

           }
       }){
           protected Map<String , String> getParams() throws AuthFailureError {
               Map<String , String> params = new HashMap<>();
               params.put("name", "kl");
               return params;
           }
       };
       VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);

   }
   public void datascience(){
       final LinearLayout mainLayout=(LinearLayout)this.findViewById(R.id.main);
       final LinearLayout  emptyLayout=(LinearLayout)this.findViewById(R.id.empty_layout);
       listItems.clear();
       adapter = new CourseAdapter(listItems,getApplicationContext());
       recyclerView.setAdapter(adapter);

       recyclerView.setItemAnimator(new DefaultItemAnimator());
       progressDialog.setMessage("Loading Courses");
       progressDialog.show();

       StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.DATASCIENCE, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               progressDialog.dismiss();
               emptyLayout.setVisibility(View.GONE);
               mainLayout.setVisibility(View.VISIBLE);
               try{

                   progressDialog.hide();
                   JSONObject jsonObject = new JSONObject(response);
                   JSONArray jsonArray = jsonObject.getJSONArray("courses");

                   Toast.makeText(MainActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
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
               emptyLayout.setVisibility(View.VISIBLE);
               mainLayout.setVisibility(View.GONE);
               Button button = (Button) findViewById(R.id.empty_button);
               button.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       datascience();

                   }
               });

           }
       }){
           protected Map<String , String> getParams() throws AuthFailureError {
               Map<String , String> params = new HashMap<>();
               params.put("name", "kl");
               return params;
           }
       };
       VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
   }

   public void cybersecurity(){
       final LinearLayout mainLayout=(LinearLayout)this.findViewById(R.id.main);
       final LinearLayout  emptyLayout=(LinearLayout)this.findViewById(R.id.empty_layout);
       listItems.clear();
       adapter = new CourseAdapter(listItems,getApplicationContext());
       recyclerView.setAdapter(adapter);

       recyclerView.setItemAnimator(new DefaultItemAnimator());
       progressDialog.setMessage("Loading Courses");
       progressDialog.show();

       StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.CYBERSECURITY, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               progressDialog.dismiss();
               emptyLayout.setVisibility(View.GONE);
               mainLayout.setVisibility(View.VISIBLE);
               try{

                   progressDialog.hide();
                   JSONObject jsonObject = new JSONObject(response);
                   JSONArray jsonArray = jsonObject.getJSONArray("courses");

                   Toast.makeText(MainActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
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
               emptyLayout.setVisibility(View.VISIBLE);
               mainLayout.setVisibility(View.GONE);
               Button button = (Button) findViewById(R.id.empty_button);
               button.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       cybersecurity();

                   }
               });

           }
       }){
           protected Map<String , String> getParams() throws AuthFailureError {
               Map<String , String> params = new HashMap<>();
               params.put("name", "kl");
               return params;
           }
       };
       VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
   }
   public void qualityassurance(){
       final LinearLayout mainLayout=(LinearLayout)this.findViewById(R.id.main);
       final LinearLayout  emptyLayout=(LinearLayout)this.findViewById(R.id.empty_layout);
       listItems.clear();
       adapter = new CourseAdapter(listItems,getApplicationContext());
       recyclerView.setAdapter(adapter);

       recyclerView.setItemAnimator(new DefaultItemAnimator());
       progressDialog.setMessage("Loading Courses");
       progressDialog.show();

       StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.QUALITYASSURANCE, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               progressDialog.dismiss();
               emptyLayout.setVisibility(View.GONE);
               mainLayout.setVisibility(View.VISIBLE);
               try{

                   progressDialog.hide();
                   JSONObject jsonObject = new JSONObject(response);
                   JSONArray jsonArray = jsonObject.getJSONArray("courses");

                   Toast.makeText(MainActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
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
               emptyLayout.setVisibility(View.VISIBLE);
               mainLayout.setVisibility(View.GONE);
               Button button = (Button) findViewById(R.id.empty_button);
               button.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       qualityassurance();

                   }
               });

           }
       }){
           protected Map<String , String> getParams() throws AuthFailureError {
               Map<String , String> params = new HashMap<>();
               params.put("name", "kl");
               return params;
           }
       };
       VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
   }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

      //  if (id == R.id.action_selected) {
       //     startActivity(new Intent(this, RegisteredCoursesActivity.class));
       // }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id==R.id.programming){

           programming();
        }

        if(id==R.id.datascience){
            datascience();
        }
        if(id==R.id.cybersecurity){
            cybersecurity();
        }
        else if (id == R.id.nav_profile) {
            startActivity(new Intent(this, Profile.class));
        }
         else if (id == R.id.nav_logout) {
            finish();
            PreferenceManager.getInstance(getApplicationContext()).logout();

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
