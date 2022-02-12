// Generated by view binder compiler. Do not edit!
package com.talor.fields.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.talor.fields.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityProfileEditBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final RelativeLayout adultInfo;

  @NonNull
  public final Button btnMale;

  @NonNull
  public final TextInputLayout editAbout;

  @NonNull
  public final TextInputLayout editAge;

  @NonNull
  public final Button editFemale;

  @NonNull
  public final TextInputLayout editName;

  @NonNull
  public final TextInputLayout editNickName;

  @NonNull
  public final TextInputLayout editPosition;

  @NonNull
  public final TextView info;

  @NonNull
  public final MaterialButton login;

  @NonNull
  public final ImageView logo;

  @NonNull
  public final MaterialButtonToggleGroup pickGender;

  @NonNull
  public final ImageView pickImg;

  @NonNull
  public final TextView title;

  @NonNull
  public final RelativeLayout topInfo;

  private ActivityProfileEditBinding(@NonNull RelativeLayout rootView,
      @NonNull RelativeLayout adultInfo, @NonNull Button btnMale,
      @NonNull TextInputLayout editAbout, @NonNull TextInputLayout editAge,
      @NonNull Button editFemale, @NonNull TextInputLayout editName,
      @NonNull TextInputLayout editNickName, @NonNull TextInputLayout editPosition,
      @NonNull TextView info, @NonNull MaterialButton login, @NonNull ImageView logo,
      @NonNull MaterialButtonToggleGroup pickGender, @NonNull ImageView pickImg,
      @NonNull TextView title, @NonNull RelativeLayout topInfo) {
    this.rootView = rootView;
    this.adultInfo = adultInfo;
    this.btnMale = btnMale;
    this.editAbout = editAbout;
    this.editAge = editAge;
    this.editFemale = editFemale;
    this.editName = editName;
    this.editNickName = editNickName;
    this.editPosition = editPosition;
    this.info = info;
    this.login = login;
    this.logo = logo;
    this.pickGender = pickGender;
    this.pickImg = pickImg;
    this.title = title;
    this.topInfo = topInfo;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityProfileEditBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityProfileEditBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_profile_edit, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityProfileEditBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.adult_info;
      RelativeLayout adultInfo = ViewBindings.findChildViewById(rootView, id);
      if (adultInfo == null) {
        break missingId;
      }

      id = R.id.btn_male;
      Button btnMale = ViewBindings.findChildViewById(rootView, id);
      if (btnMale == null) {
        break missingId;
      }

      id = R.id.edit_about;
      TextInputLayout editAbout = ViewBindings.findChildViewById(rootView, id);
      if (editAbout == null) {
        break missingId;
      }

      id = R.id.edit_age;
      TextInputLayout editAge = ViewBindings.findChildViewById(rootView, id);
      if (editAge == null) {
        break missingId;
      }

      id = R.id.edit_female;
      Button editFemale = ViewBindings.findChildViewById(rootView, id);
      if (editFemale == null) {
        break missingId;
      }

      id = R.id.edit_name;
      TextInputLayout editName = ViewBindings.findChildViewById(rootView, id);
      if (editName == null) {
        break missingId;
      }

      id = R.id.edit_nick_name;
      TextInputLayout editNickName = ViewBindings.findChildViewById(rootView, id);
      if (editNickName == null) {
        break missingId;
      }

      id = R.id.edit_position;
      TextInputLayout editPosition = ViewBindings.findChildViewById(rootView, id);
      if (editPosition == null) {
        break missingId;
      }

      id = R.id.info;
      TextView info = ViewBindings.findChildViewById(rootView, id);
      if (info == null) {
        break missingId;
      }

      id = R.id.login;
      MaterialButton login = ViewBindings.findChildViewById(rootView, id);
      if (login == null) {
        break missingId;
      }

      id = R.id.logo;
      ImageView logo = ViewBindings.findChildViewById(rootView, id);
      if (logo == null) {
        break missingId;
      }

      id = R.id.pick_gender;
      MaterialButtonToggleGroup pickGender = ViewBindings.findChildViewById(rootView, id);
      if (pickGender == null) {
        break missingId;
      }

      id = R.id.pick_img;
      ImageView pickImg = ViewBindings.findChildViewById(rootView, id);
      if (pickImg == null) {
        break missingId;
      }

      id = R.id.title;
      TextView title = ViewBindings.findChildViewById(rootView, id);
      if (title == null) {
        break missingId;
      }

      id = R.id.top_info;
      RelativeLayout topInfo = ViewBindings.findChildViewById(rootView, id);
      if (topInfo == null) {
        break missingId;
      }

      return new ActivityProfileEditBinding((RelativeLayout) rootView, adultInfo, btnMale,
          editAbout, editAge, editFemale, editName, editNickName, editPosition, info, login, logo,
          pickGender, pickImg, title, topInfo);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
