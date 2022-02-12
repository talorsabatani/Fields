// Generated by view binder compiler. Do not edit!
package com.talor.fields.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.talor.fields.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityChatBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageButton chatBTNBack;

  @NonNull
  public final ImageButton chatBTNSend;

  @NonNull
  public final CardView chatCVCardView;

  @NonNull
  public final EditText chatEDTMassage;

  @NonNull
  public final TextView chatLBLFieldName;

  @NonNull
  public final RelativeLayout chatLLMenu;

  @NonNull
  public final RecyclerView chatRCVRecyclerview;

  @NonNull
  public final ImageView mainIMGBackground;

  private ActivityChatBinding(@NonNull RelativeLayout rootView, @NonNull ImageButton chatBTNBack,
      @NonNull ImageButton chatBTNSend, @NonNull CardView chatCVCardView,
      @NonNull EditText chatEDTMassage, @NonNull TextView chatLBLFieldName,
      @NonNull RelativeLayout chatLLMenu, @NonNull RecyclerView chatRCVRecyclerview,
      @NonNull ImageView mainIMGBackground) {
    this.rootView = rootView;
    this.chatBTNBack = chatBTNBack;
    this.chatBTNSend = chatBTNSend;
    this.chatCVCardView = chatCVCardView;
    this.chatEDTMassage = chatEDTMassage;
    this.chatLBLFieldName = chatLBLFieldName;
    this.chatLLMenu = chatLLMenu;
    this.chatRCVRecyclerview = chatRCVRecyclerview;
    this.mainIMGBackground = mainIMGBackground;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityChatBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityChatBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_chat, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityChatBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.chat_BTN_back;
      ImageButton chatBTNBack = ViewBindings.findChildViewById(rootView, id);
      if (chatBTNBack == null) {
        break missingId;
      }

      id = R.id.chat_BTN_send;
      ImageButton chatBTNSend = ViewBindings.findChildViewById(rootView, id);
      if (chatBTNSend == null) {
        break missingId;
      }

      id = R.id.chat_CV_CardView;
      CardView chatCVCardView = ViewBindings.findChildViewById(rootView, id);
      if (chatCVCardView == null) {
        break missingId;
      }

      id = R.id.chat_EDT_massage;
      EditText chatEDTMassage = ViewBindings.findChildViewById(rootView, id);
      if (chatEDTMassage == null) {
        break missingId;
      }

      id = R.id.chat_LBL_field_name;
      TextView chatLBLFieldName = ViewBindings.findChildViewById(rootView, id);
      if (chatLBLFieldName == null) {
        break missingId;
      }

      id = R.id.chat_LL_menu;
      RelativeLayout chatLLMenu = ViewBindings.findChildViewById(rootView, id);
      if (chatLLMenu == null) {
        break missingId;
      }

      id = R.id.chat_RCV_recyclerview;
      RecyclerView chatRCVRecyclerview = ViewBindings.findChildViewById(rootView, id);
      if (chatRCVRecyclerview == null) {
        break missingId;
      }

      id = R.id.main_IMG_background;
      ImageView mainIMGBackground = ViewBindings.findChildViewById(rootView, id);
      if (mainIMGBackground == null) {
        break missingId;
      }

      return new ActivityChatBinding((RelativeLayout) rootView, chatBTNBack, chatBTNSend,
          chatCVCardView, chatEDTMassage, chatLBLFieldName, chatLLMenu, chatRCVRecyclerview,
          mainIMGBackground);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}