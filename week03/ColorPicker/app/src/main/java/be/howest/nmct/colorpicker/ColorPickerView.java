package be.howest.nmct.colorpicker;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
/**
 * Created by chris on 23.02.15.
 */
public class ColorPickerView extends View {


    private String color = "#FFFFFF";
    private Paint paint;
    private Rect rect;

    public void setColor(String color) {
        this.color = color;
        paint.setColor(Color.parseColor(color));
        invalidate();
    }
    public ColorPickerView(Context context) {
        super(context);
        init(null,0);
    }

    public ColorPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs,0);
    }

    public ColorPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,defStyleAttr);
    }

//    public ColorPickerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init(attrs,defStyleAttr);
//
//    }
    private void init(AttributeSet attrs,int defStyleAttr){
        paint = new Paint();
        paint.setColor(Color.parseColor(color));
        rect = new Rect(0,0,getWidth(),getHeight());
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorDialog();
            }
        });
    }

    private void showColorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Pick a color")
                .setItems(R.array.holo_colors, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which)
                    {
                        selectColor(which);
                    }
                });
        builder.create().show();
    }

    private void selectColor(int which) {
        switch (which){
            case 0:
                setColor("#33B5E5");
                break;
            case 1:
                setColor("#AA66CC");
                break;
            case 2:
                setColor("#99CC00");
                break;
            case 3:
                setColor("#FFBB33");
                break;
            case 4:
                setColor("#FF4444");
                break;
            default:
                break;
        }
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rect.set(0,0,getWidth(),getHeight());
        canvas.drawRect(rect, paint);
    }
}
