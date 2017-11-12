package activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import model.Note;
import adapter.NoteAdapter;


import luongvo.com.madara.R;

import static activity.MainActivity.noteID;

public class NoteListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, NoteAdapter.NoteAdapterListener {
    private List<Note> notes = new ArrayList<>();
    private RecyclerView recyclerView;
    private NoteAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionMode actionMode;
    private View view;
    private ActionModeCallBack actionModeCallBack;

    public NoteListFragment(){}

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        // initialize the adapter
        mAdapter = new NoteAdapter(getActivity(), notes, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        // set adapter
        recyclerView.setAdapter(mAdapter);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_list_note, container, false);

        // initialize toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        // initialize FAB
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
                builder1.setMessage("Add a new note ? ");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent getAddedNote = new Intent(getActivity(), NoteAddActivity.class);
                                startActivityForResult(getAddedNote, 1997);
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });
        // initialize recyclerView
        recyclerView = view.findViewById(R.id.recycler_view);

        // initialize swipeRefreshlayout
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        // initialize actionModeCallBack
        actionModeCallBack = new ActionModeCallBack();

       // show loader and fetch messages
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        onRefresh();
                    }
                }
        );

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1997){
                notes.add((Note)data.getSerializableExtra("note_added"));
                Toast.makeText(getActivity(), " 1 note added", Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        mAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onIconClicked(int position) {
        if( actionMode == null){
            actionMode = ((AppCompatActivity)getActivity()).startSupportActionMode(actionModeCallBack);
        }
        toggleSelection(position);
    }

    // open up action_bar
    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItems().size();
        if(count == 0 ){
            actionMode.finish();
        }else{
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    @Override
    public void onIconImportantClicked(int position) {
            // to be implemented
    }

    @Override
    public void onNoteRowClicked(int position) {
        // verify wether if action mode is on or off
        // if on then change the row state to activated
        if(mAdapter.getSelectedItems().size() > 0 ){
            enableActionMode(position);
        }else{          // Switch to the note's details here
                        // -- Not implemented yet

            Note note = notes.get(position);
            note.setRead(true);
            notes.set(position, note);
            mAdapter.notifyDataSetChanged();
            Toast.makeText(getContext(), " Reading: " + note.getTitle(),Toast.LENGTH_SHORT).show();

        }
    }

    // Switch the background, flip icon of the item at this position
    private void enableActionMode(int position) {
        if(actionMode == null){
            actionMode = ((AppCompatActivity)getActivity()).startSupportActionMode(actionModeCallBack);
        }
        toggleSelection(position);
    }

    @Override
    public void onRowLongClicked(int adapterPosition) {
        enableActionMode(adapterPosition);
    }

    // Handle action on multiple items
    private class ActionModeCallBack implements ActionMode.Callback{

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);

            // disable swipe refresh if actionMode is on
            swipeRefreshLayout.setEnabled(false);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){ // handle actions on items clicked
                case R.id.action_delete: // represent the delete option
                    // delete all selected notes
                    deleteNotes();
                    mode.finish();
                    return true;
                default: // Do nothing by default
                    return false;
            }
        }

        private void deleteNotes() {
            mAdapter.resetAnimationIndex();
            List<Integer> selectedItemPositions = mAdapter.getSelectedItems();
            for(int i = selectedItemPositions.size() - 1; i>=0; i--){
                mAdapter.removeData(selectedItemPositions.get(i));
            }
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.clearSelection();
            swipeRefreshLayout.setEnabled(true);
            actionMode = null;
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mAdapter.resetAnimationIndex();
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}


