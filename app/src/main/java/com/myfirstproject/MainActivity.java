package com.myfirstproject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.KeyStore;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MainActivity extends AppCompatActivity {


    private TextView start;
    private FingerprintManager fingerprintManager;
    private FingerprintManagerCompat fingerprintManagerCompat;
    private final String TAG = "MainActivity";
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //zhengxudong
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.start);
        fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
        fingerprintManagerCompat = FingerprintManagerCompat.from(this);
        //获取秘钥容器
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            //对称加密创建KeyGenerator对象
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            //生产key
            keyStore.load(null);
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder("abc", KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                builder.setInvalidatedByBiometricEnrollment(true);
            }
            keyGenerator.init(builder.build());
            keyGenerator.generateKey();
            //Cipher 对象是一个按照一定的加密规则，将数据进行加密后的一个对象。
            Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            SecretKey key = (SecretKey) keyStore.getKey("abc", null);
            cipher.init(Cipher.ENCRYPT_MODE,key);
           /* fingerprintManagerCompat.authenticate(new FingerprintManagerCompat.CryptoObject(cipher), 1, new CancellationSignal(), new FingerprintManagerCompat.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errMsgId, CharSequence errString) {
                    super.onAuthenticationError(errMsgId, errString);
                    Toast.makeText(MainActivity.this, "cuowu", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                    super.onAuthenticationHelp(helpMsgId, helpString);
                }

                @Override
                public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    Toast.makeText(MainActivity.this, "chenggong", Toast.LENGTH_SHORT).show();
                    try {

                        Field field = result.getClass().getDeclaredField("mFingerprint");
                        field.setAccessible(true);
                        Object fingerPrint = field.get(result);

                        Class<?> clzz = Class.forName("android.hardware.fingerprint.Fingerprint");
                        Method getName = clzz.getDeclaredMethod("getName");
                        Method getFingerId = clzz.getDeclaredMethod("getFingerId");
                        Method getGroupId = clzz.getDeclaredMethod("getGroupId");
                        Method getDeviceId = clzz.getDeclaredMethod("getDeviceId");

                        CharSequence name = (CharSequence) getName.invoke(fingerPrint);
                        int fingerId = (int) getFingerId.invoke(fingerPrint);
                        int groupId = (int) getGroupId.invoke(fingerPrint);
                        long deviceId = (long) getDeviceId.invoke(fingerPrint);
                        Toast.makeText(MainActivity.this, "111----"+fingerId, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "name: " + name);
                        Log.e(TAG, "fingerId: " + fingerId);
                        Log.e(TAG, "groupId: " + groupId);
                        Log.e(TAG, "deviceId: " + deviceId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    Toast.makeText(MainActivity.this, "shibai", Toast.LENGTH_SHORT).show();
                }
            },null);*/

           fingerprintManager.authenticate(new FingerprintManager.CryptoObject(cipher), new android.os.CancellationSignal(), 1, new FingerprintManager.AuthenticationCallback() {
               @Override
               public void onAuthenticationError(int errorCode, CharSequence errString) {
                   super.onAuthenticationError(errorCode, errString);
               }

               @Override
               public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                   super.onAuthenticationHelp(helpCode, helpString);
               }

               @Override
               public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                   super.onAuthenticationSucceeded(result);
                   Toast.makeText(MainActivity.this, "chenggong", Toast.LENGTH_SHORT).show();
                   try {
                       Field field = result.getClass().getDeclaredField("mFingerprint");
                       field.setAccessible(true);
                       Object fingerPrint = field.get(result);



                       Class<?> clzz = Class.forName("android.hardware.fingerprint.Fingerprint");
                       Method getName = clzz.getDeclaredMethod("getName");
                       Method getFingerId = clzz.getDeclaredMethod("getFingerId");
                       Method getGroupId = clzz.getDeclaredMethod("getGroupId");
                       Method getDeviceId = clzz.getDeclaredMethod("getDeviceId");
                        if (fingerPrint!=null){

                            String name = (String) getName.invoke(fingerPrint);
                            Log.e(TAG, "111");
                            Log.e(TAG, "name: "+getName.invoke(fingerPrint) );
                            int fingerId = (int) getFingerId.invoke(fingerPrint);
                            int groupId = (int) getGroupId.invoke(fingerPrint);
                            long deviceId = (long) getDeviceId.invoke(fingerPrint);
                            Toast.makeText(MainActivity.this, "111----"+fingerId, Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "name: " + name);
                            Log.e(TAG, "fingerId: " + fingerId);
                            Log.e(TAG, "groupId: " + groupId);
                            Log.e(TAG, "deviceId: " + deviceId);
                        }else {
                            Log.e(TAG, "222");
                        }
                   } catch (Exception e) {
                       e.printStackTrace();
                   }

               }

               @Override
               public void onAuthenticationFailed() {
                   super.onAuthenticationFailed();
               }
           },null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getFingerprintInfo();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    //获取手机中存储的指纹列表
    public void getFingerprintInfo() {
        try {
            //FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(Context.FINGERPRINT_SERVICE);
            Method method = FingerprintManager.class.getDeclaredMethod("getEnrolledFingerprints");
            Object obj = method.invoke(fingerprintManager);
            if (obj != null) {
                Class<?> clazz = Class.forName("android.hardware.fingerprint.Fingerprint");
                Method getFingerId = clazz.getDeclaredMethod("getFingerId");
                Method getName = clazz.getDeclaredMethod("getName");
                Method getGroupId = clazz.getDeclaredMethod("getGroupId");
                Method getDeviceId = clazz.getDeclaredMethod("getDeviceId");
                for (int i = 0; i < ((List) obj).size(); i++) {
                    Object item = ((List) obj).get(i);
                    if (null == item) {
                        continue;
                    }

                    Log.d(TAG, "fingerId: " + getFingerId.invoke(item)+"name: "+getName.invoke(item)+"groupId: "+getGroupId.invoke(item)+"deviceId: "+getDeviceId.invoke(item));
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
