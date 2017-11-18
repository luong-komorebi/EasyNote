package luongvo.com.madara.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Space;

/**
 * Created by VVLv on 11/7/2017.
 * This class help RecylerView to organise CardView. (in term of spacing between CardViews)
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    public GridSpacingItemDecoration() {
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        LinearLayout viewLayout = (LinearLayout) view;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 2;

        if (position % 2 == 0) {
            Space spaceNotebookLeft = (Space) viewLayout.getChildAt(0);
            spaceNotebookLeft.setLayoutParams(layoutParams);
        } else {
            Space spaceNotebookRight = (Space) viewLayout.getChildAt(2);
            spaceNotebookRight.setLayoutParams(layoutParams);
        }
    }
}

