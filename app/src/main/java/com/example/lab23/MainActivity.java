package com.example.lab23;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> list = new ArrayList<>();
    ArrayList<Stock> arrayList = new ArrayList<>();
    ListView listView;
    MyAdapter myAdapter;
    String url="https://financialmodelingprep.com/api/company/price/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
        list.add("NOK");
        list.add("AAPL");
        list.add("GOOGL");
        list.add("FB");

        myAdapter = new MyAdapter(this, R.layout.list_view_items, arrayList);
        listView.setAdapter(myAdapter);
        Button button = findViewById(R.id.addButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit1 = findViewById(R.id.idField);
                EditText edit2 = findViewById(R.id.addNameField);
                String id = edit1.getText().toString();
                String name = edit2.getText().toString();
                if(id!=null&&name!=null) {
                    requestJSON(id, name);
                    edit1.setText(null);
                    edit2.setText(null);
                }
            }
        });
    }


    public void requestJSON(final String id, final String name) {


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url2 = url+id+"?datatype=json";





        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url2,null,  new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                           try {
                               arrayList.add(new Stock(name, response.getJSONObject(id).getString("price")));
                               //Log.d("lhf", response.getJSONObject(list.get(i)).getString("price"));
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }

                        myAdapter.notifyDataSetChanged();
                    }



                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ei", error.toString());
                    }




                }
                );
        requestQueue.add(jsonObjectRequest);

    }
}

