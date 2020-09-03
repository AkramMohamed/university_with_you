package com.example.universitywithyou;

        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.graphics.Color;
        import android.view.LayoutInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.PopupMenu;
        import android.widget.TextView;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.constraintlayout.widget.ConstraintLayout;
        import androidx.core.app.ActivityOptionsCompat;
        import androidx.core.view.ViewCompat;


        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.gson.Gson;
        import com.squareup.picasso.Picasso;

        import java.util.List;


public class AdapterPost extends ArrayAdapter implements View.OnClickListener,PopupMenu.OnMenuItemClickListener {
    Activity activity;
    int resource;
    List<Post> list;

    Post post;


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

        post= new Post();
        post= list.get(position);

        ImageView posetPicture = view.findViewById(R.id.posterPicture);
        TextView poster = view.findViewById(R.id.poster);
        ConstraintLayout constraintLayout = view.findViewById(R.id.constrain);
        if (post.getByDirector()){
            posetPicture.setImageResource(R.drawable.director);
            poster.setText("Director");
           // constraintLayout.setBackgroundColor(Color.parseColor("#D7EEEC"));
        }

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

        TextView directedTo = view.findViewById(R.id.directedTo);
        directedTo.setText(list.get(position).getDirectedTo());

        ImageButton post_menu = view.findViewById(R.id.post_menu);
        post_menu.setOnClickListener(this);
        return view;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit :

                Toast.makeText(activity, "edit"+post.getTime(), Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_delete :
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setCancelable(false);
                builder.setMessage("Are you sure you want delete this post")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //delete comments
                                FirebaseDatabase.getInstance().getReference().child("Comments").child("post:"+post.getId_post()).removeValue();
                                //delete picture
                                FirebaseStorage.getInstance().getReferenceFromUrl(post.getPicture()).delete();
                                //delete post
                                FirebaseDatabase.getInstance().getReference().child("Posts").child(post.getId_post()).removeValue();


                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                return true;

            default:
                return false;
        }
    }

    @Override
    public void onClick(View v) {
        PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
        popupMenu.inflate(R.menu.item_post_menu);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }
}