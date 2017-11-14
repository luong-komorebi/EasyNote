package luongvo.com.madara;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

import co.dift.ui.SwipeToAction;
import luongvo.com.madara.Adapter.NoteAdapter;
import luongvo.com.madara.Model.Note;



public class ListNoteFragment extends Fragment implements View.OnClickListener {
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    SwipeToAction swipeToAction;
    View view;


    List<Note> notes = new ArrayList<>();


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        noteAdapter = new NoteAdapter(this.notes);
        recyclerView.setAdapter(noteAdapter);

        swipeToAction = new SwipeToAction(recyclerView, new SwipeToAction.SwipeListener() {
            @Override
            public boolean swipeLeft(final Object itemData) {
                final Note item = (Note) itemData;
                final int pos = removeNote(item);
                displaySnackbar(item.getTitle() + " removed", "Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addNote(pos, item);
                    }
                });
                return true;
            }

            @Override
            public boolean swipeRight(Object itemData) {
                final Note item = (Note) itemData;
                displaySnackbar(item.getTitle() + " loved", null, null);
                return true;
            }

            @Override
            public void onClick(Object itemData) {
                final Note item = (Note) itemData;
                displaySnackbar(item.getTitle() + " clicked", null, null);

            }

            @Override
            public void onLongClick(Object itemData) {
                final Note item = (Note) itemData;
                displaySnackbar(item.getTitle() + " long clicked", null, null);
            }
        });

        populate();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize Fresco library
        Fresco.initialize(getActivity());
        view = inflater.inflate(R.layout.note_list, parent, false);

        return view;
    }

    private void populate() {
        for(int id = 0;id<6;id++)
        this.notes.add(new Note(id, "Author " + id, "Title " + id, "Content " + id, "https://lh3.googleusercontent.com/oKsgcsHtHu_nIkpNd-mNCAyzUD8xo68laRPOfvFuO0hqv6nDXVNNjEMmoiv9tIDgTj8=w170"));

    }

    private void moveToFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.list_note_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

    }

    private void displaySnackbar(String text, String actionName, View.OnClickListener action) {
        Snackbar snack = Snackbar.make(view, text, Snackbar.LENGTH_LONG)
                .setAction(actionName, action);

        View v = snack.getView();
        v.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.WHITE);
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_action)).setTextColor(Color.BLACK);

        snack.show();
    }

    private void addNote(int pos, Note item) {
        notes.add(pos, item);
        noteAdapter.notifyItemInserted(pos);
    }

    private int removeNote(Note note) {
        int pos = notes.indexOf(note);
        notes.remove(note);
        noteAdapter.notifyItemRemoved(pos);
        return pos;
    }

    @Override
    public void onClick(View view) {

    }
}
