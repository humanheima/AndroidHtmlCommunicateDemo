package com.zyj.hybridbridge;

import android.webkit.JavascriptInterface;

/**
 * Created by p_dmweidu on 2023/10/9
 * Desc: H5统一通过此类与原生进行交互
 */
public class NativeInterface {

    private HandleJsMessage mHandleJsMessage;

    public NativeInterface(HandleJsMessage handleJsMessage) {
        mHandleJsMessage = handleJsMessage;
    }


    @JavascriptInterface
    public void sendMessage(String jsonStr) {
        if (mHandleJsMessage != null) {
            mHandleJsMessage.handle(jsonStr);
        }
    }

}
