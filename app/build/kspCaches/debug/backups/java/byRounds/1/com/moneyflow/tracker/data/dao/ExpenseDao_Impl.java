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
import com.moneyflow.tracker.data.model.Expense;
import com.moneyflow.tracker.data.model.FlowType;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
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
public final class ExpenseDao_Impl implements ExpenseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Expense> __insertionAdapterOfExpense;

  private final EntityDeletionOrUpdateAdapter<Expense> __deletionAdapterOfExpense;

  private final EntityDeletionOrUpdateAdapter<Expense> __updateAdapterOfExpense;

  private final SharedSQLiteStatement __preparedStmtOfDeleteExpensesByWallet;

  public ExpenseDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfExpense = new EntityInsertionAdapter<Expense>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `expenses` (`id`,`walletId`,`amount`,`title`,`timestamp`,`flowType`,`memo`,`tags`,`location`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Expense value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getWalletId());
        stmt.bindDouble(3, value.getAmount());
        if (value.getTitle() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTitle());
        }
        stmt.bindLong(5, value.getTimestamp());
        if (value.getFlowType() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, __FlowType_enumToString(value.getFlowType()));
        }
        if (value.getMemo() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getMemo());
        }
        if (value.getTags() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getTags());
        }
        if (value.getLocation() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getLocation());
        }
      }
    };
    this.__deletionAdapterOfExpense = new EntityDeletionOrUpdateAdapter<Expense>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `expenses` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Expense value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfExpense = new EntityDeletionOrUpdateAdapter<Expense>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `expenses` SET `id` = ?,`walletId` = ?,`amount` = ?,`title` = ?,`timestamp` = ?,`flowType` = ?,`memo` = ?,`tags` = ?,`location` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Expense value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getWalletId());
        stmt.bindDouble(3, value.getAmount());
        if (value.getTitle() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getTitle());
        }
        stmt.bindLong(5, value.getTimestamp());
        if (value.getFlowType() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, __FlowType_enumToString(value.getFlowType()));
        }
        if (value.getMemo() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getMemo());
        }
        if (value.getTags() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getTags());
        }
        if (value.getLocation() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getLocation());
        }
        stmt.bindLong(10, value.getId());
      }
    };
    this.__preparedStmtOfDeleteExpensesByWallet = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM expenses WHERE walletId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertExpense(final Expense expense,
      final Continuation<? super Long> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfExpense.insertAndReturnId(expense);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteExpense(final Expense expense,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfExpense.handle(expense);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateExpense(final Expense expense,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfExpense.handle(expense);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteExpensesByWallet(final long walletId,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteExpensesByWallet.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, walletId);
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteExpensesByWallet.release(_stmt);
        }
      }
    }, continuation);
  }

  @Override
  public LiveData<List<Expense>> getRecentExpenses() {
    final String _sql = "SELECT * FROM expenses ORDER BY timestamp DESC LIMIT 50";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"expenses"}, false, new Callable<List<Expense>>() {
      @Override
      public List<Expense> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWalletId = CursorUtil.getColumnIndexOrThrow(_cursor, "walletId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfFlowType = CursorUtil.getColumnIndexOrThrow(_cursor, "flowType");
          final int _cursorIndexOfMemo = CursorUtil.getColumnIndexOrThrow(_cursor, "memo");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final List<Expense> _result = new ArrayList<Expense>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Expense _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWalletId;
            _tmpWalletId = _cursor.getLong(_cursorIndexOfWalletId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final FlowType _tmpFlowType;
            _tmpFlowType = __FlowType_stringToEnum(_cursor.getString(_cursorIndexOfFlowType));
            final String _tmpMemo;
            if (_cursor.isNull(_cursorIndexOfMemo)) {
              _tmpMemo = null;
            } else {
              _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
            }
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            _item = new Expense(_tmpId,_tmpWalletId,_tmpAmount,_tmpTitle,_tmpTimestamp,_tmpFlowType,_tmpMemo,_tmpTags,_tmpLocation);
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
  public LiveData<List<Expense>> getExpensesByWallet(final long walletId) {
    final String _sql = "SELECT * FROM expenses WHERE walletId = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, walletId);
    return __db.getInvalidationTracker().createLiveData(new String[]{"expenses"}, false, new Callable<List<Expense>>() {
      @Override
      public List<Expense> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWalletId = CursorUtil.getColumnIndexOrThrow(_cursor, "walletId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfFlowType = CursorUtil.getColumnIndexOrThrow(_cursor, "flowType");
          final int _cursorIndexOfMemo = CursorUtil.getColumnIndexOrThrow(_cursor, "memo");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final List<Expense> _result = new ArrayList<Expense>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Expense _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWalletId;
            _tmpWalletId = _cursor.getLong(_cursorIndexOfWalletId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final FlowType _tmpFlowType;
            _tmpFlowType = __FlowType_stringToEnum(_cursor.getString(_cursorIndexOfFlowType));
            final String _tmpMemo;
            if (_cursor.isNull(_cursorIndexOfMemo)) {
              _tmpMemo = null;
            } else {
              _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
            }
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            _item = new Expense(_tmpId,_tmpWalletId,_tmpAmount,_tmpTitle,_tmpTimestamp,_tmpFlowType,_tmpMemo,_tmpTags,_tmpLocation);
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
  public Object getExpenseById(final long expenseId,
      final Continuation<? super Expense> continuation) {
    final String _sql = "SELECT * FROM expenses WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, expenseId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Expense>() {
      @Override
      public Expense call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWalletId = CursorUtil.getColumnIndexOrThrow(_cursor, "walletId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfFlowType = CursorUtil.getColumnIndexOrThrow(_cursor, "flowType");
          final int _cursorIndexOfMemo = CursorUtil.getColumnIndexOrThrow(_cursor, "memo");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final Expense _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWalletId;
            _tmpWalletId = _cursor.getLong(_cursorIndexOfWalletId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final FlowType _tmpFlowType;
            _tmpFlowType = __FlowType_stringToEnum(_cursor.getString(_cursorIndexOfFlowType));
            final String _tmpMemo;
            if (_cursor.isNull(_cursorIndexOfMemo)) {
              _tmpMemo = null;
            } else {
              _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
            }
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            _result = new Expense(_tmpId,_tmpWalletId,_tmpAmount,_tmpTitle,_tmpTimestamp,_tmpFlowType,_tmpMemo,_tmpTags,_tmpLocation);
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
  public Object getTotalByWalletAndType(final long walletId, final FlowType flowType,
      final Continuation<? super Double> continuation) {
    final String _sql = "SELECT SUM(amount) FROM expenses WHERE walletId = ? AND flowType = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, walletId);
    _argIndex = 2;
    if (flowType == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, __FlowType_enumToString(flowType));
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
  public LiveData<List<Expense>> getExpensesByDateRange(final long startTime, final long endTime) {
    final String _sql = "SELECT * FROM expenses WHERE timestamp BETWEEN ? AND ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startTime);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endTime);
    return __db.getInvalidationTracker().createLiveData(new String[]{"expenses"}, false, new Callable<List<Expense>>() {
      @Override
      public List<Expense> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWalletId = CursorUtil.getColumnIndexOrThrow(_cursor, "walletId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfFlowType = CursorUtil.getColumnIndexOrThrow(_cursor, "flowType");
          final int _cursorIndexOfMemo = CursorUtil.getColumnIndexOrThrow(_cursor, "memo");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final List<Expense> _result = new ArrayList<Expense>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Expense _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWalletId;
            _tmpWalletId = _cursor.getLong(_cursorIndexOfWalletId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final FlowType _tmpFlowType;
            _tmpFlowType = __FlowType_stringToEnum(_cursor.getString(_cursorIndexOfFlowType));
            final String _tmpMemo;
            if (_cursor.isNull(_cursorIndexOfMemo)) {
              _tmpMemo = null;
            } else {
              _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
            }
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            _item = new Expense(_tmpId,_tmpWalletId,_tmpAmount,_tmpTitle,_tmpTimestamp,_tmpFlowType,_tmpMemo,_tmpTags,_tmpLocation);
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
  public LiveData<List<Expense>> searchExpenses(final String query) {
    final String _sql = "SELECT * FROM expenses WHERE title LIKE '%' || ? || '%' OR memo LIKE '%' || ? || '%' ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    _argIndex = 2;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"expenses"}, false, new Callable<List<Expense>>() {
      @Override
      public List<Expense> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfWalletId = CursorUtil.getColumnIndexOrThrow(_cursor, "walletId");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfFlowType = CursorUtil.getColumnIndexOrThrow(_cursor, "flowType");
          final int _cursorIndexOfMemo = CursorUtil.getColumnIndexOrThrow(_cursor, "memo");
          final int _cursorIndexOfTags = CursorUtil.getColumnIndexOrThrow(_cursor, "tags");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final List<Expense> _result = new ArrayList<Expense>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Expense _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpWalletId;
            _tmpWalletId = _cursor.getLong(_cursorIndexOfWalletId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final FlowType _tmpFlowType;
            _tmpFlowType = __FlowType_stringToEnum(_cursor.getString(_cursorIndexOfFlowType));
            final String _tmpMemo;
            if (_cursor.isNull(_cursorIndexOfMemo)) {
              _tmpMemo = null;
            } else {
              _tmpMemo = _cursor.getString(_cursorIndexOfMemo);
            }
            final String _tmpTags;
            if (_cursor.isNull(_cursorIndexOfTags)) {
              _tmpTags = null;
            } else {
              _tmpTags = _cursor.getString(_cursorIndexOfTags);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            _item = new Expense(_tmpId,_tmpWalletId,_tmpAmount,_tmpTitle,_tmpTimestamp,_tmpFlowType,_tmpMemo,_tmpTags,_tmpLocation);
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
  public Object getTotalFlowInPeriod(final FlowType flowType, final long startTime,
      final long endTime, final Continuation<? super Double> continuation) {
    final String _sql = "SELECT SUM(amount) FROM expenses WHERE flowType = ? AND timestamp BETWEEN ? AND ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (flowType == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, __FlowType_enumToString(flowType));
    }
    _argIndex = 2;
    _statement.bindLong(_argIndex, startTime);
    _argIndex = 3;
    _statement.bindLong(_argIndex, endTime);
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private String __FlowType_enumToString(final FlowType _value) {
    if (_value == null) {
      return null;
    } switch (_value) {
      case INFLOW: return "INFLOW";
      case OUTFLOW: return "OUTFLOW";
      default: throw new IllegalArgumentException("Can't convert enum to string, unknown enum value: " + _value);
    }
  }

  private FlowType __FlowType_stringToEnum(final String _value) {
    if (_value == null) {
      return null;
    } switch (_value) {
      case "INFLOW": return FlowType.INFLOW;
      case "OUTFLOW": return FlowType.OUTFLOW;
      default: throw new IllegalArgumentException("Can't convert value to enum, unknown value: " + _value);
    }
  }
}
