package com.example.android.foodorderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.foodorderingapp.Model.Food;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodList extends AppCompatActivity {
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    homeadapter adapter;

    ArrayList<String>names=new ArrayList<>();
    ArrayList<String>images=new ArrayList<>();
//    ArrayList<String>description=new ArrayList<>();
//    ArrayList<String>discount=new ArrayList<>();
//    ArrayList<String>price=new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference reference;
    String categoryId="";
    String TAG="OnHomeList:         ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Food");
        addListener();
        adapter=new homeadapter(names,images);

        recycler_menu=(RecyclerView)findViewById(R.id.recyler_food);
        recycler_menu.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);
        recycler_menu.setAdapter(adapter);
        Log.d(TAG, "before getintent");
if(getIntent()!=null){
    categoryId=getIntent().getStringExtra("CategoryId");
    if(!categoryId.isEmpty()&&categoryId!=null){

        loadListFood(categoryId);
    }



}
    }

    private void addListener() {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Food item=dataSnapshot.getValue(Food.class);

                Log.d(TAG, "onChildAdded: "+item.getImage());
                names.add(item.getName());
                images.add(item.getImage());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void loadListFood(String categoryId) {
    }
    public  void onclick(int pos){
        Log.d(TAG, " ONCLICK: ");

        Toast.makeText(this, "   "+pos, Toast.LENGTH_SHORT).show();
        Intent foodlist=new Intent(FoodList.this,FoodDetail.class);
        foodlist.putExtra("FoodId",String.valueOf(pos));
        startActivity(foodlist);

    }
}

class homeadapter extends RecyclerView.Adapter<homeadapter.ViewHolder>{
    public ArrayList<String> names;
    public ArrayList<String> images;

    public homeadapter(ArrayList<String> name, ArrayList<String> image) {
        this.names = name;
        this.images = image;
    }

    @Override
    public homeadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item,parent,false);
        return new ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(homeadapter.ViewHolder holder, final int position) {
        final String name=this.names.get(position);
        final String image=this.images.get(position);
        holder.txtMenuName.setText(name);
        Picasso.get().load(image).resize(300,150).into(holder.imageView);
        holder.view.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((FoodList)view.getContext()).onclick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView txtMenuName;
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            txtMenuName=(TextView)itemView.findViewById(R.id.food_name);
            imageView=(ImageView)itemView.findViewById(R.id.food_image);
        }
    }
}