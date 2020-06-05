package com.example.ecommerceproject.Buyers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.ecommerceproject.Model.Cart;
import com.example.ecommerceproject.Prevalent.Prevalent;
import com.example.ecommerceproject.R;
import com.example.ecommerceproject.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CartActivity extends AppCompatActivity
{
    private Toolbar toolbar6;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private TextView txtTotalAmount, textView,updateBtn,totalprice, txtMsg1;

    private LottieAnimationView emptycart;

    private  int overTotalePrice=0;
    private int total_price = 0;
    private String p_price;
    private int total_product_count = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);



        Toolbar toolbar6 = findViewById(R.id.add_toolbar_cart);
        setTitle("Your cart");
        setSupportActionBar(toolbar6);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar6.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        toolbar6.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        updateBtn = (TextView) findViewById(R.id.cartDelbtn);
        txtTotalAmount = (TextView) findViewById(R.id.t_price);
        totalprice = findViewById(R.id.total_price);
        txtMsg1 = (TextView) findViewById(R.id.msg1);
        textView = (TextView) findViewById(R.id.textView);
        emptycart = findViewById(R.id.empty_notification1);


        getCartDetails();



//        NextProcessButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //affichage du prix total
//                //txtTotalAmount.setText("Total Price: " + String.valueOf(overTotalePrice)+ "$");
//                Intent intent = new Intent(CartActivity.this, ConfirmFinalOrderActivity.class);
//                intent.putExtra("Total Price", String.valueOf(overTotalePrice));
//                startActivity(intent);
//                finish();
//            }
//        });

    }
    @Override
    protected void onStart()
    {
        getCartDetails();
        CheckOrderState();
        super.onStart();
        //recevoir cart list des elelments de la base de donnee
        final DatabaseReference CartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(CartListRef.child("User View")
                                .child(Prevalent.currentOnlineUser.getPhone()).child("Products"), Cart.class)
                        .build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter
                =new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model)
            {
                holder.txtProductQuantity.setText("Quantity: " +model.getQuantity());
                holder.txtProductPrice.setText("$ " +model.getPrice() );
                holder.txtProductName.setText(model.getPname());
                holder.txtProductSeller.setText("by " +model.getSellerName());
                Picasso.get().load(model.getImage())
                        .placeholder(R.drawable.img_vide)
                        .fit()
                        .centerCrop()
                        .into(holder.imageView1);


                //calculer le prix total
                int oneTypeProductPrice =((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
                overTotalePrice = overTotalePrice + oneTypeProductPrice;
                txtTotalAmount.setText("$" + String.valueOf(overTotalePrice));

                //delete an item from the cart list
                holder.updatbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "yes",
                                        "no"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("are you sure? ");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                if (i==0)
                                {
                                    CartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone())
                                            .child("Products")
                                            .child(model.getPid())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task)
                                                {
                                                    if (task.isSuccessful())
                                                    {
                                                        Toast.makeText(CartActivity.this,"Item removed successfully", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                                if (i==1)
                                {
                                    return;
                                }
                            }
                        });

                        builder.show();
                    }
                });

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_list_items3, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return  holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    private void CheckOrderState()
    {
        DatabaseReference ordersRef ;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone());

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String shippingState = dataSnapshot.child("state").getValue().toString();
                    String userName = dataSnapshot.child("name").getValue().toString();
                    //si l'ordre est confirmé par le fournisseur
                    if (shippingState.equals("shipped"))
                    {
                        txtTotalAmount.setText("Dear " +userName + "\n Order is shipped successfully. ");

                        recyclerView.setVisibility(View.GONE);
                        totalprice.setVisibility(View.INVISIBLE);
                        emptycart.setVisibility(View.INVISIBLE);
                        //textView.setVisibility(View.INVISIBLE);
                        txtMsg1.setVisibility(View.VISIBLE);

                        txtMsg1.setText("Congratulations, your final order has been shipped successfully. Soon you will received your order at your door step.");
                        //NextProcessButton.setVisibility(View.GONE);

                        Toast.makeText(CartActivity.this,"You can purchase more products, once you received your first final order.", Toast.LENGTH_SHORT).show();
                    }
                    else if(shippingState.equals("not shipped"))
                    {
                        totalprice.setText("Shipping state = Not Shipped ");
                        txtMsg1.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        txtTotalAmount.setVisibility(View.INVISIBLE);
                        emptycart.setVisibility(View.INVISIBLE);
                        //NextProcessButton.setVisibility(View.GONE);

                        Toast.makeText(CartActivity.this,"You can purchase more products, once you received your first final order.", Toast.LENGTH_SHORT).show();
                    }
//                    else if (!dataSnapshot.exists()) {
//
//                        textView.setVisibility(View.VISIBLE);
//                        textView.setText("Your cart is empty.");
//                        //txtTotalAmount.setVisibility(View.INVISIBLE);
//
//                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void getCartDetails()
    {
        final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View");
//        cartListRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                if (dataSnapshot.exists()) {
//
//                    emptycart.setVisibility(View.INVISIBLE);
//                    recyclerView.setVisibility(View.VISIBLE);
//
//
//                } else if (!dataSnapshot.exists()) {
//
//                    emptycart.setVisibility(View.GONE);
//                    txtTotalAmount.setVisibility(View.GONE);
//                }
//
//            }
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        cartListRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists()) {

                    emptycart.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);


                } else if (!dataSnapshot.exists()) {

                    emptycart.setVisibility(View.VISIBLE);
                    txtTotalAmount.setVisibility(View.GONE);




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.next_id)
//        {
//            Intent intent = new Intent(CartActivity.this, ConfirmFinalOrderActivity.class);
//            intent.putExtra("Total Price", String.valueOf(overTotalePrice));
//            startActivity(intent);
//            finish();
//
//        }
//        else if (id == android.R.id.home)
//        {
//            this.finish();
//        }
//        return true;
//    }
}
