package sk.uniza.fri.handler_example;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /***
     * Holds context of running instance of this activity.
     * So is possible to send and show Toads.
     */
    private Context _context;

    /***
     * Behold, this contains our message sender for threads
     */
    private Handler _handler;


    /**
     * used as flag. If true thread will thread. If false threads will terminate itself.
     */
    private boolean _threadCanThread = true;

    /***
     * Just informaction about total amount of threading threads
     */
    private int _threadCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get context of currect instance of our main activity
        _context = getApplicationContext();
        // get handler - the message tube ;)
        _handler = new Handler();

        // logic for thread button
        Button button = (Button) findViewById(R.id.bt_run);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set threads can thread
                _threadCanThread = true;
                // create a new instance of thread, increase running threads count and start it
                new Thread(new ThreadingThread(_threadCount++)).start();
            }
        });

        // logic for thread button
        button = (Button) findViewById(R.id.bt_stop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set threading flag to false
                // all threads will close their loop end terminate safely
                _threadCanThread = false;
                makeToast("Stop the threads!");
            }
        });
    }

    /***
     * This method just run Toad
     * @param message
     */
    public void makeToast(String message){
        // call toast in main activity context
        Toast.makeText(_context, message, Toast.LENGTH_SHORT).show();
        Log.i("tmsg",message);
    }

    /***
     * Our class of threading threads
     */
    class ThreadingThread implements Runnable{

        // thread internal info about its number
        private int _threadNumber;

        public ThreadingThread(int number){
            _threadNumber = number;
        }

        @Override
        public void run() {

            while (_threadCanThread) { // thread will thread until it can
                try {
                    Thread.sleep(500); // take down thread so other can do their bussines
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // and finally send message via Handler to main activity
                _handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // call main activity method
                        makeToast("Kvak from thread " + _threadNumber);
                    }
                });
            }
        }
    }
}
