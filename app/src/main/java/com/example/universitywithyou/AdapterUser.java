package com.example.universitywithyou;

        import android.app.Activity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;


        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import java.util.ArrayList;
        import java.util.List;


public class AdapterUser  extends ArrayAdapter {
    Activity activity;
    int resource;
    List<User> list;



    public AdapterUser(Activity activity, int resource, List<User> list) {
        super(activity, resource,list);
        this.activity = activity;
        this.resource = resource;
        this.list = list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();

        View view = layoutInflater.inflate(resource,null);



        TextView name = view.findViewById(R.id.full_name);
        name.setText(list.get(position).getFirst_name()+" "+list.get(position).getFamily_name());

        ImageView imageView =view.findViewById(R.id.deleg);
        if(list.get(position).getDeleg()) {
            imageView.setVisibility(View.VISIBLE); }

        TextView last_message = view.findViewById(R.id.last_message_user);


        return view;
    }



    List<Message> list_message ;
    Message message ;
    DatabaseReference myRef ;


    public Message getLastMessage(String user_id){
        message = new Message();
        myRef = FirebaseDatabase.getInstance().getReference();
        list_message = new ArrayList<Message>();

        myRef.child("Messages").child("room:"+user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    message = dataSnapshot1.getValue(Message.class);
                    list_message.add(message);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list_message.get(list_message.size()-1);

    }

}
