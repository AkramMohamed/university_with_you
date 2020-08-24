
package com.example.universitywithyou;

        import android.app.Activity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;

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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();

        View viewRight = layoutInflater.inflate(resource1, null);
        View viewLeft = layoutInflater.inflate(resource2, null);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if (user.getEmail().equals("team@gmail.com")) {
                if (!list.get(position).getSender().equals("admin")) {
                    TextView time = viewLeft.findViewById(R.id.message_time);
                    String t = list.get(position).getTime();
                    time.setText(t);
                    TextView messageLeft = viewLeft.findViewById(R.id.message_text_left);
                    messageLeft.setText(list.get(position).getMessage_text());
                    return viewLeft;
                } else {
                    TextView time = viewRight.findViewById(R.id.message_time);
                    String t = list.get(position).getTime();
                    time.setText(t);
                    TextView messageRight = viewRight.findViewById(R.id.message_text_right);
                    messageRight.setText(list.get(position).getMessage_text());
                    return viewRight;
                }
            } else {
                if (list.get(position).getSender().equals("admin")) {
                    TextView time = viewLeft.findViewById(R.id.message_time);
                    String t = list.get(position).getTime();
                    time.setText(t);
                    TextView messageLeft = viewLeft.findViewById(R.id.message_text_left);
                    messageLeft.setText(list.get(position).getMessage_text());
                    return viewLeft;
                } else {
                    TextView time = viewRight.findViewById(R.id.message_time);
                    String t = list.get(position).getTime();
                    time.setText(t);
                    TextView messageRight = viewRight.findViewById(R.id.message_text_right);
                    messageRight.setText(list.get(position).getMessage_text());
                    return viewRight;
                }
            }

        }else {return null;}


    }
}
