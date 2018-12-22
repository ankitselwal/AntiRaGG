package its.antiragg;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class co_details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_co_details);

        ListView listView = (ListView) findViewById(R.id.co_list);
        ArrayList<Contact>contacts = new ArrayList<>();

        contacts.add(new Contact("Dr. Manjeet Singh (Proctor)","9868617422"));
        contacts.add(new Contact("Dr. Dushyant Shukla (Dy. Proctor Boys)","9958666066"));
        contacts.add(new Contact("Dr. Neelam Turk (Dy. Proctor Girls)","9958993483"));
        contacts.add(new Contact("Ms. Shruti Sharma (Assistant Proctor)","9958138139"));
        contacts.add(new Contact("Sh. Sushil Kumar (Assistant Proctor)","9759346649"));
        contacts.add(new Contact("Sh. Satvinder Singh(Assistant Proctor)","9466813899"));
        contacts.add(new Contact("Dr. OP Mishra (Assistant Proctor)","9818602901"));

        ContactAdapter contactAdapter = new ContactAdapter(co_details.this, R.layout.contact_item, contacts);
        listView.setAdapter(contactAdapter);


    }
}
