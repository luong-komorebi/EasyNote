package luongvo.com.madara.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;

/**
 * Created by Thanh on 11/17/2017.
 */

// insert MARGIN indentations to the left of LINES * lines in a text
public class LeadingMargin implements android.text.style.LeadingMarginSpan.LeadingMarginSpan2 {
    private int margin;
    private int lines;

    public LeadingMargin(int lines, int margin){
        this.margin = margin;
        this.lines = lines;
    }

    @Override
    public int getLeadingMarginLineCount() {
        return lines;
    }

    // Return the value to which identation must be added
    @Override
    public int getLeadingMargin(boolean b) {
        if(b){
            return margin;

        }else{
            return 0;
        }
    }

    @Override
    public void drawLeadingMargin(Canvas canvas, Paint paint,
                                  int i, int i1, int i2, int i3, int i4,
                                  CharSequence charSequence, int i5, int i6,
                                  boolean b, Layout layout) {
    }

}
