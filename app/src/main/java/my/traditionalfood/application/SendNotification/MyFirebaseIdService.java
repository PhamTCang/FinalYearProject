package my.traditionalfood.application.SendNotification;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import my.traditionalfood.application.R;

public class MyFirebaseIdService extends FirebaseMessagingService {

    public static final String TAG = "FCM Service";

    @Override
    public void onNewToken(@NonNull String token) {

//        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
//        String refreshToken= FirebaseInstanceId.getInstance().getToken();
//        if(firebaseUser!=null){
//            updateToken(refreshToken);
//        }
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                    }
                });

        Token token1 = new Token(token);
        FirebaseDatabase.getInstance().getReference("Tokens")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token1);
    }

//    private void updateToken(String refreshToken) {
//
//        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
//        Token token1= new Token(refreshToken);
//        FirebaseDatabase.getInstance().getReference("Tokens")
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token1);
//    }
}
