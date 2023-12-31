// Generated by view binder compiler. Do not edit!
package com.example.fragmentos_1_2023.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.fragmentos_1_2023.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentFragmentoABinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button btnFA;

  @NonNull
  public final ImageView imgF1;

  @NonNull
  public final TextView txtF1;

  private FragmentFragmentoABinding(@NonNull LinearLayout rootView, @NonNull Button btnFA,
      @NonNull ImageView imgF1, @NonNull TextView txtF1) {
    this.rootView = rootView;
    this.btnFA = btnFA;
    this.imgF1 = imgF1;
    this.txtF1 = txtF1;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentFragmentoABinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentFragmentoABinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_fragmento_a, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentFragmentoABinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnFA;
      Button btnFA = ViewBindings.findChildViewById(rootView, id);
      if (btnFA == null) {
        break missingId;
      }

      id = R.id.imgF1;
      ImageView imgF1 = ViewBindings.findChildViewById(rootView, id);
      if (imgF1 == null) {
        break missingId;
      }

      id = R.id.txtF1;
      TextView txtF1 = ViewBindings.findChildViewById(rootView, id);
      if (txtF1 == null) {
        break missingId;
      }

      return new FragmentFragmentoABinding((LinearLayout) rootView, btnFA, imgF1, txtF1);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
