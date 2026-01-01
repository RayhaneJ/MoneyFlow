package com.moneyflow.tracker.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.moneyflow.tracker.data.model.MonthlyOverview;
import java.lang.Class;
import java.lang.Exception;
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
public final class MonthlyOverviewDao_Impl implements MonthlyOverviewDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MonthlyOverview> __insertionAdapterOfMonthlyOverview;

  private final EntityDeletionOrUpdateAdapter<MonthlyOverview> __deletionAdapterOfMonthlyOverview;

  private final EntityDeletionOrUpdateAdapter<MonthlyOverview> __updateAdapterOfMonthlyOverview;

  public MonthlyOverviewDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMonthlyOverview = new EntityInsertionAdapter<MonthlyOverview>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `monthly_overview` (`id`,`yearMonth`,`totalInflow`,`totalOutflow`,`netFlow`,`savingsRate`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MonthlyOverview value) {
        stmt.bindLong(1, value.getId());
        if (value.getYearMonth() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getYearMonth());
        }
        stmt.bindDouble(3, value.getTotalInflow());
        stmt.bindDouble(4, value.getTotalOutflow());
        stmt.bindDouble(5, value.getNetFlow());
        stmt.bindDouble(6, value.getSavingsRate());
        stmt.bindLong(7, value.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfMonthlyOverview = new EntityDeletionOrUpdateAdapter<MonthlyOverview>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `monthly_overview` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MonthlyOverview value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfMonthlyOverview = new EntityDeletionOrUpdateAdapter<MonthlyOverview>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `monthly_overview` SET `id` = ?,`yearMonth` = ?,`totalInflow` = ?,`totalOutflow` = ?,`netFlow` = ?,`savingsRate` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MonthlyOverview value) {
        stmt.bindLong(1, value.getId());
        if (value.getYearMonth() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getYearMonth());
        }
        stmt.bindDouble(3, value.getTotalInflow());
        stmt.bindDouble(4, value.getTotalOutflow());
        stmt.bindDouble(5, value.getNetFlow());
        stmt.bindDouble(6, value.getSavingsRate());
        stmt.bindLong(7, value.getUpdatedAt());
        stmt.bindLong(8, value.getId());
      }
    };
  }

  @Override
  public Object insertOverview(final MonthlyOverview overview,
      final Continuation<? super Long> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfMonthlyOverview.insertAndReturnId(overview);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteOverview(final MonthlyOverview overview,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfMonthlyOverview.handle(overview);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateOverview(final MonthlyOverview overview,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfMonthlyOverview.handle(overview);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public LiveData<List<MonthlyOverview>> getAllMonthlyOverviews() {
    final String _sql = "SELECT * FROM monthly_overview ORDER BY yearMonth DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"monthly_overview"}, false, new Callable<List<MonthlyOverview>>() {
      @Override
      public List<MonthlyOverview> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfTotalInflow = CursorUtil.getColumnIndexOrThrow(_cursor, "totalInflow");
          final int _cursorIndexOfTotalOutflow = CursorUtil.getColumnIndexOrThrow(_cursor, "totalOutflow");
          final int _cursorIndexOfNetFlow = CursorUtil.getColumnIndexOrThrow(_cursor, "netFlow");
          final int _cursorIndexOfSavingsRate = CursorUtil.getColumnIndexOrThrow(_cursor, "savingsRate");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<MonthlyOverview> _result = new ArrayList<MonthlyOverview>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MonthlyOverview _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpYearMonth;
            if (_cursor.isNull(_cursorIndexOfYearMonth)) {
              _tmpYearMonth = null;
            } else {
              _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
            }
            final double _tmpTotalInflow;
            _tmpTotalInflow = _cursor.getDouble(_cursorIndexOfTotalInflow);
            final double _tmpTotalOutflow;
            _tmpTotalOutflow = _cursor.getDouble(_cursorIndexOfTotalOutflow);
            final double _tmpNetFlow;
            _tmpNetFlow = _cursor.getDouble(_cursorIndexOfNetFlow);
            final float _tmpSavingsRate;
            _tmpSavingsRate = _cursor.getFloat(_cursorIndexOfSavingsRate);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new MonthlyOverview(_tmpId,_tmpYearMonth,_tmpTotalInflow,_tmpTotalOutflow,_tmpNetFlow,_tmpSavingsRate,_tmpUpdatedAt);
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
  public Object getOverviewByMonth(final String yearMonth,
      final Continuation<? super MonthlyOverview> continuation) {
    final String _sql = "SELECT * FROM monthly_overview WHERE yearMonth = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (yearMonth == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, yearMonth);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<MonthlyOverview>() {
      @Override
      public MonthlyOverview call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfTotalInflow = CursorUtil.getColumnIndexOrThrow(_cursor, "totalInflow");
          final int _cursorIndexOfTotalOutflow = CursorUtil.getColumnIndexOrThrow(_cursor, "totalOutflow");
          final int _cursorIndexOfNetFlow = CursorUtil.getColumnIndexOrThrow(_cursor, "netFlow");
          final int _cursorIndexOfSavingsRate = CursorUtil.getColumnIndexOrThrow(_cursor, "savingsRate");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final MonthlyOverview _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpYearMonth;
            if (_cursor.isNull(_cursorIndexOfYearMonth)) {
              _tmpYearMonth = null;
            } else {
              _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
            }
            final double _tmpTotalInflow;
            _tmpTotalInflow = _cursor.getDouble(_cursorIndexOfTotalInflow);
            final double _tmpTotalOutflow;
            _tmpTotalOutflow = _cursor.getDouble(_cursorIndexOfTotalOutflow);
            final double _tmpNetFlow;
            _tmpNetFlow = _cursor.getDouble(_cursorIndexOfNetFlow);
            final float _tmpSavingsRate;
            _tmpSavingsRate = _cursor.getFloat(_cursorIndexOfSavingsRate);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new MonthlyOverview(_tmpId,_tmpYearMonth,_tmpTotalInflow,_tmpTotalOutflow,_tmpNetFlow,_tmpSavingsRate,_tmpUpdatedAt);
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
  public LiveData<MonthlyOverview> getOverviewByMonthLive(final String yearMonth) {
    final String _sql = "SELECT * FROM monthly_overview WHERE yearMonth = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (yearMonth == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, yearMonth);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"monthly_overview"}, false, new Callable<MonthlyOverview>() {
      @Override
      public MonthlyOverview call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfTotalInflow = CursorUtil.getColumnIndexOrThrow(_cursor, "totalInflow");
          final int _cursorIndexOfTotalOutflow = CursorUtil.getColumnIndexOrThrow(_cursor, "totalOutflow");
          final int _cursorIndexOfNetFlow = CursorUtil.getColumnIndexOrThrow(_cursor, "netFlow");
          final int _cursorIndexOfSavingsRate = CursorUtil.getColumnIndexOrThrow(_cursor, "savingsRate");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final MonthlyOverview _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpYearMonth;
            if (_cursor.isNull(_cursorIndexOfYearMonth)) {
              _tmpYearMonth = null;
            } else {
              _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
            }
            final double _tmpTotalInflow;
            _tmpTotalInflow = _cursor.getDouble(_cursorIndexOfTotalInflow);
            final double _tmpTotalOutflow;
            _tmpTotalOutflow = _cursor.getDouble(_cursorIndexOfTotalOutflow);
            final double _tmpNetFlow;
            _tmpNetFlow = _cursor.getDouble(_cursorIndexOfNetFlow);
            final float _tmpSavingsRate;
            _tmpSavingsRate = _cursor.getFloat(_cursorIndexOfSavingsRate);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new MonthlyOverview(_tmpId,_tmpYearMonth,_tmpTotalInflow,_tmpTotalOutflow,_tmpNetFlow,_tmpSavingsRate,_tmpUpdatedAt);
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
  public LiveData<List<MonthlyOverview>> getRecentOverviews(final int limit) {
    final String _sql = "SELECT * FROM monthly_overview ORDER BY yearMonth DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return __db.getInvalidationTracker().createLiveData(new String[]{"monthly_overview"}, false, new Callable<List<MonthlyOverview>>() {
      @Override
      public List<MonthlyOverview> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfYearMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "yearMonth");
          final int _cursorIndexOfTotalInflow = CursorUtil.getColumnIndexOrThrow(_cursor, "totalInflow");
          final int _cursorIndexOfTotalOutflow = CursorUtil.getColumnIndexOrThrow(_cursor, "totalOutflow");
          final int _cursorIndexOfNetFlow = CursorUtil.getColumnIndexOrThrow(_cursor, "netFlow");
          final int _cursorIndexOfSavingsRate = CursorUtil.getColumnIndexOrThrow(_cursor, "savingsRate");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final List<MonthlyOverview> _result = new ArrayList<MonthlyOverview>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MonthlyOverview _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpYearMonth;
            if (_cursor.isNull(_cursorIndexOfYearMonth)) {
              _tmpYearMonth = null;
            } else {
              _tmpYearMonth = _cursor.getString(_cursorIndexOfYearMonth);
            }
            final double _tmpTotalInflow;
            _tmpTotalInflow = _cursor.getDouble(_cursorIndexOfTotalInflow);
            final double _tmpTotalOutflow;
            _tmpTotalOutflow = _cursor.getDouble(_cursorIndexOfTotalOutflow);
            final double _tmpNetFlow;
            _tmpNetFlow = _cursor.getDouble(_cursorIndexOfNetFlow);
            final float _tmpSavingsRate;
            _tmpSavingsRate = _cursor.getFloat(_cursorIndexOfSavingsRate);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new MonthlyOverview(_tmpId,_tmpYearMonth,_tmpTotalInflow,_tmpTotalOutflow,_tmpNetFlow,_tmpSavingsRate,_tmpUpdatedAt);
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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
