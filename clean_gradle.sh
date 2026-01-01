#!/bin/bash
echo "🧹 Nettoyage complet de Gradle..."
cd "$(dirname "$0")"

# Supprimer les caches
rm -rf .gradle/
rm -rf app/build/
rm -rf build/

# Supprimer les fichiers locaux
rm -f local.properties
rm -rf .idea/

echo "✅ Nettoyage terminé!"
echo ""
echo "Maintenant dans Android Studio:"
echo "1. File → Invalidate Caches → Invalidate and Restart"
echo "2. Gradle Sync"
echo ""
echo "Ou en ligne de commande:"
echo "./gradlew clean"
echo "./gradlew build --refresh-dependencies"
