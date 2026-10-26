package com.gio.keymapper;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends Activity {
    private TextView status, devices, log;
    private final ArrayList<String> events = new ArrayList<>();
    private final int bg=Color.rgb(12,17,30), card=Color.rgb(26,35,54), accent=Color.rgb(76,204,244);
    private TextView makeText(String s, int size, int color) {
        TextView t = new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color);
        t.setPadding(22,18,22,18); return t;
    }
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        ScrollView scroll=new ScrollView(this); scroll.setFillViewport(true); scroll.setBackgroundColor(bg);
        LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(14,22,14,22);
        scroll.addView(root);
        TextView title=makeText("GIO KEYMAPPER  •  v0.1",24,accent); title.setTypeface(null, Typeface.BOLD); root.addView(title);
        root.addView(makeText("Keyboard + mouse hardware tester",17,Color.WHITE));
        root.addView(makeText("This prototype detects connected devices and tests keys, mouse buttons and motion inside this app. It does NOT control PUBG or inject touches into other apps.",14,0xffb9c6d9));
        devices=makeText("",15,Color.WHITE); devices.setBackgroundColor(card); root.addView(devices);
        TextView refresh=makeText("↻  Refresh connected devices",17,accent); refresh.setOnClickListener(v->refreshDevices()); root.addView(refresh);
        status=makeText("Press any keyboard key or mouse button here",17,Color.WHITE); status.setBackgroundColor(card); root.addView(status);
        TextView profile=makeText("SAMPLE PUBG PROFILE\n\nW/A/S/D — Move\nRight mouse — Aim / camera mode (planned)\nLeft mouse — Fire (planned)\nR — Reload\nSpace — Jump\nShift — Sprint\nC — Crouch\nZ — Prone\nF — Interact",16,Color.WHITE); profile.setBackgroundColor(card); root.addView(profile);
        root.addView(makeText("LIVE INPUT LOG",16,accent));
        log=makeText("Waiting for input…",14,0xffc4d2e7); log.setBackgroundColor(card); root.addView(log);
        root.setFocusableInTouchMode(true); root.requestFocus();
        setContentView(scroll); refreshDevices();
    }
    private void refreshDevices() {
        StringBuilder sb=new StringBuilder("CONNECTED INPUT DEVICES\n\n");
        int count=0;
        for(int id: InputDevice.getDeviceIds()) {
            InputDevice d=InputDevice.getDevice(id); if(d==null)continue;
            int src=d.getSources();
            if((src & InputDevice.SOURCE_KEYBOARD)==InputDevice.SOURCE_KEYBOARD || (src & InputDevice.SOURCE_MOUSE)==InputDevice.SOURCE_MOUSE) {
                sb.append("• ").append(d.getName()).append("\n"); count++;
            }
        }
        if(count==0)sb.append("No external keyboard/mouse detected yet.");
        devices.setText(sb.toString());
    }
    private void add(String s) {
        events.add(0,s); if(events.size()>12)events.remove(events.size()-1);
        StringBuilder b=new StringBuilder(); for(String e:events)b.append(e).append("\n");
        log.setText(b.toString()); status.setText(s);
    }
    @Override public boolean dispatchKeyEvent(KeyEvent e) {
        if(e.getAction()==KeyEvent.ACTION_DOWN && e.getRepeatCount()==0) {
            add("Key: "+KeyEvent.keyCodeToString(e.getKeyCode())); return true;
        }
        return super.dispatchKeyEvent(e);
    }
    @Override public boolean dispatchGenericMotionEvent(MotionEvent e) {
        if((e.getSource() & InputDevice.SOURCE_MOUSE)==InputDevice.SOURCE_MOUSE) {
            if(e.getActionMasked()==MotionEvent.ACTION_HOVER_MOVE || e.getActionMasked()==MotionEvent.ACTION_MOVE) {
                status.setText("Mouse x="+(int)e.getX()+" y="+(int)e.getY()+" buttons="+e.getButtonState()); return true;
            }
        }
        return super.dispatchGenericMotionEvent(e);
    }
    @Override public boolean dispatchTouchEvent(MotionEvent e) {
        if((e.getSource() & InputDevice.SOURCE_MOUSE)==InputDevice.SOURCE_MOUSE && e.getActionMasked()==MotionEvent.ACTION_DOWN) {
            add("Mouse click • buttons="+e.getButtonState());
        }
        return super.dispatchTouchEvent(e);
    }
}
