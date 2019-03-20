package floaterr.floater;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FloatingWindow extends Service {
    BottomNavigationView mv;
  public  Icon im ;
    WindowManager wm;
    LinearLayout ll;
    private Button mainButton;
    private ViewGroup mView;
    private LayoutInflater inflater;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

//        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mView =  (ViewGroup) inflater.inflate(R.layout.activity_test, null);




       wm = (WindowManager) getSystemService(WINDOW_SERVICE);
      //  ma();

        ll = new LinearLayout(this);
       ll.setOrientation(LinearLayout.HORIZONTAL);

      //  ll.setBackgroundColor(Color.RED);
        LinearLayout.LayoutParams layoutParameteres = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ll.setBackgroundColor(Color.argb(50,255,0,0));
        ll.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        ll.setLayoutParams(layoutParameteres);
//        ll.addView(mView);
        final WindowManager.LayoutParams parameters = new WindowManager.LayoutParams(
                650  , LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
parameters.resolveLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        parameters.gravity = Gravity.CENTER | Gravity.CENTER;
        parameters.x = 0;
        parameters.y = 0;

        ImageButton stop = new ImageButton(this);
        Button  back = new Button(this);
        TextView home = new TextView(this);
        LinearLayout.LayoutParams parambtns = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, 40);
        parambtns.weight = 1.0f;
        stop.setLayoutParams(parambtns);
        ImageButton b=new ImageButton(this);

        stop.getPaddingRight();
        b.setBackground(this.getResources().getDrawable(R.drawable.back));
        stop.setBackground(this.getResources().getDrawable(R.drawable.po2));
        back.setText("Stop");
     //   home.setText("Stop");

        ViewGroup.LayoutParams btnParameters = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

     //   stop.setLayoutParams(btnParameters);

        ViewGroup.LayoutParams imagebParameters = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        b.setLayoutParams(parambtns);
        home.setLayoutParams(parambtns);
        ll.addView(stop);
        ll.addView(home);
        ll.addView(b);

        wm.addView(ll, parameters);

        ll.setOnTouchListener(new View.OnTouchListener() {
            WindowManager.LayoutParams updatedParameters = parameters;
            double x;
            double y;
            double pressedX;
            double pressedY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        x = updatedParameters.x;
                        y = updatedParameters.y;

                        pressedX = event.getRawX();
                        pressedY = event.getRawY();

                        break;

                    case MotionEvent.ACTION_MOVE:
                        updatedParameters.x = (int) (x + (event.getRawX() - pressedX));
                        updatedParameters.y = (int) (y + (event.getRawY() - pressedY));

                        wm.updateViewLayout(ll, updatedParameters);

                    default:
                        break;
                }

                return false;
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wm.removeView(ll);
                stopSelf();
                System.exit(0);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startHome = new Intent(Intent.ACTION_MAIN);
                Intent in =new Intent(Intent.ACTION_MAIN);
                getApplication();
              //  Toast.makeText(FloatingWindow.this,    getApplication().toString(),Toast.LENGTH_LONG).show();
                startHome.addCategory(Intent.CATEGORY_HOME);
                startHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startHome);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    public void ma()
    {

        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                //WindowManager.LayoutParams.TYPE_INPUT_METHOD |
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,// | WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.RIGHT | Gravity.TOP;
        mView = (ViewGroup) inflater.inflate(R.layout.activity_test, null);

        wm.addView(mView, params);
    }
}