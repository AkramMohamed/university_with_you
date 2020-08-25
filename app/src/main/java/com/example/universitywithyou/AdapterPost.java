package com.example.universitywithyou;

        import android.app.Activity;
        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.core.app.ActivityCompat;
        import androidx.core.app.ActivityOptionsCompat;
        import androidx.core.view.ViewCompat;


        import com.google.gson.Gson;
        import com.squareup.picasso.Picasso;

        import java.io.Serializable;
        import java.util.List;


public class AdapterPost extends ArrayAdapter {
    Activity activity;
    int resource;
    List<Post> list;



    public AdapterPost(Activity activity, int resource, List<Post> list) {
        super(activity, resource,list);
        this.activity = activity;
        this.resource = resource;
        this.list = list;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();

        View view = layoutInflater.inflate(resource,null);


        TextView textView = view.findViewById(R.id.post_text);
        if (!list.get(position).getText_post().equals("")){
            textView.setVisibility(View.VISIBLE);
        textView.setText(list.get(position).getText_post());}

        Button comment = view.findViewById(R.id.post_comment);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(activity,CommentsList.class);
                Post post = list.get(position);
                intent.putExtra("Post", new Gson().toJson(post));
                activity.startActivity(intent);
            }
        });

        TextView time = view.findViewById(R.id.post_time);
        String t = list.get(position).getTime();
        time.setText(t);

        final ImageButton imageView = view.findViewById(R.id.post_image);
        if (!list.get(position).getPicture().equals("none")){
            imageView.setVisibility(View.VISIBLE);
            Picasso.get().load(list.get(position).getPicture()).into(imageView);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,FullscreenActivity.class);
                intent.putExtra("image_url",list.get(position).getPicture());
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,imageView, ViewCompat.getTransitionName(imageView));
                activity.startActivity(intent,optionsCompat.toBundle());
            }
        });

        return view;
    }
}