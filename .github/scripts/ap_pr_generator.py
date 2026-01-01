#!/usr/bin/env python3
"""
AI PR Generator for MoneyFlow
Uses Claude API to generate Android code based on task descriptions
"""

import os
import sys
import json
import traceback

try:
    import anthropic
except ImportError:
    print("Error: anthropic package not installed", file=sys.stderr)
    print("Run: pip install anthropic", file=sys.stderr)
    sys.exit(1)


def read_file_safe(filepath):
    """Safely read a file, return empty string if not exists"""
    try:
        with open(filepath, 'r', encoding='utf-8') as f:
            return f.read()
    except:
        return ""


def get_project_context():
    """Build comprehensive project context for AI"""
    
    # Read actual project files for context
    wallet_model = read_file_safe("app/src/main/java/com/moneyflow/tracker/data/model/Wallet.kt")
    expense_model = read_file_safe("app/src/main/java/com/moneyflow/tracker/data/model/Expense.kt")
    
    context = f"""
# MoneyFlow Android App

## Architecture
- **Pattern**: MVVM (Model-View-ViewModel)
- **Database**: Room (SQLite)
- **Async**: Kotlin Coroutines + Flow
- **DI**: ViewModels with ViewModelFactory
- **UI**: Material Design 3, ViewBinding

## Project Structure
```
app/src/main/java/com/moneyflow/tracker/
├── data/
│   ├── model/
│   │   ├── Wallet.kt
│   │   ├── Expense.kt  
│   │   ├── MonthlyOverview.kt
│   │   └── FlowType.kt (enum: INFLOW, OUTFLOW)
│   ├── dao/
│   │   ├── WalletDao.kt
│   │   ├── ExpenseDao.kt
│   │   └── MonthlyOverviewDao.kt
│   └── database/
│       └── MoneyFlowDatabase.kt
├── repository/
│   └── MoneyFlowRepository.kt
├── ui/
│   ├── activity/
│   │   ├── DashboardActivity.kt
│   │   ├── CreateWalletActivity.kt
│   │   ├── AddExpenseActivity.kt
│   │   ├── WalletDetailsActivity.kt
│   │   └── ReportsActivity.kt
│   ├── adapter/
│   │   ├── WalletAdapter.kt
│   │   └── ExpenseAdapter.kt
│   └── viewmodel/
│       └── MoneyFlowViewModel.kt
```

## Tech Stack
- **Language**: Kotlin 1.9.0
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 33 (Android 13)
- **Gradle**: 7.6.4
- **AGP**: 7.4.2

## Dependencies
- Room: 2.5.2
- Lifecycle: 2.6.2
- Material: 1.9.0
- Coroutines: 1.7.3
- RecyclerView: 1.3.1

## Coding Conventions
- Use ViewBinding (no findViewById)
- LiveData for UI updates
- Suspend functions for DB operations
- Repository pattern for data access
- Descriptive variable names (no single letters)
- Comments for complex logic

## Design Patterns
- **Theme**: Cyan (#00BCD4) primary color
- **Cards**: MaterialCardView with elevation
- **Lists**: RecyclerView with DiffUtil
- **Icons**: Emoji for wallets
- **Currency**: US format ($1,234.56)

## Current Models
{wallet_model[:500] if wallet_model else "Wallet model not found"}

{expense_model[:500] if expense_model else "Expense model not found"}
"""
    return context


