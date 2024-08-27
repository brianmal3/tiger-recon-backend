#!/bin/bash
# 🔴 🔴 Parameters provided (should be in this order):
# 🔴 Commit Message: refactored push script 👿

# 🍎🍎🍎🍎 COMMAND TO PUSH CODE
#  ./push.sh  "🅿️ initial commit"

echo
echo "🔴 🔴 🔴 🔴 🔴 FakeBank Push starting ..."
echo "🔴 🔴 🔴 🔴 🔴"

# Ensure the script is called with three arguments
if [ "$#" -ne 1 ]; then
  echo "👿 Please enter commit message. 👿"
  exit 1
fi

# Assign parameters to variables
ssh_key_path=~/.ssh/i_greene
repository_ssh_url=git@github.com:brianmal3/tiger-recon-backend.git
commit_message=$1

# Echo the parameters for clarity
echo "🍏 🍏 🍏 🍏 🍏🍏 🍏 🍏 🍏 🍏 🍏 🍏 🍏 🍏 🍏 Parameters provided:"
echo "🍏 SSH Key Path: $ssh_key_path"
echo "🍏 Repository SSH URL: $repository_ssh_url"
echo "🍏 Commit Message: $commit_message"
echo 🍏 🍏 🍏 🍏 🍏🍏 🍏 🍏 🍏 🍏 🍏 🍏 🍏 🍏 🍏 🍏 🍏 🍏 🍏 🍏 🍏 🍏 🍏 🍏
# Check if SSH key path file exists
if [ ! -f "$ssh_key_path" ]; then
  echo "👿 SSH key file does not exist at the specified path: $ssh_key_path 👿"
  exit 1
fi

# Check if the repository SSH URL is valid (basic check)
if ! echo "$repository_ssh_url" | grep -q "^git@github.com:.*\.git$"; then
  echo "👿 Repository SSH URL does not seem valid: $repository_ssh_url 👿"
  exit 1
fi

# Add and commit the code
echo "🎽🎽 - Adding and committing the code..."
git add .
git commit -m "$commit_message"

# Set up SSH and check connection
echo "🎽🎽🎽🎽 Pushing the code ... using SSH Key ..."
eval "$(ssh-agent -s)"
ssh-add "$ssh_key_path" || { echo "👿 Failed to add SSH key. 👿"; exit 1; }
ssh -T git@github.com
echo
# Set the remote URL
echo "🍎 🍎 🍎 Setting remote SSH URL ... $2"
git remote set-url origin "$repository_ssh_url"

# Push the code
echo
echo "🍎 🍎 🍎 ... Pushing the code ..."
git push || { echo "👿👿👿👿 Failed to push code. 👿"; exit 1; }
echo
echo "DONE pushing!! 🥬 🥬 🥬 🥬 🥬 🥬 🥬 🥬 🥬 🥬 🥬"
echo

