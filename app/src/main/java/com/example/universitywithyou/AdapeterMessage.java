
package com.example.universitywithyou;

        import android.app.Activity;
        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.core.app.ActivityOptionsCompat;
        import androidx.core.view.ViewCompat;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.squareup.picasso.Picasso;

        import java.util.List;


public class AdapeterMessage  extends ArrayAdapter<Message> {

    Activity activity;
    int resource1;
    int resource2;
    List<Message> list;





    public AdapeterMessage(Activity activity, int resource1,int resource2, List<Message> list) {
        super(activity, resource1,list);
        this.activity = activity;
        this.resource1 = resource1;
        this.resource2=resource2;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();

        View viewRight = layoutInflater.inflate(resource1, null);
        View viewLeft = layoutInflater.inflate(resource2, null);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (user.getEmail().equals("team@gmail.com")) {
                if (!list.get(position).getSender().equals("team")) {
                    TextView time = viewLeft.findViewById(R.id.message_time);
                    String t = list.get(position).getTime();
                    time.setText(t);

                    TextView messageLeft = viewLeft.findViewById(R.id.message_text_left);
                    messageLeft.setText(list.get(position).getMessage_text());

                    ImageButton imageView = viewLeft.findViewById(R.id.message_img_left);
                    if (!list.get(position).getPicture().equals("none")){
                        imageView.setVisibility(View.VISIBLE);
                    Picasso.get().load(list.get(position).getPicture()).resize(320,0).into(imageView);
                    }

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(activity,FullscreenActivity.class);
                            intent.putExtra("image_url",list.get(position).getPicture());
                            activity.startActivity(intent);
                        }
                    });

                    return viewLeft;
                } else {
                    TextView time = viewRight.findViewById(R.id.message_time);
                    String t = list.get(position).getTime();
                    time.setText(t);

                    TextView messageRight = viewRight.findViewById(R.id.message_text_right);
                    messageRight.setText(list.get(position).getMessage_text());

                    ImageButton imageView = viewRight.findViewById(R.id.message_img_right);
                    if (!list.get(position).getPicture().equals("none")){
                        imageView.setVisibility(View.VISIBLE);
                    Picasso.get().load(list.get(position).getPicture()).resize(320,0).into(imageView);
                        }
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(activity,FullscreenActivity.class);
                            intent.putExtra("image_url",list.get(position).getPicture());
                            activity.startActivity(intent);
                        }
                    });
                    return viewRight;
                }
            } else {
                if (list.get(position).getSender().equals("team")) {
                    TextView time = viewLeft.findViewById(R.id.message_time);
                    String t = list.get(position).getTime();
                    time.setText(t);
                    TextView messageLeft = viewLeft.findViewById(R.id.message_text_left);
                    messageLeft.setText(list.get(position).getMessage_text());

                    final ImageButton imageView = viewLeft.findViewById(R.id.message_img_left);
                    if (!list.get(position).getPicture().equals("none")){
                        imageView.setVisibility(View.VISIBLE);
                    Picasso.get().load(list.get(position).getPicture()).resize(320,0).into(imageView);
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
                    return viewLeft;
                } else {
                    TextView time = viewRight.findViewById(R.id.message_time);
                    String t = list.get(position).getTime();
                    time.setText(t);

                    TextView messageRight = viewRight.findViewById(R.id.message_text_right);
                    messageRight.setText(list.get(position).getMessage_text());

                    ImageButton imageView = viewRight.findViewById(R.id.message_img_right);
                    if (!list.get(position).getPicture().equals("none")){
                        imageView.setVisibility(View.VISIBLE);
                    Picasso.get().load(list.get(position).getPicture()).resize(320,0).into(imageView);
                    }
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(activity,FullscreenActivity.class);
                            intent.putExtra("image_url",list.get(position).getPicture());
                            activity.startActivity(intent);
                        }
                    });
                    return viewRight;
                }
            }

        }else {return null;}


    }
}
