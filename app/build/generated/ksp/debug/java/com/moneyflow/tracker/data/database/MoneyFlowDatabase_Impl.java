package com.moneyflow.tracker.data.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.moneyflow.tracker.data.dao.ExpenseDao;
import com.moneyflow.tracker.data.dao.ExpenseDao_Impl;
import com.moneyflow.tracker.data.dao.MonthlyOverviewDao;
import com.moneyflow.tracker.data.dao.MonthlyOverviewDao_Impl;
import com.moneyflow.tracker.data.dao.WalletDao;
import com.moneyflow.tracker.data.dao.WalletDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MoneyFlowDatabase_Impl extends MoneyFlowDatabase {
  private volatile WalletDao _walletDao;

  private volatile ExpenseDao _expenseDao;

  private volatile MonthlyOverviewDao _monthlyOverviewDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `wallets` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `icon` TEXT NOT NULL, `limit` REAL NOT NULL, `balance` REAL NOT NULL, `colorHex` TEXT NOT NULL, `isArchived` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `walletType` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `expenses` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `walletId` INTEGER NOT NULL, `amount` REAL NOT NULL, `title` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `flowType` TEXT NOT NULL, `memo` TEXT NOT NULL, `tags` TEXT NOT NULL, `location` TEXT NOT NULL, FOREIGN KEY(`walletId`) REFERENCES `wallets`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_expenses_walletId` ON `expenses` (`walletId`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `monthly_overview` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `yearMonth` TEXT NOT NULL, `totalInflow` REAL NOT NULL, `totalOutflow` REAL NOT NULL, `netFlow` REAL NOT NULL, `savingsRate` REAL NOT NULL, `updatedAt` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'd4cf5b85728bbfc0a654248e9611adbf')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `wallets`");
        _db.execSQL("DROP TABLE IF EXISTS `expenses`");
        _db.execSQL("DROP TABLE IF EXISTS `monthly_overview`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      public void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsWallets = new HashMap<String, TableInfo.Column>(9);
        _columnsWallets.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWallets.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWallets.put("icon", new TableInfo.Column("icon", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWallets.put("limit", new TableInfo.Column("limit", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWallets.put("balance", new TableInfo.Column("balance", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWallets.put("colorHex", new TableInfo.Column("colorHex", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWallets.put("isArchived", new TableInfo.Column("isArchived", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWallets.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWallets.put("walletType", new TableInfo.Column("walletType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWallets = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWallets = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWallets = new TableInfo("wallets", _columnsWallets, _foreignKeysWallets, _indicesWallets);
        final TableInfo _existingWallets = TableInfo.read(_db, "wallets");
        if (! _infoWallets.equals(_existingWallets)) {
          return new RoomOpenHelper.ValidationResult(false, "wallets(com.moneyflow.tracker.data.model.Wallet).\n"
                  + " Expected:\n" + _infoWallets + "\n"
                  + " Found:\n" + _existingWallets);
        }
        final HashMap<String, TableInfo.Column> _columnsExpenses = new HashMap<String, TableInfo.Column>(9);
        _columnsExpenses.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("walletId", new TableInfo.Column("walletId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("flowType", new TableInfo.Column("flowType", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("memo", new TableInfo.Column("memo", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("tags", new TableInfo.Column("tags", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsExpenses.put("location", new TableInfo.Column("location", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysExpenses = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysExpenses.add(new TableInfo.ForeignKey("wallets", "CASCADE", "NO ACTION",Arrays.asList("walletId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesExpenses = new HashSet<TableInfo.Index>(1);
        _indicesExpenses.add(new TableInfo.Index("index_expenses_walletId", false, Arrays.asList("walletId"), Arrays.asList("ASC")));
        final TableInfo _infoExpenses = new TableInfo("expenses", _columnsExpenses, _foreignKeysExpenses, _indicesExpenses);
        final TableInfo _existingExpenses = TableInfo.read(_db, "expenses");
        if (! _infoExpenses.equals(_existingExpenses)) {
          return new RoomOpenHelper.ValidationResult(false, "expenses(com.moneyflow.tracker.data.model.Expense).\n"
                  + " Expected:\n" + _infoExpenses + "\n"
                  + " Found:\n" + _existingExpenses);
        }
        final HashMap<String, TableInfo.Column> _columnsMonthlyOverview = new HashMap<String, TableInfo.Column>(7);
        _columnsMonthlyOverview.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMonthlyOverview.put("yearMonth", new TableInfo.Column("yearMonth", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMonthlyOverview.put("totalInflow", new TableInfo.Column("totalInflow", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMonthlyOverview.put("totalOutflow", new TableInfo.Column("totalOutflow", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMonthlyOverview.put("netFlow", new TableInfo.Column("netFlow", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMonthlyOverview.put("savingsRate", new TableInfo.Column("savingsRate", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMonthlyOverview.put("updatedAt", new TableInfo.Column("updatedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMonthlyOverview = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMonthlyOverview = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMonthlyOverview = new TableInfo("monthly_overview", _columnsMonthlyOverview, _foreignKeysMonthlyOverview, _indicesMonthlyOverview);
        final TableInfo _existingMonthlyOverview = TableInfo.read(_db, "monthly_overview");
        if (! _infoMonthlyOverview.equals(_existingMonthlyOverview)) {
          return new RoomOpenHelper.ValidationResult(false, "monthly_overview(com.moneyflow.tracker.data.model.MonthlyOverview).\n"
                  + " Expected:\n" + _infoMonthlyOverview + "\n"
                  + " Found:\n" + _existingMonthlyOverview);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "d4cf5b85728bbfc0a654248e9611adbf", "d8690952732e2d9fa23161a1394978f1");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "wallets","expenses","monthly_overview");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `wallets`");
      _db.execSQL("DELETE FROM `expenses`");
      _db.execSQL("DELETE FROM `monthly_overview`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(WalletDao.class, WalletDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ExpenseDao.class, ExpenseDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(MonthlyOverviewDao.class, MonthlyOverviewDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public WalletDao walletDao() {
    if (_walletDao != null) {
      return _walletDao;
    } else {
      synchronized(this) {
        if(_walletDao == null) {
          _walletDao = new WalletDao_Impl(this);
        }
        return _walletDao;
      }
    }
  }

  @Override
  public ExpenseDao expenseDao() {
    if (_expenseDao != null) {
      return _expenseDao;
    } else {
      synchronized(this) {
        if(_expenseDao == null) {
          _expenseDao = new ExpenseDao_Impl(this);
        }
        return _expenseDao;
      }
    }
  }

  @Override
  public MonthlyOverviewDao monthlyOverviewDao() {
    if (_monthlyOverviewDao != null) {
      return _monthlyOverviewDao;
    } else {
      synchronized(this) {
        if(_monthlyOverviewDao == null) {
          _monthlyOverviewDao = new MonthlyOverviewDao_Impl(this);
        }
        return _monthlyOverviewDao;
      }
    }
  }
}
