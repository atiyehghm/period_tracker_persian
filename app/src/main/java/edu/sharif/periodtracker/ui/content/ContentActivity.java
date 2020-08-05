package edu.sharif.periodtracker.ui.content;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.sharif.periodtracker.MyApplication;
import edu.sharif.periodtracker.R;

public class ContentActivity extends AppCompatActivity {
    public static final String KEY_ARTICLE_DATA = "KEY_ARTICLE_DATA";
    private TextView contentTitle;
    private TextView contentText;
    private Toolbar toolbar;
//    private TextView contentTitle;
    private ImageView contentImage;
    private Article article;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        getViewFromXml();
        getBundle();
        controller();
    }

    private void controller() {
        configureBackButton();
        contentTitle.setText(article.getTitle());
        contentText.setText(article.getContent());
        Resources resources = MyApplication.getContext().getResources();
        int resourceId = resources.getIdentifier(article.getImgName(), "drawable", MyApplication.getContext().getPackageName());
        contentImage.setImageResource(resourceId);

    }

    private void configureBackButton() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getViewFromXml() {
        toolbar = findViewById(R.id.toolbar);
        contentTitle = findViewById(R.id.articleTitle);
        contentText = findViewById(R.id.article_description);
        contentImage = findViewById(R.id.image_content);
    }

    private void getBundle() {
        article = (Article) getIntent().getSerializableExtra(KEY_ARTICLE_DATA);
    }
}
