package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.R.attr.top
import android.graphics.Rect
import android.opengl.ETC1.getHeight




fun Activity.hideKeyboard() {
   val imm  = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
   imm.hideSoftInputFromWindow(getCurrentFocus()?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
}

fun Activity.isKeyboardOpen() : Boolean {
   val activityRootView = window.decorView.rootView
   val r = Rect()
   //r will be populated with the coordinates of your view that area still visible.
   activityRootView.getWindowVisibleDisplayFrame(r)
   val heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top)
   return if (heightDiff < activityRootView.getRootView().getHeight() / 4)  // if more than 100 pixels, its probably a keyboard...
      false
   else
      true
}

fun Activity.isKeyboardClosed() : Boolean {
   val activityRootView = window.decorView.rootView
   val r = Rect()
   //r will be populated with the coordinates of your view that area still visible.
   activityRootView.getWindowVisibleDisplayFrame(r)
   val heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top)
   return if (heightDiff < activityRootView.getRootView().getHeight() / 4)  // if more than 100 pixels, its probably a keyboard...
      true
   else
      false
}