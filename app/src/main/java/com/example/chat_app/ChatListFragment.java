package com.example.chat_app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.renderscript.Sampler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.chat_app.models.ChatDialog;
import com.example.chat_app.models.Message;
import com.example.chat_app.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 *
 * create an instance of this fragment.
 */
public class ChatListFragment extends Fragment {

    ArrayList<ChatDialog> chats = new ArrayList<>();

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    DialogsList dialogsList;

    Button startChatButton;

    public ChatListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);

        dialogsList = (DialogsList) view.findViewById(R.id.chats_list);

        startChatButton = (Button) view.findViewById(R.id.start_chat_button);

        DialogsListAdapter dialogsListAdapter = new DialogsListAdapter<ChatDialog>(new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {

                if(!url.equals(""))
                    Picasso.get().load(url).into(imageView);
            }
        });

        dialogsList.setAdapter(dialogsListAdapter);

        startChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showChatDialogAlert();
            }
        });

        listenToIncomingChats();

        getChatsList();

        return view;
    }

    private void listenToIncomingChats()
    {
        final CollectionReference collectionReference = firestore.collection("chats");
        collectionReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {value : QuerySnapshot@18945
                if (error == null) {
                    Log.d( "Chat List", value.toString());
                }
                else {
                    error.printStackTrace();
                }

            }
        });
    }

    private void showChatDialogAlert(){
        final EditText messageEditText = new EditText(getContext());
        AlertDialog dialog = new AlertDialog.Builder(getContext())
            .setTitle("Start a chat")
                .setMessage("Enter your first message")
                .setView(messageEditText)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String  message = String.valueOf(messageEditText.getText());
                        startChat(message);
                    }
                })
                .setNegativeButton("Cancel",null)
                .create();
        dialog.show();
    }

    private void getChatsList() {

        firestore.collection("chats")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Chat List", document.getId() + " => " + document.getData());
                                ChatDialog chatDialog = document.toObject(ChatDialog.class);

                                DialogsListAdapter adapter= (DialogsListAdapter)dialogsList.getAdapter();
                                adapter.addItem(chatDialog);
                            }
                        } else {
                            Log.d("Chat List", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void startChat(String firstMessage) {

        User user= User.UserFromFirebaseUser(FirebaseAuth.getInstance().getCurrentUser());

        Message newMessage = new Message(firstMessage, user);

        ChatDialog newchat = new ChatDialog(newMessage);

        // Create a new user with a first and last name
        //Map<String, Object> chat = new HashMap<>();
        //chat.put("id", "123");
        //chat.put("dialogPhoto", "");
        //chat.put("dialogName", firstMessage);
        //chat.put("unreadCount", 0);

        firestore.collection("chats")
                .add(newchat.hashMap())

                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Chat List", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Chat List", "Error adding document", e);
                    }
                });
    }
}