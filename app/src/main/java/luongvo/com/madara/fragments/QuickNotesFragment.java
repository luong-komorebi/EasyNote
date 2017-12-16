package luongvo.com.madara.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import luongvo.com.madara.R;
import luongvo.com.madara.adapters.NotebooksAdapter;
import luongvo.com.madara.adapters.QuickNotesAdapter;
import luongvo.com.madara.database.DBHelper;
import luongvo.com.madara.helper.ItemTouchHelperCallback;
import luongvo.com.madara.model.QuickNote;
import luongvo.com.madara.utils.Constants;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuickNotesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuickNotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuickNotesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;
    private RecyclerView rvQuickNotes;
    private List<QuickNote> arrQuickNotes;
    private QuickNotesAdapter quickNotesAdapter;

    private OnFragmentInteractionListener mListener;

    public QuickNotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuickNotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuickNotesFragment newInstance(String param1, String param2) {
        QuickNotesFragment fragment = new QuickNotesFragment();
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
        view = inflater.inflate(R.layout.fragment_quick_notes, container, false);

        addControls();
        setupRvQuickNotes();

        return view;
    }

    private void setupRvQuickNotes() {
        // TODO: Replace example ArrayList with real data list
        arrQuickNotes = new ArrayList<>();
        arrQuickNotes = DBHelper.getQuickNotes();

        quickNotesAdapter = new QuickNotesAdapter(getContext(), arrQuickNotes);

        RecyclerView.LayoutManager layoutManager =
            new StaggeredGridLayoutManager(Constants.staggeredGridSpanCount,
                Configuration.ORIENTATION_PORTRAIT);
        layoutManager.setAutoMeasureEnabled(false);

        ItemTouchHelper.Callback callback =
                new ItemTouchHelperCallback(quickNotesAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(rvQuickNotes);

        rvQuickNotes.setLayoutManager(layoutManager);
        rvQuickNotes.setItemAnimator(new DefaultItemAnimator());
        rvQuickNotes.setAdapter(quickNotesAdapter);
    }

    private void addControls() {
        rvQuickNotes = view.findViewById(R.id.rvQuickNotes);
        rvQuickNotes.setHasFixedSize(true);
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
