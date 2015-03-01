package com.infmme.readilyapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.infmme.readilyapp.readable.Readable;

/**
 * Created by Dylan on 2/28/2015.
 */
public class ReaderTextView extends TextView {

    private static final int EMPHASIS_COLOR = Color.parseColor("#FA2828");
    private int PrimaryColor = Color.parseColor("#0A0A0A");
    private int SecondaryColor = Color.parseColor("#AAAAAA");
    private float padding  = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 95, this.getResources().getDisplayMetrics());
    private int pos;
    Readable contents;
    private Boolean DisplayContext;


    public void setDisplayContext(Boolean displayContext) {
        DisplayContext = displayContext;
    }

    public void setContents(Readable contents) {
        this.contents = contents;
    }

    public int getPos() {

        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void Advance(int amount) {
        this.pos += amount;
        this.invalidate();
    }

    public void Retreat(int amount ) {
        this.pos -= amount;
        this.invalidate();
    }

    public ReaderTextView(Context context) {
        super(context);
    }

    public ReaderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReaderTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {



        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 95, r.getDisplayMetrics());
        Paint p = this.getPaint();
        p.setColor(EMPHASIS_COLOR);
        float leftoffset = 0;
        float rightoffset = 0;
        float center = 0;

        //Draw Emphasis
        String emphasis = GetEmphasisString();
        center = ((this.getWidth() - p.measureText(emphasis))/2) - px/2;
        canvas.drawText(emphasis,center,this.getBaseline(),p);

        //Draw Rest of current word
        p.setColor(PrimaryColor);
        String wordLeft = GetCurrentLeft();
        String wordRight = GetCurrentRight();
        //Left
        leftoffset += p.measureText(wordLeft);
        canvas.drawText(wordLeft,center - leftoffset, this.getBaseline(),p);
        //Right
        rightoffset += p.measureText(emphasis);
        canvas.drawText(wordRight,center + rightoffset, this.getBaseline(),p);

        if (DisplayContext) {
            //Draw Context
            p.setColor(SecondaryColor);
            String contextLeft = GetContextLeft();
            String contextRight = GetContextRight();
            //Draw Left Context
            leftoffset += p.measureText(contextLeft);
            canvas.drawText(contextLeft,center - leftoffset,this.getBaseline(),p);
            //Draw Right Context
            rightoffset += p.measureText(wordRight);
            canvas.drawText(contextRight,center + rightoffset,this.getBaseline(),p);
            //super.onDraw(canvas);
        }
    }

    private String GetEmphasisString() {
        String word = contents.getWordList().get(pos);
        if (TextUtils.isEmpty(word)){ return ""; }
        int emphasisPosition = contents.getEmphasisList().get(pos);
        String wordEmphasis = word.substring(emphasisPosition, emphasisPosition + 1);
        return wordEmphasis;
    }

    private String GetCurrentLeft() {
        String word = contents.getWordList().get(pos);
        if (TextUtils.isEmpty(word)){ return ""; }
        int emphasisPosition = contents.getEmphasisList().get(pos);
        String wordLeft = word.substring(0, emphasisPosition);
        return wordLeft;
    }

    private String GetCurrentRight() {
        String word = contents.getWordList().get(pos);
        if (TextUtils.isEmpty(word)){ return ""; }
        int emphasisPosition = contents.getEmphasisList().get(pos);
        String wordRight = word.substring(emphasisPosition + 1, word.length());
        return wordRight;
    }

    private String GetContextLeft() {
        int charLen = 0;
        int i = pos;
        StringBuilder format = new StringBuilder();
        while (charLen < 40 && i > 0){
            String word = contents.getWordList().get(--i);
            if (!TextUtils.isEmpty(word)){
                charLen += word.length() + 1;
                format.append(new StringBuffer(word).append(' ').reverse().toString());
            }
        }
        format.reverse();
        return format.toString();
    }

    private String GetContextRight() {
        int charLen = 0;
        int i = pos;
        StringBuilder format = new StringBuilder(" ");
        while (charLen < 40 && i < contents.getWordList().size() - 1){
            String word = contents.getWordList().get(++i);
            if (!TextUtils.isEmpty(word)){
                charLen += word.length() + 1;
                format.append(word).append(" ");
            }
        }
        return format.toString();
    }
}
