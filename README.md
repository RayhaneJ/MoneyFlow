# MoneyFlow - Smart Money Tracker

A modern Android application for tracking your money flow with flexible wallets and intuitive flow-based management.

## 🎯 What is MoneyFlow?

MoneyFlow is a financial tracking app that helps you monitor your income and expenses using a unique **wallet-based system**. Unlike traditional budgeting apps, MoneyFlow focuses on tracking the **flow** of money (inflows and outflows) rather than strict budget assignments.

## ✨ Key Features

### 💼 Wallet System
- **Multiple Wallet Types**: Create Expense, Savings, and Income wallets
- **Optional Limits**: Set spending limits on wallets (not mandatory)
- **Custom Icons**: Personalize each wallet with emojis
- **Color Coding**: Assign unique colors to each wallet

### 💸 Flow Tracking
- **Inflow/Outflow**: Track money coming in and going out
- **Net Flow View**: See your monthly net flow at a glance
- **Real-time Updates**: Balances update automatically
- **Quick Actions**: Fast buttons for adding inflows/outflows

### 📊 Smart Features
- **Search**: Find expenses quickly
- **Tags**: Organize expenses with custom tags
- **Location**: Add location info to expenses
- **Archive**: Archive wallets instead of deleting
- **Reports**: Visual monthly flow reports

### 🎨 Beautiful Design
- **Material Design 3**: Modern, clean interface
- **Cyan/Turquoise Theme**: Fresh, unique color scheme
- **Circular Icons**: Distinctive visual style
- **Rounded Cards**: Smooth, polished look

### 🔒 Privacy First
- **100% Offline**: No internet required
- **Local Storage**: All data stays on your device
- **No Tracking**: No analytics or data collection
- **No Ads**: Clean, distraction-free experience

## 📱 Screenshots

[Dashboard View - Monthly Flow Card]
[Wallets List - Circular Icons]
[Expense Entry - Flow Type Selection]

## 🏗️ Technical Stack

| Component | Technology |
|-----------|-----------|
| Language | Kotlin 1.9.0 |
| Architecture | MVVM Pattern |
| Database | Room (SQLite) |
| UI | Material Design 3 |
| Async | Coroutines + LiveData |
| Min SDK | API 24 (Android 7.0) |
| Target SDK | API 34 (Android 14) |

## 📦 Installation

### From Source
1. Clone the repository
2. Open in Android Studio
3. Sync Gradle
4. Run on device/emulator

### Requirements
- Android Studio Arctic Fox or later
- Android SDK 24+
- JDK 17

## 🚀 Quick Start

1. **Create Your First Wallet**
   - Tap "+ New" on the dashboard
   - Enter wallet name (e.g., "Monthly Expenses")
   - Choose an icon
   - Set optional spending limit
   - Select wallet type

2. **Track Your Expenses**
   - Tap the floating "+" button
   - Enter amount and title
   - Select wallet
   - Choose flow type (Inflow/Outflow)
   - Add optional memo, tags, location

3. **Monitor Your Flow**
   - View Net Flow on dashboard
   - See monthly inflow/outflow
   - Track wallet balances
   - Check progress bars

## 💡 MoneyFlow Philosophy

### Flow-Based Tracking
MoneyFlow uses a **flow-based approach** rather than zero-based budgeting:
- Focus on money movement (inflows & outflows)
- Optional limits (not mandatory assignments)
- Flexible wallet system
- Real-time balance tracking

### Three Wallet Types

**Expense Wallets** 💳
- Track spending categories
- Set optional limits
- Monitor remaining balance

**Savings Wallets** 🏦
- Track savings goals
- Watch growth over time
- Separate from daily expenses

**Income Wallets** 💰
- Track income sources
- Monitor earnings
- Multiple income streams

## 🎨 Design Principles

### Color Palette
- **Primary**: Cyan (#00BCD4)
- **Secondary**: Teal (#009688)
- **Accent**: Amber (#FFC107)
- **Success**: Green (#4CAF50)
- **Warning**: Orange (#FF9800)
- **Error**: Deep Orange (#FF5722)

### Visual Style
- Circular icon backgrounds
- Rounded corners (12dp)
- Elevated cards (3-6dp)
- Clear visual hierarchy
- Consistent spacing

## 📊 Database Schema

### Wallets Table
```sql
CREATE TABLE wallets (
    id INTEGER PRIMARY KEY,
    name TEXT,
    icon TEXT,
    limit REAL,
    balance REAL,
    colorHex TEXT,
    isArchived INTEGER,
    walletType TEXT,
    createdAt INTEGER
);
```

### Expenses Table
```sql
CREATE TABLE expenses (
    id INTEGER PRIMARY KEY,
    walletId INTEGER,
    amount REAL,
    title TEXT,
    timestamp INTEGER,
    flowType TEXT,
    memo TEXT,
    tags TEXT,
    location TEXT,
    FOREIGN KEY(walletId) REFERENCES wallets(id)
);
```

### Monthly Overview Table
```sql
CREATE TABLE monthly_overview (
    id INTEGER PRIMARY KEY,
    yearMonth TEXT,
    totalInflow REAL,
    totalOutflow REAL,
    netFlow REAL,
    savingsRate REAL,
    updatedAt INTEGER
);
```

## 🔧 Configuration

### Change App Name
Edit `app/src/main/res/values/strings.xml`:
```xml
<string name="app_name">YourAppName</string>
```

### Change Package Name
1. Rename package in Android Studio
2. Update `applicationId` in `app/build.gradle`
3. Update manifest package

### Change Colors
Edit `app/src/main/res/values/colors.xml`:
```xml
<color name="cyan_500">#YOUR_COLOR</color>
```

## 🛠️ Development

### Build Debug APK
```bash
./gradlew assembleDebug
```

### Build Release APK
```bash
./gradlew assembleRelease
```

### Run Tests
```bash
./gradlew test
```

## 📝 License

This project is created for educational and personal use.

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

## 📧 Contact

For questions or feedback, please open an issue on the repository.

## 🙏 Acknowledgments

- Built with Material Design Components
- Uses Room for database
- Implements MVVM architecture pattern

---

**MoneyFlow** - Track Your Money Flow 💸
