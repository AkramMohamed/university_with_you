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

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;


        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.List;


public class AdapterComment extends ArrayAdapter {
    Activity activity;
    int resource;
    List<Comment> list;



    public AdapterComment(Activity activity, int resource, List<Comment> list) {
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



        TextView textView = view.findViewById(R.id.comment_text);
        textView.setText(list.get(position).getComment_text());

        TextView commentator = view.findViewById(R.id.commentator_name);
        commentator.setText(list.get(position).getCommentator());

        TextView time = view.findViewById(R.id.comment_time);
        String t = list.get(position).getTime();
        time.setText(t);

        return view;
    }
}