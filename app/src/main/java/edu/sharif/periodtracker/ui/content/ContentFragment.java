package edu.sharif.periodtracker.ui.content;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import edu.sharif.periodtracker.R;

public class ContentFragment extends Fragment {
    private RecyclerView recyclerView;
    private ContentViewModel reportViewModel;
    ContentAdapter adapter;
    ArrayList<Article> articles;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reportViewModel = ViewModelProviders.of(this).get(ContentViewModel.class);
        View root = inflater.inflate(R.layout.fragment_content, container, false);
        recyclerView = root.findViewById(R.id.content_recycler);
        recyclerView.setHasFixedSize(true);
        getArticles();
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        adapter = new ContentAdapter(root.getContext(), articles);
        recyclerView.setAdapter(adapter);
        return root;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("articles.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public void getArticles() {
        String jsonStr = loadJSONFromAsset();
        articles = new ArrayList<>();
        if (jsonStr != null) {
            try {
                JSONArray jsonArticles = new JSONArray(jsonStr);

                for (int i = 0; i < jsonArticles.length(); i++) {
                    JSONObject articleObj = jsonArticles.getJSONObject(i);
                    String title = articleObj.getString("title");
                    String desc = articleObj.getString("desc");
                    String content = articleObj.getString("content");
                    String imgName = articleObj.getString("imgName");

                    Article article = new Article(title, desc, content, imgName);

                    // adding contact to contact list
                    articles.add(article);
                }
            } catch (final JSONException e){
                e.printStackTrace();
            }

        } else {
            Log.i("*************", "json String is null");
        }
    }
}