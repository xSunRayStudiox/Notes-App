package com.sun_ray.srnotes.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.sun_ray.srnotes.model.Url;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UrlDao_Impl implements UrlDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Url> __insertionAdapterOfUrl;

  private final EntityDeletionOrUpdateAdapter<Url> __deletionAdapterOfUrl;

  public UrlDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUrl = new EntityInsertionAdapter<Url>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `Urls` (`id`,`Title`,`Link`,`Date`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Url entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getLink() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getLink());
        }
        if (entity.getDate() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDate());
        }
      }
    };
    this.__deletionAdapterOfUrl = new EntityDeletionOrUpdateAdapter<Url>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `Urls` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Url entity) {
        statement.bindLong(1, entity.getId());
      }
    };
  }

  @Override
  public void addUrl(final Url url) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfUrl.insert(url);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteUrl(final Url url) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfUrl.handle(url);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Url> getUrls() {
    final String _sql = "Select * From Urls Order By id Desc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "Title");
      final int _cursorIndexOfLink = CursorUtil.getColumnIndexOrThrow(_cursor, "Link");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "Date");
      final List<Url> _result = new ArrayList<Url>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Url _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpLink;
        if (_cursor.isNull(_cursorIndexOfLink)) {
          _tmpLink = null;
        } else {
          _tmpLink = _cursor.getString(_cursorIndexOfLink);
        }
        final String _tmpDate;
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _tmpDate = null;
        } else {
          _tmpDate = _cursor.getString(_cursorIndexOfDate);
        }
        _item = new Url(_tmpId,_tmpTitle,_tmpLink,_tmpDate);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
