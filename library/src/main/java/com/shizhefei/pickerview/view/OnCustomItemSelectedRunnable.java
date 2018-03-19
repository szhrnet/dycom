// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.shizhefei.pickerview.view;

// Referenced classes of package com.qingchifan.view:
//            LoopView, OnItemSelectedListener

public final class OnCustomItemSelectedRunnable implements Runnable {
  final CustomListView loopView;

  public OnCustomItemSelectedRunnable(CustomListView loopview) {
    loopView = loopview;
  }

  @Override public final void run() {
    loopView.onItemSelectedListener.onItemSelected(loopView.getCurrentItem());
  }
}
