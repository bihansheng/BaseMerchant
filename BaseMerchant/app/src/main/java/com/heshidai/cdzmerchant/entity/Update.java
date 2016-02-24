/******************************************************************************
 * Copyright (C) 2014 ShenZhen HeShiDai Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为合时代控股有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.heshidai.cdzmerchant.entity;

import java.io.Serializable;

/**更新实体类
 * @author xiao di fa
 *
 */
public class Update implements Serializable {

    private long fileSize;//文件大小
    private String content;//更新内容
    private String downloadLink;//下载链接
    private int versionCode;//版本code
    private String versionName;//版本名称
    private int isneed;//是否必须更新 1必须 0不需要

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getIsneed() {
        return isneed;
    }

    public void setIsneed(int isneed) {
        this.isneed = isneed;
    }
}
