package com.deitel.diego_villacis_2doparcial_prueba_01;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fisei.adressbookapp.data.DatabaseDescription.Contact;

public class ContactsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    public interface ContactsFragmentListener{
        void onContactSelected(Uri contactUri);
        void onAddContact();
    }
    private static final int CONTACTS_LOADER = 0;
    private ContactsFragmentListener listener;
    private ContactsAdapter contactsAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
        contactsAdapter = new ContactsAdapter(new ContactsAdapter.ContactClickListener(){
            @Override
            public void onClick(Uri contactUri){
                listener.onContactSelected(contactUri);
            }
        }
        );
        recyclerView.setAdapter(contactsAdapter);
        recyclerView.addItemDecoration(new ItemDivider(getContext()));
        recyclerView.setHasFixedSize(true);
        FloatingActionButton addButton = (FloatingActionButton) view.findViewById(R.id.addButton);
        addButton.setOnClickListener( new View.OnClickListener(){
            @Overrid<resources>
    <string name="app_name">Flag Quiz</string>
    <string name="action_settings">Settings</string>
    <string name="number_of_choices">Number of Choices</string>
    <string name="number_of_choices_description">Display 2, 4, 6 or 8 guess buttons</string>
    <string name="world_regions">Regions</string>
    <string name="world_regions_description">Regions to include in the quiz</string>
    <string name="guess_country">Guess the Country</string>
    <string name="results">%1$d guesses, %2$.02f%% correct</string>
    <string name="incorrect_answer">Incorrect!</string>
    <string name="default_region_message">
            One region must be selected. Setting Europe as the default region.
    </string>
    <string name="restarting_quiz">Quiz will restart with your new settings</string>
    <string name="question">Question %1$d of %2$d</string>
    <string name="reset_quiz">Reset Quiz</string>
    <string name="image_description">Image of the current flag in the quiz</string>
    <string name="default_region">Europe</string>
    <string name="title_activity_settings">Settings</string>
</resources>e
            public void onClick(View view){
                listener.onAddContact();
            }
        });
        return view;
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        listener = (ContactsFragmentListener) context;
    }
    @Override
    public void onDetach(){
        super.onDetach();
        listener = null;
    }
    @Override
    public void onActivityCreated(Bundle savedInstaceState){
        super.onActivityCreated(savedInstaceState);
        getLoaderManager().initLoader(CONTACTS_LOADER, null, this);
    }
    public void updateContactList(){
        //contactsAdapter.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args){
        switch(id){
            case CONTACTS_LOADER:
                return new CursorLoader(getActivity(),
                        Contact.CONTENT_URI,
                        null, null, null,
                        Contact.COLUMN_NAME + " COLLATE NOCASE ASC");
            default:
                return null;
        }
    }
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data){
        contactsAdapter.swapCursor(data);
    }
    @Override
    public void onLoaderReset(Loader<Cursor> loader){
        contactsAdapter.swapCursor(null);
    }
}