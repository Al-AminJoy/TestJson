package com.trustedoffer.testjson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public String url = "http://10.128.19.10/project/DemoApi/demo_api.php";

    public String update_url = "http://10.128.19.10/project/DemoApi/update_api.php";
    String SqlResponse="{\"result\":\"Success\"}";


    private Button btPost,btUpdate,btDelete;
    private TextView tvTest;
    String name="Mr.Joy";
    String number="01749411185";
    private String testData;
    RequestQueue post_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btPost=findViewById(R.id.btPost);
        btUpdate=findViewById(R.id.btUpdate);
        btDelete=findViewById(R.id.btDelete);
        tvTest=findViewById(R.id.tvTest);
         post_request=Volley.newRequestQueue(this);
        btPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // int number=01515254313;
                postData();
            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              updateData();
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             deleteData();
            }
        });

       // loadData();
        //tvTest.setText(testData);


    }

    private void updateData() {
        String update_url = "http://10.129.251.219/project/DemoApi/update_api.php";

        Map<String, String> params = new HashMap();
        params.put("id","45");
        params.put("name",name);
        params.put("phone_number",number);

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, update_url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle success
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
            }
        });

        Volley.newRequestQueue(this).add(jsonRequest);
    }

    private void deleteData() {
        String delete_url = "http://10.129.251.219/project/DemoApi/delete_api.php";

        /*Map<String, String> params = new HashMap() ;
        params.put("id","46");
        JSONObject parameters = new JSONObject(params);*/
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.DELETE, delete_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle success
                if (response.equals(SqlResponse)){
                    Toast.makeText(getApplicationContext(),"Successfully Deleted",Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(getApplicationContext(),"Couldn't Delete",Toast.LENGTH_LONG).show();

                }
                Log.d("response","Status :"+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
                Log.d("VolleyError","Error Status :"+error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", "46");
                return params;
            }
        };

        Volley.newRequestQueue(this).add(jsonRequest);
    }


    private void postData(){
        String post_url = "http://10.129.251.219/project/DemoApi/post_api.php";

        Map<String, String> params = new HashMap();
        params.put("name", name);
        params.put("phone_number", number);

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, post_url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle success
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
            }
        });

        Volley.newRequestQueue(this).add(jsonRequest);

    }

    private void loadData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject receive = jsonArray.getJSONObject(i);
                        int id = receive.getInt("id");
                        String test=String.valueOf(id);
                        String name = receive.getString("name");
                        int number = receive.getInt("phone_number");
                       // String time = receive.getString("date");
                        Log.d("DATA","Data : "+id+","+name+","+number);
                        Toast.makeText(getApplicationContext(),"Data"+test,Toast.LENGTH_LONG).show();
                        testData=name;

                    }


                } catch (JSONException e) {
                    Log.d("JSON_EXCEPTON","Error"+e.getMessage());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLY_ERROR_GeT","Error"+error.getMessage());

            }
        });
        RequestQueue requestQueue;
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
