package fri.kst.vamz;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyActivity extends Activity {

    private Context _context;
    private Handler _handler;
    private boolean _threadCanThread = true;

    private int _threadCount = 0;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        Button button = (Button) findViewById(R.id.bt_run);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _threadCanThread = true;
                new Thread(new ToastMaker(_threadCount++)).start();
            }
        });


        button = (Button) findViewById(R.id.bt_stop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _threadCanThread = false;
                makeToast("Stop the threads!");
            }
        });

        _context = getApplicationContext();
        _handler = new Handler();
    }

    public void makeToast(String message){
        Toast.makeText(_context, message, Toast.LENGTH_SHORT).show();
        Log.i("tmsg",message);
    }


    class ToastMaker implements Runnable{

        private int _threadNumber;

        public ToastMaker(int number){
            _threadNumber = number;
        }

        @Override
        public void run() {

            while (_threadCanThread) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                _handler.post(new Runnable() {
                    @Override
                    public void run() {
                        makeToast("Kvak from thread " + _threadNumber);
                    }
                });
            }
        }
    }
}
