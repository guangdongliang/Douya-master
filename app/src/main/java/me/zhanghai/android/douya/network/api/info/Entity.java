/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.android.douya.network.api.info;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Patterns;

import java.util.List;

import me.zhanghai.android.douya.link.UriSpan;
import me.zhanghai.android.douya.settings.info.Settings;
import me.zhanghai.android.douya.util.LogUtils;

public class Entity implements Parcelable {

    public int end;

    public String href;

    public int start;

    public String title;

    public static CharSequence applyEntities(CharSequence text, List<Entity> entityList,
                                             Context context) {

        if (TextUtils.isEmpty(text) || entityList.isEmpty()) {
            return text;
        }

        SpannableStringBuilder builder = new SpannableStringBuilder();
        int lastTextIndex = 0;
        for (Entity entity : entityList) {
            if (entity.start < 0 || entity.end < entity.start) {
                LogUtils.w("Ignoring malformed entity " + entity);
                continue;
            }
            if (entity.start < lastTextIndex) {
                LogUtils.w("Ignoring backward entity " + entity + ", with lastTextIndex="
                        + lastTextIndex);
                continue;
            }
            builder.append(text.subSequence(lastTextIndex, entity.start));
            CharSequence entityTitle = entity.title;
            if (!Settings.SHOW_TITLE_FOR_LINK_ENTITY.getValue(context)
                    && Patterns.WEB_URL.matcher(entityTitle).matches()) {
                entityTitle = text.subSequence(entity.start, entity.end);
            }
            int entityStart = builder.length();
            builder
                    .append(entityTitle)
                    .setSpan(new UriSpan(entity.href), entityStart,
                            entityStart + entityTitle.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            lastTextIndex = entity.end;
        }
        if (lastTextIndex != text.length()) {
            builder.append(text.subSequence(lastTextIndex, text.length()));
        }
        return builder;
    }


    public static final Parcelable.Creator<Entity> CREATOR = new Parcelable.Creator<Entity>() {
        public Entity createFromParcel(Parcel source) {
            return new Entity(source);
        }
        public Entity[] newArray(int size) {
            return new Entity[size];
        }
    };

    public Entity() {}

    protected Entity(Parcel in) {
        end = in.readInt();
        href = in.readString();
        start = in.readInt();
        title = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(end);
        dest.writeString(href);
        dest.writeInt(start);
        dest.writeString(title);
    }

}
