package its.antiragg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.util.Base64Utils;
import com.google.firebase.auth.FirebaseAuth;

public class admin_home extends AppCompatActivity{

    private Button button;
    private Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        button = (Button)findViewById(R.id.comp);
        logout = (Button)findViewById(R.id.lout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_home.this, ComplaintActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(admin_home.this, MainActivity.class));
            }
        });
    }
}
