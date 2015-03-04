package com.infmme.readilyapp;

import android.content.Context;
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
    private int PrimaryColor;
    private int SecondaryColor;
    private float padding  = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 69, this.getResources().getDisplayMetrics());
    private int pos;
    Readable contents;
    private Boolean[] DisplayContexts;


    public void setDisplayContext(Boolean[] displayContext) {
        DisplayContexts = displayContext;
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

    public void setPrimaryColor(String primaryColor) {
        PrimaryColor = Color.parseColor(primaryColor);
    }

    public void setSecondaryColor(String secondaryColor) {
        SecondaryColor = Color.parseColor(secondaryColor);
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

    /**
     * Draws the text to be displayed by the reader based on current position
     * @param canvas the canvas to draw on
     */
    @Override
    protected void onDraw(Canvas canvas) {



        Paint p = this.getPaint();
        p.setColor(EMPHASIS_COLOR);
        float leftoffset = 0;
        float rightoffset = 0;
        float center = 0;

        //Draw Emphasis
        String emphasis = GetEmphasisString();
        center = ((this.getWidth() - p.measureText(emphasis))/2) - padding/2;
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

            //Draw Context
            p.setColor(SecondaryColor);

            String contextLeft = GetContextLeft();
            String contextRight = GetContextRight();
            if (DisplayContexts[0]) {
                //Draw Left Context
                leftoffset += p.measureText(contextLeft);
                canvas.drawText(contextLeft, center - leftoffset, this.getBaseline(), p);
            }
            if (DisplayContexts[1]) {
                //Draw Right Context
                rightoffset += p.measureText(wordRight);
                canvas.drawText(contextRight, center + rightoffset, this.getBaseline(), p);
            }
            //super.onDraw(canvas);
    }

    /**
     * Gets the character in the current word to be marked with emphasis (red)
     * @return String containing emphasis character
     */
    private String GetEmphasisString() {
        String word = contents.getWordList().get(pos);
        if (TextUtils.isEmpty(word)){ return ""; }
        int emphasisPosition = contents.getEmphasisList().get(pos);
        String wordEmphasis = word.substring(emphasisPosition, emphasisPosition + 1);
        return wordEmphasis;
    }

    /**
     * Gets the characters on the left side of the emphasis character
     * @return String containing left side of the current word
     */
    private String GetCurrentLeft() {
        String word = contents.getWordList().get(pos);
        if (TextUtils.isEmpty(word)){ return ""; }
        int emphasisPosition = contents.getEmphasisList().get(pos);
        String wordLeft = word.substring(0, emphasisPosition);
        return wordLeft;
    }

    /**
     * Get the characters on the right side of the emphasis character
     * @return string containing right side of the current word
     */
    private String GetCurrentRight() {
        String word = contents.getWordList().get(pos);
        if (TextUtils.isEmpty(word)){ return ""; }
        int emphasisPosition = contents.getEmphasisList().get(pos);
        String wordRight = word.substring(emphasisPosition + 1, word.length());
        return wordRight;
    }

    /**
     * Gets up to 60 characters that have already been read
     * @return String containing 60 characters of already read words
     */
    private String GetContextLeft() {
        int charLen = 0;
        int i = pos;
        StringBuilder format = new StringBuilder();
        while (charLen < 60 && i > 0){
            String word = contents.getWordList().get(--i);
            if (!TextUtils.isEmpty(word)){
                charLen += word.length() + 1;
                format.append(new StringBuffer(word).append(' ').reverse().toString());
            }
        }
        format.reverse();
        return format.toString();
    }

    /**
     * Gets up to 60 characters that are soon to be read
     * @return String containing up to 60 characters of upcoming words
     */
    private String GetContextRight() {
        int charLen = 0;
        int i = pos;
        StringBuilder format = new StringBuilder(" ");
        while (charLen < 60 && i < contents.getWordList().size() - 1){
            String word = contents.getWordList().get(++i);
            if (!TextUtils.isEmpty(word)){
                charLen += word.length() + 1;
                format.append(word).append(" ");
            }
        }
        return format.toString();
    }
}
