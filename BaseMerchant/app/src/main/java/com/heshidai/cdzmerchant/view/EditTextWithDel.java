/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.view;


import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.heshidai.cdzmerchant.R;


/**
 * 自定义EditText带删除功能（包括一键删除，设置为true,连续删除，设置为false）
 */
@SuppressWarnings("deprecation")
public class EditTextWithDel extends EditText {

    private Context mContext;
    private Drawable imgClear;
    private DeleteValidateCode deleteValidateCode;
    private boolean isAlwaysDelete=false;
    private boolean isOnLongClick=false;
    public EditTextWithDel(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        init();
    }

    public void setValidateCode(boolean isAlwaysDelete,DeleteValidateCode deleteValidateCode){
        this.deleteValidateCode = deleteValidateCode;
        this.isAlwaysDelete = isAlwaysDelete;
    }
    /**
     * 初始化
     */
    private void init() {
        imgClear = mContext.getResources().getDrawable(R.mipmap.clear);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });
        setDrawable();
    }

    /**
     * 设置删除图片
     */
    private void setDrawable() {
        Drawable[] compoundDrawables = getCompoundDrawables();
        if(length() < 1) {
            setCompoundDrawablesWithIntrinsicBounds(compoundDrawables[0], compoundDrawables[1], null, compoundDrawables[3]);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(compoundDrawables[0], compoundDrawables[1], imgClear, compoundDrawables[3]);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventX = (int) event.getRawX();
        int eventY = (int) event.getRawY();
        if(isAlwaysDelete) {
            if (imgClear != null && event.getAction() == MotionEvent.ACTION_UP) {
                deleteRange(eventX,eventY);
            }
        }else{
            DeleteWordThread deleteWordThread = new DeleteWordThread(eventX,eventY);
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    isOnLongClick=true;
                    //连续删除
                    deleteWordThread.start();
                    break;
                case MotionEvent.ACTION_UP:
                   if(deleteWordThread !=null) {
                       isOnLongClick = false;
                   }
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(deleteWordThread !=null) {
                        isOnLongClick = true;
                    }
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    private void deleteRange(int eventX, int eventY) {
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        rect.left = rect.right - getPaddingRight() - imgClear.getIntrinsicWidth() / 2;
        if (rect.contains(eventX, eventY)) {
            deleteValidateCode.setValidateCode();
        }
    }

    //用一个线程来执行删除动作
    class DeleteWordThread extends  Thread{
        private int eventX,eventY;
        public DeleteWordThread(int eventX,int eventY){
            this.eventX=eventX;
            this.eventY=eventY;
        }
        @Override
        public void run() {
            while (isOnLongClick){
                try {
                    Thread.sleep(100);
                    deleteRange(eventX,eventY);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public  interface DeleteValidateCode{
        public void setValidateCode();
    }
}
