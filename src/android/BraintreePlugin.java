package com.pdg.plugins.braintree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.braintreepayments.api.dropin.BraintreePaymentActivity;

public class BraintreePlugin extends CordovaPlugin {

    private CallbackContext callbackContext;
    private Activity activity = null;
    private static final int DROP_IN_REQUEST = 100;
    private static final int PAYMENT_BUTTON_REQUEST = 200;
    private static final int CUSTOM_REQUEST = 300;
    private static final int THREE_D_SECURE_REQUEST = 400;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        Log.i("Bookster", "INIT:" + action);
        boolean retValue = true;
        this.callbackContext = callbackContext;
        this.activity = this.cordova.getActivity();
        if (action.equals("init")) {
            this.init(args);
        } else if (action.equals("getCreditCardInfo")) {
            this.getCreditCardInfo(args);
        } else if (action.equals("dismiss")) {
            this.dismiss(args);
        } else {
            retValue = false;
        }
        return retValue;

    }

    private void dismiss(JSONArray args) {
        // TODO Auto-generated method stub

    }

    private void getCreditCardInfo(JSONArray args) {
        // TODO Auto-generated method stub

    }

    private void startService(String clientToken) {
        Intent intent = new Intent(this.activity, BraintreePaymentActivity.class);

//        Customization customization = new CustomizationBuilder()
//                .primaryDescription("Cart")
//                .secondaryDescription("3 Items")
//                .amount("$35")
//                .submitButtonText("Purchase")
//                .build();
//        intent.putExtra(BraintreePaymentActivity.EXTRA_CUSTOMIZATION, customization);
        intent.putExtra(BraintreePaymentActivity.EXTRA_CLIENT_TOKEN, clientToken);
        this.cordova.startActivityForResult(this, intent, DROP_IN_REQUEST);
    }

    private void init(JSONArray args) {
        // TODO Auto-generated method stub
        Log.i("Bookster", "INIT:" + args);
        final String token = getTokenFromJson(args);
        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                startService(token);
            }
        });

    }

    private String getTokenFromJson(JSONArray args) {
        String token = null;
        try {
            JSONObject obj = args.getJSONObject(0);
            token = obj.getString("token");
        } catch (JSONException e) {
            Log.e("Bookster", "exception", e);
        }
        return token;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DROP_IN_REQUEST && resultCode == BraintreePaymentActivity.RESULT_OK) {
            String paymentMethodNonce = data.getStringExtra(BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE);
            Log.i("Bookster", paymentMethodNonce);
            this.callbackContext.success(paymentMethodNonce);
        } else {
            this.callbackContext.error("Authorization was ok but no code");
        }
    }
}
