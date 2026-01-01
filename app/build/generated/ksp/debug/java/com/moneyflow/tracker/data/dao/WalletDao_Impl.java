package com.moneyflow.tracker.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.moneyflow.tracker.data.model.Wallet;
import com.moneyflow.tracker.data.model.WalletType;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class WalletDao_Impl implements WalletDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Wallet> __insertionAdapterOfWallet;

  private final EntityDeletionOrUpdateAdapter<Wallet> __deletionAdapterOfWallet;

  private final EntityDeletionOrUpdateAdapter<Wallet> __updateAdapterOfWallet;

  private final SharedSQLiteStatement __preparedStmtOfUpdateBalance;

  private final SharedSQLiteStatement __preparedStmtOfArchiveWallet;

  public WalletDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWallet = new EntityInsertionAdapter<Wallet>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `wallets` (`id`,`name`,`icon`,`limit`,`balance`,`colorHex`,`isArchived`,`createdAt`,`walletType`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Wallet value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getIcon() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getIcon());
        }
        stmt.bindDouble(4, value.getLimit());
        stmt.bindDouble(5, value.getBalance());
        if (value.getColorHex() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getColorHex());
        }
        final int _tmp = value.isArchived() ? 1 : 0;
        stmt.bindLong(7, _tmp);
        stmt.bindLong(8, value.getCreatedAt());
        if (value.getWalletType() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, __WalletType_enumToString(value.getWalletType()));
        }
      }
    };
    this.__deletionAdapterOfWallet = new EntityDeletionOrUpdateAdapter<Wallet>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `wallets` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Wallet value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfWallet = new EntityDeletionOrUpdateAdapter<Wallet>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `wallets` SET `id` = ?,`name` = ?,`icon` = ?,`limit` = ?,`balance` = ?,`colorHex` = ?,`isArchived` = ?,`createdAt` = ?,`walletType` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Wallet value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getIcon() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getIcon());
        }
        stmt.bindDouble(4, value.getLimit());
        stmt.bindDouble(5, value.getBalance());
        if (value.getColorHex() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getColorHex());
        }
        final int _tmp = value.isArchived() ? 1 : 0;
        stmt.bindLong(7, _tmp);
        stmt.bindLong(8, value.getCreatedAt());
        if (value.getWalletType() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, __WalletType_enumToString(value.getWalletType()));
        }
        stmt.bindLong(10, value.getId());
      }
    };
    this.__preparedStmtOfUpdateBalance = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE wallets SET balance = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfArchiveWallet = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE wallets SET isArchived = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertWallet(final Wallet wallet, final Continuation<? super Long> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfWallet.insertAndReturnId(wallet);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteWallet(final Wallet wallet, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfWallet.handle(wallet);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateWallet(final Wallet wallet, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWallet.handle(wallet);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateBalance(final long walletId, final double balance,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateBalance.acquire();
        int _argIndex = 1;
        _stmt.bindDouble(_argIndex, balance);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, walletId);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfUpdateBalance.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public Object archiveWallet(final long walletId, final boolean isArchived,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfArchiveWallet.acquire();
        int _argIndex = 1;
        final int _tmp = isArchived ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, walletId);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfArchiveWallet.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public LiveData<List<Wallet>> getAllActiveWallets() {
    final String _sql = "SELECT * FROM wallets WHERE isArchived = 0 ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"wallets"}, false, new Callable<List<Wallet>>() {
      @Override
      public List<Wallet> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
          final int _cursorIndexOfLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "limit");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfWalletType = CursorUtil.getColumnIndexOrThrow(_cursor, "walletType");
          final List<Wallet> _result = new ArrayList<Wallet>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Wallet _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpIcon;
            if (_cursor.isNull(_cursorIndexOfIcon)) {
              _tmpIcon = null;
            } else {
              _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            }
            final double _tmpLimit;
            _tmpLimit = _cursor.getDouble(_cursorIndexOfLimit);
            final double _tmpBalance;
            _tmpBalance = _cursor.getDouble(_cursorIndexOfBalance);
            final String _tmpColorHex;
            if (_cursor.isNull(_cursorIndexOfColorHex)) {
              _tmpColorHex = null;
            } else {
              _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            }
            final boolean _tmpIsArchived;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final WalletType _tmpWalletType;
            _tmpWalletType = __WalletType_stringToEnum(_cursor.getString(_cursorIndexOfWalletType));
            _item = new Wallet(_tmpId,_tmpName,_tmpIcon,_tmpLimit,_tmpBalance,_tmpColorHex,_tmpIsArchived,_tmpCreatedAt,_tmpWalletType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getWalletById(final long walletId,
      final Continuation<? super Wallet> continuation) {
    final String _sql = "SELECT * FROM wallets WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, walletId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Wallet>() {
      @Override
      public Wallet call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
          final int _cursorIndexOfLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "limit");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfWalletType = CursorUtil.getColumnIndexOrThrow(_cursor, "walletType");
          final Wallet _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpIcon;
            if (_cursor.isNull(_cursorIndexOfIcon)) {
              _tmpIcon = null;
            } else {
              _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            }
            final double _tmpLimit;
            _tmpLimit = _cursor.getDouble(_cursorIndexOfLimit);
            final double _tmpBalance;
            _tmpBalance = _cursor.getDouble(_cursorIndexOfBalance);
            final String _tmpColorHex;
            if (_cursor.isNull(_cursorIndexOfColorHex)) {
              _tmpColorHex = null;
            } else {
              _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            }
            final boolean _tmpIsArchived;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final WalletType _tmpWalletType;
            _tmpWalletType = __WalletType_stringToEnum(_cursor.getString(_cursorIndexOfWalletType));
            _result = new Wallet(_tmpId,_tmpName,_tmpIcon,_tmpLimit,_tmpBalance,_tmpColorHex,_tmpIsArchived,_tmpCreatedAt,_tmpWalletType);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public LiveData<Wallet> getWalletByIdLive(final long walletId) {
    final String _sql = "SELECT * FROM wallets WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, walletId);
    return __db.getInvalidationTracker().createLiveData(new String[]{"wallets"}, false, new Callable<Wallet>() {
      @Override
      public Wallet call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
          final int _cursorIndexOfLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "limit");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfWalletType = CursorUtil.getColumnIndexOrThrow(_cursor, "walletType");
          final Wallet _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpIcon;
            if (_cursor.isNull(_cursorIndexOfIcon)) {
              _tmpIcon = null;
            } else {
              _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            }
            final double _tmpLimit;
            _tmpLimit = _cursor.getDouble(_cursorIndexOfLimit);
            final double _tmpBalance;
            _tmpBalance = _cursor.getDouble(_cursorIndexOfBalance);
            final String _tmpColorHex;
            if (_cursor.isNull(_cursorIndexOfColorHex)) {
              _tmpColorHex = null;
            } else {
              _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            }
            final boolean _tmpIsArchived;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final WalletType _tmpWalletType;
            _tmpWalletType = __WalletType_stringToEnum(_cursor.getString(_cursorIndexOfWalletType));
            _result = new Wallet(_tmpId,_tmpName,_tmpIcon,_tmpLimit,_tmpBalance,_tmpColorHex,_tmpIsArchived,_tmpCreatedAt,_tmpWalletType);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Wallet>> getWalletsByType(final WalletType type) {
    final String _sql = "SELECT * FROM wallets WHERE walletType = ? AND isArchived = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, __WalletType_enumToString(type));
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"wallets"}, false, new Callable<List<Wallet>>() {
      @Override
      public List<Wallet> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
          final int _cursorIndexOfLimit = CursorUtil.getColumnIndexOrThrow(_cursor, "limit");
          final int _cursorIndexOfBalance = CursorUtil.getColumnIndexOrThrow(_cursor, "balance");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "colorHex");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "isArchived");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfWalletType = CursorUtil.getColumnIndexOrThrow(_cursor, "walletType");
          final List<Wallet> _result = new ArrayList<Wallet>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Wallet _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpIcon;
            if (_cursor.isNull(_cursorIndexOfIcon)) {
              _tmpIcon = null;
            } else {
              _tmpIcon = _cursor.getString(_cursorIndexOfIcon);
            }
            final double _tmpLimit;
            _tmpLimit = _cursor.getDouble(_cursorIndexOfLimit);
            final double _tmpBalance;
            _tmpBalance = _cursor.getDouble(_cursorIndexOfBalance);
            final String _tmpColorHex;
            if (_cursor.isNull(_cursorIndexOfColorHex)) {
              _tmpColorHex = null;
            } else {
              _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            }
            final boolean _tmpIsArchived;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final WalletType _tmpWalletType;
            _tmpWalletType = __WalletType_stringToEnum(_cursor.getString(_cursorIndexOfWalletType));
            _item = new Wallet(_tmpId,_tmpName,_tmpIcon,_tmpLimit,_tmpBalance,_tmpColorHex,_tmpIsArchived,_tmpCreatedAt,_tmpWalletType);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getTotalByType(final WalletType type,
      final Continuation<? super Double> continuation) {
    final String _sql = "SELECT SUM(balance) FROM wallets WHERE walletType = ? AND isArchived = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, __WalletType_enumToString(type));
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if(_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getActiveWalletCount(final Continuation<? super Integer> continuation) {
    final String _sql = "SELECT COUNT(*) FROM wallets WHERE isArchived = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if(_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private String __WalletType_enumToString(final WalletType _value) {
    if (_value == null) {
      return null;
    } switch (_value) {
      case EXPENSE: return "EXPENSE";
      case SAVINGS: return "SAVINGS";
      case INCOME: return "INCOME";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private WalletType __WalletType_stringToEnum(final String _value) {
    if (_value == null) {
      return null;
    } switch (_value) {
      case "EXPENSE": return WalletType.EXPENSE;
      case "SAVINGS": return WalletType.SAVINGS;
      case "INCOME": return WalletType.INCOME;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
