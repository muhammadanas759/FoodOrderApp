package com.example.android.foodorderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.foodorderingapp.Model.Category;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
         TextView TextFullname,fullEmailText;
        RecyclerView recycler_menu;
        RecyclerView.LayoutManager layoutManager;
        adapter adapter;

        ArrayList<String>names=new ArrayList<>();
        ArrayList<String>images=new ArrayList<>();
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        // initialize firebase
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Category");

//        FloatingActionButton fab =  findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    //set fullname in hesder
        View headerView=navigationView.getHeaderView(0);
        TextFullname=(TextView)headerView.findViewById(R.id.TextFullName);
        fullEmailText = (TextView)headerView.findViewById(R.id.fullEmailText);
        Bundle bundle=getIntent().getExtras();
        TextFullname.setText(bundle.get("name").toString());
        fullEmailText.setText(bundle.get("email").toString());

//final ArrayList<Category> model2=new ArrayList();
//model2.add(new Category("anas","https://drive.google.com/open?id=1xnW0AOtFrZMJK8iTme5QPk4983Tr2akK"));
//        model2.add(new Category("anas","https://drive.google.com/open?id=1xnW0AOtFrZMJK8iTme5QPk4983Tr2akK"));
//        model2.add(new Category("anas","https://drive.google.com/open?id=1xnW0AOtFrZMJK8iTme5QPk4983Tr2akK"));
//        model2.add(new Category("anas","https://drive.google.com/open?id=1xnW0AOtFrZMJK8iTme5QPk4983Tr2akK"));
//        model2.add(new Category("anas","https://drive.google.com/open?id=1xnW0AOtFrZMJK8iTme5QPk4983Tr2akK"));

        //set menu
        addListener();
        adapter=new adapter(names,images);

        recycler_menu=(RecyclerView)findViewById(R.id.recylerview);
        recycler_menu.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);
         recycler_menu.setAdapter(adapter);


    }

    private void addListener() {
    reference.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Category item=dataSnapshot.getValue(Category.class);
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

    protected void onStart(){
        super.onStart();
//      FirebaseRecyclerAdapter<Category,menuViewHolder> adapter=new FirebaseRecyclerAdapter<Category, menuViewHolder>
//              (Category.class,R.layout.menu_item,menuViewHolder.class,reference) {
//          @Override
//          protected void populateViewHolder(menuViewHolder viewHolder, Category model, int position) {
//              viewHolder.txtMenuName.setText(model.getName());
//              Log.d("TEXT", "populateViewHolder:"+viewHolder.txtMenuName);
//              Picasso.get().load(model.getImage()).into(viewHolder.imageView);
//
//              Log.d("IMAGE", "populateViewHolder:"+viewHolder.imageView.toString());
//              final Category clickitem=model;
//
//              viewHolder.setItemClickListener(new itemClickListner() {
//                  @Override
//                  public void onClick(View view, int position, boolean onLongClick) {
//
//
//                      Toast.makeText(Home.this, ""+clickitem.getName(), Toast.LENGTH_SHORT).show();
//
//                  }
//              });
//
//          }
//      };

//      recycler_menu.setAdapter(adapter);

  }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // Handle the camera action
        } else if (id == R.id.nav_cart) {

        } else if (id == R.id.nav_orders) {

        } else if (id == R.id.nav_logout) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public  void onclick(int pos){
        Toast.makeText(this, "   "+pos, Toast.LENGTH_SHORT).show();
        Intent foodlist=new Intent(Home.this,FoodList.class);
        //foodlist.putExtra("CategoryId",pos);
        String position=String.valueOf(pos);
        foodlist.putExtra("CategoryId",position);
        startActivity(foodlist);

    }
}
class adapter extends RecyclerView.Adapter<adapter.ViewHolder>{
    public ArrayList<String> names;
    public ArrayList<String> images;

    public adapter(ArrayList<String> name, ArrayList<String> image) {
        this.names = name;
        this.images = image;
    }

    @Override
    public adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(adapter.ViewHolder holder, final int position) {
        final String name=this.names.get(position);
        final String image=this.images.get(position);
        holder.txtMenuName.setText(name);
        Picasso.get().load(image).into(holder.imageView);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Home)view.getContext()).onclick(position);
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
            txtMenuName=(TextView)itemView.findViewById(R.id.menu_name);
            imageView=(ImageView)itemView.findViewById(R.id.menu_image);
        }
    }
}