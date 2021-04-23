package com.example.pet.mode.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.R;
import com.example.pet.mode.models.UserClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    EditText  edEmail , edPassWord , edPassWord2 , edName;
    TextView tvHavaAccount ;
    Button btnNext ;
    FirebaseAuth mAuth ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        anhXa();
        mAuth = FirebaseAuth.getInstance();



        // BẮT SỰ KIỆN KHI NHẤN VÀO EDITTEXT THÌ NÓ SẼ CHỮ HINT SẼ MẤT ĐỂ NGƯỜI DÙNG NHẬP VÀO
        edEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edEmail.setHint("");
            }
        });
        edPassWord.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                edPassWord.setHint("");
            }
        });
        edPassWord2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                edPassWord2.setHint("");
            }
        });
        edName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                edName.setHint("");
            }
        });

        // CHUYỂN TRANG
        btnNext.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                   String userName = edName.getText().toString();
                   String email = edEmail.getText().toString();
                   String passWord = edPassWord.getText().toString();
                                           // Lấy dữ liệu editText getText().toString().
                                           if (userName.isEmpty())
                                           {
                                               Toast.makeText(getApplicationContext(), "VUI LÒNG KHÔNG BỎ TRỐNG USERNAME !!!!", Toast.LENGTH_LONG).show();
                                               return;
                                           }
                                           if (email.isEmpty())
                                           {
                                               Toast.makeText(getApplicationContext(), "VUI LÒNG KHÔNG BỎ TRỐNG EMAIL !!!!", Toast.LENGTH_LONG).show();
                                               return;
                                           }

                                           if (edPassWord.length() <= 7)
                                           {
                                               Toast.makeText(getApplicationContext(), "MẬT KHẨU CỦA BẠN QUÁ NGẮN !!!!", Toast.LENGTH_LONG).show();
                                               return;
                                           }
                                           if (edPassWord.getText().toString().equals(edPassWord2.getText().toString())) {
                                               mAuth.createUserWithEmailAndPassword(email, passWord)
                                                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<AuthResult> task) {
                                                       if (task.isSuccessful()) {
                                                           UserClass user = new UserClass(email, passWord, userName);
                                                           FirebaseDatabase.getInstance().getReference("user")
                                                                   .child(FirebaseAuth.getInstance()
                                                                   .getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                               @Override
                                                               public void onComplete(@NonNull Task<Void> task) {
                                                                   if (task.isSuccessful()) {
                                                                       Toast.makeText(getApplicationContext(), "BẠN ĐÃ ĐĂNG KÍ TÀI KHOẢN THÀNH CÔNG !!!!", Toast.LENGTH_LONG).show();
                                                                   } else {
                                                                       Toast.makeText(getApplicationContext(), "BẠN ĐÃ ĐĂNG KÍ TÀI KHOẢN THẤT BẠI VUI LÒNG THAO TÁC LẠI !!!!", Toast.LENGTH_LONG).show();
                                                                   }
                                                               }
                                                           });
                                                       } else {
                                                           Toast.makeText(getApplicationContext(), "BẠN ĐÃ ĐĂNG KÍ TÀI KHOẢN THẤT BẠI VUI LÒNG THAO TÁC LẠI !!!!", Toast.LENGTH_LONG).show();
                                                       }
                                                   }
                                               });


                                               // Truyền dữ liệu lên FireBase
//                    FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    DatabaseReference myRef = database.getReference("user");
//                    String email = edEmail.getText().toString();
//                    String userName = edName.getText().toString();
//                    String passWord = edPassWord.getText().toString();
//                    UserClass user = new UserClass( email , passWord , userName);
//                    myRef.child("1").setValue(user);
//                    Intent intent = new Intent(Registration.this , ConfirmSDT.class);
//                    intent.putExtra("phone" , edSTD.getText().toString());
                                               //    startActivity(intent);
                                           } else {

                                               Toast toast = Toast.makeText(RegistrationActivity.this, "Miật khẩu nhập lại không chính xác vu lòng kiểm tra lại !!! ", Toast.LENGTH_LONG);
                                               toast.show(); // nhớ show ra nhe
                                           }

                                       }
                                   });


        tvHavaAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this , LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }



    void anhXa()
    {
        edEmail = findViewById(R.id.edEmail);
        tvHavaAccount = findViewById(R.id.tvhaveAccount);
        btnNext = findViewById(R.id.btnNext);
        edName = findViewById(R.id.edFullName);
        edPassWord= findViewById(R.id.edPassWord);
        edPassWord2= findViewById(R.id.edPassWord2);
    }

}