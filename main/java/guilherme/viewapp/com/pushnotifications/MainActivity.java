package guilherme.viewapp.com.pushnotifications;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView mProfileLabel;
    private TextView mUsersLabel;
    private TextView mNotificationsLabel;

    private ViewPager mMainPager;

    private PageViewAdapter mPagerViewAdapter;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mProfileLabel = (TextView) findViewById(R.id.profileLabel);
        mUsersLabel = (TextView) findViewById(R.id.allusersLabel);
        mNotificationsLabel = (TextView) findViewById(R.id.notificationsLabel);

        mMainPager = (ViewPager) findViewById(R.id.mainPager);
        mMainPager.setOffscreenPageLimit(2);

        mPagerViewAdapter = new PageViewAdapter(getSupportFragmentManager());
        mMainPager.setAdapter(mPagerViewAdapter);

        mProfileLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMainPager.setCurrentItem(0);

            }
        });

        mUsersLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(1);
            }
        });

        mNotificationsLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(2);
            }
        });

        mMainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                    changeTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @TargetApi(Build.VERSION_CODES.M)
    private void changeTabs(int position) {

        if (position == 0){
            mProfileLabel.setTextColor(getColor(R.color.txtTabBright));
            mProfileLabel.setTextSize(22);

            mUsersLabel.setTextColor(getColor(R.color.txtTabLight));
            mUsersLabel.setTextSize(16);

            mNotificationsLabel.setTextColor(getColor(R.color.txtTabLight));
            mNotificationsLabel.setTextSize(16);
        }
        if (position == 1){
            mProfileLabel.setTextColor(getColor(R.color.txtTabLight));
            mProfileLabel.setTextSize(16);

            mUsersLabel.setTextColor(getColor(R.color.txtTabBright));
            mUsersLabel.setTextSize(22);

            mNotificationsLabel.setTextColor(getColor(R.color.txtTabLight));
            mNotificationsLabel.setTextSize(16);
        }
        if (position == 2){
            mProfileLabel.setTextColor(getColor(R.color.txtTabLight));
            mProfileLabel.setTextSize(16);

            mUsersLabel.setTextColor(getColor(R.color.txtTabLight));
            mUsersLabel.setTextSize(16);

            mNotificationsLabel.setTextColor(getColor(R.color.txtTabBright));
            mNotificationsLabel.setTextSize(22);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            sendToLogin();
        }
    }

    private void sendToLogin() {
        Intent Loginintent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(Loginintent);
        finish();
    }
}
