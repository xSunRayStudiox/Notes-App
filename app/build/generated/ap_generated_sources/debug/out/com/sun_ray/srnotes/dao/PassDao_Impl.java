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
import com.sun_ray.srnotes.model.Password;
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
public final class PassDao_Impl implements PassDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Password> __insertionAdapterOfPassword;

  private final EntityDeletionOrUpdateAdapter<Password> __deletionAdapterOfPassword;

  public PassDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPassword = new EntityInsertionAdapter<Password>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `Passwords` (`id`,`Headings`,`Date`,`UserId`,`Pass`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Password entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getHeading() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getHeading());
        }
        if (entity.getDate() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDate());
        }
        if (entity.getUserId() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getUserId());
        }
        if (entity.getPass() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPass());
        }
      }
    };
    this.__deletionAdapterOfPassword = new EntityDeletionOrUpdateAdapter<Password>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `Passwords` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement, final Password entity) {
        statement.bindLong(1, entity.getId());
      }
    };
  }

  @Override
  public void addPass(final Password password) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPassword.insert(password);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deletePass(final Password password) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfPassword.handle(password);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Password> getPass() {
    final String _sql = "Select * From Passwords Order By id Desc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfHeading = CursorUtil.getColumnIndexOrThrow(_cursor, "Headings");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "Date");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "UserId");
      final int _cursorIndexOfPass = CursorUtil.getColumnIndexOrThrow(_cursor, "Pass");
      final List<Password> _result = new ArrayList<Password>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final Password _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpHeading;
        if (_cursor.isNull(_cursorIndexOfHeading)) {
          _tmpHeading = null;
        } else {
          _tmpHeading = _cursor.getString(_cursorIndexOfHeading);
        }
        final String _tmpDate;
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _tmpDate = null;
        } else {
          _tmpDate = _cursor.getString(_cursorIndexOfDate);
        }
        final String _tmpUserId;
        if (_cursor.isNull(_cursorIndexOfUserId)) {
          _tmpUserId = null;
        } else {
          _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
        }
        final String _tmpPass;
        if (_cursor.isNull(_cursorIndexOfPass)) {
          _tmpPass = null;
        } else {
          _tmpPass = _cursor.getString(_cursorIndexOfPass);
        }
        _item = new Password(_tmpId,_tmpHeading,_tmpDate,_tmpUserId,_tmpPass);
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
