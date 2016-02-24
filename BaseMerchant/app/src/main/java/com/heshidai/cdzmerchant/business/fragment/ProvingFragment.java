/**
 * ***************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * ***************************************************************************
 */
package com.heshidai.cdzmerchant.business.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.heshidai.cdzmerchant.R;
import com.heshidai.cdzmerchant.base.BaseFragment;
import com.heshidai.cdzmerchant.business.adapter.NumberAdapter;
import com.heshidai.cdzmerchant.business.dialog.ValidateDialog;
import com.heshidai.cdzmerchant.business.main.LoginActivity;
import com.heshidai.cdzmerchant.business.manager.CouponManager;
import com.heshidai.cdzmerchant.business.manager.UserManager;
import com.heshidai.cdzmerchant.common.Constant;
import com.heshidai.cdzmerchant.common.SPManager;
import com.heshidai.cdzmerchant.entity.AOrderRecordEntity;
import com.heshidai.cdzmerchant.entity.User;
import com.heshidai.cdzmerchant.event.CouponEvent;
import com.heshidai.cdzmerchant.jpush.JpushUtil;
import com.heshidai.cdzmerchant.utils.CheckNet;
import com.heshidai.cdzmerchant.utils.DesUtil;
import com.heshidai.cdzmerchant.utils.DisplayUtil;
import com.heshidai.cdzmerchant.utils.FragmentDialog;
import com.heshidai.cdzmerchant.utils.PreferencesUtils;
import com.heshidai.cdzmerchant.utils.PromptManager;
import com.heshidai.cdzmerchant.utils.StringUtil;
import com.heshidai.cdzmerchant.utils.log.HLog;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * 验证券号
 *
 * @author lixiangxiang
 *         on 2015-10-08
 */
public class ProvingFragment extends BaseFragment {

    private int screenWidth;//屏幕宽度
    private List<String> lists;//键盘数字集合
    private StringBuilder stringBuilder;//验证框输入内容
    private String dialogYuan;//验证金额
    private String statusDesc;//验证券状态描述
    private String provingNumber;//最后获取的的验证码
    private String sign;//校验参数
    private AOrderRecordEntity recordEntity;//最新记录对象
    private String newHistory = "";//最新一条验证记录
    private User user;//用户对象
    private boolean isOnLongClickFirst = false;//控制删除的标记

    private View proveFragment;
    private ImageButton imageDelete;//删除按钮
    private GridView gdNumber;//数字键盘
    private EditText editNumber;//输入框
    private TextView tvHistory;//最新记录
    private TextView shopName;//toolbar 的店铺名

