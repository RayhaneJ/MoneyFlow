# 🤖 AI PR Automation for MoneyFlow

This system allows you to automatically generate Pull Requests using AI (Claude API).

## 🚀 Quick Start

### Prerequisites

1. **Anthropic API Key** (for Claude)
   - Get one from: https://console.anthropic.com/
   - Add as GitHub secret: `ANTHROPIC_API_KEY`

### Setup

1. Go to your GitHub repository → **Settings** → **Secrets and variables** → **Actions**
2. Click **New repository secret**
3. Name: `ANTHROPIC_API_KEY`
4. Value: Your Anthropic API key
5. Click **Add secret**

That's it! ✅

---

## 📋 How to Use

### Method 1: Manual Trigger (Recommended)

1. Go to **Actions** tab in your repo
2. Click **AI PR Automation** workflow
3. Click **Run workflow**
4. Enter task description (e.g., "Add export to CSV feature")
5. Click **Run workflow**
6. Wait ~30 seconds
7. Check **Pull Requests** tab for the new AI-generated PR! 🎉

### Method 2: Via GitHub Issue

1. Create a new issue with your task as the title
   - Example: "Add dark mode support"
2. Add the label **`ai-task`**
3. AI will automatically generate a PR
4. You'll get a comment on the issue with the PR link

### Method 3: Comment Command

1. On any issue or PR, comment:
   ```
   /ai Add settings screen with user preferences
   ```
2. AI will generate a PR based on your command

---

## 📝 Example Tasks

Good task descriptions:

✅ **Specific:**
- "Add swipe-to-delete gesture to expenses in WalletDetailsActivity"
- "Create a settings screen with dark mode toggle"
- "Add category icons picker in CreateWalletActivity"

✅ **Clear scope:**
- "Implement monthly expense chart in ReportsActivity"
- "Add search functionality to Dashboard"
- "Create backup/restore feature using JSON export"

❌ **Too vague:**
- "Improve the app" → Too broad
- "Fix bugs" → No specific target
- "Make it better" → Not actionable

---

## 🔧 What the AI Can Do

The AI will:
- ✅ Create new Kotlin files
- ✅ Modify existing files
- ✅ Follow MVVM architecture
- ✅ Use Material Design 3
- ✅ Add proper imports
- ✅ Include comments
- ✅ Handle errors gracefully

The AI follows your project's patterns:
- Room Database
- Coroutines
- ViewBinding
- LiveData
- Repository pattern

---

## 📊 Workflow

```
Your Task
    ↓
AI (Claude) analyzes project
    ↓
Generates Kotlin code
    ↓
Creates new branch
    ↓
Commits changes
    ↓
Opens Pull Request
    ↓
You review & merge! ✅
```

---

## ⚠️ Important Notes

### Review Before Merging!

**Always review AI-generated code:**
- ✅ Does it compile?
- ✅ Does it follow project patterns?
- ✅ Are there any security issues?
- ✅ Does it work as expected?

**The AI is smart, but not perfect!** Treat AI PRs like any other PR from a team member.

### Best Practices

1. **Start small:** Try simple tasks first
2. **Be specific:** Clear descriptions = better code
3. **Test thoroughly:** AI code needs testing
4. **Iterate:** If the first PR isn't perfect, create another task to refine it

---

## 🐛 Troubleshooting

### "AI PR Generation Failed"

**Check:**
1. Is `ANTHROPIC_API_KEY` set correctly?
2. Do you have enough API credits?
3. Is the task description clear?

**View logs:**
- Go to Actions tab → Click the failed workflow → View logs

### No PR Created

The AI might not have generated any code changes. Try:
- Make the task more specific
- Check if the feature already exists
- Review workflow logs for errors

---

## 💡 Tips

### Get Better Results

1. **Include context:**
   ```
   Add a pie chart to ReportsActivity showing expense breakdown by wallet.
   Use MPAndroidChart library.
   ```

2. **Reference existing code:**
   ```
   Add a feature similar to WalletDetailsActivity but for monthly summaries
   ```

3. **Specify UI details:**
   ```
   Create a FAB in Dashboard that opens a date picker for custom date range filtering
   ```

### Combine with Manual Edits

1. Let AI create the scaffold
2. You refine and add details
3. Perfect combination of speed + quality!

---

## 📚 Examples

### Example 1: New Feature

**Task:**
```
Add expense categories (Food, Transport, Entertainment, Shopping, Bills, Other).
Add a category selector in AddExpenseActivity.
```

**Result:**
- Creates `Category` enum
- Modifies `Expense` model to include category
- Updates `AddExpenseActivity` with category dropdown
- Adds database migration
- Updates adapter to show category icons

### Example 2: UI Improvement

**Task:**
```
Add pull-to-refresh in WalletDetailsActivity to reload expenses
```

**Result:**
- Adds SwipeRefreshLayout
- Implements refresh logic
- Updates layout XML
- Follows Material Design patterns

### Example 3: Bug Fix

**Task:**
```
Fix: Expenses not updating in real-time when returning from AddExpenseActivity
```

**Result:**
- Analyzes the issue
- Adds proper LiveData observation
- Ensures data refresh in onResume()

---

## 🎯 Project-Specific Guidelines

The AI knows about MoneyFlow's structure:

**Architecture:**
- MVVM pattern
- Repository → ViewModel → Activity
- Room for database
- Coroutines for async

**Existing Components:**
- `MoneyFlowDatabase`
- `MoneyFlowRepository`
- `MoneyFlowViewModel`
- Activities: Dashboard, CreateWallet, AddExpense, WalletDetails, Reports

**Styling:**
- Material Design 3
- Cyan theme (#00BCD4)
- Custom card designs

---

## 🔐 Security

- API keys are stored as GitHub secrets (encrypted)
- AI can only create PRs, not merge them
- All changes go through review process
- You have full control over what gets merged

---

## 📞 Support

If you encounter issues:

1. Check workflow logs in Actions tab
2. Verify API key is set correctly
3. Try a simpler task first
4. Review this README

---

## 🎉 Have Fun!

Experiment with the AI! It's a powerful tool to:
- Prototype features quickly
- Learn new patterns
- Generate boilerplate code
- Explore ideas

**Happy coding! 🚀**