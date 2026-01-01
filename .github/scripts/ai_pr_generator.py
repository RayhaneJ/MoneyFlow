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


def get_all_kotlin_files():
    """Recursively find all Kotlin files in the project"""
    import os
    
    kotlin_files = {}
    base_path = "app/src/main/java/com/moneyflow/tracker"
    
    if not os.path.exists(base_path):
        print(f"⚠️  Path not found: {base_path}", file=sys.stderr)
        return kotlin_files
    
    # Find all .kt files recursively
    for root, dirs, files in os.walk(base_path):
        for file in files:
            if file.endswith('.kt'):
                full_path = os.path.join(root, file)
                relative_path = full_path.replace(base_path + '/', '')
                content = read_file_safe(full_path)
                if content:
                    kotlin_files[relative_path] = content
    
    print(f"   ✅ Found {len(kotlin_files)} Kotlin files", file=sys.stderr)
    return kotlin_files


def get_gradle_files():
    """Read gradle files for dependencies info"""
    gradle_files = {}
    
    # App build.gradle (Kotlin or Groovy)
    for ext in ['.kts', '']:
        path = f'app/build.gradle{ext}'
        content = read_file_safe(path)
        if content:
            gradle_files['app/build.gradle'] = content
            break
    
    # Project build.gradle
    for ext in ['.kts', '']:
        path = f'build.gradle{ext}'
        content = read_file_safe(path)
        if content:
            gradle_files['build.gradle'] = content
            break
    
    print(f"   ✅ Found {len(gradle_files)} Gradle files", file=sys.stderr)
    return gradle_files


def get_manifest():
    """Read AndroidManifest.xml"""
    manifest_path = "app/src/main/AndroidManifest.xml"
    content = read_file_safe(manifest_path)
    if content:
        print(f"   ✅ Found AndroidManifest.xml", file=sys.stderr)
        return content
    return ""


def get_project_context():
    """Build comprehensive project context with ALL files"""
    
    print("📁 Reading ENTIRE repository...", file=sys.stderr)
    
    # Get all Kotlin files
    kotlin_files = get_all_kotlin_files()
    
    # Get Gradle files
    gradle_files = get_gradle_files()
    
    # Get manifest
    manifest = get_manifest()
    
    # Calculate total size
    total_chars = sum(len(content) for content in kotlin_files.values())
    total_chars += sum(len(content) for content in gradle_files.values())
    total_chars += len(manifest)
    
    print(f"   📊 Total characters: {total_chars:,}", file=sys.stderr)
    print(f"   📊 Estimated tokens: ~{total_chars // 4:,}", file=sys.stderr)
    
    # Build Kotlin files section - FULL CONTENT, no truncation
    kotlin_section = ""
    for filepath, content in sorted(kotlin_files.items()):
        kotlin_section += f"\n## FILE: {filepath}\n```kotlin\n{content}\n```\n"
    
    # Build Gradle section - FULL CONTENT
    gradle_section = ""
    for filepath, content in gradle_files.items():
        if content:
            gradle_section += f"\n## FILE: {filepath}\n```gradle\n{content}\n```\n"
    
    # Manifest section
    manifest_section = ""
    if manifest:
        manifest_section = f"\n## FILE: AndroidManifest.xml\n```xml\n{manifest}\n```\n"
    
    # File counts by category
    models = [f for f in kotlin_files if '/model/' in f]
    daos = [f for f in kotlin_files if '/dao/' in f]
    activities = [f for f in kotlin_files if '/activity/' in f]
    adapters = [f for f in kotlin_files if '/adapter/' in f]
    viewmodels = [f for f in kotlin_files if '/viewmodel/' in f]
    repositories = [f for f in kotlin_files if '/repository/' in f]
    databases = [f for f in kotlin_files if '/database/' in f]
    
    context = f"""
# MoneyFlow Android App - COMPLETE PROJECT CONTEXT

## PROJECT SUMMARY
- Total Kotlin files: {len(kotlin_files)}
- Models: {len(models)}
- DAOs: {len(daos)}
- Activities: {len(activities)}
- Adapters: {len(adapters)}
- ViewModels: {len(viewmodels)}
- Repositories: {len(repositories)}
- Databases: {len(databases)}

## BUILD CONFIGURATION
{gradle_section}

## ANDROID MANIFEST
{manifest_section}

## ALL KOTLIN SOURCE FILES
{kotlin_section}

## CRITICAL INSTRUCTIONS

### 1. YOU HAVE ACCESS TO ALL CODE ABOVE
- Every class, method, property is shown in full above
- Every import is visible
- Every dependency is listed in gradle files
- **DO NOT INVENT ANYTHING** - only use what you see above

### 2. COMPLETE IMPORTS ARE MANDATORY
Before using ANY class, verify:
1. It exists in the code above, OR
2. It's a standard Android SDK class

Example of finding imports:
- Need to use `Wallet`? → Search above for "class Wallet" → Found in data/model/Wallet.kt → Import: `import com.moneyflow.tracker.data.model.Wallet`
- Need to use `WalletDao`? → Search above for "interface WalletDao" → Found in data/dao/WalletDao.kt → Import: `import com.moneyflow.tracker.data.dao.WalletDao`

### 3. STANDARD ANDROID IMPORTS (Always Available)
```kotlin
// Core Android
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.*

// AndroidX
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.*

// Material Design
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

// Kotlin Coroutines
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
```

### 4. VERIFICATION CHECKLIST
Before generating code, verify:
- [ ] Every class I use is in the code above or standard Android
- [ ] Every method I call exists on that class
- [ ] Every property I access exists in that class
- [ ] All imports are complete and correct
- [ ] Package names match the file structure above

### 5. FOLLOW EXISTING PATTERNS EXACTLY
- Look at existing Activities → Copy their structure
- Look at existing DAOs → Copy query patterns
- Look at existing ViewModels → Copy LiveData usage
- Look at existing Adapters → Copy DiffUtil patterns

### 6. NO PLACEHOLDERS
- No "// TODO"
- No "// Implement this"
- No pseudo-code
- Complete, compilable code only
"""
    
    print("✅ Complete project context built", file=sys.stderr)
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

## CRITICAL INSTRUCTIONS

### 1. Use ONLY Existing Code
- **DO NOT** invent new classes, methods, or properties
- **ONLY** use what exists in the context above
- If you need something that doesn't exist, mention it in pr_description

### 2. Include ALL Necessary Imports
- Add EVERY import needed
- Check the "Common Imports" section above
- Include Material Design imports
- Include Room imports if using database
- Include Coroutines imports if using suspend functions

### 3. Follow Existing Patterns EXACTLY
- Look at the code in the context
- Copy the same style, naming, and structure
- Use the same dependencies (exact versions shown above)

### 4. Error Prevention
- **Before using any class**: Verify it exists in context or is standard Android
- **Before using any method**: Verify it exists on that class
- **Before using any property**: Verify it exists in the model

### 5. Complete Code ONLY
- No pseudo-code
- No "// TODO" comments
- No placeholders
- Working, compilable code only

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
  "pr_title": "Fix: [specific issue]",
  "pr_description": "## What This Fixes\\n- Bullet points\\n\\n## Changes Made\\n- List changes\\n\\n## Testing\\n1. Steps to test\\n\\n## Notes\\n- Any limitations or missing features",
  "breaking_changes": false,
  "requires_migration": false
}}

CRITICAL:
- Paths are relative to app/src/main/java/
- Use \\n for newlines in content
- Escape quotes in strings: \\"
- No markdown formatting in JSON values
- Include ALL imports at the top of each file
- Use ONLY the dependencies listed in the context
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