def generate_code(task_description):
    """Call Claude API to generate code"""
    
    api_key = os.environ.get('ANTHROPIC_API_KEY')
    if not api_key:
        print("❌ Error: ANTHROPIC_API_KEY environment variable not set", file=sys.stderr)
        print("", file=sys.stderr)
        print("Setup instructions:", file=sys.stderr)
        print("1. Go to: https://console.anthropic.com/", file=sys.stderr)
        print("2. Get an API key", file=sys.stderr)
        print("3. Add to GitHub Secrets as ANTHROPIC_API_KEY", file=sys.stderr)
        sys.exit(1)
    
    try:
        client = anthropic.Anthropic(api_key=api_key)
    except Exception as e:
        print(f"❌ Error initializing Anthropic client: {e}", file=sys.stderr)
        sys.exit(1)
    
    context = get_project_context()
    
    prompt = f"""
You are an expert Android developer working on MoneyFlow, a personal budget tracking app.

{context}

## TASK
{task_description}

## INSTRUCTIONS
Generate complete, production-ready Kotlin code to implement this task.

Follow these rules:
1. **Architecture**: Use MVVM pattern strictly
2. **Database**: If modifying data models, include Room migration
3. **UI**: Use ViewBinding, Material Design 3
4. **Async**: Use Coroutines with proper exception handling
5. **Code Style**: Follow existing patterns exactly
6. **Comments**: Add clear, helpful comments
7. **Imports**: Include all necessary imports
8. **Error Handling**: Use try-catch where appropriate

## RESPONSE FORMAT
Respond with ONLY valid JSON (no markdown, no explanation outside JSON):

{{
  "files": [
    {{
      "path": "com/moneyflow/tracker/...",
      "content": "package com.moneyflow.tracker\\n\\nimport ...",
      "action": "create|modify",
      "explanation": "Brief explanation of this file"
    }}
  ],
  "pr_title": "Add [feature name]",
  "pr_description": "## What This PR Does\\n- Bullet points\\n\\n## How to Test\\n1. Steps\\n\\n## Screenshots\\n(Optional)",
  "breaking_changes": false,
  "requires_migration": false
}}

CRITICAL:
- Paths are relative to app/src/main/java/
- Use \\n for newlines in content
- Escape quotes in strings: \\"
- No markdown formatting in JSON values
- Keep code properly formatted and indented
"""
    
    print("🤖 Calling Claude AI...", file=sys.stderr)
    print(f"📝 Task: {task_description}", file=sys.stderr)
    
    try:
        response = client.messages.create(
            model="claude-sonnet-4-20250514",
            max_tokens=8000,
            temperature=0.3,  # Lower temperature for more consistent code
            messages=[{"role": "user", "content": prompt}]
        )
        
        response_text = response.content[0].text
        
        # Clean response
        response_text = response_text.strip()
        
        # Remove markdown code blocks if present
        if "```json" in response_text:
            response_text = response_text.split("```json")[1].split("```")[0].strip()
        elif "```" in response_text:
            response_text = response_text.split("```")[1].split("```")[0].strip()
        
        # Parse JSON
        try:
            result = json.loads(response_text)
            print("✅ AI response parsed successfully", file=sys.stderr)
            return result
        except json.JSONDecodeError as e:
            print(f"❌ Error parsing AI response as JSON: {e}", file=sys.stderr)
            print(f"Response (first 500 chars):\n{response_text[:500]}", file=sys.stderr)
            sys.exit(1)
            
    except anthropic.APIError as e:
        print(f"❌ Anthropic API Error: {e}", file=sys.stderr)
        sys.exit(1)
    except Exception as e:
        print(f"❌ Unexpected error: {e}", file=sys.stderr)
        traceback.print_exc(file=sys.stderr)
        sys.exit(1)


def apply_changes(ai_response):
    """Apply AI-generated changes to files"""
    
    base_path = "app/src/main/java"
    created_files = []
    modified_files = []
    
    for file_info in ai_response.get('files', []):
        filepath = os.path.join(base_path, file_info['path'])
        action = file_info.get('action', 'create')
        
        # Create directory if needed
        os.makedirs(os.path.dirname(filepath), exist_ok=True)
        
        # Write file
        try:
            with open(filepath, 'w', encoding='utf-8') as f:
                f.write(file_info['content'])
            
            if action == 'create':
                created_files.append(filepath)
                print(f"✅ Created: {filepath}", file=sys.stderr)
            else:
                modified_files.append(filepath)
                print(f"✏️  Modified: {filepath}", file=sys.stderr)
                
        except Exception as e:
            print(f"❌ Error writing {filepath}: {e}", file=sys.stderr)
    
    return created_files, modified_files


def main():
    if len(sys.argv) < 2:
        print("Usage: python ai_pr_generator.py 'task description'", file=sys.stderr)
        print("", file=sys.stderr)
        print("Example:", file=sys.stderr)
        print("  python ai_pr_generator.py 'Add dark mode support to Dashboard'", file=sys.stderr)
        sys.exit(1)
    
    task = ' '.join(sys.argv[1:])
    
    # Generate code
    ai_response = generate_code(task)
    
    # Apply changes
    created, modified = apply_changes(ai_response)
    
    # Prepare output for GitHub Actions
    output = {
        'task': task,
        'files_created': created,
        'files_modified': modified,
        'pr_title': ai_response.get('pr_title', f'AI: {task}'),
        'pr_description': ai_response.get('pr_description', 'Changes generated by AI'),
        'breaking_changes': ai_response.get('breaking_changes', False),
        'requires_migration': ai_response.get('requires_migration', False)
    }
    
    # Save output
    with open('ai_output.json', 'w') as f:
        json.dump(output, f, indent=2)
    
    print("\n✅ Code generation complete!", file=sys.stderr)
    print(f"📁 Files created: {len(created)}", file=sys.stderr)
    print(f"📝 Files modified: {len(modified)}", file=sys.stderr)
    
    # Print JSON output for GitHub Actions
    print(json.dumps(output, indent=2))


if __name__ == '__main__':
    main()