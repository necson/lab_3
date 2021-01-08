package com.example.eventhandling;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.btn);
        textView = findViewById(R.id.tv_t);
        textView1 = findViewById(R.id.tv_text);
        textView2 = findViewById(R.id.tv_me1);
        textView3 = findViewById(R.id.tv_me2);
        textView.setVisibility(View.GONE);
        button.setVisibility(View.GONE);
        textView1.setVisibility(View.GONE);
        textView2.setVisibility(View.GONE);
        textView3.setVisibility(View.GONE);

    }
    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    private GestureDetectorCompat gDetector;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();
        Button button = findViewById(R.id.btn);

        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.click_btn:
                setTitle("ButtonClick");
                textView1.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
                textView2.setVisibility(View.GONE);
                textView3.setVisibility(View.GONE);
                button.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        textView1.setText("Button clicked");
                    }
                }
                );
                button.setOnLongClickListener(
                        new Button.OnLongClickListener() {
                            public boolean onLongClick(View v) {
                                textView1.setText("Long button click");
                                return true;
                            }
                        }
                );
                return true;
            case R.id.m_event:
                setTitle("MotionEvent");
                textView2.setVisibility(View.VISIBLE);
                textView3.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
                textView1.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                ConstraintLayout myLayout = findViewById(R.id.main_activity);
                myLayout.setOnTouchListener(new ConstraintLayout.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent m) {
                        handleTouch(m);
                        return true;
                    }
                }
                );
                return true;
            case R.id.cmn_g:
                setTitle("CommonGestures");
                textView.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
                textView1.setVisibility(View.GONE);
                textView2.setVisibility(View.GONE);
                textView3.setVisibility(View.GONE);
                ConstraintLayout myLayout1 = findViewById(R.id.main_activity);
                myLayout1.setOnTouchListener(View::onTouchEvent);
                this.gDetector = new GestureDetectorCompat(this,this);
                gDetector.setOnDoubleTapListener(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void handleTouch(MotionEvent m) {
        int pointerCount = m.getPointerCount();
        for (int i = 0; i < pointerCount; i++)
        {
            int x = (int) m.getX(i);
            int y = (int) m.getY(i);
            int id = m.getPointerId(i);
            int action = m.getActionMasked();
            int actionIndex = m.getActionIndex();
            String actionString;
            switch (action)
            {
                case MotionEvent.ACTION_DOWN:
                    actionString = "DOWN";
                    break;
                case MotionEvent.ACTION_UP:
                    actionString = "UP";
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    actionString = "PNTR DOWN";
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    actionString = "PNTR UP";
                    break;
                case MotionEvent.ACTION_MOVE:
                    actionString = "MOVE";
                    break;
                default:
                    actionString = "";
            }
            String touchStatus = "Action: " + actionString + " Index: " +
                    actionIndex + " ID: " + id + " X: " + x + " Y: " + y;
            if (id == 0)
                textView2.setText(touchStatus);
            else
                textView3.setText(touchStatus);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        textView.setText ("onDown");
        return true;
    }
    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        textView.setText("onFling");
        return true;
    }
    @Override
    public void onLongPress(MotionEvent event) {
        textView.setText("onLongPress");
    }
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2,
                            float distanceX, float distanceY) {
        textView.setText("onScroll");
        return true;
    }
    @Override
    public void onShowPress(MotionEvent event) {
        textView.setText("onShowPress");
    }
    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        textView.setText("onSingleTapUp");
        return true;
    }
    @Override
    public boolean onDoubleTap(MotionEvent event) {
        textView.setText("onDoubleTap");
        return true;
    }
    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        textView.setText("onDoubleTapEvent");
        return true;
    }
    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        textView.setText("onSingleTapConfirmed");
        return true;
    }
}
