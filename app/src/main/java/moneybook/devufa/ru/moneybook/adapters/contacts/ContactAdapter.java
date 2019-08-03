package moneybook.devufa.ru.moneybook.adapters.contacts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

import moneybook.devufa.ru.moneybook.R;
import moneybook.devufa.ru.moneybook.activity.unconfirmed.PersonPagerActivity;
import moneybook.devufa.ru.moneybook.model.AndroidContact;
import moneybook.devufa.ru.moneybook.model.Person;
import moneybook.devufa.ru.moneybook.service.PersonService;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViwHolder> {

    private List<AndroidContact> androidContacts;
    private UUID personId;
    private AppCompatActivity activity;

    class ContactViwHolder extends RecyclerView.ViewHolder {

        private TextView contactName;
        private TextView contactNumber;
        private AndroidContact contact;
        private Person person;

        public ContactViwHolder(@NonNull final View itemView) {
            super(itemView);

            contactName = itemView.findViewById(R.id.tv_contact_name);
            contactNumber = itemView.findViewById(R.id.tv_contact_number);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    person = PersonService.get(activity).getPersonById(personId);
                    person.setName(contactName.getText().toString());
                    person.setNumber(contactNumber.getText().toString());
                    PersonService.get(activity).updatePerson(person);
                    Intent intent = PersonPagerActivity.newIntent(getActivity(), person.getId());
                    activity.startActivity(intent);
                }
            });
        }

        public void bind(AndroidContact contact) {
            contactName.setText(contact.getContactName());
            contactNumber.setText(contact.getContactNumber());
            this.contact = contact;
        }
    }

    @NonNull
    @Override
    public ContactViwHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_contact, viewGroup, false);
        return new ContactViwHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViwHolder holder, int position) {
        holder.bind(androidContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return androidContacts.size();
    }

    public List<AndroidContact> getAndroidContacts() {
        return androidContacts;
    }

    public void setAndroidContacts(List<AndroidContact> androidContacts) {
        this.androidContacts = androidContacts;
    }

    public UUID getPersonId() {
        return personId;
    }

    public void setPersonId(UUID personId) {
        this.personId = personId;
    }

    public AppCompatActivity getActivity() {
        return activity;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }
}
