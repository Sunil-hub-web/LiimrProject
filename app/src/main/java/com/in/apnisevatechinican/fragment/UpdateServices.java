package com.in.apnisevatechinican.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.in.apnisevatechinican.Extra.AppUrl;
import com.in.apnisevatechinican.SharedPrefManager;
import com.in.apnisevatechinican.UserLogin;
import com.in.apnisevatechinican.adapter.CategorySpinerAdapter;
import com.in.apnisevatechinican.modelclass.CategoryDetails_model;
import com.in.apnisevatechinican.R;
import com.in.apnisevatechinican.modelclass.Login_ModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UpdateServices extends Fragment {

    Spinner WorkingCategory,WorkingSubcategory;
    ArrayList<CategoryDetails_model> Working_Category = new ArrayList<>();
    ArrayList<CategoryDetails_model> Working_Subcategory = new ArrayList<>();
    Button btn_updateservices;
    TextView text_category;

    String category_Id,category_Name,subcategory_Id,subcategory_Name,userid,str_mobileNo,str_emailId,
            str_userName,str_password,str_image,str_City,str_CityId;


    @Nullable
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater,
                             @Nullable  ViewGroup container,
                             @Nullable  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.updateservices_fragment,container,false);

        WorkingCategory = view.findViewById(R.id.WorkingCategory);
        WorkingSubcategory = view.findViewById(R.id.WorkingSubcategory);
        btn_updateservices = view.findViewById(R.id.btn_updateservices);
        text_category = view.findViewById(R.id.text_category);

        userid = SharedPrefManager.getInstance(getActivity()).getUser().getUserid();
        category_Name = SharedPrefManager.getInstance(getActivity()).getUser().getCategory();
        subcategory_Name = SharedPrefManager.getInstance(getActivity()).getUser().getSubcategory();
        str_mobileNo = SharedPrefManager.getInstance(getActivity()).getUser().getMobileNo();
        str_emailId = SharedPrefManager.getInstance(getActivity()).getUser().getEmailId();
        str_userName = SharedPrefManager.getInstance(getActivity()).getUser().getUserName();
        str_password = SharedPrefManager.getInstance(getActivity()).getUser().getPassword();
        str_image = SharedPrefManager.getInstance(getActivity()).getUser().getImage();
        str_City = SharedPrefManager.getInstance(getActivity()).getUser().getCity();
        str_CityId = SharedPrefManager.getInstance(getActivity()).getUser().getCity_id();

    //    text_category.setText(subcategory_Name);

        getCategory();


        WorkingCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {

                    CategoryDetails_model cate_data = (CategoryDetails_model) parent.getSelectedItem();

                    category_Id = cate_data.getId();
                    category_Name = cate_data.getName();
                    Log.d("category_Id", category_Id);

                    getSubCategory(category_Id);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        WorkingSubcategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {

                    CategoryDetails_model cate_data = (CategoryDetails_model) parent.getSelectedItem();

                    subcategory_Id = cate_data.getId();
                    subcategory_Name = cate_data.getName();
                    Log.d("subcategory_Id", subcategory_Id);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_updateservices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateServices();
            }
        });

        return view;
    }

    public void getCategory(){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Category Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppUrl.getmastercategory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if(status.equals("OK")){

                        String category = jsonObject.getString("category");
                        JSONArray jsonArray_category = new JSONArray(category);

                        for(int i=0; i<jsonArray_category.length(); i++){

                            JSONObject jsonObject_category = jsonArray_category.getJSONObject(i);

                            String id = jsonObject_category.getString("id");
                            String category_name = jsonObject_category.getString("category_name");

                            CategoryDetails_model categoryDetails_model = new CategoryDetails_model(

                                    category_name,id
                            );

                            Working_Category.add(categoryDetails_model);

                        }

                        CategorySpinerAdapter Working_Subcategory_adapter = new CategorySpinerAdapter(getActivity(),R.layout.spinneritem,Working_Category);
                        Working_Subcategory_adapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
                        WorkingCategory.setAdapter(Working_Subcategory_adapter);
                        int index=selectSpinnerValue(Working_Category,category_Name);
                        WorkingCategory.setSelection(index,true);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(getActivity(), "error"+error, Toast.LENGTH_SHORT).show();

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    public void getSubCategory(String categoryId){

        Working_Subcategory.clear();

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("SubCategory Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.getcategory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if(status.equals("OK")){

                        String subcategory = jsonObject.getString("result");
                        JSONArray jsonArray_subcategory = new JSONArray(subcategory);

                        for(int i=0; i<jsonArray_subcategory.length(); i++){

                            JSONObject jsonObject_subcategory = jsonArray_subcategory.getJSONObject(i);

                            String id = jsonObject_subcategory.getString("id");
                            String subcategory_name = jsonObject_subcategory.getString("category_name");

                            CategoryDetails_model categoryDetails_model = new CategoryDetails_model(

                                    subcategory_name,id
                            );

                            Working_Subcategory.add(categoryDetails_model);

                        }

                        CategorySpinerAdapter WorkingCategory_adapter = new CategorySpinerAdapter(getActivity(),R.layout.spinneritem,Working_Subcategory);
                        WorkingCategory_adapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
                        WorkingSubcategory.setAdapter(WorkingCategory_adapter);
                        int index = selectSpinnerValue(Working_Subcategory,subcategory_Name);
                        WorkingSubcategory.setSelection(index,true);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(getActivity(), "error"+error, Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("id",categoryId);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    public void updateServices(){

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Update Services Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.updateservice, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");

                    if(status.equals("OK")){

                        String msg = jsonObject.getString("msg");
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

                        Login_ModelClass login_modelClass = new Login_ModelClass(
                                userid,str_mobileNo,str_emailId,str_userName,str_password,str_image,category_Name,subcategory_Name,str_City,str_CityId
                        );

                        SharedPrefManager.getInstance(getActivity()).userLogin(login_modelClass);

                        category_Name = SharedPrefManager.getInstance(getActivity()).getUser().getCategory();

                        //text_category.setText(category_Name);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(getActivity(), "error"+error, Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("id",userid);
                params.put("work_category_id",category_Id);
                params.put("work_sub_category",subcategory_Id);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

   /* private void selectSpinnerValue(Spinner spinner, String myString)
    {
        int index = 0;
        for(int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equals(myString)){
                spinner.setSelection(i);
                break;
            }
        }
    }*/

    private int selectSpinnerValue( ArrayList<CategoryDetails_model> ListSpinner,String myString)
    {
        int index = 0;
        for(int i = 0; i < ListSpinner.size(); i++){
            if(ListSpinner.get(i).getName().equals(myString)){
                index=i;
                break;
            }
        }
        return index;
    }
}
