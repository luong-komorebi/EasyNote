package luongvo.com.madara.helper;

/**
 * Created by VVLv on 11/26/2017.
 */

public interface ItemTouchHelperAdapter {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