    private NumberAdapter numberAdapter;//键盘适配器


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (isOnLongClickFirst) {
                stringBuilder = new StringBuilder(editNumber.getText().toString().trim());
                //获取光标位置
                int selectionStart = editNumber.getSelectionStart();
                if (stringBuilder.length() > 0 && selectionStart > 0) {
                    stringBuilder.delete(selectionStart - 1, selectionStart);
                    editNumber.setText(stringBuilder);
                    editNumber.setSelection(selectionStart - 1);
                    handler.sendEmptyMessageDelayed(0, 200);
                }
            }
            return false;
        }
    });


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        proveFragment = inflater.inflate(R.layout.fragment_proving, null);
        user = (User) PreferencesUtils.readObject(getActivity(), PreferencesUtils.PREFERENCE_NAME_DATA, SPManager.USER);
        mContext = getActivity();
        screenWidth = DisplayUtil.getWidth(getActivity());
        initLayout();
        initData();
        return proveFragment;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        lists = new ArrayList<>();
        stringBuilder = new StringBuilder();
        if (user != null) {
            shopName.setText(DesUtil.decrypt(user.getMerName()));
            AOrderRecordEntity aOrder = (AOrderRecordEntity) PreferencesUtils.readObject(getActivity(), PreferencesUtils.PREFERENCE_NAME_DATA, StringUtil.getFieldForMerId(user.getMerId(), SPManager.AORDERRECORDENTITY));
            getOneNewHistory(aOrder);
        }
        for (int i = 1; i <= 9; i++) {
            lists.add(i + "");
        }
        lists.add("0");
        lists.add(getActivity().getString(R.string.proving_validate));
        numberAdapter = new NumberAdapter(getActivity(), lists, gdNumber);
        gdNumber.setAdapter(numberAdapter);
        gdNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                numberAdapter.notifyDataSetChanged();
                if (1 == user.getIsStore()) {
                    if (stringBuilder.length() < 12) {//输入框长度
                        if (position <= 8 && position >= 0) {
                            inputNumberToEditText(position + 1 + "", editNumber);
                            numberAdapter.notifyDataSetChanged();
                        }
                        if (position == 9) {
                            inputNumberToEditText(0 + "", editNumber);
                        }
                    }
                    if (position == 10) {//验证 按钮
                        PromptManager.showProgressDialog(getActivity());
                        //验证查询
                        provingNumber = StringUtil.divSpace(editNumber.getText().toString().trim()).toString();
                        if (!TextUtils.isEmpty(provingNumber) && provingNumber.length() == 10) {
                            CouponManager.getCheckOrder(getActivity(), provingNumber);
                        }
                        if (provingNumber.length() != 10) {
                            dealProvingNumber(provingNumber);
                        }
                    }
                } else {
                    FragmentDialog.mAlert(getActivity(), "", getActivity().getString(R.string.main_store), new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            FragmentDialog.close();
                        }
                    });
                }
            }
        });
    }

    /**
     * 对字符长度进行预判断
     *
     * @param provingNumber
     */
    private void dealProvingNumber(String provingNumber) {
        PromptManager.hideProgressDialog();
        if (TextUtils.isEmpty(provingNumber)) {
            FragmentDialog.mAlert(getActivity(), "", getActivity().getString(R.string.data_empty), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentDialog.close();
                }
            });
        } else {
            FragmentDialog.mAlert(getActivity(), "", getActivity().getString(R.string.proving_number), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentDialog.close();

                }
            });
        }
    }

    /**
     * 设置最新记录
     *
     * @param aOrder
     */
    private void getOneNewHistory(AOrderRecordEntity aOrder) {
        if (null != aOrder) {
            newHistory = String.format("券号%s消费<font color='#ef9200'size='26px'>%s元</font>验证成功", aOrder.checkCode, StringUtil.fenToYuan(aOrder.totalAmount));
            tvHistory.setText(Html.fromHtml(newHistory));
        }
    }

    /**
     * 给输入框设置每四个数字插入一个空格
     *
     * @param number
     * @param et
     */
    private void inputNumberToEditText(String number, EditText et) {
        //隔四个数字加一个空格
        StringUtil.addSpace(5, stringBuilder);
        stringBuilder.append(number);
        et.setText(stringBuilder);
        et.setSelection(stringBuilder.length());
    }


    /**
     * 初始化布局
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initLayout() {
        Toolbar toolbar = (Toolbar) proveFragment.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        MenuItem item = toolbar.getMenu().findItem(R.id.action_inbox);
        item.setActionView(R.layout.proving_menu);
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        tvHistory = (TextView) proveFragment.findViewById(R.id.tv_history);
        shopName = (TextView) proveFragment.findViewById(R.id.tv_title);
        gdNumber = (GridView) proveFragment.findViewById(R.id.gd_input_code);
        imageDelete = (ImageButton) proveFragment.findViewById(R.id.image_delete);
        editNumber = (EditText) proveFragment.findViewById(R.id.et_validate);
        editNumber.setFocusable(true);
        editNumber.setEnabled(false);
        imageDelete.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isOnLongClickFirst = true;
                        //连续删除
                        handler.sendEmptyMessage(0);
                        break;
                    case MotionEvent.ACTION_UP:
                        isOnLongClickFirst = false;
                        handler.removeMessages(0);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        isOnLongClickFirst = true;
                        break;
                }
                return true;
            }
        });
        //关闭点击调出系统键盘
        editNumber.setShowSoftInputOnFocus(false);
    }

    /**
     * 登出操作
     */
    private void logout() {
        FragmentDialog.mConfirm(getActivity(), getActivity().getString(R.string.proving_logout), getActivity().getString(R.string.proving_logout_sure), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentDialog.close();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.saveObject(getActivity(), PreferencesUtils.PREFERENCE_NAME_DATA, SPManager.USER, null);
                startActivity(new Intent(getActivity(), LoginActivity.class));
                UserManager.logoutUser(getActivity());
                JpushUtil.cancelAlias();//取消标签
                FragmentDialog.close();
                getActivity().finish();
            }
        });
    }

    public void onEventMainThread(CouponEvent event) {
        PromptManager.hideProgressDialog();
        switch (event.method) {
            case CouponManager.VALIDATE_TAG:
                if (event.code == 0) {
                    recordEntity = (AOrderRecordEntity) event.obj;
                    if (!recordEntity.statusDesc.contains(getActivity().getString(R.string.proving_consume))) {
                        FragmentDialog.mAlert(getActivity(), "", getActivity().getString(R.string.quan) + recordEntity.statusDesc, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FragmentDialog.close();
                            }
                        });
                    } else {
                        //最新消费
                        dialogYuan = String.format("%s%s", StringUtil.fenToYuan(recordEntity.couponAmount + ""), getActivity().getResources().getString(R.string.dialog_yuan));
                        //消费状态
                        statusDesc = recordEntity.statusDesc;
                        showDialog(recordEntity);
                    }
                }else if (event.code == 1){
                    HLog.d(Constant.TAG, event.msg);
                    FragmentDialog.mAlert(getActivity(), "", event.msg, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentDialog.close();
                        }
                    });
                }else{
                    FragmentDialog.mAlert(getActivity(), "", getActivity().getString(R.string.network_fail), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentDialog.close();
                        }
                    });
                }
                break;
            case CouponManager.CONSUME_TAG:
                if (event.code == 0) {
                    PreferencesUtils.saveObject(getActivity(), PreferencesUtils.PREFERENCE_NAME_DATA, StringUtil.getFieldForMerId(user.getMerId(), SPManager.AORDERRECORDENTITY), recordEntity);
                    //处理
                    FragmentDialog.mAlert(getActivity(), "", event.msg, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentDialog.close();
                        }
                    });
                    stringBuilder.delete(0, stringBuilder.length());
                    editNumber.setText(stringBuilder);
                    getOneNewHistory(recordEntity);//设置最新记录
                    //发送eventBus 给 历史记录页
                    CouponEvent couponEvent = new CouponEvent(CouponManager.UPDATA_TAG,0);
                    EventBus.getDefault().post(couponEvent);
                }else if (event.code == 1){
                    HLog.d(Constant.TAG, event.msg);
                    FragmentDialog.mAlert(getActivity(), "", event.msg, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentDialog.close();
                        }
                    });
                }else{
                    FragmentDialog.mAlert(getActivity(), "", getActivity().getString(R.string.network_fail), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FragmentDialog.close();
                        }
                    });
                }
                break;
        }
    }

    /**
     * 退出登录
     * @param event
     */
/*    public void onEventMainThread(UserEvent event) {
        PromptManager.hideProgressDialog();
        if (event.method ==UserManager.LOGOUT_TAG){
            if (0 == event.code){
                PromptManager.showToast(getActivity(), event.msg);
            }
        }

    }*/

    /**
     * 验证框数据
     *
     * @param recordEntity
     */
    private void showDialog(AOrderRecordEntity recordEntity) {
        FragmentDialog.showValidate(getActivity(), true);
        FragmentDialog.dialog.setCustomDialogListener(recordEntity, new ValidateDialog.OnCustomDialogListener() {
            @Override
            public void onCancelListener() {
                FragmentDialog.dialog.dismiss();
            }

            @Override
            public void onPositiveListener() {
                if (CheckNet.checkNetwork(getActivity())) {
                    //消费操作
                    CouponManager.getConsumeOrder(getActivity(), provingNumber);
                    FragmentDialog.dialog.dismiss();
                    PromptManager.showProgressDialog(getActivity());
                } else {
                    PromptManager.showToast(getActivity(), getActivity().getString(R.string.network_fail));
                }
            }
        });
    }
}
