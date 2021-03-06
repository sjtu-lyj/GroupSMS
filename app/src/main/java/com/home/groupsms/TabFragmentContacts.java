package com.home.groupsms;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.home.groupsms.Adapter.ContactsAdapter;
import com.home.groupsms.Model.Contact;

/**
 * Created by Administrator on 11/23/2015.
 */
public class TabFragmentContacts extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_contacts, container, false);

        MainActivity.RecyclerViewContacts = (RecyclerView) view.findViewById(R.id.recyclerViewContact);
        MainActivity.RecyclerViewContacts.setLayoutManager(new LinearLayoutManager(getContext()));
        MainActivity.RecyclerViewContacts.setItemAnimator(new DefaultItemAnimator());

        MainActivity.RecyclerViewContacts.addOnItemTouchListener(
                new RecyclerViewItemClickListener(getContext(), new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        ContactsAdapter.ViewHolder viewHolder = null;
                        TextView textView = null;

                        textView = (TextView) view.findViewById(R.id.text);
                        viewHolder = (ContactsAdapter.ViewHolder) textView.getTag();
                        Contact c = viewHolder.contact;
                        if (!MainActivity.HashtableSelectedContacts.containsKey(c.phone1)) {
                            Toast.makeText(getActivity().getApplicationContext(), "添加成功 " + c.title, Toast.LENGTH_SHORT).show();
                            Contact contact = new Contact(c.id, c.title, c.phone1, c.phone1Type);
                            MainActivity.HashtableSelectedContacts.put(c.phone1, contact);
                            //MainActivity.SelectedContactsAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), c.title+"已添加" , Toast.LENGTH_SHORT).show();
                        }
                    }
                })
        );

        return view;
    }
}
