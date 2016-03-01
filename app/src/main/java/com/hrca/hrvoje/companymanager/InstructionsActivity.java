package com.hrca.hrvoje.companymanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class InstructionsActivity extends Activity {

    private AdView mAdView;

    public InstructionAdapter adapter;
    public InstructionView lastInstructionView = null;
    public FrameLayout container;
    public ImageButton previousButton;
    public ImageButton nextButton;
    public TextView status;
    public FrameLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        this.adapter = new InstructionAdapter(this);
        this.container = (FrameLayout) this.findViewById(R.id.view);
        this.previousButton = (ImageButton) this.findViewById(R.id.previous);
        this.nextButton = (ImageButton) this.findViewById(R.id.next);
        this.status = (TextView) this.findViewById(R.id.status);
        this.layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        updateDisplay(this.adapter.first());
        this.mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        this.mAdView.loadAd(adRequest);
    }

    void updateDisplay(InstructionView instructionView) {
        if (instructionView == null) {
            finish();
            return;
        }
        this.previousButton.setEnabled(this.adapter.getPosition() != 0);
        status.setText(Integer.toString(this.adapter.getPosition() + 1) + "/" + Integer.toString(this.adapter.getCount()));
        if (instructionView != this.lastInstructionView) {
            this.container.removeAllViews();
            this.container.addView(instructionView, this.layoutParams);
        }
    }

    public void previous(View view) {
        updateDisplay(this.adapter.previous());
    }

    public void next(View view) {
        updateDisplay(this.adapter.next());
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
