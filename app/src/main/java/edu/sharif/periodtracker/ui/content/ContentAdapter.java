package edu.sharif.periodtracker.ui.content;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.sharif.periodtracker.MyApplication;
import edu.sharif.periodtracker.R;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<Article>  data;
    private Context context;
    private Resources resources = MyApplication.getContext().getResources();

    public ContentAdapter(Context context, ArrayList<Article> data){
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.content_card_view, parent , false);
        return new ViewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            final Article article = data.get(position);
            holder.contentTitle.setText(article.getTitle());
            holder.contentDesc.setText(article.getDescription());
            holder.contentImage.setImageResource(resources.getIdentifier(article.getImgName(), "drawable", MyApplication.getContext().getPackageName()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ContentActivity.class);
                    intent.putExtra(ContentActivity.KEY_ARTICLE_DATA, article);
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView contentTitle, contentDesc;
        ImageView contentImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contentTitle = itemView.findViewById(R.id.contentTitle);
            contentDesc = itemView.findViewById(R.id.contentdescr);
            contentImage = itemView.findViewById(R.id.imageView);
        }
    }

}
