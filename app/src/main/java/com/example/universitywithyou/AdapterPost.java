package com.example.universitywithyou;

        import android.app.Activity;
        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;


        import com.google.gson.Gson;

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
        textView.setText(list.get(position).getText_post());

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

        return view;
    }
}