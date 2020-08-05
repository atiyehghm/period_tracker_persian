package edu.sharif.periodtracker.ui.content;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.sharif.periodtracker.R;

public class ContentActivity extends AppCompatActivity {
    private TextView contentTitle;
    private Article article;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        article = (Article)intent.getSerializableExtra("article");
        setContentView(R.layout.activity_content);
        contentTitle = findViewById(R.id.articleTitle);
        contentTitle.setText(article.getTitle());
        super.onCreate(savedInstanceState);
    }
}
