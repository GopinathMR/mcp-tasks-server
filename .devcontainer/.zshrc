# Path to your oh-my-zsh installation.
export ZSH="/home/vscode/.oh-my-zsh"

# Set name of the theme to load
ZSH_THEME="robbyrussell"

# Plugins
plugins=(
  git
  docker
  gradle
  history
  jsontools
  vscode
  z
)

source $ZSH/oh-my-zsh.sh

# User configuration
export LANG=en_US.UTF-8
export EDITOR='code --wait'

# Java and Gradle aliases
alias gw='./gradlew'
alias gwb='./gradlew build'
alias gwt='./gradlew test'
alias gwc='./gradlew clean'
alias gwcb='./gradlew clean build'
alias gwct='./gradlew clean test'
alias gwi='./gradlew bootRun'

# Docker aliases
alias d='docker'
alias dc='docker-compose'
alias dps='docker ps'
alias di='docker images'

# Git aliases
alias gs='git status'
alias ga='git add'
alias gc='git commit'
alias gp='git push'
alias gl='git pull'
alias gco='git checkout'
alias gb='git branch'

# Add SDKMAN for managing Java versions
export SDKMAN_DIR="/usr/local/sdkman"
[[ -s "/usr/local/sdkman/bin/sdkman-init.sh" ]] && source "/usr/local/sdkman/bin/sdkman-init.sh"

# Add local bin to PATH
export PATH=$HOME/.local/bin:$PATH

# Set Java environment variables
export JAVA_HOME="/usr/lib/jvm/msopenjdk-current"
export PATH=$JAVA_HOME/bin:$PATH 