package com.example.apiexampleofvolly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    String url;
    RecyclerView rv1;
    private List<personlist> list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();


        url = "https://jsonplaceholder.typicode.com/posts";

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {

                        try
                        {
                            for (int i = 0; i < response.length(); i++)
                            {

                                personlist personlist1 = new personlist();

                                JSONObject jsonObject=response.getJSONObject(i);
                                personlist1.setUserId(jsonObject.getString("userId"));
                                personlist1.setId(jsonObject.getString("id"));
                                personlist1.setTitle(jsonObject.getString("title"));
                                personlist1.setBody(jsonObject.getString("body"));
                                list.add(personlist1);

                            }
                            Log.d("list", String.valueOf(list));
                            rv1=findViewById(R.id.rv1);
                            RecyclerViewAdapter recyclerViewAdapter=new RecyclerViewAdapter(list);
                            rv1.setHasFixedSize(true);
                            rv1.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            rv1.setAdapter(recyclerViewAdapter);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {

                                Log.d("errormy1",error.toString());
                            }
                        });
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(jsonArrayRequest);

            }
        }).start();

    }
}