package com.muzimp.keyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

import java.util.HashMap;

public class MuzimpKeyboardService extends InputMethodService
        implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView keyboardView;
    private Keyboard keyboard;
    private boolean toEga = true;

    private static final HashMap<Character, String> egaMap = new HashMap<>();
    private static final HashMap<String, Character> reverseMap = new HashMap<>();

    static {
        egaMap.put('A', "⎔"); egaMap.put('B', "☌"); egaMap.put('C', "𐑒");
        egaMap.put('D', "ᓚ"); egaMap.put('E', "꙰"); egaMap.put('F', "𐠄");
        egaMap.put('G', "⋔"); egaMap.put('H', "𓂀"); egaMap.put('I', "𐌠");
        egaMap.put('J', "ʬ"); egaMap.put('K', "𓃗"); egaMap.put('L', "𖣘");
        egaMap.put('M', "𐍊"); egaMap.put('N', "𖼀"); egaMap.put('O', "𐊪");
        egaMap.put('P', "ꖦ"); egaMap.put('Q', "ᙡ"); egaMap.put('R', "𖫪");
        egaMap.put('S', "𖤐"); egaMap.put('T', "𓇌"); egaMap.put('U', "𐑈");
        egaMap.put('V', "𖼨"); egaMap.put('W', "𖨆"); egaMap.put('X', "𐋎");
        egaMap.put('Y', "𐊲"); egaMap.put('Z', "𖹭");

        for (Character c : egaMap.keySet()) {
            reverseMap.put(egaMap.get(c), c);
        }
    }

    @Override
    public View onCreateInputView() {
        keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
        keyboard = new Keyboard(this, R.xml.keyboard_layout);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setOnKeyboardActionListener(this);
        return keyboardView;
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        if (ic == null) return;

        if (primaryCode == Keyboard.KEYCODE_DELETE) {
            ic.deleteSurroundingText(1, 0);
        } else if (primaryCode == -100) { // Custom switch key
            toEga = !toEga;
        } else {
            char code = (char) primaryCode;
            if (toEga) {
                String out = egaMap.getOrDefault(Character.toUpperCase(code), String.valueOf(code));
                ic.commitText(out, 1);
            } else {
                ic.commitText(String.valueOf(code), 1);
            }
        }
    }

    // Boş geçilenler
    @Override public void onPress(int i) {}
    @Override public void onRelease(int i) {}
    @Override public void onText(CharSequence charSequence) {}
    @Override public void swipeLeft() {}
    @Override public void swipeRight() {}
    @Override public void swipeDown() {}
    @Override public void swipeUp() {}
}
