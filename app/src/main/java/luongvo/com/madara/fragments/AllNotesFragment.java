package luongvo.com.madara.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import luongvo.com.madara.R;
import luongvo.com.madara.database.DBHelper;
import luongvo.com.madara.database.DBSchema;
import luongvo.com.madara.libs.SwipeToAction;
import luongvo.com.madara.adapters.NotesAdapter;
import luongvo.com.madara.model.NoteCuaThanh;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllNotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllNotesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private ArrayList<NoteCuaThanh> arrNotes;
    private RecyclerView rvNotes;
    private SwipeToAction staNotes;
    private NotesAdapter notesAdapter;


    private OnFragmentInteractionListener mListener;

    public AllNotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllNotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllNotesFragment newInstance(String param1, String param2) {
        AllNotesFragment fragment = new AllNotesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_all_notes, container, false);

        addControls();
        setUpRvNotes();
        setUpStaNotes();

        return view;
    }

    private void addControls() {
        rvNotes = view.findViewById(R.id.rvNotes);
        rvNotes.setHasFixedSize(true);
    }


    private void setUpRvNotes() {

        arrNotes = new ArrayList<>();

        String[] notebookIDs = DBHelper.getNotebooksID();
        System.out.println(notebookIDs[0]);
        for (String tmp : notebookIDs) {
            System.out.println(tmp);
            arrNotes.addAll((ArrayList) DBHelper.getNotes(tmp));
        }

        notesAdapter = new NotesAdapter(getActivity(), this.arrNotes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvNotes.setLayoutManager(layoutManager);
        rvNotes.setAdapter(notesAdapter);


    }

    private void setUpStaNotes() {
        staNotes = new SwipeToAction(rvNotes, new SwipeToAction.SwipeListener() {

            @Override
            public boolean swipeLeft(Object itemData) {
                //TODO: replace with swipe-left action on an item
                return true;
            }

            @Override
            public boolean swipeRight(Object itemData) {
                //TODO: replace with swipe-right action on an item
                return true;
            }

            @Override
            public void onClick(Object itemData) {
                //TODO: replace with click action on an item
            }

            @Override
            public void onLongClick(Object itemData) {
                //TODO: replace with long click action on an item
            }


        });


    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
